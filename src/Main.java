// Importa as classes necessárias
import java.awt.*;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Main {
    // Atributos principais da clase SistemaGestaoProjetos
    // Listas globais (estáticas) que armazenam os cadastros efetuados durante a execução do sistema

    // As variáveis do tipo List poderão ser populadsa com o que está nos arquivos
    private static final List<Usuario> usuarios = PersistenciaDados.carregarUsuarios();
    private static final List<Projeto> projetos = PersistenciaDados.carregarProjetos();
    private static final List<Tarefa> tarefas = PersistenciaDados.carregarTarefas();
    private static final List<Equipe> equipes = PersistenciaDados.carregarEquipes();

    private static final DateTimeFormatter FORMATADOR_DATA = new DateTimeFormatterBuilder()
            .appendPattern("dd/MM/yyyy")
            .parseDefaulting(ChronoField.ERA, 1) // Define a era padrão para evitar problemas de análise
            .toFormatter(Locale.of("pt", "BR")) // Usa a localidade do Brasil para garantir o formato dd/MM/yyyy
            .withResolverStyle(ResolverStyle.STRICT);

    private static final int NUMERO_DIGITOS_CPF = 11;

    public static void main(String[] args) {
        // Variável que guarda a opção escolhida pelo usuário no menu
        int opcao = -1;

        do {
            try {
                // Exibição do menu de opções
                String input = JOptionPane.showInputDialog(null,
                        """
                                -> Cadastros
                                      1. Usuário
                                      2. Projeto
                                      3. Equipe
                                      4. Tarefa
                                ____________________________________
                                -> Consultas
                                      5. Usuários
                                      6. Projetos
                                      7. Equipes
                                      8. Tarefas
                                ____________________________________
                                -> Administração
                                      9. Gerenciar dados (apagar)
                                ____________________________________
                                -> Finalizar
                                      0. Para sair
                                ____________________________________
                                Escolha a opção desejada:""",
                        "Sistema de Gestão de Projetos Educacional",
                        JOptionPane.PLAIN_MESSAGE);
                if (input == null) break; // Caso feche a janela
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        // Cadastro de usuário
                        CadastrarUsuario();
                        break;
                    case 2:
                        // Cadastro de projeto
                        CadastrarProjeto();
                        break;
                    case 3:
                        // Cadastro de equipe
                        CadastrarEquipe();
                        break;
                    case 4:
                        // Cadastro de tarefa
                        CadastrarTarefa();
                        break;
                    case 5:
                        // Consulta de usuário
                        ConsultarUsuarios();
                        break;
                    case 6:
                        // Consulta de projeto
                        ConsultarProjetos();
                        break;
                    case 7:
                        // Consulta de equipe
                        ConsultarEquipes();
                        break;
                    case 8:
                        // Consulta de tarefa
                        ConsultarTarefas();
                        break;
                    case 9:
                        gerenciarDados();
                        break;
                }
            } catch (NumberFormatException e) {
                Mensagem("Erro: Digite apenas números para a opção.");
            } catch (Exception e) {
                Mensagem("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);

        // Salve todos os dados ao sair do programa para garantir que nada se perca
        PersistenciaDados.salvarUsuarios(usuarios);
        PersistenciaDados.salvarProjetos(projetos);
        PersistenciaDados.salvarTarefas(tarefas);
        PersistenciaDados.salvarEquipes(equipes);
    }

    // Método genérico para exibição de mensagens
    private static void Mensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    // Novo método para gerenciar a limpeza de arquivos
    private static void gerenciarDados() {
        int opcao = -1;

        do {
            try {
                String input = JOptionPane.showInputDialog(null,
                        """
                                -> Gerenciar Dados
                                      1. Apagar todos os dados
                                      2. Apagar dados dos usuários
                                      3. Apagar dados dos projetos
                                      4. Apagar dados dos equipes
                                      5. Apagar dados dos tarefas
                                    ____________________________________
                                      0. Para voltar ao menu principal
                                    ____________________________________
                                    Escolha a opção desejada:""",
                        "Gerenciamento de Dados",
                        JOptionPane.PLAIN_MESSAGE);

                if (input == null) break; // Retorna se o usuário fechar a janela
                opcao = Integer.parseInt(input);

                int confirmacao;

                switch (opcao) {
                    case 1:
                        confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar TODOS os dados?", "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (confirmacao == JOptionPane.YES_OPTION) {
                            PersistenciaDados.limparDados(
                                    PersistenciaDados.ARQUIVO_USUARIOS,
                                    PersistenciaDados.ARQUIVO_PROJETOS,
                                    PersistenciaDados.ARQUIVO_EQUIPES,
                                    PersistenciaDados.ARQUIVO_TAREFAS
                            );
                            usuarios.clear();
                            projetos.clear();
                            equipes.clear();
                            tarefas.clear();
                        }
                        break;
                    case 2:
                        confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar os dados do Usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (confirmacao == JOptionPane.YES_OPTION) {
                            PersistenciaDados.limparDados(PersistenciaDados.ARQUIVO_USUARIOS);
                            usuarios.clear();
                        }
                        break;
                    case 3:
                        confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar os dados do Projeto?", "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (confirmacao == JOptionPane.YES_OPTION) {
                            PersistenciaDados.limparDados(PersistenciaDados.ARQUIVO_PROJETOS);
                            projetos.clear();
                        }
                        break;
                    case 4:
                        confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar os dados da Equipe?", "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (confirmacao == JOptionPane.YES_OPTION) {
                            PersistenciaDados.limparDados(PersistenciaDados.ARQUIVO_EQUIPES);
                            equipes.clear();
                        }
                        break;
                    case 5:
                        confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar os dados da Tarefa?", "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (confirmacao == JOptionPane.YES_OPTION) {
                            PersistenciaDados.limparDados(PersistenciaDados.ARQUIVO_TAREFAS);
                            tarefas.clear();
                        }
                        break;
                    case 0:
                        // Volta para o menu principal
                        break;
                    default:
                        Mensagem("Opção inválida. Por favor, escolha uma das opções acima.");
                }
            } catch (NumberFormatException e) {
                Mensagem("Erro: Digite apenas números para a opção.");
            }
            catch (Exception e) {
                Mensagem("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    // Cadastro de usuário
    private static void CadastrarUsuario() throws ParseException {
        // Declação do JPanel a ser exibido (formulário de cadastro)
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Posicionando os labels
        panel.add(new JLabel("Nome:"), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("CPF:"), gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("E-mail:"), gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Cargo:"), gbc);

        gbc.gridy = 4;
        panel.add(new JLabel("Login:"), gbc);

        gbc.gridy = 5;
        panel.add(new JLabel("Senha:"), gbc);

        gbc.gridy = 6;
        panel.add(new JLabel("Perfil:"), gbc);

        // Posicionando os campos para digitação
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        JTextField nomeTextField = new JTextField(10);
        panel.add(nomeTextField, gbc);

        gbc.gridy = 1;
        MaskFormatter mascaraCpf;
        mascaraCpf = new MaskFormatter("###.###.###-##");
        mascaraCpf.setPlaceholderCharacter('_');
        JFormattedTextField jFormattedTextCpf = new JFormattedTextField(mascaraCpf);
        panel.add(jFormattedTextCpf, gbc);

        gbc.gridy = 2;
        JTextField emailTextField = new JTextField(10);
        panel.add(emailTextField, gbc);

        gbc.gridy = 3;
        JTextField cargoTextField = new JTextField(10);
        panel.add(cargoTextField, gbc);

        gbc.gridy = 4;
        JTextField loginTextField = new JTextField(10);
        panel.add(loginTextField, gbc);

        gbc.gridy = 5;
        JTextField senhaTextField = new JTextField(10);
        panel.add(senhaTextField, gbc);

        gbc.gridy = 6;

        String[] perfis = new String[] {"", "Administrador", "Gerente", "Colaborador"};
        JComboBox<String> listaPerfis = new JComboBox<>(perfis);
        panel.add(listaPerfis, gbc);

        // Exibindo o painel
        boolean mostrarPainel = true;

        while (mostrarPainel) {
            int reply = JOptionPane.showConfirmDialog(null, panel, "Cadastro de usuário",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (reply == JOptionPane.OK_OPTION) {
                // Atribuindo os valores
                String nome = nomeTextField.getText();
                String cpf = jFormattedTextCpf.getText();
                String email = emailTextField.getText();
                String cargo = cargoTextField.getText();
                String login = loginTextField.getText();
                String senha = senhaTextField.getText();
                String perfil = Objects.requireNonNull(listaPerfis.getSelectedItem()).toString();

                // Verificando se os campos foram preenchidos
                if (nome.isEmpty()) {
                    Mensagem("O nome é obrigatório");
                }
                else if (cpf.isEmpty() || cpf.equals("___.___.___-__")) {
                    Mensagem("O CPF é obrigatório");
                }
                else if (email.isEmpty()) {
                    Mensagem("O e-mail é obrigatório");
                }
                else if (cargo.isEmpty()) {
                    Mensagem("O cargo é obrigatório");
                }
                else if (login.isEmpty()) {
                    Mensagem("O login é obrigatório");
                }
                else if (senha.isEmpty()) {
                    Mensagem("A senha é obrigatório");
                }
                else if (perfil.isEmpty()) {
                    Mensagem("O perfil é obrigatório");
                }
                else {
                    //Validando o CPF
                    if (CpfValido(cpf)) {

                        Usuario verificaCpf = usuarios.stream().filter(u -> u.getCpf().equalsIgnoreCase(cpf)).findFirst().orElse(null);

                        if (verificaCpf != null) {
                            Mensagem("Já existe um usuário cadastrado com este CPF!");
                        }
                        else {
                            // Inserindo o usuário
                            usuarios.add(new Usuario(nome, cpf, email, cargo, login, senha, perfil));

                            PersistenciaDados.salvarUsuarios(usuarios);

                            Mensagem("Usuário cadastrado com sucesso!");

                            mostrarPainel = false;
                        }
                    }
                    else {
                        Mensagem("O CPF informado é inválido!");
                    }
                }
            }
            else  {
                mostrarPainel = false;
            }
        }
    }

    // Consulta de usuários
    private static void ConsultarUsuarios() {
        if (!usuarios.isEmpty()) {
            // Converte a lista para uma String, com quebras de linha
            String relatorioUsuarios = usuarios.toString().replace("[", "").replace("]", "").replace(", ", "\n\n");

            // Cria um JTextArea e coloca o texto nele
            JTextArea textArea = new JTextArea(relatorioUsuarios);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // Envolve o JTextArea em um JScrollPane
            JScrollPane scrollPane = new JScrollPane(textArea);

            // Define o tamanho preferido
            scrollPane.setPreferredSize(new Dimension(250, 400));

            // Exibe o JScrollPane no JOptionPane
            JOptionPane.showMessageDialog(
                    null,
                    scrollPane,
                    "Consulta de Usuários",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
        else {
            Mensagem("Não existe nenhum usuário cadastrado.");
        }
    }

    // Cadastro de projeto
    private static void CadastrarProjeto() throws ParseException {
        // Declação do JPanel a ser exibido (formulário de cadastro)
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Posicionando os labels
        panel.add(new JLabel("Nome do projeto:"), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Descrição:"), gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Data de início:"), gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Data de término prevista:"), gbc);

        gbc.gridy = 4;
        panel.add(new JLabel("Status:"), gbc);

        gbc.gridy = 5;
        panel.add(new JLabel("Gerente responsável:"), gbc);

        // Posicionando os campos para digitação
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        JTextField nomeTextField = new JTextField(10);
        panel.add(nomeTextField, gbc);

        gbc.gridy = 1;
        JTextField decricaoTextField = new JTextField(10);
        panel.add(decricaoTextField, gbc);

        gbc.gridy = 2;
        MaskFormatter mascaraDataInicio;
        mascaraDataInicio = new MaskFormatter("##/##/####");
        mascaraDataInicio.setPlaceholderCharacter('_');
        JFormattedTextField jFormattedTextDataInicio = new JFormattedTextField(mascaraDataInicio);
        panel.add(jFormattedTextDataInicio, gbc);

        gbc.gridy = 3;
        MaskFormatter mascaraDataTermino;
        mascaraDataTermino = new MaskFormatter("##/##/####");
        mascaraDataTermino.setPlaceholderCharacter('_');
        JFormattedTextField jFormattedTextDataTermino = new JFormattedTextField(mascaraDataTermino);
        panel.add(jFormattedTextDataTermino, gbc);

        gbc.gridy = 4;
        String[] status = new String[] {"", "Planejado", "Em andamento", "Concluído", "Cancelado"};
        JComboBox<String> listaStatus = new JComboBox<>(status);
        panel.add(listaStatus, gbc);

        gbc.gridy = 5;
        String[] gerentesCadastrados = extrairGerentes(usuarios);

        JComboBox<String> listaGerentes = new JComboBox<>(gerentesCadastrados);
        panel.add(listaGerentes, gbc);

        // Exibindo o painel
        boolean mostrarPainel = true;

        while (mostrarPainel) {
            int reply = JOptionPane.showConfirmDialog(null, panel, "Cadastro de projeto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (reply == JOptionPane.OK_OPTION) {
                //Atribuindo os valores
                String nome = nomeTextField.getText();
                String descricao = decricaoTextField.getText();
                String dataInicio = jFormattedTextDataInicio.getText();
                String dataTermino = jFormattedTextDataTermino.getText();
                String statusSelecionado = Objects.requireNonNull(listaStatus.getSelectedItem()).toString();
                String nomeGerente = Objects.requireNonNull(listaGerentes.getSelectedItem()).toString();

                // Verificando se os campos foram preenchidos
                if (nome.isEmpty()) {
                    Mensagem("O nome do projeto é obrigatório");
                }
                else if (descricao.isEmpty()) {
                    Mensagem("A descricao é obrigatório");
                }
                else if (dataInicio.isEmpty() || dataInicio.equals("__/__/____")) {
                    Mensagem("A data de início é obrigatório");
                }
                else if (dataTermino.isEmpty() || dataTermino.equals("__/__/____")) {
                    Mensagem("A data de témino prevista é obrigatório");
                }
                else if (statusSelecionado.isEmpty()) {
                    Mensagem("O status é obrigatório");
                }
                else if (nomeGerente.isEmpty()) {
                    Mensagem("O nome do gerente responsável é obrigatório");
                }
                else {
                    //Validando a data de início e data de término prevista
                    if (ValidarData(dataInicio) && ValidarData(dataTermino)) {
                        Usuario gerente = usuarios.stream().filter(u -> u.getNome().equalsIgnoreCase(nomeGerente)).findFirst().orElse(null);

                        // Inserindo o projeto
                        projetos.add(new Projeto(nome, descricao, dataInicio, dataTermino, statusSelecionado, gerente));

                        PersistenciaDados.salvarProjetos(projetos);

                        Mensagem("Projeto cadastrado com sucesso!");

                        mostrarPainel = false;
                    }
                    else {
                        Mensagem("A data informada é inválida");
                    }
                }
            }
            else  {
                mostrarPainel = false;
            }
        }
    }

    // Consulta de projetos
    private static void ConsultarProjetos() {
        if (!projetos.isEmpty()) {
            // Converte a lista para uma String, com quebras de linha
            String relatorioProjetos = projetos.toString().replace("[", "").replace("]", "").replace(", ", "\n\n");

            // Cria um JTextArea e coloca o texto nele
            JTextArea textArea = new JTextArea(relatorioProjetos);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // Envolve o JTextArea em um JScrollPane
            JScrollPane scrollPane = new JScrollPane(textArea);

            // Define o tamanho preferido
            scrollPane.setPreferredSize(new Dimension(250, 400));

            // Exibe o JScrollPane no JOptionPane
            JOptionPane.showMessageDialog(
                    null,
                    scrollPane,
                    "Consulta de Projetos",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
        else {
            Mensagem("Não existe nenhum projeto cadastrado.");
        }
    }

    // Cadastro de equipe
    private static void CadastrarEquipe() {
        // Declação do JPanel a ser exibido (formulário de cadastro)
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Posicionando os labels
        panel.add(new JLabel("Nome da equipe:"), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Descrição:"), gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Membro:"), gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Projeto:"), gbc);

        // Posicionando os campos para digitação
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        JTextField nomeTextField = new JTextField(10);
        panel.add(nomeTextField, gbc);

        gbc.gridy = 1;
        JTextField descricaoTextField = new JTextField(10);
        panel.add(descricaoTextField, gbc);

        gbc.gridy = 2;
        String[] usuariosCadastrados = new String[usuarios.size()+1];
        usuariosCadastrados[0] = "";
        for (int i = 0; i < usuarios.size(); i++) {
            usuariosCadastrados[i+1] = usuarios.get(i).getNome();
        }
        JComboBox<String> listaUsuarios = new JComboBox<>(usuariosCadastrados);
        panel.add(listaUsuarios, gbc);

        gbc.gridy = 3;
        String[] projetosCadastrados = new String[projetos.size()+1];
        projetosCadastrados[0] = "";
        for (int i = 0; i < projetos.size(); i++) {
            projetosCadastrados[i+1] = projetos.get(i).getNome();
        }
        JComboBox<String> listaProjetos = new JComboBox<>(projetosCadastrados);
        panel.add(listaProjetos, gbc);

        // Exibindo o painel
        boolean mostrarPainel = true;

        while (mostrarPainel) {
            int reply = JOptionPane.showConfirmDialog(null, panel, "Cadastro de equipe",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (reply == JOptionPane.OK_OPTION) {
                //Atribuindo os valores
                String nome = nomeTextField.getText();
                String descricao = descricaoTextField.getText();
                String membroSelecionado = Objects.requireNonNull(listaUsuarios.getSelectedItem()).toString();
                String projetoSelecionado = Objects.requireNonNull(listaProjetos.getSelectedItem()).toString();

                // Verificando se os campos foram preenchidos
                if (nome.isEmpty()) {
                    Mensagem("O nome da equipe é obrigatório");
                }
                else if (descricao.isEmpty()) {
                    Mensagem("A descricao é obrigatório");
                }
                else if (membroSelecionado.isEmpty()) {
                    Mensagem("O membro é obrigatório");
                }
                else if (projetoSelecionado.isEmpty()) {
                    Mensagem("O projeto é obrigatório");
                }
                else {
                    Usuario membro = usuarios.stream().filter(u -> u.getNome().equalsIgnoreCase(membroSelecionado)).findFirst().orElse(null);
                    Projeto projeto = projetos.stream().filter(p -> p.getNome().equalsIgnoreCase(projetoSelecionado)).findFirst().orElse(null);

                    Equipe equipeExistente = equipes.stream().filter(u -> u.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);

                    if (equipeExistente != null) {
                        // A equipe já existe, apenas adiciona o membro e o projeto a ela
                        equipeExistente.adicionarMembro(membro);
                        equipeExistente.adicionarProjeto(projeto);
                        Mensagem("Membro e projeto adicionados à equipe existente!");
                    } else {
                        // A equipe não existe, cria uma nova
                        Equipe novaEquipe = new Equipe(nome, descricao);
                        novaEquipe.adicionarMembro(membro);
                        novaEquipe.adicionarProjeto(projeto);
                        equipes.add(novaEquipe);
                        Mensagem("Nova equipe cadastrada com sucesso!");
                    }

                    PersistenciaDados.salvarEquipes(equipes);

                    mostrarPainel = false;
                }
            }
            else  {
                mostrarPainel = false;
            }
        }
    }

    // Consulta de Equipes
    private static void ConsultarEquipes() {
        if (!equipes.isEmpty()) {
            // Converte a lista para uma String, com quebras de linha
            String relatorioEquipes = equipes.toString().replace("[", "").replace("]", "").replace(", ", "\n\n");

            // Cria um JTextArea e coloca o texto nele
            JTextArea textArea = new JTextArea(relatorioEquipes);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // Envolve o JTextArea em um JScrollPane
            JScrollPane scrollPane = new JScrollPane(textArea);

            // Define o tamanho preferido
            scrollPane.setPreferredSize(new Dimension(250, 400));

            // Exibe o JScrollPane no JOptionPane
            JOptionPane.showMessageDialog(
                    null,
                    scrollPane,
                    "Consulta de Equipes",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
        else {
            Mensagem("Não existe nenhuma equipe cadastrada.");
        }
    }

    // Cadastro de tarefa
    private static void CadastrarTarefa() throws ParseException {
        // Declação do JPanel a ser exibido (formulário de cadastro)
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Posicionando os labels
        panel.add(new JLabel("Nome da tarefa:"), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Descrição:"), gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Prazo:"), gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Status:"), gbc);

        gbc.gridy = 4;
        panel.add(new JLabel("Responsável:"), gbc);

        gbc.gridy = 5;
        panel.add(new JLabel("Projeto:"), gbc);

        // Posicionando os campos para digitação
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;

        JTextField nomeTextField = new JTextField(10);
        panel.add(nomeTextField, gbc);

        gbc.gridy = 1;
        JTextField descricaoTextField = new JTextField(10);
        panel.add(descricaoTextField, gbc);

        gbc.gridy = 2;
        MaskFormatter mascaraDataPrazo;
        mascaraDataPrazo = new MaskFormatter("##/##/####");
        mascaraDataPrazo.setPlaceholderCharacter('_');
        JFormattedTextField jFormattedTextDataPrazo = new JFormattedTextField(mascaraDataPrazo);
        panel.add(jFormattedTextDataPrazo, gbc);

        gbc.gridy = 3;
        String[] status = new String[] {"", "Pendente", "Em andamento", "Concluída"};
        JComboBox<String> listaStatus = new JComboBox<>(status);
        panel.add(listaStatus, gbc);

        gbc.gridy = 4;
        String[] usuariosCadastrados = new String[usuarios.size()+1];
        usuariosCadastrados[0] = "";
        for (int i = 0; i < usuarios.size(); i++) {
            usuariosCadastrados[i+1] = usuarios.get(i).getNome();
        }
        JComboBox<String> listaUsuarios = new JComboBox<>(usuariosCadastrados);
        panel.add(listaUsuarios, gbc);

        gbc.gridy = 5;
        String[] projetosCadastrados = new String[projetos.size()+1];
        projetosCadastrados[0] = "";
        for (int i = 0; i < projetos.size(); i++) {
            projetosCadastrados[i+1] = projetos.get(i).getNome();
        }
        JComboBox<String> listaProjetos = new JComboBox<>(projetosCadastrados);
        panel.add(listaProjetos, gbc);

        // Exibindo o painel
        boolean mostrarPainel = true;

        while (mostrarPainel) {
            int reply = JOptionPane.showConfirmDialog(null, panel, "Cadastro de tarefa",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (reply == JOptionPane.OK_OPTION) {
                //Atribuindo os valores
                String nome = nomeTextField.getText();
                String descricao = descricaoTextField.getText();
                String dataPrazo = jFormattedTextDataPrazo.getText();
                String statusSelecionado = Objects.requireNonNull(listaStatus.getSelectedItem()).toString();
                String responsavel = Objects.requireNonNull(listaUsuarios.getSelectedItem()).toString();
                String projeto = Objects.requireNonNull(listaProjetos.getSelectedItem()).toString();

                ArrayList<Projeto> projetoSelecionado = new ArrayList<>();
                for (Projeto value : projetos) {
                    if (value.getNome().equals(projeto)) {
                        projetoSelecionado.add(value);
                    }
                }

                // Verificando se os campos foram preenchidos
                if (nome.isEmpty()) {
                    Mensagem("O nome da tarefa é obrigatório");
                }
                else if (descricao.isEmpty()) {
                    Mensagem("A descrição é obrigatório");
                }
                else if (dataPrazo.isEmpty() || dataPrazo.equals("__/__/____")) {
                    Mensagem("A data do prazo é obrigatório");
                }
                else if (statusSelecionado.isEmpty()) {
                    Mensagem("O status é obrigatório");
                }
                else if (responsavel.isEmpty()) {
                    Mensagem("O responsável é obrigatório");
                }
                else if (projeto.isEmpty()) {
                    Mensagem("O projeto é obrigatório");
                }
                else {
                    //Validando a data do prazo
                    if (ValidarData(dataPrazo)) {
                        Usuario membro = usuarios.stream().filter(u -> u.getNome().equalsIgnoreCase(responsavel)).findFirst().orElse(null);

                        // Inserindo a tarefa
                        tarefas.add(new Tarefa(nome, descricao, membro, dataPrazo, statusSelecionado, projetoSelecionado.getFirst()));

                        PersistenciaDados.salvarTarefas(tarefas);

                        Mensagem("Tarefa cadastrada com sucesso!");

                        mostrarPainel = false;
                    }
                    else {
                        Mensagem("A data informada é inválida");
                    }
                }
            }
            else  {
                mostrarPainel = false;
            }
        }
    }

    // Consulta de Tarefas
    private static void ConsultarTarefas() {
        if (!tarefas.isEmpty()) {
            // Converte a lista para uma String, com quebras de linha
            String relatorioTarefas = tarefas.toString().replace("[", "").replace("]", "").replace(", ", "\n\n");

            // Cria um JTextArea e coloca o texto nele
            JTextArea textArea = new JTextArea(relatorioTarefas);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // Envolve o JTextArea em um JScrollPane
            JScrollPane scrollPane = new JScrollPane(textArea);

            // Define o tamanho preferido
            scrollPane.setPreferredSize(new Dimension(250, 400));

            // Exibe o JScrollPane no JOptionPane
            JOptionPane.showMessageDialog(
                    null,
                    scrollPane,
                    "Consulta de Tarefas",
                    JOptionPane.PLAIN_MESSAGE
            );
        }
        else {
            Mensagem("Não existe nenhuma tarefa cadastrada.");
        }
    }

    // Extrai/retorna um array de String com somente os perfis iguais a "Gerente"
    public static String[] extrairGerentes(List<Usuario> usuarios) {
        // Usa uma lista para armazenar os nomes dos gerentes
        List<String> gerentesEncontrados = new ArrayList<>();

        // Adiciona um valor padrão na primeira posição, se necessário
        gerentesEncontrados.add("");

        // Itera sobre a lista de usuários
        for (Usuario usuario : usuarios) {
            // Verifica o perfil do usuário
            if (usuario.getPerfil().equals("Gerente")) {
                // Adiciona o nome à lista apenas se o perfil for Gerente
                gerentesEncontrados.add(usuario.getNome());
            }
        }

        // Converte a lista para um array e retorna
        return gerentesEncontrados.toArray(new String[0]);
    }

    // Método para validar se o CPF informado é válido
    public static boolean CpfValido(String cpf) {
        if (cpf == null || cpf.length() < NUMERO_DIGITOS_CPF) {
            return false;
        }

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        if (cpf.length() != NUMERO_DIGITOS_CPF) {
            return false;
        }

        // Verifica CPFs com todos os dígitos iguais (ex: 111.111.111-11)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Calcula e valida o primeiro dígito verificador
            int digito1 = calcularDigito(cpf.substring(0, 9));
            if (digito1 != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }

            // Calcula e valida o segundo dígito verificador
            int digito2 = calcularDigito(cpf.substring(0, 10));

            return digito2 == Character.getNumericValue(cpf.charAt(10));
        } catch (NumberFormatException e) {
            // Caso a string não seja um número válido
            return false;
        }
    }

    // Método que calcula o dígito do CPF
    private static int calcularDigito(String str) {
        int soma = 0;
        int peso = str.length() + 1;
        for (char c : str.toCharArray()) {
            soma += Character.getNumericValue(c) * peso;
            peso--;
        }

        int digito = 11 - (soma % 11);
        return (digito == 10 || digito == 11) ? 0 : digito;
    }

    // Método para validar se uma data está no formato válido
    public static boolean ValidarData(String data) {
        if (data == null || data.isEmpty()) {
            return false;
        }
        try {
            FORMATADOR_DATA.parse(data);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

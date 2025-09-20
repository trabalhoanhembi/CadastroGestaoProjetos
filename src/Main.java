// Importa as classes necessárias
import java.awt.*;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Main {
    // Atributos principais da classe Main
    // As variáveis do tipo List, são listas globais (estáticas) que armazenam os cadastros efetuados durante a execução do sistema
    // E poderão ser populadas com o que está nos arquivos que são gerados/salvos ao sair do sistema
    private static final List<Usuario> usuarios = PersistenciaDados.carregarUsuarios();
    private static final List<Projeto> projetos = PersistenciaDados.carregarProjetos();
    private static final List<Tarefa> tarefas = PersistenciaDados.carregarTarefas();
    private static final List<Equipe> equipes = PersistenciaDados.carregarEquipes();

    public static void main(String[] args) {
        int opcao = -1;

        do {
            try {
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
                        -> Gerenciar
                              9. Usuários
                              10. Projetos
                              11. Equipes
                              12. Tarefas
                        ____________________________________
                        -> Administração
                              13. Gerenciar dados (apagar)
                        ____________________________________
                        -> Finalizar
                              0. Para sair
                        ____________________________________
                        Escolha a opção desejada:""",
                        "Sistema de Gestão de Projetos Educacional",
                        JOptionPane.PLAIN_MESSAGE);

                if (input == null) break;
                opcao = Integer.parseInt(input);

                switch (opcao) {
                    case 1:
                        cadastrarUsuario();
                        break;
                    case 2:
                        cadastrarProjeto();
                        break;
                    case 3:
                        cadastrarEquipe();
                        break;
                    case 4:
                        cadastrarTarefa();
                        break;
                    case 5:
                        consultarUsuarios();
                        break;
                    case 6:
                        consultarProjetos();
                        break;
                    case 7:
                        consultarEquipes();
                        break;
                    case 8:
                        consultarTarefas();
                        break;
                    case 9:
                        gerenciarUsuarios();
                        break;
                    case 10:
                        gerenciarProjetos();
                        break;
                    case 11:
                        gerenciarEquipes();
                        break;
                    case 12:
                        gerenciarTarefas();
                        break;
                    case 13:
                        gerenciarDados();
                        break;
                    case 0:
                        // Sai do loop
                        break;
                    default:
                        mensagem("Opção inválida. Por favor, escolha uma das opções acima.");
                }
            }
            catch (NumberFormatException e) {
                mensagem("Erro: Digite apenas números para a opção.");
            }
            catch (Exception e) {
                mensagem("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);

        // Salve todos os dados ao sair do programa para garantir que nada se perca
        PersistenciaDados.salvarUsuarios(usuarios);
        PersistenciaDados.salvarProjetos(projetos);
        PersistenciaDados.salvarTarefas(tarefas);
        PersistenciaDados.salvarEquipes(equipes);
    }

    // -------------------------------
    // Métodos relacionados ao USUÁRIO
    // -------------------------------
    // Método para realizar o cadastro de usuário
    private static void cadastrarUsuario() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Campos de entrada
        JTextField nomeField = new JTextField(10);
        JFormattedTextField cpfField;
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            cpfField = new JFormattedTextField(mascaraCpf);
        }
        catch (ParseException e) {
            mensagem("Erro ao formatar o CPF. Tente novamente.");
            return;
        }
        JTextField emailField = new JTextField(10);
        JTextField cargoField = new JTextField(10);
        JTextField loginField = new JTextField(10);
        JPasswordField senhaField = new JPasswordField(10);

        // ComboBox para o perfil
        String[] perfis = new String[]{"", "Administrador", "Gerente", "Colaborador"};
        JComboBox<String> perfilBox = new JComboBox<>(perfis);

        // Adicionando componentes ao painel
        adicionarCampoPainel(panel, gbc, "Nome:", nomeField);
        adicionarCampoPainel(panel, gbc, "CPF:", cpfField);
        adicionarCampoPainel(panel, gbc, "E-mail:", emailField);
        adicionarCampoPainel(panel, gbc, "Cargo:", cargoField);
        adicionarCampoPainel(panel, gbc, "Login:", loginField);
        adicionarCampoPainel(panel, gbc, "Senha:", senhaField);
        adicionarCampoPainel(panel, gbc, "Perfil:", perfilBox);

        boolean cadastroConcluido = false;

        while (!cadastroConcluido) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Cadastrar Usuário",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText().trim();
                String cpf = cpfField.getText();
                String email = emailField.getText().trim();
                String cargo = cargoField.getText().trim();
                String login = loginField.getText().trim();
                String senha = new String(senhaField.getPassword());
                String perfil = Objects.requireNonNull(perfilBox.getSelectedItem()).toString();

                if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || cargo.isEmpty() || login.isEmpty() || senha.isEmpty() || perfil.isEmpty()) {
                    mensagem("Todos os campos são obrigatórios.");
                    // O loop continua, reexibindo o formulário
                }
                else if (!validarCpf(cpf)) {
                    mensagem("CPF inválido. Por favor, digite um CPF válido.");
                    // O loop continua, reexibindo o formulário
                }
                else if (usuarios.stream().anyMatch(u -> u.getCpf().equals(cpf))) {
                    // Verificação de duplicidade de CPF
                    mensagem("Já existe um usuário cadastrado com este CPF.");
                }
                else if (!validarEmail(email)) {
                    mensagem("Formato de e-mail inválido.");
                    // O loop continua, reexibindo o formulário
                }
                else {
                    Usuario novoUsuario = new Usuario(nome, cpf, email, cargo, login, senha, perfil);
                    usuarios.add(novoUsuario);

                    PersistenciaDados.salvarUsuarios(usuarios);

                    mensagem("Usuário cadastrado com sucesso!");

                    cadastroConcluido = true; // Sai do loop
                }
            }
            else {
                // Usuário clicou em Cancelar ou fechou a janela
                cadastroConcluido = true; // Sai do loop sem cadastrar
            }
        }
    }

    // Método para para consultar usuários
    private static void consultarUsuarios() {
        if (!usuarios.isEmpty()) {
            // Converte a lista para uma String, com quebras de linha
            String relatorioUsuarios = usuarios.toString().replace("[", "").replace("]", "").replace(", ", "\n\n");

            // Cria um JTextArea com o que precisa ser exibido e atribui um JScrollPane
            JScrollPane scrollPane = new JScrollPane(criarTextArea(relatorioUsuarios));

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
            mensagem("Não existe nenhum usuário cadastrado.");
        }
    }

    // Método para o gerenciamento dos usuários (edição ou exclusão)
    private static void gerenciarUsuarios() {
        if (usuarios.isEmpty()) {
            mensagem("Não há usuários cadastrados para gerenciar.");
            return;
        }

        int opcao = -1;
        do {
            try {
                String input = JOptionPane.showInputDialog(null,
                        """
                                -> Gerenciar Usuários
                                      1. Editar um usuário
                                      2. Excluir um usuário
                                    ____________________________________
                                      0. Para voltar ao menu principal
                                    ____________________________________
                                    Escolha a opção desejada:""",
                        "Gerenciamento de Usuários",
                        JOptionPane.PLAIN_MESSAGE);

                if (input == null) break;
                opcao = Integer.parseInt(input);

                // A variável nomeUsuarioSelecionado agora é declarada dentro de cada case
                String nomeUsuarioSelecionado;
                Optional<Usuario> usuarioSelecionado;
                JPanel panel;
                JComboBox<String> usuarioComboBox;
                int confirmacao;

                switch (opcao) {
                    case 1:
                        String[] nomesUsuarios = usuarios.stream().map(Usuario::getNome).toArray(String[]::new);
                        usuarioComboBox = new JComboBox<>(nomesUsuarios);
                        panel = new JPanel();
                        panel.add(new JLabel("Selecione o usuário:"));
                        panel.add(usuarioComboBox);

                        confirmacao = JOptionPane.showConfirmDialog(null, panel, "Selecionar Usuário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (confirmacao == JOptionPane.OK_OPTION) {
                            nomeUsuarioSelecionado = Objects.requireNonNull(usuarioComboBox.getSelectedItem()).toString();
                            usuarioSelecionado = usuarios.stream()
                                    .filter(u -> u.getNome().equals(nomeUsuarioSelecionado))
                                    .findFirst();
                            usuarioSelecionado.ifPresent(Main::editarUsuario);
                        }
                        break;
                    case 2:
                        String[] nomesUsuariosExcluir = usuarios.stream().map(Usuario::getNome).toArray(String[]::new);
                        usuarioComboBox = new JComboBox<>(nomesUsuariosExcluir);
                        panel = new JPanel();
                        panel.add(new JLabel("Selecione o usuário:"));
                        panel.add(usuarioComboBox);

                        confirmacao = JOptionPane.showConfirmDialog(null, panel, "Excluir Usuário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (confirmacao == JOptionPane.OK_OPTION) {
                            nomeUsuarioSelecionado = Objects.requireNonNull(usuarioComboBox.getSelectedItem()).toString();
                            excluirUsuario(nomeUsuarioSelecionado);
                        }
                        break;
                    case 0:
                        // Sai do loop
                        break;
                    default:
                        mensagem("Opção inválida. Por favor, escolha uma das opções acima.");
                }
            }
            catch (NumberFormatException e) {
                mensagem("Erro: Digite apenas números para a opção.");
            }
            catch (Exception e) {
                mensagem("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    // Método para editar o usuário selecionado
    private static void editarUsuario(Usuario usuario) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Campos de edição com dados atuais
        JTextField nomeField = new JTextField(usuario.getNome(), 10);
        JFormattedTextField cpfField;
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            cpfField = new JFormattedTextField(mascaraCpf);
            cpfField.setText(usuario.getCpf());
        }
        catch (ParseException e) {
            mensagem("Erro ao formatar o CPF.");
            return;
        }
        JTextField emailField = new JTextField(usuario.getEmail(), 10);
        JTextField cargoField = new JTextField(usuario.getCargo(), 10);
        JTextField loginField = new JTextField(usuario.getLogin(), 10);
        JPasswordField senhaField = new JPasswordField(usuario.getSenha(), 10);

        // ComboBox para o perfil
        String[] perfis = new String[]{"Administrador", "Gerente", "Colaborador"};
        JComboBox<String> perfilBox = new JComboBox<>(perfis);
        perfilBox.setSelectedItem(usuario.getPerfil());

        // Adicionando componentes ao painel
        adicionarCampoPainel(panel, gbc, "Nome:", nomeField);
        adicionarCampoPainel(panel, gbc, "CPF:", cpfField);
        adicionarCampoPainel(panel, gbc, "E-mail:", emailField);
        adicionarCampoPainel(panel, gbc, "Cargo:", cargoField);
        adicionarCampoPainel(panel, gbc, "Login:", loginField);
        adicionarCampoPainel(panel, gbc, "Senha:", senhaField);
        adicionarCampoPainel(panel, gbc, "Perfil:", perfilBox);

        boolean edicaoConcluida = false;

        while (!edicaoConcluida) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Usuário",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String novoNome = nomeField.getText().trim();
                String novoCpf = cpfField.getText();
                String novoEmail = emailField.getText().trim();
                String novoCargo = cargoField.getText().trim();
                String novoLogin = loginField.getText().trim();
                String novaSenha = new String(senhaField.getPassword());
                String novoPerfil = Objects.requireNonNull(perfilBox.getSelectedItem()).toString();

                if (novoNome.isEmpty() || novoCpf.isEmpty() || novoEmail.isEmpty() || novoCargo.isEmpty() || novoLogin.isEmpty() || novaSenha.isEmpty() || novoPerfil.isEmpty()) {
                    mensagem("Todos os campos são obrigatórios.");
                }
                else if (!validarEmail(novoEmail)) {
                    mensagem("Formato de e-mail inválido.");
                }
                else if (!validarCpf(novoCpf)) {
                    mensagem("CPF inválido. Por favor, digite um CPF válido.");
                }
                else if (!usuario.getCpf().equals(novoCpf) && usuarios.stream().anyMatch(u -> u.getCpf().equals(novoCpf))) {
                    // Verificação de duplicidade, exceto para o próprio usuário
                    mensagem("Já existe um usuário cadastrado com este CPF.");
                }
                else {
                    usuario.setNome(novoNome);
                    usuario.setCpf(novoCpf);
                    usuario.setEmail(novoEmail);
                    usuario.setCargo(novoCargo);
                    usuario.setLogin(novoLogin);
                    usuario.setSenha(novaSenha);
                    usuario.setPerfil(novoPerfil);

                    PersistenciaDados.salvarUsuarios(usuarios);

                    mensagem("Usuário editado com sucesso!");

                    edicaoConcluida = true;
                }
            }
            else {
                // Usuário clicou em Cancelar ou fechou a janela
                edicaoConcluida = true;
            }
        }
    }

    // Método para excluir o usuário selecionado
    private static void excluirUsuario(String nomeUsuario) {
        // 1. Encontra o usuário a ser excluído
        Optional<Usuario> usuarioParaExcluir = usuarios.stream()
                .filter(u -> u.getNome().equals(nomeUsuario))
                .findFirst();

        if (usuarioParaExcluir.isPresent()) {
            Usuario usuario = usuarioParaExcluir.get();

            // 2. Verifica se o usuário está associado a alguma tarefa
            boolean temTarefaAssociada = tarefas.stream()
                    .anyMatch(t -> t.getResponsavel().equals(usuario.getNome()));

            // 3. Verifica se o usuário é gerente de algum projeto
            boolean ehGerenteDeProjeto = projetos.stream()
                    .anyMatch(p -> p.getGerenteResponsavel().equals(usuario.getNome()));

            // 4. Verifica se o usuário é membro de alguma equipe
            boolean ehMembroDeEquipe = equipes.stream()
                    .anyMatch(e -> e.getMembros().stream().anyMatch(m -> m.equals(usuario)));

            if (temTarefaAssociada || ehGerenteDeProjeto || ehMembroDeEquipe) {
                mensagem("Não é possível excluir o usuário '" + nomeUsuario + "' pois ele está associado a uma ou mais tarefas, projetos ou equipes.");
            }
            else {
                int confirmacao = JOptionPane.showConfirmDialog(null,
                        "Tem certeza que deseja excluir o usuário '" + nomeUsuario + "'?",
                        "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

                if (confirmacao == JOptionPane.YES_OPTION) {
                    usuarios.remove(usuario);
                    PersistenciaDados.salvarUsuarios(usuarios);
                    mensagem("Usuário '" + nomeUsuario + "' excluído com sucesso.");
                }
            }
        }
        else {
            mensagem("Erro: Usuário não encontrado.");
        }
    }

    // -------------------------------
    // Métodos relacionados ao PROJETO
    // -------------------------------
    // Método para realizar o cadastro de projeto
    private static void cadastrarProjeto() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Campos de entrada
        JTextField nomeTextField = new JTextField(10);
        JTextField descricaoTextField = new JTextField(10);
        JFormattedTextField dataInicioField;
        JFormattedTextField dataTerminoField;
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            dataInicioField = new JFormattedTextField(mascaraData);
            dataTerminoField = new JFormattedTextField(mascaraData);
        }
        catch (ParseException e) {
            mensagem("Erro ao formatar as datas. Tente novamente.");
            return;
        }

        // ComboBoxes para status e gerente responsável
        String[] status = new String[]{"", "Planejado", "Em andamento", "Concluído", "Cancelado"};
        JComboBox<String> listaStatus = new JComboBox<>(status);

        String[] gerentesCadastrados = extrairGerentes(usuarios);
        JComboBox<String> listaGerentes = new JComboBox<>(gerentesCadastrados);

        // Adicionando componentes ao painel
        adicionarCampoPainel(panel, gbc, "Nome do projeto:", nomeTextField);
        adicionarCampoPainel(panel, gbc, "Descrição:", descricaoTextField);
        adicionarCampoPainel(panel, gbc, "Data de início:",dataInicioField);
        adicionarCampoPainel(panel, gbc, "Data de término prevista:",dataTerminoField);
        adicionarCampoPainel(panel, gbc, "Status:",listaStatus);
        adicionarCampoPainel(panel, gbc, "Gerente responsável:",listaGerentes);

        boolean cadastroConcluido = false;

        while (!cadastroConcluido) {
            int reply = JOptionPane.showConfirmDialog(null, panel, "Cadastro de projeto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (reply == JOptionPane.OK_OPTION) {
                String nome = nomeTextField.getText().trim();
                String descricao = descricaoTextField.getText().trim();
                String dataInicio = dataInicioField.getText();
                String dataTermino = dataTerminoField.getText();
                String statusSelecionado = Objects.requireNonNull(listaStatus.getSelectedItem()).toString();
                String nomeGerente = Objects.requireNonNull(listaGerentes.getSelectedItem()).toString();

                if (nome.isEmpty() || descricao.isEmpty() || dataInicio.equals("  /  /    ") || dataTermino.equals("  /  /    ") || statusSelecionado.isEmpty() || nomeGerente.isEmpty()) {
                    mensagem("Todos os campos são obrigatórios.");
                }
                else if (!validarData(dataInicio) || !validarData(dataTermino)) {
                    mensagem("Uma das datas informadas é inválida.");
                }
                else {
                    Usuario gerente = usuarios.stream().filter(u -> u.getNome().equalsIgnoreCase(nomeGerente)).findFirst().orElse(null);

                    // Inserindo o projeto
                    projetos.add(new Projeto(nome, descricao, dataInicio, dataTermino, statusSelecionado, gerente));

                    PersistenciaDados.salvarProjetos(projetos);

                    mensagem("Projeto cadastrado com sucesso!");

                    cadastroConcluido = true;
                }
            }
            else {
                // O usuário clicou em Cancelar ou fechou a janela
                cadastroConcluido = true;
            }
        }
    }

    // Método para para consultar projetos
    private static void consultarProjetos() {
        if (!projetos.isEmpty()) {
            // Converte a lista para uma String, com quebras de linha
            String relatorioProjetos = projetos.toString().replace("[", "").replace("]", "").replace(", ", "\n\n");

            // Cria um JTextArea com o que precisa ser exibido e atribui um JScrollPane
            JScrollPane scrollPane = new JScrollPane(criarTextArea(relatorioProjetos));

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
            mensagem("Não existe nenhum projeto cadastrado.");
        }
    }

    // Método para o gerenciamento dos projetos (edição ou exclusão)
    private static void gerenciarProjetos() {
        if (projetos.isEmpty()) {
            mensagem("Não há projetos cadastrados para gerenciar.");
            return;
        }

        int opcao = -1;
        do {
            try {
                String input = JOptionPane.showInputDialog(null,
                        """
                                -> Gerenciar Projetos
                                      1. Editar um projeto
                                      2. Excluir um projeto
                                    ____________________________________
                                      0. Para voltar ao menu principal
                                    ____________________________________
                                    Escolha a opção desejada:""",
                        "Gerenciamento de Projetos",
                        JOptionPane.PLAIN_MESSAGE);

                if (input == null) break; // Retorna se o usuário fechar a janela
                opcao = Integer.parseInt(input);

                String nomeProjetoSelecionado;
                Optional<Projeto> projetoSelecionado;
                int confirmacao;

                switch (opcao) {
                    case 1:
                        // Declaração das variáveis no escopo correto
                        String[] nomesProjetos = projetos.stream().map(Projeto::getNome).toArray(String[]::new);
                        JComboBox<String> projetoComboBox = new JComboBox<>(nomesProjetos);
                        JPanel panel = new JPanel();
                        panel.add(new JLabel("Selecione o projeto:"));
                        panel.add(projetoComboBox);

                        confirmacao = JOptionPane.showConfirmDialog(null, panel, "Selecionar Projeto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (confirmacao == JOptionPane.OK_OPTION) {
                            nomeProjetoSelecionado = Objects.requireNonNull(projetoComboBox.getSelectedItem()).toString();
                            projetoSelecionado = projetos.stream()
                                    .filter(p -> p.getNome().equals(nomeProjetoSelecionado))
                                    .findFirst();
                            projetoSelecionado.ifPresent(Main::editarProjeto);
                        }
                        break;
                    case 2:
                        // Declaração das variáveis no escopo correto
                        String[] nomesProjetosExcluir = projetos.stream().map(Projeto::getNome).toArray(String[]::new);
                        JComboBox<String> projetoComboBoxExcluir = new JComboBox<>(nomesProjetosExcluir);
                        JPanel panelExcluir = new JPanel();
                        panelExcluir.add(new JLabel("Selecione o projeto:"));
                        panelExcluir.add(projetoComboBoxExcluir);

                        confirmacao = JOptionPane.showConfirmDialog(null, panelExcluir, "Excluir Projeto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (confirmacao == JOptionPane.OK_OPTION) {
                            nomeProjetoSelecionado = Objects.requireNonNull(projetoComboBoxExcluir.getSelectedItem()).toString();
                            excluirProjeto(nomeProjetoSelecionado);
                        }
                        break;
                    case 0:
                        // Sai do loop
                        break;
                    default:
                        mensagem("Opção inválida. Por favor, escolha uma das opções acima.");
                }
            }
            catch (NumberFormatException e) {
                mensagem("Erro: Digite apenas números para a opção.");
            }
            catch (Exception e) {
                mensagem("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    // Método para editar o projeto selecionado
    private static void editarProjeto(Projeto projeto) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Campos de edição com dados atuais
        JTextField nomeField = new JTextField(projeto.getNome(), 10);
        JTextField descricaoField = new JTextField(projeto.getDescricao(), 10);
        JFormattedTextField dataInicioField;
        JFormattedTextField dataTerminoField;
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            dataInicioField = new JFormattedTextField(mascaraData);
            dataInicioField.setText(projeto.getDataInicio());
            dataTerminoField = new JFormattedTextField(mascaraData);
            dataTerminoField.setText(projeto.getDataTerminoPrevista());
        }
        catch (ParseException e) {
            mensagem("Erro ao formatar a data.");
            return;
        }

        // ComboBoxes para status e gerente
        String[] statusProjetos = new String[]{"Planejado", "Em andamento", "Concluído", "Cancelado"};
        JComboBox<String> statusBox = new JComboBox<>(statusProjetos);
        statusBox.setSelectedItem(projeto.getStatus());

        String[] gerentesCadastrados = extrairGerentes(usuarios);
        JComboBox<String> gerenteBox = new JComboBox<>(gerentesCadastrados);
        gerenteBox.setSelectedItem(projeto.getGerenteResponsavel());

        // Adicionando componentes ao painel
        adicionarCampoPainel(panel, gbc, "Nome:", nomeField);
        adicionarCampoPainel(panel, gbc, "Descrição:", descricaoField);
        adicionarCampoPainel(panel, gbc, "Data de Início:", dataInicioField);
        adicionarCampoPainel(panel, gbc, "Data de Término:", dataTerminoField);
        adicionarCampoPainel(panel, gbc, "Status:", statusBox);
        adicionarCampoPainel(panel, gbc, "Gerente:", gerenteBox);

        boolean edicaoConcluida = false;

        while (!edicaoConcluida) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Projeto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String novoNome = nomeField.getText();
                String novaDescricao = descricaoField.getText();
                String novaDataInicio = dataInicioField.getText();
                String novaDataTermino = dataTerminoField.getText();
                String novoStatus = Objects.requireNonNull(statusBox.getSelectedItem()).toString();
                String novoGerenteNome = Objects.requireNonNull(gerenteBox.getSelectedItem()).toString();

                if (novoNome.trim().isEmpty() || novaDescricao.trim().isEmpty() || novaDataInicio.equals("__/__/____") || novaDataTermino.equals("__/__/____") || novoStatus.isEmpty() || novoGerenteNome.isEmpty()) {
                    mensagem("Todos os campos são obrigatórios.");
                }
                else if (validarData(novaDataInicio) && validarData(novaDataTermino)) {
                    projeto.setNome(novoNome);
                    projeto.setDescricao(novaDescricao);
                    projeto.setDataInicio(novaDataInicio);
                    projeto.setDataTerminoPrevista(novaDataTermino);
                    projeto.setStatus(novoStatus);
                    projeto.setGerenteResponsavel(usuarios.stream().filter(u -> u.getNome().equals(novoGerenteNome)).findFirst().orElse(null));

                    PersistenciaDados.salvarProjetos(projetos);

                    mensagem("Projeto editado com sucesso!");

                    edicaoConcluida = true;
                } else {
                    mensagem("Uma das datas informadas é inválida.");
                }
            }
            else {
                // Usuário clicou em Cancelar ou fechou a janela
                edicaoConcluida = true;
            }
        }
    }

    // Método para excluir o projeto selecionado
    private static void excluirProjeto(String nomeProjeto) {
        int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir o projeto '" + nomeProjeto + "'?",
                "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            projetos.removeIf(p -> p.getNome().equals(nomeProjeto));

            PersistenciaDados.salvarProjetos(projetos);

            mensagem("Projeto '" + nomeProjeto + "' excluído com sucesso.");
        }
    }

    // ------------------------------
    // Métodos relacionados ao EQUIPE
    // ------------------------------
    // Método para realizar o cadastro de equipe
    private static void cadastrarEquipe() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Campos de texto para nome e descrição
        JTextField nomeTextField = new JTextField(10);
        JTextField descricaoTextField = new JTextField(10);

        // ComboBoxes para selecionar membros e projetos
        String[] usuariosCadastrados = new String[usuarios.size() + 1];
        usuariosCadastrados[0] = "";
        for (int i = 0; i < usuarios.size(); i++) {
            usuariosCadastrados[i + 1] = usuarios.get(i).getNome();
        }
        JComboBox<String> listaUsuarios = new JComboBox<>(usuariosCadastrados);

        String[] projetosCadastrados = new String[projetos.size() + 1];
        projetosCadastrados[0] = "";
        for (int i = 0; i < projetos.size(); i++) {
            projetosCadastrados[i + 1] = projetos.get(i).getNome();
        }
        JComboBox<String> listaProjetos = new JComboBox<>(projetosCadastrados);

        adicionarCampoPainel(panel, gbc, "Nome da equipe:", nomeTextField);
        adicionarCampoPainel(panel, gbc, "Descrição:", descricaoTextField);
        adicionarCampoPainel(panel, gbc, "Membro:", listaUsuarios);
        adicionarCampoPainel(panel, gbc, "Projeto:", listaProjetos);

        boolean cadastroConcluido = false;

        while (!cadastroConcluido) {
            int reply = JOptionPane.showConfirmDialog(null, panel, "Cadastro de equipe",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (reply == JOptionPane.OK_OPTION) {
                String nome = nomeTextField.getText();
                String descricao = descricaoTextField.getText();
                String membroSelecionado = (String) listaUsuarios.getSelectedItem();
                String projetoSelecionado = (String) listaProjetos.getSelectedItem();

                if (nome == null || nome.isEmpty() || descricao == null || descricao.isEmpty() || membroSelecionado == null || membroSelecionado.isEmpty() || projetoSelecionado == null || projetoSelecionado.isEmpty()) {
                    mensagem("Todos os campos são obrigatórios.");
                }
                else {
                    Usuario membro = usuarios.stream().filter(u -> u.getNome().equals(membroSelecionado)).findFirst().orElse(null);
                    Projeto projeto = projetos.stream().filter(p -> p.getNome().equals(projetoSelecionado)).findFirst().orElse(null);

                    if (membro == null || projeto == null) {
                        mensagem("Usuário ou projeto não encontrados.");
                    }
                    else {
                        Equipe equipeExistente = equipes.stream().filter(e -> e.getNome().equals(nome)).findFirst().orElse(null);

                        if (equipeExistente != null) {
                            equipeExistente.adicionarMembro(membro);
                            equipeExistente.adicionarProjeto(projeto);
                            mensagem("Membro e projeto adicionados à equipe existente!");
                        }
                        else {
                            Equipe novaEquipe = new Equipe(nome, descricao);
                            novaEquipe.adicionarMembro(membro);
                            novaEquipe.adicionarProjeto(projeto);
                            equipes.add(novaEquipe);
                            mensagem("Nova equipe cadastrada com sucesso!");
                        }

                        PersistenciaDados.salvarEquipes(equipes);

                        cadastroConcluido = true;
                    }
                }
            }
            else {
                // O usuário clicou em Cancelar ou fechou a janela
                cadastroConcluido = true;
            }
        }
    }

    // Método para o gerenciamento de equipes (edição ou exclusão)
    private static void gerenciarEquipes() {
        if (equipes.isEmpty()) {
            mensagem("Não há equipes cadastradas para gerenciar.");
            return;
        }

        int opcao = -1;

        do {
            try {
                String input = JOptionPane.showInputDialog(null,
                        """
                                -> Gerenciar Equipes
                                      1. Editar uma equipe
                                      2. Excluir uma equipe
                                    ____________________________________
                                      0. Para voltar ao menu principal
                                    ____________________________________
                                    Escolha a opção desejada:""",
                        "Gerenciamento de Equipes",
                        JOptionPane.PLAIN_MESSAGE);

                if (input == null) break; // Retorna se o usuário fechar a janela
                opcao = Integer.parseInt(input);

                // Lógica de seleção da equipe
                String[] nomesEquipes = equipes.stream().map(Equipe::getNome).toArray(String[]::new);
                JComboBox<String> equipeComboBox = new JComboBox<>(nomesEquipes);
                JPanel panel = new JPanel();
                panel.add(new JLabel("Selecione a equipe:"));
                panel.add(equipeComboBox);

                switch (opcao) {
                    case 1:
                        int editarEscolha = JOptionPane.showConfirmDialog(null, panel, "Editar Equipe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (editarEscolha == JOptionPane.OK_OPTION) {
                            String nomeEquipeSelecionada = Objects.requireNonNull(equipeComboBox.getSelectedItem()).toString();
                            Equipe equipeSelecionada = equipes.stream()
                                    .filter(e -> e.getNome().equals(nomeEquipeSelecionada))
                                    .findFirst()
                                    .orElse(null);
                            if (equipeSelecionada != null) {
                                editarEquipe(equipeSelecionada);
                            } else {
                                mensagem("Erro: Equipe não encontrada.");
                            }
                        }
                        break;
                    case 2:
                        int excluirEscolha = JOptionPane.showConfirmDialog(null, panel, "Excluir Equipe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (excluirEscolha == JOptionPane.OK_OPTION) {
                            String nomeEquipeSelecionada = Objects.requireNonNull(equipeComboBox.getSelectedItem()).toString();
                            excluirEquipe(nomeEquipeSelecionada);
                        }
                        break;
                    case 0:
                        // Sai do loop
                        break;
                    default:
                        mensagem("Opção inválida. Por favor, escolha uma das opções acima.");
                }
            }
            catch (NumberFormatException e) {
                mensagem("Erro: Digite apenas números para a opção.");
            }
            catch (Exception e) {
                mensagem("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    // Método para editar a equipe selecionada
    private static void editarEquipe(Equipe equipe) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Campos de edição
        JTextField nomeField = new JTextField(equipe.getNome(), 10);
        JTextField descricaoField = new JTextField(equipe.getDescricao(), 10);

        // ComboBoxes para membros (incluir/excluir)
        JComboBox<String> adicionarMembroBox = new JComboBox<>(obterUsuariosDisponiveis(equipe));

        String[] membrosParaRemover = equipe.getMembros().stream()
                .map(membro -> membro.getNome() + " | " + membro.getPerfil())
                .toArray(String[]::new);
        JComboBox<String> removerMembroBox = new JComboBox<>(membrosParaRemover);
        ((DefaultComboBoxModel<String>) removerMembroBox.getModel()).insertElementAt("", 0);
        removerMembroBox.setSelectedIndex(0);

        // ComboBoxes para projetos (incluir/excluir)
        JComboBox<String> adicionarProjetoBox = new JComboBox<>(obterProjetosDisponiveis(equipe));

        String[] projetosParaRemover = equipe.getProjetos().stream()
                .map(projeto -> projeto.getNome() + " | " + projeto.getGerenteResponsavel())
                .toArray(String[]::new);
        JComboBox<String> removerProjetoBox = new JComboBox<>(projetosParaRemover);
        ((DefaultComboBoxModel<String>) removerProjetoBox.getModel()).insertElementAt("", 0);
        removerProjetoBox.setSelectedIndex(0);

        // Adicionando componentes ao painel
        adicionarCampoPainel(panel, gbc, "Nome:", nomeField);
        adicionarCampoPainel(panel, gbc, "Descrição:", descricaoField);
        adicionarCampoPainel(panel, gbc, "Adicionar Membro:", adicionarMembroBox);
        adicionarCampoPainel(panel, gbc, "Remover Membro:", removerMembroBox);
        adicionarCampoPainel(panel, gbc, "Adicionar Projeto:", adicionarProjetoBox);
        adicionarCampoPainel(panel, gbc, "Remover Projeto:", removerProjetoBox);

        boolean edicaoConcluida = false;

        while (!edicaoConcluida) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Equipe: " + equipe.getNome(),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String novoNome = nomeField.getText();
                String novaDescricao = descricaoField.getText();
                String membroParaAdicionar = (String) adicionarMembroBox.getSelectedItem();
                String membroParaRemover = (String) removerMembroBox.getSelectedItem();
                String projetoParaAdicionar = (String) adicionarProjetoBox.getSelectedItem();
                String projetoParaRemover = (String) removerProjetoBox.getSelectedItem();

                if (novoNome.trim().isEmpty() || novaDescricao.trim().isEmpty()) {
                    mensagem("Nome e Descrição da equipe são obrigatórios.");
                }
                else {
                    equipe.setNome(novoNome);
                    equipe.setDescricao(novaDescricao);

                    // Lógica de inclusão
                    Usuario novoMembro = null;
                    if (membroParaAdicionar != null && !membroParaAdicionar.isEmpty()) {
                        novoMembro = usuarios.stream()
                                .filter(u -> u.getNome().equals(membroParaAdicionar))
                                .findFirst()
                                .orElse(null);
                        if (novoMembro != null) {
                            equipe.adicionarMembro(novoMembro);
                        }
                    }

                    if (projetoParaAdicionar != null && !projetoParaAdicionar.isEmpty()) {
                        Projeto novoProjeto = projetos.stream()
                                .filter(p -> p.getNome().equals(projetoParaAdicionar))
                                .findFirst()
                                .orElse(null);
                        if (novoProjeto != null) {
                            // Se um novo membro foi adicionado, associe-o ao projeto
                            if (novoMembro != null) {
                                novoProjeto.setGerenteResponsavel(novoMembro);
                            }
                            equipe.adicionarProjeto(novoProjeto);
                        }
                    }

                    // Lógica de exclusão
                    if (membroParaRemover != null && !membroParaRemover.isEmpty()) {
                        String nomeMembro = membroParaRemover.split(" \\| ")[0];
                        equipe.removerMembro(nomeMembro);
                    }
                    if (projetoParaRemover != null && !projetoParaRemover.isEmpty()) {
                        String nomeProjeto = projetoParaRemover.split(" \\| ")[0];
                        equipe.removerProjeto(nomeProjeto);
                    }

                    PersistenciaDados.salvarEquipes(equipes);

                    mensagem("Equipe editada com sucesso!");

                    edicaoConcluida = true;
                }
            }
            else {
                // Usuário clicou em Cancelar ou fechou a janela
                edicaoConcluida = true;
            }
        }
    }

    // Método para excluir a equipe selecionada
    private static void excluirEquipe(String nomeEquipe) {
        int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir a equipe '" + nomeEquipe + "'?",
                "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            equipes.removeIf(e -> e.getNome().equals(nomeEquipe));

            PersistenciaDados.salvarEquipes(equipes);

            mensagem("Equipe '" + nomeEquipe + "' excluída com sucesso.");
        }
    }

    // Método para para consultar equipes
    private static void consultarEquipes() {
        if (!equipes.isEmpty()) {
            // Converte a lista para uma String, com quebras de linha
            String relatorioEquipes = equipes.toString().replace("[", "").replace("]", "").replace(", ", "\n\n");

            // Cria um JTextArea com o que precisa ser exibido e atribui um JScrollPane
            JScrollPane scrollPane = new JScrollPane(criarTextArea(relatorioEquipes));

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
            mensagem("Não existe nenhuma equipe cadastrada.");
        }
    }

    // ------------------------------
    // Métodos relacionados ao TAREFA
    // ------------------------------
    // Método para realizar o cadastro de tarefa
    private static void cadastrarTarefa() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Campos de entrada
        JTextField nomeField = new JTextField(10);
        JTextField descricaoField = new JTextField(10);
        JFormattedTextField prazoField;
        try {
            MaskFormatter mascaraDataPrazo = new MaskFormatter("##/##/####");
            mascaraDataPrazo.setPlaceholderCharacter('_');
            prazoField = new JFormattedTextField(mascaraDataPrazo);
        }
        catch (ParseException e) {
            mensagem("Erro ao formatar o prazo. Tente novamente.");
            return;
        }

        // ComboBoxes para status, responsável e projeto
        String[] statusTarefas = new String[]{"", "Pendente", "Em andamento", "Concluída"};
        JComboBox<String> statusBox = new JComboBox<>(statusTarefas);

        String[] nomesUsuarios = new String[usuarios.size() + 1];
        nomesUsuarios[0] = "";
        for (int i = 0; i < usuarios.size(); i++) {
            nomesUsuarios[i + 1] = usuarios.get(i).getNome();
        }
        JComboBox<String> responsavelBox = new JComboBox<>(nomesUsuarios);

        String[] nomesProjetos = new String[projetos.size() + 1];
        nomesProjetos[0] = "";
        for (int i = 0; i < projetos.size(); i++) {
            nomesProjetos[i + 1] = projetos.get(i).getNome();
        }
        JComboBox<String> projetoBox = new JComboBox<>(nomesProjetos);

        // Adicionando componentes ao painel
        adicionarCampoPainel(panel, gbc, "Nome da tarefa:", nomeField);
        adicionarCampoPainel(panel, gbc, "Descrição:", descricaoField);
        adicionarCampoPainel(panel, gbc, "Prazo:", prazoField);
        adicionarCampoPainel(panel, gbc, "Status:", statusBox);
        adicionarCampoPainel(panel, gbc, "Responsável:", responsavelBox);
        adicionarCampoPainel(panel, gbc, "Projeto:", projetoBox);

        boolean cadastroConcluido = false;

        while (!cadastroConcluido) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Cadastro de Tarefa",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String nome = nomeField.getText().trim();
                String descricao = descricaoField.getText().trim();
                String prazo = prazoField.getText();
                String status = Objects.requireNonNull(statusBox.getSelectedItem()).toString();
                String responsavelNome = Objects.requireNonNull(responsavelBox.getSelectedItem()).toString();
                String projetoNome = Objects.requireNonNull(projetoBox.getSelectedItem()).toString();

                if (nome.isEmpty() || descricao.isEmpty() || prazo.equals("  /  /    ") || status.isEmpty() || responsavelNome.isEmpty() || projetoNome.isEmpty()) {
                    mensagem("Todos os campos são obrigatórios.");
                }
                else if (!validarData(prazo)) {
                    mensagem("O prazo informado é inválido.");
                }
                else {
                    Usuario responsavel = usuarios.stream().filter(u -> u.getNome().equals(responsavelNome)).findFirst().orElse(null);
                    Projeto projeto = projetos.stream().filter(p -> p.getNome().equals(projetoNome)).findFirst().orElse(null);

                    if (responsavel == null || projeto == null) {
                        mensagem("Responsável ou projeto não encontrados.");
                    }
                    else {
                        Tarefa novaTarefa = new Tarefa(nome, descricao, responsavel, prazo, status, projeto);
                        tarefas.add(novaTarefa);

                        PersistenciaDados.salvarTarefas(tarefas);

                        mensagem("Tarefa cadastrada com sucesso!");

                        cadastroConcluido = true;
                    }
                }
            }
            else {
                // O usuário clicou em Cancelar ou fechou a janela
                cadastroConcluido = true;
            }
        }
    }

    // Método para o gerenciamento das tarefas (edição ou exclusão)
    private static void gerenciarTarefas() {
        if (tarefas.isEmpty()) {
            mensagem("Não há tarefas cadastradas para gerenciar.");
            return;
        }

        int opcao = -1;
        do {
            try {
                String input = JOptionPane.showInputDialog(null,
                        """
                                -> Gerenciar Tarefas
                                      1. Editar uma tarefa
                                      2. Excluir uma tarefa
                                    ____________________________________
                                      0. Para voltar ao menu principal
                                    ____________________________________
                                    Escolha a opção desejada:""",
                        "Gerenciamento de Tarefas",
                        JOptionPane.PLAIN_MESSAGE);

                if (input == null) break; // Retorna se o usuário fechar a janela
                opcao = Integer.parseInt(input);

                // Lógica de seleção da tarefa
                String[] nomesTarefas = tarefas.stream().map(Tarefa::getNome).toArray(String[]::new);
                JComboBox<String> tarefaComboBox = new JComboBox<>(nomesTarefas);
                JPanel panel = new JPanel();
                panel.add(new JLabel("Selecione a tarefa:"));
                panel.add(tarefaComboBox);

                int confirmacao;
                String nomeTarefaSelecionada;
                Optional<Tarefa> tarefaSelecionada;

                switch (opcao) {
                    case 1:
                        confirmacao = JOptionPane.showConfirmDialog(null, panel, "Editar Tarefa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (confirmacao == JOptionPane.OK_OPTION) {
                            nomeTarefaSelecionada = Objects.requireNonNull(tarefaComboBox.getSelectedItem()).toString();
                            tarefaSelecionada = tarefas.stream()
                                    .filter(t -> t.getNome().equals(nomeTarefaSelecionada))
                                    .findFirst();
                            tarefaSelecionada.ifPresent(Main::editarTarefa);
                        }
                        break;
                    case 2:
                        confirmacao = JOptionPane.showConfirmDialog(null, panel, "Excluir Tarefa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                        if (confirmacao == JOptionPane.OK_OPTION) {
                            nomeTarefaSelecionada = Objects.requireNonNull(tarefaComboBox.getSelectedItem()).toString();
                            excluirTarefa(nomeTarefaSelecionada);
                        }
                        break;
                    case 0:
                        // Sai do loop
                        break;
                    default:
                        mensagem("Opção inválida. Por favor, escolha uma das opções acima.");
                }
            }
            catch (NumberFormatException e) {
                mensagem("Erro: Digite apenas números para a opção.");
            }
            catch (Exception e) {
                mensagem("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    // Método para editar a tarefa selecionada
    private static void editarTarefa(Tarefa tarefa) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0);

        // Campos de edição com dados atuais
        JTextField nomeField = new JTextField(tarefa.getNome(), 10);
        JTextField descricaoField = new JTextField(tarefa.getDescricao(), 10);
        JFormattedTextField prazoField;
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            mascaraData.setPlaceholderCharacter('_');
            prazoField = new JFormattedTextField(mascaraData);
            prazoField.setText(tarefa.getPrazo());
        }
        catch (ParseException e) {
            mensagem("Erro ao formatar a data.");
            return;
        }

        // ComboBoxes para status, responsável e projeto
        String[] statusTarefas = new String[]{"Pendente", "Em andamento", "Concluída"};
        JComboBox<String> statusBox = new JComboBox<>(statusTarefas);
        statusBox.setSelectedItem(tarefa.getStatus());

        String[] nomesUsuarios = usuarios.stream().map(Usuario::getNome).toArray(String[]::new);
        JComboBox<String> responsavelBox = new JComboBox<>(nomesUsuarios);
        responsavelBox.setSelectedItem(tarefa.getResponsavel());

        String[] nomesProjetos = projetos.stream().map(Projeto::getNome).toArray(String[]::new);
        JComboBox<String> projetoBox = new JComboBox<>(nomesProjetos);
        projetoBox.setSelectedItem(tarefa.getProjetos());

        // Adicionando componentes ao painel
        adicionarCampoPainel(panel, gbc, "Nome:", nomeField);
        adicionarCampoPainel(panel, gbc, "Descrição:", descricaoField);
        adicionarCampoPainel(panel, gbc, "Prazo:", prazoField);
        adicionarCampoPainel(panel, gbc, "Status:", statusBox);
        adicionarCampoPainel(panel, gbc, "Responsável:", responsavelBox);
        adicionarCampoPainel(panel, gbc, "Projeto:", projetoBox);

        boolean edicaoConcluida = false;

        while (!edicaoConcluida) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Editar Tarefa",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String novoNome = nomeField.getText();
                String novaDescricao = descricaoField.getText();
                String novoPrazo = prazoField.getText();
                String novoStatus = Objects.requireNonNull(statusBox.getSelectedItem()).toString();
                String novoResponsavelNome = Objects.requireNonNull(responsavelBox.getSelectedItem()).toString();
                String novoProjetoNome = Objects.requireNonNull(projetoBox.getSelectedItem()).toString();

                if (novoNome.trim().isEmpty() || novaDescricao.trim().isEmpty() || novoPrazo.equals("__/__/____") || novoStatus.isEmpty() || novoResponsavelNome.isEmpty() || novoProjetoNome.isEmpty()) {
                    mensagem("Todos os campos são obrigatórios.");
                }
                else if (validarData(novoPrazo)) {
                    tarefa.setNome(novoNome);
                    tarefa.setDescricao(novaDescricao);
                    tarefa.setPrazo(novoPrazo);
                    tarefa.setStatus(novoStatus);
                    tarefa.setResponsavel(usuarios.stream().filter(u -> u.getNome().equals(novoResponsavelNome)).findFirst().orElse(null));
                    tarefa.setProjeto(projetos.stream().filter(p -> p.getNome().equals(novoProjetoNome)).findFirst().orElse(null));

                    PersistenciaDados.salvarTarefas(tarefas);

                    mensagem("Tarefa editada com sucesso!");

                    edicaoConcluida = true;
                } else {
                    mensagem("O prazo informado é inválido.");
                }
            }
            else {
                // Usuário clicou em Cancelar ou fechou a janela
                edicaoConcluida = true;
            }
        }
    }

    // Método para excluir a tarefa selecionada
    private static void excluirTarefa(String nomeTarefa) {
        int confirmacao = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir a tarefa '" + nomeTarefa + "'?",
                "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            tarefas.removeIf(t -> t.getNome().equals(nomeTarefa));

            PersistenciaDados.salvarTarefas(tarefas);

            mensagem("Tarefa '" + nomeTarefa + "' excluída com sucesso.");
        }
    }

    // Método para para consultar tarefas
    private static void consultarTarefas() {
        if (!tarefas.isEmpty()) {
            // Converte a lista para uma String, com quebras de linha
            String relatorioTarefas = tarefas.toString().replace("[", "").replace("]", "").replace(", ", "\n\n");

            // Cria um JTextArea com o que precisa ser exibido e atribui um JScrollPane
            JScrollPane scrollPane = new JScrollPane(criarTextArea(relatorioTarefas));

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
            mensagem("Não existe nenhuma tarefa cadastrada.");
        }
    }

    // ------------------
    // Métodos AUXILIARES
    // ------------------
    // Método genérico para exibição de mensagens
    private static void mensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    // Método para gerenciar os dados e/ou os arquivos
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

                if (input == null) break;
                opcao = Integer.parseInt(input);

                int confirmacao;

                switch (opcao) {
                    case 1:
                        // Apagar todos os dados
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
                            mensagem("Todos os dados foram apagados com sucesso.");
                        }
                        break;
                    case 2:
                        // Apagar dados dos usuários
                        boolean usuarioTemAssociacao = usuarios.stream().anyMatch(usuario ->
                                tarefas.stream().anyMatch(t -> t.getResponsavel().equals(usuario.getNome())) ||
                                        projetos.stream().anyMatch(p -> p.getGerenteResponsavel().equals(usuario.getNome())) ||
                                        equipes.stream().anyMatch(e -> e.getMembros().stream().anyMatch(m -> m.getNome().equals(usuario.getNome())))
                        );
                        if (usuarioTemAssociacao) {
                            mensagem("Não é possível apagar os dados dos usuários, pois existem associações com tarefas, projetos ou equipes.");
                        }
                        else {
                            confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar os dados do Usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                            if (confirmacao == JOptionPane.YES_OPTION) {
                                PersistenciaDados.limparDados(PersistenciaDados.ARQUIVO_USUARIOS);
                                usuarios.clear();
                                mensagem("Dados dos usuários apagados com sucesso.");
                            }
                        }
                        break;
                    case 3:
                        // Apagar dados dos projetos
                        boolean projetoTemAssociacao = projetos.stream().anyMatch(projeto ->
                                tarefas.stream().anyMatch(t -> t.getProjetos().equals(projeto.getNome())) ||
                                        equipes.stream().anyMatch(e -> e.getProjetos().stream().anyMatch(ep -> ep.getNome().equals(projeto.getNome())))
                        );
                        if (projetoTemAssociacao) {
                            mensagem("Não é possível apagar os dados dos projetos, pois existem associações com tarefas ou equipes.");
                        }
                        else {
                            confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar os dados do Projeto?", "Confirmação", JOptionPane.YES_NO_OPTION);
                            if (confirmacao == JOptionPane.YES_OPTION) {
                                PersistenciaDados.limparDados(PersistenciaDados.ARQUIVO_PROJETOS);
                                projetos.clear();
                                mensagem("Dados dos projetos apagados com sucesso.");
                            }
                        }
                        break;
                    case 4:
                        // Apagar dados dos equipes
                        boolean equipeTemAssociacao = equipes.stream().anyMatch(equipe ->
                                tarefas.stream().anyMatch(t -> t.getProjetos().equals(equipe.getProjetos().stream().findFirst().map(Projeto::getNome).orElse("")))
                        );
                        if (equipeTemAssociacao) {
                            mensagem("Não é possível apagar os dados das equipes, pois existem associações com tarefas.");
                        }
                        else {
                            confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar os dados da Equipe?", "Confirmação", JOptionPane.YES_NO_OPTION);
                            if (confirmacao == JOptionPane.YES_OPTION) {
                                PersistenciaDados.limparDados(PersistenciaDados.ARQUIVO_EQUIPES);
                                equipes.clear();
                                mensagem("Dados das equipes apagados com sucesso.");
                            }
                        }
                        break;
                    case 5:
                        // Apagar dados dos tarefas
                        confirmacao = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar os dados da Tarefa?", "Confirmação", JOptionPane.YES_NO_OPTION);
                        if (confirmacao == JOptionPane.YES_OPTION) {
                            PersistenciaDados.limparDados(PersistenciaDados.ARQUIVO_TAREFAS);
                            tarefas.clear();
                            mensagem("Dados das tarefas apagados com sucesso.");
                        }
                        break;
                    case 0:
                        // Volta para o menu principal
                        break;
                    default:
                        mensagem("Opção inválida. Por favor, escolha uma das opções acima.");
                }
            }
            catch (NumberFormatException e) {
                mensagem("Erro: Digite apenas números para a opção.");
            }
            catch (Exception e) {
                mensagem("Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    // Método para adicionar campo ao Jpanel
    private static void adicionarCampoPainel(JPanel panel, GridBagConstraints gbc, String label, Component campo) {
        // Adiciona o JLabel na coluna 0
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);

        // Adiciona o campo de entrada na coluna 1
        gbc.gridx = 1;
        panel.add(campo, gbc);

        // Incrementa a linha para o próximo campo
        gbc.gridy++;
    }

    // Método para criar um JTextArea
    private static JTextArea criarTextArea(String texto) {
        JTextArea textArea = new JTextArea(texto);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        return textArea;
    }

    // Método para extrair/retornar um array de String com somente os perfis de usuários iguais a "Gerente"
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
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean validarCpf(String cpf) {
        int NUMERO_DIGITOS_CPF = 11;

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

    // Método para validar se o e-mail está no formato válido
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Expressão regular para validar o formato de um e-mail
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    // Método para validar se uma data está no formato válido
    public static boolean validarData(String data) {
        DateTimeFormatter FORMATADOR_DATA = new DateTimeFormatterBuilder()
                .appendPattern("dd/MM/yyyy")
                .parseDefaulting(ChronoField.ERA, 1) // Define a era padrão para evitar problemas de análise
                .toFormatter(Locale.of("pt", "BR")) // Usa a localidade do Brasil para garantir o formato dd/MM/yyyy
                .withResolverStyle(ResolverStyle.STRICT);

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

    // Método para obter o nome dos usuário disponíveis
    private static String[] obterUsuariosDisponiveis(Equipe equipe) {
        // Pega os nomes dos usuários que já estão na equipe
        List<String> nomesUsuariosAtuais = equipe.getMembros().stream()
                .map(Usuario::getNome)
                .toList();

        // Filtra a lista global de usuários para encontrar os que NÃO estão na equipe
        List<String> nomesDisponiveis = usuarios.stream()
                .map(Usuario::getNome)
                .filter(nome -> !nomesUsuariosAtuais.contains(nome))
                .collect(Collectors.toList());

        // Adiciona um campo vazio para a seleção opcional no início
        nomesDisponiveis.addFirst("");

        // Converte a lista para um array e retorna
        return nomesDisponiveis.toArray(String[]::new);
    }

    // Método para obter o nome dos projetos disponíveis
    private static String[] obterProjetosDisponiveis(Equipe equipe) {
        // Pega os nomes dos projetos que já estão na equipe
        List<String> nomesProjetosAtuais = equipe.getProjetos().stream()
                .map(Projeto::getNome)
                .toList();

        // Filtra a lista global de projetos para encontrar os que NÃO estão na equipe
        List<String> nomesDisponiveis = projetos.stream()
                .map(Projeto::getNome)
                .filter(nome -> !nomesProjetosAtuais.contains(nome))
                .collect(Collectors.toList());

        // Adiciona um campo vazio para a seleção opcional no início
        nomesDisponiveis.addFirst("");

        // Converte a lista para um array e retorna
        return nomesDisponiveis.toArray(String[]::new);
    }
}

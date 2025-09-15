// Importa as classes necessárias
import java.awt.*;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class SistemaGestaoProjetos {
    // Atributos principais da clase SistemaGestaoProjetos
    // Listas globais (estáticas) que armazenam os cadastros efetuados durante a execução do sistema
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Projeto> projetos = new ArrayList<>();
    private static List<Equipe> equipes = new ArrayList<>();

    public static void main(String[] args) {
        // Variável que guarda a opção escolhida pelo usuário no menu
        int opcao = -1;

        do {
            try {
                // Exibição do menu de opções
                String input = JOptionPane.showInputDialog(null,
                        "-> Cadastros\n" +
                                "      1. Usuário\n" +
                                "      2. Projeto\n" +
                                "      3. Equipe\n" +
                                "____________________________________\n" +
                                "-> Consultas\n" +
                                "      4. Usuários\n" +
                                "      5. Projetos\n" +
                                "      6. Equipes\n" +
                                "____________________________________\n" +
                                "-> Finalizar\n" +
                                "      0. para sair\n" +
                                "____________________________________\n" +
                                "Escolha a opção desejada:",
                        "Sistema de Gestão de Projetos",
                        JOptionPane.DEFAULT_OPTION);
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
                        // Consulta de usuário
                        if (usuarios.size() > 0) {
                            JOptionPane.showMessageDialog(null, usuarios.toString());
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Não existe nenhum usuário cadastrado.");
                        }
                        break;
                    case 5:
                        // Consulta de projeto
                        if (projetos.size() > 0) {
                            JOptionPane.showMessageDialog(null, projetos.toString());
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Não existe nenhum projeto cadastrado.");
                        }
                        break;
                    case 6:
                        // Consulta de equipe
                        if (equipes.size() > 0) {
                            JOptionPane.showMessageDialog(null, equipes.toString());
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Não existe nenhuma equipe cadastrada.");
                        }
                        break;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erro: Digite apenas números para a opção.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    //Cadastro de usuário
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
        MaskFormatter mascaraCpf = null;
        mascaraCpf = new MaskFormatter("#########-##");
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
                String perfil = listaPerfis.getSelectedItem().toString();

                // Verificando se os campos foram preenchidos
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O nome é obrigatório");
                }
                else if (cpf.isEmpty() || cpf.equals("_________-__")) {
                    JOptionPane.showMessageDialog(null, "O CPF é obrigatório");
                }
                else if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O e-mail é obrigatório");
                }
                else if (cargo.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O cargo é obrigatório");
                }
                else if (login.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O login é obrigatório");
                }
                else if (senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A senha é obrigatório");
                }
                else if (perfil.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O perfil é obrigatório");
                }
                else {
                    // Inserindo o usuário
                    usuarios.add(new Usuario(nome, cpf, email, cargo, login, senha, perfil));

                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

                    mostrarPainel = false;
                }
            }
            else  {
                mostrarPainel = false;
            }
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
        MaskFormatter mascaraDataInicio = null;
        mascaraDataInicio = new MaskFormatter("##/##/####");
        mascaraDataInicio.setPlaceholderCharacter('_');
        JFormattedTextField jFormattedTextDataInicio = new JFormattedTextField(mascaraDataInicio);
        panel.add(jFormattedTextDataInicio, gbc);

        gbc.gridy = 3;
        MaskFormatter mascaraDataTermino = null;
        mascaraDataTermino = new MaskFormatter("##/##/####");
        mascaraDataTermino.setPlaceholderCharacter('_');
        JFormattedTextField jFormattedTextDataTermino = new JFormattedTextField(mascaraDataTermino);
        panel.add(jFormattedTextDataTermino, gbc);

        gbc.gridy = 4;
        String[] status = new String[] {"", "Planejado", "Em andamento", "Concluído", "Cancelado"};
        JComboBox<String> listaStatus = new JComboBox<>(status);
        panel.add(listaStatus, gbc);

        gbc.gridy = 5;
        String[] gerentesCadastrados = new String[usuarios.size()+1];
        gerentesCadastrados[0] = "";
        for (int i = 0; i < usuarios.size(); i++) {
            //if (usuarios.get(i).getPerfil().equals("Gerente")) {
                //gerentesCadastrados[i] = usuarios.get(i).getNomeCompleto();
            //}
            gerentesCadastrados[i+1] = usuarios.get(i).getNomeCompleto();
        }

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
                String statusSelecionado = listaStatus.getSelectedItem().toString();
                String nomeGerente = listaGerentes.getSelectedItem().toString();

                // Verificando se os campos foram preenchidos
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O nome do projeto é obrigatório");
                }
                else if (descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A descricao é obrigatório");
                }
                else if (dataInicio.isEmpty() || dataInicio.equals("__/__/____")) {
                    JOptionPane.showMessageDialog(null, "A data de início é obrigatório");
                }
                else if (dataTermino.isEmpty() || dataTermino.equals("__/__/____")) {
                    JOptionPane.showMessageDialog(null, "A data de témino prevista é obrigatório");
                }
                else if (statusSelecionado.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O status é obrigatório");
                }
                else if (nomeGerente.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O nome do gerente responsável é obrigatório");
                }
                else {
                    Usuario gerente = usuarios.stream().filter(u -> u.getNomeCompleto().equalsIgnoreCase(nomeGerente)).findFirst().orElse(null);

                    if (gerente != null && gerente.getPerfil().equalsIgnoreCase("gerente")) {
                        // Inserindo o projeto
                        projetos.add(new Projeto(nome, descricao, dataInicio, dataTermino, statusSelecionado, gerente));

                        JOptionPane.showMessageDialog(null, "Projeto cadastrado com sucesso!");

                        mostrarPainel = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Gerente inválido! Cadastre ou selecione um usuário com perfil de GERENTE.");
                    }
                }
            }
            else  {
                mostrarPainel = false;
            }
        }
    }

    // Cadastro de equipe
    private static void CadastrarEquipe(){
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
            usuariosCadastrados[i+1] = usuarios.get(i).getNomeCompleto();
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
                String membroEquipe = listaUsuarios.getSelectedItem().toString();
                String projeto = listaProjetos.getSelectedItem().toString();

                ArrayList<Projeto> projetoSelecionado = new ArrayList<>();
                for (int i = 0; i < projetos.size(); i++) {
                    if (projetos.get(i).getNome().equals(projeto)) {
                        projetoSelecionado.add(projetos.get(i));
                    }
                }

                // Verificando se os campos foram preenchidos
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O nome da equipe é obrigatório");
                }
                else if (descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "A descricao é obrigatório");
                }
                else if (membroEquipe.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O membro é obrigatório");
                }
                else if (projeto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "O projeto é obrigatório");
                }
                else {
                    Usuario membro = usuarios.stream().filter(u -> u.getNomeCompleto().equalsIgnoreCase(membroEquipe)).findFirst().orElse(null);

                    if (membro != null) {
                        Equipe verificaEquipe = equipes.stream().filter(u -> u.getNome().equalsIgnoreCase(nome)).findFirst().orElse(null);

                        if (verificaEquipe != null) {
                            // Inserindo o membro na mesma equipe
                            equipes.add(new Equipe(verificaEquipe.getNome(), verificaEquipe.getDescricao(), membro, projetoSelecionado));
                        } else {
                            // Inserindo a equipe
                            equipes.add(new Equipe(nome, descricao, membro, projetoSelecionado));
                        }

                        JOptionPane.showMessageDialog(null, "Equipe cadastrada com sucesso!");

                        mostrarPainel = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Membro da equipe não encontrado! Cadastre este membro como um usuário primeiro.");
                    }
                }
            }
            else  {
                mostrarPainel = false;
            }
        }
    }
}

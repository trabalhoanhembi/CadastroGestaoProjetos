//importações necessárias para rodar o código
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Listas globais (estáticas) que armazenam os cadastros feitos durante a execução
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Projeto> projetos = new ArrayList<>();
    private static List<Equipe> equipes = new ArrayList<>();

    public static void main(String[] args) {
        // Scanner criado (apesar de não usar entrada pelo console, deixei para fechar no final)
        Scanner sc = new Scanner(System.in);

        int opcao = -1; // variável que guarda a opção escolhida pelo usuário no menu

        // Loop do-while mantém o menu rodando até o usuário escolher "0 - Sair"
        do {
            try {
                // Exibe o menu principal usando JOptionPane
                String input = JOptionPane.showInputDialog(
                        "=== Sistema de Gestão de Projetos ===\n"
                                + "1 - Cadastrar Usuário\n"
                                + "2 - Cadastrar Projeto\n"
                                + "3 - Cadastrar Equipe\n"
                                + "4 - Listar Usuários\n"
                                + "5 - Listar Projetos\n"
                                + "6 - Listar Equipes\n"
                                + "0 - Sair\n"
                                + "Escolha uma opção:");

                // Se o usuário fechar a janela ou apertar "Cancelar", input será null → sai do loop
                if (input == null) break;

                // Converte a string digitada em número inteiro
                opcao = Integer.parseInt(input);

                // Switch para decidir qual ação executar
                switch (opcao) {
                    case 1:
                        // Cadastro de Usuário
                        String nome = JOptionPane.showInputDialog("Nome completo:");
                        String cpf = JOptionPane.showInputDialog("CPF:");
                        String email = JOptionPane.showInputDialog("Email:");
                        String cargo = JOptionPane.showInputDialog("Cargo:");
                        String login = JOptionPane.showInputDialog("Login:");
                        String senha = JOptionPane.showInputDialog("Senha:");
                        String perfil = JOptionPane.showInputDialog("Perfil (administrador, gerente ou colaborador):");

                        // Cria o objeto Usuario com os dados digitados e adiciona à lista
                        usuarios.add(new Usuario(nome, cpf, email, cargo, login, senha, perfil));

                        // Mensagem de confirmação
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        break;

                    case 2:
                        // Cadastro de Projeto
                        String nomeProjeto = JOptionPane.showInputDialog("Nome do projeto:");
                        String descricao = JOptionPane.showInputDialog("Descrição:");
                        String dataInicio = JOptionPane.showInputDialog("Data de início:");
                        String dataTermino = JOptionPane.showInputDialog("Data de término prevista:");
                        String status = JOptionPane.showInputDialog("Status (planejado, em andamento, concluído, cancelado):");

                        // Pergunta quem será o gerente responsável
                        String gerenteNome = JOptionPane.showInputDialog("Nome completo do gerente responsável:");

                        // Busca o usuário correspondente na lista de usuários
                        Usuario gerente = usuarios.stream()
                                .filter(u -> u.getNomeCompleto().equalsIgnoreCase(gerenteNome))
                                .findFirst()
                                .orElse(null);

                        // Verifica se encontrou o usuário e se ele tem perfil "gerente"
                        if (gerente != null && gerente.getPerfil().equalsIgnoreCase("gerente")) {
                            projetos.add(new Projeto(nomeProjeto, descricao, dataInicio, dataTermino, status, gerente));
                            JOptionPane.showMessageDialog(null, "Projeto cadastrado com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Gerente inválido ou não encontrado!");
                        }
                        break;

                    case 3:
                        // Cadastro de Equipe
                        String nomeEquipe = JOptionPane.showInputDialog("Nome da equipe:");
                        String descEquipe = JOptionPane.showInputDialog("Descrição da equipe:");

                        // Cria e adiciona a nova equipe à lista
                        equipes.add(new Equipe(nomeEquipe, descEquipe));
                        JOptionPane.showMessageDialog(null, "Equipe cadastrada com sucesso!");
                        break;

                    case 4:
                        // Listar Usuários cadastrados
                        JOptionPane.showMessageDialog(null, usuarios.toString());
                        break;

                    case 5:
                        // Listar Projetos cadastrados
                        JOptionPane.showMessageDialog(null, projetos.toString());
                        break;

                    case 6:
                        // Listar Equipes cadastradas
                        JOptionPane.showMessageDialog(null, equipes.toString());
                        break;

                    case 0:
                        // Sair → não faz nada, apenas permite o loop terminar
                        break;

                    default:
                        // Se digitou número inválido
                        JOptionPane.showMessageDialog(null, "Opção inválida! Escolha entre 0 e 6.");
                        break;
                }
            } catch (NumberFormatException e) {
                // Caso o usuário digite letras ou texto em vez de número no menu
                JOptionPane.showMessageDialog(null, "Erro: Digite apenas números para a opção.");
            } catch (Exception e) {
                // Tratamento genérico para qualquer outro erro inesperado
                JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage());
            }
        } while (opcao != 0); // Continua até o usuário escolher "0"

        // Fecha o Scanner (boa prática, apesar de não usarmos ele neste caso)
        sc.close();
    }
}

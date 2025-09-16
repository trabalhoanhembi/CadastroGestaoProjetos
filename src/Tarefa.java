// Importa as classes necessárias
import java.util.ArrayList;
import java.util.List;

class Tarefa {
    // Atributos principais da clase Equipe
    private final String nome;                  // Nome da tarefa
    private final String descricao;             // Descrição da tarefa
    private final String prazo;                 // Prazo da tarefa
    private final String status;                // Status do projeto (aceitando os valores pendente, em andamento, concluída)
    private final List<Usuario> responsavel;    // Lista de membros da equipe (objetos do tipo Usuario)
    private final List<Projeto> projetos;       // Lista de projetos da equipe (objetos do tipo Projeto)

    // Construtor da classe
    public Tarefa(String nome, String descricao, Usuario responsavel, String prazo, String status, ArrayList<Projeto> projeto) {
        this.nome = nome;                       // Inicializa com o nome da tarefa
        this.descricao = descricao;             // Inicializa com a descrição da tarefa
        this.prazo = prazo;                     // Inicializa o prazo da tarefa
        this.status = status;                   // Inicializa com o status da tarefa
        this.responsavel = new ArrayList<>();   // Cria uma lista vazia de responsavel/usuário
        adicionarMembro(responsavel);           // Inicializa/adiciona o responsavel/usuário
        this.projetos = new ArrayList<>();      // Cria uma lista vazia de projetos
        alocarProjeto(projeto);                 // Inicializa/adiciona/aloca o projeto
    }

    // Método para adicionar um membro/usuário a equipe
    public void adicionarMembro(Usuario usuario) {
        assert responsavel != null;
        responsavel.add(usuario);
    }

    // Método para adicionar/alocar umprojeto a equipe
    public void alocarProjeto(ArrayList<Projeto> projeto) {
        assert projetos != null;
        projetos.add(projeto.getFirst());
    }

    //Getters, permitem acessar os atributos da tarefa
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getPrazo() { return prazo; }
    public String getStatus() { return status; }
    public String getResponsavel() { return responsavel.toString(); }
    public String getProjetos() { return projetos.toString(); }

    // Sobrescreve o método toString()
    // É chamado automaticamente quando o objeto Tarefa é impresso
    @Override
    public String toString() {
        return "\n"
                + "Tarefa: " + getNome() + "\n"
                + "Descrição: " + getDescricao() + "\n"
                + "Prazo: " + getPrazo() + "\n"
                + "Status: " + getStatus() + "\n"
                + "Responsável: " + getResponsavel() + "\n"
                + "Projetos: " + getProjetos() + "\n";
    }
}

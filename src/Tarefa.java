// Importa as classes necessárias
import java.io.Serializable;

public class Tarefa implements Serializable {
    // Atributos principais da classe Equipe
    private String nome;            // Nome da tarefa
    private String descricao;       // Descrição da tarefa
    private String prazo;           // Prazo da tarefa
    private String status;          // Status do projeto (aceitando os valores pendente, em andamento, concluída)
    private Usuario responsavel;    // Responsável pela tarefa (objeto do tipo Usuario)
    private Projeto projeto;        // Projeto associado a tarefa (objeto do tipo Projeto)

    // Construtor Padrão (Necessário para serialização)
    public Tarefa() { }

    // Construtor principal para inicializar uma tarefa
    public Tarefa(String nome, String descricao, Usuario responsavel, String prazo, String status, Projeto projeto) {
        this.nome = nome;               // Inicializa com o nome da tarefa
        this.descricao = descricao;     // Inicializa com a descrição da tarefa
        this.prazo = prazo;             // Inicializa o prazo da tarefa
        this.status = status;           // Inicializa com o status da tarefa
        this.responsavel = responsavel; // Cria uma lista vazia de responsavel/usuário
        this.projeto = projeto;         // Cria uma lista vazia de projetos
    }

    // Métodos setters
    public void setNome(String nome) { this.nome = nome; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public void setPrazo(String prazo) { this.prazo = prazo; }

    public void setStatus(String status) { this.status = status; }

    public void setResponsavel(Usuario responsavel) { this.responsavel = responsavel; }

    public void setProjeto(Projeto projeto) { this.projeto = projeto; }

    // Métodos getters
    public String getNome() { return nome; }

    public String getDescricao() { return descricao; }

    public String getPrazo() { return prazo; }

    public String getStatus() { return status; }

    public String getResponsavel() { return responsavel.getNome(); }

    public String getProjetos() { return projeto.getNome(); }

    // Sobrescreve o método toString() para mostrar os dados da tarefa
    @Override
    public String toString() {
        return "Tarefa: " + getNome() + "\n"
                + "Descrição: " + getDescricao() + "\n"
                + "Prazo: " + getPrazo() + "\n"
                + "Status: " + getStatus() + "\n"
                + "Responsável: " + getResponsavel() + "\n"
                + "Projeto: " + getProjetos();
    }
}

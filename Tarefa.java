// Importa a classe Date para trabalhar com datas de início e término da tarefa
import java.util.Date;

public class Tarefa {
    // Atributos privados da classe Tarefa, só podem ser acessados por métodos getters e setters
    private String nome;          // Nome da tarefa
    private String descricao;     // Descrição da tarefa
    private Date inicio;          // Data de início da tarefa
    private Date termino;         // Data de término da tarefa
    private String status;        // Status da tarefa (ex: "pendente", "em andamento", "concluída")

    // Construtor da classe Tarefa, usado para criar objetos já preenchidos
    public Tarefa(String nome, String descricao, Date inicio, Date termino, String status) {
        this.nome = nome;             // Inicializa o atributo nome
        this.descricao = descricao;   // Inicializa o atributo descrição
        this.inicio = inicio;         // Inicializa a data de início
        this.termino = termino;       // Inicializa a data de término
        this.status = status;         // Inicializa o status da tarefa
    }

    // Métodos getters e setters
    // Eles permitem acessar e modificar os atributos de forma controlada

    public String getNome() {
        return nome; // Retorna o nome da tarefa
    }

    public void setNome(String nome) {
        this.nome = nome; // Atualiza o nome da tarefa
    }

    public String getDescricao() {
        return descricao; // Retorna a descrição da tarefa
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao; // Atualiza a descrição da tarefa
    }

    public Date getInicio() {
        return inicio; // Retorna a data de início
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio; // Atualiza a data de início
    }

    public Date getTermino() {
        return termino; // Retorna a data de término
    }

    public void setTermino(Date termino) {
        this.termino = termino; // Atualiza a data de término
    }

    public String getStatus() {
        return status; // Retorna o status da tarefa
    }

    public void setStatus(String status) {
        this.status = status; // Atualiza o status da tarefa
    }

    // Sobrescreve o método toString()
    // Esse método é chamado automaticamente quando você tenta imprimir o objeto
    @Override
    public String toString() {
        return "Tarefa: " + nome +
                ", Descrição: " + descricao +
                ", Início: " + inicio +
                ", Término: " + termino +
                ", Status: " + status;
    }
}

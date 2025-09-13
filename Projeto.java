// Importações necessárias
import java.util.ArrayList;
import java.util.List;

public class Projeto {

    // Atributos principais do projeto
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataTerminoPrevista;
    private String status; // pode ser "planejado", "em andamento", "concluído" ou "cancelado"

    // Associação: cada projeto tem um gerente responsável (que é um Usuário)
    private Usuario gerenteResponsavel;

    // Associação: cada projeto pode ter várias tarefas
    private List<Tarefa> tarefas;

    // Construtor da classe Projeto
    // Inicializa o projeto com os dados básicos e cria a lista de tarefas vazia
    public Projeto(String nome, String descricao, String dataInicio,
                   String dataTerminoPrevista, String status, Usuario gerenteResponsavel) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = status;
        this.gerenteResponsavel = gerenteResponsavel;
        this.tarefas = new ArrayList<>(); // lista começa vazia
    }

    // Getters (leitura dos atributos)
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataTerminoPrevista() {
        return dataTerminoPrevista;
    }

    public String getStatus() {
        return status;
    }

    public Usuario getGerenteResponsavel() {
        return gerenteResponsavel;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    // Método utilitário para adicionar uma tarefa ao projeto
    public void adicionarTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
    }

    // Método toString sobrescrito
    // Representa o projeto em formato de texto amigável
    @Override
    public String toString() {
        return "Projeto {" +
                "Nome='" + nome + '\'' +
                ", Descrição='" + descricao + '\'' +
                ", Início='" + dataInicio + '\'' +
                ", Término Previsto='" + dataTerminoPrevista + '\'' +
                ", Status='" + status + '\'' +
                ", Gerente='" + gerenteResponsavel.getNomeCompleto() + '\'' +
                '}';
    }
}

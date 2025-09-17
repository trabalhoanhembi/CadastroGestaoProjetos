// Importa as classes necessárias
import java.io.Serializable;

class Projeto implements Serializable {
    // Atributos principais da clase Projeto
    private String nome;                // Nome do projeto
    private String descricao;           // Descrição do projeto
    private String dataInicio;          // Data de início do projeto
    private String dataTerminoPrevista; // Data de término prevista do projeto
    private String status;              // Status do projeto (aceitando os valores planejado, em andamento, concluído, cancelado)
    private Usuario gerenteResponsavel; // Nome do gerente responsável (objeto do tipo Usuario)

    // Construtor Padrão (Necessário para serialização)
    public Projeto() { }

    // Construtor principal para inicializar um projeto
    public Projeto(String nome, String descricao, String dataInicio, String dataTerminoPrevista, String status, Usuario gerenteResponsavel) {
        this.nome = nome;                               // Inicializa com o nome do projeto
        this.descricao = descricao;                     // Inicializa com a descrição do projeto
        this.dataInicio = dataInicio;                   // Inicializa com a data de início do projeto
        this.dataTerminoPrevista = dataTerminoPrevista; // Inicializa com a date de término prevista do projeto
        this.status = status;                           // Inicializa com o status do projeto
        this.gerenteResponsavel = gerenteResponsavel;   // Inicializa com o gerente responsável pelo projeto
    }

    // Métodos getters para acessar os dados
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDataInicio() { return dataInicio; }
    public String getDataTerminoPrevista() { return dataTerminoPrevista; }
    public String getStatus() { return status; }
    public String getGerenteResponsavel() { return gerenteResponsavel.getNome(); }

    // Sobrescreve o método toString() para mostrar os dados do projeto
    @Override
    public String toString() {
        return "Nome: " + getNome() + "\n"
                + "Descrição: " + getDescricao() + "\n"
                + "Data de início: " + getDataInicio() + "\n"
                + "Data de término prevista: " + getDataTerminoPrevista() + "\n"
                + "Status: " + getStatus() + "\n"
                + "Gerente responsável: " + getGerenteResponsavel();
    }
}

class Projeto {
    // Atributos principais da clase Projeto
    private final String nome;                // Nome do projeto
    private final String descricao;           // Descrição do projeto
    private final String dataInicio;          // Data de início do projeto
    private final String dataTerminoPrevista; // Data de término prevista do projeto
    private final String status;              // Status do projeto (aceitando os valores planejado, em andamento, concluído, cancelado)
    private final Usuario gerenteResponsavel; // Nome do gerente responsável (objetos do tipo Usuario)

    // Construtor da classe
    public Projeto(String nome, String descricao, String dataInicio, String dataTerminoPrevista, String status, Usuario gerenteResponsavel) {
        this.nome = nome;                               // Inicializa com o nome do projeto
        this.descricao = descricao;                     // Inicializa com a descrição do projeto
        this.dataInicio = dataInicio;                   // Inicializa com a data de início do projeto
        this.dataTerminoPrevista = dataTerminoPrevista; // Inicializa com a date de término prevista do projeto
        this.status = status;                           // Inicializa com o status do projeto
        this.gerenteResponsavel = gerenteResponsavel;   // Inicializa com o gerente responsável pelo projeto
    }

    //Getters, permitem acessar os atributos do projeto
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getDataInicio() { return dataInicio; }
    public String getDataTerminoPrevista() { return dataTerminoPrevista; }
    public String getStatus() { return status; }
    public String getGerenteResponsavel() { return gerenteResponsavel.getNomeCompleto(); }

    // Sobrescreve o método toString()
    // É chamado automaticamente quando o objeto Projeto é impresso
    @Override
    public String toString() {
        return "\n"
                + "Nome do projeto: " + getNome() + "\n"
                + "Descrição: " + getDescricao() + "\n"
                + "Data de início: " + getDataInicio() + "\n"
                + "Data de término prevista: " + getDataTerminoPrevista() + "\n"
                + "Status: " + getStatus() + "\n"
                + "Gerente responsável (nome completo): " + getGerenteResponsavel() + "\n";
    }
}

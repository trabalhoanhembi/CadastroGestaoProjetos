class Usuario {
    // Atributos principais da clase Usuario
    private final String nomeCompleto;    // Nome completo do usuário
    private final String cpf;             // Número do CPF do usuário
    private final String email;           // E-mail do usuário
    private final String cargo;           // Cargo do usuário
    private final String login;           // Login do usuário
    private final String senha;           // Senha do usuário
    private final String perfil;          // Perfil do projeto (aceitando os valores administrador, gerente ou colaborador)

    // Construtor da classe
    public Usuario(String nomeCompleto, String cpf, String email, String cargo, String login, String senha, String perfil) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    //Getters, permitem acessar os atributos do usuario
    public String getNomeCompleto() { return nomeCompleto; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getCargo() { return cargo; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getPerfil() { return perfil; }

    // Sobrescreve o método toString()
    // É chamado automaticamente quando o objeto Usuario é impresso
    @Override
    public String toString() {
        return "\n"
                + "Nome completo: " + getNomeCompleto() + "\n"
                + "CPF: " + getCpf() + "\n"
                + "E-mail: " + getEmail() + "\n"
                + "Cargo: " + getCargo() + "\n"
                + "Login: " + getLogin() + "\n"
                + "Senha: " + getSenha() + "\n"
                + "Perfil: " + getPerfil() + "\n";
    }
}

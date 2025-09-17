// Importa as classes necessárias
import java.io.Serializable;

class Usuario extends Pessoa implements Serializable {
    // Atributos principais da clase Usuario
    private String cargo;   // Cargo do usuário
    private String login;   // Login do usuário
    private String senha;   // Senha do usuário
    private String perfil;  // Perfil do projeto (aceitando os valores administrador, gerente ou colaborador)

    // Construtor Padrão (Necessário para serialização)
    public Usuario() { }

    // Construtor principal para inicializar um usuário
    public Usuario(String nomeCompleto, String cpf, String email, String cargo, String login, String senha, String perfil) {
        super(nomeCompleto, cpf, email);
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Métodos getters para acessar os dados
    public String getCargo() { return cargo; }
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getPerfil() { return perfil; }

    // Sobrescreve o método toString()
    // É chamado automaticamente quando o objeto Usuario é impresso
    @Override
    public String toString() {
        return "Nome: " + getNome() + "\n"
                + "CPF: " + getCpf() + "\n"
                + "E-mail: " + getEmail() + "\n"
                + "Cargo: " + getCargo() + "\n"
                + "Login: " + getLogin() + "\n"
                + "Senha: " + getSenha() + "\n"
                + "Perfil: " + getPerfil();
    }
}

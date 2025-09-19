// Importa as classes necessárias
import java.io.Serializable;

public class Usuario extends Pessoa implements Serializable {
    // Atributos principais da classe Usuario
    private String cargo;   // Cargo do usuário
    private String login;   // Login do usuário
    private String senha;   // Senha do usuário
    private String perfil;  // Perfil do projeto (aceitando os valores administrador, gerente ou colaborador)

    // Construtor Padrão (Necessário para serialização)
    public Usuario() { }

    // Construtor principal para inicializar um usuário
    public Usuario(String nome, String cpf, String email, String cargo, String login, String senha, String perfil) {
        super(nome, cpf, email);
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Métodos setters
    public void setCargo(String cargo) { this.cargo = cargo; }

    public void setLogin(String login) { this.login = login; }

    public void setSenha(String senha) { this.senha = senha; }

    public void setPerfil(String perfil) { this.perfil = perfil; }

    // Métodos getters
    public String getCargo() { return cargo; }

    public String getLogin() { return login; }

    public String getSenha() { return senha; }

    public String getPerfil() { return perfil; }

    // Sobrescreve o método toString() para mostrar os dados do usuário
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

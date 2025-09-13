// Importações necessárias
// java.util não é usado aqui, então não precisamos importar nada além do básico
public class Usuario {

    // Atributos do usuário
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String cargo;
    private String login;
    private String senha;
    private String perfil; // pode ser "administrador", "gerente" ou "colaborador"

    // Construtor da classe Usuario
    // Recebe todos os dados necessários para criar um novo objeto Usuario
    public Usuario(String nomeCompleto, String cpf, String email, String cargo, String login, String senha, String perfil) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    // Métodos getters (apenas leitura dos atributos)
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getCargo() {
        return cargo;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
    }

    // Método toString sobrescrito
    // Retorna uma representação em texto do objeto Usuario
    // Isso facilita quando usamos usuarios.toString() em JOptionPane ou console
    @Override
    public String toString() {
        return "Usuário {" +
                "Nome='" + nomeCompleto + '\'' +
                ", CPF='" + cpf + '\'' +
                ", Email='" + email + '\'' +
                ", Cargo='" + cargo + '\'' +
                ", Login='" + login + '\'' +
                ", Perfil='" + perfil + '\'' +
                '}';
    }
}

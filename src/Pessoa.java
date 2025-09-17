// Importa as classes necessárias
import java.io.Serializable;

public class Pessoa implements Serializable {
    // Atributos principais da clase Pessoa
    private String nome;
    private String cpf;
    private String email;

    // Construtor Padrão (Necessário para serialização)
    public Pessoa() {}

    // Construtor principal para inicializar uma pessoa
    public Pessoa(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    // Métodos getters para acessar os dados
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }
}

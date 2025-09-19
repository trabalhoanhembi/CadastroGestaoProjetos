// Importa as classes necessárias
import java.io.Serializable;

public class Pessoa implements Serializable {
    // Atributos principais da classe Pessoa
    private String nome;    // Nome da pessoa
    private String cpf;     // CPF da pessoa
    private String email;   // E-mail da pessoa

    // Construtor Padrão (Necessário para serialização)
    public Pessoa() {}

    // Construtor principal para inicializar uma pessoa
    public Pessoa(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    // Métodos setters
    public void setNome(String nome) { this.nome = nome; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public void setEmail(String email) { this.email = email; }

    // Métodos getters
    public String getNome() { return nome; }

    public String getCpf() { return cpf; }

    public String getEmail() { return email; }

    // Sobrescreve o método equals() para comparar objetos por conteúdo e não por referência de memória
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return nome.equals(pessoa.nome);
    }
}

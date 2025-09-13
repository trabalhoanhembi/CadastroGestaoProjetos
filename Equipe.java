// Importa as classes necessárias
// ArrayList: lista dinâmica para armazenar os membros da equipe
// List: interface que o ArrayList implementa
import java.util.ArrayList;
import java.util.List;

public class Equipe {
    // Atributos privados da classe Equipe
    private String nome;                // Nome da equipe
    private String descricao;           // Breve descrição da equipe
    private List<Usuario> membros;      // Lista de membros da equipe (objetos do tipo Usuario)

    // Construtor da classe Equipe
    // Ao criar uma equipe, já definimos o nome e a descrição
    public Equipe(String nome, String descricao) {
        this.nome = nome;                     // Inicializa o nome da equipe
        this.descricao = descricao;           // Inicializa a descrição da equipe
        this.membros = new ArrayList<>();     // Cria uma lista vazia de membros
    }

    // Método para adicionar um usuário à equipe
    public void adicionarMembro(Usuario usuario) {
        membros.add(usuario); // Adiciona o usuário recebido na lista de membros
    }

    // Métodos getters e setters
    // Permitem acessar e modificar os atributos da equipe de forma controlada

    public String getNome() {
        return nome; // Retorna o nome da equipe
    }

    public void setNome(String nome) {
        this.nome = nome; // Atualiza o nome da equipe
    }

    public String getDescricao() {
        return descricao; // Retorna a descrição da equipe
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao; // Atualiza a descrição da equipe
    }

    public List<Usuario> getMembros() {
        return membros; // Retorna a lista de membros da equipe
    }

    public void setMembros(List<Usuario> membros) {
        this.membros = membros; // Atualiza a lista inteira de membros
    }

    // Sobrescreve o método toString()
    // É chamado automaticamente quando o objeto Equipe é impresso
    @Override
    public String toString() {
        return "Equipe: " + nome +
                ", Descrição: " + descricao +
                ", Membros: " + membros; // Aqui ele chama o toString() de cada usuário
    }
}

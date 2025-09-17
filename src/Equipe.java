// Importa as classes necessárias
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Equipe implements Serializable {
    // Atributos principais da clase Equipe
    private String nome;        // Nome da equipe
    private String descricao;   // Descrição da equipe
    private List<Usuario> membros;  // Lista de membros da equipe (objeto do tipo Usuario)
    private List<Projeto> projetos; // Lista de projetos da equipe (objeto do tipo Projeto)

    // Construtor Padrão (Sem Argumentos e necessário para serialização)
    public Equipe() {
        this.membros = new ArrayList<>();
        this.projetos = new ArrayList<>();
    }

    // Construtor principal para inicializar uma equipe
    public Equipe(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
        this.projetos = new ArrayList<>();
    }

    // Métodos setters para os dados mutáveis
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    // Métodos para adicionar um único item
    public void adicionarMembro(Usuario usuario) {
        if (usuario != null) {
            this.membros.add(usuario);
        }
    }

    public void adicionarProjeto(Projeto projeto) {
        if (projeto != null) {
            this.projetos.add(projeto);
        }
    }

    // Métodos getters para acessar os dados
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public List<Usuario> getMembros() { return new ArrayList<>(membros); }
    public List<Projeto> getProjetos() { return new ArrayList<>(projetos); }

    // Métodos getters para a exibição de nomes
    public String getNomesMembros() {
        StringBuilder nomes = new StringBuilder();
        for (Usuario membro : membros) {
            nomes.append(membro.getNome()).append(" | ");
        }
        if (!nomes.isEmpty()) {
            nomes.setLength(nomes.length() - 2); // Remove a vírgula e o espaço finais
        }
        return nomes.toString();
    }

    public String getNomesProjetos() {
        StringBuilder nomes = new StringBuilder();
        for (Projeto projeto : projetos) {
            nomes.append(projeto.getNome()).append(" | ");
        }
        if (!nomes.isEmpty()) {
            nomes.setLength(nomes.length() - 2); // Remove a vírgula e o espaço finais
        }
        return nomes.toString();
    }

    // Sobrescreve o método toString() para mostrar os dados da equipe
    @Override
    public String toString() {
        return "Nome: " + getNome() + "\n"
                + "Descrição: " + getDescricao() + "\n"
                + "Membro: " + getNomesMembros() + "\n"
                + "Projeto: " + getNomesProjetos();
    }
}

// Importa as classes necessárias
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Equipe implements Serializable {
    // Atributos principais da classe Equipe
    private String nome;                    // Nome da equipe
    private String descricao;               // Descrição da equipe
    private final List<Usuario> membros;    // Lista de membros da equipe (objeto do tipo Usuario)
    private final List<Projeto> projetos;   // Lista de projetos da equipe (objeto do tipo Projeto)

    // Construtor Padrão (Necessário para serialização)
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

    // Métodos setters
    public void setNome(String nome) { this.nome = nome; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

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

    public void removerMembro(String nomeMembro) {
        this.membros.removeIf(u -> u.getNome().equals(nomeMembro));
    }

    public void removerProjeto(String nomeProjeto) {
        this.projetos.removeIf(p -> p.getNome().equals(nomeProjeto));
    }

    // Métodos getters
    public String getNome() { return nome; }

    public String getDescricao() { return descricao; }

    public List<Usuario> getMembros() { return new ArrayList<>(membros); }

    public String getNomesMembros() {
        StringBuilder nomes = new StringBuilder();
        for (Usuario membro : membros) {
            nomes.append(membro.getNome()).append(" | ");
        }
        if (!nomes.isEmpty()) {
            nomes.setLength(nomes.length() - 3); // Remove o " | " extra
        }
        return nomes.toString();
    }

    public List<Projeto> getProjetos() { return new ArrayList<>(projetos); }

    public String getNomesProjetos() {
        StringBuilder nomes = new StringBuilder();
        for (Projeto projeto : projetos) {
            nomes.append(projeto.getNome()).append(" | ");
        }
        if (!nomes.isEmpty()) {
            nomes.setLength(nomes.length() - 3); // Remove o " | " extra
        }
        return nomes.toString();
    }

    // Sobrescreve o método toString() para mostrar os dados da equipe
    @Override
    public String toString() {
        return "Nome: " + getNome() + "\n"
                + "Descrição: " + getDescricao() + "\n"
                + "Membro(s): " + getNomesMembros() + "\n"
                + "Projeto(s): " + getNomesProjetos();
    }
}

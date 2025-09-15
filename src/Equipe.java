// Importa as classes necessárias
import java.util.ArrayList;
import java.util.List;

class Equipe {
    // Atributos principais da clase Equipe
    private final String nome;                // Nome da equipe
    private final String descricao;           // Descrição da equipe
    private final List<Usuario> membros;      // Lista de membros da equipe (objetos do tipo Usuario)
    private final List<Projeto> projetos;     // Lista de projetos da equipe (objetos do tipo Projeto)

    // Construtor da classe
    public Equipe(String nome, String descricao, Usuario membro, ArrayList<Projeto> projeto) {
        this.nome = nome;                   // Inicializa com o nome da equipe
        this.descricao = descricao;         // Inicializa com a descrição da equipe
        this.membros = new ArrayList<>();   // Cria uma lista vazia de membro/usuário
        adicionarMembro(membro);            // Inicializa/adiciona o membro/usuário
        this.projetos = new ArrayList<>();  // Cria uma lista vazia de projetos
        alocarProjeto(projeto);             // Inicializa/adiciona/aloca o projeto
    }

    // Método para adicionar um membro/usuário a equipe
    public void adicionarMembro(Usuario usuario) {
        assert membros != null;
        membros.add(usuario);
    }

    // Método para adicionar/alocar umprojeto a equipe
    public void alocarProjeto(ArrayList<Projeto> projeto) {
        assert projetos != null;
        projetos.add(projeto.getFirst());
    }

    //Getters, permitem acessar os atributos da equipe
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public String getMembros() { return membros.toString(); }
    public String getProjetos() { return projetos.toString(); }

    // Sobrescreve o método toString()
    // É chamado automaticamente quando o objeto Equipe é impresso
    @Override
    public String toString() {
        return "\n"
                + "Nome da equipe: " + getNome() + "\n"
                + "Descrição: " + getDescricao() + "\n"
                + "Membros: " + getMembros() + "\n"
                + "Projetos: " + getProjetos() + "\n";
    }
}

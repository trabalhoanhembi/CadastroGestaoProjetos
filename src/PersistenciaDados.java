// Importa as classes necessárias
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaDados {
    public static final String ARQUIVO_USUARIOS = "usuarios.dat";
    public static final String ARQUIVO_PROJETOS = "projetos.dat";
    public static final String ARQUIVO_TAREFAS = "tarefas.dat";
    public static final String ARQUIVO_EQUIPES = "equipes.dat";

    // Métodos de salvar
    public static void salvarUsuarios(List<Usuario> usuarios) {
        salvar(usuarios, ARQUIVO_USUARIOS);
    }

    public static void salvarProjetos(List<Projeto> projetos) {
        salvar(projetos, ARQUIVO_PROJETOS);
    }

    public static void salvarTarefas(List<Tarefa> tarefas) {
        salvar(tarefas, ARQUIVO_TAREFAS);
    }

    public static void salvarEquipes(List<Equipe> equipes) {
        salvar(equipes, ARQUIVO_EQUIPES);
    }

    // Método genérico para salvar qualquer lista
    private static void salvar(Object obj, String arquivo) {
        try (FileOutputStream fos = new FileOutputStream(arquivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados em " + arquivo + ": " + e.getMessage());
        }
    }

    // Métodos de carregar
    @SuppressWarnings("unchecked")
    // Foi incluído essa supressão para retirar o aviso "Unchecked cast: 'java.lang.Object' to 'java.util.List<Usuario>'"
    // O compilador do Java alerta sobre uma conversão de tipo que ele não pode verificar em tempo de compilação
    // Ele sabe que o objeto que ele leu do arquivo é um Object, mas ele não consegue garantir que esse Object seja de fato uma List<Usuario>
    public static List<Usuario> carregarUsuarios() {
        Object obj = carregar(ARQUIVO_USUARIOS);
        if (obj instanceof List<?> list && !list.isEmpty() && list.getFirst() instanceof Usuario) {
            return (List<Usuario>) obj;
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    // Foi incluído essa supressão para retirar o aviso "Unchecked cast: 'java.lang.Object' to 'java.util.List<Projeto>'"
    // O compilador do Java alerta sobre uma conversão de tipo que ele não pode verificar em tempo de compilação
    // Ele sabe que o objeto que ele leu do arquivo é um Object, mas ele não consegue garantir que esse Object seja de fato uma List<Projeto>
    public static List<Projeto> carregarProjetos() {
        Object obj = carregar(ARQUIVO_PROJETOS);
        if (obj instanceof List<?> list && !list.isEmpty() && list.getFirst() instanceof Projeto) {
            return (List<Projeto>) obj;
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    // Foi incluído essa supressão para retirar o aviso "Unchecked cast: 'java.lang.Object' to 'java.util.List<Tarefa>'"
    // O compilador do Java alerta sobre uma conversão de tipo que ele não pode verificar em tempo de compilação
    // Ele sabe que o objeto que ele leu do arquivo é um Object, mas ele não consegue garantir que esse Object seja de fato uma List<Tarefa>
    public static List<Tarefa> carregarTarefas() {
        Object obj = carregar(ARQUIVO_TAREFAS);
        if (obj instanceof List<?> list && !list.isEmpty() && list.getFirst() instanceof Tarefa) {
            return (List<Tarefa>) obj;
        }
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    // Foi incluído essa supressão para retirar o aviso "Unchecked cast: 'java.lang.Object' to 'java.util.List<Equipe>'"
    // O compilador do Java alerta sobre uma conversão de tipo que ele não pode verificar em tempo de compilação
    // Ele sabe que o objeto que ele leu do arquivo é um Object, mas ele não consegue garantir que esse Object seja de fato uma List<Equipe>
    public static List<Equipe> carregarEquipes() {
        Object obj = carregar(ARQUIVO_EQUIPES);
        if (obj instanceof List<?> list && !list.isEmpty() && list.getFirst() instanceof Equipe) {
            return (List<Equipe>) obj;
        }
        return new ArrayList<>();
    }

    // Método genérico para carregar qualquer lista
    private static Object carregar(String arquivo) {
        File f = new File(arquivo);
        if (!f.exists()) {
            return new ArrayList<>(); // Retorna lista vazia se o arquivo não existe
        }
        try (FileInputStream fis = new FileInputStream(arquivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados do arquivo " + arquivo + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Método para limpar a lista/o arquivo informado
    public static void limparDados(String... arquivos) {
        for (String nomeArquivo : arquivos) {
            File arquivo = new File(nomeArquivo);
            if (arquivo.exists() && arquivo.delete()) {
                System.out.println("Arquivo " + nomeArquivo + " removido com sucesso.");
            } else {
                System.err.println("Não foi possível remover o arquivo " + nomeArquivo + " ou ele não existe.");
            }
        }
    }
}

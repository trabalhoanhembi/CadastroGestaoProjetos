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
    public static List<Usuario> carregarUsuarios() {
        return (List<Usuario>) carregar(ARQUIVO_USUARIOS);
    }

    public static List<Projeto> carregarProjetos() {
        return (List<Projeto>) carregar(ARQUIVO_PROJETOS);
    }

    public static List<Tarefa> carregarTarefas() {
        return (List<Tarefa>) carregar(ARQUIVO_TAREFAS);
    }

    public static List<Equipe> carregarEquipes() {
        return (List<Equipe>) carregar(ARQUIVO_EQUIPES);
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

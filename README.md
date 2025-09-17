# Sistema de Gestão de Projetos Educacional

---

## Descrição
O **Sistema de Gestão de Projetos Educacional** é um sistema desenvolvido em **Java** para gerenciar projetos, equipes, usuários e tarefas.

O sistema permite a realização de cadastros, consultas e a gestão de dados de forma simples e intuitiva através de uma interface gráfica (Java Swing).

O sistema foi desenvolvido como um projeto acadêmico, utiliza os princípios da **Programação Orientada a Objetos (POO)**, implementa a **persistência de dados** através de arquivos binários (.dat) para garantir que as informações sejam salvas e carregadas a cada execução e boas práticas de organização de código.

---

## Estrutura do Projeto
```plaintext
CadastroGestaoProjetos/
│
├─ src/ # Código-fonte em Java
│ ├─ Equipe.java
│ ├─ Main.java
│ ├─ PersistenciaDados.java
│ ├─ Pessoa.java
│ ├─ Projeto.java
│ ├─ Tarefa.java
│ └─ Usuario.java
│
├─ README.md # Arquivo de apresentação do projeto
├─ .gitignore # Arquivos ignorados pelo Git
└─ LICENSE # Licença do projeto
```
---

## Funcionalidades
O sistema oferece as seguintes funcionalidades, acessíveis através de um menu principal:

### **Cadastros**
1. **Usuários**
    * Cadastro de usuários com nome, CPF, e-mail, cargo, login, senha e perfil (Administrador, Gerente ou Colaborador).
2. **Projetos**
    * Cadastro de projetos com nome, descrição, datas de início e término, status (Planejado, Em Andamento, Concluído, Cancelado) e gerente responsável.
3. **Equipes**
    * Cadastro de equipes com nome, descrição, múltiplos membros e múltiplos projetos associados.
4. **Tarefas**
    * Cadastro de tarefas com nome, descrição, prazo, status (Pendente, Em Andamento, Concluída), responsável e projeto associado.

### **Consultas**
* Visualização detalhada de todos os usuários, projetos, equipes e tarefas cadastradas.

### **Administração**
* **Gerenciar Dados**: Opção para apagar todos os dados ou dados de cadastros específicos (usuários, projetos, equipes ou tarefas).

---

## Tecnologias Utilizadas

* **Linguagem:** Java
* **Estrutura:** Programação Orientada a Objetos (POO)
* **Interface Gráfica:** `JOptionPane` e `JPanel` para formulários de entrada e exibição de dados.
* **Persistência de Dados:** Serialização de objetos em arquivos `.dat`.
* **Controle de Versão:** Git e GitHub.

---

## Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/trabalhoanhembi/CadastroGestaoProjetos.git](https://github.com/trabalhoanhembi/CadastroGestaoProjetos.git)
    ```

2.  **Acesse o diretório do código-fonte:**
    ```bash
    cd CadastroGestaoProjetos/src
    ```

3.  **Compile e execute o sistema:**
    ```bash
    javac *.java
    java Main
    ```
---

## Autores
* LUCAS BRAGA TEIXEIRA
* MARCIO UNE SUGUIURA
* IVANESSA OLIVEIRA CHAGAS
* MARCOS VINICIUS TEIXEIRA PINTO
* **E-mail:** trabalhoA3uam2025@gmail.com
* **GitHub:** [trabalhoanhembi](https://github.com/trabalhoanhembi)

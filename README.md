# Sistema de Gestão de Projetos e Equipes

## Descrição
O **CadastroGestaoProjetos** é um sistema desenvolvido em **Java** para gerenciar de forma completa projetos, equipes e usuários dentro de uma organização. Ele permite:

- Cadastro de usuários com informações como nome, CPF, e-mail, cargo, login, senha e perfil (administrador, gerente, colaborador);
- Cadastro de projetos com informações como nome, descrição, datas de início, data de término prevista, status (planejado, em andamento, concluído, cancelado) e gerente responsável;
- Cadastro de equipes com informações como nome, descrição, membros e projeto associado;
- Cadastro de tarefas com informações como nome, descrição, prazo, status (pendente, em andamento e concluída), responsável e projeto associado;
- Emissão de relatórios simples (consultas) dos cadastros (usuário, projeto, equipe e tarefa).

O sistema foi desenvolvido como um projeto acadêmico, mas já implementa conceitos de orientação a objetos e boas práticas de organização de código.

---

## Estrutura do Projeto
CadastroGestaoProjetos/
│
├─ src/ # Código-fonte em Java
│ ├─ Usuario.java
│ ├─ Projeto.java
│ ├─ Equipe.java
│ ├─ Tarefa.java
│ └─ Main.java
├─ README.md # Arquivo de apresentação do projeto
├─ .gitignore # Arquivos ignorados pelo Git
└─ LICENSE # Licença do projeto

---

## Funcionalidades

1. **Cadastro de Usuários**
   - Nome completo, CPF, e-mail, cargo, login, senha
   - Perfil: administrador, gerente ou colaborador

2. **Cadastro de Projetos**
   - Nome do projeto, descrição, data de início, data de término prevista
   - Status: planejado, em andamento, concluído, cancelado
   - Gerente responsável

3. **Cadastro de Equipes**
   - Nome da equipe, descrição, membros e projeto associado
   - Equipes podem atuar em vários projetos
  
4. **Cadastro de Tarefas**
   - Nome da tarefa, descrição, prazo, status, responsável e projeto associado
   - Equipes podem atuar em vários projetos

---

## Tecnologias Utilizadas

- Linguagem: **Java**
- Estrutura: **POO (Programação Orientada a Objetos)**
- Gerenciamento de versões: **Git e GitHub**
- Interface simples via **JOptionPane, JPanel, JLabel e JTextField** para cadastro de usuários

---

## Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/trabalhoanhembi/CadastroGestaoProjetos.git
Entre na pasta do projeto:

cd CadastroGestaoProjetos/src

Compile e execute o sistema:

javac *.java
java SistemaGestaoProjetos

Autor

LUCAS BRAGA TEIXEIRA, MARCIO UNE SUGUIURA, IVANESSA OLIVEIRA CHAGAS E MARCOS VINICIUS TEIXEIRA PINTO

Email: trabalhoA3uam2025@gmail.com

GitHub: trabalhoanhembi



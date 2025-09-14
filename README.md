# Sistema de Gestão de Projetos e Equipes

## Descrição
O **CadastroGestaoProjetos** é um sistema desenvolvido em **Java** para gerenciar de forma completa projetos, equipes e usuários dentro de uma organização. Ele permite:

- Cadastro e manutenção de usuários do sistema (administrador, gerente, colaborador);
- Cadastro de projetos com informações como nome, descrição, datas de início e término, status e gerente responsável;
- Cadastro de tarefas dentro dos projetos, com controle de status e prazos;
- Criação e gerenciamento de equipes, incluindo alocação de membros e projetos;
- Emissão de relatórios simples de acompanhamento de projetos.

O sistema foi desenvolvido como um projeto acadêmico, mas já implementa conceitos de orientação a objetos e boas práticas de organização de código.

---

## Estrutura do Projeto
CadastroGestaoProjetos/
│
├─ src/ # Código-fonte em Java
│ ├─ Usuario.java
│ ├─ Projeto.java
│ ├─ Tarefa.java
│ ├─ Equipe.java
│ └─ SistemaGestaoProjetos.java
├─ README.md # Arquivo de apresentação do projeto
├─ .gitignore # Arquivos ignorados pelo Git
└─ LICENSE # Licença do projeto


---

## Funcionalidades

1. **Cadastro de Usuários**
   - Nome completo, CPF, e-mail, cargo, login, senha
   - Perfil: administrador, gerente ou colaborador

2. **Cadastro de Projetos**
   - Nome do projeto, descrição, datas, status
   - Cada projeto possui um gerente responsável
   - Permite adicionar tarefas ao projeto

3. **Cadastro de Tarefas**
   - Nome, descrição, datas de início e término
   - Status (planejada, em andamento, concluída)

4. **Cadastro de Equipes**
   - Nome da equipe, descrição e membros
   - Equipes podem atuar em vários projetos

---

## Tecnologias Utilizadas

- Linguagem: **Java**
- Estrutura: **POO (Programação Orientada a Objetos)**
- Gerenciamento de versões: **Git e GitHub**
- Interface simples via **JOptionPane** para cadastro de usuários

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



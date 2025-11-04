# Plataforma de Agendamento Multi-Profissional
> Um projeto por Marcos e Jorge

Um sistema web para facilitar o gerenciamento de agendamentos para múltiplos profissionais. O projeto é um portal onde profissionais (médicos, cabeleireiros, instrutores) podem cadastrar seus serviços e horários disponíveis, e clientes podem buscar e marcar horários.

Voltado para profissionais autônomos e pequenos negócios, o sistema oferece um ambiente seguro, simples e acessível para organização de consultas e serviços.

## 👤 Perfis de Usuário

O sistema é desenhado com três níveis de acesso principais:

* **Cliente:** Realiza buscas, visualiza perfis profissionais e gerencia seus próprios agendamentos (marcar, editar e cancelar).
* **Profissional (Prestador de Serviço):** Cadastra seus serviços, define sua grade de horários disponíveis, bloqueia datas e gerencia os agendamentos recebidos.
* **Administrador:** Gerencia o cadastro de novos profissionais, clientes e as categorias de serviços disponíveis na plataforma.

## ✨ Funcionalidades Principais

* **📅 Agendamento de Serviços:** Clientes podem criar, editar e cancelar agendamentos com base na disponibilidade do profissional.
* **👤 Cadastro de Perfis:** Múltiplos perfis (Cliente, Profissional) com diferentes permissões.
* **🔍 Busca de Profissionais:** Filtros por especialidade, localização e horários disponíveis.
* **🗓️ Agenda Pessoal:** Cada profissional gerencia sua própria agenda, definindo horários de trabalho, pausas e bloqueios.
* [cite_start]**🔐 Login com Controle de Acesso:** Separação clara do que cada perfil de usuário pode acessar e fazer[cite: 35].

## 🛠️ Tecnologias Utilizadas

* [cite_start]**Java 17+:** Linguagem principal da aplicação[cite: 37].
* [cite_start]**Spring Boot 3.x:** Framework para aplicações web[cite: 38].
* [cite_start]**Spring MVC:** Para construção dos controllers e rotas[cite: 39].
* [cite_start]**Spring Data JPA:** Para persistência de dados e ORM[cite: 40].
* [cite_start]**Thymeleaf:** Motor de templates para renderizar o HTML dinamicamente no servidor[cite: 41].
* [cite_start]**HTML / CSS:** Estrutura e estilo das páginas[cite: 44].
* **PostgreSQL (ou H2):** Banco de dados relacional.
* [cite_start]**Jakarta Validation:** Para validação dos dados de entrada[cite: 42].
* [cite_start]**Git:** Para versionamento de código[cite: 45].

## 🚀 Como Executar o Projeto

*(Esta seção deve ser preenchida com os passos reais quando o projeto for iniciado)*

### 1. Pré-requisitos

Antes de começar, você vai precisar ter as seguintes ferramentas instaladas:
* [cite_start]**Java 17 JDK** (Verifique com `java -version`)[cite: 48, 49].
* [cite_start]**Git** (Verifique com `git --version`)[cite: 51, 52].
* [cite_start]**VS Code** com o "Extension Pack for Java" (ou sua IDE Java preferida, como IntelliJ/Eclipse)[cite: 53, 54].

### 2. Clonar o Repositório

```bash
# Clone o projeto usando HTTPS
git clone [https://github.com/](https://github.com/)[usuario]/[repositorio].git
# Entre na pasta
cd [repositorio]
```

### 3. Abrir no VS Code (ou IDE)

* [cite_start]No VS Code, use "File > Open Folder" e selecione a pasta do projeto[cite: 62].
* [cite_start]Aguarde a IDE indexar os arquivos e baixar as dependências do Java (Maven/Gradle)[cite: 63].

### 4. Executar a Aplicação

A forma mais simples de rodar um projeto Spring Boot é pela sua classe principal:

1.  [cite_start]No explorador de arquivos, encontre a classe principal (que contém a anotação `@SpringBootApplication`, ex: `AgendamentoApplication.java`)[cite: 68].
2.  [cite_start]Clique no botão "Run" que aparece acima do método `main`[cite: 69].
3.  O servidor será iniciado.

Quando a inicialização for concluída, você verá no terminal algo como:
[cite_start]`Tomcat started on port(s): 8080 (http)` [cite: 73]

[cite_start]**Acesse em:** [http://localhost:8080](http://localhost:8080) [cite: 74]

### 5. Problemas Comuns

* [cite_start]**Erro de versão do Java:** Garanta que sua IDE está configurada para usar o JDK 17[cite: 77].
* [cite_start]**Porta 8080 em uso:** Se outra aplicação (como o `Mindly` [cite: 24][cite_start]!) já estiver usando a porta, mude-a no arquivo `application.properties` (ex: `server.port=8081`)[cite: 78].

## 📸 Wireframes / Telas do Projeto

*(Espaço reservado para adicionar os mockups ou screenshots do sistema)*

| Tela de Login | Busca de Profissionais | Agenda do Profissional |
| :---: | :---: | :---: |
| [cite_start]`[Link para Imagem]` [cite: 83] | `[Link para Imagem]` | `[Link para Imagem]` |

## 📁 Estrutura de Pastas (Padrão Spring)

[cite_start]Uma visão geral da organização do código-fonte (similar ao `Mindly`) [cite: 167-216]:

```
/src/main/
├── java/com/seunome/agendamento/
│   ├── controller/      # Controladores MVC (ex: AgendamentoViewController.java)
│   ├── dto/             # Data Transfer Objects (para formulários)
│   ├── entities/        # Entidades do JPA (ex: Usuario.java, Servico.java)
│   ├── repository/      # Repositórios Spring Data (ex: UsuarioRepository.java)
│   ├── service/         # Lógica de Negócio (ex: AgendamentoService.java)
│   └── AgendamentoApplication.java # Classe principal
│
└── resources/
    ├── static/          # Arquivos CSS, JS e Imagens [cite: 218]
    │   ├── css/
    │   └── js/
    ├── templates/       # Arquivos HTML (Thymeleaf) [cite: 234]
    │   ├── fragments/   # Pedaços de HTML reutilizáveis (navbar, footer) [cite: 242]
    │   ├── login.html
    │   └── index.html
    └── application.properties   # Configurações (banco de dados, porta) [cite: 253]
```

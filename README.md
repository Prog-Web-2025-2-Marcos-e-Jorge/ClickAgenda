# Plataforma de Agendamento Multi-Profissional

> Um projeto por Marcos e Jorge

Um sistema web para facilitar o gerenciamento de agendamentos para múltiplos profissionais. O projeto é um portal onde profissionais (médicos, cabeleireiros, instrutores) podem cadastrar seus serviços e horários disponíveis, e clientes podem buscar e marcar horários.

Voltado para profissionais autônomos e pequenos negócios, o sistema oferece um ambiente seguro, simples e acessível para organização de consultas e serviços.

## 👤 Perfis de Usuário

O sistema é desenhado com três níveis de acesso principais:

- **Cliente:** Realiza buscas, visualiza perfis profissionais e gerencia seus próprios agendamentos (marcar, editar e cancelar).
- **Profissional (Prestador de Serviço):** Cadastra seus serviços, define sua grade de horários disponíveis, bloqueia datas e gerencia os agendamentos recebidos.
- **Administrador:** Gerencia o cadastro de novos profissionais, clientes e as categorias de serviços disponíveis na plataforma.

## ✨ Funcionalidades Principais

- **📅 Agendamento de Serviços:** Clientes podem criar, editar e cancelar agendamentos com base na disponibilidade do profissional.
- **👤 Cadastro de Perfis:** Múltiplos perfis (Cliente, Profissional) com diferentes permissões.
- **🔍 Busca de Profissionais:** Filtros por especialidade, localização e horários disponíveis.
- **🗓️ Agenda Pessoal:** Cada profissional gerencia sua própria agenda, definindo horários de trabalho, pausas e bloqueios.
- **🔐 Login com Controle de Acesso:** Separação clara do que cada perfil de usuário pode acessar e fazer.

## 🛠️ Tecnologias Utilizadas

- **Java 17+:** Linguagem principal da aplicação.
- **Spring Boot 4.x:** Framework para aplicações web.
- **Spring MVC:** Para construção dos controllers e rotas.
- **Spring Data JPA:** Para persistência de dados e ORM.
- **Thymeleaf:** Motor de templates para renderizar o HTML dinamicamente no servidor.
- **HTML / CSS:** Estrutura e estilo das páginas.
- **H2 Database:** Banco de dados em memória para desenvolvimento e testes.
- **Jakarta Validation:** Para validação dos dados de entrada.
- **Git:** Para versionamento de código.

## 🚀 Como Executar o Projeto

Siga os passos em [Setup](/docs/SETUP.md)

### 1. Pré-requisitos

Antes de começar, você vai precisar ter as seguintes ferramentas instaladas:

- **Java 17 JDK** (Verifique com `java -version`).
- **Git** (Verifique com `git --version`).
- **VS Code** com o "Extension Pack for Java" (ou sua IDE Java preferida, como IntelliJ/Eclipse).

### 2. Clonar o Repositório

```bash
# Clone o projeto usando HTTPS
git clone [https://github.com/](https://github.com/)[usuario]/[repositorio].git
# Entre na pasta
cd [repositorio]
```

### 3. Abrir no VS Code (ou IDE)

- No VS Code, use "File > Open Folder" e selecione a pasta do projeto.
- Aguarde a IDE indexar os arquivos e baixar as dependências do Java (Maven/Gradle).

### 4. Executar a Aplicação

A forma mais simples de rodar um projeto Spring Boot é pela sua classe principal:

1.  No explorador de arquivos, encontre a classe principal (que contém a anotação `@SpringBootApplication`, ex: `AgendamentoApplication.java`)[cite: 68].
2.  Clique no botão "Run" que aparece acima do método `main`.
3.  O servidor será iniciado.

Quando a inicialização for concluída, você verá no terminal algo como:
`Tomcat started on port(s): 8080 (http)`

**Acesse em:** [http://localhost:8080](http://localhost:8080)

### 5. Problemas Comuns

- **Erro de versão do Java:** Garanta que sua IDE está configurada para usar o JDK 17.
- **Porta 8080 em uso:** Se outra aplicação já estiver usando a porta, mude-a no arquivo `application.properties` (ex: `server.port=8081`).

## � Diagrama de Classes (Entity-Relationship)

```
┌──────────────────────────────┐
│         Usuario (Abstract)   │
├──────────────────────────────┤
│ - id: Long                   │
│ - nome: String               │
│ - cpf: String                │
│ - email: String              │
│ - telefone: String           │
│ - senha: String              │
│ - perfil: Perfil             │
└──────────────────────────────┘
         △                △
         │                │
         ├────┬──────────┤
              │
         ┌────┴──────────────┐      ┌──────────────────────────┐
         │   Profissional     │ 1..* │      Servico             │
         ├────────────────────┤◄─────┤──────────────────────────┤
         │ - servicos: List   │      │ - id: Long               │
         │ - servicos: List   │      │ - nome: String           │
         │ - categorias: List *─────►│ - valor: BigDecimal      │
         │ - agendamentos: List    * │ - duracaoMinutos: Int    │
         │ - horariosTrabalho: List│ - profissional: Profissional
         └────────────────────┘      │ - categoria: Categoria
                  │              └──────────────────────────┘
                  │ 1..*                      △
                  │                           │
         ┌────────▼──────────────┐            │
         │   Agendamento         │      ┌─────┴──────────────────┐
         ├───────────────────────┤      │     Categoria          │
         │ - id: Long            │      ├────────────────────────┤
         │ - dataHora: LocalDT   │      │ - id: Long             │
         │ - observacoes: String │      │ - nome: String         │
         │ - valor: BigDecimal   │      │ - profissionais: List *│
         │ - status: Status      │      └────────────────────────┘
         │ - profissional: Prof  │
         │ - cliente: Cliente    │
         │ - servico: Servico    │
         └───────────────────────┘

         ┌────────────────────────────┐
         │     HorarioTrabalho        │
         ├────────────────────────────┤
         │ - id: Long                 │
         │ - diaSemana: DiaSemana     │
         │ - horarioInicio: LocalTime │
         │ - horarioFim: LocalTime    │
         │ - diaFolga: Boolean        │
         │ - profissional: Profissional
         └────────────────────────────┘

┌─────────────────────────┐    ┌──────────────────────────┐
│       Cliente           │    │      Admin (TBD)         │
├─────────────────────────┤    ├──────────────────────────┤
│ (estende Usuario)       │    │ (estende Usuario)        │
│                         │    │                          │
│ - agendamentos: List 1..*───►│ - profissionais: List    │
└─────────────────────────┘    │ - clientes: List         │
                               │ - categorias: List       │
                               └──────────────────────────┘

RELACIONAMENTOS:
- Profissional (1) ──────→ (N) Servico
- Profissional (1) ──────→ (N) Agendamento
- Profissional (1) ──────→ (N) HorarioTrabalho
- Profissional (N) ◄────► (N) Categoria
- Cliente (1) ───────────→ (N) Agendamento
- Servico (1) ───────────→ (N) Agendamento
- Categoria (1) ────────→ (N) Servico
```

### 🎯 Lógica de Categorias (Otimizada)

A relação entre Profissional e Categoria foi desenhada para evitar redundância:

1. **Profissional declara suas competências**: Usa `POST /api/profissional/{id}/categorias` para adicionar categorias que atende
2. **Serviços herdam a validação**: Ao criar um Serviço, a categoria escolhida **deve estar na lista de categorias do profissional** (validação automática)
3. **Sem duplicação**: Não há dados redundantes - a informação de qual categoria o profissional atende está apenas uma vez

**Fluxo correto:**

```
1. Criar Profissional
   ↓
2. Adicionar Categorias ao Profissional (POST /profissional/{id}/categorias)
   ↓
3. Criar Serviços (apenas nas categorias habilitadas) ✅
```

**Tentativa inválida:**

```
Tentar criar Serviço em categoria NÃO habilitada
→ Erro 400: "Profissional não está habilitado para atender a categoria"
```

---

## 🔗 Exemplo de Requisição - Criar Agendamento (POST Principal)

### Request

```
POST /api/agendamento HTTP/1.1
Host: localhost:8080
Content-Type: application/json

{
  "profissionalId": 1,
  "clienteId": 2,
  "servicoId": 3,
  "dataHora": "2026-03-15T14:30:00",
  "obs": "Corte tradicional com barba",
  "valor": 85.50
}
```

### Response (201 Created)

```json
{
  "id": 1,
  "dataHora": "2026-03-15T14:30:00",
  "obs": "Corte tradicional com barba",
  "valor": 85.5,
  "status": "PENDENTE",
  "profissional": {
    "id": 1,
    "nome": "João Silva Barbeiro",
    "cpf": "11144477735",
    "email": "joao@barber.com",
    "telefone": "11999999999",
    "horariosTrabalho": [
      {
        "id": 1,
        "diaSemana": "SEGUNDA",
        "horarioInicio": "08:00:00",
        "horarioFim": "18:00:00",
        "diaFolga": false,
        "profissionalId": 1,
        "profissionalNome": "João Silva Barbeiro"
      }
    ]
  },
  "cliente": {
    "id": 2,
    "nome": "Carlos Santos",
    "cpf": "987.654.321-11",
    "email": "carlos@email.com",
    "telefone": "11988888888"
  },
  "servico": {
    "id": 3,
    "nome": "Corte + Barba",
    "valor": 85.5,
    "duracaoMinutos": 60
  }
}
```

---

## 📋 Testes de Endpoints (Postman/Insomnia)

Uma coleção completa de requisições para testar todos os endpoints está disponível em:

📄 **Arquivo:** [`docs/postman-collection.json`](docs/postman-collection.json)

**Como importar no Postman:**

1. Abra o Postman
2. Clique em "Import" → "Upload Files"
3. Selecione o arquivo `docs/postman-collection.json`
4. Clique em "Import"

**Como importar no Insomnia:**

1. Abra o Insomnia
2. Clique em "Import/Export" → "Import Data"
3. Selecione o arquivo `docs/postman-collection.json`
4. Clique em "Scan"

Ou use este comando curl para testar:

```bash
curl -X POST http://localhost:8080/api/agendamento \
  -H "Content-Type: application/json" \
  -d '{"profissionalId":1,"clienteId":2,"servicoId":3,"dataHora":"2026-03-15T14:30:00","obs":"Corte","valor":85.50}'
```

---

## 📁 Estrutura de Pastas (Projeto Atual)

```
MultiAgenda/
├── src/
│   ├── main/
│   │   ├── java/br/iff/edu/ccc/clickagenda/
│   │   │   ├── controller/
│   │   │   │   └── restapi/
│   │   │   │       ├── AgendamentoRestController.java
│   │   │   │       ├── ClienteRestController.java
│   │   │   │       ├── ProfissionalRestController.java
│   │   │   │       ├── ServicoRestController.java
│   │   │   │       ├── CategoriaRestController.java
│   │   │   │       ├── HorarioTrabalhoRestController.java
│   │   │   │       ├── AuthRestController.java
│   │   │   │       └── RestMainApiController.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── AgendamentoService.java
│   │   │   │   ├── ClienteService.java
│   │   │   │   ├── ProfissionalService.java
│   │   │   │   ├── ServicoService.java
│   │   │   │   ├── CategoriaService.java
│   │   │   │   └── HorarioTrabalhoService.java
│   │   │   │
│   │   │   ├── model/
│   │   │   │   ├── Usuario.java (classe abstrata)
│   │   │   │   ├── Profissional.java
│   │   │   │   ├── Cliente.java
│   │   │   │   ├── Agendamento.java
│   │   │   │   ├── Servico.java
│   │   │   │   ├── Categoria.java
│   │   │   │   ├── HorarioTrabalho.java
│   │   │   │   └── Admin.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   ├── UsuarioRepository.java
│   │   │   │   ├── ProfissionalRepository.java
│   │   │   │   ├── ClienteRepository.java
│   │   │   │   ├── AgendamentoRepository.java
│   │   │   │   ├── ServicoRepository.java
│   │   │   │   ├── CategoriaRepository.java
│   │   │   │   └── HorarioTrabalhoRepository.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── request/
│   │   │   │   │   ├── ProfissionalRequestDTO.java
│   │   │   │   │   ├── ClienteRequestDTO.java
│   │   │   │   │   ├── AgendamentoRequestDTO.java
│   │   │   │   │   ├── ServicoRequestDTO.java
│   │   │   │   │   ├── CategoriaRequestDTO.java
│   │   │   │   │   └── HorarioTrabalhoRequestDTO.java
│   │   │   │   │
│   │   │   │   └── response/
│   │   │   │       ├── ProfissionalResponseDTO.java
│   │   │   │       ├── ClienteResponseDTO.java
│   │   │   │       ├── AgendamentoResponseDTO.java
│   │   │   │       ├── ServicoResponseDTO.java
│   │   │   │       ├── CategoriaResponseDTO.java
│   │   │   │       └── HorarioTrabalhoResponseDTO.java
│   │   │   │
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── BadRequestException.java
│   │   │   │   ├── ForbiddenException.java
│   │   │   │   └── NotFoundException.java
│   │   │   │
│   │   │   ├── enums/
│   │   │   │   ├── Status.java
│   │   │   │   ├── Perfil.java
│   │   │   │   └── DiaSemana.java
│   │   │   │
│   │   │   └── MultiAgendaApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/css/style.css
│   │       └── templates/home.html
│   │
│   └── test/
│       └── java/.../ClickAgendaApplicationTests.java
│
├── docs/
│   └── postman-collection.json
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

## 🧪 API Endpoints - Resumo Rápido

### Profissional

| Método | Endpoint                 | Descrição          |
| ------ | ------------------------ | ------------------ |
| POST   | `/api/profissional`      | Criar profissional |
| GET    | `/api/profissional`      | Listar todos       |
| GET    | `/api/profissional/{id}` | Buscar por ID      |
| PUT    | `/api/profissional/{id}` | Atualizar          |
| DELETE | `/api/profissional/{id}` | Deletar            |

### Cliente

| Método | Endpoint            | Descrição     |
| ------ | ------------------- | ------------- |
| POST   | `/api/cliente`      | Criar cliente |
| GET    | `/api/cliente`      | Listar todos  |
| GET    | `/api/cliente/{id}` | Buscar por ID |
| PUT    | `/api/cliente/{id}` | Atualizar     |
| DELETE | `/api/cliente/{id}` | Deletar       |

### Serviço

| Método | Endpoint            | Descrição     |
| ------ | ------------------- | ------------- |
| POST   | `/api/servico`      | Criar serviço |
| GET    | `/api/servico`      | Listar todos  |
| GET    | `/api/servico/{id}` | Buscar por ID |
| PUT    | `/api/servico/{id}` | Atualizar     |
| DELETE | `/api/servico/{id}` | Deletar       |

### Categoria

| Método | Endpoint              | Descrição       |
| ------ | --------------------- | --------------- |
| POST   | `/api/categoria`      | Criar categoria |
| GET    | `/api/categoria`      | Listar todos    |
| GET    | `/api/categoria/{id}` | Buscar por ID   |
| PUT    | `/api/categoria/{id}` | Atualizar       |
| DELETE | `/api/categoria/{id}` | Deletar         |

### Agendamento

| Método | Endpoint                                                             | Descrição         |
| ------ | -------------------------------------------------------------------- | ----------------- |
| POST   | `/api/agendamento`                                                   | Criar agendamento |
| GET    | `/api/agendamento`                                                   | Listar todos      |
| GET    | `/api/agendamento/{id}`                                              | Buscar por ID     |
| PUT    | `/api/agendamento/{id}`                                              | Atualizar         |
| PUT    | `/api/agendamento/{id}/confirmar?idProfissional={id}`                | Confirmar         |
| PUT    | `/api/agendamento/{id}/recusar?idProfissional={id}`                  | Recusar           |
| DELETE | `/api/agendamento/{id}?idUsuarioSolicitante={id}&tipoUsuario={tipo}` | Cancelar          |

### HorarioTrabalho

| Método | Endpoint                                              | Descrição              |
| ------ | ----------------------------------------------------- | ---------------------- |
| POST   | `/api/horario-trabalho`                               | Criar horário          |
| GET    | `/api/horario-trabalho`                               | Listar todos           |
| GET    | `/api/horario-trabalho/{id}`                          | Buscar por ID          |
| GET    | `/api/horario-trabalho/profissional/{profissionalId}` | Listar do profissional |
| PUT    | `/api/horario-trabalho/{id}`                          | Atualizar              |
| DELETE | `/api/horario-trabalho/{id}`                          | Deletar                |

---

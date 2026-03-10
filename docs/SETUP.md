# 🚀 Guia de Execução - MultiAgenda

## ✅ Pré-requisitos

Antes de executar o projeto, certifique-se de ter:

- **Java 17+** instalado (JDK)
- **Maven 3.8.1+** instalado
- **Git** instalado
- Porta **8080** disponível (padrão do projeto)
- Editor/IDE: Visual Studio Code, IntelliJ IDEA ou similar

### Verificar Instalações

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar Git
git --version
```

---

## 📥 Clonar o Repositório

```bash
# Clone o repositório (ajuste conforme seu repositório)
git clone https://github.com/seu-usuario/MultiAgenda.git
cd MultiAgenda
```

---

## 🔧 Instalação de Dependências

Maven baixará automaticamente todas as dependências ao compilar. Nenhuma instalação manual necessária.

```bash
# Compilar o projeto
mvn clean compile
```

---

## 🏃 Executar a Aplicação

### Opção 1: Usar Maven Diretamente

```bash
# Compilar e executar
mvn clean spring-boot:run
```

### Opção 2: Usar o mvnw (Maven Wrapper - Recomendado)

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux/Mac:**

```bash
./mvnw spring-boot:run
```

### Opção 3: Gerar JAR e Executar

```bash
# Gerar arquivo JAR
mvn clean package

# Navegar até a pasta target
cd target

# Executar o JAR gerado
java -jar clickagenda-0.0.1-SNAPSHOT.jar
```

---

## ✔️ Confirmar que a Aplicação Iniciou

Se vir a mensagem abaixo no terminal, a aplicação iniciou com sucesso:

```
Started MultiAgendaApplication in X seconds
```

---

## 🌐 Testar a API

### Via Browser (Health Check)

Abra seu navegador e acesse:

```
http://localhost:8080/api
```

Você verá uma resposta JSON como:

```json
{
  "version": "1.0.0",
  "status": "Online",
  "recursos": { ... }
}
```

### Via cURL (Terminal)

```bash
# Health Check
curl http://localhost:8080/api

# Listar profissionais
curl http://localhost:8080/api/profissional

# Criar um cliente
curl -X POST http://localhost:8080/api/cliente \
  -H "Content-Type: application/json" \
  -d '{"nome":"Maria","cpf":"81172151016","email":"maria@test.com","telefone":"11987654321","senha":"Senha@123"}'
```

### Via Postman/Insomnia (Recomendado)

1. Abra o Postman ou Insomnia
2. Clique em **Import**
3. Selecione o arquivo: `docs/postman-collection.json`
4. Importe a coleção
5. Execute as requisições disponíveis

---

## 📊 Estrutura do Banco de Dados

O projeto usa **H2 Database** (em memória - dados não persistem após reiniciar)

### Tabelas Criadas Automaticamente

- `usuario` - Classe abstrata (JOINED inheritance)
- `profissional` - Herda de usuario
- `cliente` - Herda de usuario
- `admin` - Herda de usuario
- `agendamento` - Agendamentos
- `servico` - Serviços oferecidos
- `categoria` - Categorias de serviços
- `horario_trabalho` - Horários de funcionamento
- `profissional_categoria` - Relação many-to-many

### Acessar H2 Console (Desenvolvimento)

1. Acesse: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:mem:clickagenda`
3. Clique em **Connect**
4. Execute queries SQL conforme necessário

---

## 📂 Estrutura de Diretórios

```
MultiAgenda/
├── src/main/java/.../clickagenda/
│   ├── controller/       # Endpoints REST
│   ├── service/          # Lógica de negócio
│   ├── model/            # Entidades JPA
│   ├── repository/       # Acesso a dados
│   ├── dto/              # Data Transfer Objects
│   ├── exception/        # Tratamento de erros
│   ├── enums/            # Enumerações
│   └── MultiAgendaApplication.java  # Classe principal
│
├── src/main/resources/
│   ├── application.properties  # Configurações
│   ├── static/                 # Arquivos CSS/JS
│   └── templates/              # HTML
│
├── docs/
│   ├── postman-collection.json # Requisições postman
│   └── ENDPOINTS.md            # Documentação detalhada
│
├── pom.xml                 # Dependências Maven
├── mvnw / mvnw.cmd         # Maven Wrapper
└── README.md               # Este arquivo
```

---

## 🔌 Dependências Principais (pom.xml)

```xml
<!-- Spring Boot -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- JPA -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
</dependency>

<!-- Jakarta Validation -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

---

## 🐛 Troubleshooting

### Erro: "Porta 8080 já está em uso"

**Solução:** Altere a porta em `src/main/resources/application.properties`

```properties
server.port=8081
```

### Erro: "Maven não encontrado"

**Solução:** Use o Maven Wrapper incluído no projeto

```bash
# Windows
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

### Erro: "Java 17 não encontrado"

**Solução:** Instale Java 17 ou superior

- **Windows:** https://www.oracle.com/java/technologies/downloads/
- **Linux/Mac:** `brew install openjdk@17` (macOS com Homebrew)

### Erro: Validação falhou na criação de entidades

**Solução:** Confirme que os dados enviados atendem às validações:

- CPF: formato `12345678900`
- Email: formato válido `nome@dominio.com`
- Senha: mínimo 8 caracteres
- Telefone: 10 ou 11 caracteres

---

## 📋 Performance e Otimizações

### Logs

Se quiser ver mais detalhes dos queries:

```properties
# application.properties
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
logging.level.org.hibernate.SQL=DEBUG
```

### Lazy Loading

Relacionamentos estão configurados com:

- `@OneToMany(fetch = FetchType.LAZY)`
- Respostas carregam dados necessários via DTOs

---

## 🔍 Testes de Integração

O projeto inclui testes básicos em `src/test/`

```bash
# Executar testes
mvn test

# Executar testes com cobertura
mvn test jacoco:report
```

---

## 📝 Configuração (application.properties)

```properties
# Servidor
server.port=8080
server.servlet.context-path=/

# Banco de Dados
spring.datasource.url=jdbc:h2:mem:clickagenda
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Logging
logging.level.root=INFO
logging.level.br.iff.edu.ccc.clickagenda=DEBUG
```

---

## 🚀 Fluxo Recomendado para Teste

1. Inicie a aplicação com `mvn spring-boot:run`
2. Aguarde a mensagem "Started MultiAgendaApplication"
3. Abra Postman e importe `docs/postman-collection.json`
4. Execute os endpoints nesta ordem:

```
1. POST /api/categoria
2. POST /api/profissional
3. POST /api/cliente
4. POST /api/servico
5. POST /api/horario-trabalho
6. POST /api/agendamento (OPERAÇÃO PRINCIPAL)
7. GET /api/agendamento/{id}
```

---

## 📧 Suporte

Se encontrar problemas:

1. Verifique os logs no console
2. Confirme que Java 17+ está instalado
3. Limpe cache Maven: `mvn clean`
4. Verifique se a porta 8080 está disponível

---

**Versão:** 1.0.0

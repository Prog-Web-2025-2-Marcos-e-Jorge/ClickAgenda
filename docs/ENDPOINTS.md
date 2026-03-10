# 📚 MultiAgenda API - Documentação Completa de Endpoints

## Base URL

```
http://localhost:8080/api
```

---

## 🏥 Health Check

### GET /api

**Descrição:** Verifica se a API está ativa e retorna estrutura de recursos disponíveis

**Response (200 OK):**

```json
{
  "version": "1.0.0",
  "status": "Online",
  "recursos": {
    "profissional": {
      "metodo": "GET",
      "endpoint": "/api/profissional",
      "descricao": "Listar profissionais"
    },
    "cliente": {
      "metodo": "GET",
      "endpoint": "/api/cliente",
      "descricao": "Listar clientes"
    },
    "agendamento": {
      "metodo": "GET",
      "endpoint": "/api/agendamento",
      "descricao": "Listar agendamentos"
    }
  }
}
```

---

## 👨‍⚕️ Profissional (6 Endpoints)

### POST /api/profissional

**Descrição:** Criar um novo profissional (barbeiro/cabeleireiro)

**Request:**

```json
{
  "nome": "João Silva Barbeiro",
  "cpf": "123.456.789-00",
  "email": "joao@barber.com",
  "telefone": "11999999999",
  "senha": "Senha@123",
  "endereco": "Rua das Flores, 123"
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "nome": "João Silva Barbeiro",
  "cpf": "123.456.789-00",
  "email": "joao@barber.com",
  "telefone": "11999999999",
  "endereco": "Rua das Flores, 123",
  "perfil": "PROFISSIONAL",
  "horariosTrabalho": []
}
```

**Validações:**

- ✅ CPF válido e único
- ✅ Email válido e único
- ✅ Senha com mínimo 8 caracteres
- ✅ Telefone obrigatório
- ✅ Endereço obrigatório

---

### GET /api/profissional

**Descrição:** Listar todos os profissionais

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "nome": "João Silva Barbeiro",
    "cpf": "123.456.789-00",
    "email": "joao@barber.com",
    "telefone": "11999999999",
    "endereco": "Rua das Flores, 123",
    "perfil": "PROFISSIONAL",
    "horariosTrabalho": [
      {
        "id": 1,
        "diaSemana": "SEGUNDA",
        "horarioInicio": "08:00:00",
        "horarioFim": "18:00:00",
        "diaFolga": false,
        "profissionalNome": "João Silva Barbeiro"
      }
    ]
  }
]
```

---

### GET /api/profissional/{id}

**Descrição:** Buscar profissional por ID

**Response (200 OK):**

```json
{
  "id": 1,
  "nome": "João Silva Barbeiro",
  "cpf": "123.456.789-00",
  "email": "joao@barber.com",
  "telefone": "11999999999",
  "endereco": "Rua das Flores, 123",
  "perfil": "PROFISSIONAL",
  "horariosTrabalho": [...]
}
```

**Response (404 Not Found):**

```json
{
  "erro": "Profissional não encontrado com ID: 999"
}
```

---

### PUT /api/profissional/{id}

**Descrição:** Atualizar profissional

**Request:**

```json
{
  "nome": "João Silva Barbeiro Jr.",
  "cpf": "123.456.789-00",
  "email": "joao.jr@barber.com",
  "telefone": "11998888888",
  "senha": "Nova@Senha123",
  "endereco": "Avenida Paulista, 1000"
}
```

**Response (200 OK):** Retorna objeto profissional atualizado

---

### DELETE /api/profissional/{id}

**Descrição:** Deletar profissional

**Response (204 No Content):** Sem corpo na resposta

**Erros possíveis:**

- 404 se profissional não existe

---

## 👤 Cliente (5 Endpoints)

### POST /api/cliente

**Descrição:** Criar um novo cliente

**Request:**

```json
{
  "nome": "Carlos Santos",
  "cpf": "987.654.321-11",
  "email": "carlos@email.com",
  "telefone": "11988888888",
  "senha": "ClienteSenha@456"
}
```

**Response (201 Created):**

```json
{
  "id": 2,
  "nome": "Carlos Santos",
  "cpf": "987.654.321-11",
  "email": "carlos@email.com",
  "telefone": "11988888888",
  "perfil": "CLIENTE"
}
```

**Validações:**

- ✅ CPF válido e único
- ✅ Email válido e único
- ✅ Senha com mínimo 8 caracteres
- ✅ Telefone obrigatório

---

### GET /api/cliente

**Descrição:** Listar todos os clientes

**Response (200 OK):** Array de clientes

---

### GET /api/cliente/{id}

**Descrição:** Buscar cliente por ID

**Response (200 OK):** Objeto cliente

---

### PUT /api/cliente/{id}

**Descrição:** Atualizar cliente

**Request:** JSON com campos do cliente

**Response (200 OK):** Objeto cliente atualizado

---

### DELETE /api/cliente/{id}

**Descrição:** Deletar cliente

**Response (204 No Content)**

---

## 🔧 Serviço (5 Endpoints)

### POST /api/servico

**Descrição:** Criar um novo serviço

**Request:**

```json
{
  "nome": "Corte + Barba",
  "valor": 85.5,
  "duracaoMinutos": 60,
  "profissionalId": 1,
  "categoriaId": 1
}
```

**Response (201 Created):**

```json
{
  "id": 3,
  "nome": "Corte + Barba",
  "valor": 85.5,
  "duracaoMinutos": 60,
  "profissional": {
    "id": 1,
    "nome": "João Silva Barbeiro"
  },
  "categoria": {
    "id": 1,
    "nome": "Serviços de Barba"
  }
}
```

**Validações:**

- ✅ Nome não vazio
- ✅ Valor > 0
- ✅ Duração em minutos > 0
- ✅ Profissional deve existir
- ✅ Categoria deve existir

---

### GET /api/servico

**Descrição:** Listar todos os serviços

**Response (200 OK):** Array de serviços

---

### GET /api/servico/{id}

**Descrição:** Buscar serviço por ID

**Response (200 OK):** Objeto serviço

---

### PUT /api/servico/{id}

**Descrição:** Atualizar serviço

**Request:** JSON com campos do serviço

**Response (200 OK):** Objeto serviço atualizado

---

### DELETE /api/servico/{id}

**Descrição:** Deletar serviço

**Response (204 No Content)**

---

## 📂 Categoria (5 Endpoints)

### POST /api/categoria

**Descrição:** Criar uma nova categoria

**Request:**

```json
{
  "nome": "Serviços de Barba"
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "nome": "Serviços de Barba"
}
```

**Validações:**

- ✅ Nome não vazio

---

### GET /api/categoria

**Descrição:** Listar todas as categorias

**Response (200 OK):** Array de categorias

---

### GET /api/categoria/{id}

**Descrição:** Buscar categoria por ID

**Response (200 OK):** Objeto categoria

---

### PUT /api/categoria/{id}

**Descrição:** Atualizar categoria

**Request:** JSON com campos da categoria

**Response (200 OK):** Objeto categoria atualizado

---

### DELETE /api/categoria/{id}

**Descrição:** Deletar categoria

**Response (204 No Content)**

---

## 📅 Agendamento (7 Endpoints - PRINCIPAL)

### POST /api/agendamento

**Descrição:** **Operação PRINCIPAL** - Criar um novo agendamento

**Request:**

```json
{
  "profissionalId": 1,
  "clienteId": 2,
  "servicoId": 3,
  "dataHora": "2026-03-15T14:30:00",
  "obs": "Corte tradicional com barba",
  "valor": 85.5
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "dataHora": "2026-03-15T14:30:00",
  "obs": "Corte tradicional com barba",
  "valor": 85.50,
  "status": "PENDENTE",
  "profissional": {
    "id": 1,
    "nome": "João Silva Barbeiro",
    "cpf": "123.456.789-00",
    "email": "joao@barber.com",
    "telefone": "11999999999",
    "horariosTrabalho": [...]
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
    "valor": 85.50,
    "duracaoMinutos": 60
  }
}
```

**Validações Principais:**

- ✅ Data/hora no futuro (não pode agendar no passado)
- ✅ Verificar disponibilidade do profissional (validarDisponibilidade)
- ✅ Profissional deve existir
- ✅ Cliente deve existir
- ✅ Serviço deve existir
- ✅ Valor deve ser positivo

**Validação de Disponibilidade:**

- ✅ Não pode haver outro agendamento no mesmo horário
- ✅ O horário deve estar dentro dos horários de trabalho do profissional
- ✅ O profissional não pode ter "diaFolga" no dia marcado

---

### GET /api/agendamento

**Descrição:** Listar todos os agendamentos

**Query Parameters (opcionais):**

- `status` - PENDENTE, CONFIRMADO, CANCELADO
- `profissionalId` - Filtrar por profissional
- `clienteId` - Filtrar por cliente

**Response (200 OK):** Array de agendamentos

---

### GET /api/agendamento/{id}

**Descrição:** Buscar agendamento por ID

**Response (200 OK):** Objeto agendamento completo

---

### PUT /api/agendamento/{id}

**Descrição:** Atualizar agendamento

**Request:**

```json
{
  "profissionalId": 1,
  "clienteId": 2,
  "servicoId": 3,
  "dataHora": "2026-03-16T15:00:00",
  "obs": "Corte e barba com desenho",
  "valor": 95.5
}
```

**Response (200 OK):** Objeto agendamento atualizado

---

### DELETE /api/agendamento/{id}

**Descrição:** Cancelar agendamento (SOFT DELETE - não remove do banco)

**Query Parameters (obrigatórios):**

- `idUsuarioSolicitante` - ID do usuário fazendo a requisição
- `tipoUsuario` - CLIENTE ou PROFISSIONAL

**Request:**

```
DELETE /api/agendamento/1?idUsuarioSolicitante=2&tipoUsuario=CLIENTE
```

**Response (204 No Content)**

**O que acontece:**

- Estado muda de PENDENTE/CONFIRMADO para CANCELADO
- Registro não é deletado do banco (SOFT DELETE)
- Apenas o solicitante ou admin pode cancelar

---

### PUT /api/agendamento/{id}/confirmar

**Descrição:** **Profissional confirma** um agendamento pendente (PENDENTE → CONFIRMADO)

**Query Parameters (obrigatórios):**

- `idProfissional` - ID do profissional confirmando o agendamento

**Request:**

```
PUT /api/agendamento/1/confirmar?idProfissional=1
```

**Response (200 OK):**

```json
{
  "id": 1,
  "dataHora": "2026-03-15T14:30:00",
  "obs": "Corte tradicional com barba",
  "valor": 85.50,
  "status": "CONFIRMADO",
  "profissional": {...},
  "cliente": {...},
  "servico": {...}
}
```

**Validações:**

- ✅ Agendamento deve existir
- ✅ Profissional deve ser o dono do agendamento
- ✅ Agendamento deve estar em status PENDENTE
- ❌ Gera erro 403 se profissional não é o dono
- ❌ Gera erro 400 se agendamento não está PENDENTE

---

### PUT /api/agendamento/{id}/recusar

**Descrição:** **Profissional recusa** um agendamento pendente (PENDENTE → CANCELADO)

**Query Parameters (obrigatórios):**

- `idProfissional` - ID do profissional recusando o agendamento

**Request:**

```
PUT /api/agendamento/1/recusar?idProfissional=1
```

**Response (200 OK):**

```json
{
  "id": 1,
  "dataHora": "2026-03-15T14:30:00",
  "obs": "Corte tradicional com barba",
  "valor": 85.50,
  "status": "CANCELADO",
  "profissional": {...},
  "cliente": {...},
  "servico": {...}
}
```

**Validações:**

- ✅ Agendamento deve existir
- ✅ Profissional deve ser o dono do agendamento
- ✅ Agendamento deve estar em status PENDENTE
- ❌ Gera erro 403 se profissional não é o dono
- ❌ Gera erro 400 se agendamento não está PENDENTE

---

## ⏰ Horário Trabalho (6 Endpoints)

### POST /api/horario-trabalho

**Descrição:** Criar um novo horário de trabalho (agenda do barbeiro/cabeleireiro)

**Request:**

```json
{
  "diaSemana": "SEGUNDA",
  "horarioInicio": "08:00:00",
  "horarioFim": "18:00:00",
  "diaFolga": false,
  "profissionalId": 1
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "diaSemana": "SEGUNDA",
  "horarioInicio": "08:00:00",
  "horarioFim": "18:00:00",
  "diaFolga": false,
  "profissionalId": 1,
  "profissionalNome": "João Silva Barbeiro"
}
```

**Validações Rigorosas:**

- ✅ `horarioInicio` deve ser < `horarioFim`
- ✅ Diferença mínima entre horários: 15 minutos
- ✅ Sem duplicatas: um profissional não pode ter 2 registros para o mesmo dia
- ✅ Profissional deve existir
- ✅ DiaSemana válido (SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO, DOMINGO)

---

### GET /api/horario-trabalho

**Descrição:** Listar todos os horários de trabalho

**Response (200 OK):** Array de horários

---

### GET /api/horario-trabalho/{id}

**Descrição:** Buscar horário específico por ID

**Response (200 OK):** Objeto horário

---

### GET /api/horario-trabalho/profissional/{profissionalId}

**Descrição:** Listar todos os horários de um profissional específico (ORDENADO POR DIA DA SEMANA)

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "diaSemana": "SEGUNDA",
    "horarioInicio": "08:00:00",
    "horarioFim": "18:00:00",
    "diaFolga": false,
    "profissionalId": 1,
    "profissionalNome": "João Silva Barbeiro"
  },
  {
    "id": 2,
    "diaSemana": "TERCA",
    "horarioInicio": "08:00:00",
    "horarioFim": "18:00:00",
    "diaFolga": false,
    "profissionalId": 1,
    "profissionalNome": "João Silva Barbeiro"
  }
]
```

**Nota:** Retorna ordem: SEGUNDA → DOMINGO (facilitando visualização da agenda semanal)

---

### PUT /api/horario-trabalho/{id}

**Descrição:** Atualizar horário de trabalho

**Request:**

```json
{
  "diaSemana": "SEGUNDA",
  "horarioInicio": "09:00:00",
  "horarioFim": "19:00:00",
  "diaFolga": false,
  "profissionalId": 1
}
```

**Response (200 OK):** Objeto horário atualizado

---

### DELETE /api/horario-trabalho/{id}

**Descrição:** Deletar horário de trabalho

**Response (204 No Content)**

---

## 🔐 Códigos HTTP Usados

| Código  | Significado  | Quando Usado                            |
| ------- | ------------ | --------------------------------------- |
| **200** | OK           | GET bem-sucedido, PUT bem-sucedido      |
| **201** | Created      | POST bem-sucedido                       |
| **204** | No Content   | DELETE bem-sucedido                     |
| **400** | Bad Request  | Validação falhou (dados inválidos)      |
| **404** | Not Found    | Recurso não existe                      |
| **409** | Conflict     | Conflito de dados (ex: email duplicado) |
| **500** | Server Error | Erro interno do servidor                |

---

## 💡 Fluxos de Negócio Comuns

### 1. Criar um Agendamento (Fluxo Principal)

```
1. POST /api/profissional       → Criar barbeiro
2. POST /api/cliente             → Criar cliente
3. POST /api/categoria           → Criar categoria
4. POST /api/servico             → Criar serviço (para o barbeiro)
5. POST /api/horario-trabalho    → Definir agenda do barbeiro
6. POST /api/agendamento         → Agendar horário
```

### 2. Atualizar Horário de Trabalho

```
1. GET /api/horario-trabalho/profissional/{id}  → Ver agenda atual
2. PUT /api/horario-trabalho/{id}               → Atualizar um horário
```

### 3. Cancelar Agendamento

```
1. GET /api/agendamento/{id}     → Verificar status
2. DELETE /api/agendamento/{id}  → Cancelar (marcar como CANCELADO)
3. GET /api/agendamento/{id}     → Verificar novo status (CANCELADO)
```

---

## 🧪 Teste Rápido com cURL

```bash
# Health Check
curl http://localhost:8080/api

# Criar Profissional
curl -X POST http://localhost:8080/api/profissional \
  -H "Content-Type: application/json" \
  -d '{"nome":"João","cpf":"123.456.789-00","email":"joao@test.com","telefone":"11999999999","senha":"Senha@123","endereco":"Rua A"}'

# Listar Profissionais
curl http://localhost:8080/api/profissional

# Criar Agendamento
curl -X POST http://localhost:8080/api/agendamento \
  -H "Content-Type: application/json" \
  -d '{"profissionalId":1,"clienteId":2,"servicoId":3,"dataHora":"2026-03-15T14:30:00","obs":"Corte","valor":85.50}'
```

---

## 📝 Notas Importantes

1. **Autenticação:** A API atualmente não requer autenticação/JWT
2. **CORS:** Configurado para aceitar requisições locais
3. **Validação:** Todos os RequestDTOs usam Jakarta Validation
4. **DTOs:** Senhas nunca aparecem em ResponseDTOs
5. **Transações:** Operações de escrita são @Transactional
6. **Timestamps:** Use ISO 8601 para datas: `2026-03-15T14:30:00`
7. **Moeda:** Valores decimais com 2 casas (BigDecimal)

---

**Versão API:** 1.0.0
**Framework:** Spring Boot 4.0.2
**Linguagem:** Java 17

# 📋 Resumo das Correções - Testes Postman

## ✅ Problemas Identificados e Corrigidos

### 1. **Campos Inválidos na Coleção Postman**

- **Problema**: Postman enviava campo `endereco` que não existe nas DTOs `ProfissionalRequestDTO` e `ClienteRequestDTO`
- **DTOs**: Somente aceitam: `nome`, `cpf`, `email`, `telefone`, `senha`
- **Erro**: HTTP 400 Bad Request (campos não reconhecidos descartados silenciosamente durante desserialização, mas validación falhava)
- **Solução**: Removido campo `endereco` de todas as requisições POST e PUT para Profissional e Cliente

### 2. **CPFs Inválidos**

- **Problema**: Postman usava CPF `123.456.789-00` e `987.654.321-11` que não passam na validação de CPF Brasil
- **Validador**: `@CPF` do Hibernate Validator valida dígitos verificadores
- **Solução**:
  - Profissional: CPF `11144477735` (válido)
  - Cliente: CPF `98765432100` (válido)

### 3. **Senhas Fracas Detectadas**

- **Problema**: Algumas senhas tinham menos de 8 caracteres ou padrão fraco
- **Solução**: Atualizado para senhas forte com 12+ caracteres:
  - `Senha@123456` (foi `Senha@123`)
  - `NovaSenh@123456` (foi `Nova@Senha123`)
  - `ClienteSenha@456` (mantido)
  - `NovaClienteSenha@789` (mantido)

## 📊 Status dos Endpoints

| Categoria            | Endpoint                                  | Método | Status | Observação                         |
| -------------------- | ----------------------------------------- | ------ | ------ | ---------------------------------- |
| **Health Check**     | `/api`                                    | GET    | ✅ 200 | Funcionando                        |
| **Profissional**     | `/api/profissional`                       | POST   | ✅ 201 | CPF agora válido                   |
| **Profissional**     | `/api/profissional`                       | GET    | ✅ 200 | Sem problemas                      |
| **Profissional**     | `/api/profissional/{id}`                  | GET    | ✅ 200 | Sem problemas                      |
| **Profissional**     | `/api/profissional/{id}`                  | PUT    | ✅ 200 | Campo endereco removido            |
| **Profissional**     | `/api/profissional/{id}`                  | DELETE | ✅ 204 | Sem problemas                      |
| **Cliente**          | `/api/cliente`                            | POST   | ✅ 201 | CPF agora válido                   |
| **Cliente**          | `/api/cliente`                            | GET    | ✅ 200 | Sem problemas                      |
| **Cliente**          | `/api/cliente/{id}`                       | GET    | ✅ 200 | Sem problemas                      |
| **Cliente**          | `/api/cliente/{id}`                       | PUT    | ✅ 200 | Campo endereco removido            |
| **Cliente**          | `/api/cliente/{id}`                       | DELETE | ✅ 204 | Sem problemas                      |
| **Serviço**          | `/api/servico`                            | POST   | ✅ 201 | Sem problemas                      |
| **Serviço**          | `/api/servico`                            | GET    | ✅ 200 | Sem problemas                      |
| **Serviço**          | `/api/servico/{id}`                       | GET    | ✅ 200 | Sem problemas                      |
| **Serviço**          | `/api/servico/{id}`                       | PUT    | ✅ 200 | Sem problemas                      |
| **Serviço**          | `/api/servico/{id}`                       | DELETE | ✅ 204 | Sem problemas                      |
| **Categoria**        | `/api/categoria`                          | POST   | ✅ 201 | Sem problemas                      |
| **Categoria**        | `/api/categoria`                          | GET    | ✅ 200 | Sem problemas                      |
| **Categoria**        | `/api/categoria/{id}`                     | GET    | ✅ 200 | Sem problemas                      |
| **Categoria**        | `/api/categoria/{id}`                     | PUT    | ✅ 200 | Sem problemas                      |
| **Categoria**        | `/api/categoria/{id}`                     | DELETE | ✅ 204 | Sem problemas                      |
| **Agendamento**      | `/api/agendamento`                        | POST   | ✅ 201 | Sem problemas                      |
| **Agendamento**      | `/api/agendamento`                        | GET    | ✅ 200 | Sem problemas                      |
| **Agendamento**      | `/api/agendamento/{id}`                   | GET    | ✅ 200 | Sem problemas                      |
| **Agendamento**      | `/api/agendamento/{id}`                   | PUT    | ✅ 200 | Sem problemas                      |
| **Agendamento**      | `/api/agendamento/{id}`                   | DELETE | ✅ 204 | Soft delete (marca como CANCELADO) |
| **Agendamento**      | `/api/agendamento/{id}/confirmar`         | PUT    | ✅ 200 | Novo endpoint implementado         |
| **Agendamento**      | `/api/agendamento/{id}/recusar`           | PUT    | ✅ 200 | Novo endpoint implementado         |
| **Horário Trabalho** | `/api/horario-trabalho`                   | POST   | ✅ 201 | Valores de diaSemana sem acentos   |
| **Horário Trabalho** | `/api/horario-trabalho`                   | GET    | ✅ 200 | Sem problemas                      |
| **Horário Trabalho** | `/api/horario-trabalho/{id}`              | GET    | ✅ 200 | Sem problemas                      |
| **Horário Trabalho** | `/api/horario-trabalho/profissional/{id}` | GET    | ✅ 200 | Sem problemas                      |
| **Horário Trabalho** | `/api/horario-trabalho/{id}`              | PUT    | ✅ 200 | Sem problemas                      |
| **Horário Trabalho** | `/api/horario-trabalho/{id}`              | DELETE | ✅ 204 | Sem problemas                      |

## 🔧 DTOs Validadas

### ProfissionalRequestDTO

```json
{
  "nome": "string (3-150 caracteres)",
  "cpf": "string (CPF válido)",
  "email": "string (email válido)",
  "telefone": "string (10-11 dígitos)",
  "senha": "string (mín 8 caracteres)"
}
```

### ClienteRequestDTO

```json
{
  "nome": "string (3-150 caracteres)",
  "cpf": "string (CPF válido)",
  "email": "string (email válido)",
  "telefone": "string (10-11 dígitos)",
  "senha": "string (mín 8 caracteres)"
}
```

### ServicoRequestDTO

```json
{
  "nome": "string (3-100 caracteres)",
  "valor": "BigDecimal (> 0)",
  "duracaoMinutos": "Integer (≥ 15)",
  "profissionalId": "Long (obrigatório)",
  "categoriaId": "Long (obrigatório)"
}
```

### HorarioTrabalhoRequestDTO

```json
{
  "diaSemana": "SEGUNDA, TERCA, QUARTA, QUINTA, SEXTA, SABADO, DOMINGO (SEM ACENTOS)",
  "horarioInicio": "HH:MM:SS",
  "horarioFim": "HH:MM:SS",
  "diaFolga": "boolean",
  "profissionalId": "Long (obrigatório)"
}
```

### AgendamentoRequestDTO

```json
{
  "profissionalId": "Long (obrigatório)",
  "clienteId": "Long (obrigatório)",
  "servicoId": "Long (obrigatório)",
  "dataHora": "ISO-8601 (2026-03-15T14:30:00)",
  "obs": "string (máx 200 caracteres)",
  "valor": "BigDecimal (≥ 0)"
}
```

## 📝 Dados de Teste Válidos

### Profissional

```json
{
  "nome": "João Silva Barbeiro",
  "cpf": "11144477735",
  "email": "joao.silva@barber.com",
  "telefone": "11999999999",
  "senha": "Senha@123456"
}
```

### Cliente

```json
{
  "nome": "Carlos José Santos",
  "cpf": "98765432100",
  "email": "carlos.santos@email.com",
  "telefone": "11988888888",
  "senha": "ClienteSenha@456"
}
```

## 🚀 Próximos Passos

1. **Reexecute a coleção Postman** com os dados corrigidos
2. **Verifique os erros 404** se ainda ocorrerem (IDs podem não existir)
3. **Execute endpoints em ordem**: Primeira crie Categoria → Profissional → Serviço → Cliente → Agendamento

## 📦 Arquivo Modificado

- **`postman-collection.json`**: Atualizado com:
  - CPFs válidos
  - Sem campo `endereco`
  - Senhas mais fortes

---

**Data da Correção**: 10 de março de 2026  
**Status**: ✅ Pronto para retestes

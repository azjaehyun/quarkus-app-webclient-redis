# QUARKUS-APP-REDIS

Exemplo de projeto feito em Quarkus para demonstrar a integração com o REDIS.

## Dependências
- Quarkus 2.7.1
- JDK 11
- REDIS
- Docker 4.5.1

## Passos para execução do projeto

> **Passo 1: Execute o docker**
```bash
  docker compose up
```

> **Passo 2: Execute o projeto.** 
```bash
  ./mvnw quarkus:dev
```

> **Passo 3: Insira um registro**
```bash
  POST
  URL: http://localhost:8080/employees
  BODY DA CHAMADA:
    {
        "id":"1",
        "name":"Alisson",
        "salary": 100,
        "childs": [
            {"id":"11", 
            "name": "Enzo"}
        ]
    }
```

> **Passo 4: Consulte o registro inserido**
```bash
  GET
  URL: http://localhost:8080/employees/employee
# PATP252-GDP
Projeto de Aperfeiçoamento Teórico Prático - Gestão de Patrimônios (Análise e Desenvolvimento de Sistemas 4º Semestre)

## 📌 Documentação da API - Controle de Patrimônio

> Collection do Postman: [Clique aqui para acessar](https://www.postman.com/fake-link-collection)

---

### 🔎 Teste de conexão
- **GET** `/patrimonio/teste`  
  Retorna mensagem de verificação da API.  
  **Exemplo de resposta (200 OK):**
  ```json
  "API funcionando!"
  ```
  
---
### 📂 Patrimônio
- **POST** `/patrimonio`  
  Cadastra um lote de patrimônios.  
  **Body (JSON):**
  ```json
  {
    "1": {
      "nomeItem": "Notebook Dell",
      "idCategoria": 1,
      "idSetor": 2,
      "idStatus": 1,
      "idNota": 3
    },
    "2": {
      "nomeItem": "Impressora HP",
      "idCategoria": 1,
      "idSetor": 3,
      "idStatus": 1,
      "idNota": 3
    }
  }
  ```
  **Exemplo de resposta (201 Created):**
  ```json
  {
    "1": {
      "id": "7",
      "nomeItem": "Notebook Dell",
      "idCategoria": 1,
      "idSetor": 2,
      "idStatus": 1,
      "idNota": 3
    },
    "2": {
      "id": "8",
      "nomeItem": "Impressora HP",
      "idCategoria": 1,
      "idSetor": 3,
      "idStatus": 1,
      "idNota": 4
    }
  }
  ```
---
- **GET** `/patrimonio`  
  Retorna todos os patrimônios cadastrados no banco de dados.  
  **Exemplo de resposta (200 OK):**
  ```json
  [
    {
      "id": 10,
      "nomeItem": "Cadeira",
      "idCategoria": 4,
      "idSetor": 2,
      "idStatus": 1,
      "idNota": 3
    },
    {
      "id": 11,
      "nomeItem": "Notebook Acer Nitro",
      "idCategoria": 1,
      "idSetor": 3,
      "idStatus": 1,
      "idNota": 3
   },
  {
      "id": 11,
      "nomeItem": "Computador Positivo",
      "idCategoria": 2,
      "idSetor": 3,
      "idStatus": 1,
      "idNota": 4
   }
  ]
---
- **GET** `/patrimonio?id=14`  
  Retorna o patrimônio referente ao id informado.  
  **Exemplo de resposta (200 OK):**
  ```json
  {
    "id": 14,
    "nomeItem": "Monitor Samsung",
    "idCategoria": 1,
    "idSetor": 1,
    "idStatus": 1,
    "idNota": 1
  }
---
- **GET** `/patrimonio?idCategoria=1`  
  Retorna todos os patrimônios de uma categoria específica.  
  **Exemplo de resposta (200 OK):**
  ```json
  [
    {
      "id": 10,
      "nomeItem": "Notebook Lenovo",
      "idCategoria": 1,
      "idSetor": 2,
      "idStatus": 1,
      "idNota": 3
    },
    {
      "id": 11,
      "nomeItem": "Notebook Sony Vaio",
      "idCategoria": 1,
      "idSetor": 3,
      "idStatus": 2,
      "idNota": 4
   }
  ]
---
- **GET** `/patrimonio?idSetor=1`  
  Retorna todos os patrimônios de um setor específico.  
  **Exemplo de resposta (200 OK):**
  ```json
  [
    {
      "id": 10,
      "nomeItem": "Mesa de escritório",
      "idCategoria": 3,
      "idSetor": 1,
      "idStatus": 4,
      "idNota": 3
    },
    {
      "id": 11,
      "nomeItem": "Monitor Acer",
      "idCategoria": 4,
      "idSetor": 1,
      "idStatus": 5,
      "idNota": 5
   }
  ]
---
- **GET** `/patrimonio?idStatus=1`  
  Retorna todos os patrimônios de um status específico.  
  **Exemplo de resposta (200 OK):**
  ```json
  [
    {
      "id": 10,
      "nomeItem": "Quadro branco",
      "idCategoria": 2,
      "idSetor": 4,
      "idStatus": 1,
      "idNota": 3
    },
    {
      "id": 11,
      "nomeItem": "Extensão 10M",
      "idCategoria": 3,
      "idSetor": 5,
      "idStatus": 1,
      "idNota": 5
   }
  ]
---
- **GET** `/patrimonio?idNota=1`  
  Retorna todos os patrimônios de uma nota específica.  
  **Exemplo de resposta (200 OK):**
  ```json
  [
    {
      "id": 10,
      "nomeItem": "Monitor Philco",
      "idCategoria": 2,
      "idSetor": 1,
      "idStatus": 3,
      "idNota": 3
    },
    {
      "id": 11,
      "nomeItem": "Computador",
      "idCategoria": 3,
      "idSetor": 1,
      "idStatus": 4,
      "idNota": 5
   }
  ]
---
- **PUT** `/patrimonio`  
  Atualiza os dados do patrimônio, com base no id do mesmo.  
  **Body (JSON):**
  ```json
  {
    "id": 10,
    "nomeItem": "Cadeira de Escritório",
    "idCategoria": 2,
    "idSetor": 1,
    "idStatus": 3,
    "idNota": 3
  }
  ```
  **Exemplo de resposta (200 OK):**
  ```json
  {
    "id": 10,
    "nomeItem": "Cadeira de Escritório",
    "idCategoria": 2,
    "idSetor": 1,
    "idStatus": 3,
    "idNota": 3
  }
---
- **DELETE** `/patrimonio/{id}`  
  Deleta o patrimônio conforme o id informado.

  **Respostas possíveis:**
    - ✅ `204 No Content` → Exclusão realizada com sucesso.
    - ❌ `404 Not Found` → Nenhum patrimônio encontrado com o ID informado.  
---
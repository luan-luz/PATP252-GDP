# PATP252-GDP
Projeto de Aperfeiçoamento Teórico Prático - Gestão de Patrimônios ( Análise e Desenvolvimento de Sistemas 4º Semestre )
## 📌 Rotas da API - Controle de Patrimônio

### Collection no Postman
https://.postman.co/workspace/My-Workspace~b6675b87-cd40-4eff-8973-bbe06d0d9a3a/collection/44013103-ddc3feb1-3a9b-424d-9bc1-b49f25728417?action=share&creator=44013103

### 📂 Patrimônio

### 🔎 Teste de conexão
- **GET** `/patrimonio/teste`  
  Retorna mensagem de verificação da API.
---

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
      "idNota": 4
    }
  }

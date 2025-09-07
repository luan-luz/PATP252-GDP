CREATE TABLE cidade (
  id SERIAL PRIMARY KEY,
  descricao VARCHAR(100),
  uf CHAR(2),
  codigo_ibge INT,
  ddd CHAR(2)
);
CREATE INDEX idx_cidade_id ON cidade(id);
CREATE INDEX idx_cidade_id_uf ON cidade(id, uf);
CREATE INDEX idx_cidade_uf ON cidade(uf);

CREATE TABLE logradouro (
  id SERIAL PRIMARY KEY,
  cep VARCHAR(11) NOT NULL,
  tipo VARCHAR(50),
  descricao VARCHAR(100),
  id_cidade INT NOT NULL,
  descricao_bairro VARCHAR(100),
  FOREIGN KEY (id) REFERENCES cidade(id)
);

CREATE TABLE fornecedores (
    id SERIAL PRIMARY KEY,
    razao_social VARCHAR(150) NOT NULL,
    nome_fantasia VARCHAR(150),
    cnpj CHAR(14) NOT NULL UNIQUE,
    ie VARCHAR(20),
    id_logradouro INT NOT NULL,
    numero VARCHAR(10),
    complemento VARCHAR(30),
    FOREIGN KEY (id) REFERENCES logradouro(id)
);

CREATE TABLE status_item (
    id SERIAL PRIMARY KEY,
    em_uso BOOLEAN DEFAULT FALSE,
    manutencao BOOLEAN DEFAULT FALSE,
    transf BOOLEAN DEFAULT FALSE,
    baixa BOOLEAN DEFAULT FALSE,
    inativo BOOLEAN DEFAULT FALSE
);

CREATE TABLE categorias (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE setores (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE notas_f (
    id SERIAL PRIMARY KEY,
    n_serie VARCHAR(50),
    n_nfe VARCHAR(50),
    dt_aquisicao DATE,
    vlr_total NUMERIC(12,2),
    fornecedor_id INT REFERENCES fornecedores(id)
);

CREATE TABLE patrimonio (
    id SERIAL PRIMARY KEY,
    nome_item VARCHAR(50) NOT NULL,
    categoria_id INT REFERENCES categorias(id),
    setor_id INT REFERENCES setores(id),
    status_id INT REFERENCES status_item(id),
    nota_ref_id INT REFERENCES notas_f(id)
);

CREATE TABLE acessos (
    id SERIAL PRIMARY KEY,
    admin BOOLEAN DEFAULT FALSE,
    consulta BOOLEAN DEFAULT FALSE
);

CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    usuario VARCHAR(20) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    grupo_id INT REFERENCES acessos(id)
);


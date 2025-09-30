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
    nome VARCHAR (50)
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

CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    nome_item VARCHAR(50) NOT NULL,
    categoria_id INT REFERENCES categorias(id)
);

CREATE TABLE patrimonio (
    id SERIAL,
    id_item INTEGER,
    id_setor INTEGER,
    id_status INTEGER,
    id_nota INTEGER,
    num_patr VARCHAR (14),
    val_compra DECIMAL(10, 2),
    aliq_deprec_mes DECIMAL(10, 2),
    dt_aquisicao DATE,
    CONSTRAINT fk_itens_patr foreign key (id_item) REFERENCES item (id),
    CONSTRAINT fk_setor_patr  foreign key (id_setor) REFERENCES setores(id),
    CONSTRAINT fk_status_patr  foreign key (id_status) REFERENCES status_item(id),
    CONSTRAINT fk_nota_patr  foreign key (id_nota) REFERENCES notas_f(id)
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


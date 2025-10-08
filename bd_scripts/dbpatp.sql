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

CREATE TABLE locais (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE notas_f (
    id SERIAL PRIMARY KEY,
    chave_acesso VARCHAR(44),
    n_serie VARCHAR(50),
    n_nfe VARCHAR(50),
    dt_emissao DATE,
    vlr_total NUMERIC(12,2),
    fornecedor_id BIGINT REFERENCES fornecedores(id)
);

CREATE TABLE item (
    id SERIAL PRIMARY KEY,
    nome_item VARCHAR(50) NOT NULL,
    categoria_id BIGINT REFERENCES categorias(id)
);

CREATE TABLE patrimonio (
    id SERIAL,
    id_item BIGINT,
    id_local BIGINT,
    id_status BIGINT,
    id_nota BIGINT,
    num_patr VARCHAR (14),
    val_compra DECIMAL(10, 2),
    aliq_deprec_mes DECIMAL(10, 2),
    dt_aquisicao DATE,
    CONSTRAINT fk_itens_patr foreign key (id_item) REFERENCES item (id),
    CONSTRAINT fk_local_patr  foreign key (id_local) REFERENCES locais(id),
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
    grupo_id BIGINT REFERENCES acessos(id)
);

insert into categorias values (1, 'Notebooks');
insert into categorias values (2, 'Cadeiras');
insert into categorias values (3, 'Computadores');
insert into cidade values (1, 'tres de maio', 'rs', 4321808, 54);
insert into logradouro values (1, 99051310, 'rua', 'rua dos bobos numero 0', 1, 'petropolis');
insert into fornecedores values (1, 'teste", "teste', 123, 07858433000121, 1, 1, 'casa');
insert into notas_f values (1, 12345678901234567890123456789012345678904444, 1, 1, current_timestamp, 1, 1);
insert into locais values (1, 'TI');
insert into locais values (2, 'Recepção');
insert into locais values (3, 'Laboratório');
insert into status_item values (1, 'Ativo');
insert into status_item values (2, 'Em manutenção');
insert into status_item values (3, 'Baixado');
insert into item values (default, 'Notebook Acer Nitro 5 AN517', 1);
insert into item values (default, 'Cadeira de escritório Cavaletti', 2);
insert into patrimonio values (default, 1, 1, 1, 1, 000001, 1200.05, 2.0934, '10/08/2002');
insert into patrimonio values (default, 2, 2, 2, 1, 000002, 200.75, 1.512, '10/08/2025');
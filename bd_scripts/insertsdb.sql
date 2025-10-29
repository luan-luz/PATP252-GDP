-- Itens da categoria 1: Notebooks
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Dell Inspiron 15', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Lenovo IdeaPad 3', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Acer Aspire 5', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook HP Pavilion x360', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Samsung Book Intel i5', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Asus VivoBook 15', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Apple MacBook Air M1', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Dell Latitude 3420', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Lenovo ThinkPad E14', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Acer Nitro 5 AN515', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook HP ProBook 450 G9', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Positivo Motion Q232A', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Samsung Galaxy Book3', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Asus TUF Gaming F15', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Dell XPS 13', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Lenovo Yoga 7i', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Acer Swift 3', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook HP Envy 13', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Apple MacBook Pro 14', 1);
INSERT INTO item (nome_item, categoria_id) VALUES ('Notebook Asus ZenBook 14', 1);

-- Itens da categoria 2: Cadeiras
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira de Escritório Presidente Couro', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Gamer ThunderX3 TGC12', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Ergonômica Cavaletti 16005', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Secretária Giratória Base Cromada', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Gamer Redragon Taurus', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira de Escritório Mesh Preta', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Gamer Pichau Hawk', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira de Escritório Base Estrela', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Presidente em Couro Sintético', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Gamer Husky Blizzard 300', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Ergonômica Flexform Alpha', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Secretária com Braço', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira de Escritório Cavaletti Office', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Gamer Razer Iskur', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Ergonômica Plaxmetal Joy', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira de Escritório com Regulagem de Altura', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Gamer Cooler Master Caliber R3', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Presidente com Apoio Lombar', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira de Escritório Compacta', 2);
INSERT INTO item (nome_item, categoria_id) VALUES ('Cadeira Gamer Vertagear SL5000', 2);

-- Itens da categoria 3: Computadores
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Dell Optiplex 7090', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador HP ProDesk 400 G7', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Lenovo ThinkCentre M70s', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Acer Aspire XC-885', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Gamer Pichau Frost Ryzen 5', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Dell Vostro 3681', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Positivo Master D340', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Asus ExpertCenter D500', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Gamer TGT Ryzen 7 + RTX 4060', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador HP EliteDesk 800 G6', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Compacto Intel NUC 12', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador All in One Lenovo AIO 3i', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Gamer Redragon i7-12700K', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Dell Precision 3660', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Acer Veriton X', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Lenovo V50s Small Form', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Apple Mac Mini M2', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador HP Pavilion Desktop TG01', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Dell XPS Desktop 8950', 3);
INSERT INTO item (nome_item, categoria_id) VALUES ('Computador Positivo Union U10', 3);

-- Inserções de patrimônio simuladas
INSERT INTO patrimonio (id_item, id_local, id_status, id_nota, num_patr, val_compra, aliq_deprec_mes, dt_aquisicao)
VALUES
(1, 1, 1, 1, '000001', 4500.00, 2.10, '2020-03-12'),
(2, 2, 2, 1, '000002', 1200.50, 1.75, '2021-07-18'),
(3, 3, 1, 1, '000003', 3500.99, 2.05, '2019-11-10'),
(4, 1, 3, 1, '000004', 3800.00, 2.00, '2018-02-01'),
(5, 3, 1, 1, '000005', 2800.00, 1.95, '2022-01-25'),
(6, 1, 1, 1, '000006', 3100.75, 2.00, '2020-09-05'),
(7, 2, 2, 1, '000007', 9500.00, 2.50, '2023-03-20'),
(8, 1, 1, 1, '000008', 4800.40, 2.15, '2020-12-10'),
(9, 2, 1, 1, '000009', 4100.00, 1.85, '2021-06-15'),
(10, 3, 3, 1, '000010', 5500.00, 2.20, '2019-09-11'),
(11, 1, 1, 1, '000011', 6200.00, 2.30, '2022-10-05'),
(12, 2, 1, 1, '000012', 1800.00, 1.60, '2021-12-01'),
(13, 1, 2, 1, '000013', 3000.00, 1.80, '2020-04-17'),
(14, 3, 1, 1, '000014', 4200.00, 2.10, '2023-01-03'),
(15, 2, 3, 1, '000015', 7000.00, 2.50, '2018-08-23'),
(16, 1, 1, 1, '000016', 2900.00, 1.90, '2021-09-12'),
(17, 3, 1, 1, '000017', 3100.00, 2.00, '2020-02-14'),
(18, 2, 2, 1, '000018', 2500.00, 1.70, '2023-06-01'),
(19, 1, 1, 1, '000019', 9300.00, 2.80, '2022-02-19'),
(20, 3, 3, 1, '000020', 1500.00, 1.50, '2017-05-07'),
(21, 1, 1, 1, '000021', 950.00, 1.20, '2021-08-11'),
(22, 2, 2, 1, '000022', 1100.00, 1.40, '2020-12-25'),
(23, 3, 1, 1, '000023', 870.00, 1.10, '2019-03-15'),
(24, 1, 1, 1, '000024', 1500.00, 1.60, '2022-01-20'),
(25, 2, 1, 1, '000025', 980.00, 1.30, '2023-05-10'),
(26, 3, 3, 1, '000026', 1350.00, 1.50, '2018-10-28'),
(27, 1, 2, 1, '000027', 890.00, 1.10, '2020-07-01'),
(28, 2, 1, 1, '000028', 1600.00, 1.60, '2021-09-13'),
(29, 3, 2, 1, '000029', 1200.00, 1.40, '2023-02-09'),
(30, 1, 1, 1, '000030', 1400.00, 1.50, '2022-11-11'),
(31, 2, 1, 1, '000031', 3500.00, 1.90, '2020-03-22'),
(32, 3, 2, 1, '000032', 4100.00, 2.10, '2023-04-04'),
(33, 1, 3, 1, '000033', 3200.00, 2.00, '2019-07-07'),
(34, 2, 1, 1, '000034', 3800.00, 1.85, '2021-10-15'),
(35, 3, 1, 1, '000035', 2500.00, 1.70, '2020-08-08'),
(36, 1, 2, 1, '000036', 2800.00, 1.80, '2023-01-14'),
(37, 2, 1, 1, '000037', 4600.00, 2.10, '2022-05-27'),
(38, 3, 1, 1, '000038', 4900.00, 2.20, '2020-12-19'),
(39, 1, 3, 1, '000039', 5100.00, 2.25, '2018-04-30'),
(40, 2, 1, 1, '000040', 4700.00, 2.10, '2021-06-09'),
(41, 3, 1, 1, '000041', 5300.00, 2.30, '2019-10-20'),
(42, 1, 2, 1, '000042', 6000.00, 2.40, '2022-08-18'),
(43, 2, 3, 1, '000043', 7200.00, 2.50, '2017-12-25'),
(44, 3, 1, 1, '000044', 6900.00, 2.35, '2020-02-10'),
(45, 1, 1, 1, '000045', 8800.00, 2.60, '2023-03-03'),
(46, 2, 1, 1, '000046', 9100.00, 2.70, '2021-05-05'),
(47, 3, 2, 1, '000047', 9700.00, 2.80, '2022-09-09'),
(48, 1, 1, 1, '000048', 10200.00, 3.00, '2020-01-15'),
(49, 2, 1, 1, '000049', 7500.00, 2.40, '2019-11-11'),
(50, 3, 3, 1, '000050', 6400.00, 2.20, '2018-08-08'),
(51, 1, 1, 1, '000051', 5200.00, 2.10, '2023-02-17'),
(52, 2, 2, 1, '000052', 4300.00, 1.90, '2022-12-30'),
(53, 3, 1, 1, '000053', 3900.00, 1.85, '2020-09-19'),
(54, 1, 1, 1, '000054', 3700.00, 1.80, '2021-01-01'),
(55, 2, 3, 1, '000055', 3000.00, 1.70, '2018-05-09'),
(56, 3, 1, 1, '000056', 2700.00, 1.60, '2023-07-07'),
(57, 1, 2, 1, '000057', 2300.00, 1.50, '2021-08-21'),
(58, 2, 1, 1, '000058', 1950.00, 1.40, '2020-10-12'),
(59, 3, 2, 1, '000059', 2600.00, 1.60, '2023-03-23'),
(60, 1, 1, 1, '000060', 2900.00, 1.80, '2022-04-17');


select * from patrimonio;
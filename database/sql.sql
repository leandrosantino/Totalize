-- Criação da tabela Produtos


create table product (
 product_id integer primary key autoincrement,
 barcode integer(13) not null unique,
 description varchar(35) not null,
 price int not null);

INSERT INTO product (barcode, description, price) 
VALUES 
(1234567890128, 'Sample Product 1', 1999),
(2345678901234, 'Sample Product 2', 2999),
(3456789012345, 'Sample Product 3', 3999);



--Criação da tabela compras


create table purchase (
    purchase_id integer primary key autoincrement,
    CPF char(11),
    total_price integer not null,
    purchase_date text not null
);



INSERT INTO purchase (CPF, total_price, purchase_date) 
VALUES 
('12345678901', 4999, '2024-09-10'),
('98765432100', 2999, '2024-09-11');


-- Criação da tabela de produtos comprados


CREATE TABLE purchased_products (
    product_id INTEGER,
    purchase_id INTEGER,
    amount INTEGER NOT NULL,
    PRIMARY KEY (product_id, purchase_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    FOREIGN KEY (purchase_id) REFERENCES purchase(purchase_id) ON DELETE CASCADE
);



INSERT INTO purchased_products (product_id, purchase_id, amount) 
VALUES 
(1, 1, 2),
(2, 1, 1),
(3, 2, 1);


-- Exemplo de como os comandos terão que ser no caixa


BEGIN TRANSACTION;

INSERT INTO product (barcode, description, price) 
VALUES 
(1234567890129, 'Sample Product 4', 1999);

INSERT INTO purchase (CPF, total_price, purchase_date) 
VALUES 
('12345678901', 1999, '2024-09-10');


INSERT INTO purchased_products (product_id, purchase_id, amount) 
VALUES 
(4, 4, 4);

COMMIT;

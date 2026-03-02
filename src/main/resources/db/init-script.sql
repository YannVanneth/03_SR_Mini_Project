CREATE DATABASE StockManagementDB;

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    unit_price NUMERIC(10, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    imported_date DATE NOT NULL
);

INSERT INTO products(name, unit_price, quantity, imported_date)
VALUES('Grape', 1.6, 110, NOW()),
('Banana',0.5,150,NOW()),
('Orange',1.0, 120,NOW());

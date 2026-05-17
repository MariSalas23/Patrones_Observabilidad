CREATE TABLE comidas (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100),
    calorias INT
);

INSERT INTO comidas(nombre, calorias)
VALUES
('Hamburguesa', 800),
('Pizza', 1200),
('Ensalada', 300);
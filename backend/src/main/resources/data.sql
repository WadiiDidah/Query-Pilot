INSERT INTO customers (name, city)
SELECT 'Alice Martin', 'Paris'
WHERE NOT EXISTS (SELECT 1 FROM customers WHERE name = 'Alice Martin');

INSERT INTO customers (name, city)
SELECT 'Lucas Bernard', 'Lyon'
WHERE NOT EXISTS (SELECT 1 FROM customers WHERE name = 'Lucas Bernard');

INSERT INTO customers (name, city)
SELECT 'Sofia Durand', 'Marseille'
WHERE NOT EXISTS (SELECT 1 FROM customers WHERE name = 'Sofia Durand');

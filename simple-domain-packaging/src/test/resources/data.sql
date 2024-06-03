select nextval('sequence_generator');
INSERT INTO customer (version, id, email, username) VALUES (1, 1, 'username', 'username@example.com');
INSERT INTO address (version, id, city, state, street, type, zip) VALUES (1, 1, 'Borricon de Arriba', 'State', '13, Rue del Percebe', 'HOME', 'Zip');

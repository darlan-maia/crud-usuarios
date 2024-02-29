INSERT INTO
    tb_usuarios (username, password, first_name, last_name, email, telefones)
VALUES
    ('joao', '$2a$12$5kKu5bRxSkJa/lHF3CwtmODy2UhTV3CX6LD.JtOrqgkSke5vOCELS', 'João', 'Silva', 'joao@email.com', ARRAY ['71999999999#CELULAR']),
    ('maria', '$2a$12$qB6C.g35Ny93FnwckMaOku9hcdN2ZtlmjjzRM4oFM6YNWLED6I8TK', 'Maria', 'Souza', 'maria@email.com', ARRAY ['71988888888#CELULAR', '7133333333#RESIDENCIAL']);
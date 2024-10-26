CREATE TABLE IF NOT EXISTS "usuario" (
    "id" BIGINT PRIMARY KEY,
    "perfil" VARCHAR(255),
    "data_nascimento" DATE,
    "cpf" VARCHAR(11),
    "email" VARCHAR(255),
    "nome" VARCHAR(255),
    "password" VARCHAR(255)
);
INSERT INTO "usuario" ("perfil", "data_nascimento", "cpf", "email", "nome", "password") VALUES ('1', '2000-08-12', '12345678910', 'teste@teste.com', 'Usuário de Testes', '$2a$12$kn9RMO2pcOCYONZcQES2ROLfduIPWsHBnPNF1eyDacCR1VegYs7ku');
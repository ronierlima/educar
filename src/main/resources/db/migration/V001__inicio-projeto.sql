CREATE TABLE pessoa
(
    id              SERIAL PRIMARY KEY,
    nome            VARCHAR(255),
    data_nascimento DATE
);

CREATE TABLE usuario
(
    id        SERIAL PRIMARY KEY,
    login     VARCHAR(255),
    email     VARCHAR(255),
    senha     VARCHAR(255),
    pessoa_id INT REFERENCES pessoa (id),
    unique (email),
    unique (login)
);

CREATE TABLE papel
(
    id         SERIAL PRIMARY KEY,
    nome_papel VARCHAR(255),
    descricao  VARCHAR(255)
);

CREATE TABLE usuario_papel
(
    id         SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuario (id),
    papel_id   INT REFERENCES papel (id)
);

CREATE TABLE curso
(
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(255),
    semestres INT,
    descricao TEXT
);

CREATE TABLE matriz
(
    id        SERIAL PRIMARY KEY,
    curso_id  INT REFERENCES curso (id),
    ano       INT,
    descricao TEXT
);

CREATE TABLE disciplina
(
    id            SERIAL PRIMARY KEY,
    nome          VARCHAR(255),
    descricao     TEXT,
    carga_horaria INT
);

CREATE TABLE aluno
(
    id        SERIAL PRIMARY KEY,
    pessoa_id INT REFERENCES pessoa (id)
);

CREATE TABLE matricula
(
    id             SERIAL PRIMARY KEY,
    aluno_id       INT REFERENCES aluno (id),
    curso_id       INT REFERENCES curso (id),
    data_inicio    DATE,
    data_conclusao DATE
);

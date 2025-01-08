CREATE TABLE curso (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    curso ENUM('CATEGORIA_1', 'CATEGORIA_2', 'CATEGORIA_3') NOT NULL, -- ajuste para suas categorias
    nome VARCHAR(255) NOT NULL
);

CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE Topicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    dataCriacao DATETIME NOT NULL,
    estado ENUM('ATIVO', 'INATIVO') NOT NULL,  -- Ajuste para os estados possíveis do Topico
    curso_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (curso_id) REFERENCES curso(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE respostas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mensagem TEXT NOT NULL,
    dataCriacao DATETIME NOT NULL,
    topico_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (topico_id) REFERENCES Topicos(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

USE recruitment_db;

CREATE TABLE candidate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    gender ENUM('MASCULINO', 'FEMENINO'),
    salary_expected DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO candidate (name, email, gender, salary_expected) VALUES
('Deyvis Garcia', 'deyvis@gmail.com', 'MASCULINO', 50000),
('Ronald Garcia', 'ronald@gmail.com', 'MASCULINO', 60000),
('Lesly Rocio', 'lesly@gmail.com', 'FEMENINO', 45000);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(300) NOT NULL,
    role VARCHAR(20) NOT NULL
);

INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$GtvDJVXLkb483Cytpm2iE./zmsA2AZJ2c1WzE7njN7n8yqrJuRS2S', 'ADMIN'), -- Contraseña: 123456
('user', '$2a$10$GtvDJVXLkb483Cytpm2iE./zmsA2AZJ2c1WzE7njN7n8yqrJuRS2S', 'USER'); -- Contraseña: 123456
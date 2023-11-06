# Educar API

## Introdução

Este é o repositório da API do projeto Educar, um projeto realizado como parte do desafio técnico.

## Tecnologias

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT (JSON Web Tokens)

## Requisitos de Instalação

1. Clone o repositório:

   ```shell
   git clone https://github.com/ronierlima/educar-api.git

   cd educar-api
   ```

2. Configure seu banco de dados no arquivo `src/main/resources/application.properties`.

3. Instale as dependências e compile o projeto:

   ```shell
   ./mvnw clean install
   ```

## Executando o Projeto

1. Inicie o servidor:

   ```shell
   ./mvnw spring-boot:run
   ```

2. A API estará disponível em `http://localhost:8080`.

## Deploy com Docker

Para implantar a API Educar usando Docker, siga estas etapas:

1. Certifique-se de que o Docker esteja instalado na sua máquina.

2. Crie uma imagem Docker da API executando o seguinte comando na raiz do projeto:

   ```shell
   docker build -t educar-api:latest .
   ```

3. Execute um contêiner Docker com a imagem criada:

   ```shell
   docker run -p 8080:8080 educar-api:latest
   ```

4. A API estará disponível em `http://localhost:8080`.


## Contato

- [Ronier Lima](https://ronierlima.dev)
- eu@ronierlima.dev
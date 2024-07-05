# CRUD com Spring + JWT + Docker
App que cria, edita e deleta notas usando Spring Data JPA, cada nota recebe uma categoria, que pode ser criada também, autenticação JWT com Spring Security, Docker com PostgreSQL para banco de dados, testes unitários e
possuí interfaces usando Thymeleaf para renderizar dados do servidor.

### Objetivo
Aplicar a um projeto o que venho aprendendo sobre Java, Spring Boot, Docker, JWT, testes unitários e bancos de dados. 

## Tecnologias

- Java
- JUnit
- Mockito
- Docker
- Spring Boot
- Spring Security
- Spring Data JPA
- Autenticação JWT
- PostgreSQL
- Thymeleaf
- HTML, CSS, JS

## Instalação

### Requisitos
- JDK 17
- Maven
- Docker
- PostgreSQL

### Iniciando aplicação

1) clone o repositório:
```bash
git clone https://github.com/Gabriel-sy/CRUD-spring-JWT.git
```
2) Inicie a aplicação com o docker:
```bash
cd CRUD-spring-JWT
docker-compose build
docker-compose up
```
A aplicação vai estar rodando na porta 8080, acesse:
```bash
localhost:8080/auth/register
```
Após registrar, você será redirecionado para a página de login.

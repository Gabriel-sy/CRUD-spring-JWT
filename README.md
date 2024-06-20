# CRUD com Spring + JWT + Docker
App que cria, edita e deleta notas usando Spring Data JPA, cada nota recebe uma categoria, que pode ser criada também, autenticação JWT com Spring Security, 
possuí interfaces usando Thymeleaf para renderizar dados do servidor, o código também tem exceções personalizadas e testes unitários.

### Objetivo
Aplicar a um projeto o que venho aprendendo sobre Spring Boot, Docker, testes unitários e bancos de dados. 

## Tecnologias

- Java
- JUnit
- Mockito
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

### Iniciando aplicação

Primeiro, clone o repositório:
```bash
git clone https://github.com/Gabriel-sy/CRUD-spring-JWT.git
```
Inicie a aplicação com o docker:
```bash
cd CRUD-spring-JWT
docker-compose up
```
A aplicação vai estar rodando na porta 8080, para registrar, acesse:
```bash
localhost:8080/auth/register
```
Após registrar, você será redirecionado para a página de login.
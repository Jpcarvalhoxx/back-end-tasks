# Sistema de Gerenciamento de Tarefas

Este projeto é uma API RESTful para um sistema de gerenciamento de tarefas, construído com **Spring Boot**. O objetivo é criar uma aplicação robusta e segura, utilizando as melhores práticas de desenvolvimento, como a arquitetura em camadas e o uso de DTOs para a comunicação.

---

## Tecnologias Utilizadas

O projeto foi desenvolvido com as seguintes tecnologias e frameworks:

* **Java 17**: Linguagem de programação principal.
* **Spring Boot**: Framework para simplificar a criação da API.
* **Spring Data JPA**: Para a camada de persistência de dados.
* **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
* **Lombok**: Biblioteca que reduz o código boilerplate (getters, setters, etc.).
* **Spring Security**: Para proteger a API e gerenciar a autenticação e autorização.
* **BCrypt**: Algoritmo de criptografia para senhas, utilizado pelo Spring Security.
* **Maven**: Ferramenta de automação de construção e gerenciamento de dependências.
* **Validação (JSR 380)**: Para garantir a integridade dos dados de entrada.

---

## Funcionalidades Implementadas

Até o momento, a API possui as seguintes funcionalidades:

* **Gerenciamento de Usuários**:
    * Cadastro de novos usuários.
    * Criptografia segura de senhas com BCrypt.
    * Validação de campos (e-mail, nome, senha).
* **API RESTful**:
    * Uso de DTOs para padronizar a entrada e saída de dados.
* **Segurança**:
    * Configuração do Spring Security para proteger todos os endpoints.
    * Configuração de CORS para permitir requisições de diferentes origens.
    * Liberação de endpoints públicos (como o de cadastro) na configuração de segurança.
* **Gerenciamento de Tarefas**:
    * Entidade para tarefas com status e tipo pré-definidos (Enums).
    * Relacionamento `One-to-Many` entre `User` e `Task`.

---

## Arquitetura do Projeto

O projeto segue a arquitetura em camadas, o que garante a separação de responsabilidades e facilita a manutenção.

* `controller`: Responsável por receber as requisições HTTP e roteá-las para a camada de serviço.
* `service`: Contém a lógica de negócio e orquestra a comunicação entre o `controller` e o `repository`.
* `repository`: Interage diretamente com o banco de dados, usando o Spring Data JPA para executar as operações de CRUD.
* `entity`: Representa as tabelas do banco de dados (modelos).
* `dto`: Objetos de transferência de dados que definem o contrato da API.
* `security` e `config`: Camadas para as configurações de segurança, incluindo o `PasswordEncoder` e as regras de autorização.

---

## Como Começar

### Pré-requisitos

* Java 17 ou superior
* Maven
* Banco de dados PostgreSQL
* Um editor de código (VS Code, IntelliJ IDEA, Eclipse)

### Configuração do Banco de Dados

1.  Certifique-se de que o seu banco de dados PostgreSQL está em execução.
2.  Crie um banco de dados e ajuste a sua configuração no arquivo `application.properties`.
3.  Crie o arquivo `.env` na raiz do projeto e adicione as suas credenciais, como `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`.
4.  Certifique-se de que o `.env` está no seu `.gitignore` para não ser enviado ao GitHub.

### Executando a Aplicação

Para iniciar a aplicação, utilize o Maven:

```bash
mvn spring-boot:run
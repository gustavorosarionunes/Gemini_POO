# 📚 Recomendador de Livros (Spring Boot + Gemini)

Um projeto académico de aplicação web full-stack que recomenda livros com base no sentimento do usuário. O projeto utiliza o Spring Boot para o backend e integra-se diretamente com a API do Google Gemini para a funcionalidade de IA.

## 🎯 Requisitos do Projeto
Este projeto foi construído para cumprir os seguintes requisitos académicos:

- **Uso do framework Spring:** (✅ Cumprido)  
  O projeto é construído sobre o Spring Boot, utilizando Spring Web (para controllers), Spring Data (para o banco de dados) e Injeção de Dependências.

- **Telas webs com Thymeleaf:** (✅ Cumprido)  
  O frontend (`index.html` e `historico.html`) é servido pelo Spring Boot usando Thymeleaf através do `PageController`.

- **Persistência de dados com Spring Data + banco de dados relacional:** (✅ Cumprido)  
  Usamos Spring Data JPA e um banco de dados H2 em memória.  
  A entidade `Recommendation.java` é usada para salvar cada pergunta (prompt) e resposta (response) da IA no banco de dados.  
  A nova página `/historico` lê e exibe todos os registos do banco, demonstrando a persistência de dados.

- **Funcionalidade inteligente com integração de IA Generativa (Gemini):** (✅ Cumprido)  
  O `GeminiService.java` chama a API do Google Gemini.  
  Ele usa Engenharia de Prompt para instruir a IA a agir como um recomendador de livros em português.

- **Spring AI:** (⚠️ Não Cumprido - Decisão de Design)  
  Nota: A biblioteca Spring AI não foi utilizada. Esta foi uma decisão de design para evitar instabilidades de dependência (Dependency not found) encontradas com as bibliotecas milestone.  
  **Solução Alternativa:** A integração com o Gemini foi implementada manualmente usando o `RestTemplate` do Spring (uma ferramenta HTTP padrão). Este método cumpre o requisito de integração com IA de forma robusta.

## 💻 Tecnologias Utilizadas

**Backend**
- Java 17
- Spring Boot 3.3.5
- Spring Web (`RestTemplate`)
- Spring Data JPA
- H2 Database (Em memória)

**Frontend**
- Thymeleaf
- HTML5
- CSS3
- JavaScript (Fetch API / AJAX)

**APIs & Build**
- Google Gemini API (chamada REST direta)
- Maven

## ⚙️ Configuração (Como Rodar)

### 1. Pré-requisitos
- Java JDK 17
- Apache Maven

### 2. Chave de API do Google Gemini
1. Vá ao Google AI Studio e crie uma nova chave de API.
2. Copie a chave.

### 3. Ficheiro `application.properties`
Abra o ficheiro `src/main/resources/application.properties` e configure-o da seguinte forma (substituindo a sua chave):

```properties
# =========================================
# CHAVE DA API (MÉTODO MANUAL)
# =========================================
google.api.key=SUA_CHAVE_API_VAI_AQUI

# =========================================
# CONFIGURAÇÃO DO BANCO DE DADOS (H2)
# =========================================
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
# Define um banco de dados em memória chamado 'bookdb'
spring.datasource.url=jdbc:h2:mem:bookdb
spring.datasource.username=sa
spring.datasource.password=
# Cria/atualiza o schema do banco de dados automaticamente
spring.jpa.hibernate.ddl-auto=update
# Mostra o SQL no console
spring.jpa.show-sql=true
```

## 🚀 Como Executar

Existem duas formas fáceis de executar:

**Opção A: Pela sua IDE (IntelliJ)**  
- Recarregue o Maven: Clique com o botão direito no `pom.xml` > Maven > Reload project.  
- Execute: Encontre o ficheiro `BookRecommenderApplication.java`, clique com o botão direito e selecione "Run...".

**Opção B: Pelo Terminal (Maven)**  
Abra um terminal na pasta raiz do projeto (onde está o `pom.xml`) e execute:

```bash
mvn spring-boot:run
```

## 🚀 Como Usar a Aplicação

Após a aplicação arrancar, abra o seu navegador:

- Aceda a `http://localhost:8080/`

Na caixa de texto **"Como você está se sentindo?"**, digite um sentimento, um género ou uma ideia. Por exemplo:

- `feliz`
- `triste`
- `otimista`
- `stressado`
- `procurando uma aventura`
- `precisando de uma boa gargalhada`
- `querendo um mistério complexo`
- `um romance leve`
- `uma ficção científica que faça pensar`
- `algo para me inspirar`

Clique no botão **"Gerar Recomendação"**.

### Para ver o Histórico (Requisito Spring Data)
Na página inicial, clique no link **"Ver histórico de solicitações"** (ou aceda diretamente a `http://localhost:8080/historico`).  
Esta página irá mostrar uma tabela com todas as perguntas e respostas que foram salvas no banco de dados H2.

### Para ver o Banco de Dados (Opcional)
Aceda a `http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:bookdb`  
- **User Name:** `sa`  
- **Password:** (deixe em branco)

Clique em "Connect". Você poderá ver a tabela `RECOMMENDATION` com os dados.

---




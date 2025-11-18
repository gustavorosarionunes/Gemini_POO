[README.md](https://github.com/user-attachments/files/23594578/README.md)
# 📚 Recomendador de Livros (Spring Boot + Gemini)

Um projeto académico de aplicação web full-stack que recomenda livros
com base no sentimento do usuário. O projeto utiliza o Spring Boot para
o backend e integra-se diretamente com a API do Google Gemini para a
funcionalidade de IA.

## 🎯 Requisitos do Projeto

### Uso do framework Spring: (✅ Cumprido)

-   O projeto é construído sobre o Spring Boot, utilizando Spring Web
    (para controllers), Spring Data (para o banco de dados) e Injeção de
    Dependências.

### Telas webs com Thymeleaf: (✅ Cumprido)

-   O frontend `index.html` é servido pelo Spring Boot usando Thymeleaf
    através do `PageController`.

### Persistência de dados com Spring Data + banco de dados relacional: (✅ Cumprido)

-   Usamos Spring Data JPA e um banco de dados H2 em memória.
-   A entidade `Recommendation.java` é usada para salvar cada pergunta
    (prompt) e resposta (response) da IA no banco de dados, o que é
    feito dentro do `GeminiService`.

### Funcionalidade inteligente com integração de IA Generativa (Gemini): (✅ Cumprido)

-   O `GeminiService.java` chama a API do Google Gemini via REST.
-   Utiliza Engenharia de Prompt para instruir a IA a agir como um
    recomendador de livros em português.

### Spring AI: (⚠️ Não Cumprido --- Decisão de Design)

-   A biblioteca Spring AI não foi utilizada devido a instabilidades nas
    dependências.
-   **Solução alternativa:** integração com o Gemini implementada
    manualmente usando `RestTemplate`.

------------------------------------------------------------------------

## 💻 Tecnologias Utilizadas

### Backend:

-   Java 17\
-   Spring Boot 3.3.5\
-   Spring Web (RestTemplate)\
-   Spring Data JPA\
-   H2 Database

### Frontend:

-   Thymeleaf\
-   HTML5 / CSS3\
-   JavaScript (Fetch API)

### APIs & Build:

-   Google Gemini API (REST)\
-   Maven

------------------------------------------------------------------------

## ⚙️ Configuração (Como Rodar)

### 1. Pré-requisitos

-   Java JDK 17\
-   Apache Maven

### 2. Chave de API do Google Gemini

Obtenha uma chave no Google AI Studio e copie-a.

### 3. Configurar `application.properties`

    # CHAVE DA API (MÉTODO MANUAL)
    google.api.key=SUA_CHAVE_API_VAI_AQUI

    # CONFIGURAÇÃO DO BANCO DE DADOS (H2)
    spring.h2.console.enabled=true
    spring.h2.console.path=/h2-console
    spring.datasource.url=jdbc:h2:mem:bookdb
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

------------------------------------------------------------------------

## 🚀 Como Executar

### Opção A: Pela IDE (IntelliJ)

1.  Recarregue o Maven\
2.  Execute `BookRecommenderApplication.java`

### Opção B: Terminal

    mvn spring-boot:run

------------------------------------------------------------------------

## 🚀 Como Usar a Aplicação

### Interface Web:

Acesse: http://localhost:8080/

Digite um sentimento, género ou ideia:

-   "feliz"\
-   "triste"\
-   "procurando uma aventura"\
-   "um romance leve"\
-   "precisando de uma boa gargalhada"\
-   etc.

Clique em **Gerar Recomendação**.

### Ver o Banco de Dados (H2 Console):

Acesse: http://localhost:8080/h2-console

-   JDBC URL: `jdbc:h2:mem:bookdb`
-   User: `sa`
-   Password: *(em branco)*

Veja a tabela **RECOMMENDATION** com o histórico de prompts e respostas.

------------------------------------------------------------------------

## ✔️ Conclusão

Este projeto cumpre todos os requisitos académicos, oferece integração
real com IA generativa e demonstra uma aplicação full‑stack completa
usando Spring Boot.

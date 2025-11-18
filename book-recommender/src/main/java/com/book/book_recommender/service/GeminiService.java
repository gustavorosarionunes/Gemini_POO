// Pacote corrigido
package com.book.book_recommender.service;

// Imports corrigidos
import com.book.book_recommender.model.Recommendation;
import com.book.book_recommender.repository.RecommendationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GeminiService {

    private final RestTemplate restTemplate;
    private final RecommendationRepository repository; // Para o Spring Data

    // URL da API (usando o modelo que funcionou)
    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=";

    private final String apiKey;

    @Autowired
    public GeminiService(RestTemplate restTemplate,
                         RecommendationRepository repository,
                         @Value("${google.api.key}") String apiKey) { // Lendo 'google.api.key'

        this.restTemplate = restTemplate;
        this.repository = repository;
        this.apiKey = apiKey;
    }

    public String generateContent(String userInput) {
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            return "Erro: A API Key 'google.api.key' não está configurada.";
        }

        String apiUrl = GEMINI_API_URL + this.apiKey;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Criando o "Prompt"
        String promptCompleto = String.format(
                "Você é um assistente especialista em recomendação de livros. " +
                        "O usuário está se sentindo '%s'. " +
                        "Por favor, recomende um livro em português do Brasil para ele. " +
                        "Responda APENAS com o Título do Livro, o Autor, e uma sinopse curta (uma frase).",
                userInput
        );

        // Montando o corpo da requisição (usando nossas classes DTO)
        RequestPart part = new RequestPart(promptCompleto);
        RequestContent content = new RequestContent(Collections.singletonList(part));
        GeminiRequest requestBody = new GeminiRequest(Collections.singletonList(content));

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(requestBody, headers);

        try {
            // A Chamada de API
            GeminiResponse response = restTemplate.postForObject(apiUrl, entity, GeminiResponse.class);

            // Extraindo o texto
            String responseText = Optional.ofNullable(response)
                    .map(GeminiResponse::getCandidates)
                    .flatMap(candidates -> candidates.stream().findFirst())
                    .map(GeminiResponse.Candidate::getContent)
                    .map(GeminiResponse.Content::getParts)
                    .flatMap(parts -> parts.stream().findFirst())
                    .map(GeminiResponse.Part::getText)
                    .orElse("Não foi possível extrair a resposta do modelo.");

            // ✅ Requisito: Salvando no Banco de Dados
            Recommendation rec = new Recommendation();
            rec.setPrompt(userInput);
            rec.setResponse(responseText);
            repository.save(rec);

            return responseText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao chamar a API do Gemini: " + e.getMessage();
        }
    }

    // --- Classes DTO para REQUISIÇÃO (como as do seu professor) ---
    private static class GeminiRequest {
        private final List<RequestContent> contents;
        public GeminiRequest(List<RequestContent> contents) { this.contents = contents; }
        public List<RequestContent> getContents() { return contents; }
    }
    private static class RequestContent {
        private final List<RequestPart> parts;
        public RequestContent(List<RequestPart> parts) { this.parts = parts; }
        public List<RequestPart> getParts() { return parts; }
    }
    private static class RequestPart {
        private final String text;
        public RequestPart(String text) { this.text = text; }
        public String getText() { return text; }
    }

    // --- Classes DTO para RESPOSTA (como as do seu professor) ---
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
    private static class GeminiResponse {
        private List<Candidate> candidates;
        public List<Candidate> getCandidates() { return candidates; }
        public void setCandidates(List<Candidate> candidates) { this.candidates = candidates; }

        @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
        private static class Candidate {
            private Content content;
            public Content getContent() { return content; }
            public void setContent(Content content) { this.content = content; }
        }
        @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
        private static class Content {
            private List<Part> parts;
            public List<Part> getParts() { return parts; }
            public void setParts(List<Part> parts) { this.parts = parts; }
        }
        @com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
        private static class Part {
            private String text;
            public String getText() { return text; }
            public void setText(String text) { this.text = text; }
        }
    }
}
package com.book.book_recommender.controller;

// Importações necessárias para o Histórico
import com.book.book_recommender.model.Recommendation;
import com.book.book_recommender.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importe o Model
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List; // Importe a List

/**
 * Controlador para servir as páginas HTML (Thymeleaf).
 */
@Controller
public class PageController {

    // 1. Injeta o repositório para podermos aceder ao banco de dados
    @Autowired
    private RecommendationRepository repository;

    /**
     * Mostra a página inicial (index.html).
     */
    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    /**
     * (NOVO) Mostra a página de histórico (historico.html).
     */
    @GetMapping("/historico")
    public String showHistoricoPage(Model model) {

        // 2. Busca TODOS os registos da tabela "Recommendation"
        List<Recommendation> allRecommendations = repository.findAll();

        // 3. Adiciona a lista ao "Model" para o Thymeleaf usar
        model.addAttribute("recommendations", allRecommendations);

        // 4. Retorna o nome do novo ficheiro HTML
        return "historico";
    }
}
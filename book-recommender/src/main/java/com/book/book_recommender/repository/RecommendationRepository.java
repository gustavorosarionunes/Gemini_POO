package com.book.book_recommender.repository;
import com.book.book_recommender.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
}
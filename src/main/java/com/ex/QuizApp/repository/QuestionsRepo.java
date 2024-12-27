package com.ex.QuizApp.repository;

import com.ex.QuizApp.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionsRepo extends JpaRepository<Question, Integer>{

    List<Question> findByCategory(String category);


    @Query(value = "SELECT * FROM question g WHERE g.category= :category ORDER BY RAND() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(@Param("category") String category, @Param("numQ") int numQ);
}

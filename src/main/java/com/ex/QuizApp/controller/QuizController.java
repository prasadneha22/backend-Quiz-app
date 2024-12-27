package com.ex.QuizApp.controller;

import com.ex.QuizApp.entity.Question;
import com.ex.QuizApp.entity.QuestionWrapper;
import com.ex.QuizApp.entity.Quiz;
import com.ex.QuizApp.entity.Response;
import com.ex.QuizApp.repository.QuestionsRepo;
import com.ex.QuizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){

        return quizService.createQuiz(category,numQ,title);

    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){

        return quizService.getQuizQuestions(id);



    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @ResponseBody List<Response> responses){
        return quizService.calculateResult(id,responses);
    }
}

package com.ex.QuizApp.controller;

import com.ex.QuizApp.entity.Question;
import com.ex.QuizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("all")
    public ResponseEntity<List<Question> >getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Integer id){

        return questionService.getQuestionById(id);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable Integer id){
        questionService.deleteQuestion(id);
    }

    @PutMapping("/update/{id}")
    public Question updateQuestion(@PathVariable Integer id, @RequestBody Question question){
        return questionService.updateQuestion(id,question);
    }





}

package com.ex.QuizApp.service;

import com.ex.QuizApp.entity.Question;
import com.ex.QuizApp.repository.QuestionsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class QuestionService {

    @Autowired
    private QuestionsRepo questionsRepo;

    public  ResponseEntity<List<Question>> getQuestionsByCategory(String category) {

        try{
            return new ResponseEntity<>(questionsRepo.findByCategory(category),HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<List<Question>> getAllQuestions(){

        try{
            return new ResponseEntity<>(questionsRepo.findAll(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();

        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }


    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionsRepo.save(question);
            return new ResponseEntity<>("Success",HttpStatus.CREATED);

        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to add Question: " , HttpStatus.BAD_REQUEST);

    }

    public Question getQuestionById(Integer id) {
        Optional<Question> question = questionsRepo.findById(id);
        if(question.isPresent()){
            return question.get();
        }else{
            throw new RuntimeException("Question with ID" + id + " not found");
        }
    }

    public void deleteQuestion(Integer id) {
        questionsRepo.deleteById(id);
    }


    public Question updateQuestion(Integer id, Question question) {

        Question existingQuestion = getQuestionById(id);

        existingQuestion.setQuestion(question.getQuestion());
        existingQuestion.setOption1(question.getOption1());
        existingQuestion.setOption2(question.getOption2());
        existingQuestion.setOption4(question.getOption4());
        existingQuestion.setCategory(question.getCategory());
        existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
        existingQuestion.setRightAnswer(question.getRightAnswer());

        return questionsRepo.save(existingQuestion);

    }
}

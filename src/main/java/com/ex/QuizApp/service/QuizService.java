package com.ex.QuizApp.service;

import com.ex.QuizApp.entity.Question;
import com.ex.QuizApp.entity.QuestionWrapper;
import com.ex.QuizApp.entity.Quiz;
import com.ex.QuizApp.entity.Response;
import com.ex.QuizApp.repository.QuestionsRepo;
import com.ex.QuizApp.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuestionsRepo questionsRepo;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionsRepo.findRandomQuestionByCategory(category, numQ);

        if(questions == null || questions.isEmpty()){
            return new ResponseEntity<>("No questions found for this category",HttpStatus.BAD_REQUEST);
        }

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionList(questions);

        try{
            quizRepo.save(quiz);
            return new ResponseEntity<>("Quiz created successfully",HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred",HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

         Optional<Quiz> quiz =  quizRepo.findById(id);
         List<Question> questionFromDb = quiz.get().getQuestionList();

         List<QuestionWrapper> questionForList = new ArrayList<>();

         for(Question q:questionFromDb){
             QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestion(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
             questionForList.add(qw);
         }

         return new ResponseEntity<>(questionForList,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizRepo.findById(id).get();
        List<Question> questions = quiz.getQuestionList();
        int right = 0;
        int i=0;

        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;

            i++;

        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}

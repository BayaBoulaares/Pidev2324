package edu.esprit.tests;

import edu.esprit.entities.Question;
import edu.esprit.entities.Reponse;
import edu.esprit.services.QuestionService;
import edu.esprit.services.ReponseService;
import edu.esprit.utils.DataSource;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();
        QuestionService questionService = new QuestionService();
        ReponseService reponseService = new ReponseService();

        // Add a new question
        Question newQuestion = new Question("What is the capital of France?", "london");
        //questionService.add(newQuestion);

        // Update an existing question
        int questionIdToUpdate = 6;
        Question questionToUpdate = new Question( "What is the capital of France?", "Marseille");
        //questionService.update(questionToUpdate);

        // Delete a question
        int questionIdToDelete = 7;
        //questionService.delete(questionIdToDelete);

        // Retrieve a question by ID
        int questionIdToRetrieve = 6;
        Question retrievedQuestion = questionService.getByIdq(questionIdToRetrieve);
        //System.out.println("Retrieved question by ID " + questionIdToRetrieve + ": " + retrievedQuestion);

        // Retrieve all questions
        HashSet<Question> allQuestions = questionService.getAll();
        System.out.println("All questions:");
        for (Question question : allQuestions) {
            System.out.println(question);
        }
//*******************************************************************
        // Add a new response
        Reponse newResponse = new Reponse(5, "Paris", "Nice");
        reponseService.add(newResponse);

        // Update an existing response
        int responseIdToUpdate = 5;
        Reponse responseToUpdate = new Reponse(5, "Paris", "Lyon");
        //reponseService.update(responseToUpdate);

        // Delete a response
        int responseIdToDelete = 4;
        //reponseService.delete(responseIdToDelete);

        // Retrieve a response by ID
        int responseIdToRetrieve = 4;
        //Reponse retrievedResponse = reponseService.getById(responseIdToRetrieve);
        //System.out.println("Retrieved response by ID " + responseIdToRetrieve + ": " + retrievedResponse);

        // Retrieve all responses
        /*HashSet<Reponse> allResponses = reponseService.getAll();
        System.out.println("All responses:");
        for (Reponse response : allResponses) {
            System.out.println(response);
        }*/
    }
}

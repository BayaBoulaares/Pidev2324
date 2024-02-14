package edu.esprit.tests;

import edu.esprit.entities.Reponse;
import edu.esprit.services.QuestionService;
import edu.esprit.services.ReponseService;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        QuestionService questionService = new QuestionService();
        ReponseService reponseService = new ReponseService();

        // Test QuestionService
       /* System.out.println("Testing QuestionService:");
        System.out.println("========================");

        // Add a new question
        Question newQuestion = new Question();
        questionService.add(newQuestion);
        System.out.println("Added new question: " + newQuestion);

        // Retrieve all questions
        HashSet<Question> allQuestions = questionService.getAll();
        System.out.println("All questions:");
        for (Question question : allQuestions) {
            System.out.println(question);
        }*/

        // Test ReponseService
        System.out.println("\nTesting ReponseService:");
        System.out.println("========================");

        // Add a new response
        int idrep=10;
        Reponse newResponse = new Reponse();
        newResponse = new Reponse();
        //reponseService.add(newResponse);
        //System.out.println("Added new response: " + newResponse);

        // Retrieve all responses
        HashSet<Reponse> allResponses = reponseService.getAll();
        System.out.println("All responses:");
        for (Reponse response : allResponses) {
            System.out.println(response);
        }
    }
}

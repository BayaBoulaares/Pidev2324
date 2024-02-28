/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

public class ChatBot {
    public static String processInput(String input) {
        if(null == input)return "Malheuresement j'ai pas une reponse à ce genre de Message , merci d'attendez nos mise à jour systeme !";
        else switch (input) {
            case "salut":
                return "bonjour, comment puis-je vous aider ?";
            case "bonjour":
                return "bonjour, comment puis-je vous aider ?";
            case "pouvez vous m'expliquer le concept de cette plateforme":
                return "Auto_Learn est ne application educatif pour les enfants autistes \n et pour les aidees a comprendre plus facilement " ;
            case "les services":
                return "-Des cours\n" +
                        "-Des tests\n" +
                        "-Des Quiz \n" +
                        "-Événements  \n" +
                        "-Programmes pour enfants " ;
            case "merci":
                return "A tout moment , je suis là pour vous aidez !";
            case "le concept":
                return "fournir un espace dédié à l'education des enfants.  ";


            case "comment puis-je accéder aux cours disponibles ?":
                return "Vous pouvez accéder aux cours disponibles en vous connectant à \nvotre compte et en naviguant vers la section 'Cours'.";
            case "quels types de tests proposez-vous ?":
                return "Nous proposons différents types de tests, y compris des évaluations de connaissances,\n des quiz interactifs et des examens pratiques.";
            case "comment puis-je inscrire mon enfant à un événement ?":
                return "Pour inscrire votre enfant à un événement, accédez à la section 'Événements' sur la plateforme et suivez\n les instructions d'inscription.";
            case "pouvez-vous recommander des programmes adaptés à l'âge de mon enfant ?":
                return "Certainement ! Nous pouvons recommander des programmes éducatifs adaptés à l'âge de votre enfant.\n Veuillez fournir l'âge de votre enfant pour des suggestions personnalisées.";
            case "comment fonctionne la fonction d'auto-apprentissage ?":
                return "La fonction d'auto-apprentissage utilise des algorithmes intelligents pour adapter le contenu éducatif \naux besoins spécifiques de chaque enfant. Plus votre enfant utilise la plateforme, plus elle s'adapte à ses préférences d'apprentissage.";

            default:
                return "Malheuresement j'ai pas une reponse à ce genre de Message , merci d'attendez nos mise à jour systeme !";
        }
    }
}

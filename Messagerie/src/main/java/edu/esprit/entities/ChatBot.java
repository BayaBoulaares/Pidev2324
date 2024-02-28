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
            default:
                return "Malheuresement j'ai pas une reponse à ce genre de Message , merci d'attendez nos mise à jour systeme !";
        }
    }
}

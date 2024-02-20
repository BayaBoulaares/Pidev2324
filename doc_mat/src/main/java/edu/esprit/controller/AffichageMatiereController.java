package edu.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import edu.esprit.entities.Matiere;
import edu.esprit.services.SeviceMatiere;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AffichageMatiereController {

    @FXML
    private Button idaj;

    @FXML
    private Button idret;
    @FXML
    private FlowPane matiereFlowPane;
    @FXML
    private VBox alphabetButtons;


    private SeviceMatiere serviceMatiere = new SeviceMatiere();

    @FXML
    public void initialize() {

        // Load matières at startup
        loadMatieres();
    }

    public void loadMatieres() {
        try {
            // Fetch matières from the database or any other source
            List<Matiere> matieres = serviceMatiere.getAll();
            // Clear existing content in matiereFlowPane
            matiereFlowPane.getChildren().clear();

            // Dynamically create labels, delete buttons, and edit buttons for each matière's name
            int itemsPerRow = 3;
            for (Matiere matiere : matieres) {
                HBox matiereHBox = createMatiereHBox(matiere);
                matiereFlowPane.getChildren().add(matiereHBox);

                // Add a new row after every 'itemsPerRow' items
                if (matiereFlowPane.getChildren().size() % itemsPerRow == 0) {
                    matiereFlowPane.getChildren().add(new HBox()); // New row
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public HBox createMatiereHBox(Matiere matiere) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER); // Align HBox content to the center
        hBox.setSpacing(8); // Set spacing between label, delete button, and edit button

        Label label = new Label(matiere.getNommatiere());
        label.setStyle(
                "-fx-background-color: #27DEC1; " +
                        "-fx-border-color: #2b3674; " +
                        "-fx-border-width: 2px; " +
                        "-fx-padding: 3px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("System Bold", 20));

        // Bouton Supprimer
        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(event -> handleDeleteButtonClick(matiere));

        // Bouton Editer
        Button editButton = new Button("Editer");
        editButton.setOnAction(event -> handleEditButtonClick(matiere));

        // Bouton Ajouter Document
        Button ajouterDocumentButton = new Button("Ajouter Document");
        ajouterDocumentButton.setOnAction(event -> handleAjouterDocumentButtonClick(matiere));

        // Bouton Afficher Document
        Button afficherDocumentButton = new Button("Afficher Document");
        afficherDocumentButton.setOnAction(event -> handleAfficherDocumentButtonClick(matiere));
        Button  detailmatierebutton = new Button("Detail Document");
        detailmatierebutton.setOnAction(event -> handleDetailButtonClick(matiere));

        // Incorporer les boutons dans un HBox
        HBox buttonsHBox = new HBox(deleteButton, editButton, ajouterDocumentButton, afficherDocumentButton,detailmatierebutton);
        buttonsHBox.setSpacing(5);

        VBox buttonVBox = new VBox(label, buttonsHBox);
        buttonVBox.setAlignment(Pos.CENTER); // Align content in VBox to the center
        buttonVBox.setMinSize(50, 50); // Set your preferred size

        hBox.getChildren().add(buttonVBox);
        return hBox;
    }

    public void handleDeleteButtonClick(Matiere matiere) {
        try {
            // Handle the delete action here
            serviceMatiere.supprimer(matiere.getId());

            // Remove the HBox from the VBox after deletion
            matiereFlowPane.getChildren().removeIf(node ->
                    node instanceof HBox &&
                            ((HBox) node).getChildren().stream()
                                    .anyMatch(child -> child instanceof Label && ((Label) child).getText().equals(matiere.getNommatiere())));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Success");
            alert.setContentText("Matiere supprimee avec succes");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setContentText("Failed to delete matiere: " + e.getMessage());
            alert.showAndWait();
        }
        loadMatieres();
    }

    public void handleEditButtonClick(Matiere matiere) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModiffierMatiere.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de modification
            ModifierMatiereController modifierController = loader.getController();
            Matiere me=serviceMatiere.test(matiere);
           if( me!=null)
            // Appeler la méthode pour passer la matière à modifier
           { modifierController.setMatiereToModify(me);}

            // Changer la scène pour afficher le formulaire de modification
            idret.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void handleAjouterDocumentButtonClick(Matiere matiere) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDocument.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de modification
            AjouterDocument ajouterdoc= loader.getController();
            Matiere me=serviceMatiere.test(matiere);
            if( me!=null)
            // Appeler la méthode pour passer la matière à modifier
            { ajouterdoc.setMatToAdd(me);}

            // Changer la scène pour afficher le formulaire de modification
            idret.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
}
public void handleAfficherDocumentButtonClick( Matiere matiere){
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDocument.fxml"));
        Parent root = loader.load();
        // Accéder au contrôleur du formulaire de modification
        AfficherDocument affichedoc= loader.getController();
        Matiere me=serviceMatiere.test(matiere);
        if( me!=null)
        // Appeler la méthode pour passer la matière à modifier
        { affichedoc.setMatToShow(me);}
        // Changer la scène pour afficher le formulaire de modification
        idret.getScene().setRoot(root);
    } catch (IOException e) {
        e.printStackTrace();
    }catch (SQLException e)
    { throw new RuntimeException(e);}

}

    public void ajouterMatiere(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMatiere.fxml"));
            Parent root = loader.load();
            idaj.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleDetailButtonClick(Matiere matiere) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailMatiere.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de modification
       DetailMatiere detailMatiere = loader.getController();
            Matiere me=serviceMatiere.getOne(matiere.getId());
            if( me!=null)
            // Appeler la méthode pour passer la matière à modifier
            { detailMatiere.setMatiereToShow(me);}

            // Changer la scène pour afficher le formulaire de modification
            idret.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void handleAlphabetButtonClick(ActionEvent actionEvent) {
        Button clickedButton = (Button) actionEvent.getSource();
        String selectedAlphabet = clickedButton.getText();
        filterMatieresByAlphabet(selectedAlphabet);
    }
    public void filterMatieresByAlphabet(String selectedAlphabet) {
        try {
            // Fetch matières from the database or any other source
            List<Matiere> matieres = serviceMatiere.getMatiereByAlphabet(selectedAlphabet);

            // Afficher les matières filtrées
            displayFilteredMatieres(matieres);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void displayFilteredMatieres(List<Matiere> matieres) {
        // Clear existing content in matiereFlowPane
        matiereFlowPane.getChildren().clear();

        // Dynamically create labels, delete buttons, and edit buttons for each matière's name
        int itemsPerRow = 3;
        for (Matiere matiere : matieres) {
            HBox matiereHBox = createMatiereHBox(matiere);
            matiereFlowPane.getChildren().add(matiereHBox);

            // Add a new row after every 'itemsPerRow' items
            if (matiereFlowPane.getChildren().size() % itemsPerRow == 0) {
                matiereFlowPane.getChildren().add(new HBox()); // New row
            }
        }

    }

    public void affichertout(ActionEvent actionEvent) {
        loadMatieres();
    }
}
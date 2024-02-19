package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherMessage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private TableColumn<Messagerie, Date> Date;

    @FXML
    private DatePicker DateID;

    @FXML
    private TextField MeesageID;

    @FXML
    private TableColumn<Messagerie, String> Message;

    @FXML
    private TableColumn<Messagerie, String> Nom;

    @FXML
    private TextField NomID;

    @FXML
    private TableView<Messagerie> TableView;

    @FXML
    private Button Modifier;

    @FXML
    private Button Supprimer;

    public final ServiceMessagerie ps = new ServiceMessagerie();
    private Messagerie messagerie;

    @FXML
    void initialize() {
        try {
            List<Messagerie> messagerieList = new ArrayList<>(ps.getAll());

            if (messagerieList.isEmpty()) {
                showAlert("Aucune donnée à afficher.");
            } else {
                ObservableList<Messagerie> observableList = FXCollections.observableList(messagerieList);
                TableView.setItems(observableList);
            }
        } catch (SQLException e) {
            showAlert("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace(); // Handle the exception properly in your application
        }

        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Message.setCellValueFactory(new PropertyValueFactory<>("message"));
    }

    @FXML
    public void mouceClicked(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Messagerie messagerie = TableView.getSelectionModel().getSelectedItem();
            if (messagerie != null && messagerie.getNom() != null && messagerie.getDate() != null && messagerie.getMessage() != null) {
                this.messagerie = new Messagerie(messagerie.getId(), messagerie.getNom(), messagerie.getDate(), messagerie.getMessage());
                NomID.setText(messagerie.getNom());
                MeesageID.setText(messagerie.getMessage());
                DateID.setValue(messagerie.getDate().toLocalDate());
            } else {
                // Display an alert or handle the situation when the selected Messagerie or its attributes are null
                showAlert("Invalid Messagerie data selected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifiermessage(ActionEvent event) {
        try {
            if (validateInput()) {
                ServiceMessagerie ps = new ServiceMessagerie();
                messagerie = new Messagerie(messagerie.getId(), NomID.getText(), MeesageID.getText());
                // Vérifier si le message commence par une majuscule
                if (!MeesageID.getText().isEmpty() && !Character.isUpperCase(MeesageID.getText().charAt(0))) {
                    showAlert("Le message doit commencer par une majuscule.");
                    return;
                }

                ps.modifier(messagerie);
                initialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateInput() {
        String nom = NomID.getText();
        String message = MeesageID.getText();

        if (nom.isEmpty() || message.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return false;
        }

        // Valider le format du nom (uniquement des lettres et des espaces)
        if (!nom.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")) {
            showAlert("Le nom doit contenir uniquement des lettres et des espaces.");
            return false;
        }

        // Vous pouvez ajouter d'autres validations selon vos besoins

        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void supprimermessage(javafx.event.ActionEvent event) {
        try {
            if (validateSelection()) {
                ServiceMessagerie ps = new ServiceMessagerie();
                if (messagerie != null) {
                    ps.supprimer(messagerie.getId());
                    initialize();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateSelection() {
        if (TableView.getSelectionModel().getSelectedItem() == null) {
            showAlert("Veuillez sélectionner un élément à supprimer.");
            return false;
        }
        return true;
    }


}

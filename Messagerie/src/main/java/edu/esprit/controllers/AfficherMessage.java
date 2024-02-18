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
            ObservableList<Messagerie> observableList = FXCollections.observableList(messagerieList);
            TableView.setItems(observableList);
        } catch (SQLException e) {
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
            if (messagerie != null) {
                messagerie = new Messagerie(messagerie.getId(), messagerie.getNom(), messagerie.getDate(), messagerie.getMessage());
                this.messagerie = messagerie;
                NomID.setText(messagerie.getNom());
                MeesageID.setText(messagerie.getMessage());
                DateID.setValue(messagerie.getDate().toLocalDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifiermessage(ActionEvent event) {
        try {
            ServiceMessagerie ps = new ServiceMessagerie();
            messagerie = new Messagerie(messagerie.getId(), NomID.getText(), MeesageID.getText());
            ps.modifier(messagerie);
            initialize();
                } catch (Exception e) {
            e.printStackTrace();
        }
    }




    void supprimermessage(javafx.event.ActionEvent event) {
        try {
            ServiceMessagerie ps = new ServiceMessagerie();
            if (messagerie != null) {
                ps.supprimer(messagerie.getId());
                initialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

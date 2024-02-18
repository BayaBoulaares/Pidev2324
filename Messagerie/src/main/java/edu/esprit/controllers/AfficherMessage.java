package edu.esprit.controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AfficherMessage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TableColumn<Messagerie, String> Nom;
    @FXML
    private TableColumn<Messagerie, Date> Date;

    @FXML
    private TableColumn<Messagerie, String> Message;




    @FXML
    private TableView<Messagerie> TableView;

    private final ServiceMessagerie ps = new ServiceMessagerie();

    @FXML
    void initialize() {
        try {
            List<Messagerie> messagerieList = new ArrayList<>(ps.getAll()); // Corrected syntax
            ObservableList<Messagerie> observableList = FXCollections.observableList(messagerieList);
            TableView.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }

        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date")); // Corrected attribute name
        Message.setCellValueFactory(new PropertyValueFactory<>("message")); // Corrected attribute name
    }
}

package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.ParentE;
import edu.esprit.crudoff.entities.Professeur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AffichageParent {

    @FXML
    private Button dcxn;

    @FXML
    private Button evaluation1;

    @FXML
    private Button evenements1;

    @FXML
    private TextField filterField;

    @FXML
    private Button profile2;

    @FXML
    private Button reclamation1;

    @FXML
    private TableView<ParentE> table;

    @FXML
    private TableColumn<ParentE, String> tadresse;

    @FXML
    private TableColumn<ParentE, Date> tdob;

    @FXML
    private TableColumn<ParentE, String> tdobenfant;

    @FXML
    private TableColumn<ParentE, String> tlogin;

    @FXML
    private TableColumn<ParentE, Integer> tniveau;

    @FXML
    private TableColumn<ParentE, String> tnom;

    @FXML
    private TableColumn<ParentE, String> tnomenfant;

    @FXML
    private TableColumn<ParentE, String> tprenom;

    @FXML
    private TableColumn<ParentE, String> tprenomenfant;

    @FXML
    private TableColumn<ParentE, Integer> ttel;
    @FXML
    private TableColumn<ParentE, Void> taction;
    @FXML
    private Button deletebutton;
    ObservableList<ParentE>  parentlist = FXCollections.observableArrayList();
    //ObservableList<Professeur> rechercheList;

    ServiceParent SU = new ServiceParent();

    @FXML
    public void initialize() throws IOException, SQLException {
           List<ParentE> userList = new ArrayList<>(SU.getAll()) ;
        ObservableList<ParentE> observableList = FXCollections.observableList(userList);

        tnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            tprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            tadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            tdob.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            ttel.setCellValueFactory(new PropertyValueFactory<>("tel"));
            tlogin.setCellValueFactory(new PropertyValueFactory<>("login"));
            tnomenfant.setCellValueFactory(new PropertyValueFactory<>("nomE"));
            tprenomenfant.setCellValueFactory(new PropertyValueFactory<>("prenomE"));
            tdobenfant.setCellValueFactory(new PropertyValueFactory<>("dateNaissanceE"));
            tniveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
            table.setItems(observableList);
            // Configuration de la colonne d'action pour afficher les boutons
            taction.setCellFactory(param -> new TableCell<ParentE, Void>() {

                private final Button deletebutton = new Button("Supprimer");

                {deletebutton.setStyle("-fx-background-color: #8F9BBA; -fx-background-radius: 3px; -fx-min-width: 15px; -fx-min-height: 15px;");

                deletebutton.setOnAction(event -> {
                    ObservableList<ParentE> selectedItems = table.getSelectionModel().getSelectedItems();

                    ParentE utilisateur = table.getSelectionModel().getSelectedItem();
                    if (!selectedItems.isEmpty()) {
                        // Code pour la suppression

                        ServiceParent su = new ServiceParent();


                            ParentE _user=su.getByLogin(utilisateur.getLogin());
                            System.out.println(_user);
                            System.out.println(_user.getId());
                            su.supprimer(_user.getId());

                        try {
                            su.supprimer(_user.getId());
                            List<ParentE> updatedList = null;
                            updatedList = (List<ParentE>)su.getAll();
                            observableList.clear();
                            observableList.addAll(updatedList);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }








                    } else {
                        // Aucun utilisateur sélectionné, afficher un message d'erreur
                        System.out.println("Aucun utilisateur sélectionné pour la suppression.");
                    }


                });
                    // Créez un prédicat pour filtrer les données en fonction du texte entré dans le champ de recherche
                    filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                        // Créez un prédicat pour filtrer les données
                        FilteredList<ParentE> filteredData = new FilteredList<>(observableList, p -> true);

                        // Appliquez le nouveau filtre lorsque le texte change
                        filteredData.setPredicate(parentE -> {
                            // Si le texte de recherche est vide, affichez toutes les lignes
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }

                            // Comparez le texte de recherche avec les attributs de votre objet Professeur
                            String lowerCaseFilter = newValue.toLowerCase();
                            if (parentE.getNom().toLowerCase().contains(lowerCaseFilter) ||
                                    parentE.getPrenom().toLowerCase().contains(lowerCaseFilter) ||
                                    parentE.getLogin().toLowerCase().contains(lowerCaseFilter) ){
                                //professeur.getDiscpline().toLowerCase().contains(lowerCaseFilter)) {
                                return true; // Correspondance trouvée
                            }


                            return false; // Pas de correspondance trouvée
                        });

                        // Appliquez le filtre à la table
                        SortedList<ParentE> sortedData = new SortedList<>(filteredData);
                        sortedData.comparatorProperty().bind(table.comparatorProperty());
                        table.refresh();
                        table.setItems(sortedData);
                    });


    }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new HBox(deletebutton));
                    }
                }

});

    }
    @FXML
    void deconnexion(ActionEvent event) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        dcxn.getScene().setRoot(root);
    }



    @FXML
    void toProfesseurs(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        dcxn.getScene().setRoot(root);

    }



}

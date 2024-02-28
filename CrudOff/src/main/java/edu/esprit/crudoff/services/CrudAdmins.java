package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.Administrateur;
import edu.esprit.crudoff.entities.Professeur;
import edu.esprit.crudoff.entities.Utilisateur;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrudAdmins {
    ServiceProfesseur su =new ServiceProfesseur();
    @FXML
    private Button edi;
    @FXML
    private Button dcxn;
    @FXML
    private Button supp;
    @FXML
    private Button evaluation;

    @FXML
    private Button evenements;

    @FXML
    private Button profile;

    @FXML
    private Button profile1;

    @FXML
    private Button profile3;

    @FXML
    private Button reclamation;

    @FXML
    private TableColumn<Professeur, String> tadresse;

    @FXML
    private TableColumn<Professeur, String> tdisc;

    @FXML
    private TableColumn<Professeur, Date> tdob;

    @FXML
    private TableColumn<Professeur, String> tlogin;

    @FXML
    private TableColumn<Professeur, String> tnom;

    @FXML
    private TextField filterField;


    @FXML
    private Button toprof;

    @FXML
    private Button tousers;

    @FXML
    private TableColumn<Professeur, String> tprenom;

    @FXML
    private TableColumn<Professeur, String> trole;

    @FXML
    private TableColumn<Professeur, Integer> ttel;
    @FXML
    private TableColumn<Professeur, Void> taction;
    @FXML
    private TableView<Professeur> table;
    ObservableList<Professeur> StudentList = FXCollections.observableArrayList();
    ObservableList<Professeur> rechercheList;

   ServiceUtilisateur SU = new ServiceUtilisateur();

    @FXML
    public void initialize() throws IOException, SQLException {


            List<Professeur> userList = new ArrayList<>(su.getAll()) ;
            ObservableList<Professeur> observableList = FXCollections.observableList(userList);
            tnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            tprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            tadresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            tdob.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
            ttel.setCellValueFactory(new PropertyValueFactory<>("tel"));
            trole.setCellValueFactory(cellData -> new SimpleStringProperty(getUserRole(cellData.getValue())));
            tlogin.setCellValueFactory(new PropertyValueFactory<>("login"));
            tdisc.setCellValueFactory(new PropertyValueFactory<>("discpline"));
            //table.refresh();
            table.setItems(observableList);
            // Configuration de la colonne d'action pour afficher les boutons
            taction.setCellFactory(param -> new TableCell<Professeur, Void>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Supprimer");
                // Crée des ImageView avec les icônes souhaitées





                {editButton.setStyle("-fx-background-color: #2B3674; -fx-background-radius: 3px; -fx-min-width: 10px; -fx-min-height: 10px;");
                editButton.setOnAction(event -> {
                    // Récupérer l'utilisateur sélectionné dans la TableView
                    Professeur utilisateur = getTableView().getItems().get(getIndex());
                    // Vérifier si un utilisateur est sélectionné
                    if (utilisateur != null) {
                        // Afficher une fenêtre de modification des informations de l'utilisateur
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditProfs.fxml"));
                        try {
                            Parent root = loader.load();
                            System.out.println(utilisateur);
                            // Récupérer le contrôleur de la fenêtre de modification
                            if (utilisateur != null) {
                                // Mettre à jour l'utilisateur sélectionné avec les nouvelles valeurs
                                if (utilisateur instanceof Professeur) {
                                    Professeur professeur = (Professeur) utilisateur;
                                    EditProfs controller = loader.getController();
                                    controller.fillFormData(professeur);

                                } }

                            // Créer une nouvelle scène avec la fenêtre de modification
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("Modifier Utilisateur");
                            stage.show();
                            table.refresh();
                            //initialize();


                        } catch (IOException e) {
                            e.printStackTrace();
                            // Gérer les erreurs d'entrée/sortie
                        }
                    } else {
                        // Aucun utilisateur sélectionné, afficher un message d'erreur
                        System.out.println("Aucun utilisateur sélectionné pour l'édition.");
                    }


                });

                deleteButton.setStyle("-fx-background-color: #2B3674; -fx-background-radius: 3px; -fx-min-width: 10px; -fx-min-height: 10px;");

                deleteButton.setOnAction(event -> {
                    ObservableList<Professeur> selectedItems = table.getSelectionModel().getSelectedItems();

                    Utilisateur utilisateur = table.getSelectionModel().getSelectedItem();
                    if (!selectedItems.isEmpty()) {
                        // Code pour la suppression

                        //erviceUtilisateur su = new ServiceUtilisateur();
                        ServiceProfesseur sp = new ServiceProfesseur();


                            Professeur _user=sp.getByLogin(utilisateur.getLogin());
                            System.out.println(_user);
                            System.out.println(_user.getId());
                        try {
                            sp.supprimer(_user.getId());
                            List<Professeur> updatedList = (List<Professeur>) su.getAll();
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
                    FilteredList<Professeur> filteredData = new FilteredList<>(observableList, p -> true);

                    // Appliquez le nouveau filtre lorsque le texte change
                    filteredData.setPredicate(professeur -> {
                        // Si le texte de recherche est vide, affichez toutes les lignes
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }

                        // Comparez le texte de recherche avec les attributs de votre objet Professeur
                        String lowerCaseFilter = newValue.toLowerCase();
                        if (professeur.getNom().toLowerCase().contains(lowerCaseFilter) ||
                                professeur.getPrenom().toLowerCase().contains(lowerCaseFilter) ||
                                professeur.getLogin().toLowerCase().contains(lowerCaseFilter) ){
                                //professeur.getDiscpline().toLowerCase().contains(lowerCaseFilter)) {
                            return true; // Correspondance trouvée
                        }


                        return false; // Pas de correspondance trouvée
                    });

                    // Appliquez le filtre à la table
                    SortedList<Professeur> sortedData = new SortedList<>(filteredData);
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
                    setGraphic(new HBox(editButton, deleteButton));
                }
            }
        });


    }


    @FXML
    public void deleteButtonClicked(ActionEvent actionEvent) {
        try {
            // Récupérer l'utilisateur sélectionné dans la TableView
            Utilisateur utilisateur = table.getSelectionModel().getSelectedItem();
            ServiceAdmin sa = new ServiceAdmin();
            ServiceProfesseur sp =new ServiceProfesseur();
            String role = su.obtenirRole(utilisateur.getId());

            // Vérifier si un utilisateur est sélectionné
            if (utilisateur != null) {
                // Vérifier le rôle de l'utilisateur
                if (role.equals("Parent")) {
                    // Appeler la fonction pour supprimer l'administrateur
                    sa.supprimer(utilisateur.getId());
                } else if (role.equals("Professeur")) {
                    // Appeler la fonction pour supprimer le professeur
                    sp.supprimer(utilisateur.getId());
                } else {
                    // Gérer le cas où l'utilisateur a un rôle différent (si nécessaire)
                    System.out.println("Impossible de supprimer l'utilisateur avec le rôle spécifié.");
                }
            } else {
                // Aucun utilisateur sélectionné, afficher un message d'erreur
                System.out.println("Aucun utilisateur sélectionné pour la suppression.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            search_user();
            // Gérer les exceptions
        }
    }
    private String getUserRole(Utilisateur utilisateur) {

        ServiceAdmin sa = new ServiceAdmin();
        ServiceProfesseur sp = new ServiceProfesseur();
        if (utilisateur instanceof Administrateur) {
            // Supposons que vous ayez une classe ServiceAdmin pour récupérer le rôle de l'administrateur
            return sa.getRole(utilisateur.getId());
        } else if (utilisateur instanceof Professeur) {
            // Supposons que vous ayez une classe ServiceProfesseur pour récupérer le rôle du professeur
            return sp.getRole(utilisateur.getId());
        } else {
            return "Parent"; // Gérez le cas où le type d'utilisateur n'est pas pris en charge ou s'il n'y a pas de rôle correspondant
        }
    }


    public void deconnexion(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        edi.getScene().setRoot(root);
    }

    public void toaddprof(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AddProfs.fxml"));
        Parent root=loader.load();
        edi.getScene().setRoot(root);
    }
    @FXML
    public void search_user()
    {   /*tnom.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Nom"));
        tprenom.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Prénom"));
        tadresse.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Adresse"));
        tdob.setCellValueFactory(new PropertyValueFactory<Professeur,Date>("Date de naissance"));
        ttel.setCellValueFactory(new  PropertyValueFactory<Professeur,Integer>("Téléphone"));
        trole.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Role"));
        tlogin.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Login"));
        tdisc.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Discipline"));
        rechercheList = (ObservableList<Professeur>) su.getAll();
        table.setItems(rechercheList);
        FilteredList<Professeur> filteredList = new FilteredList<>(rechercheList, b -> true);
        filterField.textProperty().addListener((observable,oldValue,newValue )-> {
            filteredList.setPredicate(professeur -> {if(newValue == null || newValue.isEmpty()){
            return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
                if (professeur.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1)
                {
                    return true; //filter matches username
                }
                else
                    return false;//does not match
            });
        });

        SortedList<Professeur> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);*/
        tnom.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Nom"));
        tprenom.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Prénom"));
        tadresse.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Adresse"));
        tdob.setCellValueFactory(new PropertyValueFactory<Professeur,Date>("Date de naissance"));
        ttel.setCellValueFactory(new PropertyValueFactory<Professeur,Integer>("Téléphone"));
        trole.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Role"));
        tlogin.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Login"));
        tdisc.setCellValueFactory(new PropertyValueFactory<Professeur,String>("Discipline"));

        rechercheList = FXCollections.observableArrayList(su.getAll());
        table.setItems(rechercheList);

        FilteredList<Professeur> filteredList = new FilteredList<>(rechercheList, b -> true);
        filterField.textProperty().addListener((observable,oldValue,newValue) -> {
            filteredList.setPredicate(professeur -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (professeur.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; //filter matches username
                } else {
                    return false; //does not match
                }
            });
        });

        SortedList<Professeur> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    @FXML
    public void toParent(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AffichageParent.fxml"));
        Parent root=loader.load();
        dcxn.getScene().setRoot(root);

    }

    public void toaccueil(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
        Parent root=loader.load();
        dcxn.getScene().setRoot(root);
    }
}

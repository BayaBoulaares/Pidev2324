package edu.esprit.controller;

import edu.esprit.APIapploadfichier.Statistique;
import edu.esprit.APIapploadfichier.StatistiquesAPI;
import edu.esprit.entities.Professeur;
import edu.esprit.services.CredentialsManager;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AffichageMatiereController {

    @FXML
    private Button idaj;


    @FXML
    private FlowPane matiereFlowPane;

    @FXML
    private TextField idrecherche;
    @FXML
    private Button idstate;
    private ObservableList<Matiere> filteredMatieres = FXCollections.observableArrayList();


    private SeviceMatiere serviceMatiere = new SeviceMatiere();
    private final PauseTransition pause = new PauseTransition(Duration.seconds(1));
    public static  Professeur prod;
    public void setProftoGet(Professeur prof)
    {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        prod=prof;
        System.out.println(prod);

    }

    @FXML
    public void initialize() {
        idrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> {
                chercherMatiers();
            });
            pause.playFromStart();
        });

        // Load matières at startup
        loadMatieres();
    }

    public void loadMatieres() {
        try {
            // Fetch matières from the database or any other source
            List<Matiere> matieres = (List<Matiere>) serviceMatiere.getAll();
            // Clear existing content in matiereFlowPane
            matiereFlowPane.getChildren().clear();

            matiereFlowPane.setHgap(10); // Ajout de l'espace horizontal entre les VBox
            matiereFlowPane.setVgap(10); // Ajout de l'espace vertical entre les VBox

            // Dynamically create labels, delete buttons, and edit buttons for each matière's name
            int itemsPerRow = 3;
            for (Matiere matiere : matieres) {
                VBox matiereHBox = createMatiereVBox(matiere);
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
    public VBox createMatiereVBox(Matiere matiere) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER); // Align VBox content to the center
        vBox.setSpacing(8); // Set spacing between label and buttons
        vBox.setStyle( "-fx-background-color:  #FAFEFC; "+" -fx-border-width: 2px;"+" -fx-border-color: #2b3674;"+"-fx-background-radius: 5px;"+  "-fx-border-radius: 5px; ");

        Label label = new Label(matiere.getNommatiere());
        label.setStyle(
                "-fx-background-color:   #27DEC1;" +
                        "-fx-border-width: 2px; " +
                        "-fx-padding: 3px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-text-fill: #2b3674;" +
                        "-fx-border-color:#2b3674;" +
                        "-fx-background-radius: 5px;"
        );

        label.setFont(new Font("System Bold", 20));

        // Bouton Supprimer
        Button deleteButton = new Button("Supprimer");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill:#2b3674;");
        deleteButton.setOnAction(event -> handleDeleteButtonClick(matiere));

        // Bouton Editer
        Button editButton = new Button("Editer");
        editButton.setStyle("-fx-background-color: transparent; -fx-text-fill:#2b3674;");
        editButton.setOnAction(event -> handleEditButtonClick(matiere));

        // Bouton Ajouter Document
        Button ajouterDocumentButton = new Button("Ajouter Document");
        ajouterDocumentButton.setStyle("-fx-background-color: transparent; -fx-text-fill:#2b3674;  ");
        ajouterDocumentButton.setOnAction(event -> handleAjouterDocumentButtonClick(matiere));

        // Bouton Afficher Document
        Button afficherDocumentButton = new Button("Afficher Document");
        afficherDocumentButton.setStyle("-fx-background-color: transparent; -fx-text-fill:#2b3674;  ");
        afficherDocumentButton.setOnAction(event -> handleAfficherDocumentButtonClick(matiere));
        Button  detailmatierebutton = new Button("Detail Matiere");
        detailmatierebutton.setStyle("-fx-background-color:transparent; -fx-text-fill:#2b3674;  ");
        detailmatierebutton.setOnAction(event -> handleDetailButtonClick(matiere));

        // Ajouter le label et les boutons dans le VBox
        vBox.getChildren().addAll(label, deleteButton, editButton, ajouterDocumentButton, afficherDocumentButton, detailmatierebutton);
        vBox.setPrefWidth(180); // Replace 200 with your desired width
        vBox.setPrefHeight(180); // Replace 200 with your desired height
        return vBox;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModiffierMatiere.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de modification
            ModifierMatiereController modifierController = loader.getController();
            Matiere me=serviceMatiere.test(matiere);
           if( me!=null)
            // Appeler la méthode pour passer la matière à modifier
           { modifierController.setMatiereToModify(me);}

            // Changer la scène pour afficher le formulaire de modification
            idrecherche.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void handleAjouterDocumentButtonClick(Matiere matiere) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterDocument.fxml"));
            Parent root = loader.load();
            AjouterDocument ajouterdoc= loader.getController();
            Matiere me=serviceMatiere.getOne(matiere.getId());
            if( me!=null)

            { ajouterdoc.setMatToAdd(me);}

            // Changer la scène pour afficher le formulaire de modification
            idaj.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
}
public void handleAfficherDocumentButtonClick(Matiere matiere){
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherDocument.fxml"));
        Parent root = loader.load();
        // Accéder au contrôleur du formulaire de modification
        AfficherDocument affichedoc= loader.getController();
        Matiere me=serviceMatiere.test(matiere);
        if( me!=null)
        // Appeler la méthode pour passer la matière à modifier
        { affichedoc.setMatToShow(me);}
        // Changer la scène pour afficher le formulaire de modification
        idaj.getScene().setRoot(root);
    } catch (IOException e) {
        e.printStackTrace();
    }catch (SQLException e)
    { throw new RuntimeException(e);}

}
@FXML
    public void ajouterMatiere(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterMatiere.fxml"));
            Parent root = loader.load();
            MatiereController mc=loader.getController();
           // mc.professGet(prod);
            idaj.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleDetailButtonClick(Matiere matiere) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DetailMatiere.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de modification
       DetailMatiere detailMatiere = loader.getController();
            Matiere me=serviceMatiere.getOne(matiere.getId());
            if( me!=null)
            // Appeler la méthode pour passer la matière à modifier
            { detailMatiere.setMatiereToShow(me);}

            // Changer la scène pour afficher le formulaire de modification
            idaj.getScene().setRoot(root);
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
            VBox  matiereHBox = createMatiereVBox(matiere);
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
    @FXML

    public void chercherMatiers() {
        String searchQuery = idrecherche.getText().toLowerCase().trim();
        if (searchQuery.isEmpty()) {
            // Handle the case where the search query is empty (optional)
            // You can display all matières or show a message to enter a query.
            // For now, let's display all matières:
            loadMatieres();
        } else {
            try {
                // Fetch matières from the database or any other source
                List<Matiere> allMatieres = (List<Matiere>) serviceMatiere.getAll();

                // Filter matières based on the search query
                filteredMatieres.setAll(allMatieres.stream()
                        .filter(matiere -> matiere.getNommatiere().toLowerCase().contains(searchQuery))
                        .collect(Collectors.toList()));

                // Display the filtered matières
                displayFilteredMatieres(filteredMatieres);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    @FXML
    public void showState(ActionEvent actionEvent) {
        StatistiquesAPI api = new StatistiquesAPI();
        List<Statistique> statistiques = api.getStatistiques();
        showStatistics(statistiques);
    }
    private void showStatistics(List<Statistique> statistiques) {
        Stage stage = new Stage();
        stage.setTitle("Statistiques");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Statistiques des documents par matière");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Statistique statistique : statistiques) {
            String matiere = statistique.getTitre();
            int nombreDocuments = statistique.getNembre();
            series.getData().add(new XYChart.Data<>(matiere, nombreDocuments));
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


@FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMatiere.fxml"));
    Parent root=loader.load();
    AffichageMatiereController acm=loader.getController();
    acm.setProftoGet(prod);
    idaj.getScene().setRoot(root);
    }
@FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
    CredentialsManager.clearCredentials();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Parent root = loader.load();
    idaj.getScene().setRoot(root);
    }
@FXML
    public void tomessage(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMessage.fxml"));
    Parent root=loader.load();
    idaj.getScene().setRoot(root);
    }
    @FXML
    public void toEvaluation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/gui/Quiz/admin/homePage.fxml"));
        Parent root=loader.load();
        idaj.getScene().setRoot(root);
    }
}

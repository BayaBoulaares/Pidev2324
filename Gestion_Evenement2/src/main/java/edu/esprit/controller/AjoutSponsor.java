package edu.esprit.controller;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Fond;
import edu.esprit.entities.Sponsor;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceSponsor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AjoutSponsor {

    public ImageView sponsorImage;
    public Button selectImageButton;
    @FXML
    private TextField sponsorName;

    @FXML
    private TextField sponsorDescription;

    @FXML
    private ComboBox<Evenement> eventComboBox;

    @FXML
    private ComboBox<String> sponsorFond;

    private String imagePath;


    @FXML
    void initialize() {
        // Remplir la ComboBox avec les événements lors du chargement du formulaire
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        try {
            eventComboBox.setItems(FXCollections.observableArrayList(serviceEvenement.getAll()));
            // Remplacer la cellule par défaut pour afficher uniquement les noms des événements
            eventComboBox.setCellFactory(param -> new ListCell<Evenement>() {
                @Override
                protected void updateItem(Evenement item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNom_Event());
                    }
                }
            });
        } catch (SQLException e) {
            afficherAlerte("Erreur lors du chargement des événements : " + e.getMessage());
        }
    }

    @FXML
    void ajouter() {
        try {
            Evenement selectedEvent = eventComboBox.getValue();
            if (selectedEvent == null) {
                setFieldAsInvalid(eventComboBox);
                return;
            }

            String nomSponsor = sponsorName.getText();
            String descriptionSponsor = sponsorDescription.getText();
            Fond fondSponsor = null;

            String fondValue = sponsorFond.getValue();
            if (fondValue != null) {
                fondSponsor = Fond.valueOf(fondValue);
            } else {
                setFieldAsInvalid(sponsorFond);
                return;
            }

            // Validate sponsor name and description length
            if (nomSponsor.length() < 3 || descriptionSponsor.length() < 3) {
                setFieldAsInvalid(sponsorName);
                setFieldAsInvalid(sponsorDescription);
                return;
            }

            // Validate sponsor name: length and absence of digits
            if (nomSponsor.length() > 20 || nomSponsor.matches(".*\\d.*")) {
                setFieldAsInvalid(sponsorName);
                return;
            }

            // Validate sponsor name and description: absence of special characters
            if (contientCaracteresSpeciaux(nomSponsor) || contientCaracteresSpeciaux(descriptionSponsor)) {
                return;
            }

            // Check if the sponsor is already assigned to this event
            ServiceSponsor serviceSponsor = new ServiceSponsor();
            boolean sponsorExists = serviceSponsor.sponsorExistsForEvent(selectedEvent.getNom_Event());
            if (sponsorExists) {
                setFieldAsInvalid(sponsorName);
                afficherAlerte("Ce sponsor est déjà attribué à cet événement !");
                return;
            }

            // Validate if all required fields are filled
            if (nomSponsor.isEmpty() || descriptionSponsor.isEmpty() || fondSponsor == null || imagePath.isEmpty()) {
                setFieldAsInvalid(sponsorName);
                setFieldAsInvalid(sponsorDescription);
                setFieldAsInvalid(sponsorFond);
                afficherAlerte("Veuillez sélectionner une image pour le sponsor.");
                return;
            }

            setFieldAsValid(sponsorName);
            setFieldAsValid(sponsorDescription);
            setFieldAsValid(sponsorFond);

            // Create a new Sponsor object with the provided information
            Sponsor nouveauSponsor = new Sponsor();
            nouveauSponsor.setNomSponsor(nomSponsor);
            nouveauSponsor.setDescription_s(descriptionSponsor);
            nouveauSponsor.setFond(fondSponsor);
            nouveauSponsor.setEvenement(selectedEvent);
            nouveauSponsor.setImage(imagePath); // Set the image path

            // Call the service to add the new sponsor
            serviceSponsor.ajouter(nouveauSponsor);

            afficherAlerte("Sponsor ajouté avec succès");

            // Reset the fields after adding
            sponsorName.clear();
            sponsorDescription.clear();
            eventComboBox.getSelectionModel().clearSelection();
            sponsorFond.getSelectionModel().clearSelection();
            imagePath = ""; // Reset the image path

        } catch (SQLException | NumberFormatException e) {
            afficherAlerte("Une erreur de type " + e.getClass().getSimpleName() + " s'est produite lors de l'ajout du sponsor : " + e.getMessage());
        }
    }

    private boolean contientCaracteresSpeciaux(String text) {
        if (text.matches(".*\\d.*")) {
            afficherAlerte("Le nom de l'événement ne peut pas contenir de chiffres !");
            return true; // Contains digits
        } else if (!text.matches("[a-zA-ZÀ-ÿ\\s]*")) {
            afficherAlerte("Le nom de l'événement ne doit pas contenir de caractères spéciaux !");
            return true; // Contains special characters
        } else if (text.matches(".*[^a-zA-ZÀ-ÿ0-9\\s].*")) {
            afficherAlerte("La description de l'événement ne doit pas contenir de caractères spéciaux !");
            return true; // Contains special characters
        }
        return false; // No digits or special characters
    }


    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString()); // Corrected line
                sponsorImage.setImage(image);
                imagePath = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setFieldAsValid(TextField field) {
        field.getStyleClass().removeAll("invalid-field");
        field.getStyleClass().add("valid-field");
    }

    private void setFieldAsInvalid(TextField field) {
        field.getStyleClass().removeAll("valid-field");
        field.getStyleClass().add("invalid-field");
        field.setPromptText("Champ invalide");
    }

    private void setFieldAsValid(ComboBox<?> field) {
        field.getStyleClass().removeAll("invalid-field");
        field.getStyleClass().add("valid-field");
    }

    private void setFieldAsInvalid(ComboBox<?> field) {
        field.getStyleClass().removeAll("valid-field");
        field.getStyleClass().add("invalid-field");
        field.setPromptText("Champ invalide");
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void consulterStatistique(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Statistique.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Statistiques des sponsors");
            stage.setScene(new Scene(root));


            stage.show();
        } catch (IOException e) {
            // Handle any IO exceptions
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la vue de statistiques", e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions
            e.printStackTrace();
            showAlert("Erreur", "Une erreur inattendue s'est produite", e.getMessage());
        }

    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

    @FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatierAdmin.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void tohisprofile(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void toacceuiel(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }}

package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.Professeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditProfs {



    @FXML
    private PasswordField pmdp;

    @FXML
    private DatePicker editdob;
        @FXML
        private Button accueil;

        @FXML
        private Button adminform;

        @FXML
        private Button bconfirm;

        @FXML
        private Button breturn;

        @FXML
        private TextField editpdiscfield;

        @FXML
        private TextField editpadressefield;

        @FXML
        private DatePicker editdobfield;

        @FXML
        private TextField editpemailfield;

        @FXML
        private TextField editpnomfield;

        @FXML
        private TextField editpprenomfield;

        @FXML
        private TextField editptelfield;

        @FXML
        private Button proform;

        @FXML
        private Button users;

    // Dans le contrôleur du formulaire (FormController)
    public void fillFormData(Professeur p) {
        // Remplir les champs du formulaire avec les informations de l'utilisateur
        editpnomfield.setText(p.getNom());
        editpprenomfield.setText(p.getPrenom());
        editpadressefield.setText(p.getAdresse());
        editpdiscfield.setText(p.getDiscpline());//bel instanceOf
        editptelfield.setText(String.valueOf(p.getTel()));
        editpemailfield.setText(p.getLogin());
        //editpmdp.setText(p.getMdp());


        // Remplir d'autres champs selon les besoins
    }
    @FXML
    void confirmchanges(ActionEvent event) throws SQLException, IOException {
        try {
        ServiceProfesseur sl = new ServiceProfesseur();
        System.out.println("baya");
        String nom = editpnomfield.getText();
        String prenom = editpprenomfield.getText();
        String adresse = editpadressefield.getText();
        String discipline = editpdiscfield.getText();
        LocalDate localDate = editdob.getValue();
        String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        java.sql.Date dateNaissance = java.sql.Date.valueOf(dateString);
        System.out.println(discipline);
        String email = editpemailfield.getText();
        int idy = sl.recupereId(email);
        System.out.println(idy);
        String mdp = pmdp.getText();
        System.out.println(mdp);
        // Tentative de conversion du numéro de téléphone en entier
        int tel = Integer.parseInt(editptelfield.getText());
        // Créer un nouvel objet Professeur avec les données récupérées
        Professeur ppp = new Professeur(idy,nom, prenom, adresse,dateNaissance, tel, email,mdp, discipline);
        System.out.println(ppp);
        // Appeler la méthode de modification dans votre service
            sl.modifier(ppp);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Le professeur est mis à jour avec succès updated ");
            alert.showAndWait();

           FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        editpdiscfield.getScene().setRoot(root);


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }












    public void deconnexion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        editpdiscfield.getScene().setRoot(root);
    }



    @FXML
    void toprof(ActionEvent event)throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        editpdiscfield.getScene().setRoot(root);

    }

    @FXML
    void tousers(ActionEvent event) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        editpdiscfield.getScene().setRoot(root);

    }
}



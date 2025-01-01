package Controller;

import dao.UserImpl;
import entites.Laborantin;
import entites.Medecin;
import entites.Patient;
import entites.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistrationFormController implements Initializable {
    @FXML
    private Button button_register;

    @FXML
    private ComboBox<String> options;

    @FXML
    private TextField tf_confirmPassword;

    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_prenom;

    @FXML
    private TextField tf_telephone;

    @FXML
    private TextField tf_username;

    @FXML
    public void register(ActionEvent event) {
        try {
            // Récupérer les données du formulaire
            String nom = tf_nom.getText();
            String prenom = tf_prenom.getText();
            String username = tf_username.getText();
            String password = tf_password.getText();
            String confirmPassword = tf_confirmPassword.getText();
            String telephone = tf_telephone.getText();
            String selectedRole = options.getValue(); // Récupérer la valeur sélectionnée

            // Vérifications simples
            if (nom.isEmpty() || prenom.isEmpty() || username.isEmpty() || password.isEmpty() || selectedRole == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Champs manquants", "Veuillez remplir tous les champs obligatoires.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Mot de passe incorrect", "Les mots de passe ne correspondent pas.");
                return;
            }

            // Créer un nouvel utilisateur
            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // Le mot de passe sera hashé dans le DAO
            user.setRole(selectedRole);

            // Associer un patient, laborantin ou médecin selon le rôle
            switch (selectedRole) {
                case "ROLE_PATIENT":
                    Patient patient = new Patient();
                    patient.setNom(nom);
                    patient.setPrenom(prenom);
                    patient.setTelephone(telephone);
                    patient.setUser(user); // Lier le patient à l'utilisateur
                    user.setPatient(patient); // Lier l'utilisateur au patient
                    break;

                case "ROLE_LABORANTIN":
                    Laborantin laborantin = new Laborantin();
                    laborantin.setNom(nom);
                    laborantin.setPrenom(prenom);
                    laborantin.setTelephone(telephone);
                    laborantin.setUser(user); // Lier le laborantin à l'utilisateur
                    user.setLaborantin(laborantin); // Lier l'utilisateur au laborantin
                    break;

                case "ROLE_MEDECIN":
                    Medecin medecin = new Medecin();
                    medecin.setNom(nom);
                    medecin.setPrenom(prenom);
                    medecin.setTelephone(telephone);
                    medecin.setUser(user); // Lier le médecin à l'utilisateur
                    user.setMedecin(medecin); // Lier l'utilisateur au médecin
                    break;

                case "ROLE_ADMIN":
                    // Pas d'objet associé pour le rôle Admin
                    break;

                default:
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Rôle invalide", "Veuillez sélectionner un rôle valide.");
                    return;
            }

            // Enregistrer l'utilisateur dans la base de données
            UserImpl userDao = new UserImpl();
            userDao.create(user);

            // Afficher une alerte de succès
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Inscription réussie", "L'utilisateur a été enregistré avec succès !");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur inattendue", "Une erreur s'est produite lors de l'enregistrement de l'utilisateur.");
        }
    }

    // Méthode utilitaire pour afficher des alertes
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

/*
    @FXML
    public void register(ActionEvent event) {
        try {
            // Récupérer les données du formulaire
            String nom = tf_nom.getText();
            String prenom = tf_prenom.getText();
            String username = tf_username.getText();
            String password = tf_password.getText();
            String confirmPassword = tf_confirmPassword.getText();
            String telephone = tf_telephone.getText();
            String selectedRole = options.getValue(); // Récupérer la valeur sélectionnée

            // Vérifications simples (exemple)
            if (nom.isEmpty() || prenom.isEmpty() || username.isEmpty() || password.isEmpty() || selectedRole == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Champs manquants");
                alert.setContentText("Veuillez remplir tous les champs obligatoires.");
                alert.showAndWait();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Mot de passe incorrect");
                alert.setContentText("Les mots de passe ne correspondent pas.");
                alert.showAndWait();
                return;
            }

            // Créer un nouvel utilisateur
            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // Le mot de passe sera hashé dans le DAO
            user.setRole(selectedRole);

            // Associer un patient, laborantin ou médecin selon le rôle
            switch (selectedRole) {
                case "ROLE_PATIENT":
                    Patient patient = new Patient();
                    patient.setNom(nom);
                    patient.setPrenom(prenom);
                    patient.setTelephone(telephone);
                    patient.setUser(user); // Associer le patient à l'utilisateur
                    user.setPatient(patient); // Associer l'utilisateur au patient
                    break;

                case "ROLE_LABORANTIN":
                    Laborantin laborantin = new Laborantin();
                    laborantin.setNom(nom);
                    laborantin.setPrenom(prenom);
                    laborantin.setTelephone(telephone);
                    laborantin.setUser(user);
                    user.setLaborantin(laborantin);
                    break;

                case "ROLE_MEDECIN":
                    Medecin medecin = new Medecin();
                    medecin.setNom(nom);
                    medecin.setPrenom(prenom);
                    medecin.setTelephone(telephone);
                    medecin.setUser(user);
                    user.setMedecin(medecin);
                    break;
                case "ROLE_ADMIN":
                    // L'Admin n'a pas besoin d'objet associé comme Patient, Médecin ou Laborantin
                    break;

                default:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Rôle invalide");
                    alert.setContentText("Veuillez sélectionner un rôle valide.");
                    alert.showAndWait();
                    return;
            }

            // Enregistrer l'utilisateur dans la base de données
            UserImpl userDao = new UserImpl();
            userDao.create(user);

            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Inscription réussie");
            alert.setContentText("L'utilisateur a été enregistré avec succès !");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            // Afficher une alerte d'erreur en cas d'exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur inattendue");
            alert.setContentText("Une erreur s'est produite lors de l'enregistrement de l'utilisateur.");
            alert.showAndWait();
        }
    }*/


    @FXML
    public void selectRole(){
        List<String> listU=new ArrayList<>();
        for (String data: Roles.roles){
            listU.add(data);
        }
        ObservableList listData= FXCollections.observableArrayList(listU);
        options.setItems(listData);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectRole();
    }
}

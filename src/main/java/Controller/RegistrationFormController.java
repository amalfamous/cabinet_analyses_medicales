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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private Button button_sortir;

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

    @FXML
    void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir sortir ?");

        // Récupérer la réponse de l'utilisateur
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.isPresent() && option.get() == ButtonType.OK) {
                Stage currentStage = (Stage) button_sortir.getScene().getWindow();
                currentStage.close();
                Parent root = null;
                Stage stage = new Stage();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/UI/login.fxml")));
                stage.setScene(scene);
                stage.setTitle("Login");
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

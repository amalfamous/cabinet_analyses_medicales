package Controller;

import dao.UserImpl;
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
import java.util.ResourceBundle;
//AdminEspaceController
public class FXMLDocumentController implements Initializable {

    @FXML
    private ComboBox<String> admin_user;

    @FXML
    private Button button_loggin;

    @FXML
    private Button button_sign_up;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_username;

    private Alert alert;

    private void errorMessage(String message){
        alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Message d'erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
    private void successMessage(String message){
        alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message d'information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
    public void loginAccount() {
        String username = tf_username.getText();
        String password = tf_password.getText();
        System.out.println("Nom d'utilisateur : " + username);
        System.out.println("Mot de passe : " + password);

        if (username.isEmpty() || password.isEmpty()) { // Vérification des champs vides
            errorMessage("Veuillez remplir tous les champs !");
        } else {
            // Appel à la méthode authenticate
            UserImpl userDao = new UserImpl();
            User user = userDao.authenticate(username, password);

            if (user != null) { // Si l'utilisateur est authentifié
                successMessage("Connexion réussie !");
                // Rediriger vers l'espace correspondant à l'utilisateur
                try {
                    Parent root = null;
                    String role = user.getRole();
                    if ("ROLE_PATIENT".equalsIgnoreCase(role)) {
                        root = FXMLLoader.load(getClass().getResource("/UI/PatientEspace.fxml"));
                    } else if ("ROLE_LABORANTIN".equalsIgnoreCase(role)) {
                        root = FXMLLoader.load(getClass().getResource("/UI/LaborantinEspace.fxml"));
                    } else if ("ROLE_MEDECIN".equalsIgnoreCase(role)) {
                        root = FXMLLoader.load(getClass().getResource("/UI/MedecinEspace.fxml"));
                    } else if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
                        root = FXMLLoader.load(getClass().getResource("/UI/AdminEspace.fxml"));
                    } else {
                        errorMessage("Rôle inconnu !");
                        return;
                    }
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                    tf_username.getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorMessage("Erreur lors du chargement de l'interface !");
                }
            } else { // Si l'utilisateur n'est pas authentifié
                errorMessage("Nom d'utilisateur ou mot de passe incorrect !");
            }
            System.out.println("Utilisateur authentifié : " + (user != null ? user.getUsername() : "null"));

        }
    }


    public void switchForm(ActionEvent event) {
        try{
            Parent root=null;
            if (admin_user.getSelectionModel().getSelectedItem().equals("espace patient")) {
                root= FXMLLoader.load(getClass().getResource("/UI/PatientEspace.fxml"));

            }
            else if (admin_user.getSelectionModel().getSelectedItem().equals("espace laborantin")){
                root= FXMLLoader.load(getClass().getResource("/UI/laborantinEspace.fxml"));
            }
            else if (admin_user.getSelectionModel().getSelectedItem().equals("espace medecin")){
                root= FXMLLoader.load(getClass().getResource("/UI/MedecinEspace.fxml"));
            }
            else if (admin_user.getSelectionModel().getSelectedItem().equals("espace admin")){
                root= FXMLLoader.load(getClass().getResource("/UI/login.fxml"));
            }
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            admin_user.getScene().getWindow().hide();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void selectUser(){
        List<String> listU=new ArrayList<>();
        for (String data: Users.users){
            listU.add(data);
        }
        ObservableList listData= FXCollections.observableArrayList(listU);
        admin_user.setItems(listData);
    }

    @FXML
    public void btnSignup(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/UI/registerForm.fxml"));
        Scene scene = new Scene(root);

        // Récupérer le Stage à partir de l'événement
        Stage primaryStage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Formulaire d'inscription");
        primaryStage.centerOnScreen();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectUser();
    }
}

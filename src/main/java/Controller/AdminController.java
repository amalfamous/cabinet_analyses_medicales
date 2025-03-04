package Controller;


import dao.AnalyseImpl;
import dao.UserImpl;
import entites.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TextField btn_rechercher21;

    @FXML
    private TextField btn_rechercher22111;

    @FXML
    private Button button_logout;

    @FXML
    private Button button_resultatsAnalyses1;

    @FXML
    private TableColumn<?, ?> col_dateFacture1111;

    @FXML
    private TableColumn<User, String> col_username;

    @FXML
    private TableColumn<?, ?> col_etatPaiementF1111;

    @FXML
    private TableColumn<User, Integer> col_UserId;

    @FXML
    private TableColumn<User, String> col_laborantin;

    @FXML
    private TableColumn<User, String> col_medecin;


    @FXML
    private TableColumn<User, String> col_patient;

    @FXML
    private TableColumn<User, String> col_role;

    @FXML
    private AnchorPane form_facture1111;

    @FXML
    private AnchorPane form_users;

    @FXML
    private AnchorPane userPane;

    @FXML
    private Label username;

    @FXML
    private TableView<User> users_tableView;

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        // Récupérer la réponse de l'utilisateur
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.isPresent() && option.get() == ButtonType.OK) {
                // Ferme la fenêtre actuelle
                Stage currentStage = (Stage) button_logout.getScene().getWindow();
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
    void rechercherFacture(ActionEvent event) {

    }

    @FXML
    void rechercherUser(ActionEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) {

    }
    public void loadUsers() {
        UserImpl userDao = new UserImpl();
        List<User> analyses = userDao.findAll();
        col_UserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_medecin.setCellValueFactory(data -> {
            User user = data.getValue();
            if (user != null && user.getMedecin() != null) {
                return new SimpleStringProperty(user.getMedecin().getNom());
            }
            return new SimpleStringProperty("N/A");
        });
        col_laborantin.setCellValueFactory(data -> {
            User user = data.getValue();
            if (user != null && user.getLaborantin() != null) {
                return new SimpleStringProperty(user.getLaborantin().getNom());
            }
            return new SimpleStringProperty("N/A");
        });
        col_patient.setCellValueFactory(data -> {
            User user = data.getValue();
            if (user != null && user.getPatient() != null) {
                return new SimpleStringProperty(user.getPatient().getNom());
            }
            return new SimpleStringProperty("N/A");
        });

        ObservableList<User> userObservableList = FXCollections.observableArrayList(analyses);
        users_tableView.setItems(userObservableList);
    }

    public void displayUsername(){
        username.setText(getData.username);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        loadUsers();
    }
}

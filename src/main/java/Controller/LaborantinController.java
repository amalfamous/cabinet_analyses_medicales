package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LaborantinController implements Initializable {


    @FXML
    private TableView<?> analyses_tableView;

    @FXML
    private Button btn_ajouter;

    @FXML
    private Button btn_effacer;

    @FXML
    private Button btn_importer;

    @FXML
    private TextField btn_rechercher;

    @FXML
    private Button btn_supprimer;

    @FXML
    private Button btn_update;

    @FXML
    private Button button_analyses;

    @FXML
    private Button button_dashboard;

    @FXML
    private Button button_logout;

    @FXML
    private Button button_resultatsAnalyses;

    @FXML
    private TableColumn<?, ?> col_analyseId;

    @FXML
    private TableColumn<?, ?> col_analyseNom;

    @FXML
    private TableColumn<?, ?> col_analyse_description;

    @FXML
    private TableColumn<?, ?> col_analyse_prix;

    @FXML
    private TextField descriptionAnalyse;

    @FXML
    private AnchorPane form_analyses;

    @FXML
    private AreaChart<?, ?> graphe;

    @FXML
    private TextField nomAnalyse;

    @FXML
    private TextField prixAnalyse;

    @FXML
    private Label username;

    @FXML
    private Label vlr1;

    @FXML
    private Label vlr2;

    @FXML
    private Label vlr3;

    @FXML
    void analyses(ActionEvent event) {

    }

    @FXML
    void dashboard(ActionEvent event) {

    }

    @FXML
    void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        // Récupérer la réponse de l'utilisateur
        Optional<ButtonType> option = alert.showAndWait();
        try {
            // Si l'utilisateur confirme, on ferme la fenêtre actuelle et on charge la fenêtre de connexion
            if (option.isPresent() && option.get() == ButtonType.OK) {
                // Ferme la fenêtre actuelle
                Stage currentStage = (Stage) button_logout.getScene().getWindow();
                currentStage.close();  // Ferme la fenêtre laborantin
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
    void resultatsAnalyses(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

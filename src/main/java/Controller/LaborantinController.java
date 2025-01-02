package Controller;

import dao.AnalyseImpl;
import entites.Analyse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LaborantinController implements Initializable {
    @FXML
    private TableView<Analyse> analyses_tableView;

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
    private TableColumn<Analyse, Integer> col_analyseId;

    @FXML
    private TableColumn<Analyse, String> col_analyseNom;

    @FXML
    private TableColumn<Analyse, String> col_analyse_description;

    @FXML
    private TableColumn<Analyse, Double> col_analyse_prix;

    @FXML
    private AnchorPane dashboard;

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

    public void displayUsername(){
        username.setText(getData.username);
    }
    public void switchForm(ActionEvent event){
        if (event.getSource()==button_dashboard){
            form_analyses.setVisible(false);
            dashboard.setVisible(true);

            button_dashboard.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_analyses.setStyle("-fx-background-color: transparent");
            //form_analyses.setStyle("-fx-background-color: transparent");   // diri hna resultat d'analyses
        } else if (event.getSource()==button_analyses) {
            form_analyses.setVisible(true);
            dashboard.setVisible(false);
            form_analyses.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            button_dashboard.setStyle("-fx-background-color: transparent");

        }else if (event.getSource()==button_resultatsAnalyses) {
            form_analyses.setVisible(false);
            dashboard.setVisible(false);
            //button_dashboard.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_analyses.setStyle("-fx-background-color: transparent");
            button_dashboard.setStyle("-fx-background-color: transparent");

        }

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
    @FXML
    void supprimerAnalyse(ActionEvent event) {
        Analyse analyse = analyses_tableView.getSelectionModel().getSelectedItem();
        if (analyse == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner une analyse à supprimer !");
            alert.show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette analyse ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            AnalyseImpl analyseDao = new AnalyseImpl();
            analyseDao.delete((long) analyse.getId());

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("Analyse supprimée avec succès !");
            success.show();

            loadAnalyses(); // Recharge la liste
        }
    }


    @FXML
    void mettreAJourAnalyse(ActionEvent event) {
        Analyse analyse = analyses_tableView.getSelectionModel().getSelectedItem();
        if (analyse == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner une analyse à mettre à jour !");
            alert.show();
            return;
        }
        String nom = nomAnalyse.getText();
        String description = descriptionAnalyse.getText();
        String prixText = prixAnalyse.getText();
        if (nom.isEmpty() && description.isEmpty() && prixText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir au moins un champ pour effectuer une mise à jour !");
            alert.show();
            return;
        }
        try {
            // Mettre à jour uniquement les champs renseignés
            if (!nom.isEmpty()) {
                analyse.setNom(nom);
            }
            if (!description.isEmpty()) {
                analyse.setDescription(description);
            }
            if (!prixText.isEmpty()) {
                double prix = Double.parseDouble(prixText);
                analyse.setPrix(prix);
            }
            AnalyseImpl analyseDao = new AnalyseImpl();
            analyseDao.update((long) analyse.getId(), analyse);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Analyse mise à jour avec succès !");
            alert.show();
            loadAnalyses();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le prix doit être un nombre valide !");
            alert.show();
        }
    }

    @FXML
    void effacerChamps() {
        nomAnalyse.clear();
        descriptionAnalyse.clear();
        prixAnalyse.clear();
    }

    @FXML
    private void rechercherAnalyse() {
        // Récupérer la chaîne de recherche
        String query = btn_rechercher.getText().toLowerCase();

        // Récupérer toutes les données actuelles du TableView
        ObservableList<Analyse> tableData = analyses_tableView.getItems();

        // Réinitialiser si le champ de recherche est vide
        if (query.isEmpty()) {
            analyses_tableView.setVisible(true);
            analyses_tableView.refresh(); // Pour réafficher toutes les lignes
            return;
        }

        // Filtrer les données visibles dans le TableView
        FilteredList<Analyse> filteredData = new FilteredList<>(tableData, analyse -> {
            // Vérifier si la recherche correspond à l'une des colonnes
            return analyse.getNom().toLowerCase().contains(query) ||
                    analyse.getDescription().toLowerCase().contains(query) ||
                    String.valueOf(analyse.getPrix()).contains(query);
        });

        // Appliquer le filtre au TableView
        analyses_tableView.setItems(filteredData);
        analyses_tableView.refresh(); // Mise à jour de l'affichage
    }


    @FXML
    void ajouterAnalyse(ActionEvent event) {
        String nom = nomAnalyse.getText();
        String description = descriptionAnalyse.getText();
        String prixText = prixAnalyse.getText();

        if (nom.isEmpty() || description.isEmpty() || prixText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.show();
            return;
        }

        try {
            double prix = Double.parseDouble(prixText);
            Analyse analyse = new Analyse();
            analyse.setNom(nom);
            analyse.setDescription(description);
            analyse.setPrix(prix);

            AnalyseImpl analyseDao = new AnalyseImpl();
            analyseDao.create(analyse);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Analyse ajoutée avec succès !");
            alert.show();

            loadAnalyses(); // Recharge la liste
            effacerChamps(); // Efface les champs
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le prix doit être un nombre valide !");
            alert.show();
        }
    }

    public void loadAnalyses() {
        AnalyseImpl analyseDao = new AnalyseImpl();
        List<Analyse> analyses = analyseDao.findAll();
        col_analyseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_analyseNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_analyse_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_analyse_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        // Ajoute les analyses à la TableView
        ObservableList<Analyse> analyseObservableList = FXCollections.observableArrayList(analyses);
        analyses_tableView.setItems(analyseObservableList);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        loadAnalyses();

    }
}

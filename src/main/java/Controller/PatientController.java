package Controller;

import dao.FactureImpl;
import dao.OrdreAnalyseImpl;
import dao.ResultatAnalyseImpl;
import entites.Facture;
import entites.OrdreAnalyse;
import entites.ResultatAnalyse;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PatientController implements Initializable {

    @FXML
    private TextField btn_rechercher;

    @FXML
    private TextField btn_rechercher2;

    @FXML
    private TextField btn_rechercher21;

    @FXML
    private Button button_logout;

    @FXML
    private Button button_ordreAnalyses;

    @FXML
    private Button button_factures;

    @FXML
    private Button button_resultatsAnalyses;


    @FXML
    private TableColumn<ResultatAnalyse, LocalDateTime> col_date_resultatAnalyse;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_detail_resultatAnalyse;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_Analyse_resultatAnalyse;

    @FXML
    private TableColumn<Facture, LocalDateTime> col_dateFacture;

    @FXML
    private TableColumn<Facture, Boolean> col_etatPaiementF;

    @FXML
    private TableColumn<Facture, Integer> col_factureId;

    @FXML
    private TableColumn<Facture, Double> col_montantTotal;


    @FXML
    private TableColumn<OrdreAnalyse, LocalDateTime> col_date_ordre_analyse;

    @FXML
    private TableColumn<OrdreAnalyse, Integer> col_ordreAnalyseId;

    @FXML
    private TableColumn<OrdreAnalyse, String> col_resultat;

    @FXML
    private TableColumn<OrdreAnalyse, String> col_medecin;


    @FXML
    private TableColumn<ResultatAnalyse, Integer> col_resultatAnalyseId;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_valeurKey_resultatAnalyse;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_valeurValue_resultatAnalyse;

    @FXML
    private TableView<Facture> facture_tableView;

    @FXML
    private AnchorPane form_ordreA;

    @FXML
    private AnchorPane form_resultatAnalyse;

    @FXML
    private AnchorPane form_facture;

    @FXML
    private TableView<OrdreAnalyse> ordreAnalyse_tableView;

    @FXML
    private TableView<ResultatAnalyse> resultatAnalyses_tableView;

    @FXML
    private Label username;

    @FXML
    void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message de confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir vous déconnecter ?");

        // Récupérer la réponse de l'utilisateur
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.isPresent() && option.get() == ButtonType.OK) {
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
        String query = btn_rechercher2.getText().toLowerCase();
        ObservableList<Facture> tableData = facture_tableView.getItems();

        if (query.isEmpty()) {
            facture_tableView.setVisible(true);
            facture_tableView.refresh();
            return;
        }

        FilteredList<Facture> filteredData = new FilteredList<>(tableData, facture -> {
            boolean matchesId = String.valueOf(facture.getId()).contains(query);
            boolean matchesEtat = String.valueOf(facture.isEtatPaiement()).toLowerCase().contains(query);
            boolean matchesMontant = String.valueOf(facture.getMontantTotal()).contains(query);
            boolean matchesDate = facture.getDateFacture() != null &&
                    String.valueOf(facture.getDateFacture()).toLowerCase().contains(query);
            return matchesId || matchesEtat || matchesDate || matchesMontant;
        });

        facture_tableView.setItems(filteredData);
        facture_tableView.refresh();
    }

    @FXML
    void rechercherOrdreAnalyse(ActionEvent event) {
        String query = btn_rechercher21.getText().toLowerCase();
        ObservableList<OrdreAnalyse> tableData = ordreAnalyse_tableView.getItems();

        if (query.isEmpty()) {
            ordreAnalyse_tableView.setVisible(true);
            ordreAnalyse_tableView.refresh();
            return;
        }

        FilteredList<OrdreAnalyse> filteredData = new FilteredList<>(tableData, ordreAnalyse -> {
            boolean matchesId = String.valueOf(ordreAnalyse.getId()).contains(query);
            boolean matchesDate = ordreAnalyse.getDateOrdre() != null &&
                    String.valueOf(ordreAnalyse.getDateOrdre()).toLowerCase().contains(query);
            boolean matchesMedecin = ordreAnalyse.getMedecin() != null &&
                    ordreAnalyse.getMedecin().getNom().toLowerCase().contains(query);
            boolean matchesResultat = ordreAnalyse.getResultatAnalyses() != null &&
                    ordreAnalyse.getResultatAnalyses().stream()  // Utilisation du stream pour parcourir la liste
                            .anyMatch(resultat -> resultat.getDetailsResultat() != null &&
                                    resultat.getDetailsResultat().toLowerCase().contains(query));

            return matchesId || matchesMedecin || matchesDate || matchesResultat;
        });

        ordreAnalyse_tableView.setItems(filteredData);
        ordreAnalyse_tableView.refresh();
    }

    @FXML
    private void rechercherResultatAnalyse() {
        String query = btn_rechercher.getText().toLowerCase();
        ObservableList<ResultatAnalyse> tableData = resultatAnalyses_tableView.getItems();

        if (query.isEmpty()) {
            resultatAnalyses_tableView.setVisible(true);
            resultatAnalyses_tableView.refresh();
            return;
        }

        FilteredList<ResultatAnalyse> filteredData = new FilteredList<>(tableData, resultat -> {
            boolean matchesId = String.valueOf(resultat.getId()).contains(query);
            boolean matchesDetails = resultat.getDetailsResultat().toLowerCase().contains(query);
            boolean matchesDate = resultat.getDateResultat() != null &&
                    String.valueOf(resultat.getDateResultat()).toLowerCase().contains(query);
            boolean matchesAnalyse = resultat.getAnalyse() != null &&
                    resultat.getAnalyse().getNom().toLowerCase().contains(query);
            boolean matchesValeurKey = resultat.getValeurs().keySet().stream()
                    .anyMatch(key -> key.toLowerCase().contains(query));
            boolean matchesValeurValue = resultat.getValeurs().values().stream()
                    .anyMatch(value -> String.valueOf(value).toLowerCase().contains(query));
            return matchesId || matchesDetails || matchesDate || matchesAnalyse || matchesValeurKey || matchesValeurValue;
        });

        resultatAnalyses_tableView.setItems(filteredData);
        resultatAnalyses_tableView.refresh();
    }


    public void displayUsername() {
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == button_factures) {
            form_resultatAnalyse.setVisible(false);
            form_ordreA.setVisible(false);
            form_facture.setVisible(true);

            form_facture.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_resultatAnalyse.setStyle("-fx-background-color: transparent");
            form_ordreA.setStyle("-fx-background-color: transparent");
        } else if (event.getSource() == button_ordreAnalyses) {
            form_ordreA.setVisible(true);
            form_resultatAnalyse.setVisible(false);
            form_facture.setVisible(false);

            form_ordreA.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_resultatAnalyse.setStyle("-fx-background-color: transparent");
            form_facture.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == button_resultatsAnalyses) {
            form_facture.setVisible(false);
            form_ordreA.setVisible(false);
            form_resultatAnalyse.setVisible(true);

            form_resultatAnalyse.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_ordreA.setStyle("-fx-background-color: transparent");
            form_facture.setStyle("-fx-background-color: transparent");

        }

    }

    public void loadResultatsAnalyses() {
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();
        List<ResultatAnalyse> resultatAnalyses = resultatAnalyseDao.findAll();

        col_resultatAnalyseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_detail_resultatAnalyse.setCellValueFactory(new PropertyValueFactory<>("detailsResultat"));
        col_date_resultatAnalyse.setCellValueFactory(new PropertyValueFactory<>("dateResultat"));

        col_Analyse_resultatAnalyse.setCellValueFactory(data -> {
            ResultatAnalyse resultat = data.getValue();// Récupère un objet ResultatAnalyse pour la ligne actuelle
            if (resultat != null && resultat.getAnalyse() != null) {
                return new SimpleStringProperty(resultat.getAnalyse().getNom());
            }
            return new SimpleStringProperty("N/A");
        });

        col_valeurKey_resultatAnalyse.setCellValueFactory(data -> {
            // Extraire les clés de la carte
            String keys = String.join(", ", data.getValue().getValeurs().keySet());
            return new SimpleStringProperty(keys);
        });

        col_valeurValue_resultatAnalyse.setCellValueFactory(data -> {
            // Extraire les valeurs de la carte
            String values = data.getValue().getValeurs().values().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(values);
        });
        ObservableList<ResultatAnalyse> resultatAnalyseObservableList = FXCollections.observableArrayList(resultatAnalyses);
        resultatAnalyses_tableView.setItems(resultatAnalyseObservableList);
    }

    public void loadFacture() {
        FactureImpl factureDao = new FactureImpl();
        List<Facture> factures = factureDao.findAll();

        col_factureId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_montantTotal.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        col_dateFacture.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        col_etatPaiementF.setCellValueFactory(new PropertyValueFactory<>("etatPaiement"));

        ObservableList<Facture> factureObservableList = FXCollections.observableArrayList(factures);
        facture_tableView.setItems(factureObservableList);
    }

    public void loadOrdreAnalyses() {
        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        List<OrdreAnalyse> ordreAnalyses = ordreAnalyseDao.findAll();
        col_ordreAnalyseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_date_ordre_analyse.setCellValueFactory(new PropertyValueFactory<>("dateOrdre"));
        col_medecin.setCellValueFactory(data -> {
            OrdreAnalyse ordreAnalyse = data.getValue();
            if (ordreAnalyse != null && ordreAnalyse.getMedecin() != null) {
                return new SimpleStringProperty(ordreAnalyse.getMedecin().getNom());
            }
            return new SimpleStringProperty("N/A");
        });

        col_resultat.setCellValueFactory(data -> {
            OrdreAnalyse ordreAnalyse = data.getValue();
            if (ordreAnalyse != null && ordreAnalyse.getResultatAnalyses() != null && !ordreAnalyse.getResultatAnalyses().isEmpty()) {
                StringBuilder resultDetails = new StringBuilder();
                for (ResultatAnalyse resultat : ordreAnalyse.getResultatAnalyses()) {
                    resultDetails.append(resultat.getDetailsResultat()).append("\n");
                }
                return new SimpleStringProperty(resultDetails.toString());
            }
            return new SimpleStringProperty("Aucun résultat disponible");
        });

        ObservableList<OrdreAnalyse> ordreAnalyseObservableList = FXCollections.observableArrayList(ordreAnalyses);
        ordreAnalyse_tableView.setItems(ordreAnalyseObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadResultatsAnalyses();
        loadOrdreAnalyses();
        loadFacture();
        displayUsername();
    }
}

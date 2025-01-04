package Controller;

import dao.*;
import entites.*;
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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class LaborantinController implements Initializable {

    @FXML
    private TextField Valeur_key;

    @FXML
    private TextField Valeur_value;
    @FXML
    private Text DetailsResultat;

    @FXML
    private TableView<Analyse> analyses_tableView;

    @FXML
    private TableView<ResultatAnalyse> resultat_analyses_tableView;

    @FXML
    private Button btn_ajouter;

    @FXML
    private Button btn_ajouter1;

    @FXML
    private Button btn_effacer;

    @FXML
    private Button btn_effacer1;

    @FXML
    private TextField btn_rechercher;
    @FXML
    private TextField btn_rechercher2;

    @FXML
    private Button btn_supprimer;

    @FXML
    private Button btn_supprimer1;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_update1;

    @FXML
    private Button button_analyses;

    @FXML
    private Button button_dashboard;

    @FXML
    private Button button_logout;

    @FXML
    private Button button_resultatsAnalyses;


    @FXML
    private TableColumn<ResultatAnalyse, String> col_valeur_key;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_valeur_value;

    @FXML
    private TableColumn<Analyse, Integer> col_analyseId;

    @FXML
    private TableColumn<Analyse, String> col_analyseNom;

    @FXML
    private TableColumn<Analyse, String> col_analyse_description;

    @FXML
    private TableColumn<Analyse, Double> col_analyse_prix;

    @FXML
    private TableColumn<ResultatAnalyse, LocalDateTime> col_dateResultat;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_detailsResultat;

    @FXML
    private TableColumn<ResultatAnalyse, Integer> col_ordreAnalyseId;

    @FXML
    private AnchorPane dashboard;

    @FXML
    private DatePicker dateResultat;

    @FXML
    private TextField descriptionAnalyse;

    @FXML
    private TextField detailsResultat;

    @FXML
    private AnchorPane form_analyses;

    @FXML
    private AnchorPane form_ordreAnalyses;

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
            form_ordreAnalyses.setVisible(false);
            dashboard.setVisible(true);

            button_dashboard.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_analyses.setStyle("-fx-background-color: transparent");
            form_ordreAnalyses.setStyle("-fx-background-color: transparent");
        } else if (event.getSource()==button_analyses) {
            form_analyses.setVisible(true);
            dashboard.setVisible(false);
            form_ordreAnalyses.setVisible(false);

            form_analyses.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            button_dashboard.setStyle("-fx-background-color: transparent");
            form_ordreAnalyses.setStyle("-fx-background-color: transparent");

        }else if (event.getSource()==button_resultatsAnalyses) {
            form_analyses.setVisible(false);
            dashboard.setVisible(false);
            form_ordreAnalyses.setVisible(true);

            form_ordreAnalyses.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
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
            loadAnalyses();
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
        String query = btn_rechercher.getText().toLowerCase();
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
        analyses_tableView.setItems(filteredData);
        analyses_tableView.refresh();
    }


    @FXML
    private void rechercherResultatAnalyse() {
        String query = btn_rechercher2.getText().toLowerCase();
        ObservableList<ResultatAnalyse> tableData = resultat_analyses_tableView.getItems();

        if (query.isEmpty()) {
            resultat_analyses_tableView.setVisible(true);
            resultat_analyses_tableView.refresh();
            return;
        }

        FilteredList<ResultatAnalyse> filteredData = new FilteredList<>(tableData, resultat ->
                resultat.getDetailsResultat().toLowerCase().contains(query) ||
                        String.valueOf(resultat.getDateResultat()).contains(query) ||
                        resultat.getValeurs().entrySet().stream().anyMatch(entry ->
                                entry.getKey().toLowerCase().contains(query) ||
                                        String.valueOf(entry.getValue()).contains(query)
                        )
        );

        resultat_analyses_tableView.setItems(filteredData);
        resultat_analyses_tableView.refresh();
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

            loadAnalyses();
            effacerChamps();
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
    public void loadResultatsAnalyses() {
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();
        List<ResultatAnalyse> resultatAnalyses = resultatAnalyseDao.findAll();

        col_ordreAnalyseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_detailsResultat.setCellValueFactory(new PropertyValueFactory<>("detailsResultat"));
        col_dateResultat.setCellValueFactory(new PropertyValueFactory<>("dateResultat"));

        // Configurer les colonnes valeur_key et valeur_value
        col_valeur_key.setCellValueFactory(data -> {
            // Extraire les clés de la carte
            String keys = String.join(", ", data.getValue().getValeurs().keySet());
            return new SimpleStringProperty(keys);
        });

        col_valeur_value.setCellValueFactory(data -> {
            // Extraire les valeurs de la carte
            String values = data.getValue().getValeurs().values().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(values);
        });

        // Ajouter les analyses à la TableView
        ObservableList<ResultatAnalyse> resultatAnalyseObservableList = FXCollections.observableArrayList(resultatAnalyses);
        resultat_analyses_tableView.setItems(resultatAnalyseObservableList);
    }


    @FXML
    void ajouterResultatAnalyse(ActionEvent event) {
        String details = detailsResultat.getText();
        LocalDateTime dateRes = (this.dateResultat.getValue() != null) ? this.dateResultat.getValue().atStartOfDay() : null;
        String valeurKey = Valeur_key.getText();
        String valeurValue = Valeur_value.getText();

        if (details.isEmpty() || dateRes == null || valeurKey.isEmpty() || valeurValue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.show();
            return;
        }
        ResultatAnalyse resultat = new ResultatAnalyse();
        resultat.setDetailsResultat(details);
        resultat.setDateResultat(dateRes);
        try {
            double value = Double.parseDouble(valeurValue);
            resultat.getValeurs().put(valeurKey, value);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("La valeur doit être un nombre valide !");
            alert.show();
            return;
        }

        ResultatAnalyseImpl resultatDao = new ResultatAnalyseImpl();
        resultatDao.create(resultat);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Résultat d'analyse ajouté avec succès !");
        alert.show();

        loadResultatsAnalyses();
        effacerChampsResultat();
    }

   /* @FXML
    void mettreAJourResultat(ActionEvent event) {
        ResultatAnalyse resultat = resultat_analyses_tableView.getSelectionModel().getSelectedItem();
        if (resultat == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner un résultat à mettre à jour !");
            alert.show();
            return;
        }

        String details = detailsResultat.getText();
        LocalDateTime dateRes = (this.dateResultat.getValue() != null) ? this.dateResultat.getValue().atStartOfDay() : null;
        String valeurKey = Valeur_key.getText();
        String valeurValue = Valeur_value.getText();

        if (!details.isEmpty()) {
            resultat.setDetailsResultat(details);
        }
        if (dateRes != null) {
            resultat.setDateResultat(dateRes);
        }

        // Mise à jour des paires clé/valeur
        if (!valeurKey.isEmpty() && !valeurValue.isEmpty()) {
            try {
                double value = Double.parseDouble(valeurValue);
                resultat.getValeurs().put(valeurKey, value);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("La valeur doit être un nombre valide !");
                alert.show();
                return;
            }
        }

        ResultatAnalyseImpl resultatDao = new ResultatAnalyseImpl();
        resultatDao.update((long) resultat.getId(), resultat);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Résultat d'analyse mis à jour avec succès !");
        alert.show();

        loadResultatsAnalyses();
        effacerChampsResultat();
    }
*/
   @FXML
   void mettreAJourResultat(ActionEvent event) {
       ResultatAnalyse resultat = resultat_analyses_tableView.getSelectionModel().getSelectedItem();
       if (resultat != null) {
           System.out.println("Résultat sélectionné : " + resultat);
       } else {
           System.out.println("Aucun résultat sélectionné !");
       }
       if (resultat == null) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Veuillez sélectionner un résultat à mettre à jour !");
           alert.show();
           return;
       }
       // Récupérer les entrées utilisateur
       String details = detailsResultat.getText();
       LocalDateTime dateRes = (this.dateResultat.getValue() != null) ? this.dateResultat.getValue().atStartOfDay() : null;
       String valeurKey = Valeur_key.getText();
       String valeurValue = Valeur_value.getText();
       System.out.println("Details: " + details);
       System.out.println("Date Resultat: " + dateRes);
       System.out.println("Valeur Key: " + valeurKey);
       System.out.println("Valeur Value: " + valeurValue);
       if (!details.isEmpty()) {
           resultat.setDetailsResultat(details);
       }
       if (dateRes != null) {
           resultat.setDateResultat(dateRes);
       }
       if (!valeurKey.isEmpty() && !valeurValue.isEmpty()) {
           try {
               double value = Double.parseDouble(valeurValue);
               Map<String, Double> valeurs = resultat.getValeurs();
               if (valeurs == null) {
                   valeurs = new HashMap<>();
               }
               valeurs.put(valeurKey, value);
               resultat.setValeurs(valeurs);
           } catch (NumberFormatException e) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("La valeur doit être un nombre valide !");
               alert.show();
               return;
           }
       }
//fhadi li commentit il crée une nouvelle carte au lieu de mettre à jour la carte existante.
       /*if (!valeurKey.isEmpty() && !valeurValue.isEmpty()) {
           try {
               double value = Double.parseDouble(valeurValue);
               Map<String, Double> newValeurs = new HashMap<>(resultat.getValeurs());
               newValeurs.put(valeurKey, value);
               resultat.setValeurs(newValeurs);
           } catch (NumberFormatException e) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("La valeur doit être un nombre valide !");
               alert.show();
               return;
           }
       }*/

     /*  if (!valeurKey.isEmpty() && !valeurValue.isEmpty()) {
           try {
               double value = Double.parseDouble(valeurValue);
               resultat.getValeurs().put(valeurKey, value);
           } catch (NumberFormatException e) {
               // Alerte si la valeur entrée n'est pas un nombre valide
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setContentText("La valeur doit être un nombre valide !");
               alert.show();
               return;
           }
       }*/
       try {
           ResultatAnalyseImpl resultatDao = new ResultatAnalyseImpl();
           resultatDao.update((long) resultat.getId(), resultat);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("Résultat d'analyse mis à jour avec succès !");
           alert.show();
           loadResultatsAnalyses();
           effacerChampsResultat();
       } catch (Exception e) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setContentText("Une erreur s'est produite lors de la mise à jour du résultat !");
           alert.show();
           e.printStackTrace();
       }
   }


    @FXML
    void supprimerResultatAnalyse(ActionEvent event) {
        ResultatAnalyse resultat = resultat_analyses_tableView.getSelectionModel().getSelectedItem();
        if (resultat == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Aucun résultat sélectionné");
            alert.setContentText("Veuillez sélectionner un résultat à supprimer !");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Suppression d'un résultat d'analyse");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce résultat ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                ResultatAnalyseImpl resultatDao = new ResultatAnalyseImpl();
                resultatDao.delete((long) resultat.getId());
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setHeaderText("Suppression réussie");
                success.setContentText("Le résultat d'analyse a été supprimé avec succès !");
                success.show();
                loadResultatsAnalyses();
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText("Erreur lors de la suppression");
                error.setContentText("Une erreur est survenue lors de la suppression du résultat : " + e.getMessage());
                error.show();
                e.printStackTrace();
            }
        }
    }





    @FXML
    void effacerChampsResultat() {
        detailsResultat.clear();
        dateResultat.setValue(null);
        nomAnalyse.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        loadAnalyses();
        loadResultatsAnalyses();

    }
}

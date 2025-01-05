package Controller;

import dao.AnalyseImpl;
import dao.OrdreAnalyseImpl;
import dao.PatientImpl;
import dao.ResultatAnalyseImpl;
import entites.Analyse;
import entites.OrdreAnalyse;
import entites.Patient;
import entites.ResultatAnalyse;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class MedecinController implements Initializable {

    @FXML
    private Text DetailsResultat;

    @FXML
    private Text DetailsResultat1;

    @FXML
    private Text DetailsResultat2;

    @FXML
    private Text DetailsResultat21;

    @FXML
    private TextField adresse_patient;

    @FXML
    private Button btn_ajouter1;

    @FXML
    private Button btn_ajouter11;

    @FXML
    private Button btn_effacer1;

    @FXML
    private Button btn_effacer11;

    @FXML
    private TextField btn_rechercher;

    @FXML
    private TextField btn_rechercher2;

    @FXML
    private TextField btn_rechercher21;

    @FXML
    private Button btn_supprimer1;

    @FXML
    private Button btn_supprimer11;

    @FXML
    private Button btn_update1;

    @FXML
    private Button btn_update11;

    @FXML
    private Button button_ordreAnalyses;

    @FXML
    private Button button_dashboard;

    @FXML
    private Button button_logout;

    @FXML
    private Button button_patients;

    @FXML
    private Button button_resultatsAnalyses;


    @FXML
    private AnchorPane col_Nom_patient;

    @FXML
    private TableColumn<Patient, String> col_adresse_patient;

    @FXML
    private TableColumn<Patient, LocalDateTime> col_date_naissance_patient;

    @FXML
    private TableColumn<Patient, String> col_mail_patient;

    @FXML
    private TableColumn<Patient, Integer> col_nb_ordre;

    @FXML
    private TableColumn<Patient, String> col_nom_patient;

    @FXML
    private TableColumn<Patient, Integer> col_patientId;


    @FXML
    private TableColumn<Patient, String> col_prenom_patient;


    @FXML
    private TableColumn<Patient, String> col_tele_patient;

    @FXML
    private TableColumn<OrdreAnalyse, LocalDateTime> col_date_ordre_analyse;

    @FXML
    private TableColumn<OrdreAnalyse, String> col_patient;

    @FXML
    private TableColumn<OrdreAnalyse, String> col_resultat;

    @FXML
    private TableColumn<OrdreAnalyse, Integer> col_ordreAnalyseId;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_Analyse_resultatAnalyse;

    @FXML
    private TableColumn<ResultatAnalyse, LocalDateTime> col_date_resultatAnalyse;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_detail_resultatAnalyse;


    @FXML
    private TableColumn<ResultatAnalyse, String> col_valeurKey_resultatAnalyse;

    @FXML
    private TableColumn<ResultatAnalyse, String> col_valeurValue_resultatAnalyse;

    @FXML
    private AnchorPane dashboard;

    @FXML
    private DatePicker dateNaissance;

    @FXML
    private DatePicker dateO;

    @FXML
    private AnchorPane form_patient;

    @FXML
    private AnchorPane form_ordreA;

    @FXML
    private AnchorPane form_resultat_analyses;

    @FXML
    private AreaChart<?, ?> graphe;

    @FXML
    private TextField mail_patient;

    @FXML
    private TextField nom_patient;

    @FXML
    private TableView<OrdreAnalyse> ordreAnalyse_tableView;

    @FXML
    private TextField patientOrdreA;

    @FXML
    private TableView<Patient> patient_tableView;

    @FXML
    private TextField prenom_patient;

    @FXML
    private TableView<ResultatAnalyse> resultatAnalyses_tableView;

    @FXML
    private TextField resultatO;

    @FXML
    private TextField tele_patient;

    @FXML
    private Label username;

    @FXML
    private Label vlr1;

    @FXML
    private Label vlr2;

    @FXML
    private Label vlr3;


    @FXML
    void ajouterOrdreAnalyse(ActionEvent event) {
        String nomPatient = patientOrdreA.getText();
        LocalDateTime dateOrdre = (this.dateO.getValue() != null) ? this.dateO.getValue().atStartOfDay() : null;
        String resultatOrdre = resultatO.getText();
        if (nomPatient.isEmpty() || dateOrdre == null || resultatOrdre.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.show();
            return;
        }
        OrdreAnalyse ordreAnalyse = new OrdreAnalyse();
        // Création d'un nouveau Patient (assurez-vous que le Patient existe déjà en base ou créez-le si nécessaire)
        Patient patient = new Patient();  // Assurez-vous que l'objet Patient est initialisé et lié à l'OrdreAnalyse
        patient.setNom(nomPatient);
        ordreAnalyse.setPatient(patient);  // On lie le patient à l'OrdreAnalyse

        ResultatAnalyse resultatAnalyse = new ResultatAnalyse();
        resultatAnalyse.setDetailsResultat(resultatOrdre);
        // Ajouter le résultat à la liste de résultats d'analyses
        ordreAnalyse.getResultatAnalyses().add(resultatAnalyse); // Ajout du résultat d'analyse à la liste

        // Définir la date de l'ordre d'analyse
        ordreAnalyse.setDateOrdre(dateOrdre);

        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        ordreAnalyseDao.create(ordreAnalyse);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Résultat d'analyse ajouté avec succès !");
        alert.show();

        loadOrdreAnalyses();
        effacerChampsOrdreA();
    }

    @FXML
    void mettreAJourOrdreResultat(ActionEvent event) {
        OrdreAnalyse ordreAnalyse = ordreAnalyse_tableView.getSelectionModel().getSelectedItem();
        if (ordreAnalyse == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner un ordre d'analyse à mettre à jour !");
            alert.show();
            return;
        }

        String nomPatient = patientOrdreA.getText();
        LocalDateTime dateOrdre = (this.dateO.getValue() != null) ? this.dateO.getValue().atStartOfDay() : null;
        String resultatOrdre = resultatO.getText();
        if (nomPatient.isEmpty() && resultatOrdre.isEmpty() && dateOrdre == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir au moins un champ pour effectuer une mise à jour !");
            alert.show();
            return;
        }
        try {
            if (!nomPatient.isEmpty()) {
                // Reattach the patient to the session if necessary
                Patient patient =new Patient();
                patient.setNom(nomPatient);
                ordreAnalyse.setPatient(patient);

            }
            if (!resultatOrdre.isEmpty()) {
                ResultatAnalyse resultatAnalyse = new ResultatAnalyse();
                resultatAnalyse.setDetailsResultat(resultatOrdre);
                ordreAnalyse.getResultatAnalyses().add(resultatAnalyse);//kan ajouter resultat d'analyse a la liste
            }
            if (dateO != null) {
                ordreAnalyse.setDateOrdre(dateOrdre);
            }
            OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
            ordreAnalyseDao.update((long) ordreAnalyse.getId(), ordreAnalyse);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Ordre d'analyse mise à jour avec succès !");
            alert.show();
            loadOrdreAnalyses();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        FilteredList<OrdreAnalyse> filteredData = new FilteredList<>(tableData, resultat -> {
            boolean matchDate = String.valueOf(resultat.getDateOrdre()).contains(query);
            boolean matchPatient = resultat.getPatient() != null &&
                    resultat.getPatient().getNom().toLowerCase().contains(query);
            // Vérification si au moins un des résultats d'analyse correspond
            boolean matchResult = false; //kan supposiw bli tachi resultat d'analyse ma correspond la recherche
            if (resultat.getResultatAnalyses() != null) {
                for (ResultatAnalyse resultat1 : resultat.getResultatAnalyses()) {
                    if (resultat1.getDetailsResultat().toLowerCase().contains(query)) {
                        matchResult = true;
                        break;  // On peut s'arrêter dès qu'on trouve une correspondance
                    }
                }
            }
            return matchPatient || matchDate || matchResult;
        });
        ordreAnalyse_tableView.setItems(filteredData);
        ordreAnalyse_tableView.refresh();
    }


    @FXML
    void supprimerOrdreAnalyse(ActionEvent event) {
        OrdreAnalyse ordreAnalyse = ordreAnalyse_tableView.getSelectionModel().getSelectedItem();
        if (ordreAnalyse == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Aucun ordre d'analyse sélectionné");
            alert.setContentText("Veuillez sélectionner un ordre d'analyse à supprimer !");
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
                OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
                ordreAnalyseDao.delete((long) ordreAnalyse.getId());
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setHeaderText("Suppression réussie");
                success.setContentText("L'ordre d'analyse a été supprimé avec succès !");
                success.show();
                loadResultatsAnalyses();
            } catch (Exception e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setTitle("Erreur");
                error.setHeaderText("Erreur lors de la suppression");
                error.setContentText("Une erreur est survenue lors de la suppression d'ordre d'analyse : " + e.getMessage());
                error.show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void mettreAJourPatient(ActionEvent event) {
        Patient patient = patient_tableView.getSelectionModel().getSelectedItem();
        if (patient == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner un patient à mettre à jour !");
            alert.show();
            return;
        }
        String nom = nom_patient.getText();
        String prenom = prenom_patient.getText();
        String adresse = adresse_patient.getText();
        String tele = tele_patient.getText();
        String email = mail_patient.getText();
        LocalDateTime dateRes = (this.dateNaissance.getValue() != null) ? this.dateNaissance.getValue().atStartOfDay() : null;
        if (nom.isEmpty() && prenom.isEmpty() && adresse.isEmpty() && tele.isEmpty() && email.isEmpty() && dateRes != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir au moins un champ pour effectuer une mise à jour !");
            alert.show();
            return;
        }
        try {
            if (!nom.isEmpty()) {
                patient.setNom(nom);
            }
            if (!prenom.isEmpty()) {
                patient.setPrenom(prenom);
            }
            if (!adresse.isEmpty()) {
                patient.setAdresse(adresse);
            }
            if (!tele.isEmpty()) {
                patient.setTelephone(tele);
            }
            if (!email.isEmpty()) {
                patient.setEmail(email);
            }
            if (dateRes != null) {
                patient.setDateNaissance(dateRes);
            }
            PatientImpl patientDao = new PatientImpl();
            patientDao.update((long) patient.getId(), patient);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Patient mise à jour avec succès !");
            alert.show();
            loadPatients();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerPatient(ActionEvent event) {
        Patient patient = patient_tableView.getSelectionModel().getSelectedItem();
        if (patient == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez sélectionner un patient à supprimer !");
            alert.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette patient ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            PatientImpl patientDao = new PatientImpl();
            patientDao.delete((long) patient.getId());
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setContentText("Patient supprimée avec succès !");
            success.show();
            loadPatients();
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

    public void switchForm(ActionEvent event) {
        if (event.getSource() == button_dashboard) {
            form_ordreA.setVisible(false);
            form_patient.setVisible(false);
            form_resultat_analyses.setVisible(false);
            dashboard.setVisible(true);
            button_dashboard.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_ordreA.setStyle("-fx-background-color: transparent");
            form_patient.setStyle("-fx-background-color: transparent");
            form_resultat_analyses.setStyle("-fx-background-color: transparent");
        } else if (event.getSource() == button_ordreAnalyses) {
            form_ordreA.setVisible(true);
            dashboard.setVisible(false);
            form_patient.setVisible(false);
            form_resultat_analyses.setVisible(false);
            form_ordreA.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            button_dashboard.setStyle("-fx-background-color: transparent");
            form_resultat_analyses.setStyle("-fx-background-color: transparent");
            form_resultat_analyses.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == button_resultatsAnalyses) {
            form_ordreA.setVisible(false);
            dashboard.setVisible(false);
            form_patient.setVisible(false);
            form_resultat_analyses.setVisible(true);
            form_resultat_analyses.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_patient.setStyle("-fx-background-color: transparent");
            form_ordreA.setStyle("-fx-background-color: transparent");
            button_dashboard.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == button_patients) {
            form_resultat_analyses.setVisible(false);
            dashboard.setVisible(false);
            form_ordreA.setVisible(false);
            form_patient.setVisible(true);
            form_patient.setStyle("linear-gradient(to bottom right,#535878, #9DB0CE);");
            form_resultat_analyses.setStyle("-fx-background-color: transparent");
            form_ordreA.setStyle("-fx-background-color: transparent");
            button_dashboard.setStyle("-fx-background-color: transparent");

        }

    }

    public void displayUsername() {
        username.setText(getData.username);
    }

    @FXML
    void effacerChampsOrdreA() {
        patientOrdreA.clear();
        resultatO.clear();
        dateO.setValue(null);
    }

    @FXML
    void effacerChampsPatient() {
        nom_patient.clear();
        prenom_patient.clear();
        adresse_patient.clear();
        tele_patient.clear();
        mail_patient.clear();
        dateNaissance.setValue(null);
    }

    @FXML
    void ajouterPatient(ActionEvent event) {
        String nom = nom_patient.getText();
        String prenom = prenom_patient.getText();
        String email = mail_patient.getText();
        String tele = tele_patient.getText();
        LocalDateTime dateRes = (this.dateNaissance.getValue() != null) ? this.dateNaissance.getValue().atStartOfDay() : null;
        String adresse = adresse_patient.getText();
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || tele.isEmpty() || dateRes == null || adresse.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.show();
            return;
        }
        try {
            Patient patient = new Patient();
            patient.setNom(nom);
            patient.setPrenom(prenom);
            patient.setEmail(email);
            patient.setTelephone(tele);
            patient.setDateNaissance(dateRes);
            patient.setAdresse(adresse);

            PatientImpl pateintDao = new PatientImpl();
            pateintDao.create(patient);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Pateint ajoutée avec succès !");
            alert.show();

            loadPatients();
            effacerChampsPatient();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Patient n'est pas ajouter !");
            alert.show();
        }
    }

    public void loadPatients() {
        PatientImpl patientDao = new PatientImpl();
        List<Patient> patients = patientDao.findAll();
        col_patientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_mail_patient.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_adresse_patient.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        col_nom_patient.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom_patient.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_tele_patient.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        col_date_naissance_patient.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        // Configurer la colonne pour le numéro d'ordre
        col_nb_ordre.setCellValueFactory(cellData -> {
            // Retourne l'index de l'élément dans la liste + 1 (pour commencer à 1)
            return new ReadOnlyObjectWrapper<>(patient_tableView.getItems().indexOf(cellData.getValue()) + 1);
        });/*ReadOnlyObjectWrapper : Cette classe permet de créer une cellule en lecture seule dans la TableView, contenant le numéro d'ordre.

patient_tableView.getItems().indexOf(cellData.getValue()) + 1 :

indexOf(cellData.getValue()) retourne l'index de l'élément actuel dans la liste des éléments de la TableView.*/
        // Ajoute les analyses à la TableView
        ObservableList<Patient> patientObservableList = FXCollections.observableArrayList(patients);
        patient_tableView.setItems(patientObservableList);
    }

    @FXML
    void rechercherPatient(ActionEvent event) {
        // Récupérer la requête utilisateur depuis un champ de recherche
        String query = btn_rechercher2.getText().toLowerCase(); // Assurez-vous que searchField est défini correctement
        ObservableList<Patient> tableData = patient_tableView.getItems();

        if (query.isEmpty()) {
            patient_tableView.setItems(tableData);
            patient_tableView.refresh(); // Rafraîchir les données
            return;
        }

        // Utilisation de FilteredList pour filtrer les patients
        FilteredList<Patient> filteredData = new FilteredList<>(tableData, patient -> {
            if (patient == null) return false;
            // Vérifier si la recherche correspond à une des colonnes
            return (patient.getNom() != null && patient.getNom().toLowerCase().contains(query)) ||
                    (patient.getPrenom() != null && patient.getPrenom().toLowerCase().contains(query)) ||
                    (patient.getTelephone() != null && patient.getTelephone().toLowerCase().contains(query)) ||
                    (patient.getAdresse() != null && patient.getAdresse().toLowerCase().contains(query)) ||
                    (patient.getEmail() != null && patient.getEmail().toLowerCase().contains(query)) ||
                    (patient.getDateNaissance() != null && String.valueOf(patient.getDateNaissance()).contains(query));
        });

        // Associer les données filtrées à la TableView
        patient_tableView.setItems(filteredData);

        // Mettre à jour le numéro d'ordre
        col_nb_ordre.setCellValueFactory(cellData -> {
            return new ReadOnlyObjectWrapper<>(filteredData.indexOf(cellData.getValue()) + 1);
        });

        patient_tableView.refresh(); // Rafraîchir la vue
    }

    /*

    @FXML
    void rechercherPatient(ActionEvent event) {
        String query = btn_rechercher2.getText().toLowerCase();
        ObservableList<Patient> tableData = patient_tableView.getItems();
        // Réinitialiser si le champ de recherche est vide
        if (query.isEmpty()) {
            patient_tableView.setVisible(true);
            patient_tableView.refresh(); // Pour réafficher toutes les lignes
            return;
        }
        // Filtrer les données visibles dans le TableView
        FilteredList<Patient> filteredData = new FilteredList<>(tableData, patient -> {
            // Vérifier si la recherche correspond à l'une des colonnes
            return patient.getNom().toLowerCase().contains(query) ||
                    patient.getPrenom().toLowerCase().contains(query) || patient.getTelephone().toLowerCase().contains(query) ||
                    patient.getAdresse().toLowerCase().contains(query) || patient.getEmail().toLowerCase().contains(query) ||
                    String.valueOf(patient.getDateNaissance()).contains(query);
        });
        patient_tableView.setItems(filteredData);
        patient_tableView.refresh();
        patient_tableView.setVisible(true);
    }*/

    public void rechercherResultatAnalyse(ActionEvent event) {
        String query = btn_rechercher.getText().toLowerCase();
        ObservableList<ResultatAnalyse> tableData = resultatAnalyses_tableView.getItems();

        if (query.isEmpty()) {
            resultatAnalyses_tableView.setVisible(true);
            resultatAnalyses_tableView.refresh();
            return;
        }
        FilteredList<ResultatAnalyse> filteredData = new FilteredList<>(tableData, resultat -> {
            boolean matchDetails = resultat.getDetailsResultat().toLowerCase().contains(query);
            boolean matchDate = String.valueOf(resultat.getDateResultat()).contains(query);

            boolean matchValeurs = resultat.getValeurs().entrySet().stream().anyMatch(entry ->
                    entry.getKey().toLowerCase().contains(query) ||
                            String.valueOf(entry.getValue()).contains(query)
            );

            // Vérifier si le nom de l'analyse correspond
            boolean matchAnalyse = resultat.getAnalyse() != null &&
                    resultat.getAnalyse().getNom().toLowerCase().contains(query);

            // Retourner vrai si au moins un critère correspond
            return matchDetails || matchDate || matchValeurs || matchAnalyse;
        });
        resultatAnalyses_tableView.setItems(filteredData);
        resultatAnalyses_tableView.refresh();
    }

    public void loadResultatsAnalyses() {
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();
        List<ResultatAnalyse> resultatAnalyses = resultatAnalyseDao.findAll();

        col_ordreAnalyseId.setCellValueFactory(new PropertyValueFactory<>("id"));
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

    public void loadOrdreAnalyses() {
        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        List<OrdreAnalyse> ordreAnalyses = ordreAnalyseDao.findAll();
        col_ordreAnalyseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_date_ordre_analyse.setCellValueFactory(new PropertyValueFactory<>("dateOrdre"));
        col_patient.setCellValueFactory(data -> {
            OrdreAnalyse ordreAnalyse = data.getValue();
            if (ordreAnalyse != null && ordreAnalyse.getPatient() != null) {
                return new SimpleStringProperty(ordreAnalyse.getPatient().getNom());
            }
            return new SimpleStringProperty("N/A");
        });
        /*col_resultat.setCellValueFactory(data -> {
            OrdreAnalyse ordreAnalyse = data.getValue();
            if (ordreAnalyse != null && ordreAnalyse.getResultatAnalyses() != null) {
                return new SimpleStringProperty(ordreAnalyse.getResultatAnalyses().getFirst().getDetailsResultat());
            }
            return new SimpleStringProperty("N/A");
        });*/
        col_resultat.setCellValueFactory(data -> {
            OrdreAnalyse ordreAnalyse = data.getValue();
            if (ordreAnalyse != null && ordreAnalyse.getResultatAnalyses() != null && !ordreAnalyse.getResultatAnalyses().isEmpty()) {
                // Concaténer tous les résultats d'analyses
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
        displayUsername();
        loadPatients();
        loadResultatsAnalyses();
        loadOrdreAnalyses();
    }
}

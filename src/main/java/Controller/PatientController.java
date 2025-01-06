package Controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dao.FactureImpl;
import dao.OrdreAnalyseImpl;
import dao.ResultatAnalyseImpl;
import dao.UserImpl;
import entites.*;
import jakarta.persistence.Table;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
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
    private Button button_download_resultat;

    // Méthode utilitaire pour ajouter un en-tête de colonne
    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell(new Phrase(headerTitle));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(header);
    }
    public void telechargerResultatAnalyse() {
        // Récupérer les données à partir de votre DAO
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();
        List<ResultatAnalyse> resultatAnalyses = resultatAnalyseDao.findAll();

        // Utiliser un FileChooser pour permettre à l'utilisateur de choisir l'emplacement du fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            try {
                // Initialisation du PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Ajouter un titre
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
                Paragraph title = new Paragraph("Rapport des Résultats d'Analyse", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(new Paragraph("\n")); // Saut de ligne

                // Créer une table avec 6 colonnes
                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100); // Largeur de la table
                table.setSpacingBefore(10f); // Espacement avant la table
                table.setSpacingAfter(10f); // Espacement après la table

                // Définir les largeurs des colonnes
                table.setWidths(new float[]{1, 3, 2, 2, 3, 3});

                // Ajouter les en-têtes de colonnes
                addTableHeader(table, "ID");
                addTableHeader(table, "Analyse");
                addTableHeader(table, "Date Résultat");
                addTableHeader(table, "Clés");
                addTableHeader(table, "Valeurs");
                addTableHeader(table, "Détails");

                // Ajouter les données des résultats d'analyse
                for (ResultatAnalyse resultat : resultatAnalyses) {
                    String id = String.valueOf(resultat.getId());
                    String analyseNom = (resultat.getAnalyse() != null) ? resultat.getAnalyse().getNom() : "N/A";
                    String dateResultat = (resultat.getDateResultat() != null) ? resultat.getDateResultat().toString() : "N/A";
                    String keys = String.join(", ", resultat.getValeurs().keySet());
                    String values = String.join(", ", resultat.getValeurs().values().stream().map(String::valueOf).toList());
                    String details = (resultat.getDetailsResultat() != null) ? resultat.getDetailsResultat() : "N/A";

                    // Ajouter les cellules à la table
                    table.addCell(new PdfPCell(new Phrase(id)));
                    table.addCell(new PdfPCell(new Phrase(analyseNom)));
                    table.addCell(new PdfPCell(new Phrase(dateResultat)));
                    table.addCell(new PdfPCell(new Phrase(keys)));
                    table.addCell(new PdfPCell(new Phrase(values)));
                    table.addCell(new PdfPCell(new Phrase(details)));
                }

                // Ajouter la table au document
                document.add(table);

                // Fermer le document
                document.close();

                // Message de confirmation
                System.out.println("Le fichier PDF a été généré avec succès : " + file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la génération du fichier PDF : " + e.getMessage());
            }
        }
    }

    public void telechargerResultatAnalyseCSV() {
        // Obtenez la liste des résultats d'analyse
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();
        List<ResultatAnalyse> resultatAnalyses = resultatAnalyseDao.findAll();

        // Créer un FileChooser pour choisir le chemin et le nom du fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            // Si l'utilisateur a choisi un fichier, on continue
            try (FileWriter writer = new FileWriter(file)) {
                // Écrire l'en-tête du fichier CSV
                writer.append("ID,Analyse,Date Résultat,Clés,Valeurs,Détails\n");

                // Parcourir les résultats et les écrire dans le fichier CSV
                for (ResultatAnalyse resultat : resultatAnalyses) {
                    // Préparer les données à écrire : ID, Nom de l'analyse, Date, etc.
                    String id = String.valueOf(resultat.getId());
                    String analyseNom = (resultat.getAnalyse() != null) ? resultat.getAnalyse().getNom() : "N/A";
                    String dateResultat = (resultat.getDateResultat() != null) ? resultat.getDateResultat().toString() : "N/A";
                    String keys = String.join(", ", resultat.getValeurs().keySet());
                    String values = String.join(", ", resultat.getValeurs().values().stream().map(String::valueOf).toList());
                    String details = (resultat.getDetailsResultat() != null) ? resultat.getDetailsResultat() : "N/A";

                    // Écrire la ligne dans le fichier CSV
                    writer.append(String.join(",", id, analyseNom, dateResultat, keys, values, details));
                    writer.append("\n");
                }

                // Message de confirmation
                System.out.println("Les résultats ont été exportés avec succès dans : " + file.getAbsolutePath());
            } catch (IOException e) {
                // Si une erreur survient lors de l'écriture dans le fichier
                e.printStackTrace();
                System.err.println("Erreur lors de l'exportation des résultats : " + e.getMessage());
            }
        }}
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
        System.out.println("getData.username: " + getData.username);
        username.setText(getData.username);
    }

    public void loadResultatsAnalyses() {
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();
        List<ResultatAnalyse> resultatAnalyses = resultatAnalyseDao.findAll();

        col_resultatAnalyseId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_Analyse_resultatAnalyse.setCellValueFactory(data -> {
            ResultatAnalyse resultat = data.getValue();// Récupère un objet ResultatAnalyse pour la ligne actuelle
            if (resultat != null && resultat.getAnalyse() != null) {
                return new SimpleStringProperty(resultat.getAnalyse().getNom());
            }
            return new SimpleStringProperty("N/A");
        });
        col_date_resultatAnalyse.setCellValueFactory(new PropertyValueFactory<>("dateResultat"));
        col_valeurValue_resultatAnalyse.setCellValueFactory(data -> {
            // Extraire les valeurs de la carte
            String values = data.getValue().getValeurs().values().stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(values);
        });
        col_valeurKey_resultatAnalyse.setCellValueFactory(data -> {
            // Extraire les clés de la carte
            String keys = String.join(", ", data.getValue().getValeurs().keySet());
            return new SimpleStringProperty(keys);
        });
        col_detail_resultatAnalyse.setCellValueFactory(new PropertyValueFactory<>("detailsResultat"));

        ObservableList<ResultatAnalyse> resultatAnalyseObservableList = FXCollections.observableArrayList(resultatAnalyses);
        resultatAnalyses_tableView.setItems(resultatAnalyseObservableList);
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


    public void loadFacture() {
        FactureImpl factureDao = new FactureImpl();
        List<Facture> factures = factureDao.findAll();

        col_factureId.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_montantTotal.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
        col_dateFacture.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
        //col_etatPaiementF.setCellValueFactory(new PropertyValueFactory<>("etatPaiement"));

        col_etatPaiementF.setCellValueFactory(new PropertyValueFactory<>("etatPaiement"));

        col_etatPaiementF.setCellFactory(column -> new TableCell<Facture, Boolean>() {
            @Override
            protected void updateItem(Boolean etatPaiement, boolean empty) {
                super.updateItem(etatPaiement, empty);
                if (empty || etatPaiement == null) {
                    setText(null);
                } else {
                    setText(etatPaiement ? "Payé" : "Non payé");
                }
            }
        });
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

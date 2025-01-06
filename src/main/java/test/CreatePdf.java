package test;/*
package test;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CreatePdf {
    private static final String pdf_filePath = "C:/Users/hp/Desktop/";
    private static final String pdf_name = "test.pdf";

    public static void main(String[] args) {
        System.out.println("Creating PDF using iText...");
        Document document = new Document();

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File(pdf_filePath + pdf_name)));
            document.open();

            // Create a font
            Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 14, BaseColor.BLUE);

            // Add content
            Chunk chunk = new Chunk("Hello, World!", font);
            document.add(chunk);

            document.close();
            System.out.println("PDF created successfully at " + pdf_filePath + pdf_name);

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
*/
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import dao.ResultatAnalyseImpl;
import entites.ResultatAnalyse;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class CreatePdf {

    public void telechargerResultatAnalyse() {
        // Récupérer tous les résultats d'analyse de la base de données
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();
        List<ResultatAnalyse> resultatAnalyses = resultatAnalyseDao.findAll();

        // Créer un document PDF
        Document document = new Document();
        try {
            // Créer un PdfWriter qui écrit dans le fichier sur le bureau
            PdfWriter.getInstance(document, new FileOutputStream("C:/Users/hp/Desktop/rapport_resultats.pdf"));
            document.open();

            // Ajouter un titre au rapport
            document.add(new Paragraph("Rapport des Résultats d'Analyse", FontFactory.getFont(FontFactory.COURIER_BOLD, 18, BaseColor.BLACK)));

            // Ajouter un saut de ligne
            document.add(new Paragraph("\n"));

            // Ajouter les résultats dans le PDF
            for (ResultatAnalyse resultat : resultatAnalyses) {
                // Ajouter chaque détail du résultat d'analyse
                document.add(new Paragraph("ID : " + resultat.getId()));
                document.add(new Paragraph("Détails : " + resultat.getDetailsResultat()));
                document.add(new Paragraph("Date : " + resultat.getDateResultat()));
                if (resultat.getAnalyse() != null) {
                    document.add(new Paragraph("Analyse : " + resultat.getAnalyse().getNom()));
                }
                document.add(new Paragraph("Valeur : " + String.join(", ", resultat.getValeurs().values().toString())));
                document.add(new Paragraph("\n"));
            }

            // Fermer le document PDF
            document.close();

            // Afficher un message de confirmation
            System.out.println("Le rapport des résultats d'analyse a été généré avec succès.");

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la génération du rapport.");
        }
    }

    public static void main(String[] args) {
        CreatePdf createPdf = new CreatePdf();
        createPdf.telechargerResultatAnalyse();
    }
}

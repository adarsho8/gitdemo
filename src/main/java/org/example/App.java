package org.example;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class App {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Prompt the user for HTML input
            System.out.println("Enter the HTML content:");
            StringBuilder htmlContentBuilder = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).isEmpty()) {
                htmlContentBuilder.append(line).append("\n");
            }
            String htmlContent = htmlContentBuilder.toString();

            String outputPdfPath = "mm.pdf"; // Output file path
            convertHtmlToPdf(htmlContent, outputPdfPath);
            System.out.println("PDF conversion completed successfully. Saved as: " + outputPdfPath);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Converts an HTML string to a PDF file.
     *
     * @param htmlContent    The HTML content as a string.
     * @param outputPdfPath  The desired output path for the PDF file.
     * @throws Exception If an error occurs during PDF creation.
     */
    public static void convertHtmlToPdf(String htmlContent, String outputPdfPath) throws Exception {
        // Parse the HTML content
        Document doc = Jsoup.parse(htmlContent);
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        // Convert HTML to PDF
        try (OutputStream os = new FileOutputStream(outputPdfPath)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(doc.html());
            renderer.layout();
            renderer.createPDF(os);
        }
    }
}

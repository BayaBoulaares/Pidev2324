package edu.esprit.APIapploadfichier;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PDFViewer extends JFrame {
    public PDFViewer(String title, PDDocument document) throws IOException {
        super(title);
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage image = renderer.renderImageWithDPI(0, 300);
        JLabel label = new JLabel(new ImageIcon(image));
        JScrollPane scrollPane = new JScrollPane(label);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
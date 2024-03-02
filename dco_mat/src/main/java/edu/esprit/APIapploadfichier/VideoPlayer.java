package edu.esprit.APIapploadfichier;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class VideoPlayer extends JFrame {
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public VideoPlayer(String title, byte[] videoBytes, String extension) {
        super(title);
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        setContentPane(mediaPlayerComponent);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Créer des boutons pour contrôler la vidéo
        JButton playButton = new JButton("Play");
        playButton.addActionListener(e -> mediaPlayerComponent.mediaPlayer().controls().play());

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(e -> mediaPlayerComponent.mediaPlayer().controls().pause());

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> mediaPlayerComponent.mediaPlayer().controls().stop());

        JSlider volumeSlider = new JSlider(0, 100);
        volumeSlider.addChangeListener(e -> mediaPlayerComponent.mediaPlayer().audio().setVolume(volumeSlider.getValue()));

        // Ajouter les boutons à la fenêtre
        JPanel controlPanel = new JPanel();
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stopButton);
        controlPanel.add(volumeSlider);

        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);

        try {
            // Créer un fichier temporaire avec l'extension appropriée
            Path tempFile = Files.createTempFile("video", "." + extension);
            try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                fos.write(videoBytes);
            }

            // Lire le fichier vidéo
            mediaPlayerComponent.mediaPlayer().media().play(tempFile.toAbsolutePath().toString());

            // Supprimer le fichier temporaire à la sortie
            tempFile.toFile().deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

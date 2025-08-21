package ui;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {

    public static void copyToClipboard(String text) {
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        Clipboard.getSystemClipboard().setContent(content);
    }

    public static void saveToFile(String content) {
        try {
            Path path = Files.createTempFile("gemini_output_", ".txt");
            try (FileWriter writer = new FileWriter(path.toFile())) {
                writer.write(content);
            }
            System.out.println("Response saved to: " + path);
        } catch (IOException e) {
            System.out.println("Failed to save file: " + e.getMessage());
        }
    }
}

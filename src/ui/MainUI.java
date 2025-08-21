package ui;

import api.GeminiClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainUI {
    public Parent createContent(Stage stage) {
        // ===== Chat tab (your existing UI) =====
        Label promptLabel = new Label("Enter your prompt:");
        TextArea promptArea = new TextArea();
        promptArea.setWrapText(true);
        Label charCountLabel = new Label("Characters: 0");
        promptArea.textProperty().addListener((obs, o, n) -> charCountLabel.setText("Characters: " + n.length()));

        Label responseLabel = new Label("Gemini Response:");
        TextArea responseArea = new TextArea();
        responseArea.setWrapText(true);
        responseArea.setEditable(false);

        Button sendButton = new Button("Send to Gemini");
        Button copyButton = new Button("Copy");
        Button clearButton = new Button("Clear");
        Button saveButton = new Button("Save");

        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setVisible(false);
        spinner.setPrefSize(20, 20);

        sendButton.setOnAction(e -> {
            String prompt = promptArea.getText().trim();
            if (prompt.isEmpty()) {
                responseArea.setText("Please enter a prompt.");
                return;
            }
            spinner.setVisible(true);
            responseArea.setText("");
            new Thread(() -> {
                String resp = GeminiClient.getGeminiResponse(prompt);
                javafx.application.Platform.runLater(() -> {
                    responseArea.setText(resp);
                    spinner.setVisible(false);
                });
            }).start();
        });

        copyButton.setOnAction(e -> FileUtils.copyToClipboard(responseArea.getText()));
        clearButton.setOnAction(e -> {
            promptArea.clear();
            responseArea.clear();
            charCountLabel.setText("Characters: 0");
        });
        saveButton.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Save Response");
            fc.setInitialFileName("response.txt");
            File f = fc.showSaveDialog(stage);
            if (f != null) {
                try (FileWriter w = new FileWriter(f)) {
                    w.write(responseArea.getText());
                } catch (IOException ex) {
                    responseArea.setText("Error saving file: " + ex.getMessage());
                }
            }
        });

        HBox promptBox = new HBox(10, promptLabel, charCountLabel);
        promptBox.setAlignment(Pos.CENTER_LEFT);
        HBox buttonBox = new HBox(10, sendButton, copyButton, clearButton, saveButton, spinner);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(5, 0, 0, 0));

        VBox chatLayout = new VBox(10, promptBox, promptArea, buttonBox, responseLabel, responseArea);
        chatLayout.setPadding(new Insets(15));

        // ===== Resume Builder tab =====
        ResumeBuilderUI resumeUI = new ResumeBuilderUI();
        Parent resumeLayout = resumeUI.createContent(stage); // ✅ Fixed here

        // ===== Tabs =====
        TabPane tabs = new TabPane();
        Tab resumeTab = new Tab("Resume Builder", resumeLayout);
        resumeTab.setClosable(false);
        Tab chatTab = new Tab("Chat", chatLayout);
        chatTab.setClosable(false);

        tabs.getTabs().addAll(resumeTab, chatTab);
        return tabs;
    }
}

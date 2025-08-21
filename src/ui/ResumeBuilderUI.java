package ui;

import java.io.FileOutputStream;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResumeBuilderUI {

    public Parent createContent(Stage stage) {
        // Input fields
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();
        TextField linkedinField = new TextField();
        TextArea skillsField = new TextArea();
        TextArea educationField = new TextArea();
        TextArea projectsField = new TextArea();
        TextArea certsField = new TextArea();

        skillsField.setPrefRowCount(3);
        educationField.setPrefRowCount(3);
        projectsField.setPrefRowCount(3);
        certsField.setPrefRowCount(3);

        // Output area
        Label outputLabel = new Label("Generated Resume:");
        TextArea resumeOutput = new TextArea();
        resumeOutput.setEditable(false);
        resumeOutput.setWrapText(true);
        resumeOutput.setPrefRowCount(15);  // ✅ bigger box

        // Buttons
        Button generateButton = new Button("Generate Resume Text");
        Button copyButton = new Button("Copy");
        Button saveButton = new Button("Save");

        // Actions
        generateButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String linkedin = linkedinField.getText();
            String skills = skillsField.getText();
            String education = educationField.getText();
            String projects = projectsField.getText();
            String certs = certsField.getText();

            // ✅ Proper resume format
            String resumeText = String.format(
                    "%s\n%s | %s | %s\n\nSkills:\n%s\n\nEducation:\n%s\n\nProjects:\n%s\n\nCertifications:\n%s",
                    name, phone, email, linkedin, skills, education, projects, certs
            );

            resumeOutput.setText(resumeText);
        });

        copyButton.setOnAction(e -> {
            resumeOutput.selectAll();
            resumeOutput.copy();
        });

        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Resume");
            fileChooser.setInitialFileName("resume.pdf");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    com.lowagie.text.Document document = new com.lowagie.text.Document();
                    com.lowagie.text.pdf.PdfWriter.getInstance(document, fos);

                    document.open();
                    document.add(new com.lowagie.text.Paragraph(resumeOutput.getText()));
                    document.close();

                    resumeOutput.setText("✅ Resume saved as PDF: " + file.getAbsolutePath());
                } catch (Exception ex) {
                    resumeOutput.setText("❌ Error saving PDF: " + ex.getMessage());
                }
            }
        });


        // Layout grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Name:"), 0, 0);     grid.add(nameField, 1, 0);
        grid.add(new Label("Email:"), 0, 1);    grid.add(emailField, 1, 1);
        grid.add(new Label("Phone:"), 0, 2);    grid.add(phoneField, 1, 2);
        grid.add(new Label("LinkedIn:"), 0, 3); grid.add(linkedinField, 1, 3);
        grid.add(new Label("Skills:"), 0, 4);   grid.add(skillsField, 1, 4);
        grid.add(new Label("Education:"), 0, 5); grid.add(educationField, 1, 5);
        grid.add(new Label("Projects:"), 0, 6); grid.add(projectsField, 1, 6);
        grid.add(new Label("Certifications:"), 0, 7); grid.add(certsField, 1, 7);

        HBox buttonBox = new HBox(10, generateButton, copyButton, saveButton);

        VBox layout = new VBox(10,
                grid,
                buttonBox,
                outputLabel,
                resumeOutput
        );
        layout.setPadding(new Insets(10));

        return layout;
    }
}

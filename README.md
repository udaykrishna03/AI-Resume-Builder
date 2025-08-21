# AI Resume Builder

A JavaFX application that helps you create professional resumes. The app uses the Gemini API to generate AI-powered content and allows you to save resumes as PDF files.

---

## Features

- Enter personal details, skills, education, projects, and certifications.
- Generate a well-formatted resume text.
- Copy the resume text to clipboard.
- Save the resume as a PDF.
- AI-powered content suggestions via Gemini API.

---

## Setup Instructions

1. Clone the repository:

```bash
git clone https://github.com/udaykrishna03/AI-Resume-Builder.git
cd AI-Resume-Builder
```

2. Add your API key:
- Copy the example config file:
```bash
cp config.example.txt config.txt
```
Open config.txt and replace YOUR_API_KEY_HERE with your own Gemini API key.
Do not commit config.txt — it must stay private.

3. Open the project in IntelliJ IDEA:
Make sure JavaFX is configured correctly.
Build and run the project.

## Usage

1. Launch the application.
2. Fill in your details in the input fields.
3. Click **Generate Resume Text** to create the resume.
4. Click **Copy** to copy it, or **Save** to export as a PDF.

---

## Important Notes

- Keep your `config.txt` file private.
- Share only `config.example.txt` if sharing the project.

---

## Project Structure

AIJavaFXAssistant/
├─ src/ ← Java source files
├─ config.txt ← Your API key (ignored by Git)
├─ config.example.txt ← API key placeholder
├─ .gitignore ← ignores config.txt and IDE files


---

## License

This project is open source under the MIT License.

---

## GitHub Repository

[https://github.com/udaykrishna03/AI-Resume-Builder](https://github.com/udaykrishna03/AI-Resume-Builder)


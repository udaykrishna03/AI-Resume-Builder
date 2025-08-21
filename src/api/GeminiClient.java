package api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.Scanner;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiClient {

    // Read API key from config.txt
    private static String readApiKey() {
        try {
            File file = new File("config.txt"); // Must be in project root
            Scanner sc = new Scanner(file);
            String key = sc.nextLine().trim();
            sc.close();
            return key;
        } catch (Exception e) {
            System.out.println("Error reading API key from config.txt: " + e.getMessage());
            return "";
        }
    }

    private static final String API_KEY = readApiKey();
    private static final String GEMINI_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + API_KEY;

    public static String getGeminiResponse(String prompt) {
        try {
            String safePrompt = prompt.replace("\"", "\\\"");
            String requestBody = """
                    {
                      "contents": [
                        {
                          "parts": [
                            {
                              "text": "%s"
                            }
                          ]
                        }
                      ]
                    }
                    """.formatted(safePrompt);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GEMINI_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                JSONObject json = new JSONObject(responseBody);
                JSONArray candidates = json.optJSONArray("candidates");

                if (candidates != null && candidates.length() > 0) {
                    JSONObject content = candidates.getJSONObject(0).optJSONObject("content");
                    if (content != null) {
                        JSONArray parts = content.optJSONArray("parts");
                        if (parts != null && parts.length() > 0) {
                            String text = parts.getJSONObject(0).optString("text", "");
                            return cleanGeminiOutput(text);
                        }
                    }
                }
                return "No content found in Gemini response.";
            } else {
                return "Error: Gemini API returned status " + response.statusCode() + " - " + response.body();
            }
        } catch (Exception e) {
            return "Exception while contacting Gemini API: " + e.getMessage();
        }
    }

    private static String cleanGeminiOutput(String text) {
        return text
                .replaceAll("(?m)^\\s*//.*$", "")
                .replaceAll("(?m)^\\*+\\s*", "")
                .replaceAll("[`*]", "")
                .replace("\\u003e", ">")
                .replace("\\n", "\n")
                .replace("\\", "")
                .trim();
    }
}

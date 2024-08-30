package com.deep.stockclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.datatype.jsr310.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deep.stockclient.models.Users;
import com.deep.stockclient.models.Project;
@Log4j2
public class LoginController
{

    @FXML
    private TextField emailField;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private Label InvalidCredentialsErrorLabel;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String LOGIN_API_URL = "http://192.168.1.7:90/api/login";

    @FXML
    private void handleLoginButtonAction()
    {

        String email = emailField.getText();
        String password = passwordField.getText();

        // Clear previous error messages
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");
        InvalidCredentialsErrorLabel.setText("");

        try {
            URL url = new URL(LOGIN_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            String jsonInputString = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

            // Writing the request body
            try (OutputStream os = conn.getOutputStream())
            {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Reading the response
            int responseCode = conn.getResponseCode();
            InputStream responseStream = responseCode == 200 ? conn.getInputStream() : conn.getErrorStream();
            String responseMessage;

            try (Scanner scanner = new Scanner(responseStream)) {
                responseMessage = scanner.useDelimiter("\\A").next();
            }
            logger.info("FULL JSON RESPONSE: {}", responseMessage);

            if (responseCode == 200)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());

                JsonNode rootNode = objectMapper.readTree(responseMessage);

                // Parse the response JSON
                String status = rootNode.path("status").asText();
                logger.info("Status: {}", status);
                String message = rootNode.path("message").asText();
                logger.info("Message: {}", message);
                String token = rootNode.has("token") ? rootNode.path("token").asText() : "";
                logger.info("Token: {}", token);

                JsonNode userNode = rootNode.path("user");
                Users user = objectMapper.treeToValue(userNode, Users.class);
                logger.info("Current User: {}", user);

                JsonNode projectsNode = rootNode.path("projects");
                List<Project> projects = rootNode.has("projects") && !rootNode.path("projects").isNull() ?
                        objectMapper.readValue(projectsNode.toString(), new TypeReference<List<Project>>() {}) : new ArrayList<>();
                logger.info("All Projects: {}", projects);

                // Loading the new view instead of showing an alert
                Stage stage = (Stage) emailField.getScene().getWindow();
                //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/user_dashboard.fxml"));
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/trial.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.setTitle("User Dashboard");

                // Pass user data to the dashboard controller if needed
                UserController userController = fxmlLoader.getController();
                userController.setUserData(user, projects);





            }
            else {
                // Handle error response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseMessage);

                // Check for general error messages
                String generalMessage = rootNode.path("message").asText();
                if (!generalMessage.isEmpty()) {
                    InvalidCredentialsErrorLabel.setText(generalMessage);
                }

                // Check for specific field errors
                JsonNode errorNode = rootNode.path("error");

                if (errorNode.has("email")) {
                    emailErrorLabel.setText(String.join(", ", objectMapper.convertValue(errorNode.path("email"), String[].class)));
                }

                if (errorNode.has("password")) {
                    passwordErrorLabel.setText(String.join(", ", objectMapper.convertValue(errorNode.path("password"), String[].class)));
                }
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            showAlert("Error", "An error occurred: " + e.getMessage());
        }


        finally
        {
            // Clear the password field after an attempt
            passwordField.clear();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }
}

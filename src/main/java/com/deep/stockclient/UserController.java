package com.deep.stockclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Log4j2
public class UserController {

    @FXML
    private ListView<Users> userListView; // Ensure Users is the correct type

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label colourPaletteLabel;
    @FXML
    private Label createdAtLabel;
    @FXML
    private Label updatedAtLabel;

    private WebClientStockClient webClientStockClient;

    public UserController() {
        WebClient webClient = WebClient.builder().baseUrl("http://192.168.1.7:90/api/users").build(); // Set base URL
        this.webClientStockClient = new WebClientStockClient(webClient);
    }

    @FXML
    public void initialize() {
        // Fetch users and set up ListView
        Flux<Users> userFlux = webClientStockClient.getAllUsers();
        log.info(userFlux);
        ObservableList<Users> users = FXCollections.observableArrayList();
        log.info("In initialize Method");
        log.info(users);
        
        userFlux.subscribe(
                user -> {
                    users.add(user);
                    // Print each user to the console
                    log.info("Fetched user: " + user);
                },
                error -> {
                    log.error("Error fetching users: ", error);
                    // Handle error, e.g., show an error message to the user
                },
                () -> {
                    userListView.setItems(users); // Set items to the ListView once completed
                    log.info("Users loaded successfully.");
                }
        );

        // Set up selection listener for the ListView
        userListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue)
        );
    }

    private void showUserDetails(Users user) {
        if (user != null) {
            idLabel.setText("ID: " + user.getId());
            nameLabel.setText("Name: " + user.getName());
            emailLabel.setText("Email: " + user.getEmail());
            phoneLabel.setText("Phone: " + user.getPhone());
            roleLabel.setText("Role: " + user.getRole());
            colourPaletteLabel.setText("Colour Palette: " + user.getColourPalette());
            createdAtLabel.setText("Created At: " + user.getCreatedAt());
            updatedAtLabel.setText("Updated At: " + user.getUpdatedAt());
        }
    }
}

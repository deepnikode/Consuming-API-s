package com.deep.stockclient.controllers;

import com.deep.stockclient.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;


@Log4j2
public class UserController implements Initializable {



    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label employeeCodeLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label personalEmailLabel;
    @FXML
    private Label panNumberLabel;
    @FXML
    private Label adharNumberLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label alternatePhoneLabel;
    @FXML
    private Label currentAddressLabel;
    @FXML
    private Label permanentAddressLabel;
    @FXML
    private Label designationLabel;
    @FXML
    private Label defaultSalaryLabel;
    @FXML
    private Label joininDateLabel;
    @FXML
    private Label dateOfBirthLabel;
    @FXML
    private Label emailVerifiedAtLabel;
    @FXML
    private Label isActiveLabel;
    @FXML
    private Label colourPaletteLabel;
    @FXML
    private Label imageLabel;
    @FXML
    private Label panDocumentLabel;
    @FXML
    private Label adharDocumentLabel;
    @FXML
    private Label apiTokenLabel;
    @FXML
    private Label createdAtLabel;
    @FXML
    private Label updatedAtLabel;

    @FXML
    private Label taskHeadingLabel;
    @FXML
    private Label taskNameLabel;
    @FXML
    private Label DescriptionLabel;
    @FXML
    private HBox taskContainer;



    @FXML
    private TableView<Project> projectsTableView;
    @FXML
    private TableColumn<Project, Integer> idColumn;
    @FXML
    private TableColumn<Project, String> nameColumn;
    @FXML
    private TableColumn<Project, String> startDateColumn;
    @FXML
    private TableColumn<Project, Boolean> activeColumn;


    @FXML
    private TableColumn<Project, Void> viewColumn; // Added viewColumn

    @FXML
    private ListView<Project> projectsListView;


    // BUTTONS
    @FXML
    private Button btnHome;
    @FXML
    private Button btnProfile;

    @FXML
    private Button btnProjects;
    @FXML
    private Button btnSignout;

    // PNL
    @FXML
    private Pane pnlHome;
    @FXML
    private Pane pnlProfile;

    @FXML
    private Pane pnlProjects;
    @FXML
    private Pane pnlSignout;


//    @FXML
//    private ScrollPane pnlAssignmentCards;

    @FXML
    private Pane pnlAssignmentCards;


    private Users user;
    private List<Project> projects;


    // INITIALIZE_METHOD
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        pnlHome.setVisible(true);
        pnlProfile.setVisible(false);
        pnlProjects.setVisible(false);
        pnlSignout.setVisible(false);
        pnlAssignmentCards.setVisible(false);

//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
//        activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));


        viewColumn.setCellFactory(tc -> {
            return new TableCell<>() {
                public final Button viewButton = new Button("View");

                {
                    viewButton.setOnAction(event ->
                    {
                        Project project = getTableView().getItems().get(getIndex());
                        ObjectMapper objectMapper = new ObjectMapper();

                        System.out.println("Viewing project: " + project.getName());
                        String LIST_API_URL = "http://192.168.1.7:90/api/" + project.getId() + "/lists";
                        //String LIST_API_URL = "http://192.168.1.7:90/api/36/lists";
                        System.out.println("Viewing API: url " + LIST_API_URL);
                        String token=user.getApiToken();

                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(LIST_API_URL))
                                .header("Authorization", "Bearer " + token)
                                .GET()
                                .build();

                        HttpClient.newHttpClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenApply(HttpResponse::body)
                                .thenAccept(response -> {
                                    try {
                                            ListApiResponse listApiResponse = objectMapper.readValue(response, ListApiResponse.class);
                                            System.out.println("DIRECT RAW DATA" + listApiResponse);

                                            System.out.println("INDIRECT LIST ResponseeeeEEEEEEEE " + listApiResponse.getData());

                                            // CALLING METHOD TO HANDEL PROJECT RELATED LIST DATA
                                            handleListApiResponse(listApiResponse);

                                            pnlProjects.setVisible(false);
                                            pnlAssignmentCards.setVisible(true);


                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                        showErrorDialog("Failed to fetch via API's");
                                    }

                                })
                                .join();


                    });
                }


                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                    }
                }


            };
        });

        displayProjects();
    }


    // HANDEL_LIST-API_RESPONSE
    private void handleListApiResponse(ListApiResponse listApiResponse) {
        Platform.runLater(() -> {

            taskContainer.getChildren().clear(); // Clear previous content

            listApiResponse.getData().forEach((key, value) -> {

                // Create and style a task heading container (VBox)
                VBox taskHeadingContainer = new VBox();
                taskHeadingContainer.setAlignment(Pos.TOP_CENTER);
                taskHeadingContainer.setStyle("-fx-padding: 15; -fx-border-color: #ccc; " +
                        "-fx-border-width: 1px; -fx-border-radius: 7; -fx-background-radius: 5; " +
                        "-fx-background-color: #e6f7ff;");
                taskHeadingContainer.setSpacing(10);


                // TASK TITLE HEADING
                Text taskHeadingText = new Text(key);
                taskHeadingText.setStyle("-fx-font-size: 18px; -fx-padding: 10; -fx-font-weight: bold;");
                TextFlow taskHeadingTextFlow = new TextFlow(taskHeadingText);
                taskHeadingTextFlow.setTextAlignment(TextAlignment.CENTER); // Center the text within the TextFlow
                taskHeadingContainer.getChildren().add(taskHeadingTextFlow);


                // Add each task as a card
                value.forEach(task -> {
                    // Creating VBox for each task card
                    VBox taskCard = new VBox();
                    taskCard.setStyle("-fx-background-color: #ffffff; -fx-padding: 10;" +
                            " -fx-border-color: #dcdcdc; -fx-border-width: 1px; " +
                            "-fx-border-radius: 5; -fx-background-radius: 5;");
                    taskCard.setAlignment(Pos.TOP_CENTER);

                    taskCard.setSpacing(10);
                    taskCard.setPrefWidth(200); // Fixed width for the task card


                    // Task Name (TextFlow)
                    Text taskNameText = new Text(task.getName());
                    taskNameText.setStyle("-fx-font-weight: bold; -fx-font-size: 15px;");
                    TextFlow taskNameTextFlow = new TextFlow(taskNameText);
                    taskNameTextFlow.setTextAlignment(TextAlignment.CENTER); // Center the text within the TextFlow
                    taskCard.getChildren().add(taskNameTextFlow);


                    // Task Description (TextFlow)
                    TextFlow descriptionTextFlow = new TextFlow();
                    descriptionTextFlow.setMaxWidth(Region.USE_COMPUTED_SIZE);
                    descriptionTextFlow.setStyle("-fx-font-size: 14px;-fx-font-size: 12px");
                    Text descriptionText = new Text(task.getDescription());
                    descriptionTextFlow.getChildren().add(descriptionText);
                    //descriptionTextFlow.setTextAlignment(TextAlignment.CENTER); // Center the text within the TextFlow
                    taskCard.getChildren().add(descriptionTextFlow);

                    taskHeadingContainer.getChildren().add(taskCard);
                });

                taskContainer.getChildren().add(taskHeadingContainer);
            });
        });
    }





    // RESET_PANELS
    private void resetPanels()
    {
        pnlHome.setVisible(false);
        pnlProfile.setVisible(false);
        pnlProjects.setVisible(false);
        pnlSignout.setVisible(false);
        pnlAssignmentCards.setVisible(false);
    }

    // HANDEL_CLICKS
    public void handleClicks(ActionEvent actionEvent)
    {
        resetPanels();

        if (actionEvent.getSource() == btnHome) {
            //pnlHome.setStyle("-fx-background-color: #de3131;");

            pnlHome.setVisible(true);
        } else if (actionEvent.getSource() == btnProfile) {
            //pnlProfile.setStyle("-fx-background-color: #cae4db;");
            pnlProfile.setVisible(true);
        }

        else if (actionEvent.getSource() == btnProjects) {
            //pnlProjects.setStyle("-fx-background-color: #e4cae4;");
            pnlProjects.setVisible(true);
//            pnlAssignmentCards.setVisible(false);
        } else if (actionEvent.getSource() == btnSignout) {
           loadLoginPage();
        }


    }

    // Load the login page
    private void loadLoginPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/login_page.fxml"));
            Stage stage = (Stage) btnSignout.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showErrorDialog("Failed to load the login page.");
        }
    }


    // SET_USERS_DATA
    public void setUserData(Users user, List<Project> projects)
    {
        this.user = user;
        this.projects = projects;
        displayUserData();
        displayProjects();
    }


    // DISPLAY_USERS
    private void displayUserData()
    {
        if (user != null) {
            idLabel.setText(String.valueOf(user.getId()));
            nameLabel.setText(user.getName());
            employeeCodeLabel.setText(user.getEmployee_code());
            roleLabel.setText(user.getRole());
            genderLabel.setText(user.getGender());
            emailLabel.setText(user.getEmail());
            personalEmailLabel.setText(user.getPersonalEmail());
            panNumberLabel.setText(user.getPanNumber());
            adharNumberLabel.setText(user.getAdharNumber());
            phoneLabel.setText(user.getPhone());
            alternatePhoneLabel.setText(user.getAlternatePhone());
            currentAddressLabel.setText(user.getCurrentAddress());
            permanentAddressLabel.setText(user.getPermanentAddress());
            designationLabel.setText(user.getDesignation());
            defaultSalaryLabel.setText(String.valueOf(user.getDefaultSalary()));
            joininDateLabel.setText(String.valueOf(user.getJoiningDate()));
            dateOfBirthLabel.setText(String.valueOf(user.getDateOfBirth()));
            emailVerifiedAtLabel.setText(String.valueOf(user.getEmailVerifiedAt()));
            isActiveLabel.setText(String.valueOf(user.isActive()));
            colourPaletteLabel.setText(user.getColourPalette());
            imageLabel.setText(user.getImage());
            panDocumentLabel.setText(user.getPanDocument());
            adharDocumentLabel.setText(user.getAdharDocument());
            apiTokenLabel.setText(user.getApiToken());
            createdAtLabel.setText(String.valueOf(user.getCreatedAt()));
            updatedAtLabel.setText(String.valueOf(user.getUpdatedAt()));
        }
    }

    // DISPLAY_PROJECTS
    private void displayProjects() {
        if (projects != null)
        {
            ObservableList<Project> projectList = FXCollections.observableArrayList(projects);
            projectsTableView.setItems(projectList);
        }
    }
    private void showErrorDialog(String errorMessage) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
        });
    }



}

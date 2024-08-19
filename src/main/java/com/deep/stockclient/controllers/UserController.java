package com.deep.stockclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.deep.stockclient.models.Users;
import com.deep.stockclient.models.Project;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class UserController {

    @FXML
    private ListView<Users> userListView;

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

    @FXML
    private ListView<Project> projectsListView;

    private Users user;
    private List<Project> projects;

    public void setUserData(Users user, List<Project> projects) {
        this.user = user;
        this.projects = projects;
        displayUserData();
        displayProjects();
    }

    private void displayUserData() {
        if (user != null) {
            idLabel.setText(String.valueOf(user.getId()));
            nameLabel.setText( user.getName());
            emailLabel.setText( user.getEmail());
            phoneLabel.setText( user.getPhone());
            roleLabel.setText( user.getRole());
            colourPaletteLabel.setText( user.getColourPalette());
            createdAtLabel.setText(String.valueOf(user.getCreatedAt()));
            updatedAtLabel.setText(String.valueOf(user.getUpdatedAt()));
        }
    }

    private void displayProjects() {
        if (projects != null) {
            ObservableList<Project> projectList = FXCollections.observableArrayList(projects);
            projectsListView.setItems(projectList);
        }
    }
}

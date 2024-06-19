package com.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController
{
    @FXML
    private AnchorPane LogPane;
    @FXML
    private Rectangle logRect;
    @FXML
    private Text travellerText;
    @FXML
    private Text managerText;
    @FXML
    private Button travellerLogin;
    @FXML
    private Button managerLogin;
    @FXML
    private Button back;
    @FXML
    private Stage stage;






    @FXML
    private void loginTraveller(ActionEvent ex) {
        try {
            // Load the payment FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Traveller.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from any control that is already loaded
            Stage stage = (Stage) managerLogin.getScene().getWindow(); // Replace btnYourButton with any actual control from your current scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or display an error message
        }
    }

    @FXML
    private void loginManager(ActionEvent ex){
        try {
            // Load the payment FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from any control that is already loaded
            Stage stage = (Stage) managerLogin.getScene().getWindow(); // Replace btnYourButton with any actual control from your current scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or display an error message
        }
    }

    @FXML
    private void backWelcome(ActionEvent ex){
        try {
            // Load the payment FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) from any control that is already loaded
            Stage stage = (Stage) back.getScene().getWindow(); // Replace btnYourButton with any actual control from your current scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or display an error message
        }
    }
}
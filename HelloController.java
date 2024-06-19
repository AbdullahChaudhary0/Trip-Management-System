package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class HelloController {
    @FXML
    private ImageView logoImage;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private Button welcomeButton;
    @FXML
    private Text craftingText;
    @FXML
    private Text unchartedText;
    @FXML
    private Rectangle rect;
    @FXML
    private Stage stage;


    @FXML
    public void moveLogin(ActionEvent ex) {
        try {
            // Load the payment FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) welcomeButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or display an error message
        }
    }
}
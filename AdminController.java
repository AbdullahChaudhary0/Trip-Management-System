package com.example.demo;
import com.example.demo.BLL.Query;
import com.example.demo.BLL.Refund;
import com.example.demo.BLL.Trip;
import com.example.demo.DLL.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

import java.io.IOException;

public class AdminController{

    @FXML
    private TableView<Trip> tripTable;

    @FXML
    private TableColumn<Query, String> queryCol;

    @FXML
    private TableColumn<Query, String> travCol3;

    @FXML
    private TableView<Refund> refundView;

    @FXML
    private TableColumn<Refund, String> travCol2;
    @FXML
    private TableColumn<Refund, Double> amountCol;



    @FXML
    private TableView<Query> queryView;

    @FXML
    private AnchorPane adminPanel;

    @FXML
    private Rectangle adminRect;

    @FXML
    private Text slotTe;

    @FXML
    private TextField addSlotField;

    @FXML
    private Text managerText;

    @FXML
    private Button back;

    @FXML
    private Button trip;

    @FXML
    private Button manageTraveller;

    @FXML
    private Button refund;

    @FXML
    private Text slotsText;

    @FXML
    private TextField slotsField;

    @FXML
    private Button query;

    @FXML
    private ImageView managerLogo;

    @FXML
    private AnchorPane mPanel;

    @FXML
    private AnchorPane tripPanel;

    @FXML
    private Text tripLabelText;

    @FXML
    private Text sourceText;

    @FXML
    private Text destinationText;

    @FXML
    private Text costText;

    @FXML
    private Text srcText;

    @FXML
    private Text endDateText;

    @FXML
    private Text startDateText;

    @FXML
    private TextField sourceTextField;

    @FXML
    private TextField destinationTextField;

    @FXML
    private TextField costTextField;

    @FXML
    private TextField startTextField;

    @FXML
    private TextField endTextField;

    @FXML
    private AnchorPane manageTravPanel;

    @FXML
    private Text manageText;

    @FXML
    private Text sourceText11;

    @FXML
    private Text destinationText1;

    @FXML
    private TextField sourceText1;

    @FXML
    private TextField destinationField1;

    @FXML
    private Button searchButton;

    @FXML
    private AnchorPane refundPanel;

    @FXML
    private Text refundLabel;

    @FXML
    private Text refundName;

    @FXML
    private TextField nameField;

    @FXML
    private Button refundBut;

    @FXML
    private AnchorPane queryPanel;

    @FXML
    private Text queryLabel;

    @FXML
    private Text nameLabel;

    @FXML
    private TextField sourceField1;

    @FXML
    private TextField destinationField;

    @FXML
    private TextField travellerNameField;

    @FXML
    private Text responseLabel;

    @FXML
    private TextArea responseArea;

    @FXML
    private Button submitButton;

    @FXML
    private TableView<Trip> travellerView;

    @FXML
    private TableColumn<Trip, String> tripCol; // To show source
    @FXML
    private TableColumn<Trip, String> travellerCol; // To show destination
    @FXML
    private TableColumn<Trip, Integer> slotsCol; // To show slots

    @FXML
    private void submitOnAction(ActionEvent ex){

    }

    @FXML
    private void refundButOnAction(ActionEvent ex) {
        String refundName = nameField.getText().trim();  // Get the name from the TextField and trim any leading or trailing whitespace

        if (refundName.isEmpty()) {
            showAlert("Input Error", "Please enter a name to process the refund.");
            return;
        }

        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            showAlert("Database Error", "Failed to connect to the database.");
            return;
        }

        // First, check if the refund name exists in the table
        String checkSql = "SELECT COUNT(*) FROM refundtable WHERE refundname = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, refundName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // If the name exists, proceed to delete
                String deleteSql = "DELETE FROM refundtable WHERE refundname = ?";
                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setString(1, refundName);
                    int rowsAffected = deleteStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        showAlert("Success", "Refund processed successfully for " + refundName + ".");
                    } else {
                        showAlert("Error", "Refund processing failed for " + refundName + ".");
                    }
                }
            } else {
                showAlert("Not Found", "No record found for " + refundName + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error executing database operation: " + e.getMessage());
        }
    }

    @FXML
    private void searchOnAction(ActionEvent ex) {
        String source = sourceField1.getText();
        String destination = destinationField.getText();
        String newSlotsStr = addSlotField.getText();

        // Validate inputs
        if (source.isEmpty() || destination.isEmpty() || newSlotsStr.isEmpty()) {
            showAlert("Input Error", "Please fill in all fields.");
            return;
        }

        int newSlots;
        try {
            newSlots = Integer.parseInt(newSlotsStr);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Slots must be a valid integer.");
            return;
        }

        // Database operations
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            showAlert("Database Error", "Failed to connect to the database.");
            return;
        }

        // Check if the trip with the given source and destination exists
        String checkSql = "SELECT count(*) FROM triptable WHERE source = ? AND destination = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, source);
            checkStmt.setString(2, destination);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                // Update slots for the trip
                String updateSql = "UPDATE triptable SET slots = slots + ? WHERE source = ? AND destination = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, newSlots);
                    updateStmt.setString(2, source);
                    updateStmt.setString(3, destination);

                    int rowsAffected = updateStmt.executeUpdate();
                    if (rowsAffected > 0) {
                        showAlert("Success", "Slots updated successfully.");
                        manageTravPanel.setVisible(false);
                        mPanel.setVisible(true);
                    } else {
                        showAlert("Update Error", "No rows were updated; check your data.");
                    }
                }
            } else {
                showAlert("Not Found", "No matching trip found with the given source and destination.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error executing database operation: " + e.getMessage());
        }
    }


    @FXML
    public void initialize() {
        tripCol.setCellValueFactory(new PropertyValueFactory<>("source"));
        travellerCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        slotsCol.setCellValueFactory(new PropertyValueFactory<>("slots"));

        travCol2.setCellValueFactory(new PropertyValueFactory<>("travellerName"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        travCol3.setCellValueFactory(new PropertyValueFactory<>("traveller"));
        queryCol.setCellValueFactory(new PropertyValueFactory<>("feedback"));
    }


    @FXML
    private void addOnAction(ActionEvent ex){
        // Hide other panels
        mPanel.setVisible(false);
        manageTravPanel.setVisible(true);
        refundPanel.setVisible(false);
        queryPanel.setVisible(false);
        tripPanel.setVisible(false);
        queryPanel.setVisible(false);

        // Retrieve input values
        String source = sourceTextField.getText();
        String destination = destinationTextField.getText();
        String startDate = startTextField.getText();
        String endDate = endTextField.getText();
        String cost = costTextField.getText();
        String slots = slotsField.getText();  // Assuming the fx:id is slotText

        // Input validation
        if (source.isEmpty() || destination.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || cost.isEmpty() || slots.isEmpty()) {
            showAlert("Validation Error", "All fields must be filled. Please check your inputs and try again.");
            return;
        }

        int c, sl;
        try {
            c = Integer.parseInt(cost);
            sl = Integer.parseInt(slots);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Cost and slots must be valid numbers.");
            return;
        }

        // Database insertion
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            showAlert("Database Error", "Failed to connect to database.");
            return;
        }

        String sql = "INSERT INTO triptable (source, destination, startDate, endDate, cost, slots) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, source);
            stmt.setString(2, destination);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);
            stmt.setInt(5, c);
            stmt.setInt(6, sl);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Success", "Trip has been added successfully.");
            } else {
                showAlert("Error", "No trip was added to the database.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Error executing database operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void queryOnAction(ActionEvent ex) {
        // Manage panel visibility
        mPanel.setVisible(false);
        manageTravPanel.setVisible(false);
        refundPanel.setVisible(false);
        queryPanel.setVisible(true);
        tripPanel.setVisible(false);

        // Load data from the database
        loadQueryData();
    }

    private void loadQueryData() {
        ObservableList<Query> queries = FXCollections.observableArrayList();
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            showAlert("Database Error", "Failed to connect to the database.");
            return;
        }

        String sql = "SELECT name, chat FROM feedbacktable";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String traveller = rs.getString("name");
                String feedback = rs.getString("chat");
                queries.add(new Query(traveller, feedback));  // Ensure this constructor matches your Query class
            }
            queryView.setItems(queries);
            queryView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error fetching query data: " + e.getMessage());
        }
    }


    @FXML
    private void refundOnAction(ActionEvent ex) {
        // Manage panel visibility
        mPanel.setVisible(false);
        manageTravPanel.setVisible(false);
        refundPanel.setVisible(true);
        queryPanel.setVisible(false);
        tripPanel.setVisible(false);

        // Load data from the database
        loadDataIntoRefundView();
    }

    private void loadDataIntoRefundView() {
        ObservableList<Refund> refunds = FXCollections.observableArrayList();
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            showAlert("Database Error", "Failed to connect to the database.");
            return;
        }

        String sql = "SELECT refundname, refundamount FROM refundtable";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String refundname = rs.getString("refundname");
                Double refundamount = rs.getDouble("refundamount");
                refunds.add(new Refund(refundname, refundamount));
            }
            refundView.setItems(refunds);
            refundView.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error fetching refund data: " + e.getMessage());
        }
    }

    @FXML
    private void manageTravellerOnAction(ActionEvent ex) {
        mPanel.setVisible(false);
        manageTravPanel.setVisible(true);
        refundPanel.setVisible(false);
        queryPanel.setVisible(false);
        tripPanel.setVisible(false);

        // Load data from database
        loadTripData();
    }
    @FXML
    private void loadTripData() {
        ObservableList<Trip> trips = FXCollections.observableArrayList();
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT source, destination, slots FROM triptable";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                int slots = rs.getInt("slots");
                trips.add(new Trip(source, destination, null, null, 0, slots));
            }
            travellerView.setItems(trips);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to load trip data: " + e.getMessage());
        }
    }

    @FXML
    private void tripOnAction(ActionEvent ex){
        // Hide mPanel
        mPanel.setVisible(false);
        manageTravPanel.setVisible(false);
        refundPanel.setVisible(false);
        queryPanel.setVisible(false);
        tripPanel.setVisible(false);

        // Show tripPanel
        tripPanel.setVisible(true);

            // Assume more fields are gathered similarly...

            // Logic to handle the addition of the trip, perhaps inserting into a database or a list





    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void backOnAction(ActionEvent ex){
        try {
            // Load the payment FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
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
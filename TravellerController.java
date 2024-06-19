package com.example.demo;
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

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TravellerController {

    @FXML
    private TableView<Trip> tripView;
    @FXML
    private TableColumn<Trip, String> srcCol;
    @FXML
    private TableColumn<Trip, String> destCol;
    @FXML
    private TableColumn<Trip, String> startDateCol;
    @FXML
    private TableColumn<Trip, String> endDateCol;
    @FXML
    private TableColumn<Trip, Double> costCol;
    @FXML
    private TableColumn<Trip, Integer> slotsCol;

    @FXML
    private AnchorPane travellerPanel;

    @FXML
    private Rectangle rect;

    @FXML
    private Text travellerLabel;

    @FXML
    private Button back;

    @FXML
    private Button browseTripButton;

    @FXML
    private Button joinTrip;

    @FXML
    private Button paymentButton;

    @FXML
    private Button chatButton;

    @FXML
    private AnchorPane tPanel;

    @FXML
    private ImageView logoImage;

    @FXML
    private AnchorPane browseTripPanel;

    @FXML
    private Text tripLabel;

    @FXML
    private AnchorPane joinTripPanel;

    @FXML
    private Text joinTripLabel;

    @FXML
    private Text srcLabel;

    @FXML
    private Text destLabel;

    @FXML
    private Text slotsLabel;

    @FXML
    private Button book;

    @FXML
    private TextField srcField;

    @FXML
    private TextField destField;

    @FXML
    private TextField slotsField;

    @FXML
    private AnchorPane bookingPanel;

    @FXML
    private Text paymentLabel;

    @FXML
    private RadioButton jazzCash;

    @FXML
    private RadioButton easyPaisa;

    @FXML
    private RadioButton bank;

    @FXML
    private Text accLabel;

    @FXML
    private Text amountLabel;

    @FXML
    private TextField amountField;

    @FXML
    private TextField accountField;

    @FXML
    private TextField getCancelSrc;


    @FXML
    private TextField getCancelDest;


    @FXML
    private TextField cancelName;


    @FXML
    private Button finalTripButton;

    @FXML
    private TextField nameField;

    @FXML
    private Text nameLabel;

    @FXML
    private Text src1;

    @FXML
    private Text dest1;

    @FXML
    private Text name;

    @FXML
    private Text cancelLabel;

    @FXML
    private TextField cancelSrc;

    @FXML
    private TextField cancelDest;


    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane cancelPanel;

    @FXML
    private AnchorPane chatPanel;

    @FXML
    private Text chatLabel;

    @FXML
    private Text nameLabel1;

    @FXML
    private TextField chatName;

    @FXML
    private Text chatLab;

    @FXML
    private TextArea chatArea;

    @FXML
    private Button queryButton;


    @FXML
    private void finalTripOnAction(ActionEvent ex) {
        RadioButton selectedMethod = (RadioButton) paymentGroup.getSelectedToggle();
        if (selectedMethod == null) {
            showAlert("Payment Error", "Please select a payment method.");
            return;
        }

        String paymentMethod = selectedMethod.getText();
        String accountNumber = accountField.getText().trim();
        String amountString = amountField.getText().trim();

        if (accountNumber.isEmpty() || amountString.isEmpty()) {
            showAlert("Input Error", "Please fill in all required fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Invalid amount format.");
            return;
        }

        // Process payment here
        processPayment(paymentMethod, accountNumber, amount);
    }




    private void processPayment(String method, String accountNumber, double amount) {
        // Log or process payment
        System.out.println("Processing " + method + " payment to account " + accountNumber + " for $" + amount);

        // Assuming the connection handling and potential errors are logged/managed outside this method
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            showAlert("Database Error", "Failed to connect to the database.");
            return;
        }

        String sql = "INSERT INTO paymenttable (method, accountNum, amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, method);
            pstmt.setString(2, accountNumber);
            pstmt.setDouble(3, amount);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("Payment Successful", "Payment of $" + amount + " via " + method + " was successful and recorded.");
            } else {
                showAlert("Payment Error", "Payment was processed but not recorded. Please check the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while recording the payment: " + e.getMessage());
        }
    }


    @FXML
    private void queryOnAction(ActionEvent ex) {
        // Retrieve values from text fields
        String name = chatName.getText();
        String query = chatArea.getText();

        // Validate input (optional)
        if (name.isEmpty() || query.isEmpty()) {
            // Handle invalid input
            showAlert("Error", "Name or query is empty.");
            return;
        }

        // Get database connection
        Connection connection = DatabaseConnection.getInstance().getConnection();

        try {
            // Insert into feedbacktable
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO feedbacktable (name, chat) VALUES (?, ?)");
            insertStatement.setString(1, name);
            insertStatement.setString(2, query);
            insertStatement.executeUpdate();

            // Show success message
            showAlert("Success", "Query submitted successfully.");

            // Clear text fields (optional)
            chatName.clear();
            chatArea.clear();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
            showAlert("Error", "Failed to submit query.");
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent ex) {
        // Retrieve values from text fields
        String cancelSrc1 = cancelSrc.getText();
        String cancelDest1 = cancelDest.getText();
        String cancelName1 = cancelName.getText();
        Connection connection = DatabaseConnection.getInstance().getConnection();

        try {
            // Use values to query bookingtable for matching row
            PreparedStatement bookingStatement = connection.prepareStatement("SELECT * FROM bookingtable WHERE source = ? AND destination = ? AND bookingname = ?");
            bookingStatement.setString(1, cancelSrc1);
            bookingStatement.setString(2, cancelDest1);
            bookingStatement.setString(3, cancelName1);
            ResultSet bookingResult = bookingStatement.executeQuery();

            if (bookingResult.next()) {
                // Extract booking name and slots
                String bookingName = bookingResult.getString("bookingname");
                int slots = bookingResult.getInt("slots");

                // Query triptable to get cost associated with source and destination
                PreparedStatement tripStatement = connection.prepareStatement("SELECT cost FROM triptable WHERE source = ? AND destination = ?");
                tripStatement.setString(1, cancelSrc1);
                tripStatement.setString(2, cancelDest1);
                ResultSet tripResult = tripStatement.executeQuery();

                if (tripResult.next()) {
                    int cost = tripResult.getInt("cost");

                    // Calculate refund amount
                    int refundAmount = cost * slots;

                    // Delete row from bookingtable
                    PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM bookingtable WHERE source = ? AND destination = ? AND bookingname = ?");
                    deleteStatement.setString(1, cancelSrc1);
                    deleteStatement.setString(2, cancelDest1);
                    deleteStatement.setString(3, cancelName1);
                    deleteStatement.executeUpdate();

                    // Insert new row into refundtable
                    PreparedStatement refundStatement = connection.prepareStatement("INSERT INTO refundtable (refundname, refundamount) VALUES (?, ?)");
                    refundStatement.setString(1, bookingName);
                    refundStatement.setInt(2, refundAmount);
                    refundStatement.executeUpdate();
                    showAlert("Success", "Done Successfully");
                } else {
                    showAlert("Error", "Cost not found in triptable for the specified source and destination.");
                }
            } else {
                showAlert("Error", "Booking not present.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void bookOnAction(ActionEvent ex) {
        // Hide all other panels
        tPanel.setVisible(false);
        browseTripPanel.setVisible(false);
        joinTripPanel.setVisible(false);
        bookingPanel.setVisible(false);
        cancelPanel.setVisible(false);
        chatPanel.setVisible(false);

        // Retrieve data from TextField
        String source = srcField.getText();
        String destination = destField.getText();
        String slots = slotsField.getText();
        String name = nameField.getText();

        // Check if any field is empty
        if (source.isEmpty() || destination.isEmpty() || slots.isEmpty() || name.isEmpty()) {
            showAlert("Error", "Enter complete information.");
            return; // Exit the method if any field is empty
        }

        int slotsInt;
        try {
            slotsInt = Integer.parseInt(slots);
        } catch (NumberFormatException e) {
            showAlert("Error", "Slots must be a valid number.");
            return;
        }

        // Get the connection from your singleton database connection class
        Connection conn = DatabaseConnection.getInstance().getConnection();

        if (conn == null) {
            showAlert("Error", "Database connection failed. Please check the connection.");
            return;
        }

        // Check if source and destination exist in the triptable and if slots are available
        String checkSql = "SELECT slots FROM triptable WHERE source = ? AND destination = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, source);
            checkStmt.setString(2, destination);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                int availableSlots = rs.getInt("slots");
                if (slotsInt > availableSlots) {
                    showAlert("Invalid Request", "Not enough slots available. Please enter a smaller number.");
                    return;
                }

                // Proceed with the booking
                String sql = "INSERT INTO bookingtable (source, destination, bookingname, slots) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, source);
                    stmt.setString(2, destination);
                    stmt.setString(3, name);
                    stmt.setInt(4, slotsInt);

                    // Execute the booking update
                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0) {
                        // Update slots in triptable
                        String updateSql = "UPDATE triptable SET slots = slots - ? WHERE source = ? AND destination = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setInt(1, slotsInt);
                            updateStmt.setString(2, source);
                            updateStmt.setString(3, destination);

                            updateStmt.executeUpdate();
                            showAlert("Booking Successful", "Your booking was successful and slots have been updated.");
                            bookingPanel.setVisible(true);
                        }
                    } else {
                        System.out.println("No booking was added.");
                        joinTripPanel.setVisible(true);
                    }
                }
            } else {
                showAlert("Invalid Trip", "No matching trip found. Please check your input.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while processing your booking.");
        }
    }

    @FXML
    private void chatOnAction(ActionEvent ex){
        tPanel.setVisible(false);
        browseTripPanel.setVisible(false);
        joinTripPanel.setVisible(false);
        bookingPanel.setVisible(false);
        cancelPanel.setVisible(false);
        chatPanel.setVisible(false);

        chatPanel.setVisible(true);
    }

    @FXML
    private void paymentOnAction(ActionEvent ex){
        tPanel.setVisible(false);
        browseTripPanel.setVisible(false);
        joinTripPanel.setVisible(false);
        bookingPanel.setVisible(false);
        cancelPanel.setVisible(false);
        chatPanel.setVisible(false);

        cancelPanel.setVisible(true);
    }

    @FXML
    private void joinTripOnAction(ActionEvent ex){
        tPanel.setVisible(false);
        browseTripPanel.setVisible(false);
        joinTripPanel.setVisible(false);
        bookingPanel.setVisible(false);
        cancelPanel.setVisible(false);
        chatPanel.setVisible(false);

        joinTripPanel.setVisible(true);
    }

    private ToggleGroup paymentGroup;

    @FXML
    private void initialize() {
        srcCol.setCellValueFactory(new PropertyValueFactory<>("source"));
        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        slotsCol.setCellValueFactory(new PropertyValueFactory<>("slots"));

        paymentGroup = new ToggleGroup();
        jazzCash.setToggleGroup(paymentGroup);
        easyPaisa.setToggleGroup(paymentGroup);
        bank.setToggleGroup(paymentGroup);
    }


    @FXML
    private void browseTripOnAction(ActionEvent ex) {
        // Hide all other panels
        tPanel.setVisible(false);
        browseTripPanel.setVisible(true);
        joinTripPanel.setVisible(false);
        bookingPanel.setVisible(false);
        cancelPanel.setVisible(false);
        chatPanel.setVisible(false);

        loadDataIntoTripView();
    }

    private void loadDataIntoTripView(){
        ObservableList<Trip> tripData = FXCollections.observableArrayList();
        Connection conn = DatabaseConnection.getInstance().getConnection();
        if (conn == null) {
            showAlert("Database Error", "Failed to connect to the database.");
            return;
        }

        String sql = "SELECT source, destination, startDate, endDate, cost, slots FROM triptable";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                String startDate = rs.getString("startDate"); // Assume these are already in the correct string format
                String endDate = rs.getString("endDate");
                double cost = rs.getDouble("cost");
                int slots = rs.getInt("slots");

                tripData.add(new Trip(source, destination, startDate, endDate, cost, slots));
            }
            tripView.setItems(tripData);
            tripView.refresh();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load trip data.");
        }
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

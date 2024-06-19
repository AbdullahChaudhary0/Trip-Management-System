package com.example.demo.BLL;
import javafx.scene.control.Alert;

public abstract class Payment {
    protected String accountNum;
    protected double amount;

    public Payment(String accountNum, double amount) {
        this.accountNum = accountNum;
        this.amount = amount;
    }

    public abstract void processPayment();

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

class JazzCash extends Payment {
    public JazzCash(String accountNum, double amount) {
        super(accountNum, amount);
    }

    @Override
    public void processPayment() {
        showAlert("Payment", "Jazz cash payment being processed now.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

class EasyPaisa extends Payment {
    public EasyPaisa(String accountNum, double amount) {
        super(accountNum, amount);
    }

    @Override
    public void processPayment() {
        showAlert("Payment", "Easy paisa payment being processed now.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

class Bank extends Payment {
    public Bank(String accountNum, double amount) {
        super(accountNum, amount);
    }

    @Override
    public void processPayment() {
        showAlert("Payment", "Bank account payment being processed now.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


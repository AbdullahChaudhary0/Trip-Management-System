package com.example.demo.BLL;

public class Refund {
    private String travellerName;
    private double amount;

    public Refund(String travellerName, double amount) {
        this.travellerName = travellerName;
        this.amount = amount;
    }

    public String getTravellerName() {
        return travellerName;
    }

    public void setTravellerName(String travellerName) {
        this.travellerName = travellerName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

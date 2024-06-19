package com.example.demo.BLL;

public class Trip {
    private String source;
    private String destination;
    private String startDate;
    private String endDate;
    private double cost;
    private int slots;

    public Trip(String source, String destination, String startDate, String endDate, double cost, int slots) {
        this.source = source;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cost = cost;
        this.slots = slots;
    }

    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public double getCost() { return cost; }
    public int getSlots() { return slots; }
}

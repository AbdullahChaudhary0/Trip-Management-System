package com.example.demo.BLL;

public class Booking {
    private String source;
    private String destination;
    private int slots;
    private String name;

    public Booking(String source, String destination, int slots, String name) {
        this.source = source;
        this.destination = destination;
        this.slots = slots;
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

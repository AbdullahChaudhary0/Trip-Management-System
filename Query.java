package com.example.demo.BLL;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Query {
    private StringProperty traveller;
    private StringProperty feedback;

    public Query(String traveller, String feedback) {
        this.traveller = new SimpleStringProperty(traveller);
        this.feedback = new SimpleStringProperty(feedback);
    }

    public String getTraveller() {
        return traveller.get();
    }

    public void setTraveller(String value) {
        traveller.set(value);
    }

    public String getFeedback() {
        return feedback.get();
    }

    public void setFeedback(String value) {
        feedback.set(value);
    }
}

package com.example.demo.BLL;

public class Feedback {
    private String name;
    private String chat;

    public Feedback(String name, String chat) {
        this.name = name;
        this.chat = chat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}

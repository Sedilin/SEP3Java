package com.SEP3.SEP3.api.model.DTOs;


import com.SEP3.SEP3.api.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDto {
    private User sender;
    private User receiver;
   private String message;

    public MessageDto(User sender, User receiver, String message)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }
    @JsonProperty("Sender")
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    @JsonProperty("Receiver")
    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @JsonProperty("Message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

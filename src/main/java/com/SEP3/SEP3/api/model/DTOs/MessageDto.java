package com.SEP3.SEP3.api.model.DTOs;


import com.SEP3.SEP3.api.model.User;

public class MessageDto {
    private User Sender;
    private User Receiver;
   private String Message;

    public MessageDto(User Sender, User Receiver, String Message) {
        this.Sender = Sender;
        this.Receiver = Receiver;
        this.Message = Message;
    }

    public User getSender() {
        return Sender;
    }

    public void setSender(User sender) {
        this.Sender = sender;
    }

    public User getReceiver() {
        return Receiver;
    }

    public void setReceiver(User receiver) {
        this.Receiver = receiver;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }
}

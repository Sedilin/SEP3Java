package com.SEP3.SEP3.api.controller;

import com.SEP3.SEP3.api.model.DTOs.MessageDto;
import com.SEP3.SEP3.persistance.IServices.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/archive")
public class MessageController {

    private IMessageService messageService;
    @Autowired
    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping()
    public ResponseEntity<Boolean> archiveMessage(@RequestBody MessageDto dto) {
        boolean success = messageService.archiveMessage(dto.getSender(), dto.getMessage(), dto.getReceiver());
        if (success) {
            return new ResponseEntity<>(success, HttpStatus.OK);//ResponseEntity.noContent().build(); Send back an empty response entity with status 200 (OK)
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
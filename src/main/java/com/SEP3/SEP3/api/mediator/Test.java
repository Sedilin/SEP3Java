package com.SEP3.SEP3.api.mediator;

import com.SEP3.SEP3.api.mediator.MessageDb.MessageDAO;
import com.SEP3.SEP3.api.mediator.MessageDb.MessageDAOImpl;
import com.SEP3.SEP3.api.mediator.UserDb.UserDAO;
import com.SEP3.SEP3.api.mediator.UserDb.UserDAOImpl;
import com.SEP3.SEP3.api.model.DTOs.MessageDto;
import com.SEP3.SEP3.api.model.User;
import com.SEP3.SEP3.persistance.UserService;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        MessageDAO messageDAO = new MessageDAOImpl();

        List<MessageDto> messageDtos = messageDAO.showMessages(5,6);

        for (MessageDto dto : messageDtos
        ) {
            System.out.print(dto.getMessage() + " |");

            System.out.print(dto.getSender().getId() + " |");
            System.out.print(dto.getSender().getUserName() + " |");
            System.out.print(dto.getSender().getPassword() + " |");
            System.out.print(dto.getSender().getUserType() + " |");

            System.out.print(dto.getReceiver().getId() + " |");
            System.out.print(dto.getReceiver().getUserName() + " |");
            System.out.print(dto.getReceiver().getPassword() + " |");
            System.out.println(dto.getReceiver().getUserType() + " |");
        }

    }
}

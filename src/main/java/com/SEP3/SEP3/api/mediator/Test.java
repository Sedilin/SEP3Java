package com.SEP3.SEP3.api.mediator;

import com.SEP3.SEP3.api.mediator.UserDb.UserDAO;
import com.SEP3.SEP3.api.mediator.UserDb.UserDAOImpl;
import com.SEP3.SEP3.api.model.User;
import com.SEP3.SEP3.persistance.UserService;

public class Test {
    public static void main(String[] args) {
        UserDAO users = new UserDAOImpl();
        UserService service = new UserService();
//        users.addUser(new User("Lukasz","1234"));
//        System.out.printf(users.getByUsername("Gabi").getId() + "");
        users.isUserNameExistent("Gabi");
    }
}

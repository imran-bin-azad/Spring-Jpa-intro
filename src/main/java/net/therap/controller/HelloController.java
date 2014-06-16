package net.therap.controller;

import net.therap.InvalidUserException;
import net.therap.domain.User;
import net.therap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {
    @Autowired
    @Qualifier("txAnnotatedUserService")
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        User user = databaseTest();
        model.addAttribute("username", user.getUserName());
        model.addAttribute("password", user.getPassword());
//        testInsertInDB();
        List<User> admins = userService.getListOfAdmins();
        printAllUsersFromList(admins);
        setUsersInModel(admins, model);
        return "hello";
    }

    private User databaseTest() {
        String username = "imran.bin.azad";
        String password = "therap";
        User user = new User(username, password);
        try {
            user = userService.verifyUserAndGetLoginDetails(user);
        } catch (InvalidUserException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void testInsertInDB() {
        User user = new User();
        user.setUserName("sujan");
        user.setPassword("therap");
        user.setName("Sujan");
        user.setAdmin(false);
        userService.addNewUser(user);
    }

    private void printAllUsersFromList(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }

    private void setUsersInModel(List<User> admins, ModelMap model) {
        String listToString = "";
        for (User user : admins) {
            listToString += user.toString();
            listToString += "\n";
        }
        model.addAttribute("usersList", listToString);
    }
}
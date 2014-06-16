package net.therap.service;

import net.therap.InvalidUserException;
import net.therap.domain.User;

import java.util.List;

/**
 * Created by imran.azad on 6/16/14.
 */
public interface UserService {
    public User verifyUserAndGetLoginDetails(User user) throws InvalidUserException;
    public User getUserInfoOf(User user);
    public void addNewUser(User user);
    public List<User> getListOfAdmins();
}

package services;


import model.User;
import DAO.UserDAO;

import java.util.List;

public class UserService {
    private UserDAO userDAO;
    private static volatile UserService instance;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public synchronized User getUserById(String username){
        return userDAO.read(username);
    }

    public synchronized List<User> getAllUsers(){
        return userDAO.readAll("users");
    }

    public synchronized void addUser(User user){
        userDAO.create(user);
    }

    public synchronized void updateUser(User user) {
        userDAO.update(user);
    }

    public synchronized void deleteUser(User user) {
        userDAO.delete(user);
    }
}

package service;

import dao.UserImpl;
import entites.User;

public class AuthService {
    private UserImpl userDao;

    public AuthService() {
        this.userDao = new UserImpl();
    }

    public User signUp(User user) {
        userDao.create(user);
        return user;
    }

    public User signIn(String username, String password) {
        return userDao.authenticate(username, password);
    }
}

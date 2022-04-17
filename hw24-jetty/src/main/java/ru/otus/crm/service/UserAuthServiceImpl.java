package ru.otus.crm.service;

public class UserAuthServiceImpl implements UserAuthService {

    @Override
    public boolean authenticate(String login, String password) {
        return ("user1".equals(login) && "12345".equals(password));
    }

}

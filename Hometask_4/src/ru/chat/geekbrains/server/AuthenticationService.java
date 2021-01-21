package ru.chat.geekbrains.server;

import ru.chat.geekbrains.DBonnection.DBQueries;
import ru.chat.geekbrains.DBonnection.User;

import java.util.Set;

public class AuthenticationService {
    private Set<User> users;

    public void test(){
        users = DBQueries.findAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

    public String findNicknameByLoginAndPassword(String login, String password) {
        users = DBQueries.findAllUsers();
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user.getNickname();
            }
        }
        return null;
    }
    public String findNicknameByLoginAndPassword_2(String login, String password) {
        return  DBQueries.findUser(login, password);

    }

    public String  changeNickname(String login, String password,String newNickname){
            return DBQueries.changeNickname(login,password,newNickname);
    }
}

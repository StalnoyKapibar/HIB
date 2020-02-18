package com.example.server.dao;




import com.example.server.model.User;

import java.util.List;

public interface UserDao  {
    void add(User user);
    List<User> getUser();
    void update(User user);
    void delete(int id);
    User getUserById(int id);
    User findByUsername(String username);


}

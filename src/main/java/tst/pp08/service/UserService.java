package tst.pp08.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import tst.pp08.model.User;

import java.util.List;


public interface UserService extends UserDetailsService {
    boolean add(User user);

    List<User> getUser();

    void update(User user);

    void delete(int id);

    User findByUsername(String username);

    User getById(int id);




}

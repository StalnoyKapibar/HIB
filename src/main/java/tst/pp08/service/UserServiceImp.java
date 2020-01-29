package web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.*;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;


    //   @Autowired
    //   BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override

    public boolean add(User user) {
        if (userDao.findByUsername(user.getUsername()) == null) {

            userDao.add(user);
            return true;
        }
        return false;

    }

    @Override
    public List<User> getUser() {
        return userDao.getUser();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}

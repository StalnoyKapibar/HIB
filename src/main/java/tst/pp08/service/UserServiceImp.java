package tst.pp08.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tst.pp08.model.User;


import java.util.*;


@Service
public class UserServiceImp implements UserService {


    @Autowired
    private RestTemplate restTemplate;


    //   @Autowired
    //   BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override

    public boolean add(User user) {
        final String uri = "http://localhost:8081/server/add";

        if (findByUsername(user.getUsername()) == null) {

            restTemplate.postForObject(uri, user, String.class);

            return true;
        }


        else return false;
    }

    @Override
    public List<User> getUser() {

        final String uri = "http://localhost:8081/server/getuser";
        return restTemplate.getForObject(uri, List.class);
    }

    @Override
    public void update(User user) {

        final String uri = "http://localhost:8081/server/update";
        restTemplate.postForObject(uri, user, String.class);


    }

    @Override
    public void delete(int id) {
        final String uri = "http://localhost:8081/server/del";
        restTemplate.postForObject(uri, id, String.class);
    }

    @Override
    public User findByUsername(String username) {
        final String uri0 = "http://localhost:8081/server/findbyusername";
        return restTemplate.postForObject(uri0, username, User.class);
    }

    @Override
    public User getById(int id) {

        final String uri0 = "http://localhost:8081/server/getbyid";
        return restTemplate.postForObject(uri0, id, User.class);


    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}

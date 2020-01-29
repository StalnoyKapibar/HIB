package web.dao;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import web.model.User;

import java.util.List;

public interface UserDao  {
    void add(User user);
    List<User> getUser();
    void update(User user);
    void delete(int id);
    User getUserById(int id);
    User findByUsername(String username);

}

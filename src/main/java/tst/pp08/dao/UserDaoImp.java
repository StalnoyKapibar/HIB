package web.dao;


import com.sun.istack.NotNull;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<User> getUser() {
        return entityManager.createQuery("FROM User").getResultList();

    }

    @Override
    public void update(User user) {
        entityManager.createQuery("update User  set username = :username, password = :password where id =:id").setParameter("username", user.getUsername()).setParameter("password", user.getPassword()).setParameter("id", user.getId()).executeUpdate();
    }

    @Override
    public void delete(int id) {
        //  entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));

        entityManager.remove(getUserById(id));
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        //  entityManager.find(User.class, username);
        try {
            return (User) entityManager.createQuery("FROM User where username=:username").setParameter("username", username).getSingleResult();
        } catch (NoResultException ex) {
            return null;

        }

    }


    @Override
    public void add(User user) {
        entityManager.persist(user);

    }

}

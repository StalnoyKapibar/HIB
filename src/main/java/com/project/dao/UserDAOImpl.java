package com.project.dao;

import com.project.model.UserAccount;
import com.project.model.UserDTO;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserDTO getUserByLogin(String login) {
        String temp = "Select new com.project.model.UserDTO(ua.userId, ua.login, ua.email, ua.password, ua.firstName, ua.lastName) FROM UserAccount ua where ua.login=:login";
        UserDTO userDTO = entityManager.createQuery(temp, UserDTO.class).setParameter("login", login).getSingleResult();
        return userDTO;
    }

    @Override
    public void saveUserDTOPersonalInformation(UserDTO userDTO) {
        entityManager.createQuery("update UserAccount set email = :email, firstName = :firstName, lastName = :lastName where userId =:userId")
                .setParameter("email", userDTO.getEmail())
                .setParameter("firstName", userDTO.getFirstName())
                .setParameter("lastName", userDTO.getLastName())
                .setParameter("userId", userDTO.getUserId())
                .executeUpdate();
    }

    @Override
    public boolean checkEmailFromOtherUsers(String email, long id) {
        List<UserAccount> userAccountList = entityManager.createQuery("SELECT ua FROM UserAccount ua where ua.email=:email AND ua.userId <>: id")
                .setParameter("email", email)
                .setParameter("id", id)
                .getResultList();
        if (userAccountList.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void saveUserDTOPassword(UserDTO userDTO) {
        entityManager.createQuery("update UserAccount set password = :password where userId =:userId")
                .setParameter("password", userDTO.getPassword())
                .setParameter("userId", userDTO.getUserId())
                .executeUpdate();
    }
}
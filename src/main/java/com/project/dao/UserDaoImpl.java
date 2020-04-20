package com.project.dao;

import com.project.dao.abstraction.UserDao;
import com.project.model.UserAccount;
import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDao<Long, UserAccount> implements UserDao {
    UserDaoImpl() {
        super(UserAccount.class);
    }

    @Override
    public UserDTO getUserByLogin(String login) {
        String temp = "Select new com.project.model.UserDTO(ua.id, ua.login, ua.email, ua.password, ua.firstName, ua.lastName) FROM UserAccount ua where ua.login=:login";
        UserDTO userDTO = entityManager.createQuery(temp, UserDTO.class).setParameter("login", login).getSingleResult();
        return userDTO;
    }

    @Override
    public void saveUserDTOPersonalInformation(UserDTO userDTO) {
        entityManager.createQuery("update UserAccount set email = :email, firstName = :firstName, lastName = :lastName where id =:userId")
                .setParameter("email", userDTO.getEmail())
                .setParameter("firstName", userDTO.getFirstName())
                .setParameter("lastName", userDTO.getLastName())
                .setParameter("userId", userDTO.getUserId())
                .executeUpdate();
    }

    @Override
    public boolean checkEmailFromOtherUsers(String email, long id) {
        List<UserAccount> userAccountList = entityManager.createQuery("SELECT ua FROM UserAccount ua where ua.email=:email AND ua.id <>: id")
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
    public void saveUserDTOPassword(UserDTONewPassword userDTONewPassword) {
        entityManager.createQuery("update UserAccount set password = :password where id =:userId")
                .setParameter("password", userDTONewPassword.getNewPassword())
                .setParameter("userId", userDTONewPassword.getUserId())
                .executeUpdate();
    }

    @Override
    public String getOldPassword(long id) {
        return (String) entityManager.createQuery("SELECT ua.password FROM UserAccount ua where ua.id=:userId").setParameter("userId", id).getSingleResult();
    }
}

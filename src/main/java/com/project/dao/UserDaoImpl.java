package com.project.dao;

import com.project.dao.abstraction.UserDao;
import com.project.model.UserAccount;
import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserDaoImpl extends AbstractDao<Long, UserAccount> implements UserDao {
    UserDaoImpl() {
        super(UserAccount.class);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        String temp = "SELECT new com.project.model.UserDTO(ua.id, ua.email, ua.password, ua.firstName, ua.lastName, false, ua.roles, ua.phone) " +
                "FROM UserAccount ua WHERE ua.email = :email";
        try {
            return entityManager
                    .createQuery(temp, UserDTO.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            throw new UsernameNotFoundException(email);
        }
    }

    @Override
    public void saveUserDTOPersonalInformation(UserDTO userDTO) {
        entityManager.createQuery("UPDATE UserAccount" +
                " SET email = :email, firstName = :firstName, lastName = :lastName, phone = :phone" +
                " WHERE id =:userId")
                .setParameter("email", userDTO.getEmail())
                .setParameter("firstName", userDTO.getFirstName())
                .setParameter("lastName", userDTO.getLastName())
                .setParameter("phone", userDTO.getPhone())
                .setParameter("userId", userDTO.getUserId())
                .executeUpdate();
    }

    @Override
    public boolean checkEmailFromOtherUsers(String email, long id) {
        List<UserAccount> userAccountList = entityManager
                .createQuery("SELECT ua " +
                        "FROM UserAccount ua " +
                        "WHERE ua.email=:email " +
                        "AND ua.id <>: id", UserAccount.class)
                .setParameter("email", email)
                .setParameter("id", id)
                .getResultList();
        return userAccountList.size() <= 0;
    }

    @Override
    public void saveUserDTOPassword(UserDTONewPassword userDTONewPassword) {
        entityManager.createQuery("UPDATE UserAccount " +
                "SET password = :password " +
                "WHERE id =:userId")
                .setParameter("password", userDTONewPassword.getNewPassword())
                .setParameter("userId", userDTONewPassword.getUserId())
                .executeUpdate();
    }

    @Override
    public String getOldPassword(long id) {
        return (String) entityManager.createQuery("SELECT ua.password " +
                "FROM UserAccount ua " +
                "WHERE ua.id=:userId")
                .setParameter("userId", id)
                .getSingleResult();
    }
}

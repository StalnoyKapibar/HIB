package com.project.dao.abstraction;

import com.project.model.UserAccount;
import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;

public interface UserDao extends GenericDao<Long, UserAccount> {
    UserDTO getUserByLogin(String login);

    void saveUserDTOPersonalInformation(UserDTO userDTO);

    boolean checkEmailFromOtherUsers(String email, long id);

    void saveUserDTOPassword(UserDTONewPassword userDTONewPassword);

    String getOldPassword(long id);
}

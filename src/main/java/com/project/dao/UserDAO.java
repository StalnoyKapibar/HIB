package com.project.dao;

import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;

public interface UserDAO {
    UserDTO getUserByLogin(String login);
    void saveUserDTOPersonalInformation(UserDTO userDTO);
    boolean checkEmailFromOtherUsers(String email, long id);
    void saveUserDTOPassword(UserDTONewPassword userDTONewPassword);
    String getOldPassword(long id);
}

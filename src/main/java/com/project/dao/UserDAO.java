package com.project.dao;

import com.project.model.UserDTO;

public interface UserDAO {
    UserDTO getUserByLogin(String login);
    void saveUserDTOPersonalInformation(UserDTO userDTO);
    boolean checkEmailFromOtherUsers(String email, long id);
    void saveUserDTOPassword(UserDTO userDTO);
}

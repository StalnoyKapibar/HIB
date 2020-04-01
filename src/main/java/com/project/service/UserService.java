package com.project.service;

import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;

public interface UserService {
    UserDTO getUserDTOByLogin(String login);
    String saveUserDTOPersonalInformation(UserDTO userDTO);
    boolean checkEmailFromOtherUsers(String email, long id);
    String saveUserDTOPassword(UserDTONewPassword userDTONewPassword);
}

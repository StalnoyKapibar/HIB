package com.project.service.abstraction;

import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;

public interface UserService {
    UserDTO getUserDTOByLogin(String login, boolean isOAuth2Acc);
    String saveUserDTOPersonalInformation(UserDTO userDTO);
    boolean checkEmailFromOtherUsers(String email, long id);
    String saveUserDTOPassword(UserDTONewPassword userDTONewPassword);
}

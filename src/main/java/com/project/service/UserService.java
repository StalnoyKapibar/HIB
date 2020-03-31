package com.project.service;

import com.project.model.UserDTO;

public interface UserService {
    UserDTO getUserDTOByLogin(String login);
    String saveUserDTOPersonalInformation(UserDTO userDTO);
    boolean checkEmailFromOtherUsers(String email, long id);
    String saveUserDTOPassword(UserDTO userDTO);
}

package com.project.service;

import com.project.model.UserDTO;

public interface UserService {
    UserDTO getUserDTOByLogin(String login);
    void saveUserDTOPersonalInformation(UserDTO userDTO);
    boolean checkEmailFromOtherUsers(String email, long id);
    void saveUserDTOPassword(UserDTO userDTO);
}

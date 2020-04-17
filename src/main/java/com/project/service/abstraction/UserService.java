package com.project.service.abstraction;

import com.project.model.UserDto;
import com.project.model.UserDtoNewPassword;

public interface UserService {
    UserDto getUserDTOByLogin(String login);
    String saveUserDTOPersonalInformation(UserDto userDTO);
    boolean checkEmailFromOtherUsers(String email, long id);
    String saveUserDTOPassword(UserDtoNewPassword userDTONewPassword);
}

package com.project.service.abstraction;

import com.project.model.*;

import java.util.List;

public interface UserService {
    UserDTO getUserDTOByEmail(String email, boolean isOAuth2Acc);
    String saveUserDTOPersonalInformation(UserDTO userDTO);

    RegistrationUserDTO converterContactsToRegistrationUser(ContactsOfOrderDTO contacts);

    boolean checkEmailFromOtherUsers(String email, long id);
    String saveUserDTOPassword(UserDTONewPassword userDTONewPassword);
}

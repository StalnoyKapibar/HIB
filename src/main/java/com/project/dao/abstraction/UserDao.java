package com.project.dao.abstraction;

import com.project.model.UserAccount;
import com.project.model.UserDto;
import com.project.model.UserDtoNewPassword;

public interface UserDao extends GenericDao<Long, UserAccount> {
    UserDto getUserByLogin(String login);

    void saveUserDTOPersonalInformation(UserDto userDTO);

    boolean checkEmailFromOtherUsers(String email, long id);

    void saveUserDTOPassword(UserDtoNewPassword userDTONewPassword);

    String getOldPassword(long id);
}

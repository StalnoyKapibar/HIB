package com.project.service;

import com.project.dao.abstraction.UserDao;
import com.project.model.UserDTO;
import com.project.model.UserDTONewPassword;
import com.project.service.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private PasswordEncoder encoder;
    private UserDao userDAO;

    @Autowired
    public UserServiceImpl(PasswordEncoder encoder, UserDao userDAO) {
        this.encoder = encoder;
        this.userDAO = userDAO;
    }

    @Override
    public UserDTO getUserDTOByLogin(String login, boolean isOAuth2Acc) {
         UserDTO userDTO  = userDAO.getUserByLogin(login);
         userDTO.setOauth2Acc(isOAuth2Acc);
        return userDTO;
    }

    @Override
    public String saveUserDTOPersonalInformation(UserDTO userDTO) {
        String reg = "^(.+)@([a-zA-Z]+)\\.([a-zA-Z]+)$";
        if (!checkEmailFromOtherUsers(userDTO.getEmail(), userDTO.getUserId())) {
            return "error";
        } else {
            if (Pattern.matches(reg, userDTO.getEmail())) {
                userDAO.saveUserDTOPersonalInformation(userDTO);
                return "ok";
            } else {
                return "synError";
            }
        }
    }

    @Override
    public boolean checkEmailFromOtherUsers(String email, long id) {
        return userDAO.checkEmailFromOtherUsers(email, id);
    }

    @Override
    public String saveUserDTOPassword(UserDTONewPassword userDTONewPassword) {
        String reg = "^[a-zA-Z0-9]{5,}$";
        if (Pattern.matches(reg, userDTONewPassword.getNewPassword()) && (userDTONewPassword.getNewPassword().length() >= 5 && userDTONewPassword.getNewPassword().length() <= 64)) {
            if (encoder.matches(userDTONewPassword.getOldPassword(), userDAO.getOldPassword(userDTONewPassword.getUserId()))) {
                String newPass = encoder.encode(userDTONewPassword.getNewPassword());
                userDTONewPassword.setNewPassword(newPass);
                userDAO.saveUserDTOPassword(userDTONewPassword);
                return "passOk";
            } else {
                return "wrongPassword";
            }
        } else {
            return "passError";
        }
    }
}

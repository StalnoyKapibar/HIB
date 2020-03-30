package com.project.service;

import com.project.dao.UserDAO;
import com.project.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDTO getUserDTOByLogin(String login) {
        return userDAO.getUserByLogin(login);
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
    public String saveUserDTOPassword(UserDTO userDTO) {
        String reg = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}$";
        if (Pattern.matches(reg, userDTO.getPassword()) && (userDTO.getPassword().length() >= 8 && userDTO.getPassword().length() <= 64)) {
            String tmpPasswordEncode = encoder.encode(userDTO.getPassword());
            userDTO.setPassword(tmpPasswordEncode);
            userDAO.saveUserDTOPassword(userDTO);
            return "passOk";
        } else {
            return "passError";
        }
    }
}

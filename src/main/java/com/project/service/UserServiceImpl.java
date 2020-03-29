package com.project.service;

import com.project.dao.UserDAO;
import com.project.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDTO getUserDTOByLogin(String login) {
        return userDAO.getUserByLogin(login);
    }

    @Override
    public void saveUserDTOPersonalInformation(UserDTO userDTO) {
        userDAO.saveUserDTOPersonalInformation(userDTO);
    }

    @Override
    public boolean checkEmailFromOtherUsers(String email, long id) {
       return userDAO.checkEmailFromOtherUsers(email, id);
    }

    @Override
    public void saveUserDTOPassword(UserDTO userDTO) {
        userDAO.saveUserDTOPassword(userDTO);
    }
}

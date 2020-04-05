package com.project.service;

import com.project.model.UserDTONewPassword;
import com.project.model.UserDTOResetPassword;

public interface ResetPasswordService {
    String sendEmailResetPassword(String email);

    boolean checkTokenResetPassword(String token);

    String saveNewPasswordReset(UserDTOResetPassword userDTOResetPassword);
}

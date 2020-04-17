package com.project.service.abstraction;

import com.project.model.UserDtoResetPassword;

public interface ResetPasswordService {
    String sendEmailResetPassword(String email);

    boolean checkTokenResetPassword(String token);

    String saveNewPasswordReset(UserDtoResetPassword userDTOResetPassword);
}

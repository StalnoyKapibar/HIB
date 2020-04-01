package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTONewPassword {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}

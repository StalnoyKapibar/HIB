package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private Long userId;
    private String login;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isOauth2Acc;
}

package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDTO {
    @Email
    @Pattern(regexp = "^(.+)@([a-zA-Z]+)\\.([a-zA-Z]+)$", message = "Email не соответствует шаблону")
    private String email;

    @Size(min = 5, max = 64, message = "пароль должен быть в диапазоне от 5 до 64")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,}$", message = "Пароль может содержать числа, символы в верхнем и нижнем регистрах, без пробела")
    private String password;

    private String firstName;

    private String lastName;

    private long regDate;

    private long lastSeenDate;

    private String provider;

    private boolean autoReg;

    private String phone;
}

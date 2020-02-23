package com.project.model;

import lombok.*;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WelcomeLocaleDTO {
    private String nameLocale;
}

package com.project.model;

import lombok.*;

import javax.persistence.Entity;


@Data
public class WelcomeLocaleDTO {

    private String nameLocale;



    public WelcomeLocaleDTO(String nameLocale) {
        this.nameLocale = nameLocale;
    }
    public WelcomeLocaleDTO() {

    }
}

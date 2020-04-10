package com.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "otherLanguage")
public class OtherLanguage {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String translitNameBook;
    private String translitAuthor;
    private String otherLangNameBook;
    private String otherLangAuthor;
}

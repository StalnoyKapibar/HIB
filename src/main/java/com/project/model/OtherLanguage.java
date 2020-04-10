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
    @Column
    private String translitNameBook;
    @Column
    private String translitAuthor;
    @Column
    private String otherLangNameBook;
    @Column
    private String otherLangAuthor;
}

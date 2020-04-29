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
    private String otherLangName;
    @Column
    private String transliterateName;
    @Column
    private String otherLangAuthor;
    @Column
    private String transliterateAuthor;
    @Column
    private String otherLangDesc;
    @Column
    private String transliterateDesc;
    @Column
    private String otherLangEdition;
    @Column
    private String transliterateEdition;
}

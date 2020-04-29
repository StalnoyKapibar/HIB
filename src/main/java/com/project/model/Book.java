package com.project.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {

    public Book(LocaleString nameLocale, LocaleString authorLocale) {
        this.nameLocale = nameLocale;
        this.authorLocale = authorLocale;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString nameLocale;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString authorLocale;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString desc;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString edition;

    private String yearOfEdition;

    private Long pages;

    private Long price;

    private String coverImage;

    private String originalLanguage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Singular("listImage")
    private List<Image> listImage;

    private Long views;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private OtherLanguage otherLanguageOfBook;

    private boolean isShow = true;

    @OneToOne
    private Category category;
}

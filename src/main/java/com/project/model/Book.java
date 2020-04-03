package com.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@NoArgsConstructor
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
    private long id;

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

    private Integer price;

    private String coverImage;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Image> listImage;

    @Column
    private Long views;
}

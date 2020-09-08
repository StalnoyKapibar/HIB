package com.project.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "book")
public class Book {

    public Book(LocaleString nameLocale, LocaleString author) {
        this.name = nameLocale;
        this.author = author;
    }

    public Book(Long id, OriginalLanguage originalLanguage, Long price, String coverImage) {
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.price = price;
        this.coverImage = coverImage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString author;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString edition;

    private Long yearOfEdition;

    private Long pages;

    private Long price;

    private String coverImage;

    private String originalLanguageName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Singular("listImage")
    private List<Image> listImage;

    private Long views;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private OriginalLanguage originalLanguage;

    private boolean isShow;

    private boolean lastBookOrdered;

    @OneToOne
    private Category category;
}

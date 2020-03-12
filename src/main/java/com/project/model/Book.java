package com.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
//    @JoinTable(name = "name_of_book_locale",
//            joinColumns = @JoinColumn(name = "name_id", referencedColumnName = "id"))
    private LocaleString nameLocale;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "author_locale",
//            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private LocaleString authorLocale;
}

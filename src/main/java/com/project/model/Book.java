package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cover_image")
    String coverImage;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "name_of_book_locale",
            joinColumns = @JoinColumn(name = "name_id", referencedColumnName = "id"))
    private LocaleString nameLocale;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "author_locale",
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private LocaleString authorLocale;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "book_image",
            joinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    private List<Image> listImage;
}

package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Indexed
@NoArgsConstructor
@AllArgsConstructor
public class OriginalLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Field
    private String name;
    @Field
    private String author;
    @Field
    private String edition;
    @Field
    private String nameTranslit;
    @Field
    private String authorTranslit;
    @Field
    private String editionTranslit;

    public OriginalLanguage(String name, String author, String edition,
                            String nameTranslit, String authorTranslit, String editionTranslit){
        this.name = name;
        this.author = author;
        this.edition = edition;
        this.nameTranslit = nameTranslit;
        this.authorTranslit = authorTranslit;
        this.editionTranslit = editionTranslit;
    }
}

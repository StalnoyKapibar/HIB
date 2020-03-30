package com.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Data
@Entity
@Indexed
@Table(name = "localString")
public class LocaleString {

    public LocaleString(String ru, String en, String fr, String it, String de, String cs, String gr) {
        this.ru = ru;
        this.en = en;
        this.fr = fr;
        this.it = it;
        this.de = de;
        this.cs = cs;
        this.gr = gr;
    }

    public LocaleString(Map<String, String> languages) {
        this.ru = languages.get("ru");
        this.en = languages.get("en");
        this.fr = languages.get("fr");
        this.it = languages.get("it");
        this.de = languages.get("de");
        this.cs = languages.get("cs");
        this.gr = languages.get("gr");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field
    @Column(name = "ru")
    private String ru;

    @Field
    @Column(name = "en")
    private String en;

    @Field
    @Column(name = "fr")
    private String fr;

    @Field
    @Column(name = "it")
    private String it;

    @Field
    @Column(name = "de")
    private String de;

    @Field
    @Column(name = "cs")
    private String cs;

    @Field
    @Column(name = "gr")
    private String gr;
}

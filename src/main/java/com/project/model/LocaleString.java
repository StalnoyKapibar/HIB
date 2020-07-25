package com.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
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
    @Column(name = "ru", columnDefinition = "text")
    private String ru;

    @Field
    @Column(name = "en", columnDefinition = "text")
    private String en;

    @Field
    @Column(name = "fr", columnDefinition = "text")
    private String fr;

    @Field
    @Column(name = "it", columnDefinition = "text")
    private String it;

    @Field
    @Column(name = "de", columnDefinition = "text")
    private String de;

    @Field
    @Column(name = "cs", columnDefinition = "text")
    private String cs;

    @Field
    @Column(name = "gr", columnDefinition = "text")
    private String gr;
}
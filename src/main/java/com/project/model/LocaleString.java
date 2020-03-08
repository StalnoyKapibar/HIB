package com.project.model;

import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "localString")
public class LocaleString {

    public LocaleString(String ru, String en, String fr, String it, String de, String cs) {
        this.ru = ru;
        this.en = en;
        this.fr = fr;
        this.it = it;
        this.de = de;
        this.cs = cs;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ru")
    private String ru;

    @Column(name = "en")
    private String en;

    @Column(name = "fr")
    private String fr;

    @Column(name = "it")
    private String it;

    @Column(name = "de")
    private String de;

    @Column(name = "cs")
    private String cs;
}

package com.project.model;


import lombok.*;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity
@Table(name = "localString")
public class LocaleString {


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

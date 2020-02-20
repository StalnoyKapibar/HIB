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
    private long id;


    @Column(name = "ru", nullable = false)
    private String ru;


    @Column(name = "en", nullable = false)
    private String en;


    @Column(name = "fr", nullable = false)
    private String fr;


    @Column(name = "it", nullable = false)
    private String it;


    @Column(name = "de", nullable = false)
    private String de;


    @Column(name = "cs", nullable = false)
    private String cs;






}

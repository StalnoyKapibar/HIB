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
    @NonNull
    private long id;


    @Column(name = "ru")
    @NonNull
    private String ru;


    @Column(name = "en")
    @NonNull
    private String en;


    @Column(name = "fr")
    @NonNull
    private String fr;


    @Column(name = "it")
    @NonNull
    private String it;


    @Column(name = "de")
    @NonNull
    private String de;


    @Column(name = "cs")
    @NonNull
    private String cs;






}

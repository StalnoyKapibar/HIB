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


    @Column(name = "ru", nullable = false)
    @NonNull
    private String ru;


    @Column(name = "en", nullable = false)
    @NonNull
    private String en;


    @Column(name = "fr", nullable = false)
    @NonNull
    private String fr;


    @Column(name = "it", nullable = false)
    @NonNull
    private String it;


    @Column(name = "de", nullable = false)
    @NonNull
    private String de;


    @Column(name = "cs", nullable = false)
    @NonNull
    private String cs;






}

package com.project.model;


import javax.persistence.*;


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





    public long getId() {
        return id;
    }

    public String getRu() {
        return ru;
    }

    public String getEn() {
        return en;
    }

    public String getFr() {
        return fr;
    }

    public String getIt() {
        return it;
    }

    public String getDe() {
        return de;
    }

    public String getCs() {
        return cs;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setRu(String ru) {
        this.ru = ru;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public void setIt(String it) {
        this.it = it;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }


}

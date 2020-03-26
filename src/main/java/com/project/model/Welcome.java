package com.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Table(name = "welcome")
public class Welcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LocaleString body;
}

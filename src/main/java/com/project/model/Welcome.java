package com.project.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "welcome")
public class Welcome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "welcome_body",
            joinColumns = @JoinColumn(name = "welcome_id", referencedColumnName = "id"))

    private LocaleString body;





}

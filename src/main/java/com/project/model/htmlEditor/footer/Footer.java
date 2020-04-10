package com.project.model.htmlEditor.footer;

import com.project.model.htmlEditor.Link;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Footer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Link> links;

    private Long updateDate;
}

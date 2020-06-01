package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feedback_request")
public class FeedbackRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "content")
    private String content;

    @Column(name = "sender_email")
    private String senderEmail;

    @Column(name = "replied")
    private Boolean replied;

    @OneToOne(cascade = CascadeType.MERGE)
    private Book book;

    @Column(name = "viewed")
    private Boolean viewed;
}

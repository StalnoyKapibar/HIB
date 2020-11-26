package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.controller.restcontroller.emailUtil.emailParser.EmailParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDTO {
    private String messageId;
    private String sender;
    private String text;
    private String subject;
    @JsonIgnore
    private EmailParser emailParser;
}
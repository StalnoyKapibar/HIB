package com.project.controller.restcontroller;

import com.project.mail.MailService;
import com.project.model.FeedbackRequest;
import com.project.service.FeedbackRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback-request")
public class FeedbackController {
    private final static Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class.getName());
    private final FeedbackRequestService feedbackRequestService;
    private final MailService mailService;

    @Autowired
    public FeedbackController(FeedbackRequestService feedbackRequestService, MailService mailService) {
        this.feedbackRequestService = feedbackRequestService;
        this.mailService = mailService;
    }

    @PostMapping()
    public FeedbackRequest sendNewFeedBackRequest(@RequestBody FeedbackRequest feedbackRequest) {
        LOGGER.info("POST request '/feedback-request' with {}", feedbackRequest);
        feedbackRequest.setReplied(false);
        return feedbackRequestService.save(feedbackRequest);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/reply")
    public void send(@RequestBody SimpleMailMessage simpleMailMessage) {
        LOGGER.info("POST request '/feedback-request/reply' with {}", simpleMailMessage);
        mailService.sendEmail(simpleMailMessage);
    }

    @GetMapping("/test")
    public SimpleMailMessage test() {
        return new SimpleMailMessage();
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public List<FeedbackRequest> getAll() {
        return feedbackRequestService.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("{id}")
    public FeedbackRequest getById(@PathVariable Long id) {
        return feedbackRequestService.getById(id);
    }
}

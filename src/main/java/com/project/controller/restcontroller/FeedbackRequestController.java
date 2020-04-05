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
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
@RequestMapping("/feedback-request")
public class FeedbackRequestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(FeedbackRequestController.class.getName());
    private final FeedbackRequestService feedbackRequestService;
    private final MailService mailService;

    @Autowired
    public FeedbackRequestController(FeedbackRequestService feedbackRequestService, MailService mailService) {
        this.feedbackRequestService = feedbackRequestService;
        this.mailService = mailService;
    }

    @PostMapping
    public FeedbackRequest sendNewFeedBackRequest(@RequestBody FeedbackRequest feedbackRequest) {
        LOGGER.debug("POST request '/feedback-request' with {}", feedbackRequest);
        feedbackRequest.setId(null);
        feedbackRequest.setReplied(false);
        feedbackRequest.setSenderName(HtmlUtils.htmlEscape(feedbackRequest.getSenderName()));
        feedbackRequest.setContent(HtmlUtils.htmlEscape(feedbackRequest.getContent()));
        feedbackRequest.setSenderEmail(HtmlUtils.htmlEscape(feedbackRequest.getSenderEmail()));
        return feedbackRequestService.save(feedbackRequest);
    }

    @PostMapping("/reply/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void send(@PathVariable Long id, @RequestBody SimpleMailMessage simpleMailMessage) {
        FeedbackRequest feedbackRequest = feedbackRequestService.getById(id);
        simpleMailMessage.setTo(feedbackRequest.getSenderEmail());
        simpleMailMessage.setFrom("hibthebestproject@yandex.ru");
        feedbackRequest.setReplied(true);
        feedbackRequestService.save(feedbackRequest);
        LOGGER.debug("POST request '/feedback-request/reply/{}' with {}", id, simpleMailMessage);
        mailService.sendEmail(simpleMailMessage);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<FeedbackRequest> getAll() {
        return feedbackRequestService.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("{id}")
    public FeedbackRequest getById(@PathVariable Long id) {
        return feedbackRequestService.getById(id);
    }
}

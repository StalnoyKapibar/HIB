package com.project.controller.restcontroller;

import com.project.mail.MailService;
import com.project.model.FeedbackRequest;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.FeedbackRequestService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RestController
@AllArgsConstructor
@PropertySource("classpath:application.properties")
public class FeedbackRequestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(FeedbackRequestController.class.getName());
    private final FeedbackRequestService feedbackRequestService;
    private final MailService mailService;
    private final Environment env;
    private final BookService bookService;

    @PostMapping
    @RequestMapping(value = "/api/feedback-request", params = "bookId")
    public FeedbackRequest sendNewFeedBackRequest(@RequestBody FeedbackRequest feedbackRequest, @RequestParam("bookId") String bookId) {
        LOGGER.debug("POST request '/feedback-request' with {}", feedbackRequest);
        feedbackRequest.setId(null);
        feedbackRequest.setReplied(false);
        feedbackRequest.setSenderName(HtmlUtils.htmlEscape(feedbackRequest.getSenderName()));
        feedbackRequest.setContent(HtmlUtils.htmlEscape(feedbackRequest.getContent()));
        feedbackRequest.setSenderEmail(HtmlUtils.htmlEscape(feedbackRequest.getSenderEmail()));
        if (!bookId.equals("null")) {
            feedbackRequest.setBook(bookService.getBookById(Long.parseLong(bookId)));
        }
        return feedbackRequestService.save(feedbackRequest);
    }

    @PostMapping("/api/admin/feedback-request/{id}/{replied}")
    public void markFeedbackAsRead(@PathVariable Long id, @PathVariable Boolean replied){
        FeedbackRequest feedbackRequest = feedbackRequestService.getById(id);
        feedbackRequest.setReplied(!replied);
        feedbackRequestService.save(feedbackRequest);
    }

    @SuppressWarnings("all")
    @PostMapping("/api/admin/feedback-request/reply/{id}")
    public void send(@PathVariable Long id, @RequestBody SimpleMailMessage simpleMailMessage) {
        LOGGER.debug("POST request '/feedback-request/reply/{}' with {}", id, simpleMailMessage);
        FeedbackRequest feedbackRequest = feedbackRequestService.getById(id);
        simpleMailMessage.setTo(feedbackRequest.getSenderEmail());
        simpleMailMessage.setFrom(env.getProperty("spring.mail.username"));
        feedbackRequest.setReplied(true);
        feedbackRequestService.save(feedbackRequest);
        mailService.sendEmail(simpleMailMessage);
    }

    @GetMapping(value = "/api/admin/feedback-request", params = "!replied")
    public List<FeedbackRequest> getAll() {
        return feedbackRequestService.findAll();
    }

    @GetMapping("/api/admin/feedback-request")
    public List<FeedbackRequest> getByReplied(@RequestParam Boolean replied) {
        return feedbackRequestService.getByReplied(replied);
    }

    @GetMapping("/api/admin/feedback-request/{id}")
    public FeedbackRequest getById(@PathVariable Long id) {
        return feedbackRequestService.getById(id);
    }
}

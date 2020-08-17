package com.project.controller.restcontroller;

import com.project.mail.MailService;
import com.project.model.DataEnterInAdminPanel;
import com.project.model.FeedbackRequest;
import com.project.service.DataEnterInAdminPanelService;
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

import javax.servlet.http.HttpSession;
import java.time.Instant;
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
    private DataEnterInAdminPanelService dataEnterInAdminPanelService;

    @PostMapping(value = "/api/feedback-request", params = "book_id")
    public FeedbackRequest sendNewFeedBackRequest(@RequestBody FeedbackRequest feedbackRequest,
                                                  @RequestParam("book_id") String bookId) {
        LOGGER.debug("POST request '/feedback-request' with {}", feedbackRequest);
        feedbackRequest.setId(null);
        feedbackRequest.setData(Instant.now().getEpochSecond());
        feedbackRequest.setReplied(false);
        feedbackRequest.setViewed(false);
        feedbackRequest.setSenderName(HtmlUtils.htmlEscape(feedbackRequest.getSenderName()));
        feedbackRequest.setContent(HtmlUtils.htmlEscape(feedbackRequest.getContent()));
        feedbackRequest.setSenderEmail(HtmlUtils.htmlEscape(feedbackRequest.getSenderEmail()));
        if (!bookId.equals("null")) {
            feedbackRequest.setBook(bookService.getBookById(Long.parseLong(bookId)));
        }
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(env.getProperty("spring.mail.username"));
        mailMessage.setTo(feedbackRequest.getSenderEmail());
        mailMessage.setSubject(env.getProperty("spring.mail.subject"));
        mailMessage.setText("Dear " + feedbackRequest.getSenderName() +
                ".\n" + env.getProperty("spring.mail.request") +
                "\n" + feedbackRequest.getContent() +
                "\n" + env.getProperty("spring.mail.answer"));
        mailService.sendEmail(mailMessage, feedbackRequest.getSenderEmail());
        return feedbackRequestService.save(feedbackRequest);
    }

    @PostMapping("/api/admin/feedback-request/{id}/{viewed}")
    public void markFeedbackAsRead(@PathVariable Long id, @PathVariable Boolean viewed) {
        FeedbackRequest feedbackRequest = feedbackRequestService.getById(id);
        feedbackRequest.setViewed(viewed);
        feedbackRequest.setReplied(true);
        feedbackRequestService.save(feedbackRequest);
    }

    @PostMapping("/api/admin/feedback-request/replied/{id}/{status}")
    public void markFeedback(@PathVariable Long id, @PathVariable Boolean status) {
        FeedbackRequest feedbackRequest = feedbackRequestService.getById(id);
        feedbackRequest.setReplied(status);
        feedbackRequest.setViewed(status);
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
        feedbackRequest.setViewed(true);
        feedbackRequestService.save(feedbackRequest);
        mailService.sendEmail(simpleMailMessage, feedbackRequest.getSenderEmail());
    }

    @GetMapping(value = "/api/admin/feedback-request", params = "!viewed")
    public List<FeedbackRequest> getAll() {
        return feedbackRequestService.findAll();
    }

    @GetMapping(value = "/api/admin/feedback-request/{senderEmail}/replied")
    public List<FeedbackRequest> getBySenderByReplied(@PathVariable("senderEmail") String senderEmail,
                                                      @RequestParam Boolean replied) {
        return feedbackRequestService.getBySenderByReplied(senderEmail, replied);
    }

    @GetMapping("/api/admin/feedback-request")
    public List<FeedbackRequest> getByReplied(HttpSession session, @RequestParam Boolean viewed) {
        DataEnterInAdminPanel data = (DataEnterInAdminPanel) session.getAttribute("data");
        data.setDataEnterInFeedback(Instant.now().getEpochSecond());
        dataEnterInAdminPanelService.update(data);
        session.setAttribute("data", data);
        return feedbackRequestService.getByViewed(viewed);
    }

    @GetMapping("/api/admin/feedback-request/{id}")
    public FeedbackRequest getById(@PathVariable Long id) {
        return feedbackRequestService.getById(id);
    }

    @GetMapping(value = "/api/admin/feedback-request-count")
    public int getFeedbackRequestCount(HttpSession session) {
        if (session.getAttribute("data") == null) {
            session.setAttribute("data", dataEnterInAdminPanelService.findById(1L));
        }
        return feedbackRequestService.getCountOfFeedBack();
    }

    @GetMapping(value = "/api/admin/feedback-request/{senderEmail}/amount")
    public Long[] getAmountOfFeedback(@PathVariable("senderEmail") String senderEmail) {
        return feedbackRequestService.getAmountOfFeedback(senderEmail);
    }

    @GetMapping("/api/admin/feedback-request/book-id/{id}")
    public List<FeedbackRequest> getFeedbackByIdBook(@PathVariable Long id) {
        return feedbackRequestService.findAllRequestByIdBook(id);
    }

    @GetMapping("/api/admin/feedback-requests/unread/book-id/{id}")
    public List<FeedbackRequest> getUnreadFeedbacksByBookId(@PathVariable Long id) {
        return feedbackRequestService.findAllUnreadRequestsByBookId(id);
    }
}

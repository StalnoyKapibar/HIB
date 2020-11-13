package com.project.controller.restcontroller.swagger_annotated;

import com.project.mail.MailService;
import com.project.model.DataEnterInAdminPanel;
import com.project.model.FeedbackRequest;
import com.project.service.DataEnterInAdminPanelService;
import com.project.service.abstraction.BookService;
import com.project.service.abstraction.FeedbackRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.*;

@CrossOrigin(origins = "*")
@Api(tags = "REST-API документ, " +
        "описывающий взаимодействие с сервисом обратной связи: feedback")
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

    //добавление запроса в базу данных, отправка ответа на почту
    @ApiOperation(value = "Добавление запроса в БД, отправка ответа на почту",
            notes = "Эндпоинт получает параметр feedbackRequest типа FeedbackRequest и bookId типа String",
            response = FeedbackRequest.class)
    @PostMapping(value = "/api/feedback-request", params = "book_id")
    public FeedbackRequest sendNewFeedBackRequest(
            @ApiParam(value = "FeedbackRequest", required = true)
            @RequestBody FeedbackRequest feedbackRequest,
            @ApiParam(value = "String")
            @RequestParam("book_id") String bookId)
    {
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
        FeedbackRequest savedFeedbackRequest = feedbackRequestService.save(feedbackRequest);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(env.getProperty("spring.mail.username"));
        mailMessage.setTo(feedbackRequest.getSenderEmail());
        mailMessage.setSubject("Feedback №" + savedFeedbackRequest.getId());
        mailMessage.setText("Dear " + feedbackRequest.getSenderName() +
                ".\n" + env.getProperty("spring.mail.request") +
                "\n" + feedbackRequest.getContent() +
                "\n" + env.getProperty("spring.mail.answer"));
        mailService.sendEmail(mailMessage, feedbackRequest.getSenderEmail());
        return savedFeedbackRequest;
    }

    @ApiOperation(value = "Установить флаг о просмотре feedbackRequest",
            notes = "Эндпоинт получает id типа Long, для нахождения feedbackRequest" +
                    ", и viewed типа Boolean, который будет установлен для feedbackRequest.viewed")
    @PostMapping("/api/admin/feedback-request/{id}/{viewed}")
    public void markFeedbackAsRead(@ApiParam(value = "id", required = true) @PathVariable Long id,
                                   @ApiParam(value = "viewed", required = true)@PathVariable Boolean viewed) {
        FeedbackRequest feedbackRequest = feedbackRequestService.getById(id);
        feedbackRequest.setViewed(viewed);
        feedbackRequest.setReplied(true);
        feedbackRequestService.save(feedbackRequest);
    }

    @ApiOperation(value = "Изменить флаг о просмотре feedbackRequest",
            notes = "Эндпоинт получает id типа Long, для нахождения feedbackRequest" +
                    ", и status типа Boolean, который будет установлен для feedbackRequest.replied")
    @PostMapping("/api/admin/feedback-request/replied/{id}/{status}")
    public void markFeedback(@ApiParam(value = "id", required = true) @PathVariable Long id,
                             @ApiParam(value = "status", required = true)@PathVariable Boolean status) {
        FeedbackRequest feedbackRequest = feedbackRequestService.getById(id);
        feedbackRequest.setReplied(status);
        feedbackRequest.setViewed(status);
        feedbackRequestService.save(feedbackRequest);
    }


    @SuppressWarnings("all")
    @ApiOperation(value = "Отправка письма по указанному id feedbackRequest",
            notes = "Эндпойнт получает id типа Long и simpleMailMessage типа SimpleMailMessage"+
                    ", по полученному id находится feedbackRequest, senderEmail которого передается в полученный "+
                    "simpleMailMessage. simpleMailMessage отправляется сервисом mailService")
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

    @ApiOperation(value = "получить List объектов FeedbackRequest",
            response = FeedbackRequest.class,
            responseContainer = "List")
    @GetMapping(value = "/api/admin/feedback-request", params = "!viewed")
    public List<FeedbackRequest> getAll() {
        List<FeedbackRequest>  fr = feedbackRequestService.findAll();
        Collections.sort(fr, new Comparator<FeedbackRequest>() {
            public int compare(FeedbackRequest o1, FeedbackRequest o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return fr;
    }

    @ApiOperation(value = "Получить List объектов FeedbackRequest",
            notes = "Эндпойнт получает параметр senderEmail типа String и replied типа Boolean, "+
                    "которые соответсвуют полям senderEmail и replied объекта FeedbackRequest"+
                    " и возвращает List объектов FeedbackRequest",
            response = FeedbackRequest.class,
            responseContainer = "List")
    @GetMapping(value = "/api/admin/feedback-request/{senderEmail}/replied")
    public List<FeedbackRequest> getBySenderByReplied(@ApiParam(value = "senderEmail", required = true) @PathVariable("senderEmail") String senderEmail,
                                                      @ApiParam(value = "replied", required = true)@RequestParam Boolean replied) {
        return feedbackRequestService.getBySenderByReplied(senderEmail, replied);
    }

    @ApiOperation(value = "Получить List объектов FeedbackRequest с заданным полем viewed в текущей сессии",
            notes = "Эндпойнт получает параметр session типа HttpSession "+
                    "и viewed типа Boolean и возвращает List объектов FeedbackRequest с переданным полем viewed в текущей сессии")
    @GetMapping("/api/admin/feedback-request")
    public List<FeedbackRequest> getByReplied(@ApiParam(value = "session", required = true) HttpSession session,
                                              @ApiParam(value = "viewed", required = true)@RequestParam Boolean viewed) {
        DataEnterInAdminPanel data = (DataEnterInAdminPanel) session.getAttribute("data");
        data.setDataEnterInFeedback(Instant.now().getEpochSecond());
        dataEnterInAdminPanelService.update(data);
        session.setAttribute("data", data);
        return feedbackRequestService.getByViewed(viewed);
    }

    @ApiOperation(value = "Получить объект FeedbackRequest по id",
            notes = "Эндпойнт получает параметр id типа Long "+
                    "возвращает FeedbackRequest с этим id",
            response = FeedbackRequest.class)
    @GetMapping("/api/admin/feedback-request/{id}")
    public FeedbackRequest getById(@ApiParam(value = "id") @PathVariable Long id) {
        return feedbackRequestService.getById(id);
    }

    @ApiOperation(value = "Получить колличество FeedbackRequest, в которых поле viewed=false в текущей сессии",
            notes = "Эндпойнт получает параметр session типа HttpSession "+
                    " и возвращает количество FeedbackRequest, в которых поле viewed=false в текущей сессии")
    @GetMapping(value = "/api/admin/feedback-request-count")
    public int getFeedbackRequestCount(@ApiParam(value = "session", required = true) HttpSession session) {
        if (session.getAttribute("data") == null) {
            session.setAttribute("data", dataEnterInAdminPanelService.findById(1L));
        }
        return feedbackRequestService.getCountOfFeedBack();
    }

    @ApiOperation(value = "Получить колличество FeedbackRequest по senderEmail",
            notes = "Эндпоинт получает параметр senderEmail типа String "+
                    "соответсвующий полю senderEmail объекта FeedbackRequest",
            response = FeedbackRequest.class,
            responseContainer = "List")
    @GetMapping(value = "/api/admin/feedback-request/{senderEmail}/amount")
    public Long[] getAmountOfFeedback(@ApiParam(value = "senderEmail", required = true) @PathVariable("senderEmail") String senderEmail) {
        return feedbackRequestService.getAmountOfFeedback(senderEmail);
    }

    @ApiOperation(value = "Получить все feedbackRequest по id книги",
            notes = "Эндпоинт получает параметр id типа Long",
            response = FeedbackRequest.class,
            responseContainer = "List")
    @GetMapping("/api/admin/feedback-request/book-id/{id}")
    public List<FeedbackRequest> getFeedbackByIdBook(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return feedbackRequestService.findAllRequestByIdBook(id);
    }

    @ApiOperation(value = "Получить все непрочитанные feedbackRequest по id книги",
            notes = "Эндпоинт получает параметр id типа Long",
            response = FeedbackRequest.class,
            responseContainer = "List")
    @GetMapping("/api/admin/feedback-requests/unread/book-id/{id}")
    public List<FeedbackRequest> getUnreadFeedbacksByBookId(@ApiParam(value = "id", required = true) @PathVariable Long id) {
        return feedbackRequestService.findAllUnreadRequestsByBookId(id);
    }
}

package com.project.controller.controller;

import com.project.exceptions.NoValuePresentException;
import com.project.service.abstraction.FormLoginErrorMessageService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ErrorController extends AbstractErrorController {
    private static final String ERROR_PATH = "/error";
    private HttpSession session;
    private FormLoginErrorMessageService messageService;

    @Autowired
    public ErrorController(ErrorAttributes errorAttributes, HttpSession session, FormLoginErrorMessageService messageService) {
        super(errorAttributes);
        this.session = session;
        this.messageService = messageService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView notFound() throws IOException {
        ModelAndView view = new ModelAndView(new RedirectView("/err/not-found"));
        return view;
    }

    @ExceptionHandler(NoValuePresentException.class)
    public ModelAndView noValuePresent() throws IOException {
        ModelAndView view = new ModelAndView("user/user");
        view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnNoValuePresent());
        return view;
    }

    @GetMapping("error/book_not_found")
    public ModelAndView bookNotFound() {
        ModelAndView view = new ModelAndView();
        view.setViewName("/admin/not-found");
        return view;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ModelAndView badCredential() {
        ModelAndView view = new ModelAndView("user/user");
        view.getModelMap().addAttribute("errorMessage", messageService.getErrorMessageOnBadCredential());
        return view;
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ModelAndView serverError() throws IOException {
        ModelAndView view = new ModelAndView(new RedirectView("err/server-error"));
        return view;
    }

    //All errors witch our program will throw this path will catch
    @GetMapping(ERROR_PATH)
    public ResponseEntity<?> handleErrors(HttpServletRequest request) throws NotFoundException, BadCredentialsException, NoValuePresentException {
        HttpStatus status = getStatus(request);

        if (status.equals(HttpStatus.NOT_FOUND)) {
            throw new NotFoundException("This url could not be found");
        }

        if (status.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            // In this case we try to find reason of exception related to client authorization
            // The reason we can find in request parameter witch we put in com.project.handler.LoginFailureHandlerImpl

            String reasonOfAuthException = request.getParameter("failure");
            if (reasonOfAuthException != null) {
                if (reasonOfAuthException.equals("BadCredentialsException")) {
                    throw new BadCredentialsException("Bad credential in \"sign in\" operation");
                }
                if (reasonOfAuthException.equals("NoValuePresentException")) {
                    throw new NoValuePresentException("Can`t find UserAccount with this credential");
                }
            } else {
                throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return ResponseEntity.status(status).body(getErrorAttributes(request, false));
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}

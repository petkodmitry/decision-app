package org.inbank.petko.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.inbank.petko.dto.CreditOrder;
import org.inbank.petko.dto.DecisionDto;
import org.inbank.petko.dto.ErrorDto;
import org.inbank.petko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Main Controller for Error logic
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@RestController
@RequestMapping("/error")
@Hidden
public class CustomErrorController implements ErrorController {

    private final UserRepository userRepository;        // Just for DEMO purpose. In order to choose a User conveniently

    /**
     * Constructor with Autowired Spring beans
     * @param userRepository  Spring bean
     */
    @Autowired
    public CustomErrorController(UserRepository userRepository) {
        this.userRepository = userRepository;           // Just for DEMO purpose. In order to choose a User conveniently
    }

    /**
     * Performs error logic for API requests
     * @param request current {@link HttpServletRequest}
     * @return error info {@link ErrorDto} as JSON
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ErrorDto handleErrorJson(HttpServletRequest request) {
        return setResponseErrorMessage(new ErrorDto(), request);
    }

    /**
     * Performs error logic for HTML requests
     * @param creditOrder Model params holder, {@link CreditOrder}
     * @param request     current {@link HttpServletRequest}
     * @param model       current {@link Model}
     * @return new index page with error info
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping
    public ModelAndView handleErrorHtml(@ModelAttribute CreditOrder creditOrder, @ModelAttribute DecisionDto decision,
                                        HttpServletRequest request, Model model) {
        model.addAttribute("users", userRepository.findAll());      // Just for DEMO purpose. In order to choose a User conveniently
        decision =new DecisionDto();
        setResponseErrorMessage(decision, request);
        model.addAttribute("decision", decision);
        return new ModelAndView("index");
    }

    private ErrorDto setResponseErrorMessage(ErrorDto msgHolder, HttpServletRequest request) {
        Exception ex = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (ex != null) {
            msgHolder.setErrorMsg(ex.getMessage());
        }
        return msgHolder;
    }

}

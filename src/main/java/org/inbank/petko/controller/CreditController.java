package org.inbank.petko.controller;

import org.inbank.petko.dto.CreditOrderDto;
import org.inbank.petko.dto.DecisionDto;
import org.inbank.petko.service.CreditDecisionService;
import org.inbank.petko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Main Controller for Credit Decision logic
 * @author Dmitry Petko (mailto:petkodmitry@gmail.com)
 */
@RestController
@RequestMapping("/credit")
public class CreditController {

    private final UserService userService;        // Just for DEMO purpose. In order to choose a User conveniently
    private final CreditDecisionService decisionService;

    /**
     * Constructor with Autowired Spring beans
//     * @param userRepository  Spring bean
     * @param userService  Spring bean
     * @param decisionService Spring bean
     */
    @Autowired
    public CreditController(UserService userService, CreditDecisionService decisionService) {
        this.userService = userService;           // Just for DEMO purpose. In order to choose a User conveniently
        this.decisionService = decisionService;
    }

    /**
     * Initial Credit Decision page
     * @param creditOrder empty Model attribute, {@link CreditOrderDto}
     * @param model       current {@link Model}
     * @return new index view
     */
    @GetMapping
    public ModelAndView openStartPageHtml(@ModelAttribute @Nullable CreditOrderDto creditOrder, Model model) {
        model.addAttribute("users", userService.findAllUsers());      // Just for DEMO purpose. In order to choose a User conveniently
        return new ModelAndView("index");
    }

    /**
     * Performs credit decision logic for API requests
     * @param creditOrder input params holder, {@link CreditOrderDto}
     * @return decision info {@link DecisionDto} as JSON
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DecisionDto performCreditDecisionJson(@RequestBody @Validated CreditOrderDto creditOrder) {
        return performCreditDecision(creditOrder);
    }

    /**
     * Performs credit decision logic for HTML requests
     * @param creditOrder input params holder, {@link CreditOrderDto}
     * @param model       current {@link Model}
     * @return new index page with response info
     */
    @PostMapping
    public ModelAndView performCreditDecisionHtml(@ModelAttribute @Validated CreditOrderDto creditOrder, Model model) {
        model.addAttribute("users", userService.findAllUsers());      // Just for DEMO purpose. In order to choose a User conveniently
        DecisionDto decision = performCreditDecision(creditOrder);
        model.addAttribute("decision", decision);

        return new ModelAndView("index");
    }

    private DecisionDto performCreditDecision(CreditOrderDto creditOrder) {
        return decisionService.performCreditDecision(creditOrder);
    }
}

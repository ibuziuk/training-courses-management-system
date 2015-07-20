package org.exadel.training.controller;

import org.exadel.training.service.EmailNotifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;

@Controller
public class LoginController {
    @Autowired
    private EmailNotifierService emailNotifierService;
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, @RequestParam(value = "error", required = false) String loginError) {
        if (loginError != null) {
            model.addAttribute("loginError", true);
        }
        String[] mails = {"yaroshevich.yana@gmail.com","phantom.rvr@gmail.com","toshabely@gmail.com","alexey_hw@tut.by","shchaurouski.slava@gmail.com","gennady.trubach@mail.ru"};
        Context ctx = new Context();
        emailNotifierService.sendEmailNotification(mails, "login", ctx);
        return "login";
    }
}

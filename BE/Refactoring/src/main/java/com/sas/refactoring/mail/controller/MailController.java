package com.sas.refactoring.mail.controller;

import com.sas.refactoring.mail.service.MailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RequestMapping("/mail")
@RestController
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/sendcheck")
    public void sendCheckMail(@RequestBody List<String> data) throws Exception {
        mailService.sendCheckMail(data);
    }
}

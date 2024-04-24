package com.rs.datvexe.controller;

import com.rs.datvexe.model.MailStructure;
import com.rs.datvexe.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    MailSenderService mailSenderService;
    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure){
        mailSenderService.sendMail(mail,mailStructure);
        return "Successful send";
    }
}

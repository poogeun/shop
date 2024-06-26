package com.shop.controller;

import com.shop.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail){
        int number = emailService.sendMail(mail);

        String num = "" + number;

        return num;
    }
}

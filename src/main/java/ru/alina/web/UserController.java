package ru.alina.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping
    public String getStartPage() {
        return "index";
    }

    @PostMapping
    public String signIn(@RequestParam("userId") Integer userId) {
        SecurityUtil.setAuthUserId(userId);
        return  "redirect:/conspectus";
    }
}

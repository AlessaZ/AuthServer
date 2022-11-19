package com.auth.authserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogInController {

    @GetMapping({"/",""})
    public String logIn(){
        System.out.println("que fue sesta lento");
        return "logIn";
    }

    @PostMapping(value = "/log")
    public @ResponseBody String processData(HttpServletRequest request) {

        System.out.println(request.getRemoteAddr());
        return "redirect:/log";
    }
    
}

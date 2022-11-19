package com.auth.authserver;

import com.auth.authserver.Repository.UserRepo;
import com.auth.authserver.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class LogInController {

    @Autowired
    UserRepo userRepo;

    @GetMapping({"/",""})
    public String logIn(){
        return "logIn";
    }

    @PostMapping(value = "/log")
    public @ResponseBody String processData(@RequestParam("codigo") String codigo, @RequestParam("password") String password, HttpServletRequest request) {
        String encodedPass = DigestUtils.sha1Hex(password);
        Optional<User> optUser = Optional.ofNullable(userRepo.findUserByCodigoAndPassword(codigo, encodedPass));
        if(optUser.isPresent()){
            System.out.println(request.getRemoteAddr());
            System.out.println("Si existe");
        }else{
            System.out.println("No existe");
        }

        return "redirect:/log";
    }
    
}

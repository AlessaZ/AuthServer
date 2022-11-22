package com.auth.authserver;

import com.auth.authserver.Repository.IptableRepo;
import com.auth.authserver.Repository.UserRepo;
import com.auth.authserver.entity.Iptable;
import com.auth.authserver.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LogInController {

    private static int MINUTOS_PERMISO = 30; //Cambiar luego a 30

    @Autowired
    UserRepo userRepo;
    @Autowired
    IptableRepo iptableRepo;

    @GetMapping({"/",""})
    public String logIn(){
        return "logIn";
    }

    @PostMapping(value = "/log")
    public @ResponseBody String processData(@RequestParam("codigo") String codigo, @RequestParam("password") String password, HttpServletRequest request, HttpSession session) {
        String encodedPass = DigestUtils.sha1Hex(password);
        Optional<User> optUser = Optional.ofNullable(userRepo.findUserByCodigoAndPassword(codigo, encodedPass));
        if(optUser.isPresent()){
            User user = optUser.get();
            if (iptableRepo.findByUidAndIp(user.getUid(), request.getRemoteAddr()).isEmpty()){
                Iptable iptable = new Iptable();
                iptable.setIp(request.getRemoteAddr());
                iptable.setUid(optUser.get().getUid());
                iptable = iptableRepo.saveAndFlush(iptable);
                iptableRepo.crearEvento(iptable.getId(), MINUTOS_PERMISO);
            }
            System.out.println(userRepo.isUserDTI(optUser.get().getUid()));
        }else{
            System.out.println("No existe");
        }
        return "redirect:/log";
    }
    
}

package com.auth.authserver;

import com.auth.authserver.Repository.IptableRepo;
import com.auth.authserver.Repository.UserRepo;
import com.auth.authserver.entity.Iptable;
import com.auth.authserver.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class LogInController {

    private static int MINUTOS_PERMISO = 10; //Cambiar luego a 30

    @Autowired
    UserRepo userRepo;
    @Autowired
    IptableRepo iptableRepo;

    @GetMapping({"/",""})
    public String logIn(Model model, HttpSession session){
        if (session.getAttribute("uid") != null){
            LocalDateTime localDateTime = (LocalDateTime) session.getAttribute("endTime");
            if (localDateTime.isAfter(LocalDateTime.now())){
                Duration d = Duration.between(LocalDateTime.now(), localDateTime);
                String strRemaining = String.format("%02d:%02d%n", d.toMinutes(), d.minusMinutes(d.toMinutes()).getSeconds());
                model.addAttribute("remainingTime", strRemaining);
                return "message";
            }else{
                session.removeAttribute("uid");
                session.removeAttribute("nombre");
                session.removeAttribute("endTime");
                session.removeAttribute("isDTI");
            }
        }
        return "logIn";
    }

    @PostMapping(value = "/log")
    public String processData(@RequestParam("codigo") String codigo, @RequestParam("password") String password,
                                            HttpServletRequest request, HttpSession session, RedirectAttributes attr, Model model) {

        if (session.getAttribute("uid") != null){
            LocalDateTime localDateTime = (LocalDateTime) session.getAttribute("endTime");
            if (localDateTime.isAfter(LocalDateTime.now())){
                Duration d = Duration.between(LocalDateTime.now(), localDateTime);
                String strRemaining = String.format("%02d:%02d%n", d.toMinutes(), d.minusMinutes(d.toMinutes()).getSeconds());
                model.addAttribute("remainingTime", strRemaining);
                return "message";
            }else{
                session.removeAttribute("uid");
                session.removeAttribute("nombre");
                session.removeAttribute("endTime");
                session.removeAttribute("isDTI");
                return "logIn";
            }
        }

        String encodedPass = DigestUtils.sha1Hex(password);
        Optional<User> optUser = Optional.ofNullable(userRepo.findUserByCodigoAndPassword(codigo, encodedPass));
        if(optUser.isEmpty()){
            attr.addFlashAttribute("alert", "Datos incorrectos");
            return "redirect:/";
        }
        User user = optUser.get();
        session.setAttribute("uid", user.getUid());
        session.setAttribute("nombre", user.getNombre());
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(MINUTOS_PERMISO);
        session.setAttribute("endTime", localDateTime);
        if (iptableRepo.findByUidAndIp(user.getUid(), request.getRemoteAddr()).isEmpty()){
            Iptable iptable = new Iptable();
            iptable.setIp(request.getRemoteAddr());
            iptable.setUid(optUser.get().getUid());
            iptable = iptableRepo.saveAndFlush(iptable);
            iptableRepo.crearEvento(iptable.getId(), MINUTOS_PERMISO);
        }
        boolean isDTI = userRepo.isUserDTI(optUser.get().getUid());
        session.setAttribute("isDTI", isDTI);
        Duration d = Duration.between(LocalDateTime.now(), localDateTime);
        String strRemaining = String.format("%02d:%02d%n", d.toMinutes(), d.minusMinutes(d.toMinutes()).getSeconds());
        model.addAttribute("remainingTime", strRemaining);
        return "message";
    }
    
}

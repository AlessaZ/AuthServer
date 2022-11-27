package com.auth.authserver;

import com.auth.authserver.Repository.ResourceRepo;
import com.auth.authserver.entity.Resource;
import com.auth.authserver.entity.Rol;
import com.auth.authserver.Repository.RolRepo;
import com.auth.authserver.Repository.UserRepo;
import com.auth.authserver.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/rol")
public class RolController {

    @Autowired
    RolRepo rolRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ResourceRepo resourceRepo;

    @GetMapping({"/","","list"})
    public String listar(Model model, HttpSession session){
        if (session.getAttribute("uid") != null){
            LocalDateTime localDateTime = (LocalDateTime) session.getAttribute("endTime");
            if (localDateTime.isBefore(LocalDateTime.now())){
                session.removeAttribute("uid");
                session.removeAttribute("nombre");
                session.removeAttribute("endTime");
                session.removeAttribute("isDTI");
                return "redirect:/";
            } else if (!((boolean) session.getAttribute("isDTI"))) {
                return "redirect:/";
            }
        }else{
            return "redirect:/";
        }
        List<Rol> rolList = rolRepo.findAll();
        model.addAttribute("rolList", rolList);
        return "rol/list";
    }

    @GetMapping("/edit")
    public String getEdit(@RequestParam(required=false,name="id") String strId, Model model, HttpSession session){
        if (session.getAttribute("uid") != null){
            LocalDateTime localDateTime = (LocalDateTime) session.getAttribute("endTime");
            if (localDateTime.isBefore(LocalDateTime.now())){
                session.removeAttribute("uid");
                session.removeAttribute("nombre");
                session.removeAttribute("endTime");
                session.removeAttribute("isDTI");
                return "redirect:/";
            } else if (!((boolean) session.getAttribute("isDTI"))) {
                return "redirect:/";
            }
        }else{
            return "redirect:/";
        }

        int id=0;
        try{
            id= Integer.parseInt(strId);
        } catch (Exception e){
            return "redirect:/rol";
        }
        Optional<Rol> optionalRol = rolRepo.findById(id);
        if(optionalRol.isEmpty()){return "redirect:/rol";}
        if(optionalRol.get().getNombrerol().equals("Comunidad PUCP")) return "redirect:/rol";

        List<Resource> resourceList = resourceRepo.findAll();
        List<User> userList = userRepo.findAll();
        model.addAttribute("userList", userList);
        model.addAttribute("resourceList", resourceList);
        model.addAttribute("role", optionalRol.get());
        return "rol/form";
    }

    @PostMapping("/edit")
    public String postEdit(Model model, @ModelAttribute("rol") Rol rol, HttpSession session, RedirectAttributes attr){
        if (session.getAttribute("uid") != null){
            LocalDateTime localDateTime = (LocalDateTime) session.getAttribute("endTime");
            if (localDateTime.isBefore(LocalDateTime.now())){
                session.removeAttribute("uid");
                session.removeAttribute("nombre");
                session.removeAttribute("endTime");
                session.removeAttribute("isDTI");
                return "redirect:/";
            } else if (!((boolean) session.getAttribute("isDTI"))) {
                return "redirect:/";
            }
        }else{
            return "redirect:/";
        }

        Optional<Rol> optionalRol = rolRepo.findById(rol.getId());
        if(optionalRol.isEmpty()){return "redirect:/rol";}
        if(optionalRol.get().getNombrerol().equals("Comunidad PUCP")) return "redirect:/rol";

        if(rol.getNombrerol().trim().isEmpty()){
            attr.addFlashAttribute("alert", "El rol no puede estar vacío");
            return "redirect:/rol/edit?id="+rol.getId();
        }

        rolRepo.save(rol);
        return "redirect:/rol";
    }

}

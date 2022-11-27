package com.auth.authserver;

import com.auth.authserver.Repository.ResourceRepo;
import com.auth.authserver.Repository.RolRepo;
import com.auth.authserver.Repository.UserRepo;
import com.auth.authserver.entity.Resource;
import com.auth.authserver.entity.Rol;
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
import java.util.regex.Pattern;


@Controller
@RequestMapping("/resource")
public class ResourceController {

    String zeroTo255
            = "(\\d{1,2}|(0|1)\\"
            + "d{2}|2[0-4]\\d|25[0-5])";

    String IPV4_PATTERN
            = zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255;


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
        List<Resource> resourceList = resourceRepo.findAll();
        model.addAttribute("resourceList", resourceList);
        return "resource/list";
    }

    @GetMapping("/new")
    public String getNew(HttpSession session, Model model, @ModelAttribute("resource") Resource resource){
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
        return "resource/form";
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
            return "redirect:/resource";
        }
        Optional<Resource> optionalResource = resourceRepo.findById(id);
        if(optionalResource.isEmpty()){return "redirect:/resource";}

        model.addAttribute("resource", optionalResource.get());
        return "resource/form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required=false,name="id") String strId, Model model, HttpSession session){
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
            return "redirect:/resource";
        }
        Optional<Resource> optionalResource = resourceRepo.findById(id);
        if(optionalResource.isEmpty()){return "redirect:/resource";}

        resourceRepo.deleteAllRhById(id);
        resourceRepo.deleteById(id);
        return "redirect:/resource";
    }

    @PostMapping("/save")
    public String save(Model model, @ModelAttribute("resource") Resource resource, HttpSession session, RedirectAttributes attr){
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

        String redirect = "edit?id="+resource.getId();

        if(resource.getId()==0){
            redirect = "new";
        }

        if(resource.getAlias().isEmpty()){
            attr.addFlashAttribute("alert", "El nombre del recurso no puede estar vacío");

            return "redirect:/resource/"+redirect;
        }
        System.out.println(resource.getIp());
        if(!Pattern.matches(IPV4_PATTERN, resource.getIp())){

            attr.addFlashAttribute("alert", "Ingresa una IP válida");
            return "redirect:/resource/"+redirect;
        }

        resourceRepo.save(resource);
        return "redirect:/resource";
    }

}

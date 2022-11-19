import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogInController {

    @GetMapping("/log")
    public String logIn(){
        return "logIn";
    }

    @PostMapping(value = "/log")
    public @ResponseBody String processData(@RequestParam("workflow") final String workflow,
                                                     @RequestParam("conf") final String value, @RequestParam("dc") final String dc, HttpServletRequest request) {

        System.out.println(workflow);
        System.out.println(value);
        System.out.println(dc);
        System.out.println(request.getRemoteAddr());
        return "redirect:/log";
    }
    
}

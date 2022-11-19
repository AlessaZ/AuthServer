import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogInController {

    @GetMapping
    public String logIn(){
        return "logIn";
    }

    @PostMapping
    public String logInPost(){
        return "logIn";
    }
    
}

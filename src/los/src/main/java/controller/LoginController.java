package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String index() 
    {
        return "Login";
    }

    @RequestMapping("/signin")
    public String signin() 
    {
        return "Professor";
    }
    
    @RequestMapping("/signout")
    public String signout() 
    {
        return "Home";
    }
}

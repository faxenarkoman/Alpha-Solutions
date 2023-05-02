package kea.dk.alpha_solutions.alphaController;

import jakarta.servlet.http.HttpSession;
import kea.dk.alpha_solutions.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AlphaController
{
    @GetMapping(value ="/")
    public String login()
    {
        return "login.html";
    }


    @PostMapping(value = "/check-login")
    public String checkLogin(@RequestParam("username") String mail, @RequestParam("password") String password, HttpServletRequest request)
    {
        User tmpUser = new User(mail,password);
        HttpSession session;
        if(tmp.checkUser(mail,password))
        {
            session = request.getSession();
            session.setAttribute("username", tmpUser.getMail());
            session.setAttribute("password", tmpUser.getPassword());
            return "redirect:/index";
        }
        else
        {
            return "redirect:/";
        }
    }
}
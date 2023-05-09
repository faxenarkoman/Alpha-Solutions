package kea.dk.alpha_solutions.alphaController;

import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Controller
public class AlphaController
{
    @GetMapping(value ="/")
    public String login()
    {
        return "login";
    }


    @PostMapping("/login")
    public String doLogin(@RequestParam("email") String email, HttpSession session,
                          @RequestParam("password") String password){
        // Kryptering af password vha. hashing via BCrypt
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        //set attributer i session
        session.setAttribute("email", email);
        // Store hashed password in session rather than password
        session.setAttribute("password", hashedPassword);
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}

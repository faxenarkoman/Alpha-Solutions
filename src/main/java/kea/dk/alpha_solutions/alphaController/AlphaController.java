package kea.dk.alpha_solutions.alphaController;

import jakarta.servlet.http.HttpSession;
import kea.dk.alpha_solutions.alphaRepository.AlphaRepositoryUser;
import kea.dk.alpha_solutions.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "login";
    }

    @Autowired
    private AlphaRepositoryUser alphaRepositoryUser;
    @PostMapping("/login")
    public String doLogin(@RequestParam("email") String email, HttpSession session,
                          @RequestParam("password") String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println("Email: " + email);
        System.out.println("Plain password: " + password);
        System.out.println("Hashed password: " + hashedPassword);

        User user = alphaRepositoryUser.getUserByEmail(email);

        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            // If user exists and password matches, set attributes in session
            session.setAttribute("email", email);
            session.setAttribute("password", user.getPassword()); // Store hashed password in session
            return "redirect:/index";
        } else {
            // If user does not exist or password does not match, return to login page with error message
            return "login?error";
        }
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}

package kea.dk.alpha_solutions.alphaController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import kea.dk.alpha_solutions.alphaRepository.AlphaRepository;

import kea.dk.alpha_solutions.alphaRepository.AlphaRepositoryProject;
import kea.dk.alpha_solutions.model.Project;
import kea.dk.alpha_solutions.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AlphaController
{

    private AlphaRepositoryProject alphaRepositoryProject = new AlphaRepositoryProject();


    @GetMapping(value = "/")
    public String login()
    {
        return "login";
    }


    @PostMapping("/login")
    public String doLogin(@RequestParam("email") String email, HttpSession session,
                          @RequestParam("password") String password)
    {
        //set username attribut i session
        session.setAttribute("email", email);
        session.setAttribute("password", password);
        return "redirect:/index";

        //Kryptering af password vha. hashing

        //String password = "Almindelig kode"
        //String bCryptPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        //bCryptPassword = "Hashet kode" - Denne gemmes i DB
        // Når du skal tjekke
        // Checker for krypteret kode sammenligning
        // if (BCrypt.checkpw(password, loggedUser.getPassword())){
        // Sæt session osv osv }
        //import denne
        // import org.springframework.security.crypto.bcrypt.BCrypt;

        //tilføj til POM
        //<dependency> <groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-security</artifactId> </dependency>
    }

    @GetMapping("/index")
    public String createProjectList(Model model)
    {
        model.addAttribute("alphas", alphaRepositoryProject.getAll());
        return "projectList";
    }

    @PostMapping("/create")
    public String createProduct(
            @RequestParam("project-UserID") int newUserID,
            @RequestParam("project-ProjectID") int newProjectID,
            @RequestParam("project-")

    }

    public AlphaRepositoryProject getAlphaRepositoryProject()
    {
        return alphaRepositoryProject;
    }
}
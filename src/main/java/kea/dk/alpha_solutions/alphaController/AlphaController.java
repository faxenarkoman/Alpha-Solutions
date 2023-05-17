package kea.dk.alpha_solutions.alphaController;

import kea.dk.alpha_solutions.alphaRepository.AlphaRepositoryProject;
import kea.dk.alpha_solutions.model.Project;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import kea.dk.alpha_solutions.alphaRepository.AlphaRepositoryUser;
import kea.dk.alpha_solutions.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AlphaController
{
    private final AlphaRepositoryProject alphaRepositoryProject;
    private final AlphaRepositoryUser alphaRepositoryUser;
    @Autowired
    public AlphaController(AlphaRepositoryProject alphaRepositoryProject, AlphaRepositoryUser alphaRepositoryUser) {
        this.alphaRepositoryProject = alphaRepositoryProject;
        this.alphaRepositoryUser = alphaRepositoryUser;
    }


    @GetMapping(value ="/")
    public String login()
    {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("email") String email, HttpSession session,
                          @RequestParam("password") String password, Model model) {
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
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/index")
    public String showProjectList(Model model, HttpSession session)
    {
/*        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }*/
        model.addAttribute("alpha", alphaRepositoryProject.getAll());
        return "index";
    }
    @GetMapping("/create")
    public String createProject(Model model, HttpSession session)
    {
/*        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }*/
        List<User>userList = alphaRepositoryUser.getAll();
        model.addAttribute("userList", userList);
        return "create";
    }

    @PostMapping("/create")
    public String createProduct(Model model,
            @RequestParam("project-title") String projectTitle,
            @RequestParam("project-description") String projectDescription,
            @RequestParam("deadline") String deadline,
            @RequestParam("nr-of-users") List<Integer> userId,
            @RequestParam("nr-of-hours") int nrOfHours,
            @RequestParam("project-price") double projectPrice,
            @RequestParam("hours-per-day") int hoursPerDay) {

        Project newProject = new Project();
        newProject.setProjectTitle(projectTitle);
        newProject.setProjectDescription(projectDescription);
        newProject.setDeadline(deadline);
        newProject.setNrOfUsers(userId.size());
        newProject.setNrOfHours(nrOfHours);
        newProject.setProjectPrice(projectPrice);
        newProject.setHoursPerDay(hoursPerDay);

        alphaRepositoryProject.addProject(newProject);
        model.addAttribute("userList", alphaRepositoryUser.getAll());

        return "redirect:/index";
    }

    @GetMapping("/project")
    public String showProject(Model model, HttpSession session)
    {
        //if (session.getAttribute("email") == null) {
        //    return "redirect:/";
        //}
        model.addAttribute("alpha", alphaRepositoryProject.getAll());

        return "project";
    }


}

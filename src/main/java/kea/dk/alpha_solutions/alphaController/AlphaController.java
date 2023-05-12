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

import java.util.ArrayList;

@Controller
public class AlphaController
{

    public AlphaController(AlphaRepositoryProject alphaRepositoryProject) {
        this.alphaRepositoryProject = alphaRepositoryProject;
    }

    AlphaRepositoryProject alphaRepositoryProject = new AlphaRepositoryProject();


    @GetMapping(value ="/")
    public String login()
    {
        return "login";
    }

    @Autowired
    private AlphaRepositoryUser alphaRepositoryUser;
    @PostMapping("/login")
    public String doLogin(@RequestParam("email") String email, HttpSession session,
                          @RequestParam("password") String password, Model model) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

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
        /*// Check if user is logged in
        if(session.getAttribute("email") == null || session.getAttribute("password") == null)
        {
            // Redirect to login page if not logged in
            return "login";
        }*/
        model.addAttribute("alpha", alphaRepositoryProject.getAll());
        return "index";

    }
@GetMapping("/create")
public String createProject(HttpSession session, Model model){
/*    // Check if user is logged in
    if(session.getAttribute("email") == null || session.getAttribute("password") == null)
    {
        // Redirect to login page if not logged in
        return "login";
    }*/
    model.addAttribute("name", alphaRepositoryUser.getName());

        return "create";
}

    @PostMapping("/create")
    public String createProduct(
            @RequestParam("ProjectTitle") String newProjectTitle,
            @RequestParam("ProjectDescription") String newDescription,
            @RequestParam("Deadline") int newNrOfHours,
            @RequestParam("NrOfUsers") int newNrOfUsers,
            @RequestParam("ProjectPrice") int newProjectPrice,
            @RequestParam("HoursPrDay") int newHoursPrDay)
    {

        Project newProject = new Project();
        newProject.setProjectTitle(newProjectTitle);
        newProject.setProjectDescription(newDescription);
        newProject.setNrOfHours(newNrOfHours);
        newProject.setNrOfHours(newNrOfUsers);
        newProject.setProjectPrice(newProjectPrice);
        newProject.setHoursPerDay(newHoursPrDay);

        //gem nyt produkt
        AlphaRepositoryProject alphaRepositoryProject = new AlphaRepositoryProject();
        alphaRepositoryProject.addProject(newProject);

        //tilbage til index
        return "redirect:/index";
    }
}

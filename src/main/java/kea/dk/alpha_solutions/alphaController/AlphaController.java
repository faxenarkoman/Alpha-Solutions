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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showProjectList(Model model)
    {
        model.addAttribute("alpha", alphaRepositoryProject.getAll());
        return "index";

    }



    @PostMapping("/create")
    public String createProduct(
            @RequestParam("project-UserID") int newUserID,
            @RequestParam("project-ProjectID") int newProjectID,
            @RequestParam("project-ProjectTitle") String newProjectTitle,
            @RequestParam("project-ProjectDescription") String newDescription,
            @RequestParam("project-Deadline") int newNrOfHours,
            @RequestParam("project-NrOfUsers") int newNrOfUsers,
            @RequestParam("project-ProjectPrice") int newProjectPrice,
            @RequestParam("project-HoursPrDay") int newHoursPrDay)
    {

        Project newProject = new Project();

        newProject.setUserID(newUserID);
        newProject.setProjectID(newProjectID);
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

    @GetMapping("/open/{projectID}")
    public String openProject(@PathVariable("projectID") int projectID, Model model) {
        // Retrieve the project data based on the projectID
        Project project = alphaRepositoryProject.getProjectByID(projectID);

        // Add the project data to the model
        model.addAttribute("project", project);

        // Return the name of the HTML template to render
        return "project-details";
    }

    @GetMapping("/project")
    public String showProjec(Model model)
    {
        model.addAttribute("alpha", alphaRepositoryProject.getAll());
        return "project";

    }


}

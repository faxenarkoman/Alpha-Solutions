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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }
        model.addAttribute("alpha", alphaRepositoryProject.getAll());
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);
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
    public String createProject(Model model,
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

    @GetMapping("/project/{projectID}")
    public String openProject(@PathVariable("projectID") int projectID, Model model) {
        // Retrieve the project data based on the projectID
        Project project = alphaRepositoryProject.getProjectByID(projectID);

        // Add project data to model
        model.addAttribute("project", project);

        // Return the name of the HTML template to render
        return "project";
    }

    @GetMapping("/createUser")
    public String createUser(Model model, HttpSession session)
    {
        //if (session.getAttribute("email") == null) {
        //    return "redirect:/";
        //}
        return "createUser";
    }
    @PostMapping("/createUser")
    public String createUser(Model model,
                @RequestParam("email") String email,
                @RequestParam("password") String password,
                @RequestParam("hourlyWage") int hourlyWage,
                @RequestParam("name") String name)
                 {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User();
        newUser.setMail(email);
        newUser.setPassword(hashedPassword);
        newUser.setHourlyWage(hourlyWage);
        newUser.setName(name);


        alphaRepositoryUser.addUser(newUser);
        model.addAttribute("userList", alphaRepositoryUser.getAll());

        return "redirect:/index";
    }


    @GetMapping("/delete/{projectID}")
    public String deleteProject(@PathVariable int projectID)
    {
        alphaRepositoryProject.deleteByProjectID(projectID);

        return "redirect:/index";
    }

    @PostMapping("/update/{projectID}")
    public String updateProject(@RequestParam("projectTitle") String projectTitle,
                                @RequestParam("projectDescription") String projectDescription,
                                @RequestParam("deadline") String deadline,
                                @RequestParam("nrOfUsers") int nrOfUsers,
                                @RequestParam("nrOfHours") int nrOfHours,
                                @RequestParam("HoursPerDay") int hoursPerDay,
                                @PathVariable("projectID") int projectID)
    {
        //Project updateProject = new Project(projectTitle, projectDescription, deadline, nrOfUsers, nrOfHours, hoursPerDay, projectID);
        //alphaRepositoryProject.updateProject(updateProject);

        return "redirect:/index";
    }
}

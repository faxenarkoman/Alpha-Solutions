package kea.dk.alpha_solutions.alphaController;

import kea.dk.alpha_solutions.alphaRepository.AlphaRepositoryProject;
import kea.dk.alpha_solutions.alphaRepository.AlphaRepositoryTask;
import kea.dk.alpha_solutions.model.Project;
import kea.dk.alpha_solutions.model.Task;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import kea.dk.alpha_solutions.alphaRepository.AlphaRepositoryUser;
import kea.dk.alpha_solutions.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class AlphaController
{
    private final AlphaRepositoryProject alphaRepositoryProject;
    private final AlphaRepositoryUser alphaRepositoryUser;

    private final AlphaRepositoryTask alphaRepositoryTask;

    @Autowired
    public AlphaController(AlphaRepositoryProject alphaRepositoryProject, AlphaRepositoryUser alphaRepositoryUser, AlphaRepositoryTask alphaRepositoryTask) {
        this.alphaRepositoryProject = alphaRepositoryProject;
        this.alphaRepositoryUser = alphaRepositoryUser;
        this.alphaRepositoryTask = alphaRepositoryTask;
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

    @GetMapping("showTask")


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

    @GetMapping("/projects/{projectId}/tasks")
    public Set<Task> getTasksForProject(@PathVariable int projectId) {

        // Retrieve the project with the given projectId from the database
       Project project = alphaRepositoryProject.getProjectByID(projectId);

        if (project != null) {
            // Return the tasks associated with the project
            return project.getTasks();
        } else {
            // Handle case when project is not found
            return Collections.emptySet();
        }
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
}

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
import java.util.List;
import java.util.stream.Collectors;

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
        // Retrieve user by email from the repository
        User user = alphaRepositoryUser.getUserByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            // If user exists and password matches, set attributes in session
            session.setAttribute("email", email);
            // Store hashed password in session
            session.setAttribute("password", user.getPassword());
            return "redirect:/index";
        } else {
            // If user does not exist or match, return to login page with error message
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/index")
    public String showProjectList(Model model, HttpSession session)
    {
       /* if (session.getAttribute("email") == null) {
            return "redirect:/";
        }*/
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
        model.addAttribute("userList", alphaRepositoryUser.getAll());
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
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);
        return "createUser";
    }
    @PostMapping("/createUser")
    public String createUser(Model model, HttpSession session,
                @RequestParam("email") String email,
                @RequestParam("password") String password,
                @RequestParam("hourlyWage") int hourlyWage,
                @RequestParam("name") String name,
                @RequestParam(value = "admin", defaultValue = "false") boolean admin)
                 {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User();
        newUser.setMail(email);
        newUser.setPassword(hashedPassword);
        newUser.setHourlyWage(hourlyWage);
        newUser.setName(name);
        newUser.setAdmin(admin);


        alphaRepositoryUser.addUser(newUser);
        model.addAttribute("userList", alphaRepositoryUser.getAll());
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);

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
    @GetMapping("/adminPanel")
    public String admin(Model model, HttpSession session)
    {
        //if (session.getAttribute("email") == null) {
        //    return "redirect:/";
        //}
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);
        return "adminPanel";
    }
    @GetMapping("/deleteUser")
    public String deleteUser(Model model, HttpSession session)
    {
        //if (session.getAttribute("email") == null) {
        //    return "redirect:/";
        //}
        model.addAttribute("userList", alphaRepositoryUser.getAll());
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin",isAdmin);
        return "deleteUser";
    }
    @PostMapping("/deleteUser")
    public String deleteUser(Model model, HttpSession session, @RequestParam("email") String email)
    {
        alphaRepositoryUser.deleteById(email);
        return "redirect:/index";
    }
    @GetMapping("/updateUser")
    public String updateUser(Model model, HttpSession session) {
        List<User> userList = alphaRepositoryUser.getAll();
        model.addAttribute("userList", userList);
        model.addAttribute("user", new User());

        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin",isAdmin);


        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(Model model,
                             @RequestParam("userId") int userId,
                             @RequestParam("email") String mail,
                             @RequestParam("password") String password,
                             @RequestParam("hourlyWage") int hourlyWage,
                             @RequestParam("name") String name,
                             @RequestParam(value = "admin", required = false) boolean admin)
    {

        User updateUser = new User(userId, mail, password, hourlyWage, name, admin);
        alphaRepositoryUser.updateUser(updateUser);
        System.out.println(updateUser);

            return "adminPanel";

    }
}

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

import java.util.List;

@Controller
public class AlphaController
{
    private final AlphaRepositoryProject alphaRepositoryProject;
    private final AlphaRepositoryUser alphaRepositoryUser;

    private final AlphaRepositoryTask alphaRepositoryTask;

    @Autowired
    public AlphaController(AlphaRepositoryProject alphaRepositoryProject, AlphaRepositoryUser alphaRepositoryUser, AlphaRepositoryTask alphaRepositoryTask)
    {
        this.alphaRepositoryProject = alphaRepositoryProject;
        this.alphaRepositoryUser = alphaRepositoryUser;
        this.alphaRepositoryTask = alphaRepositoryTask;
    }


    @GetMapping(value = "/")
    public String login()
    {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("email") String email, HttpSession session,
                          @RequestParam("password") String password, Model model)
    {
        // Retrieve user by email from the repository
        User user = alphaRepositoryUser.getUserByEmail(email);

        if (user != null)
        {
            if (user.getPassword() != null && BCrypt.checkpw(password, user.getPassword()))
            {
                // If password matches, set attributes in session
                session.setAttribute("email", email);
                // Store hashed password in session
                session.setAttribute("password", user.getPassword());
                return "redirect:/index";
            }
            else
            {
                // If password does not match, return to login page with error message
                model.addAttribute("error", "Invalid password");
                return "login";
            }
        }
    return "login";
    }



    @GetMapping("/index")
    public String showProjectList(Model model, HttpSession session)
    {
        //Check if user is logged in
       if (session.getAttribute("email") == null) {
            return "redirect:/";
        }
        model.addAttribute("project", alphaRepositoryProject.getAll());
        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);



        return "index";
    }

    @GetMapping("/create")
    public String createProject(Model model, HttpSession session)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }
        model.addAttribute("userList", alphaRepositoryUser.getAll());
        //check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);
        return "create";
    }

    @PostMapping("/create")
    public String createProject(Model model,
                                @RequestParam("project-title") String projectTitle,
                                @RequestParam("project-description") String projectDescription,
                                @RequestParam("deadline") String deadline,
                                @RequestParam("hours-per-day") int hoursPerDay )
    {

        Project newProject = new Project();
        newProject.setProjectTitle(projectTitle);
        newProject.setProjectDescription(projectDescription);
        newProject.setDeadline(deadline);
        newProject.setHoursPerDay(hoursPerDay);
        alphaRepositoryProject.addProject(newProject);

        return "redirect:/index";
    }

    @GetMapping("/project")
    public String showProject(Model model, HttpSession session)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }

        model.addAttribute("alpha", alphaRepositoryProject.getAll());
        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);

        return "project";
    }

    @GetMapping("/project/{projectID}")
    public String openProject(@PathVariable("projectID") int projectID, Model model, HttpSession session)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }

        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);

        // Retrieve the project data based on the projectID
        Project project = alphaRepositoryProject.getProjectByID(projectID);
        List<Task> tasks = alphaRepositoryTask.getTasksByProjectID(projectID);

        //Calculate total cost of project
        int totalCost = 0;
        for (Task task : tasks){
            totalCost = + totalCost + task.getTaskNrOfHours();
        }
        totalCost = totalCost * 150; // 150 is the hourly rate

        //Calculate percentage of completed tasks
        
        int totalTasks = tasks.size();

        System.out.println(tasks.get(0).getCompleted());
        int completedTasks = 0;


        for (Task task : tasks) {
            if (task.getCompleted()) {
                completedTasks++;
            }
        }

        int percentageCompleted = (int) ((completedTasks / (double) totalTasks) * 100);


        // Add project data to model
        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        model.addAttribute("percentageCompleted", percentageCompleted);


        model.addAttribute("totalCost", totalCost);

        // Return the name of the HTML template to render
        return "project";
    }

    @GetMapping("/task/{projectID}")
    public String showCreateTask(@PathVariable("projectID") int projectID, Model model, HttpSession session) {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }
        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);

        // Retrieve the project by ID
        Project project = alphaRepositoryProject.getProjectByID(projectID);
        List<User> userList = alphaRepositoryUser.getAll();
        List<Task> tasks = alphaRepositoryTask.getTasksByProjectID(projectID);
        model.addAttribute("userList", userList);

        // Create a new Task object
        Task task = new Task();

        // Pass the project and task objects to the view
        model.addAttribute("project", project);
        model.addAttribute("task", task);
        model.addAttribute("projectID", projectID); // Add this line to set the projectID as a model attribute

        // Return the name of the view template
        return "task";
    }


    @PostMapping("/task/{projectID}/createTask")
    public String createTask(@PathVariable("projectID") int projectID, @ModelAttribute("task") Task task) {
        // Retrieve the project by ID
        Project project = alphaRepositoryProject.getProjectByID(projectID);

        // Set the project ID for the task
        task.setProjectId(projectID);

        // Create the task in the project
        Task createdTask = alphaRepositoryTask.createTaskInProject(project, task);

        if (createdTask != null) {
            // Task creation successful
            // Redirect to the project details page
            return "redirect:/project/" + projectID;
        } else {
            // Task creation failed
            // Handle the error or redirect to an error page
            return "redirect:/error";
        }
    }

    @PostMapping("/task/{taskID}/delete")
    public String deleteTask(@PathVariable("taskID") int taskID, @RequestParam("projectID") int projectID, HttpSession session, Model model)
    {
        // Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);

        List<Task> tasks = alphaRepositoryTask.getTasksByProjectID(projectID);
        model.addAttribute("tasks", tasks);

        alphaRepositoryTask.deleteByTaskID(taskID);

        return "redirect:/project/" + projectID;

    }
    @GetMapping("/editTask/{taskID}")
    public String showTask(@PathVariable("taskID") int taskID, Model model, HttpSession session) {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }
        Task task = alphaRepositoryTask.getTaskByID(taskID);
        model.addAttribute("task", task);
        return "taskUpdate";
    }

    @PostMapping("/editTask/{taskId}")
    public String editTask(@PathVariable("taskId") int taskID, Model model, @ModelAttribute("task") Task updatedTask) {
        alphaRepositoryTask.updateTask(taskID, updatedTask);
        Task task = alphaRepositoryTask.getTaskByID(taskID);
        model.addAttribute("task", task);
        return "redirect:/project/" + task.getProjectId();
    }


    @PostMapping("/taskDone/{taskId}")
    public String taskDone(@PathVariable("taskId") int taskId) {
        Task task = alphaRepositoryTask.getTaskByID(taskId);
        task.setCompleted(true);
        alphaRepositoryTask.updateTask(taskId, task);
        return "redirect:/project/" + task.getProjectId();

    }



    @GetMapping("/createUser")
    public String createUser(Model model, HttpSession session)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }
        //check if user is admin
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
    public String deleteProject(@PathVariable int projectID, HttpSession session, Model model)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }

        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);

        alphaRepositoryProject.deleteByProjectID(projectID);

        return "redirect:/index";
    }


    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") int updateId, Model model, HttpSession session)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }

        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);
        // find produkt med id=updateId i databasen
        Project updateProject = alphaRepositoryProject.getProjectByID(updateId);

        // tilføj produkt til viewmodel, så det kan bruges i Thymeleaf
        model.addAttribute("project", updateProject);
        // fortæl Spring hvilken HTML side, der skal vises
        return "update";
    }

    @PostMapping("/update/{projectID}")
    public String updateProjectById(@PathVariable("projectID") int projectID,
                                    @RequestParam("project-title") String projectTitle,
                                    @RequestParam("project-description") String projectDescription,
                                    @RequestParam("deadline") String deadline,
                                    @RequestParam("hours-per-day") int hoursPerDay)

    {
        Project updateProject = new Project(projectID, projectTitle, deadline, hoursPerDay, projectDescription);
        alphaRepositoryProject.updateProject(updateProject);

        return "redirect:/index";
    }


    @GetMapping("/adminPanel")
    public String admin(Model model, HttpSession session)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }
        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);
        return "adminPanel";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(Model model, HttpSession session)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }
        model.addAttribute("userList", alphaRepositoryUser.getAll());
        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);
        return "deleteUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(Model model, HttpSession session,
                             @RequestParam("email") String email)
    {
        alphaRepositoryUser.deleteByEmail(email);
        return "redirect:/index";
    }


    @GetMapping("/updateUser")

    public String updateUser(Model model, HttpSession session)
    {
        //Check if user is logged in
        if (session.getAttribute("email") == null) {
            return "redirect:/";
        }

        List<User> userList = alphaRepositoryUser.getAll();
        model.addAttribute("userList", userList);
        model.addAttribute("user", new User());

        //Check if user is admin
        String email = (String) session.getAttribute("email");
        boolean isAdmin = alphaRepositoryUser.isAdmin(email);
        model.addAttribute("isAdmin", isAdmin);


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

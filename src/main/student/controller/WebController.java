@Controller
public class WebController {
  @Autowired private StudentService svc;

  @GetMapping("/") public String home(Model m){
    m.addAttribute("students", svc.listAll());
    return "index";
  }

  @GetMapping("/students/new") public String addForm(Model m){
    m.addAttribute("student", new Student());
    return "add-student";
  }

  @PostMapping("/students") public String save(@ModelAttribute Student s){
    svc.save(s);
    return "redirect:/";
  }

  @GetMapping("/students/delete/{id}") public String delete(@PathVariable int id){
    svc.delete(id);
    return "redirect:/";
  }

  @GetMapping("/login") public String login() { return "login"; }
}

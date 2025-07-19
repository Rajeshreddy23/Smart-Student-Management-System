@RestController
@RequestMapping("/api/students")
public class StudentController {
  @Autowired private StudentService svc;

  @GetMapping public List<Student> list() { return svc.listAll(); }
  @GetMapping("/{id}") public Student get(@PathVariable int id){return svc.get(id);}
  @PostMapping public Student create(@Valid @RequestBody Student s) { return svc.save(s); }
  @PutMapping("/{id}") public Student update(@PathVariable int id, @Valid @RequestBody Student s){
    svc.get(id);
    s.setId(id);
    return svc.save(s);
  }
  @DeleteMapping("/{id}") public void delete(@PathVariable int id){ svc.delete(id); }
}

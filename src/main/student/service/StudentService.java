@Service
public class StudentService {
  private final StudentRepository repo;
  public StudentService(StudentRepository repo) { this.repo = repo; }

  public List<Student> listAll(){ return repo.findAll(); }
  public Student get(int id){return repo.findById(id).orElseThrow();}
  public Student save(Student s){return repo.save(s);}
  public void delete(int id){repo.deleteById(id);}
}

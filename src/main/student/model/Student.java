@Entity
@Table(name="students")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank @Size(min=3, max=100)
    private String name;

    @Email @NotBlank
    @Column(unique=true)
    private String email;

    // getters/setters
}

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired private AuthenticationManager authManager;
  @Autowired private JwtUtil jwtUtil;
  @Autowired private UserDetailsService uds;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthRequest ar){
    try {
      authManager.authenticate(
        new UsernamePasswordAuthenticationToken(ar.getUsername(), ar.getPassword()));
    } catch(Exception e){ return ResponseEntity.status(401).body("Invalid credentials"); }

    UserDetails ud = uds.loadUserByUsername(ar.getUsername());
    String jwt = jwtUtil.generateToken(ud.getUsername());
    return ResponseEntity.ok(new AuthResponse(jwt));
  }
}

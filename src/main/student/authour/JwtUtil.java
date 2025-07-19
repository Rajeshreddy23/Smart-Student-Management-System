@Component
public class JwtUtil {
  @Value("${jwt.secret}") private String SECRET_KEY;
  @Value("${jwt.expiration}") private long EXPIRATION_MS;

  public String generateToken(String username){
    return Jwts.builder()
               .setSubject(username)
               .setIssuedAt(new Date())
               .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
               .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
               .compact();
  }
  public String extractUsername(String token){
    return Jwts.parser().setSigningKey(SECRET_KEY)
                       .parseClaimsJws(token).getBody().getSubject();
  }
  public boolean validate(String token, UserDetails ud){
    return extractUsername(token).equals(ud.getUsername());
  }
}

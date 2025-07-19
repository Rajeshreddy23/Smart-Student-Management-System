@Component
public class JwtRequestFilter extends OncePerRequestFilter {
  @Autowired private UserDetailsService uds;
  @Autowired private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc)
      throws ServletException, IOException {
    String header = req.getHeader("Authorization");
    String jwt = null, user=null;

    if(header!=null && header.startsWith("Bearer ")){
      jwt=header.substring(7);
      user=jwtUtil.extractUsername(jwt);
    }

    if(user!=null && SecurityContextHolder.getContext().getAuthentication()==null){
      UserDetails ud = uds.loadUserByUsername(user);
      if(jwtUtil.validate(jwt, ud)){
        UsernamePasswordAuthenticationToken token =
          new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(token);
      }
    }
    fc.doFilter(req, res);
  }
}

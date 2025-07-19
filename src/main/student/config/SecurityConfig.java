@Configuration
@EnableWebSecurity
public class SecurityConfig {
  @Autowired private JwtRequestFilter jwtFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .authorizeHttpRequests()
          .requestMatchers("/login","/api/auth/**","/css/**").permitAll()
          .antMatchers("/api/**").authenticated()
          .anyRequest().permitAll()
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin().loginPage("/login").defaultSuccessUrl("/", true)
        .and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/login");

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
               .inMemoryAuthentication()
               .withUser("admin").password("{noop}admin123").roles("ADMIN")
               .and()
               .withUser("user").password("{noop}user123").roles("USER")
               .and()
               .and()
               .build();
  }

  @Bean
  public UserDetailsService userDetailsService(){
    InMemoryUserDetailsManager mgr = new InMemoryUserDetailsManager();
    mgr.createUser(User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build());
    mgr.createUser(User.withUsername("user").password("{noop}user123").roles("USER").build());
    return mgr;
  }
}

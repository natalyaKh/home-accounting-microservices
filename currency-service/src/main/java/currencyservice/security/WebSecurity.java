package currencyservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private Environment environment;
    //    private UsersService usersService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public WebSecurity(Environment environment, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.environment = environment;
//        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers().hasIpAddress(environment.getProperty("gateway.ip"))
                .antMatchers( "h2-console").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), environment));
        http.headers().frameOptions().disable();
    }

//    private AuthenticationFilter getAuthenticationFilter() throws Exception
//    {
//        AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, environment, authenticationManager());
//        //authenticationFilter.setAuthenticationManager(authenticationManager());
//        authenticationFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
//        return authenticationFilter;
//    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
//    }

}


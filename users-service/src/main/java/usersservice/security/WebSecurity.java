package usersservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import usersservice.constants.SecurityConstants;
import usersservice.service.UserService;

import java.util.Arrays;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
                       Environment env) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**добавляет cors*/
                .cors().and()
                .csrf().disable().authorizeRequests()
//        .antMatchers("/users/**").permitAll()
//                /** разрешаем доступ для получения запосов с конкретного IP */
//                .antMatchers("/**")
//                .hasIpAddress(env.getProperty("gateway.ip"))
                /** проверка pingController - разрешена всем */
                .antMatchers("/test/**")
                .permitAll()
                .antMatchers("/clean-data/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                .permitAll()
                /** создание администратора - разрешено всем*/
                .antMatchers(HttpMethod.GET, SecurityConstants.CREATE_ADMIN)
                .permitAll()
                /**проверка e-mail - разрешена всем**/
                .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
                .permitAll()
                /**изменение пароля - доступно всем**/
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
                .permitAll()
                /**вход по ссылке для изменения пароля из письма доступен всем*/
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
                .permitAll()
                /** доступ к свагеру */
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                .permitAll()
                .antMatchers("/users/**").permitAll()
                .anyRequest().authenticated()
                .and()
                /**
                 * фильтр для всех обьектов, которые проходят через процедуру аутентификации
                 */
                .addFilter(getAuthenticationFilter())
                /**
                 * только тот, кто вошел в пприложение, может менять что то в нем
                 */
                .addFilter(new AuthorizationFilter(authenticationManager()));

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * логин пользователя теперь по ссылке .../users/login
     *
     * @return
     * @throws Exception
     */
    protected AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

    /**
     * добавляет cors
     *
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

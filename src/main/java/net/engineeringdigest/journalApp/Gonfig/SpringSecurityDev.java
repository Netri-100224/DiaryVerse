package net.engineeringdigest.journalApp.Gonfig;

import net.engineeringdigest.journalApp.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityDev extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userservimpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/journal/**","/user/**","/public/login/**","/Journal/**").authenticated()  //Wildcard pattern
                .antMatchers("/admin").hasRole("Admin")
                .antMatchers( "/public/signup","/public/healthcheck").permitAll()
                .anyRequest().permitAll()  //Authenticate all endpoints with this request URL and permit them.
                .and()
                .httpBasic();       //put authentication restrictions on endpoints other than above URL request
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf(csrf -> csrf.disable()); //added because Postman was giving 403 forbidden error
//by default it requires CSRF token

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userservimpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}

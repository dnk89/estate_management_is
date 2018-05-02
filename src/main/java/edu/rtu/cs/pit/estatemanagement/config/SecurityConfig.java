package edu.rtu.cs.pit.estatemanagement.config;

import edu.rtu.cs.pit.estatemanagement.users.services.UserSecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityServiceImpl userSecurityServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/operator/**").hasRole("OPERATOR")
                    .antMatchers("/customer/**").hasRole("CUSTOMER")
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/index")
                    .failureUrl("/index?error")
                    .defaultSuccessUrl("/authorization")
                    .permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/index?logout")
                    .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityServiceImpl);
    }
}

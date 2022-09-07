package com.epam.edumanagementsystem.config;

import com.epam.edumanagementsystem.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService)
                .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/sign-in")
                .permitAll()
                .failureUrl("/sign-in?error")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/sign-in")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")
                .and()
                .authorizeRequests()
                .antMatchers("/home/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/admin/*").hasAnyAuthority("ADMIN")
                .anyRequest().permitAll();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/resources/**");
        webSecurity.ignoring().antMatchers("/css/**");
        webSecurity.ignoring().antMatchers("/images/**");
        webSecurity.ignoring().antMatchers("/js/**");
        webSecurity.ignoring().antMatchers("/assets/**");
        webSecurity.ignoring().antMatchers("/plugins/**");
        webSecurity.ignoring().antMatchers("/pages/**");
        webSecurity.ignoring().antMatchers("/web/**");
    }


}
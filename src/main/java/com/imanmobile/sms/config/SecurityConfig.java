package com.imanmobile.sms.config;

import com.imanmobile.sms.security.CustomAuthenticationProvider;
import com.imanmobile.sms.security.CustomAuthenticationSuccessHandler;
import com.imanmobile.sms.security.CustomLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * Created by jome on 2014/02/28.
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    CustomAuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider);

    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {

        return super.authenticationManager();
    }

    @Bean
    protected LogoutHandler logoutHandler(){
        return new CustomLogoutHandler();
    }

    @Bean
    protected AuthenticationSuccessHandler successHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/appcenter").hasAuthority("USER")
                .antMatchers("/contacts").hasAuthority("USER")
                .antMatchers("/quicksms").hasAnyAuthority("USER")
                .antMatchers("/account-settings").hasAnyAuthority("USER")
                .antMatchers("/resources/**").permitAll()
                .anyRequest().permitAll();

        http.formLogin().defaultSuccessUrl("/appcenter");
        http.formLogin().loginProcessingUrl("/processlogin");
        http.formLogin().loginPage("/login").permitAll().and().logout().logoutUrl("/logout").addLogoutHandler(logoutHandler()).logoutSuccessUrl("/");
        //http.httpBasic();
        http.csrf().disable();
    }
}

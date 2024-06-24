package com.spring.dongnae.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import com.spring.dongnae.user.service.CustomAuthenticationProvider;

import com.spring.dongnae.user.service.CustomAuthFailureHandler;
import com.spring.dongnae.user.service.CustomAuthenticationSuccessHandler;
import com.spring.dongnae.user.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthFailureHandler customFailureHandler;
	 
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            	//컨트롤러 전에 실행 
            	.antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/login", "/joinform").permitAll()
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/login").permitAll()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/authenticate")
//                .defaultSuccessUrl("/", true)
                .successHandler(customAuthenticationSuccessHandler) // 커스텀 성공 핸들러 설정
                .failureHandler(customFailureHandler)
//                .failureUrl("/loginerror")
                .and()
            .logout()
//            	.logoutSuccessUrl("/login")
                .permitAll()
        		.and()
            .sessionManagement()
            	.sessionFixation().none();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
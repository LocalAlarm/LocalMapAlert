package com.spring.dongnae.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.spring.dongnae.user.service.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("시큐리티 필터 체인 작동");
        http
            .csrf().disable()
            .authorizeRequests()
            	// antMatches: 이 url에 관한 요청은 인증을 필요로하지 않는다.
                .antMatchers("/", "/home", "/register", "/login", "/resources/**", "/login-proc", "index.jsp").permitAll()
                //anyRequest: 로그인을 필요로한다.
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	// 로그인에 대한 get요청
                .loginPage("/login").permitAll()
                // 로그인에 대한 post요청
                .loginProcessingUrl("/login-proc").permitAll()
                // 로그인 성공시
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login?error=true")
                .and()
            .logout()
                .permitAll();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }
}

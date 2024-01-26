package ru.itgirl.libraryproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize)
                -> authorize.requestMatchers("/book")
                .hasRole("USER")
                .requestMatchers("/book/v2")
                .hasRole("ADMIN")
                .requestMatchers("/books")
                .hasRole("ADMIN")
                .anyRequest().authenticated()).httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsManager users(DataSource dataSource) {
        User.UserBuilder users = User.builder().passwordEncoder(encoder()::encode);
        var joe = users
                .username("john")
                .password("doe")
                .roles("USER")
                .build();
        var jane = users
                .username("jane")
                .password("doe")
                .roles("USER", "ADMIN")
                .build();
        var admin = users
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        var manager = new JdbcUserDetailsManager(dataSource);
        manager.createUser(joe);
        manager.createUser(jane);
        manager.createUser(admin);
        return manager;
    }
}

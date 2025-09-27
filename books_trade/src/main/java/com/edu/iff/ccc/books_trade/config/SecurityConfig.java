package com.edu.iff.ccc.books_trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // 1. REGRAS PÚBLICAS: Acesso permitido a todos
                .requestMatchers(HttpMethod.GET, "/", "/livros", "/livros/{id}").permitAll()
                .requestMatchers("/auth/**", "/css/**", "/js/**", "/images/**").permitAll()
                // Permite que usuários não logados se cadastrem (POST para /usuarios)
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                // 2. REGRAS PRIVADAS: Exigem autenticação
                .requestMatchers("/livros/novo", "/trocas/**", "/usuarios/perfil/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/livros").authenticated() // POST para criar livros
                .requestMatchers("/admin/**").authenticated() // Toda a área de admin

                // 3. Qualquer outra requisição não mapeada será negada
                .anyRequest().denyAll()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .defaultSuccessUrl("/livros", true) // Após login, vai para a lista de livros
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Após logout, vai para a home
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

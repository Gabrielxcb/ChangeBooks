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
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll() // Cadastro

                // 2. REGRAS PRIVADAS: Exigem autenticação
                .requestMatchers("/livros/novo", "/trocas/**", "/usuarios/perfil/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/livros").authenticated() // Salvar (criar e editar)
                
                // --- NOVAS REGRAS ADICIONADAS AQUI ---
                // Permite acessar o formulário de edição e executar a exclusão
                .requestMatchers("/livros/{id}/editar").authenticated()
                .requestMatchers(HttpMethod.POST, "/livros/{id}/excluir").authenticated()
                // -----------------------------------------
                
                .requestMatchers("/admin/**").authenticated()

                // 3. Qualquer outra requisição não mapeada será negada
                .anyRequest().denyAll()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .defaultSuccessUrl("/livros", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

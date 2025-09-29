package com.edu.iff.ccc.books_trade.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(1) // PRIORIDADE 1: Esta regra é verificada PRIMEIRO
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**") // Aplica esta configuração APENAS para URLs que começam com /api/
            .authorizeHttpRequests(authorize -> authorize
                // Por enquanto, vamos permitir todas as requisições para a API para facilitar os testes
                .anyRequest().permitAll() // TODO: Mudar para .authenticated() na Etapa 5 (Segurança da API com JWT)
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Diz ao Spring para NÃO usar sessões para a API
            .csrf(csrf -> csrf.disable()); // Desabilita o CSRF para a API, pois usaremos tokens JWT no futuro

        return http.build();
    }

    @Bean
    @Order(2) // PRIORIDADE 2: Se a URL não for /api/**, esta regra será verificada
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                // Regras para a aplicação web (exatamente como estavam antes)
                .requestMatchers(HttpMethod.GET, "/", "/livros", "/livros/{id}").permitAll()
                .requestMatchers("/auth/**", "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .requestMatchers("/livros/{id}/editar", "/livros/{id}/excluir", "/livros/novo", "/trocas/**").authenticated()
                .anyRequest().authenticated() // Qualquer outra requisição WEB precisa de autenticação
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
}

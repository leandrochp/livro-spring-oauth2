package com.github.leandrochp.bookserver.configuracao.seguranca.basic;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ConfiguracaoDeSeguranca {

    @Configuration
    public static class ConfiguracaoParaUsuario extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            String[] caminhosPermitidos = new String[]{
                    "/", "/home", "/usuarios",
                    "/webjars/**", "/static/**", "/jquery*"
            };

            http
                    .authorizeRequests()
                    .antMatchers(caminhosPermitidos).permitAll()
                    .anyRequest().authenticated().and()
                    .formLogin()
                    .permitAll()
                    .loginPage("/login")
                    .and()
                    .logout()
                    .permitAll()
                    .and()
                    .csrf().disable();
        }
    }
}

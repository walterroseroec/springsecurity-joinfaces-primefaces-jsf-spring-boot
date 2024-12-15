package com.std.ec.config.security;

import com.std.ec.config.filter.AutorizacionFilter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserCredentialsSecurity userCredentialsSecurity;
    @Autowired
    private AutorizacionFilter autorizacionFilter;

    //metodo auxiliar para ccrear coincidencias de rutas con MVC
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    //metodo para encriptar claves
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //metodo para validar la autenticacion del usuario a loguearse
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userCredentialsSecurity)
                .passwordEncoder(passwordEncoder());

        return builder.build();
    }

    //metodo de oconfiguracion con spring security
    @Bean
    public SecurityFilterChain configure(HttpSecurity http, MvcRequestMatcher.Builder mvc) {
        try {
            http.csrf(AbstractHttpConfigurer::disable);
            http.authorizeHttpRequests(authorize -> {
                        authorize.requestMatchers(mvc.pattern("/login.xhtml")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/jakarta.faces.resource/**")).permitAll()
                                .anyRequest()
                                .denyAll();
                    })
                    //configuracion del formulario de login
                    .formLogin(formLogin -> formLogin
                            .loginPage("/login.xhtml").permitAll()
                            .failureUrl("/login.xhtml?error=true")
                            .defaultSuccessUrl("/home.xhtml")
                            //parametros personalizados de campos en login
                            .usernameParameter("nombreUsuario")
                            .passwordParameter("clave")
                    )
                    //configuracion de cerrar sesion
                    .logout(logout -> logout
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/login.xhtml")
                            .deleteCookies("JSESSIONID")
                    )
                    //validacion de pagina 403
                    .exceptionHandling(ex -> ex.accessDeniedPage("/403.xhtml"))
                    //validacion de acceso de rutas y roles despues de loguearse
                    .addFilterAfter(autorizacionFilter, UsernamePasswordAuthenticationFilter.class)
            ;
            return http.build();
        } catch (Exception ex) {
            throw new BeanCreationException("Wrong spring security configuration", ex);
        }
    }

}

package bo.com.micrium.modulobase.security.config;

import java.io.Serializable;

//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;// @deprecado
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import bo.com.micrium.modulobase.security.controllers.JwtAuthenticationController;
import bo.com.micrium.modulobase.security.filters.JwtRequestFilter;
import bo.com.micrium.modulobase.controllers.EtiquetaControler;

/**
 *
 * @author alepaco.maton
 */
@Configuration
@EnableWebSecurity
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebSecurityConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Autowired
    //private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    // Definición de AuthenticationManager como bean
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authRequest ->
                    authRequest.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .requestMatchers(EtiquetaControler.RESOURCE_BY_LLAVE).permitAll()
                            .requestMatchers(EtiquetaControler.RESOURCE_BY_GRUPO).permitAll()
                            // .requestMatchers(PerfilControler.RESOURCE_CAMBIOLOGIN).permitAll().
                            .requestMatchers(JwtAuthenticationController.METODO_AUTENTICACION).permitAll()
                            .requestMatchers(JwtAuthenticationController.METODO_VERSION).permitAll()
                            .anyRequest().authenticated()
            )
            .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //.authenticationProvider(authProvider)
            //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            //.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/access-denied"))
            //.cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}

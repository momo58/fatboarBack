package com.pfa.fatboar.FatboarBack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pfa.fatboar.FatboarBack.oauth2.CustomAuthenticationSuccessHandler;
import com.pfa.fatboar.FatboarBack.oauth2.CustomOAuth2UserService;
import com.pfa.fatboar.FatboarBack.oauth2.CustomOidcUserService;
import com.pfa.fatboar.FatboarBack.security.JwtAuthenticationEntryPoint;
import com.pfa.fatboar.FatboarBack.security.JwtAuthenticationFilter;
import com.pfa.fatboar.FatboarBack.services.ServiceImpl.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableAsync
/**
 * Contains all the security configs required
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter implements ApplicationContextAware {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private CustomOidcUserService customOidcUserService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public JwtAuthenticationFilter authenticationTokenFilterBean() {
        return new JwtAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/tickets/insertTickets", "/admin/introducethegame").hasAuthority("SUPER_ADMIN")
                .antMatchers(HttpMethod.GET, "/admin/getContactList").hasAuthority("SUPER_ADMIN")
                .antMatchers(HttpMethod.GET, "/admin/admins").hasAuthority("SUPER_ADMIN")
                .antMatchers(HttpMethod.POST, "/admin/admin").hasAuthority("SUPER_ADMIN")
                .antMatchers(HttpMethod.PUT, "/admin/admin").hasAuthority("SUPER_ADMIN")
                .antMatchers(HttpMethod.DELETE, "/admin/admin").hasAuthority("SUPER_ADMIN")
                
                .antMatchers(HttpMethod.GET, "/admin/managers").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/admin/manager").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/admin/manager").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/admin/manager").hasAuthority("ADMIN")
                
                .antMatchers(HttpMethod.GET, "/admin/employes").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.POST, "/admin/employe").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.PUT, "/admin/employe").hasAuthority("MANAGER")
                .antMatchers(HttpMethod.DELETE, "/admin/employe").hasAuthority("MANAGER")

                .antMatchers(HttpMethod.GET, "/api/tickets/employe/**").hasAuthority("EMPLOYE")
                
                .antMatchers("/api/tickets/**","/api/auth/**","/api/**","/admin/**", "/client/**", "/misc/**").permitAll()
                
                .anyRequest()
                         .authenticated()
                         .and()
                         .csrf().disable()
                         .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                         .and()

                .oauth2Login()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                        .and()

                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)
                        .and()

                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                        .authorizationRequestRepository(customAuthorizationRequestRepository())
                        .and()

                    .successHandler(customAuthenticationSuccessHandler);

        http
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * All the state related to the authorization request is saved using the AuthorizationRequestRepository
     * @return
     */
    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> customAuthorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}

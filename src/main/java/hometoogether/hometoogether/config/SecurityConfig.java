package hometoogether.hometoogether.config;

import hometoogether.hometoogether.config.jwt.JwtAuthenticationEntryPoint;
import hometoogether.hometoogether.config.jwt.JwtAuthenticationFilter;
import hometoogether.hometoogether.domain.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final JwtAuthenticationEntryPoint unauthorizedHandler;
//    private final CustomUserDetailsService customUserDetailsService;
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//
//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
////        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
//    }
//
//    @Bean(BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // For CORS error
////        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
////        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
////        http.csrf().disable()
////                // dont authenticate this particular request
////                .authorizeRequests().antMatchers("*").permitAll().
//////        "/api/users/login", "/api/users/signup", "/api/users/find/password",
//////                "/api/users/find/userName/**", "/api/forums/count/**", "/api/forums/**",
//////                "/routine/**", "/rooms", "/routines", "/routine", "/myRoutines/**",
//////                "/room/**", "/start", "/ws-stomp/**", "/api/challenges/**", "/api/trials/**", "/demo/similarity",
////                // all other requests need to be authenticated
////                        anyRequest().authenticated().and().
////                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        // Add our custom JWT security filter
////         http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        // - (3)
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}

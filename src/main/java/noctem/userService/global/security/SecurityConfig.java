package noctem.userService.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final String LOGIN_PROCESSING_URL = "/api/user-service/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.logout().disable();
        http.httpBasic().disable();
        http.formLogin().disable();
        http.headers().frameOptions().disable();
        http.rememberMe().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/api/**").permitAll();

//        http.addFilterAfter(new JwtRequestProcessingFilter("/api/*"), SecurityContextPersistenceFilter.class);
//        http.addFilterBefore(corsFilter, SecurityContextPersistenceFilter.class);
//        applyJwtConfigurer(http);
    }
}

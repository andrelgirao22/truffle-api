package br.com.alg.trufflesapi.jwt.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.com.alg.trufflesapi.jwt.security.TokenHelper;
import br.com.alg.trufflesapi.jwt.security.auth.RestAuthenticationEntryPoint;
import br.com.alg.trufflesapi.jwt.security.auth.TokenAuthenticationFilter;
import br.com.alg.trufflesapi.jwt.service.impl.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	public PasswordEncoder passwordEncoder() {
		
		String idForEncode = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(idForEncode, new BCryptPasswordEncoder());
		
		PasswordEncoder passwordEncoder =
			    new DelegatingPasswordEncoder(idForEncode, encoders);
		
		return passwordEncoder;
	}
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Autowired
    TokenHelper tokenHelper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
    		.cors().and()
            .sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
            .exceptionHandling().authenticationEntryPoint( restAuthenticationEntryPoint ).and()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated().and()
            .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService), BasicAuthenticationFilter.class);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                HttpMethod.POST,
                "/auth/login"
        );
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/",
                "/webjars/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/info",
                "/trace",
                "/health",
                "/health/"
            );

    }
    
    @Bean
    public WebMvcConfigurer corsConfigure() {
    	return new WebMvcConfigurerAdapter() {
    		@Override
    		public void addCorsMappings(CorsRegistry registry) {
    			registry.addMapping("/**");
    		}
		};
    }
}

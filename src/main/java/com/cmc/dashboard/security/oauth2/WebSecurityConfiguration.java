package com.cmc.dashboard.security.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cmc.dashboard.security.CustomUserDetailsService;
import com.cmc.dashboard.security.UserAuthenticationProvider;

/**
 * 
 * @author longl
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  private Logger logger = LoggerFactory.getLogger(WebSecurityConfiguration.class);
	@Autowired
    private CustomUserDetailsService userDetailsService;
//
    @Autowired
    private UserAuthenticationProvider accountAuthenticationProvider;
//
//@Autowired
//	private AuthenticationManager authenticationManager;

	@Override
	public void configure(WebSecurity web) throws Exception {
	  logger.info("---444public void configure(WebSecurity web)");
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/scss/**", "/js/**", "/images/**",
				"/assets/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  logger.info("---444protected void configure(HttpSecurity http) throws Exception");
		http.csrf()
			.disable()
			.antMatcher("/api/**")
			.authorizeRequests()
			.antMatchers("oauth/token", "/webjars/**")
			.permitAll()
			.antMatchers(HttpMethod.OPTIONS).permitAll()
			.anyRequest()
			.authenticated();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	  logger.info("---444PasswordEncoder");

		return new BCryptPasswordEncoder();
	}

	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	  logger.info("---444configure(AuthenticationManagerBuilder auth)");
	  
	  logger.info(auth.toString());
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(accountAuthenticationProvider);
//        auth.parentAuthenticationManager(authenticationManager);
    }

}

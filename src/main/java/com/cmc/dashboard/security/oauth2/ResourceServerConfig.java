package com.cmc.dashboard.security.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
  private Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);
	@Value("${app.jwt.signingKey}")
	private String signinKey;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
	  logger.info("---444configure (ResourceServerSecurityConfigurer resources)");
		resources.resourceId("restservice").tokenStore(tokenStore());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
	  logger.info("---444configure(HttpSecurity http)");
		http.csrf().disable().authorizeRequests().antMatchers("/api/").authenticated();
	}

	@Bean
	public TokenStore tokenStore() {
	  logger.info("---444public TokenStore tokenStore()");
		return new InMemoryTokenStore();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
	  logger.info("---444JwtAccessTokenConverter ");
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(signinKey);
		 logger.info(signinKey.toString());
		return converter;
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
	  logger.info("---444DefaultTokenServices ");
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

}

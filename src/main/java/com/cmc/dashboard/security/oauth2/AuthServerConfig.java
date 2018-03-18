package com.cmc.dashboard.security.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.cmc.dashboard.security.CustomUserDetailsService;
import com.cmc.dashboard.security.UserAuthenticationProvider;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {
  private Logger logger = LoggerFactory.getLogger(AuthServerConfig.class);
	@Autowired
    private AuthenticationManager authenticationManager;
// 
    @Autowired
	private TokenStore tokenStore;
//    
   @Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
    
    @Autowired
	private CustomUserDetailsService userDetailsService;

    @Value("${app.jwt.signingKey}")
	private String signinKey;
    
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//        oauthServer.tokenKeyAccess("permitAll()")
//          .checkTokenAccess("isAuthenticated()");
//    }
 
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      logger.info("---444------------configure---------------");
      System.out.print("configure");
      logger.info(clients.toString());
      System.out.print(clients.toString());
    	clients.inMemory()
    	//User Basic Authen
		.withClient("dashboard") // Username Basic Auth
		.secret("abc@1234!2354%")//Password Basic Auth
		.authorizedGrantTypes("password", "refresh_token")
		.authorities("USER","DUL","QA", "PM","BOD").scopes("read", "write")
		.resourceIds("restservice")
                // set a zero or negative value for the access token validity to the token to expire
		.accessTokenValiditySeconds(0);
    }
 
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      logger.info("---444configure(AuthorizationServerEndpointsConfigurer endpoints)");
//        endpoints.authenticationManager(authenticationManager);
    	endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
		.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailsService);
    }
    
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signinKey);
        logger.info(" ---444JwtAccessTokenConverter");
        logger.info(signinKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
      logger.info("---444 TokenStore tokenStore()");
        return new InMemoryTokenStore();
    }
    
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
      logger.info("  ---444public DefaultTokenServices tokenServices() ");
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }
}

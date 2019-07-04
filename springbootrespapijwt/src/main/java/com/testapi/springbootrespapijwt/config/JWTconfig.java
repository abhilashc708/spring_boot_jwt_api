package com.testapi.springbootrespapijwt.config;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class JWTconfig extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager  authenticationManager;
	
	@Autowired
	@Value("${test.oauth.tokenTimeout:3600}")
	private int expiration;
	
	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
	private String signingkey;
	
	@Autowired
	private TokenStore tokenstore;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	
	@Bean
	public  JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter= new JwtAccessTokenConverter();
		converter.setSigningKey("jwtdemo123");
		return converter;
	}

	public void configure(AuthorizationServerEndpointsConfigurer configurer) throws Exception{
		TokenEnhancerChain enhancerChain= new TokenEnhancerChain();
		List list= Arrays.asList(accessTokenConverter);
		enhancerChain.setTokenEnhancers(list);
		
		configurer.tokenStore(tokenstore)
		.accessTokenConverter(accessTokenConverter)
		.tokenEnhancer(enhancerChain);
		
		
		//GET THE DETAILS FROM DATABASE
		configurer.authenticationManager(authenticationManager);
		configurer.userDetailsService(userDetailsService);
	}
	
	public void configure(ClientDetailsServiceConfigurer clients)throws Exception{
		
		//hard code value api token
		/*
		clients.inMemory().withClient("varun").secret("secret").accessTokenValiditySeconds(expiration)
		.scopes("read","write").authorizedGrantTypes("password","refresh_token").resourceIds("resource");
		*/
		//using database to access token
		clients.jdbc(dataSource);
	}
}

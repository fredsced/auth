package fr.formation.jwtresourceserver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class JwtResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${jwt-resource-server.resourceId}")
    private String resourceId;

    @Value("${jwt-resource-server.publicKey}")
    private String publicKey;

    @Override
    public void configure(HttpSecurity http) throws Exception {
	// Disable CSRF, no need with JWT if not cookie-based
	// Disable CORS if API is public, better to enable in general
	http.csrf().disable().cors().disable().anonymous().disable()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests().antMatchers("/api/public").permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
	config.tokenServices(tokenServices());
	config.resourceId(resourceId); // I am this resource server
    }

    @Bean
    public TokenStore tokenStore() {
	return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	Resource resource = new ClassPathResource(publicKey);
	String publicKey = null;
	try (InputStream is = resource.getInputStream()) {
	    publicKey = IOUtils.toString(is, Charset.defaultCharset());
	} catch (IOException ex) {
	    throw new RuntimeException(ex);
	}
	converter.setVerifierKey(publicKey);
	return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
	DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	defaultTokenServices.setTokenStore(tokenStore());
	return defaultTokenServices;
    }
}

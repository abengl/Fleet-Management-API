package com.fleetmanagement.api_rest.configuration;

import com.fleetmanagement.api_rest.business.service.UserDetailService;
import com.fleetmanagement.api_rest.configuration.filter.JwtTokenValidator;
import com.fleetmanagement.api_rest.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration // indicates that the class can be used by the Spring IoC container as a source of bean definitions
@EnableWebSecurity // Spring Security framework, enables and configures web security
public class SecurityConfig {

	@Autowired
	private JwtUtils jwtUtils;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider)
			throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(http -> {
					// Public EndPoints
					http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();

					// Private EndPoints
					http.requestMatchers(HttpMethod.GET, "/method/get").hasAuthority("READ");
					http.requestMatchers(HttpMethod.POST, "/method/post").hasAuthority("CREATE");
					http.requestMatchers(HttpMethod.DELETE, "/method/delete").hasAuthority("DELETE");
					http.requestMatchers(HttpMethod.PUT, "/method/put").hasAuthority("UPDATE");

					http.anyRequest().denyAll();
				})
				.addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(UserDetailService userDetailService) {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(passwordEncoder());

		provider.setUserDetailsService(userDetailService);

		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}
}

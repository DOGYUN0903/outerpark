package com.lockers.outerpark.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lockers.outerpark.common.security.filter.JwtFilter;
import com.lockers.outerpark.common.security.handler.CustomAccessDeniedHandler;
import com.lockers.outerpark.common.security.handler.CustomAuthenticationEntryPoint;
import com.lockers.outerpark.domain.user.type.UserRole;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(ex -> ex
				.authenticationEntryPoint(customAuthenticationEntryPoint)  // 인증 실패 시
				.accessDeniedHandler(customAccessDeniedHandler)            // 인가 실패 시
			)
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/signup", "/api/auth/signin").permitAll()
				.requestMatchers("/api/admin/**").hasRole(UserRole.ADMIN.name())
				.requestMatchers(HttpMethod.POST, "api/concerts").hasRole(UserRole.ADMIN.name())
				.requestMatchers(HttpMethod.GET, "api/concerts/*").permitAll()
				.requestMatchers(HttpMethod.GET, "api/concerts").permitAll()
				.requestMatchers(HttpMethod.PATCH, "api/concerts/*").hasRole(UserRole.ADMIN.name())
				.requestMatchers(HttpMethod.DELETE, "api/concerts/*").hasRole(UserRole.ADMIN.name())
				.anyRequest().authenticated()
			)
			.build();
	}
}

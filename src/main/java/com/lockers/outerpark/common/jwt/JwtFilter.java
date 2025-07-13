package com.lockers.outerpark.common.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lockers.outerpark.domain.user.type.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse,
		FilterChain filterChain) throws ServletException, IOException {

		String bearerJwt = httpRequest.getHeader("Authorization");

		if (bearerJwt == null) {
			// 토큰이 없으면 Security가 판단
			filterChain.doFilter(httpRequest, httpResponse);
			return;
		}

		try {

			String jwt = jwtUtil.substringToken(bearerJwt);
			// JWT 유효성 검사와 claims 추출
			Claims claims = jwtUtil.extractClaims(jwt);
			if (claims == null) {
				filterChain.doFilter(httpRequest, httpResponse);
				return;
			}

			UserRole userRole = UserRole.valueOf(claims.get("userRole", String.class));
			long userId = Long.parseLong(claims.getSubject());

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userId,
				null,
				List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()))
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(httpRequest, httpResponse);

		} catch (SecurityException | MalformedJwtException e) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
		} catch (Exception e) {
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}

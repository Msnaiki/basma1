package self.learning.edu.basma_online_Store.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import self.learning.edu.basma_online_Store.util.JWTokenProvider;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	private JWTokenProvider jwtokenprovider;

	@Autowired
	public JWTAuthorizationFilter(JWTokenProvider jwtokenprovider) {
		this.jwtokenprovider = jwtokenprovider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
			response.setStatus(HttpStatus.OK.value());
		} else {
			String auth_Header = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (auth_Header == null || !auth_Header.startsWith("Bearer ")) {
				filterChain.doFilter(request, response);
				return;
			}
			String token = auth_Header.substring(7);
			String username = jwtokenprovider.getSubject(token);

			if (jwtokenprovider.isTokenValid(username, token)
					&& SecurityContextHolder.getContext().getAuthentication() == null) {
				List<GrantedAuthority> authorities = jwtokenprovider.getAuthorities(token);
				Authentication authentication = jwtokenprovider.getAuthentication(username, authorities, request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				SecurityContextHolder.clearContext();
			}
		}
		filterChain.doFilter(request, response);

	}

}

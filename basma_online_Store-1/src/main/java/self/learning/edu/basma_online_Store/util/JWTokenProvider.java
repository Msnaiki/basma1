package self.learning.edu.basma_online_Store.util;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import self.learning.edu.basma_online_Store.models.UserPrincipal;

@Component
public class JWTokenProvider {

	@Value(value = "${jwt.secret}")
	private String secret_Key;

	public String generateJWToken(UserPrincipal principal) {
		String[] claims = getClaimsFromUser(principal);
		return JWT.create().withIssuer("Issuer").withAudience("audience").withIssuedAt(new Date())
				.withSubject(principal.getUsername()).withArrayClaim("authorities", claims)
				.withExpiresAt(new Date(System.currentTimeMillis() + 18_000_000))
				.sign(Algorithm.HMAC512(secret_Key.getBytes()));
	}

	private String[] getClaimsFromUser(UserPrincipal principal) {
		List<String> authorities = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : principal.getAuthorities()) {
			authorities.add(grantedAuthority.getAuthority());
		}
		return authorities.toArray(new String[0]);
	}

	public List<GrantedAuthority> getAuthorities(String token) {
		String[] claims = getClaimsFromToken(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim("authorities").asArray(String.class);
	}

	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			verifier = JWT.require(Algorithm.HMAC512(secret_Key)).withIssuer("Issuer").build();
		} catch (JWTVerificationException e) {
			throw new JWTVerificationException("Token Cannot Be Verified");
		}
		return verifier;
	}

	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
			HttpServletRequest request) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return authenticationToken;
	}

	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = getJWTVerifier();
		return StringUtils.isNotEmpty(username) && !isTokenNotExpired(verifier, token);
	}

	private boolean isTokenNotExpired(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getSubject();
	}
}

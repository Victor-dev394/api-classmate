package br.com.victor.classmate.config.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.victor.classmate.model.Perfil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	@Value("${classmate.jwt.expiration}")
	private String expiration;
	
	private Key secret;
	
	public TokenService() {
		this.secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	}
	
	public String gerarToken(Authentication authentication) {
		Perfil logado = (Perfil) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("API do forum da alura")
				.setSubject(logado.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(dataExpiracao)
				.signWith(secret)
				.compact();
	}
	
	public boolean isTokenValido(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(this.secret).build().parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(this.secret).build().parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}
	
}

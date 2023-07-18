package com.maxence_macia.RezoProjectJava.services;

import java.security.Key;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtService {

	private final String SECRET_KEY = "4eacd42331195f498eda8ea1c6275a4fcd2cb8e993195290e3b9366c71aff16e";

	public String extractUsername(String token) {
		return this.extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = this.extractAllClaims(token);
		
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(this.getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY );
		
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

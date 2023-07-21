package com.maxence_macia.RezoProjectJava.services;

import java.security.Key;
import java.util.*;

import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final String SECRET_KEY = "4eacd42331195f498eda8ea1c6275a4fcd2cb8e993195290e3b9366c71aff16e";
	private final long ACCESS_TOKEN_EXPIRATION = 86400000;
	private final long REFRESH_TOKEN_EXPIRATION = 604800000;
	
	public String generateAccessToken(UserDetails user) {
		return this.generateToken(new HashMap<>(), user, this.ACCESS_TOKEN_EXPIRATION);
	}
	
	public String generateRefreshToken(UserDetails user) {
		return this.generateToken(new HashMap<>(), user, this.REFRESH_TOKEN_EXPIRATION);
	}
	
	public String generateToken(
			Map<String, Object> extraClaims,
			UserDetails user,
			long expiration
			) {
		
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(user.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(this.getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
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

	public boolean isTokenValid(String token, UserDetails user) {
		final String username = this.extractUsername(token);
		
		return (username.equals(user.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return this.extractExpirationDate(token).before(new Date());
	}

	private Date extractExpirationDate(String token) {
		return this.extractClaim(token, Claims::getExpiration);
	}

	public long getACCESS_TOKEN_EXPIRATION() {
		return ACCESS_TOKEN_EXPIRATION;
	}

	public long getREFRESH_TOKEN_EXPIRATION() {
		return REFRESH_TOKEN_EXPIRATION;
	}
}

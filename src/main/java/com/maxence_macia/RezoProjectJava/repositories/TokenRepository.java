package com.maxence_macia.RezoProjectJava.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maxence_macia.RezoProjectJava.entities.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
	@Query("""
			select t from Token t inner join User u on t.user.id = u.id
			where u.id = :userId and (t.expired = false or t.revoked = false)
			""")
	public List<Token> findAllValidTokenByUser(@Param("userId") long userId);
	
	@Query("""
			select t from Token t inner join User u on t.user.id = u.id
			where u.id = :userId
			""")
	public List<Token> findAllTokenByUser(@Param("userId") long userId);
	
	public Optional<Token> findByToken(String token);
}

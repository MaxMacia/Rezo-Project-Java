package com.maxence_macia.RezoProjectJava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maxence_macia.RezoProjectJava.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}

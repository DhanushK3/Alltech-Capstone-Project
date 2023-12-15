package com.group.repository;

import java.util.List;

import com.group.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	List<Message> findAllByOrderByPostedAtAsc();

}

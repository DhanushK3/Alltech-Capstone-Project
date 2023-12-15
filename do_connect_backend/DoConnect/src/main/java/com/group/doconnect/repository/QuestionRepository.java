package com.group.doconnect.repository;

import java.util.List;

import com.group.doconnect.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	boolean existsById(Long id);
	
	List<Question> findByIsApprovedTrue();
	List<Question> findByIsApprovedFalse();
	
	List<Question> findByQuestionContainingIgnoreCaseAndIsApprovedTrue(String question);
	List<Question> findByTopicContainingIgnoreCaseAndIsApprovedTrue(String topic);
	List<Question> findByTopicContainingIgnoreCaseAndQuestionContainingIgnoreCaseAndIsApprovedTrue(String topic, String question);
}

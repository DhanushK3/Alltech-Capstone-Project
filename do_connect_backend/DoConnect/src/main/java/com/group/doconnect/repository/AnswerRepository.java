package com.group.doconnect.repository;

import java.util.List;
import java.util.Optional;

import com.group.doconnect.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	List<Answer> findByIsApprovedTrue();
	List<Answer> findByIsApprovedFalse();
	
	List<Answer> findByQuestionId(Long questionId);
	Optional<Answer> findByIdAndQuestionId(Long answerId, Long questionId);
	
	List<Answer> findByQuestionIdAndIsApprovedTrue(Long questionId);
	List<Answer> findByQuestionIdAndIsApprovedFalse(Long questionId);

}

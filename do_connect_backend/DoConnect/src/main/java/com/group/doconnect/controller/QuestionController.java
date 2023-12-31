package com.group.doconnect.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.group.doconnect.dto.QuestionRequestDTO;
import com.group.doconnect.dto.QuestionResponseDTO;
import com.group.doconnect.dto.QuestionUpdateDTO;
import com.group.doconnect.dto.StatusDTO;
import com.group.doconnect.service.QuestionService;
import com.group.doconnect.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private Utilities utilities;
	
	@GetMapping("/questions")
	public ResponseEntity<?> getAllQuestions(@RequestParam(name="status") Optional<String> optionalStatus, @RequestParam(name="search") Optional<String> optionalSearch, @RequestParam(name="topic") Optional<String> optionalTopic) {
		String status = "approved";
		if (optionalStatus.isPresent()) {
			status = (optionalStatus.get().length() > 0) ? optionalStatus.get() : "approved";
		}
		String search = null;
		if (optionalSearch.isPresent()) {
			search = (optionalSearch.get().length() > 0) ? optionalSearch.get() : null;
		}
		String topic = null;
		if (optionalTopic.isPresent()) {
			topic = (optionalTopic.get().length() > 0) ? optionalTopic.get() : null;
		}
		StatusDTO<List<QuestionResponseDTO>> questionStatus = questionService.getAllQuestions(status, search, topic);
		if (!questionStatus.getIsValid()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(questionStatus.getStatusMessage());
		}
		return ResponseEntity.ok(questionStatus.getObject());
	}
	
	@GetMapping("/questions/{questionId}")
	public ResponseEntity<?> getQuestionById(@PathVariable Long questionId) {
		StatusDTO<QuestionResponseDTO> questionStatus = questionService.getQuestionById(questionId);
		if (!questionStatus.getIsValid()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(questionStatus.getStatusMessage());
		}
		return ResponseEntity.ok(questionStatus.getObject());
	}
	
	@PostMapping("/questions")
	public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequestDTO questionRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		String postedBy = utilities.getUsernameFromAuthorizationHeader(authorizationHeader);
		StatusDTO<QuestionResponseDTO> questionStatus = questionService.createQuestion(questionRequestDTO, postedBy);
		if (!questionStatus.getIsValid()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(questionStatus.getStatusMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(questionStatus.getObject());
	}
	
	@PutMapping("/questions/{questionId}")
	public ResponseEntity<?> updateQuestion(@Valid @RequestBody QuestionUpdateDTO questionUpdateDTO, @PathVariable Long questionId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		String approvedBy = utilities.getUsernameFromAuthorizationHeader(authorizationHeader);
		StatusDTO<QuestionResponseDTO> questionStatus = questionService.updateQuestion(questionUpdateDTO, questionId, approvedBy);
		if (!questionStatus.getIsValid()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(questionStatus.getStatusMessage());
		}
		return ResponseEntity.ok(questionStatus.getObject());
	}
	
	@DeleteMapping("/questions/{questionId}")
	public ResponseEntity<?> deleteQuestionById(@PathVariable @Min(1) Long questionId) {
		StatusDTO<Boolean> deletionStatus = questionService.deleteQuestionById(questionId);
		if (!deletionStatus.getIsValid()) {
			Boolean status = deletionStatus.getObject();
			if (status == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deletionStatus.getStatusMessage());
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletionStatus.getStatusMessage());
			}
		}
		return ResponseEntity.ok("Question deleted successfully.");
	}

}

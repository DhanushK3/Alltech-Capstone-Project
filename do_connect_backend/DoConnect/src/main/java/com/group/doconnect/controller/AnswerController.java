package com.group.doconnect.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.group.doconnect.dto.AnswerRequestDTO;
import com.group.doconnect.dto.AnswerResponseDTO;
import com.group.doconnect.dto.AnswerUpdateDTO;
import com.group.doconnect.dto.StatusDTO;
import com.group.doconnect.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.group.doconnect.service.AnswerService;

@RestController
@RequestMapping("/api/v1")
public class AnswerController {
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	private Utilities utilities;
	
	@GetMapping("/answers")
	public ResponseEntity<?> getAllAnswers(@RequestParam(name="status") Optional<String> optionalStatus) {
		String answerStatus = "approved";
		if (optionalStatus.isPresent()) {
			answerStatus = optionalStatus.get();
		}
		StatusDTO<List<AnswerResponseDTO>> answerResponseDTOStatus = answerService.getAllAnswers(answerStatus);
		if (!answerResponseDTOStatus.getIsValid()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(answerResponseDTOStatus.getStatusMessage());
		}
		return ResponseEntity.ok(answerResponseDTOStatus.getObject());
	}
	
	@GetMapping("/questions/{questionId}/answers")
	public ResponseEntity<?> getAllAnswersForQuestionId(@PathVariable(value="questionId") Long questionId, @RequestParam(name="status") Optional<String> optionalStatus) {
		String answerStatus = "approved";
		if (optionalStatus.isPresent()) {
			answerStatus = optionalStatus.get();
		}
		StatusDTO<List<AnswerResponseDTO>> answerResponseDTOStatus = answerService.getAllAnswersForQuestionId(questionId, answerStatus);
		if (!answerResponseDTOStatus.getIsValid()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(answerResponseDTOStatus.getStatusMessage());
		}
		return ResponseEntity.ok(answerResponseDTOStatus.getObject());
	}
	
	@PostMapping("/questions/{questionId}/answers")
	public ResponseEntity<?> createAnswerForQuestionId(@PathVariable(value="questionId") Long questionId, @Valid @RequestBody AnswerRequestDTO answerRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		String postedBy = utilities.getUsernameFromAuthorizationHeader(authorizationHeader);
		StatusDTO<AnswerResponseDTO> answerResponseDTOStatus = answerService.createAnswerForQuestionId(questionId, answerRequestDTO, postedBy);
		if (!answerResponseDTOStatus.getIsValid()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(answerResponseDTOStatus.getStatusMessage());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(answerResponseDTOStatus.getObject());
	}
	
	@PutMapping("/questions/{questionId}/answers/{answerId}")
	public ResponseEntity<?> updateAnswerForQuestionId(@Valid @RequestBody AnswerUpdateDTO answerUpdateDTO, @PathVariable(value="questionId") Long questionId, @PathVariable(value="answerId") Long answerId, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		String approvedBy = utilities.getUsernameFromAuthorizationHeader(authorizationHeader);
		StatusDTO<AnswerResponseDTO> answerResponseDTOStatus = answerService.updateAnswerForQuestionId(questionId, answerId, answerUpdateDTO, approvedBy);
		if (!answerResponseDTOStatus.getIsValid()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(answerResponseDTOStatus.getStatusMessage());
		}
		return ResponseEntity.ok(answerResponseDTOStatus.getObject());
	}
	
	@DeleteMapping("/questions/{questionId}/answers/{answerId}")
	public ResponseEntity<?> deleteAnswerForQuestionById(@PathVariable(value="questionId") Long questionId, @PathVariable(value="answerId") Long answerId) {
		StatusDTO<Boolean> deletionStatus = answerService.deleteAnswerForQuestionById(questionId, answerId);
		if (!deletionStatus.getIsValid()) {
			Boolean status = deletionStatus.getObject();
			if (status == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deletionStatus.getStatusMessage());
			}
			else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(deletionStatus.getStatusMessage());
			}
		}
		return ResponseEntity.ok("Answer for Question ID " + questionId + " deleted successfully.");
	}

}

package com.group.doconnect.controller;

import java.util.List;

import javax.validation.Valid;

import com.group.doconnect.dto.ChatMessageDTO;
import com.group.doconnect.dto.ChatMessageRequestDTO;
import com.group.doconnect.util.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
public class ChatController {
	
	@Value("${chat-microservice-base-url}")
	private String chatMicroserviceBaseUrl;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Utilities utilities;
	
	@GetMapping("/messages")
	public ResponseEntity<?> getAllChatMessages() {
		return restTemplate.getForEntity(chatMicroserviceBaseUrl + "/api/v1/messages", List.class);
	}
	
	@PostMapping("/messages")
	public ResponseEntity<?> createQuestion(@Valid @RequestBody ChatMessageRequestDTO chatMessageRequestDTO, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
		String postedBy = utilities.getUsernameFromAuthorizationHeader(authorizationHeader);
		ChatMessageDTO chatMessageDTO = new ChatMessageDTO(chatMessageRequestDTO.getMessage(), postedBy);
		return restTemplate.postForEntity(chatMicroserviceBaseUrl + "/api/v1/messages", chatMessageDTO, List.class);
	}

}

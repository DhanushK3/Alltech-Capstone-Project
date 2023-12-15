package com.group.service;

import java.util.List;
import java.util.stream.Collectors;

import com.group.dto.MessageRequestDTO;
import com.group.dto.MessageResponseDTO;
import com.group.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.repository.MessageRepository;

@Service
public class MessageServiceImp {
	
	@Autowired
	private MessageRepository messageRepository;
	
	private MessageResponseDTO convertMessageToMessageResponseDTO(Message message) {
		return new MessageResponseDTO(message.getMessage(), message.getPostedAt(), message.getPostedBy());
	}


	public List<MessageResponseDTO> getAllMessages() {
		return messageRepository.findAllByOrderByPostedAtAsc().stream().map(message -> convertMessageToMessageResponseDTO(message)).collect(Collectors.toList());
	}


	public boolean createMessage(MessageRequestDTO messageRequestDTO) {
		try {
			messageRepository.save(new Message(messageRequestDTO.getMessage(), messageRequestDTO.getPostedBy()));
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

}

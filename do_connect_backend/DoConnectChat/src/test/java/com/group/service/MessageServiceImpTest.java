/*
* @Author: Komal Anil Lawand
* Modified Date: 30-08-2022
* Description: Test Cases for MessageServiceImp
*/

package com.group.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.group.dto.MessageRequestDTO;
import com.group.dto.MessageResponseDTO;


@SpringBootTest
class MessageServiceImpTest {

	@Autowired
	MessageServiceImp service;

	@Test
	void testGetAllMessages() {
		
		List<MessageResponseDTO> response = service.getAllMessages();
		assertNotNull(response);
	}

	@Test
	void testCreateMessage() {
		MessageRequestDTO msgdto = new MessageRequestDTO("Hey ","User");
		boolean response = service.createMessage(msgdto);
		
		assertNotNull(response);
	}

}
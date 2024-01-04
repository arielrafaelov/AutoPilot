package com.etech7.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etech7.dto.AIMessage;
import com.etech7.entity.Messages;
import com.etech7.service.MessagesService;

@RestController
public class MessagesController {

	@Autowired
	MessagesService messagesService;

	private static final Logger log = LoggerFactory.getLogger(MessagesController.class);

	
	@GetMapping("/getMessages")
	public List<Messages> getMessages(@RequestParam(required = true) String conversationId) {
		return messagesService.findActiveMessages(conversationId);
	}

	@PostMapping("/addMessage")
	public Messages addMessage(@RequestBody Messages Message) {
		return messagesService.addMessage(Message);
	}
	
	@PostMapping("/updateAIMessage")
	public Messages updateAIMessage(@RequestBody AIMessage message) {
		return messagesService.updateAIPrompt(message.getId(),message.getAiText().trim());
	}
	
	@GetMapping("/getMessage")
	public Messages getMessage(@RequestParam(required = true) String messageId) {
		return messagesService.getMessageById(messageId);
	}

	@GetMapping("/deleteMessage")
	public Messages deleteMessage(@RequestParam(required = true) String messageId) {
		return messagesService.updateToDeleteMessageById(messageId);
	}

	@GetMapping("/updateFeedback")
	public Messages updateFeedback(@RequestParam(required = true) String messageId, 
	                              @RequestParam(required = true) boolean feedback) {
	    return messagesService.updateFeedback(feedback, messageId);
	}

}

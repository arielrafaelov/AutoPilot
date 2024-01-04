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
import com.etech7.entity.DocumentMessages;
import com.etech7.service.DocumentMessagesService;

@RestController
public class DocumentMessagesController {

	@Autowired
	DocumentMessagesService documentMessagesService;

	private static final Logger log = LoggerFactory.getLogger(DocumentMessagesController.class);

	@GetMapping("/getDocumentMessages")
	public List<DocumentMessages> getDocumentMessages(@RequestParam(required = true) String documentConversationID) {
		return documentMessagesService.findActiveDocumentMessages(documentConversationID);
	}

	@PostMapping("/addDocumentMessage")
	public DocumentMessages addDocumentMessage(@RequestBody DocumentMessages DocumentMessage) {
		return documentMessagesService.addDocumentMessage(DocumentMessage);
	}
	
	@PostMapping("/updateAIDocumentMessage")
	public DocumentMessages updateAIDocumentMessage(@RequestBody AIMessage documentMessage) {
		return documentMessagesService.updateAIPrompt(documentMessage.getId(),documentMessage.getAiText().trim());
	}
	
	@GetMapping("/getDocumentMessage")
	public DocumentMessages getDocumentMessage(@RequestParam(required = true) String documentMessageId) {
		return documentMessagesService.getDocumentMessageById(documentMessageId);
	}

	@GetMapping("/deleteDocumentMessage")
	public DocumentMessages deleteDocumentMessage(@RequestParam(required = true) String documentMessageId) {
		return documentMessagesService.updateToDeleteDocumentMessageById(documentMessageId);
	}

}

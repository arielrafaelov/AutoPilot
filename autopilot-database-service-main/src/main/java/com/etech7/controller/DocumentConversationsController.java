package com.etech7.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etech7.entity.DocumentConversations;
import com.etech7.service.DocumentConversationsService;

@RestController
public class DocumentConversationsController {

	@Autowired
	DocumentConversationsService documentConversationsService;

	private static final Logger log = LoggerFactory.getLogger(DocumentConversationsController.class);


	@GetMapping("/getDocumentConversations")
	public List<DocumentConversations> getDocumentConversations(@RequestParam(required = true) String userId) {
		return documentConversationsService.findActiveConversations(userId);
	}
	
	@GetMapping("/getDocumentConversationsById")
	public DocumentConversations getDocumentConversationsByID(@RequestParam(required = true) String id) {
		return documentConversationsService.getConversationById(id);
	}
	
	@PostMapping("/updateDocumentConversationData")
	public ResponseEntity<DocumentConversations> updateDocumentConversationData(@RequestParam String id, @RequestBody List<String> newData) {
	    if (id == null || id.isEmpty() || newData == null) {
	        return ResponseEntity.badRequest().body(null);
	    }
	    return ResponseEntity.ok(documentConversationsService.updateDocumentConversationData(id, newData));
	}

	@PostMapping("/addDocumentConversation")
	public ResponseEntity<DocumentConversations> addDocumentConversation(@RequestBody DocumentConversations conversation) {
	    
	    if (Objects.isNull(conversation) || StringUtils.isEmpty(conversation.getUserId())
	            || conversation.getUserId().equals("")
	            ) {
	        return ResponseEntity.badRequest().body(null);
	    }
	    return ResponseEntity.ok(documentConversationsService.addConversation(conversation));
	}


//	@GetMapping("/updateDocumentConversationPrompt")
//	public DocumentConversation updateConversationPrompt(@RequestParam(required = true) String conversationId,
//			@RequestParam(required = true) String customPrompt) {
//		return cs.updateCustomPrompt(conversationId, customPrompt);
//
//	}

	@GetMapping("/deleteDocumentConversation")
	public DocumentConversations deleteDocumentConversation(@RequestParam(required = true) String documentConversationId) {
		return documentConversationsService.updateToDeleteDocumentConversationById(documentConversationId);

	}

}

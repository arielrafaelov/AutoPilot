package com.etech7.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etech7.entity.Conversations;
import com.etech7.service.ConversationsService;

@RestController
@RequestMapping("/conversations")
public class ConversationsController {


	@Autowired
	ConversationsService cs;

	private static final Logger log = LoggerFactory.getLogger(ConversationsController.class);


	@GetMapping("/getConversations")
	public List<Conversations> getConversations(@RequestParam(required = true) String userId) {
		return cs.findActiveConversations(userId);
	}
	
	@GetMapping("/getConversationsById")
	public Conversations getConversationsByID(@RequestParam(required = true) String id) {
		return cs.getConversationById(id);
	}
	
	

	@PostMapping("/addConversation")
	public ResponseEntity<Conversations> addConversation(@RequestBody Conversations conversation) {
	    
	    if (Objects.isNull(conversation) || StringUtils.isEmpty(conversation.getUserId())
	            || StringUtils.isEmpty(conversation.getAgentID()) || conversation.getUserId().equals("")
	            || conversation.getAgentID().equals("")) {
	        return ResponseEntity.badRequest().body(null);
	    }
	    return ResponseEntity.ok(cs.addConversation(conversation));
	}


	@GetMapping("/updateConversationPrompt")
	public Conversations updateConversationPrompt(@RequestParam(required = true) String conversationId,
			@RequestParam(required = true) String customPrompt) {
		return cs.updateCustomPrompt(conversationId, customPrompt);

	}

	@GetMapping("/deleteConversation")
	public Conversations get(@RequestParam(required = true) String conversationId) {
		return cs.updateToDeleteConversationById(conversationId);

	}

	
}

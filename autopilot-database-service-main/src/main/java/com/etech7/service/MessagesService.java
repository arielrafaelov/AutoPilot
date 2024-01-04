package com.etech7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.etech7.entity.Messages;
import com.etech7.repository.MessageRepository;

@Service
public class MessagesService {

	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public Messages getMessageById(String id) {
	    Query query = new Query(Criteria.where("id").is(id));
	    return mongoTemplate.findOne(query, Messages.class);
	}

	public Messages updateToDeleteMessageById(String id) {
	    Query query = new Query(Criteria.where("id").is(id));
	    Update update = new Update();
	    update.set("deleted", true);
//        return mongoTemplate.findAndModify(query, update, Message.class);
	    
	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);

	    return mongoTemplate.findAndModify(query, update, options, Messages.class);

	}
	
	public Messages updateAIPrompt(String messageId, String aiPrompt) {
	    Query query = new Query(Criteria.where("id").is(messageId));
	    Update update = new Update();
	    update.set("aiContent", aiPrompt);
//	    return mongoTemplate.findAndModify(query, update, Message.class);
	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);

	    return mongoTemplate.findAndModify(query, update, options, Messages.class);

	}

	
	public List<Messages> findActiveMessages(String conversationId) {
	    Criteria criteria = new Criteria().andOperator(
	        Criteria.where("conversationId").is(conversationId),
	        new Criteria().orOperator(
	            Criteria.where("deleted").is(false),
	            Criteria.where("deleted").exists(false)
	        )
	    );
	    Query query = Query.query(criteria);
	    query.with(Sort.by(Sort.Direction.DESC, "timeStamp"));

	    return mongoTemplate.find(query, Messages.class);
	
	}
	
	public Messages updateFeedback(boolean feedback, String messageId) {
	    Query query = new Query(Criteria.where("id").is(messageId));
	    Update update = new Update();
	    update.set("feedback", feedback);
//	    return mongoTemplate.findAndModify(query, update, Message.class);
	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);

	    return mongoTemplate.findAndModify(query, update, options, Messages.class);

	}
	
	public Messages addMessage(Messages message) {
        return messageRepository.save(message);
    }
}

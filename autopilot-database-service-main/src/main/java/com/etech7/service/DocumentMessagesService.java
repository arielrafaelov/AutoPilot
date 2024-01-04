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

import com.etech7.entity.DocumentMessages;
import com.etech7.repository.DocumentMessageRepository;

@Service
public class DocumentMessagesService {

	@Autowired
	DocumentMessageRepository mr;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public DocumentMessages getDocumentMessageById(String id) {
	    Query query = new Query(Criteria.where("id").is(id));
	    return mongoTemplate.findOne(query, DocumentMessages.class);
	}

	public DocumentMessages updateToDeleteDocumentMessageById(String id) {
	    Query query = new Query(Criteria.where("id").is(id));
	    Update update = new Update();
	    update.set("deleted", true);
//        return mongoTemplate.findAndModify(query, update, DocumentMessage.class);
	    
	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);

	    return mongoTemplate.findAndModify(query, update, options, DocumentMessages.class);

	}
	
	public DocumentMessages updateAIPrompt(String messageId, String aiPrompt) {
	    Query query = new Query(Criteria.where("id").is(messageId));
	    Update update = new Update();
	    update.set("aiContent", aiPrompt);
//	    return mongoTemplate.findAndModify(query, update, DocumentMessage.class);
	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);

	    return mongoTemplate.findAndModify(query, update, options, DocumentMessages.class);

	}

	
	public List<DocumentMessages> findActiveDocumentMessages(String documentConversationID) {
	    Criteria criteria = new Criteria().andOperator(
	        Criteria.where("documentConversationID").is(documentConversationID),
	        new Criteria().orOperator(
	            Criteria.where("deleted").is(false),
	            Criteria.where("deleted").exists(false)
	        )
	    );
	    Query query = Query.query(criteria);
	    query.with(Sort.by(Sort.Direction.DESC, "timeStamp"));

	    return mongoTemplate.find(query, DocumentMessages.class);
	
	}

	
	public DocumentMessages addDocumentMessage(DocumentMessages message) {
        return mr.save(message);
    }
}

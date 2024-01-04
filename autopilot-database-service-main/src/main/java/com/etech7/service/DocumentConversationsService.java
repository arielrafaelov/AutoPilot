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

import com.etech7.entity.DocumentConversations;
import com.etech7.entity.DocumentMessages;
import com.etech7.repository.DocumentConversationRepository;

@Service
public class DocumentConversationsService {

	@Autowired
	DocumentConversationRepository cr;

	@Autowired
	private MongoTemplate mongoTemplate;

	public DocumentConversations updateToDeleteDocumentConversationById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deleted", true);
		DocumentConversations updatedConversation = mongoTemplate.findAndModify(query, update, DocumentConversations.class);
		if (updatedConversation != null) {
			Query messageQuery = new Query(Criteria.where("documentConversationID").is(id));
			Update messageUpdate = new Update();
			messageUpdate.set("deleted", true);
			mongoTemplate.updateMulti(messageQuery, messageUpdate, DocumentMessages.class);
		}

		return updatedConversation;
	}
	
	public DocumentConversations updateDocumentConversationData(String id, List<String> newData) {
	    Query query = new Query(Criteria.where("id").is(id));
	    Update update = new Update();
	    update.set("data", newData);
	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);

	    return mongoTemplate.findAndModify(query, update, options, DocumentConversations.class);
	}


	public List<DocumentConversations> findActiveConversations(String userID) {

//		IndexOperations indexOps = mongoTemplate.indexOps(DocumentConversation.class);
//		IndexDefinition indexDef = new Index().on("timeStamp", Sort.Direction.DESC);
//		indexOps.ensureIndex(indexDef);

		Criteria criteria = new Criteria().andOperator(Criteria.where("userID").is(userID), new Criteria()
				.orOperator(Criteria.where("deleted").is(false), Criteria.where("deleted").exists(false)));
		Query query = Query.query(criteria);
		query.with(Sort.by(Sort.Direction.DESC, "timeStamp"));

		return mongoTemplate.find(query, DocumentConversations.class);

	}

	public DocumentConversations addConversation(DocumentConversations conversation) {
		return cr.save(conversation);
	}

	public DocumentConversations updateCustomPrompt(String id, String customPrompt) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("customPrompt", customPrompt);
		  FindAndModifyOptions options = new FindAndModifyOptions();
		    options.returnNew(true);

		    return mongoTemplate.findAndModify(query, update, options, DocumentConversations.class);

	}

	public DocumentConversations getConversationById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, DocumentConversations.class);
	}

}

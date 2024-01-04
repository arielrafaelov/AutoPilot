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

import com.etech7.entity.Conversations;
import com.etech7.entity.Messages;
import com.etech7.repository.ConversationRepository;

@Service
public class ConversationsService {

	@Autowired
	ConversationRepository conversationRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public Conversations updateToDeleteConversationById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deleted", true);
		Conversations updatedConversation = mongoTemplate.findAndModify(query, update, Conversations.class);
		if (updatedConversation != null) {
			Query messageQuery = new Query(Criteria.where("conversationId").is(id));
			Update messageUpdate = new Update();
			messageUpdate.set("deleted", true);
			mongoTemplate.updateMulti(messageQuery, messageUpdate, Messages.class);
		}

		return updatedConversation;
	}

	public List<Conversations> findActiveConversations(String userId) {

//		IndexOperations indexOps = mongoTemplate.indexOps(Conversation.class);
//		IndexDefinition indexDef = new Index().on("timeStamp", Sort.Direction.DESC);
//		indexOps.ensureIndex(indexDef);

		Criteria criteria = new Criteria().andOperator(Criteria.where("userId").is(userId), new Criteria()
				.orOperator(Criteria.where("deleted").is(false), Criteria.where("deleted").exists(false)));
		Query query = Query.query(criteria);
		query.with(Sort.by(Sort.Direction.DESC, "timeStamp"));

		return mongoTemplate.find(query, Conversations.class);

	}

	public Conversations addConversation(Conversations conversation) {
		return conversationRepository.save(conversation);
	}

	public Conversations updateCustomPrompt(String id, String customPrompt) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("customPrompt", customPrompt);
//		return mongoTemplate.findAndModify(query, update, Conversation.class);
		  FindAndModifyOptions options = new FindAndModifyOptions();
		    options.returnNew(true);

		    return mongoTemplate.findAndModify(query, update, options, Conversations.class);

	}

	public Conversations getConversationById(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, Conversations.class);
	}

}

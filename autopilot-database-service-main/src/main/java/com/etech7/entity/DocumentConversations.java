package com.etech7.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "DocumentConversations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentConversations {
	 @Id
	private String id;
	private String userId;
	private String conversationName;
	private List<String> data;
	private String customPrompt;
	@CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
	private Date timeStamp;
	private boolean deleted;

}
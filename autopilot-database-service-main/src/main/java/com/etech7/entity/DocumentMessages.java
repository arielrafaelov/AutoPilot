package com.etech7.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "DocumentMessages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentMessages {
	@Id
	private String id;
	private String documentConversationId;
	private String userContent;
	private String aiContent;
	@CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
	private Date timeStamp;
	private boolean deleted;


}

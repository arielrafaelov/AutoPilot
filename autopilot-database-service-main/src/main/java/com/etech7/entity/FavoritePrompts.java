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

@Document(collection = "FavoritePrompts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoritePrompts {

    @Id
    private String id;
    private String userId;
	private String promptName;
	private String prompt;
	@Indexed(direction = IndexDirection.DESCENDING)
	@CreatedDate
	private Date timeStamp;
	private boolean deleted;
	
}

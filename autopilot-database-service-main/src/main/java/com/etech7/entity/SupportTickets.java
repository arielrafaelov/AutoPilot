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

@Document(collection = "SupportTickets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupportTickets {
    @Id
	private String id;
//    private String mspId;
    private String mspCustomDomain;
    private String ticketId;
	private String userId;
    private String title;
    private String description;
    private String summary;
    private String category;
    private String subcategory;
    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private Date timeStamp;
    private boolean closed;
}
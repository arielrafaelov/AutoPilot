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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Permissions")
public class Permissions {

    @Id
    private String id;
    private String name;  
    private String description; 
    private boolean isActive; 
    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private Date timeStamp;
	
}

package com.etech7.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "Rooms")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rooms {
    @Id
    private String id;
    private String userId;
    private String roomName;
    private String roomIndustry;
    private String roomUserInput;
    @CreatedDate
    private Date timeStamp;
    private boolean deleted;
    
}

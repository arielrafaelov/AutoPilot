package com.etech7.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.etech7.dto.RoomAgents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "RoomMessage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomMessage {
    @Id
    private String id;
    private String roomId;
    private RoomAgents roomAgents;
    private RoomAgents summarizedRoomAgents;
    @CreatedDate
    private Date timeStamp;
    private boolean deleted;
    
}

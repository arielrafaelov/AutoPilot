package com.etech7.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "AuditTrails")
public class AuditTrails {
    @Id
    private String id;
    private String mspId;
    private String mspCustomDomain;
    private String action;  // Changed permission for user2
    private String userEmail;  // ID of the user who performed the action
    private String targetEmail;
    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime timestamp; 
    private String details;  // other relevant details
}

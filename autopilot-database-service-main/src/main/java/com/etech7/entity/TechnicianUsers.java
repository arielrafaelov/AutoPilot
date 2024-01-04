package com.etech7.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "TechnicanUsers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(def = "{'email': 1, 'mspId': 1}", name = "email_mspId_idx", unique = true)
public class TechnicianUsers {
	
    @Id
    private String id;
//    private String mspId;
    private String mspCustomDomain;
    private String roleId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private boolean deleted;
//    private String tierLevel;
    private int connectWiseTechnicanId;
    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private Date timeStamp;

}
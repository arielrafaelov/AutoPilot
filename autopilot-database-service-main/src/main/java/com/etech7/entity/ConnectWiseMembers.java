package com.etech7.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "ConnectWiseMembers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ConnectWiseMembers {
	
	@Id
	private String id;
//  private String mspId;
	private String mspCustomDomain;  
    private int connectWiseMembersId;
    private String identifier;
    private String firstName;
    private String lastName;
    private String primaryEmail;
    private String mobilePhone;
    private String officeEmail;
    private String officePhone;
    private String tier;
    
} 

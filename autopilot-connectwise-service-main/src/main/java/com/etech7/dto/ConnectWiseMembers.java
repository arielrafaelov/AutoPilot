package com.etech7.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ConnectWiseMembers {
	
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

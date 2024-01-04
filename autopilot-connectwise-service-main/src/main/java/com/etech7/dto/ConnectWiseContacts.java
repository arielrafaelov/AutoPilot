package com.etech7.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ConnectWiseContacts {


	private String id;
//    private String mspId;
    private String mspCustomDomain;  
    private int connectWiseContactId;
    private int connectWiseCompanyId;
    private ConnectWiseCompanyDAO company;
    private String firstName;
    private String lastName;
    private String title;
    private String defaultPhoneType;
    private String defaultPhoneNbr;    
    private String connectWiseEmailId;
    private String connectWisePhoneNumber;
    private List<ConnectWiseConfigurations> configuration;
    }

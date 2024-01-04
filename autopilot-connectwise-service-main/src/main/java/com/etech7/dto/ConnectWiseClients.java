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
public class ConnectWiseClients {


	private String id;
//    private String mspId;
	private String mspCustomDomain;
	private int connectWiseCompanyId;
	private String identifier;
	private String name;
	private ConnectWiseStatus status;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private ConnectWiseCountry country;
	private String phoneNumber;
	private ConnectWiseDefaultContact defaultContact;
	private List<ConnectWiseType> types;
	private ConnectWiseSite site;
	private MicrosoftGraphIntegration microsoftGraphIntegration;


}

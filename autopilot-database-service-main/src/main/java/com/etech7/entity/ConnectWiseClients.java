package com.etech7.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.etech7.dto.ConnectWiseCountry;
import com.etech7.dto.ConnectWiseDefaultContact;
import com.etech7.dto.ConnectWiseSite;
import com.etech7.dto.ConnectWiseStatus;
import com.etech7.dto.ConnectWiseType;
import com.etech7.dto.MicrosoftGraphIntegration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "ConnectWiseClients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectWiseClients {

	@Id
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

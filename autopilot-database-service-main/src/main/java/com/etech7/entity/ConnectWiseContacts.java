package com.etech7.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.etech7.dto.ConnectWiseCompanyDAO;
import com.etech7.dto.ConnectWiseConfigurations;
import com.etech7.dto.ConnectWiseManageConfigurationsDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "ConnectWiseContacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectWiseContacts {

	@Id
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

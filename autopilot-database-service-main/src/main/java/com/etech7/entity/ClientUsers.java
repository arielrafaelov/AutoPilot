package com.etech7.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.etech7.dto.Address;
import com.etech7.dto.ConnectWiseDetails;
import com.mongodb.lang.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "ClientUsers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(def = "{'email': 1, 'mspId': 1}", name = "email_mspId_idx", unique = true)
public class ClientUsers {
	
    @Id
    private String id;
    @NonNull
//    private String mspId;
    private String roleId;
    private String mspCustomDomain;
    private String connectWiseClientsAutopilotDbId;
    private String connectWiseContactsAutopilotDbId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String companyName;
    private Address companyAddress;
    private String email;
    private String password;
//    private ConnectWiseDetails connectWiseDetails; 
    private boolean deleted;  
    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private Date timeStamp;

}
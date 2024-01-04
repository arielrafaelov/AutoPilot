package com.etech7.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.etech7.dto.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "MSP")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MSP {

    @Id
    private String id;
    private String mspName;
    @Indexed(unique = true)
    private String customDomain;
    private String brandLogoUrl;
    private Address companyAddress;
    private boolean deleted;  
    @CreatedDate
    @Indexed(direction = IndexDirection.DESCENDING)
    private Date timeStamp;
}

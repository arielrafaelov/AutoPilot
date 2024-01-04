package com.etech7.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectWiseContactsCW {

    private int id;
    private String firstName;
    private String lastName;
    private ConnectWiseCompanyDAO company;
    private String title;
    private String defaultPhoneType;
    private String defaultPhoneNbr;
    private List<ConnectWiseCommunicationItems> communicationItems;
    private int connectWiseCompanyId;
    private String connectWiseEmailId;
    private String connectWisePhoneNumber;
    private List<ConnectWiseConfigurations> configuration;
}

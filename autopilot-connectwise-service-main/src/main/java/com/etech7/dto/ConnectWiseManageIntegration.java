package com.etech7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectWiseManageIntegration {
    private String clientId;
    private String companyId;
    private String publicKey;
    private String privateKey;
 
}
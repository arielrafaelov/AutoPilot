package com.etech7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectWiseAutomateIntegration {
	
	private String baseUrl;
    private String clientId;
    private String companyId;
    private String oneTimeSecurePassword;
 
}
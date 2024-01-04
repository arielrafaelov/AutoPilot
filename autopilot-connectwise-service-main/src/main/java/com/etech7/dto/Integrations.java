package com.etech7.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Integrations {
    private String id;
    private String mspCustomDomain;
	private boolean connectWiseManageIntegrator;
	private boolean connectWiseAutomateIntegrator;
	private boolean microsoftGraphIntegrator;
	private boolean emailIntegrator;
    private ConnectWiseManageIntegration connectWiseManageIntegration;
    private ConnectWiseAutomateIntegration connectWiseAutomateIntegration;
    private EmailConnectorIntegration emailConnectorIntegration;
    private MicrosoftGraphIntegration microsoftGraphIntegration;
}

package com.etech7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MSPIntegrations {
	
	 private boolean connectWiseManageIntegration;
	 private boolean connectWiseAutomateIntegration;
	 private boolean microsoftGraphIntegration;
	 private boolean emailIntegration;
	 private ConnectWiseWorkflowConfiguration connectWiseWorkflowConfiguration;

}
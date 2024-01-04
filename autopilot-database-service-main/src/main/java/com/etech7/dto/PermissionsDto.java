package com.etech7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PermissionsDto {

	private boolean clientBilling;
	private boolean mspBilling;
	private boolean clientUserManagement;
	private boolean technicianUserManagement;
	private boolean mspBranding;
	private boolean mspIntegrations;
	private boolean clientDocuments;
	private boolean mspDocuments;
	
}

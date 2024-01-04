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
public class ConnectWiseManageInitializationDto {

	private List<Priorities> prioritiesList;
	private List<MSPConnectWiseManageCategorizations> mspConnectWiseManageCategorizations;
	
}

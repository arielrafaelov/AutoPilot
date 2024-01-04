package com.etech7.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectWiseCommunicationItems {

	private int id;
	private ConnectWiseType type;
	private String value;
	private boolean defaultFlag;
	private String domain;
	private String communicationType;
}

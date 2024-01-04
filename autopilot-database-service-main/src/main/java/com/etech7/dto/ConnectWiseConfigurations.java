package com.etech7.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectWiseConfigurations {

	private int id;
    private String name;
    private String deviceIdentifier;
    private ConnectWiseSite site;
    private ConnectWiseLocation location;
}

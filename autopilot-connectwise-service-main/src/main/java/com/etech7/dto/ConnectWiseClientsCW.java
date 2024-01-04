package com.etech7.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectWiseClientsCW {


	private int id;
	private String identifier;
	private String name;
	private ConnectWiseStatus status;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zip;
	private ConnectWiseCountry country;
	private String phoneNumber;
	private ConnectWiseDefaultContact defaultContact;
	private List<ConnectWiseType> types;
	private ConnectWiseSite site;

}

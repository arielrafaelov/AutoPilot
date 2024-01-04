package com.etech7.dto;

import lombok.Data;

@Data
public class UserSignInRequest {

	
	private String emailId;
	private String password;
	private String newPassword;
}

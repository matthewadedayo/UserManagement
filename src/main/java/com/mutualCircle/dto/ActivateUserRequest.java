package com.mutualCircle.dto;

import lombok.Data;


@Data
public class ActivateUserRequest {
	
	
	private String emailAddress;
	private String password;
        private String activationCode;
	
}

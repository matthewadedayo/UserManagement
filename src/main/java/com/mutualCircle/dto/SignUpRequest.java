package com.mutualCircle.dto;

import lombok.Data;

@Data
public class SignUpRequest {

        private String userName;
        
	private String firstName;
	
	private String lastName;
	
	private String emailAddress;
        
        private String location;
	
	private String phoneNumber;
        
        private String gender;
	
	private String role;
	
}

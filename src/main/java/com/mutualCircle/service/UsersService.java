package com.mutualCircle.service;

import com.mutualCircle.dto.ActivateUserRequest;
import com.mutualCircle.dto.PasswordResetDto;
import com.mutualCircle.dto.ResendUserActivationCodeDto;
import com.mutualCircle.dto.ResendUserPasswordDto;
import com.mutualCircle.dto.ServerResponse;
import com.mutualCircle.dto.SignInRequest;
import com.mutualCircle.dto.SignUpRequest;
import com.mutualCircle.dto.UpdateUserRequestDto;
import com.mutualCircle.model.Users;
import java.util.Collection;

import org.springframework.stereotype.Service;




@Service
public interface UsersService {
	
	public Collection<Users> findAll();
	
	public Users findById(long usersId);
	
	public Users findByEmail(String emailAddress);
	
	public Users findByPhone(String phoneNumber);
	
	public Users findByEmailOrPhone(String emailAddress, String phoneNumber);

	public ServerResponse create(SignUpRequest request);
	
	//public ServerResponse userActivation(ActivateUserRequest request);
	
	public ServerResponse reSendUserActivation(ResendUserActivationCodeDto request);
	
	public ServerResponse reSendUserPassword(ResendUserPasswordDto request);
	
	public ServerResponse passwordReset(PasswordResetDto request);
	
	public ServerResponse updateUser(UpdateUserRequestDto request);
	
	public ServerResponse login(SignInRequest request);
	
	public ServerResponse viewAll();
	
	public ServerResponse delete(long usersId);
	

}

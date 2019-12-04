package com.mutualCircle.service.impl;


import com.mutualCircle.constant.AppConstants;
import com.mutualCircle.constant.ServerResponseStatus;
import com.mutualCircle.dto.ActivateUserRequest;
import com.mutualCircle.dto.PasswordResetDto;
import com.mutualCircle.dto.ResendUserActivationCodeDto;
import com.mutualCircle.dto.ResendUserPasswordDto;
import com.mutualCircle.dto.ServerResponse;
import com.mutualCircle.dto.SignInRequest;
import com.mutualCircle.dto.SignUpRequest;
import com.mutualCircle.dto.UpdateUserRequestDto;
//import com.mutualCircle.mail.EmailService;
import com.mutualCircle.mail.Mail;
import com.mutualCircle.model.ConfirmationToken;
import com.mutualCircle.model.Role;
import com.mutualCircle.model.Users;
import com.mutualCircle.repository.ConfirmationTokenRepository;
import com.mutualCircle.repository.RoleRepository;
import com.mutualCircle.repository.UsersRepository;
import com.mutualCircle.service.UsersService;
import com.mutualCircle.utility.Utility;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class UsersServiceImpl implements UsersService{
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	AppConstants appConstants;
	
        //@Autowired
        //EmailService emailService;  
        
        @Autowired
        private ConfirmationTokenRepository confirmationTokenRepository;

        @Autowired
        private EmailSenderService emailSenderService;
		

    
    Utility utility = new Utility();
	
    private static Logger logger = LogManager.getLogger(UsersServiceImpl.class);
	
	@Override
	public Collection<Users> findAll() {
		
		try {
			return (Collection<Users>) usersRepository.findAll();
					
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return null;
	}

	@Override
	public Users findById(long usersId) {
		
		try {
			return usersRepository.findByUsersId(usersId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Users findByEmail(String emailAddress) {
		
		try {
			return usersRepository.findByEmailAddress(emailAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Users findByPhone(String phoneNumber) {
		
		try {
			return usersRepository.findByPhoneNumber(phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	@Override
	public Users findByEmailOrPhone(String emailAddress, String phoneNumber) {
		
		try {
			return usersRepository.findByPhoneNumberOrEmailAddress(phoneNumber, emailAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


/************************************************************************************************************
 *                            USER ACCOUNT CREATION
 ***********************************************************************************************************/
	
	@Override
	public ServerResponse create(SignUpRequest request) {
	
       ServerResponse response = new ServerResponse();
		
		Users user = null;
		
                String emailAddress = request.getEmailAddress() != null ? request.getEmailAddress() : request.getEmailAddress();
		String userName = request.getUserName() != null ? request.getUserName() : request.getUserName();
		String phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : request.getPhoneNumber();;
		String firstName = request.getFirstName() != null ? request.getFirstName() : request.getFirstName();
                String location = request.getLocation() != null ? request.getLocation() : request.getLocation();
                String gender = request.getGender() != null ? request.getGender() : request.getGender();
		String lastName = request.getLastName() != null ? request.getLastName() : request.getLastName();
		
                
		if (emailAddress != null && !Utility.isValidEmail(emailAddress)) {
			
			response.setData("");
            response.setMessage("Please enter valid email address");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		if (phoneNumber == null || !Utility.isValidPhone(phoneNumber)) {
			response.setData("");
            response.setMessage("Please enter valid phone number");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		if (firstName == null || firstName.isEmpty()) {
			response.setData("");
            response.setMessage("Please enter first name");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
                
                if (userName == null || !userName.isEmpty()){
		} else {
                    response.setData("");
                    response.setMessage("Please enter your username");
                    response.setSuccess(false);
                    response.setStatus(ServerResponseStatus.FAILED);
                    
                    return response;
            }
                
		
		if (lastName == null || lastName.isEmpty()) {
			response.setData("");
            response.setMessage("Please enter last name");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
                
                 if (location == null || !location.isEmpty()){
		} else {
                    response.setData("");
                    response.setMessage("Please enter your location");
                    response.setSuccess(false);
                    response.setStatus(ServerResponseStatus.FAILED);
                    
                    return response;
            }
                 
                  if (gender == null || !gender.isEmpty()){
		} else {
                    response.setData("");
                    response.setMessage("Please select your gender");
                    response.setSuccess(false);
                    response.setStatus(ServerResponseStatus.FAILED);
                    
                    return response;
            }
		
		if (request.getRole() == null || request.getRole().isEmpty()) {
			response.setData("");
            response.setMessage("Please enter role");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		try {
			
			Users requestUsersEmail = usersRepository.findByEmailAddress(emailAddress);
					
			
			if (requestUsersEmail != null) {
				response.setData("");
                response.setMessage("Email already exist");
                response.setSuccess(false);
                response.setStatus(ServerResponseStatus.FAILED);

                return response;
			}
			
			Users requestUser = usersRepository.findByPhoneNumberOrEmailAddress(phoneNumber, emailAddress);
			
			if (requestUser != null) {
				response.setData("");
                response.setMessage("User email or phone number already exist");
                response.setSuccess(false);
                response.setStatus(ServerResponseStatus.FAILED);

                return response;
			}
			
			user = new Users();
						
			Role role = roleRepository.findByName(request.getRole());
			
			if(role == null){
				response.setData("");
                response.setMessage("Invalid user role");
                response.setSuccess(false);
                response.setStatus(ServerResponseStatus.FAILED);

                return response;
			}
		
			String activationCode = String.valueOf(Utility.generateActivationCode());

			user.setRole(role);
                        user.setUserName(userName);
			user.setEmailAddress(emailAddress);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setPhoneNumber(phoneNumber);
                        user.setLocation(location);
                        user.setGender(gender);
			user.setDateCreated(new Date());
			user.setActive(false);
			user.setActivationCode(activationCode);
		
			usersRepository.save(user);
                        
                        ConfirmationToken confirmationToken = new ConfirmationToken(user);
            
           /* Mail mail = new Mail();
            mail.setTo(user.getEmailAddress());
            mail.setFrom("info@codetouch.com");
            mail.setSubject("Account verification");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("name", firstName);
            model.put("code", activationCode);
            mail.setModel(model);
            mail.setTemplate("account_verification_email_template.ftl");
            emailService.sendSimpleMessage(mail);*/
           
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmailAddress());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("info@codetouch.com");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:9191/verification?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            
	        response.setData(user);
            response.setMessage("User successfully created");
            response.setData(activationCode);
            response.setSuccess(true);
            response.setStatus(ServerResponseStatus.OK);
            

		} catch (Exception e) {
		  response.setData("");
          response.setMessage("Failed to create user account");
          response.setSuccess(false);
          response.setStatus(ServerResponseStatus.FAILED);

          logger.error("An error occured while creating recipient account");
          e.printStackTrace();
		}
		return response;
		
	}

                         

     @Override
      public ServerResponse reSendUserActivation(ResendUserActivationCodeDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			String emailAddressOrPhoneNumber = request.getEmailAddress() != null ? request.getEmailAddress() : request.getEmailAddress();

 			Users user = findByEmail(emailAddressOrPhoneNumber);
 			
 			if (user == null) {
 				
 				response.setData("");
 		        response.setMessage("User email address not found");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseStatus.FAILED);
 				
 				return response;
 			}
 		
 			String activationCode = String.valueOf(Utility.generateActivationCode());

 			user.setActivationCode(activationCode);
 			
 			Mail mail = new Mail();
                        mail.setTo(user.getEmailAddress());
                        mail.setFrom("info@codetouch.com");
                        mail.setSubject("Account verification");

                        Map<String, Object> model = new HashMap<String, Object>();
                        model.put("name", user.getFirstName());
                        model.put("code", activationCode);
                        mail.setModel(model);
                        mail.setTemplate("account_verification_email_template.ftl");
                     // emailService.sendSimpleMessage(mail);
 			
 			response.setData("");
 	        response.setMessage("Activation code sent successfully");
 	        response.setSuccess(true);
 	        response.setData(activationCode);
 	        response.setStatus(ServerResponseStatus.OK);
 			
 		} catch (Exception e) {
 			
 			response.setData("");
 	        response.setMessage("Failed to create user account");
 	        response.setSuccess(false);
 	        response.setStatus(ServerResponseStatus.FAILED);
 	          
 			e.printStackTrace();
 		}
		return response;
 		
     }

     @Override
      public ServerResponse reSendUserPassword(ResendUserPasswordDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			String emailAddress = request.getEmailAddress() != null ? request.getEmailAddress() : request.getEmailAddress();

 			Users user = findByEmail(emailAddress);
 			
 			if (user == null) {
 				
 				response.setData("");
 		        response.setMessage("User not found");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseStatus.FAILED);
 				
 				return response;
 			}
 	        
 			String passwordResetCode = String.valueOf(Utility.generateActivationCode());

 			user.setPasswordResetCode(passwordResetCode);
 			
 			response.setData("password reset code sent successfully");
 	        response.setMessage("password reset code sent successfully");
 	        response.setSuccess(true);
 	        response.setData(passwordResetCode);
 	        response.setStatus(ServerResponseStatus.OK);
 			
 		} catch (Exception e) {
 			
 			response.setData("");
 	        response.setMessage("Failed to send user password reset code");
 	        response.setSuccess(false);
 	        response.setStatus(ServerResponseStatus.FAILED);
 	          
 			e.printStackTrace();
 		}
 		return response;
         }

     @Override
      public ServerResponse passwordReset(PasswordResetDto request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			String passwordResetCode = request.getResetCode() != null ? request.getResetCode() : request.getResetCode();
 			String password = request.getPassword() != null ? request.getPassword() : request.getPassword();

 			Users user = usersRepository.findByPasswordResetCode(passwordResetCode);

 			if (user == null) {
 				
 				response.setData("");
 		        response.setMessage("Invalid password reset Code");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseStatus.FAILED);
 				
 				return response;
 			}
 			
 			user.setPassword(passwordEncoder.encode(password));
 			user.setPasswordResetCode(null);
 			
 			response.setData("");
 	        response.setMessage("User password successfully changed");
 	        response.setSuccess(true);
 	        response.setData(password);
 	        response.setStatus(ServerResponseStatus.OK);
 			
 		} catch (Exception e) {
 			
 			response.setData("");
 	        response.setMessage("Failed to reset user password");
 	        response.setSuccess(false);
 	        response.setStatus(ServerResponseStatus.FAILED);
 	          
 			e.printStackTrace();
 		}
 		return response;
        }

     @Override
     public ServerResponse login(SignInRequest request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		try {
 			
 			logger.info(request.getUsername());
 			
 			//convert client id and client secret to base64 token 
 			String authorization = Utility.getCredentials(appConstants.CLIENT_ID, appConstants.CLIENT_SECRET);
 			logger.info(authorization);
 			request.setGrant_type(appConstants.GRANT_TYPE);
 			
 			//send login request
 			response = Utility.loginHttpRequest( appConstants.APP_LOGIN_URL, request, authorization);
 			
 		} catch (Exception e) {
 			response.setData("Something went wrong !!!" + e.getMessage());
 			response.setMessage("User authentication failed");
 			response.setSuccess(false);
 			response.setStatus(ServerResponseStatus.FAILED);
 			
 			return response;
 		}
 		
 		return response;
      }

    /* @Override
     public ServerResponse userActivation(ActivateUserRequest request) {
    	 
    	 ServerResponse response = new ServerResponse();
 		
 		try {
 			
                        
 			String activationCode = request.getActivationCode() != null ? request.getActivationCode() : request.getActivationCode();
 			String password = request.getPassword() != null ? request.getPassword() : request.getPassword();
 			
 			Users users = usersRepository.findByActivationCode(activationCode);
                        String useMail = users.getEmailAddress();
          

 			if (users == null && useMail.equals(useMail)) {
 				
 				response.setData("");
 		        response.setMessage("Invalid activation Code");
 		        response.setSuccess(false);
 		        response.setStatus(ServerResponseStatus.FAILED);
 				
 				return response;
 			}

 			
 			users.setPassword(passwordEncoder.encode(password));
 			users.setActive(true);
 			users.setActivationCode(activationCode);
             
 			
 			response.setData("User successfully activated");
 	        response.setMessage("User successfully activated");
 	        response.setSuccess(true);
 	        response.setStatus(ServerResponseStatus.OK);
 			
 		} catch (Exception e) {
 			
 			response.setData("Something went wrong" + e.getMessage());
 	        response.setMessage("Failed to create user account");
 	        response.setSuccess(false);
 	        response.setStatus(ServerResponseStatus.FAILED);
 	          
 			e.printStackTrace();
 		}
 		return response;
      }*/

     
     
	   @Override
	    public ServerResponse viewAll() {
		
		ServerResponse response = new ServerResponse();
		
		Collection<Users> users = null;
		
		try {
			
			users = findAll();
			
			if(users == null) {
				response.setData("No user available");	
				response.setStatus(ServerResponseStatus.NO_CONTENT);
				
				return response;
			}
			
			response.setData(users);
			response.setStatus(ServerResponseStatus.OK);
			response.setSuccess(true);
			response.setMessage("Get users successfully");
			
		} catch (Exception e) {
			
			response.setData("Failed to fetch users" + e.getMessage());
			response.setSuccess(false);
			response.setMessage("Failed to fetch users");
			response.setStatus(ServerResponseStatus.FAILED);
			e.printStackTrace();
		}
		
		return response;
	}

	   
	@Override
	public ServerResponse updateUser(UpdateUserRequestDto request) {
		
		ServerResponse response = new ServerResponse();
		Users user = null;
		
		String emailAddress = request.getEmailAddress() != null ? request.getEmailAddress() : request.getEmailAddress();
		//String username = request.getUserName() != null ? request.getUserName() : request.getUserName();
		String phoneNumber = request.getPhoneNumber() != null ? request.getPhoneNumber() : request.getPhoneNumber();;
		String firstName = request.getFirstName() != null ? request.getFirstName() : request.getFirstName();
               // String location = request.getLocation() != null ? request.getLocation() : request.getLocation();
               // String gender = request.getGender() != null ? request.getGender() : request.getGender();
		String lastName = request.getLastName() != null ? request.getLastName() : request.getLastName();
		
		
		if (emailAddress != null && !Utility.isValidEmail(emailAddress)) {
			
			response.setData("");
            response.setMessage("Email address not found");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		if (phoneNumber == null || !Utility.isValidPhone(phoneNumber)) {
			response.setData("");
            response.setMessage("phone number not found");
            response.setSuccess(false);
            response.setStatus(ServerResponseStatus.FAILED);

            return response;
		}
		
		try {
			 
			Users userUpdate = findByEmail(request.getEmailAddress());
			
			if(userUpdate == null) {
				response.setData("User not found");
				response.setStatus(ServerResponseStatus.FAILED);
				response.setSuccess(false);
				
				return response;
			}
			
			  user = usersRepository.findByEmailAddress(emailAddress);
			
			  if (firstName != null) 
				  user.setFirstName(firstName);
                          // if (username != null) 
			//	  user.setUserName(username);
			   if (lastName != null) 
				   user.setLastName(lastName);
			   if (phoneNumber != null) 
				   user.setPhoneNumber(phoneNumber);
                          // if (gender != null) 
				//   user.setGender(gender);
                            //if (location != null) 
				//   user.setLocation(location);
			   if (emailAddress != null)
				   user.setEmailAddress(emailAddress);
			   
			   usersRepository.save(user);
			   
			   response.setData(user);
			   response.setStatus(ServerResponseStatus.UPDATED);
			   response.setSuccess(true);
			   response.setMessage("User successfully updated");
			
		} catch (Exception e) {
			response.setData("Failed to update user details" + e.getMessage());
			response.setMessage("Failed to update user details");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.FAILED);
			e.printStackTrace();
			
		}
		
		return response;
	}

	@Override
	public ServerResponse delete(long usersId) {
	
      ServerResponse response = new ServerResponse();
		
		if (usersId == 0) {
			response.setData("code can not be null");
			response.setStatus(ServerResponseStatus.FAILED);
			response.setSuccess(false);
				
			return response;
		}
		
		try {
			
			Users user = usersRepository.findByUsersId(usersId);
			
			
			if (user == null) {
				response.setData("User not found");
				response.setStatus(ServerResponseStatus.FAILED);
				response.setSuccess(false);
				
				return response;
			}
			
			usersRepository.delete(user);
			
			 response.setStatus(ServerResponseStatus.DELETED);
			 response.setData("User account has been successfully deleted");
			 response.setSuccess(true);

	        } catch (Exception e) {
	        	response.setStatus(ServerResponseStatus.FAILED);
	        	response.setData("Failed to delete user account");
	        	response.setSuccess(false);
	            e.printStackTrace();
	        }
		
		return response;
		
	}
		
	
}

package com.mutualCircle.repository;

import com.mutualCircle.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface UsersRepository extends CrudRepository<Users, Long>{
	
	public Users findByUsersId(long usersId);

	public Users findByEmailAddress(String emailAddress);
	
	public Users findByPhoneNumber(String phoneNumber);
	
	public Users findByPhoneNumberOrEmailAddress(String emailAddress, String phoneNumber);
	
	public Users findByActivationCode(String activationCode);
	
	public Users findByPasswordResetCode(String passwordResetCode);
	
	 //public Users findByEmailIdIgnoreCase(String emailAddress);
}

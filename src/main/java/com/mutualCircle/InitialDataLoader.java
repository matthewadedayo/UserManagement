package com.mutualCircle;

import com.mutualCircle.constant.AppConstants;
import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import com.mutualCircle.model.Privilege;
import com.mutualCircle.model.Role;
import com.mutualCircle.model.Users;
import com.mutualCircle.repository.PrivilegeRepository;
import com.mutualCircle.repository.RoleRepository;
import com.mutualCircle.repository.UsersRepository;
import com.mutualCircle.service.PrivilegeService;
import com.mutualCircle.service.RoleService;
import com.mutualCircle.service.UsersService;



@Transactional
@Component
public class InitialDataLoader  implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private AppConstants appConstants;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private UsersService usersService;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleRepository roleRepository;

	
	private boolean hasBeenSetup;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	private static Logger logger = LogManager.getLogger(InitialDataLoader.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (hasBeenSetup) {
			return;
		}

		createPrivilegesIfNotFound();
		createRolesIfNotFound();
		createSuperAdminAccountIfNotFound();
		
		
		hasBeenSetup = true;
	}

	private Privilege createPrivilegesIfNotFound() {

		Privilege privileges;
		Privilege create = privilegeService.findByName("CREATE");
		Privilege upadte = privilegeService.findByName("UPDATE");
		Privilege delete = privilegeService.findByName("DELETE");
		Privilege read = privilegeService.findByName("READ");

		if (create == null) {
			privileges = new Privilege();
			privileges.setName("CREATE");
			privilegeRepository.save(privileges);
		}

		if (upadte == null) {
			privileges = new Privilege();
			privileges.setName("UPDATE");
			privilegeRepository.save(privileges);
		}
		if (delete == null) {
			privileges = new Privilege();
			privileges.setName("DELETE");
			privilegeRepository.save(privileges);
		}

		if (read == null) {
			privileges = new Privilege();
			privileges.setName("READ");
			privilegeRepository.save(privileges);
		}

		

		return null;
	}

	private void createRolesIfNotFound() {

		Role superAdminRole = roleService.findByName("SUPER_ADMIN");
		Role adminRole = roleService.findByName("ADMIN");
		Role userRole = roleService.findByName("USER");
		Role storeOfficerRole = roleService.findByName("STORE_OFFICER");
		
		Privilege create = privilegeService.findByName("CREATE");
		Privilege update = privilegeService.findByName("UPDATE");
		Privilege delete = privilegeService.findByName("DELETE");
		Privilege read = privilegeService.findByName("READ");
		
		Collection<Privilege> SuperAdminPrivileges = new HashSet<>();
		SuperAdminPrivileges.add(create);
		SuperAdminPrivileges.add(update);
		SuperAdminPrivileges.add(delete);
		SuperAdminPrivileges.add(read);
		
		Collection<Privilege> adminPrivileges = new HashSet<>();
		adminPrivileges.add(create);
		//adminPrivileges.add(update);
		adminPrivileges.add(read);
		
		Collection<Privilege> userPrivileges = new HashSet<>();
		//userPrivileges.add(update);
		userPrivileges.add(read);
		
		Collection<Privilege> storeOfficerPrivileges = new HashSet<>();
	//	storeOfficerPrivileges.add(update);
		storeOfficerPrivileges.add(read);
		
		if (superAdminRole == null) {
			superAdminRole = new Role();
			superAdminRole.setName("SUPER_ADMIN");
			superAdminRole.setPrivileges(SuperAdminPrivileges);
			roleRepository.save(superAdminRole);
		}

		if (userRole == null) {
			userRole = new Role();
			userRole.setName("USER");
			userRole.setPrivileges(userPrivileges);
			roleRepository.save(userRole);
		}
		
		if (adminRole == null) {
			adminRole = new Role();
			adminRole.setName("ADMIN");
			adminRole.setPrivileges(adminPrivileges);
			roleRepository.save(adminRole);
		}
		
		
		if (storeOfficerRole == null) {
			storeOfficerRole = new Role();
			storeOfficerRole.setName("STORE_OFFICER");
			storeOfficerRole.setPrivileges(storeOfficerPrivileges);
			roleRepository.save(storeOfficerRole);
		}

		
	}

	private Users createSuperAdminAccountIfNotFound() {

		Users users = usersService.findByEmail(appConstants.APP_ADMIN_EMAIL);
		Role superAdmin = roleRepository.findByName("SUPER_ADMIN");
		
		logger.info("Starting to create super-admin account ");

		if (users != null) {
			return null;
		}

		Users user = new Users();
		user.setActive(true);
		user.setEmailAddress(appConstants.APP_ADMIN_EMAIL);
		user.setPhoneNumber(appConstants.APP_DEFAULT_ADMIN_PHONE);
		user.setFirstName(appConstants.APP_DEFAULT_ADMIN_NAME);
		user.setLastName(appConstants.APP_DEFAULT_ADMIN_NAME);
                user.setUserName(appConstants.APP_USERNAME);
                user.setLocation(appConstants.APP_LOCATION);
		user.setPassword(passwordEncoder.encode(appConstants.APP_ADMIN_PASSWORD));
		user.setRole(superAdmin);
//		user.setUserName(appConstants.APP_DEFAULT_ADMIN_NAME);

		logger.info("Admin Account " + user.getEmailAddress());
		
		Users findEmail = usersRepository.findByEmailAddress(user.getEmailAddress());

		if (findEmail != null) {
			return null;
		}

		usersRepository.save(user);

		return user;
	}

	


}

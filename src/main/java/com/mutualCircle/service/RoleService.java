package com.mutualCircle.service;

import com.mutualCircle.dto.ServerResponse;
import com.mutualCircle.model.Role;
import java.util.Collection;

import org.springframework.stereotype.Service;



@Service
public interface RoleService {
	
	 public Role findById(long id);

	    public Role findByName(String name);

	    public Collection<Role> getRoles();
	    
	    public ServerResponse findAllRole();

}

package com.mutualCircle.service;

import com.mutualCircle.dto.ServerResponse;
import com.mutualCircle.model.Privilege;
import java.util.Collection;

import org.springframework.stereotype.Service;




@Service
public interface PrivilegeService {
	
	 	public Privilege findById(long id);

	    public Privilege findByName(String name);

	    public ServerResponse update(Privilege privilege);

	    public ServerResponse create(Privilege privilege);

	    public ServerResponse delete(Privilege role);

	    public ServerResponse getPrivileges();
	    
	    public ServerResponse getPrivilegesByRole(String name);

	    public Collection<Privilege> findAll();
	    
	 

}

package com.mutualCircle.service.impl;

import com.mutualCircle.constant.ServerResponseStatus;
import com.mutualCircle.dto.ServerResponse;
import com.mutualCircle.model.Privilege;
import com.mutualCircle.repository.PrivilegeRepository;
import com.mutualCircle.service.PrivilegeService;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Transactional
@Service
public class PrivilegeServiceImpl implements PrivilegeService{
	
	@Autowired
	private PrivilegeRepository privilegeRepository;

	
	@Override
	public Privilege findById(long id) {
		
		try {
			return privilegeRepository.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	@Override
	public Collection<Privilege> findAll() {
		
		try {
			privilegeRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Privilege findByName(String name) {
		
		try {
			return privilegeRepository.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return null;
	}
	

	@Override
	public ServerResponse update(Privilege privilege) {
		
		ServerResponse response = new ServerResponse();
				
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ServerResponse create(Privilege privilege) {
		
		ServerResponse response = new ServerResponse();
		
	
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public ServerResponse delete(Privilege role) {
		
		ServerResponse response = new ServerResponse();
		
		try {
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return response;
	}


	@Override
	public ServerResponse getPrivileges() {
	ServerResponse response = new ServerResponse();
		
		try {
			
			Collection<Privilege> privileges = (Collection<Privilege>) privilegeRepository.findAll();
			
			if (privileges.size() < 1) {
				response.setData("");
				response.setMessage("No privilege found");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.OK);
			}
			
			response.setData(privileges);
			response.setMessage("Privileges fetched successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.OK);
			
		} catch (Exception e) {
			response.setData("Something went wrong" + e.getMessage());
			response.setMessage("Something went wrong");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return response;
	}

	@Override
	public ServerResponse getPrivilegesByRole(String name) {
		ServerResponse response = new ServerResponse();
		
		try {
			
			Collection<Privilege> privileges =  privilegeRepository.findByRoles_name(name); 
			
			if (privileges.size() < 1) {
				response.setData("");
				response.setMessage("No privilege found");
				response.setSuccess(false);
				response.setStatus(ServerResponseStatus.OK);
			}
			
			response.setData(privileges);
			response.setMessage("Get data successfully");
			response.setSuccess(true);
			response.setStatus(ServerResponseStatus.OK);
			
		} catch (Exception e) {
			response.setData("Something went wrong" + e.getMessage());
			response.setMessage("Something went wrong");
			response.setSuccess(false);
			response.setStatus(ServerResponseStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		
		return response;
	}

	

}

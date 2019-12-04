package com.mutualCircle.repository;

import com.mutualCircle.model.Privilege;
import com.mutualCircle.model.Role;
import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long>{
	
	public Privilege findById(long id);

	public Privilege findByName(String name);
	
	public  Collection<Privilege> findAllByRoles(Role role);
	
	public Collection<Privilege> findByRoles_name(String name);
	

}

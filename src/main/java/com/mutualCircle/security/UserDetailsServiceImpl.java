package com.mutualCircle.security;

import com.mutualCircle.model.Privilege;
import com.mutualCircle.model.Role;
import com.mutualCircle.model.Users;
import com.mutualCircle.service.UsersService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserDetailsServiceImpl extends Users implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    private static Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
    
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {

        logger.info("NOW STARTING AUTHENTICATION AT USER DETAILS SERVICE");

        Users users = usersService.findByEmail(emailAddress);
        
        if(users == null){
        	throw new UsernameNotFoundException("user does not exists");
        }
        
        logger.info("DONE WITH AUTHENTICATION AT USER DETAILS SERVICE" + users.getLastName());

        return new org.springframework.security.core.userdetails.User(emailAddress, users.getPassword(), getAuthorities(users.getRole()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return getGrantedAuthorities(getPrivileges(role));
    }

    private List<String> getPrivileges(Role role) {
        List<String> privileges = new ArrayList<String>();
        List<Privilege> collection = new ArrayList<Privilege>();
        collection.addAll(role.getPrivileges());

        for (Privilege item : collection) {
            privileges.add(item.getName().toString());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
package com.mutualCircle.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, unique = true)
	private long usersId;
        
        @Column(name = "username")
	private String userName;
	
	@Column(name = "firstname")
	private String firstName;
	
	@Column(name = "lastname")
	private String lastName;
        
        @Column(name = "location")
	private String location;
	
	@Column(name = "email", unique=true)
	private String emailAddress;
        
        @Column(name = "gender")
	private String gender;
	
	@Column(name = "phone")
	private String phoneNumber;
	
	@JsonIgnore
	@Column(name = "password")
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
        private Role role;
	    
	//@JsonIgnore
	@Column(name = "active")
	private boolean active;
	    
        @JsonIgnore
        @Column(name = "date_created", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
        @Temporal(TemporalType.TIMESTAMP)
        private Date dateCreated;

        // @JsonIgnore
        @Column(name = "activation_code")
	private String activationCode;
		
	@JsonIgnore
	@Column(name = "password_reset_code")
	private String passwordResetCode;
	

	
}

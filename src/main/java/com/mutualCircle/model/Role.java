package com.mutualCircle.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "role")
public class Role implements Serializable {
	
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", nullable = false, unique = true)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@JsonIgnore
        @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private Collection<Users> users;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "role_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"), 
                inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"))
	private Collection<Privilege> privileges;

	
		
	
}

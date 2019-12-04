package com.mutualCircle.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
@Table(name = "privilege")
public class Privilege implements Serializable {
	
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "privilege_id", nullable = false, unique = true)
	private long id; 
	
	@Column(name = "name")
	private String name;
	
	@JsonIgnore
        @LazyCollection(LazyCollectionOption.FALSE)
        @ManyToMany(mappedBy = "privileges")
	private Collection<Role> roles;

	

	
}

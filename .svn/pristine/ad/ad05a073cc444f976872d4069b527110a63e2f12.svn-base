package com.liyang.domain.authority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Administrator
 *
 */
@Entity
public class Authority {
	
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	private int id ;
	private String name ;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Authority(String name) {
		super();
		this.name = name;
	} 
	
	public Authority(){		
	}
}

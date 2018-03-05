package com.liyang.domain.submitproposal;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 投保人图像
 * @author Administrator
 *
 */
@Entity
@Table(name="toubao_person_image")
public class SubProParImaJsoToubaoPersonImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="toubaoPersonImage")
	private Set<ImagesInfo> images;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ImagesInfo> getImages() {
		return images;
	}

	public void setImages(Set<ImagesInfo> images) {
		this.images = images;
	}

}

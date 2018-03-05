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
 * 投保图像
 * @author Administrator
 *
 */
@Entity
@Table(name="toubao_image")
public class SubProParImaJsoToubaoImage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="toubaoImage")
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

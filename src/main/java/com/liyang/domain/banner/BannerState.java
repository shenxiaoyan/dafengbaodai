package com.liyang.domain.banner;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;
/**
 * banner状态
 * @author Administrator
 *
 */
@Entity
@Table(name="banner_state")
@Cacheable
public class BannerState extends AbstractAuditorState<Banner,BannerAct>{
	public BannerState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
		// TODO Auto-generated constructor stub
	}

	public BannerState() {
		super();
	}
}

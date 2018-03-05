package com.liyang.domain.webadvertisetype;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;

@Entity
@Table(name="web_advertise_type_state")
public class WebAdvertiseTypeState extends AbstractAuditorState<WebAdvertiseType, WebAdvertiseTypeAct>{

	private static final long serialVersionUID = -5862099046801711483L;
	
	public WebAdvertiseTypeState(String lable, Integer sort, String stateCode) {
		super(lable, sort, stateCode);
	}
	
	public WebAdvertiseTypeState() {
		super();
	}
	
}

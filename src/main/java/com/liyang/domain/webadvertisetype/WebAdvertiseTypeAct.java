package com.liyang.domain.webadvertisetype;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;

@Entity
@Table(name="web_advertise_type_act")
public class WebAdvertiseTypeAct extends AbstractAuditorAct<WebAdvertiseTypeState>{
	
	private static final long serialVersionUID = -1628707707492862528L;

	public WebAdvertiseTypeAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
	}
	
	public WebAdvertiseTypeAct() {
		super();
	}
}

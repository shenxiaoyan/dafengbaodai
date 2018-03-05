package com.liyang.domain.otherInsure;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractWorkflowAct;

/**
 * 其他保险act
 * @author Administrator
 *
 */
@Entity
@Table(name="other_insure_act")
public class OtherInsureAct  extends AbstractAuditorAct<OtherInsureState>{
	
	public OtherInsureAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	
	public OtherInsureAct(){
		super();
	}
}

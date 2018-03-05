package com.liyang.domain.timgroup;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "tim_group_act")
public class TIMGroupAct extends AbstractAuditorAct<TIMGroupState> {

	public TIMGroupAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
		// TODO Auto-generated constructor stub
	}
	public TIMGroupAct(){
		super();
	}

}

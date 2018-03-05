package com.liyang.domain.productcompany;

import javax.persistence.Entity;

import com.liyang.domain.base.AbstractAuditorAct;

/** 
* @author Adam 
* @version 创建时间：2018年1月30日 下午5:38:30 
* 类说明 
*/
@Entity
public class ProductCompanyAct extends AbstractAuditorAct<ProductCompanyState>{

	private static final long serialVersionUID = 1L;
	
	public ProductCompanyAct() {
		super();
	}
	
	public ProductCompanyAct(String label, String actCode, Integer sort, ActGroup actGroup) {
		super(label, actCode, sort, actGroup);
	}
	
	
}

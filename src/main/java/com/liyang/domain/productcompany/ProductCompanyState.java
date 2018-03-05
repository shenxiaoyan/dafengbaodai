package com.liyang.domain.productcompany;

import javax.persistence.Entity;

import com.liyang.domain.base.AbstractAuditorState;

/** 
* @author Adam 
* @version 创建时间：2018年1月30日 下午5:37:26 
* 类说明 
*/
@Entity
public class ProductCompanyState extends AbstractAuditorState<ProductCompany, ProductCompanyAct>{

	private static final long serialVersionUID = 1L;
	
	public ProductCompanyState() {
		super();
	}

	public ProductCompanyState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
	}
}

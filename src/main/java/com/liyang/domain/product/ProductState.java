package com.liyang.domain.product;

import javax.persistence.Entity;

import com.liyang.domain.base.AbstractAuditorState;

/** 
* @author Adam 
* @version 创建时间：2018年1月30日 下午6:04:59 
* 类说明 
*/
@Entity
public class ProductState extends AbstractAuditorState<Product, ProductAct>{

	private static final long serialVersionUID = 1L;
	
	public ProductState() {
		super();
	}
	
	public ProductState(String label, Integer sort, String stateCode) {
		super(label, sort, stateCode);
	}

}

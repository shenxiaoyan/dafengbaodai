package com.liyang.client;

import com.liyang.client.tianan.CreateEnquiryJsonParseAdapterTiananImpl;
import com.liyang.client.tianan.enums.ApiSupplierEnums;
import com.liyang.client.xiaoma.CreateEnquiryJsonParseAdapterXiaomaImpl;
import com.liyang.domain.createenquiry.CreateEnquiry;

/**
 * 创建
 * CreateEntityJsonParseAdapter的工厂，根据不同的ApiSupplier生成不同的适配器来解析CreateEntity中的json字符串
 * 
 * @author huanghengkun
 * @create 2017年12月1日
 */
public class CreateEnquiryJsonParseFactory {
	CreateEnquiry createEntity;

	public CreateEnquiryJsonParseFactory(CreateEnquiry createEntity) {
		this.createEntity = createEntity;
	}

	/**
	 * 根据条件创建对应的适配器类
	 * 
	 * @return
	 */
	public ICreateEnquiryJsonParseAdapter createAdapter() {
		ICreateEnquiryJsonParseAdapter result = null;

		if (this.createEntity == null) {
			return result;
		} else if (ApiSupplierEnums.TIANAN.equals(this.createEntity.getApiSupplier())) {
			return new CreateEnquiryJsonParseAdapterTiananImpl(createEntity);
		} else if (ApiSupplierEnums.XIAOMA.equals(this.createEntity.getApiSupplier())) {
			return new CreateEnquiryJsonParseAdapterXiaomaImpl(createEntity);
		}
		//默认为小马接口
		if (result == null) {
			result = new CreateEnquiryJsonParseAdapterXiaomaImpl(createEntity);
		}
		return result;
	}

}

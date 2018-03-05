package com.liyang.client.xiaoma;

import com.liyang.client.ICreateEnquiryJsonParseAdapter;
import com.liyang.domain.createenquiry.CreateEnquiry;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class CreateEnquiryJsonParseAdapterXiaomaImpl implements ICreateEnquiryJsonParseAdapter {

	JSONObject params;

	public CreateEnquiryJsonParseAdapterXiaomaImpl(CreateEnquiry createEntity) {
		params = createEntity.getCreateEnquiryParams();
	}

	@Override
	public Object getLicenseNumber() {
		if (params != null) {
			return params.get("licenseNumber");
		}
		return null;

	}

	@Override
	public Object getOwnerName() {
		if (params != null) {
			return params.get("ownerName");
		}
		return null;
	}

	@Override
	public Object getInsurancesListSchemeName() {
		if (params != null) {
			return params.getJSONArray("insurancesList").getJSONObject(0).get("schemeName");
		}
		return null;
	}

	@Override
	public Object getCityName() {
		if (params != null) {
			return params.get("cityName");
		}
		return null;
	}

	@Override
	public Object getForceInsuranceStartTime() {
		if (params != null) {
			return params.get("forceInsuranceStartTime");
		}
		return null;
	}

	@Override
	public Object getInsuranceStartTime() {
		if (params != null) {
			return params.get("insuranceStartTime");
		}
		return null;
	}

	@Override
	public Object getInsuranceCompanyName() {
		if (params != null) {
			return params.get("insuranceCompanyName");
		}
		return null;
	}

	@Override
	public Object getIdCard() {
		if (params != null) {
			return params.get("idCard");
		}
		return null;
	}

}

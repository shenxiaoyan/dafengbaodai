package com.liyang.client.tianan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liyang.client.ICreateEnquiryJsonParseAdapter;
import com.liyang.client.tianan.enums.QuotationTypeEnums;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.enums.InsureCompanyEnum;
import com.liyang.util.CityCodeUtil;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class CreateEnquiryJsonParseAdapterTiananImpl implements ICreateEnquiryJsonParseAdapter {
	JSONObject params;
	CreateEnquiry enquiry;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private final static Logger logger = LoggerFactory.getLogger(CreateEnquiryJsonParseAdapterTiananImpl.class);

	private final static int CITY_CODE_LENGTH = 6;

	public CreateEnquiryJsonParseAdapterTiananImpl(CreateEnquiry createEntity) {
		params = createEntity.getCreateEnquiryParams();
		enquiry = createEntity;
	}

	@Override
	public Object getLicenseNumber() {
		if (params != null) {
			return enquiry.getLicenseNumber();
		}
		return null;
	}

	@Override
	public Object getOwnerName() {
		if (params != null) {
			return enquiry.getOwnerName();
		}
		return null;
	}

	@Override
	public Object getInsurancesListSchemeName() {
		if (params != null) {
			try {
				return params.getJSONArray("combosList").getJSONObject(0).getString("comboNo");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Object getCityName() {
		if (params != null) {
			String cityCode = String.valueOf(params.get("cityCode"));
			if (cityCode.length() == CITY_CODE_LENGTH + 1) {
				cityCode = cityCode.substring(1);
			}
			String cityName = CityCodeUtil.getCityName(cityCode);
			return cityName;
		}
		return null;
	}

	@Override
	public Object getForceInsuranceStartTime() {
		if (params != null && params.get("startDate") != null && params.get("quotationType") != null) {
			Date startDate;
			if (QuotationTypeEnums.compulsoryInsurance.getCode().equals(params.get("quotationType"))) {
				// 单交强，startDate是交强险的起保时间
				try {
					startDate = sdf.parse((String) params.get("startDate"));
					return startDate.getTime() / 1000;
				} catch (ParseException e) {
					logger.warn("startDate格式化Date错误,startDate:" + params.getString("startDate"));
					return 0;
				}
			} else if (QuotationTypeEnums.businessInsurance.getCode().equals(params.get("quotationType"))) {
				// 单商业，无时间
				return null;
			} else {
				// 混合，startDate是交强险的起保时间
				try {
					startDate = sdf.parse((String) params.get("startDate"));
					return startDate.getTime() / 1000;
				} catch (ParseException e) {
					logger.warn("startDate格式化Date错误,startDate:" + params.getString("startDate"));
					return 0;
				}
			}
		}
		return null;
	}

	@Override
	public Object getInsuranceStartTime() {
		if (params != null && params.get("startDateBus") != null && params.get("quotationType") != null) {
			Date startDateBus;
			if (QuotationTypeEnums.compulsoryInsurance.getCode().equals(params.get("quotationType"))) {
				// 单交强，无时间
				return null;
			} else if (QuotationTypeEnums.businessInsurance.getCode().equals(params.get("quotationType"))) {
				// 单商业，startDate是商业险的起保时间
				try {
					startDateBus = sdf.parse((String) params.get("startDate"));
					return startDateBus.getTime() / 1000;
				} catch (ParseException e) {
					logger.warn("startDate格式化Date错误,startDateBus:" + params.getString("startDate"));
					return 0;
				}
			} else {
				// 混合，startDateBus是商业险的起保时间
				try {
					startDateBus = sdf.parse((String) params.get("startDateBus"));
					return startDateBus.getTime() / 1000;
				} catch (ParseException e) {
					logger.warn("startDate格式化Date错误,startDateBus:" + params.getString("startDateBus"));
					return 0;
				}
			}
		}
		return null;
	}

	@Override
	public Object getInsuranceCompanyName() {
		if (params != null) {
			return String.valueOf(InsureCompanyEnum.TIANAN.getId().intValue());
		}
		return null;
	}

	@Override
	public Object getIdCard() {
		if (params != null) {
			try {
				return params.getJSONObject("carInfoDto").getString("carOwnerIdentifyNumber");
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}

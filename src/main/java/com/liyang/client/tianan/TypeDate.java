package com.liyang.client.tianan;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.liyang.client.IValidatable;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 抽象数据类型:日期
 * 
 * @author Administrator
 *
 */
public class TypeDate implements IValidatable {
	private String dateString;

	public TypeDate(String dateString) throws ExceptionTiananParamInvliad {
		this.setDateString(dateString);
	}

	public TypeDate(Date date) throws ExceptionTiananParamInvliad {
		if (date == null) {
			this.setDateString(null);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.setDateString(sdf.format(date));
		}
	}

	public TypeDate() {

	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		String error = "";
		if (StringUtils.isEmpty(this.dateString)) {
			error = "请提供日期字符串或日期";
			throw new ExceptionTiananParamInvliad(error);
		}
		// \d{4,4}-\d{2,2}-\d{2,2}\s\d{2,2}:\d{2,2}:\d{2,2}
		if (!this.dateString.matches("\\d{4,4}-\\d{2,2}-\\d{2,2}")) {
			error = "无效的日期格式,必须是YYYY-MM-DD";
			throw new ExceptionTiananParamInvliad(error);
		}
	}

	public String getString() {
		return this.dateString;
	}

	/**
	 * @param dateString
	 *            the dateString to set
	 * @throws ExceptionTiananParamInvliad
	 */
	public void setDateString(String dateString) throws ExceptionTiananParamInvliad {
		this.dateString = dateString;
		this.validate();
	}

}

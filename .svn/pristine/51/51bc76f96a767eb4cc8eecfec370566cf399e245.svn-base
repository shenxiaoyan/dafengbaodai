package com.liyang.client.tianan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.util.StringUtils;

import com.liyang.client.BaseRequestHeader;

/**
 * @author Administrator
 *
 */
public class FactoryRequestHeader {
	public BaseRequestHeader createRequestHeader(MessageGetSign messageGetSign, String sign, String tradeNo) {
		return this.createRequestHeader(messageGetSign, sign, tradeNo, true);
	}

	public BaseRequestHeader createRequestHeader(MessageGetSign messageGetSign, String sign, String tradeNo,
			boolean carInsurance) {
		BaseRequestHeader result = new BaseRequestHeader();
		if (messageGetSign == null) {
			return null;
		}
		result.setOpenID(messageGetSign.getOpenID());
		result.setToken(messageGetSign.getToken());
		result.setSign(sign);
		String tradeType = carInsurance ? "car" : "nocar";
		result.setTradeType(tradeType);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tradeDate = sdf.format(new Date());
		result.setTradeDate(tradeDate);
		// String tradeNo = UUID.randomUUID().toString().replaceAll("-", "");
		//同一个业务流程的tradeNo相同   如果tradeNo为空，则生成一个新的tradeNo
		if (StringUtils.isEmpty(tradeNo)) {
			tradeNo = createUnique();
		}
		result.setTradeNo(tradeNo);

		return result;
	}
	
	/**
	 * 生成唯一键 格式 102-年月日时分秒毫秒-4位随机数
	 * @return
	 */ 
	private String createUnique() {
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
		String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
		String hour = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY));
		String minute = String.format("%02d", calendar.get(Calendar.MINUTE));
		String second = String.format("%02d", calendar.get(Calendar.SECOND));
		String milliSecond = String.format("%03d", calendar.get(Calendar.MILLISECOND));
		String time = year + month + day + hour + minute + second + milliSecond;
		UUID id = UUID.randomUUID();
		String[] idd = id.toString().split("-");
		// 小马使用101 ，天安使用102
		return "102-" + time + "-" + idd[1];
	}
}

package com.liyang.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.domain.responseReturn.Data;
import com.liyang.domain.responseReturn.ErrorMsg;
import com.liyang.domain.responseReturn.ResponseReturn;
import com.liyang.domain.temporaryresult.TemporaryResult;
import com.liyang.domain.temporaryresult.TemporaryResultRepository;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;
import com.liyang.util.TransUtils;

import net.sf.json.JSONObject;
/**
 * 暂存结果服务
 * @author Administrator
 */
@Service
public class TemporaryResultService {
	@Autowired
	PlatformRepository platformRepository;
	@Autowired
	OfferResultRepository offerResultRepository;
	@Autowired
	TemporaryResultRepository temporaryResultRepository;
	public String saveTemResAndForward(Map<String, Object> temResMap) throws Exception{
		if((boolean) temResMap.get("successful")){
			//根据orderId获取到相关平台，并关联
			String orderId=JSONObject.fromObject(temResMap).getJSONObject("data").getString("orderId");
			Platform platform=offerResultRepository.findPlatformByOfferId(orderId);
			if(platform==null){
				System.out.println("该订单没有平台来源");
			}
			//将暂存结果数据封装成对象
			TemporaryResult temporaryResult=TransUtils.mapTransObject(temResMap,TemporaryResult.class);
			temporaryResult.setPlatform(platform);
			temporaryResultRepository.save(temporaryResult);
			//将结果post到指定的平台
//			HttpUtil.postBody(platform.getPlatformOfferURL(), JSONObject.fromObject(temResMap).toString());
			return  responseReturn(orderId);
		}else{
			//暂存结果失败
			return null;
		}
	}
	
	public String responseReturn(String offerId) {
		ResponseReturn responseReturn=new ResponseReturn(new ErrorMsg("success", "操作成功"), new Data(offerId), true);
		return JSONObject.fromObject(responseReturn).toString();
	}
}

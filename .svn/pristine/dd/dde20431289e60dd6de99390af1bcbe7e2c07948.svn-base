package com.liyang.client.tianan.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liyang.client.BaseClient;
import com.liyang.domain.querypayment.RequestPay;
import com.liyang.util.TransUtils;

/**
 * @author Administrator
 *
 */
public class QueryPayStateThread implements Runnable{

	private final static Logger logger = LoggerFactory.getLogger(QueryPayStateThread.class);
	
	private String orderNo;
	
	public QueryPayStateThread(String orderNo){
		this.orderNo = orderNo;
	}
	
	@Override
	public void run() {
		try {
			//
			Thread.sleep(1000*60);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		RequestPay requestPay = new RequestPay();
		requestPay.setOrderNo(orderNo);
		
		//天安网销订单号   模拟随机生成
		String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Random random = new Random();
		// 获取5位随机数
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		requestPay.setOrderId(str+rannum);
		
		int tempInt = random.nextInt(10);
		if (tempInt<=2) {
			requestPay.setStatue(0);
		}else {
			requestPay.setStatue(1);
		}
		
		
		requestPay.setBankTradeDate(str);
		
		requestPay.setFee(0.01);
		
		requestPay.setExt1("扩展字段1");
		
		requestPay.setExt2("扩展字段2");
		
		requestPay.setPayNo("上海地区平台交易号");
		
		requestPay.setPolicyNo("车险：大保单号");
		
		requestPay.setBizPolicyNo("商业险保单号");
		
		requestPay.setForcePolicyNo("交强险保单号");
		
		requestPay.setRcldPolicyNo("人身险保单号");
		
		String url = "http://testwww.dafengbaoxian.com/dafeng/confirmPayTianan";
		
		Map<String, Object> map = null;
		
		try {
			map = TransUtils.object2Map(requestPay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		BaseClient client = new BaseClient(url);
		
		System.out.println(client.getData(url, map));
	}

}


















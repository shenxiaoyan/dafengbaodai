package com.liyang.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.domain.insuranceresult.InsuranceResultData;
import com.liyang.domain.insuranceresult.InsuranceResultRepository;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalRepository;
import com.liyang.domain.submitproposal.SubmitProposalStateRepository;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.helper.ResponseBody;
import com.liyang.util.FailReturnObject;
import com.liyang.websocket.MyWebSocket;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/test")
public class TestController {
	
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	AdvertiseRepository advertiseRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EntityManagerFactory emf;
	
	@Autowired
	InsuranceResultRepository insResRepository;
	
	@Autowired
	SubmitProposalRepository subProRepository;
	
	@Autowired
	SubmitProposalStateRepository subProStateRepository;
	
	@RequestMapping("/testnotrefresh")
	public ResponseBody testAdvertise(@RequestBody Map<String, Object> creEnqMap){
		try {
			for (Entry<Integer, MyWebSocket> entry : MyWebSocket.getWebSockets().entrySet()) {
				Map<String, Object> messageMap = new HashMap<>();
				messageMap.put("title", "新的询价创建");
				JSONObject object = JSONObject.fromObject(creEnqMap.get("createEnquiryParams"));
				String content = "承保车牌："+object.getString("licenseNumber")+"<br/>承保车主："+object.getString("ownerName")+
						"<br/>询价用户："+creEnqMap.get("mobilePhone");
				messageMap.put("content", content);
				messageMap.put("url", "待添加");
				messageMap.put("refreshFlag", 0);
				String messageTxt = JSON.toJSONString(messageMap);
				entry.getValue().sendMessage(messageTxt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseBody("测试成功！");
	}
	

	@RequestMapping("/testrefresh")
	@Transactional
	public ResponseBody testAdvertiserefresh(@RequestBody Map<String, Object> creEnqMap){
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Map<String, Object> messageMap = new HashMap<>();
		JSONObject object = JSONObject.fromObject(creEnqMap.get("createEnquiryParams"));
		String content = "承保车牌："+object.getString("licenseNumber")+"<br/>承保车主："+object.getString("ownerName")+
				"<br/>询价用户："+creEnqMap.get("mobilePhone");
		User user = userRepository.findOne(1);
		Advertise advertise = new Advertise();
		advertise.setTitle("新的询价创建");
		advertise.setContent(content);
		advertise.setIsRead(0);
		advertise.setZnxType(5);
		advertise.setType(3);
		advertise.setCreatedAt(new Date());
		advertise.setLinkUrl("/#/workflowEntity/createEnquiry/list");
		advertise.setUser(user);
		advertise = advertiseRepository.save(advertise);
		em.getTransaction().commit();
		try {
			for (Entry<Integer, MyWebSocket> entry : MyWebSocket.getWebSockets().entrySet()) {
				messageMap.put("title", "新的询价创建");
				messageMap.put("content", content);
				messageMap.put("linkUrl", "/#/workflowEntity/createEnquiry/list");
				messageMap.put("refreshFlag", 1);
				String messageTxt = JSON.toJSONString(messageMap);
				entry.getValue().sendMessage(messageTxt);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseBody("消息发送失败");
		}
		
		return new ResponseBody("测试成功！");
	}
	
	
	
	@RequestMapping("webSocket")
	public String test() {
		Map<Integer, MyWebSocket> webSockets = MyWebSocket.getWebSockets();
		for (Entry<Integer, MyWebSocket> entity : webSockets.entrySet()) {
			try {
				entity.getValue().sendMessage("测试一个杠r\r杠");
				entity.getValue().sendMessage("测试两个杠r\\r杠");
				entity.getValue().sendMessage("测试一个杠n\n杠");
				entity.getValue().sendMessage("测试两个杠n\\n杠");
				entity.getValue().sendMessage("测试一个杠rn\r\n杠");
				entity.getValue().sendMessage("测试两个杠rn\\r\\n杠");
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("web消息发送失败！");
				throw new FailReturnObject(333, "web消息发送失败");
			}
		}
		return "测试成功！";
	}
	
	@RequestMapping("/saveInsResult")
	@Transactional
	public String testSaveInsResult() {
		InsuranceResult insResult = new InsuranceResult();
		InsuranceResultData data = new InsuranceResultData();
		insResult.setData(data);
		SubmitProposal subProposal = new SubmitProposal();
		insResult.setSubmitProposal(subProposal);
		insResult = insResRepository.save(insResult);
		subProposal.setState(subProStateRepository.findByStateCode("CHENGBAO_SUCCESS"));
		subProRepository.save(subProposal);
		return "保存成功";
	}
	
	@RequestMapping("/string")
	public String testString(@RequestParam(value = "data")String argument) {
		return argument;
	}
	
	
	
	
}

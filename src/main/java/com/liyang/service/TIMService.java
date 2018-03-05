package com.liyang.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.el.lang.EvaluationContext;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.config.TIMKey;
import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.department.Department;
import com.liyang.domain.departmenttype.Departmenttype;
import com.liyang.domain.exception.Exception;
import com.liyang.domain.exception.ExceptionRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.message.CustomContent;
import com.liyang.message.CustomContent.Ext;
import com.liyang.message.Message;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;
import com.liyang.util.ReturnObjectImpl;

/**
 * @author Administrator
 *
 */
@Service
public class TIMService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	ExceptionRepository exceptionRepository;
	
	@Autowired
	private HttpServletRequest request;
	

	private String ver = "V4";
	private long sdkappid = TIMKey.sdkAppId;
	private String identifier = TIMKey.identifier;
	private String usersig = TIMKey.usersig;
	private int random = 9999;
	private String contenttype = "json";

	private String urlTemplate = "https://console.tim.qq.com/" + ver + "/%s/%s?sdkappid=" + sdkappid + "&identifier="+ identifier+"&usersig="+usersig+"&random=%d&contenttype=json";
//	StringBuilder sb = new StringBuilder();
//	private String urlTemplate = sb.append("https://console.tim.qq.com/") .append(ver) .append("/%s/%s?sdkappid=")
//	.append(sdkappid).append("&identifier=").append(identifier).append("&usersig=").append(usersig).append("&random=%d&contenttype=json").toString();

	public void doNotice(User fromUser, AbstractAuditorEntity entity, AbstractAuditorAct act) {
		if(request.getParameter("notice")!=null && !"".equals(request.getParameter("notice"))){
			entity.setNotice(request.getParameter("notice"));
		}
		
		if (act.getNoticeSelfTemplate() != null && !act.getNoticeSelfTemplate().trim().equals("")) {
			AbstractAuditorAct actNotice = entity.getService().getActRepository().findByActCode("noticeActUser");
			if (actNotice == null) {
				throw new FailReturnObject(ExceptionResultEnum.TIM_NOTICE_ACTUSER_ERROR);
//				throw new FailReturnObject(1800, "noticeActUser动作不存在");
			}
			String sendCustomElemMessage = sendCustomElemMessage(CommonUtil.getPrincipal(), entity, act,
					act.getNoticeSelfTemplate());
			if (sendCustomElemMessage != null) {

				noticeLog(CommonUtil.getPrincipal(), entity, actNotice, sendCustomElemMessage);
			}
		}
		if (act.getNoticeShowTemplate() != null && !act.getNoticeShowTemplate().trim().equals("")) {
			AbstractAuditorAct actNotice = entity.getService().getActRepository().findByActCode("noticeShowUser");
			if (actNotice == null) {
				throw new FailReturnObject(ExceptionResultEnum.TIM_NOTICE_SHOWUSER_ERROR);
//				throw new FailReturnObject(1810, "noticeShowUser动作不存在");
			}
			String sendCustomElemMessage = sendCustomElemMessage(fromUser, entity, act, act.getNoticeShowTemplate());
			if (sendCustomElemMessage != null) {
				noticeLog(fromUser, entity, actNotice, sendCustomElemMessage);
			}
		}
		if (act.getNoticeOtherTemplate() != null && !act.getNoticeOtherTemplate().trim().equals("")) {
			AbstractAuditorAct actNotice = entity.getService().getActRepository().findByActCode("noticeOther");
			if (actNotice == null) {
				throw new FailReturnObject(ExceptionResultEnum.TIM_NOTICE_OTHER_ERROR);
//				throw new FailReturnObject(1820, "noticeOther动作不存在");
			}
			if (entity.getNotice() != null && !entity.getNotice().equals("")) {
				
				String[] split = entity.getNotice().split(",");
				
				for (String u : split) {
//					User findByUnionid = userRepository.findByUnionid(u);
					//unionid 该 openid
					User findOne=userRepository.findOne(Integer.valueOf(u));
					String sendCustomElemMessage = sendCustomElemMessage(findOne, entity, act,
							act.getNoticeOtherTemplate());
					if (sendCustomElemMessage != null) {
						noticeLog(findOne, entity, actNotice, sendCustomElemMessage);
					}
				}
			}
			if (act.getNoticeDepartmenttype() != null && act.getNoticeRole() != null) {
				
				List<User> users = walkByDepartmentTypeAndRole(entity , act.getNoticeDepartmenttype(), act.getNoticeRole());

				if (users == null || users.isEmpty()) {
					Exception exception = new Exception();
					exception.setActionStatus("FAIL");
					exception.setErrorCode(3102);
					StringBuilder sb2 = new StringBuilder();
					exception.setErrorInfo(sb2.append(entity.getClass().getSimpleName()).append( "的动作") .append( act.getLabel() ).append( "的自动通知没有用户接收").toString());
					exceptionRepository.save(exception);
					
				} else {
					for (User user : users) {
						String sendCustomElemMessage = sendCustomElemMessage(user, entity, act,
								act.getNoticeOtherTemplate());
						if (sendCustomElemMessage != null) {
							noticeLog(user, entity, actNotice, sendCustomElemMessage);
						}
					}
				}
			}
		}
	}
	

	private List<User> walkByDepartmentTypeAndRole(AbstractAuditorEntity entity, Departmenttype noticeDepartmentType,
			Role noticeRole) {
		
		if(entity.getCreatedByDepartment()!=null){
			Department department = searchDepartment(entity.getCreatedByDepartment(),noticeDepartmentType);
			if(department==null){
				Exception exception = new Exception();
				exception.setActionStatus("FAIL");
				exception.setErrorCode(3114);
				StringBuilder sb3 = new StringBuilder();
				exception.setErrorInfo(sb3.append(entity.getClass().getSimpleName()) .append( entity.toString()) .append( "通知的部门类型")
						.append( noticeDepartmentType.getCode()).append("没有部门").toString());
				exceptionRepository.save(exception);
			}else{
				if(!department.getType().getRoles().contains(noticeRole)){
					Exception exception = new Exception();
					exception.setActionStatus("FAIL");
					exception.setErrorCode(3116);
					StringBuilder sb4 = new StringBuilder();
					exception.setErrorInfo(sb4.append("通知的部门类型") .append( noticeDepartmentType.getCode()) .append( "没有角色")
							.append(noticeRole.getRoleCode()).toString());
					exceptionRepository.save(exception);
				}else{
					return userRepository.findByDepartmentAndRole(department,noticeRole);
				}
			}
		}else{
			Exception exception = new Exception();
			exception.setActionStatus("FAIL");
			exception.setErrorCode(3112);
			StringBuilder sb6 = new StringBuilder();
			exception.setErrorInfo(sb6.append(entity.getClass().getSimpleName()).append(":").append(entity.toString()).append("没有创建部门").toString());
			exceptionRepository.save(exception);
		}
		

		return null;
	}






	private Department searchDepartment(Department createdByDepartment, Departmenttype noticeDepartmentType) {
		if(createdByDepartment.getType().equals(noticeDepartmentType)){
			return createdByDepartment;
		}else{
			if(createdByDepartment.getParent()!=null){
				return searchDepartment(createdByDepartment.getParent(),noticeDepartmentType);
			}
			return null;
		}
	}






	private void noticeLog(User user, AbstractAuditorEntity entity, AbstractAuditorAct act, String message) {
		AbstractAuditorLog logInstance = entity.getLogInstance();
		logInstance.setAct(act);
		logInstance.setActGroup(act.getActGroup());
		logInstance.setEntity(entity);
		logInstance.setLabel(act.getLabel());
		logInstance.setNoticeTo(user);
		logInstance.setNotice(message);
		entity.getLogRepository().save(logInstance);
	}

	public String sendCustomElemMessage(User user, AbstractAuditorEntity entity, AbstractAuditorAct act,
			String template) {
		if(act.getMessageSender()==null){
			throw new FailReturnObject(ExceptionResultEnum.TIM_SEND_ACCOUNT_ERROR,ReturnObject.Level.DISPLAY);
//			throw new FailReturnObject(6361, "发送的账户不存在", ReturnObject.Level.DISPLAY);
		}


		if (user != null) {
			String url = String.format(urlTemplate, "openim", "sendmsg", new Random().nextInt(700000000));
			String dataFromTemplate = getDataFromTemplate(user, entity, act, template);
			if (dataFromTemplate == null) {
				throw new FailReturnObject(ExceptionResultEnum.TIM_SEND_ACTINFO_ERROR);
//				throw new FailReturnObject(1900, "没有设置行为消息模板");
			}
			
			Ext ext = new CustomContent.Ext();
			ext.setEntityId(entity.getId());
			ext.setEntityName(entity.getClass().getSimpleName());
			ext.setType("notice");
			if(entity instanceof AbstractWorkflowEntity){
				ext.setEntityType("workflowEntity");
			}else if(entity instanceof AbstractAuditorEntity){
				ext.setEntityType("auditEntity");
			}
			
			Message message = CommonUtil.customElemMessageWrapper(dataFromTemplate, ext, act.getMessageSender().getId().toString(),user.getId().toString(), 2);
			ReturnObject restTemplatePost = restTemplatePost(url, message);
			if (restTemplatePost != null && "OK".equals(restTemplatePost.getActionStatus())) {
				return dataFromTemplate;
			} else {
				return restTemplatePost.getErrorCode() + ":" + restTemplatePost.getErrorInfo();
			}

		} else {
			return null;
		}
	}

	private String getDataFromTemplate(User user, AbstractAuditorEntity entity, AbstractAuditorAct act,
			String template) {
		SpelContext spelContext = new SpelContext(user, entity, act);
		ExpressionParser parser = new SpelExpressionParser();

		String result = parser.parseExpression(template, new TemplateParserContext()).getValue(spelContext,
				String.class);
		return result;

	}

	private ReturnObject restTemplatePost(String url, Object message) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		String ret = restTemplate.postForObject(url, message, String.class);
		ObjectMapper mapper = new ObjectMapper();
		ReturnObjectImpl readValue;
		try {
			readValue = mapper.readValue(ret, ReturnObjectImpl.class);
		} catch (IOException e) {
			throw new FailReturnObject(ExceptionResultEnum.TIM_REST_PARSEINFO_ERROR);
//			throw new FailReturnObject(1800, "解析tim信息错误");
		}
		return readValue;
	}

	public ReturnObject addUser(String identifier, String nick, String faceUrl) {
		String url = String.format(urlTemplate, "im_open_login_svc", "account_import", new Random().nextInt(100000000));
		AddUser user = new AddUser();
		user.setFaceUrl(faceUrl);
		user.setIdentifier(identifier);
		user.setNick(nick);
		ReturnObject restTemplatePost = restTemplatePost(url, user);
		if (restTemplatePost != null && "OK".equals(restTemplatePost.getActionStatus())) {
			return restTemplatePost;
		} else {
			throw new FailReturnObject(1750, restTemplatePost.getErrorCode() + ":" + restTemplatePost.getErrorInfo());
		}
	}

	private class AddUser {
		@JsonProperty("Identifier")
		private String identifier;
		@JsonProperty("Nick")
		private String nick;
		@JsonProperty("FaceUrl")
		private String faceUrl;

		public String getIdentifier() {
			return identifier;
		}

		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		public String getNick() {
			return nick;
		}

		public void setNick(String nick) {
			this.nick = nick;
		}

		public String getFaceUrl() {
			return faceUrl;
		}

		public void setFaceUrl(String faceUrl) {
			this.faceUrl = faceUrl;
		}

	}

	private class SpelContext {
		private User user;
		private AbstractAuditorEntity entity;
		private AbstractAuditorAct act;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public AbstractAuditorEntity getEntity() {
			return entity;
		}

		public void setEntity(AbstractAuditorEntity entity) {
			this.entity = entity;
		}

		public AbstractAuditorAct getAct() {
			return act;
		}

		public void setAct(AbstractAuditorAct act) {
			this.act = act;
		}

		public SpelContext(User user, AbstractAuditorEntity entity, AbstractAuditorAct act) {
			super();
			this.user = user;
			this.entity = entity;
			this.act = act;
		}

	}
}

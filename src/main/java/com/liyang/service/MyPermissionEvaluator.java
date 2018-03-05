package com.liyang.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorEntity;
import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Component
public class MyPermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TIMService timService;

	private AbstractAuditorEntity getEntityFromDomainObject(Object targetDomainObject) {
		AbstractAuditorEntity e = null;
		if (targetDomainObject instanceof PageImpl) {
			if (((PageImpl) targetDomainObject).hasContent()) {
				Object o = ((PageImpl) targetDomainObject).getContent().get(0);
				e = (AbstractAuditorEntity) o;
			}
		}
		else if (targetDomainObject instanceof List || targetDomainObject instanceof Set) {
			if (!((Collection) targetDomainObject).isEmpty()) {
				e = (AbstractAuditorEntity) ((Collection) targetDomainObject).iterator().next();
			}
		} 
	else if (targetDomainObject != null) {
			e = (AbstractAuditorEntity) targetDomainObject;
		}
		return e;
	}

	private AbstractAuditorAct getActFromPermission(AbstractAuditorEntity e, Object permission) {
		String actStr = permission.toString();
		if(e.getBeforeState()==null){
			e.setBeforeState(e.getState());
		}
		if ("read".equals(permission.toString())){
			e.setupMap();
		}
				
		if ("read".equals(permission.toString()) && request.getParameter("act") != null) {
			actStr = request.getParameter("act").trim();
		}
		System.out.println("-----"+e.getService());
		return e.getService().getAct(e, actStr, null);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		// TODO Auto-generated method stub
		AbstractAuditorEntity e = getEntityFromDomainObject(targetDomainObject);
		if (e == null) {
			return true;
		}
		AbstractAuditorAct act = getActFromPermission(e, permission);

//		if (request.getParameter("notice") != null && !request.getParameter("notice").trim().equals("")) {
//			e.setNotice(request.getParameter("notice"));
//		}
//
//		User fromUser = null;
//		if (request.getParameter("fromUser") != null) {
//			fromUser = userRepository.findByUnionid(request.getParameter("fromUser"));
//		}
//		timService.doNotice(fromUser, e, act);

		return true;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

}

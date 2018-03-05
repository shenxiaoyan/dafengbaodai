package com.liyang.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.base.StateVO;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerForSearch;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.customer.CustomerVO;
import com.liyang.domain.team.Team;
import com.liyang.domain.upgradeapply.UpgradeApply;
import com.liyang.domain.upgradeapply.UpgradeApplyRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.DisplayException;
import com.liyang.helper.ResponseBody;
import com.liyang.service.AdminService;
import com.liyang.service.CustomerService;
import com.liyang.service.TeamService;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PageUtil;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/user")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerService customerService;
	@Autowired
	TeamService teamService;
	@Autowired
	private UpgradeApplyRepository upgradeApplyRepository;
	@Autowired
	private AdminService adminService;

	/**
	 * 获取用户信息
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public ResponseBody detail(HttpSession session) {
		int id = ((Customer) session.getAttribute("user_info")).getId();
		Customer customer = customerRepository.getById(id);
		ResponseBody response = new ResponseBody(customer);
		return response;
	}

	/**
	 * //根据Id获取用户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getCustomerDetail", method = RequestMethod.GET)
	public ResponseBody getCustomerDetailById(Integer id) {
		Customer customer = customerRepository.getById(id);
		ResponseBody response = new ResponseBody(customer);
		return response;
	}

	/**
	 * 修改用户信息
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.PATCH })
	public ResponseBody update(@RequestBody Map<String, String> customer, HttpServletRequest request) {
		ResponseBody result = customerService.update(customer, request);
		return result;
	}

	@RequestMapping(value = "/changeState", method = RequestMethod.PATCH)
	public ResponseBody changeState(Integer id) {
		Customer type = customerRepository.findOne(id);
		if (type == null) {
			throw new FailReturnObject(ExceptionResultEnum.DEPARTMENTTYPE_ID_ERROR);
		}
		customerService.updateState(type);
		return new ResponseBody("");
	}

	/**
	 * 用户申请升级为粉丝
	 */
	@RequestMapping(value = "/upgrade", method = RequestMethod.POST)
	@Transactional
	public ResponseBody upgrade(@Valid @RequestBody UpgradeApply upgrade) {
		upgrade.setApplyTime(new Date());
		upgrade.setStatus(0);
		// upgrade.setPhone(((User1)session.getAttribute("user_info")).getPhone());
		try {
			List<UpgradeApply> list = upgradeApplyRepository.queryLatest(upgrade.getPhone());
			if (list.size() != 0) {
				if (list.get(0).getStatus() == 0) {
					throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_UPGRADE_APPLY_AUIDT_ERROR);
					// throw new DisplayException("您的申请正在审核中，请耐心等待审核");
				}
			}
			upgradeApplyRepository.save(upgrade);
			// 自动同意升级认证
			adminService.accpetUpgrade(upgrade);
			ResponseBody resp = new ResponseBody(0, "ok");
			return resp;
		} catch (DisplayException e) {
			throw e;
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
			System.out.println(e.getClass().getName());
			throw new FailReturnObject(ExceptionResultEnum.REGIS_MOB_INSERT_ERROR);
		}
	}

	/*
	 * replaced with queryInviteCustomersForMobile
	 */
	// @RequestMapping(value="/queryInviteCustomers" , method=RequestMethod.GET)
	// public String queryInviteCustomers(HttpServletRequest request) {
	// Customer customer =
	// customerRepository.findByToken(request.getHeader("token"));
	// if (customer == null) {
	// throw new FailReturnObject(ExceptionResultEnum.CUSTOMER_NOUSER_ERROR);
	// // throw new RuntimeException("用户不存在");
	// }
	// String myInvite = customer.getMyInvite();
	// String response = HttpUtil.getBody("http://"+ ip +
	// "/rest/customers/search/getInvitedList?invite="+myInvite +
	// "&projection=customerProjection");
	// return response ;
	// }

	@RequestMapping(value = "/queryInviteCustomersForMobile", method = RequestMethod.GET)
	public ResponseBody mobileQueryInviteCustomers(HttpServletRequest request) {
		return customerService.mobileQueryInviteCustomers(request.getHeader("token"));
	}

	@RequestMapping(value = "/getMyInfoForMobile", method = RequestMethod.GET)
	public ResponseBody mobileGeMyInfo(HttpServletRequest request) {
		String token = request.getHeader("token");
		return customerService.geMyInfoSer(token);
	}

	/*
	 * / replaced with getMyInfoForMobile
	 */
	// @RequestMapping(value="/getMyInfo" , method = RequestMethod.GET)
	// public CustomerProjectionClass geMyInfo(HttpServletRequest request){
	// String token = request.getHeader("token");
	// Customer customer = customerRepository.findByToken(token);
	// //用户被禁用
	// if("DISABLED".equals(customer.getState().getStateCode())){
	// throw new CustomerDisableException();
	// }
	//
	// CustomerProjectionClass cusProClass=dataPackage(customer); //数据封装
	// return cusProClass ;
	// }
	//
	// public CustomerProjectionClass dataPackage(Customer customer) {
	// CustomerProjectionClass customerProjectionClass=new
	// CustomerProjectionClass();
	// customerProjectionClass.setId(customer.getId());
	// customerProjectionClass.setClient(customer.getClient());
	// customerProjectionClass.setCreateTime(customer.getCreateTime());
	// customerProjectionClass.setGrade(customer.getGrade());
	// customerProjectionClass.setHeadimgurl(customer.getHeadimgurl());
	// customerProjectionClass.setInvite(customer.getInvite());
	// customerProjectionClass.setMyInvite(customer.getMyInvite());
	// customerProjectionClass.setNickname(customer.getNickname());
	// customerProjectionClass.setPhone(customer.getPhone());
	// customerProjectionClass.setPushToken(customer.getPushToken());
	// customerProjectionClass.setSex(customer.getSex());
	// customerProjectionClass.setTag(customer.getTag());
	// customerProjectionClass.setToken(customer.getToken());
	// customerProjectionClass.setUpdateTime(customer.getUpdateTime());
	// customerProjectionClass.setAccount(customer.getAccount());
	// return customerProjectionClass;
	// }

	/**
	 * //邀请列表查询分页
	 * 
	 * @param pageable
	 * @param id
	 * @return
	 */
	@PostMapping("/searchCustomerInviteForCustomer")
	public Map<String, Object> multiConditionSearchInviteForCustomer(
			@PageableDefault(value = 15, sort = "lastModifiedAt", direction = Sort.Direction.DESC) Pageable pageable,
			Integer id) {
		String myInvite = customerService.getMyInviteForCustomerById(id);
		Map<String, Object> returnMap = new HashMap<>();
		Page<Customer> customerPage = customerService.multiConditionSearchInviteForCustomer(pageable, myInvite);
		List<Object> customerMapList = new ArrayList<>();
		for (Customer c : customerPage.getContent()) {
			Map<String, Object> customerMap = new HashMap<String, Object>();
			customerMap.put("id", c.getId());
			customerMap.put("myInvite", c.getMyInvite());
			customerMap.put("headimgurl", c.getHeadimgurl());
			customerMap.put("nickname", c.getNickname());
			customerMap.put("phone", c.getPhone());
			customerMap.put("grade", c.getGrade());
			customerMap.put("createdAt", c.getCreatedAt());
			customerMap.put("lastModifiedAt", c.getLastModifiedAt());
			customerMapList.add(customerMap);
		}
		returnMap.put("customers", customerMapList);
		Map<String, Integer> pageDataMap = new HashMap<>();
		pageDataMap.put("size", customerPage.getSize());
		pageDataMap.put("totalElements", (int) customerPage.getTotalElements());
		pageDataMap.put("totalPages", customerPage.getTotalPages());
		pageDataMap.put("number", customerPage.getNumber());
		returnMap.put("page", pageDataMap);
		return returnMap;
	}

	/**
	 * //根据条件复杂查询粉丝列表
	 * 
	 * @param customerForSearch
	 * @param request
	 * @param pageable
	 * @param grade
	 * @return
	 */
	@RequestMapping("/searchFansList")
	public ResponseBody searchFansList(@RequestBody CustomerForSearch customerForSearch, HttpServletRequest request,
			@PageableDefault(15) Pageable pageable, Integer grade) {
		Map<String, Object> returnMap = new HashMap<>();
		Page<Customer> customerPage = customerService.seniorMultiConditionSearch(customerForSearch, pageable);
//		List<Customer> list = new ArrayList<>();
//		for (Customer customer : customerPage) {
//			customer.setSubmitProposalFile(null);
//			customer.setDafengApiCustomer(null);
//			if (null != customer.getState()) {
//				customer.getState().setActs(null);
//				customer.getState().setFromActs(null);
//				customer.getState().setWorkflows(null);
//			}
//			list.add(customer);
//		}
//		returnMap.put("customers", list);
//		-------------------------------------
		List<CustomerVO> VOList = new ArrayList<>();
		for (Customer customer : customerPage) {
			CustomerVO customerVO = new CustomerVO();
			BeanUtils.copyProperties(customer, customerVO);
			StateVO stateVO = new StateVO();
			BeanUtils.copyProperties(customer.getState(), stateVO);
			customerVO.setState(stateVO);
			VOList.add(customerVO);
		}
		returnMap.put("customers", VOList);
//		Map<String, Integer> pageDataMap = new HashMap<>();
//		pageDataMap.put("size", customerPage.getSize());
//		pageDataMap.put("totalElements", (int) customerPage.getTotalElements());
//		pageDataMap.put("totalPages", customerPage.getTotalPages());
//		pageDataMap.put("number", customerPage.getNumber());
		Map<String, Integer> pageDataMap = PageUtil.getPageData(customerPage);
		returnMap.put("page", pageDataMap);
		return new ResponseBody(returnMap);
	}

	/**
	 * //前端用户列表
	 * 
	 * @param customerForSearch
	 * @param pageable
	 * @return
	 */
	@PostMapping("/searchCustomer")
	public Map<String, Object> multiConditionSearch(@RequestBody CustomerForSearch customerForSearch,
			@PageableDefault(value = 15, sort = "lastModifiedAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Map<String, Object> returnMap = new HashMap<>();
		Page<Customer> customerPage = customerService.multiConditionSearch(customerForSearch, pageable);
//		List<Customer> list = new ArrayList<>();
//		for (Customer customer : customerPage) {
//			customer.setSubmitProposalFile(null);
//			customer.setDafengApiCustomer(null);
//			if (null != customer.getState()) {
//				customer.getState().setActs(null);
//				customer.getState().setFromActs(null);
//				customer.getState().setWorkflows(null);
//				// TODO 暂时简单处理
//				if (null != customer.getTeam()) {
//					Team team = new Team();
//					team.setLabel(customer.getTeam().getLabel());
//					customer.setTeam(team);
//				}
//			}
//			list.add(customer);
//		}
//		returnMap.put("customers", list);
//		---------------------------------------
		List<CustomerVO> VOList = new ArrayList<>();
		for (Customer customer : customerPage) {
			CustomerVO customerVO = new CustomerVO();
			BeanUtils.copyProperties(customer, customerVO);
			StateVO stateVO = new StateVO();
			BeanUtils.copyProperties(customer.getState(), stateVO);
			customerVO.setState(stateVO);
			VOList.add(customerVO);
		}
		returnMap.put("customers", VOList);
//		-------------------------------------
//		Map<String, Integer> pageDataMap = new HashMap<>();
//		pageDataMap.put("size", customerPage.getSize());
//		pageDataMap.put("totalElements", (int) customerPage.getTotalElements());
//		pageDataMap.put("totalPages", customerPage.getTotalPages());
//		pageDataMap.put("number", customerPage.getNumber());
		Map<String, Integer> pageDataMap = PageUtil.getPageData(customerPage);
		returnMap.put("page", pageDataMap);
		return returnMap;
	}

	/**
	 * 根据部分号码查询未加入团队且完成实名认证的有效用户
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping("/getNoTeamCustomer")
	public ResponseBody getNoTeamCustomer(@RequestParam(required = true) String phone) {
		List<Map<String, Object>> returnList = customerService.findNoTeamByPhone(phone);
		return new ResponseBody(returnList);
	}

}

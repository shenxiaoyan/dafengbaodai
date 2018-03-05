package com.liyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.advertise.AdvertiseForSearch;
import com.liyang.domain.advertise.AdvertiseVO;
import com.liyang.domain.user.User;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.AdvertiseService;
import com.liyang.util.CommonUtil;
import com.liyang.util.PageUtil;

/**
 * 通知控制器
 * 
 * @author huanghengkun
 * @create 2017年12月17日
 */
@RestController
@RequestMapping("/dafeng/advertise/")
public class AdvertiseController {
	@Autowired
	private AdvertiseService advertiseService;

	// private Logger logger = LoggerFactory.getLogger(AdvertiseController.class);

	@GetMapping("/queryZnxForMobile")
	public ResponseBody mobileQueryZnx(HttpServletRequest request, @PageableDefault(15) Pageable pageable) {
		String token = request.getHeader("token");
		Page<Advertise> advertisePage = advertiseService.findZnxListByToken(token, pageable);
		return new ResponseBody(advertisePage);
//		List<AdvertiseVO> VOList = new ArrayList<>();
//		for (Advertise advertise : advertisePage) {
//			AdvertiseVO advVO = new AdvertiseVO();
//			BeanUtils.copyProperties(advertise, advVO);
//			VOList.add(advVO);
//		}
//		Map<String, Object> resultMap = PageUtil.getResultMapPage(advertisePage);
//		resultMap.put("content", VOList);
//		return new ResponseBody(resultMap);
		
	}

	@GetMapping(value = "/queryAdvertiseForMobile")
	public ResponseBody mobileQueryAdvetise(HttpServletRequest request, @PageableDefault(15) Pageable pageable) {
		Page<Advertise> advertisePage = advertiseService.findPublished(pageable);
		return new ResponseBody(advertisePage);
	}

	@PostMapping(value = "/updateReadStateForMobile")
	public ResponseBody mobileUpdateReadStateByToken(HttpServletRequest request) {
		String token = request.getHeader("token");
		advertiseService.updateReadStateByToken(token);
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}

	@GetMapping(value = "/isAnyNotReadForMobile")
	public ResponseBody mobileIsAnyNotRead(HttpServletRequest request) {
		String token = request.getHeader("token");
		boolean flag = advertiseService.checkIsAnyNotRead(token);
		return new ResponseBody(flag);
	}

	@GetMapping(value = "/getLatestForMobile")
	public ResponseBody getLatestAdvertise() {
		List<Advertise> list = advertiseService.getAdvertiseListOrderByTime();
		if (list != null && !list.isEmpty()) {
			return new ResponseBody(list.get(0).getTitle());
		}
		return new ResponseBody(ExceptionResultEnum.SUCCESS);
	}

	@PostMapping("/advertiseSearch")
	public ResponseBody multiConditionSearch(@RequestBody AdvertiseForSearch advertiseForSearch, @PageableDefault(15) Pageable pageable) {
		Page<Advertise> page = advertiseService.multiConditionSearch(advertiseForSearch, pageable);
//		for (Advertise advertise : page) {
//			advertise.setCreatedByDepartment(null);
//			if (advertise.getState() != null) {
//				advertise.getState().setActs(null);
//				advertise.getState().setEntities(null);
//				advertise.getState().setStateGroup(null);
//				advertise.getState().setCreatedByDepartment(null);
//			}
//			if (null != advertise.getOfferResult()) {
//				advertise.getOfferResult().setCreateEnquiry(null);
//			}
//		}
//		return new ResponseBody(page);
		
		List<AdvertiseVO> VOList = new ArrayList<>();
		for (Advertise advertise : page) {
			AdvertiseVO advVO = new AdvertiseVO();
			BeanUtils.copyProperties(advertise, advVO);
			VOList.add(advVO);
		}
		Map<String, Object> resultMap = PageUtil.getResultMapPage(page);
		resultMap.put("content", VOList);
		return new ResponseBody(resultMap);
	}

	/**
	 * 获取当前后台web用户5条最新未读消息， 并将所有未读消息设为已读
	 * 
	 * @return
	 */
	@GetMapping("/web/unRead")
	public ResponseBody getCurrengUserNotread(
			@PageableDefault(value = 5, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
		User user = CommonUtil.getPrincipal();
		if (null != user) {
			Map<String, Object> returnData = advertiseService.getCurrengUserNotread(user, pageable);
			return new ResponseBody(returnData);
		} else {
			return new ResponseBody("");
		}
	}

	/**
	 * 获取当前web用户未读信息数量
	 * 
	 * @return
	 */
	@RequestMapping("/web/unReadNum")
	public ResponseBody getCurrentUserNotReadNUm() {
		User user = CommonUtil.getPrincipal();
		if (null != user) {
			return new ResponseBody(advertiseService.getCurrentUserNotReadNum(user));
		} else {
			return new ResponseBody("");
		}
	}

	/**
	 * 获取当前web后台用户所有消息
	 * 
	 * @param pageable
	 * @return
	 */
	@RequestMapping("/web/all")
	public ResponseBody getCurrentUserAll(
			@PageableDefault(value = 15, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
		User user = CommonUtil.getPrincipal();
		if (null != user) {
			Map<String, Object> returnData = advertiseService.getCurrengUserAllAdvertise(user, pageable);
			return new ResponseBody(returnData);
		} else {
			return new ResponseBody("");
		}

	}

//	/**
//	 * 后台临时方法，为已创建的站内信增加creEnqId,手动执行一次更新下数据库即可
//	 * 
//	 * @return
//	 */
//	@RequestMapping("/updateAdvertiseCreEnqId")
//	public String updateAdvertiseCreEnqId(@PageableDefault(1000) Pageable pageable) {
//		AdvertiseForSearch advertiseForSearch = new AdvertiseForSearch();
//		advertiseForSearch.setType(String.valueOf(2));
//		Page<Advertise> advertisePage = advertiseService.multiConditionSearch(advertiseForSearch, null);
//		int totalNum = advertiseService.updateAdvertiseCreEnqId(advertisePage.getContent());
//		return "共更新" + totalNum + "条数据";
//	}

}

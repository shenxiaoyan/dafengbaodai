package com.liyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.customer.Customer;
import com.liyang.domain.team.Team;
import com.liyang.domain.teamadvertise.TeamAdvertise;
import com.liyang.domain.teamadvertise.TeamAdvertiseTO;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.CustomerService;
import com.liyang.service.TeamAdvertiseService;
import com.liyang.service.TeamService;
import com.liyang.util.FailReturnObject;
import com.liyang.util.PageUtil;

@RestController
@RequestMapping("/dafeng/team/advertise")
public class TeamAdvertiseComtroller {

	@Autowired
	TeamAdvertiseService teamAdvService;
	@Autowired
	TeamService teamService;
	@Autowired
	CustomerService customerService;

	// -------------------Mobile Methods ----------------------------

	/**
	 * 新增
	 * 
	 * @param teamAdvTO
	 * @return
	 */
	@PostMapping("/add")
	public ResponseBody mobileAddAdvertise(@RequestBody(required = true) TeamAdvertiseTO teamAdvTO) {
		TeamAdvertise teamAdvertise = new TeamAdvertise();
		BeanUtils.copyProperties(teamAdvTO, teamAdvertise);
		Team team = teamService.findOne(teamAdvTO.getTeamId());
		teamAdvertise.setTeam(team);
		teamAdvertise = teamAdvService.save(teamAdvertise);
		teamAdvTO.setId(teamAdvertise.getId());
		return new ResponseBody(teamAdvTO);
	}

	/**
	 * Mobile 删除
	 * 
	 * @param teamAdvTO
	 * @return
	 */
	@RequestMapping("/drop")
	public ResponseBody mobileChangeState(@RequestBody(required = true) TeamAdvertiseTO teamAdvTO) {
		TeamAdvertise teamAdvertise = teamAdvService.findById(teamAdvTO.getId());
		teamAdvService.delete(teamAdvertise);
		return new ResponseBody("删除成功");
	}

	/**
	 * 自身团队公告列表
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	public ResponseBody mobileGetList(@RequestBody TeamAdvertiseTO teamAdvTO, HttpServletRequest request, 
			@PageableDefault(value = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
		Customer customer = customerService.findbyToken(request.getHeader("token"));
		if (null == customer.getTeam()) {
			return new ResponseBody("");
		}
		teamAdvTO.setTeamId(customer.getTeam().getId());
		teamAdvTO.setStateCode("PUBLISHED");
		Page<TeamAdvertise> page = teamAdvService.query(teamAdvTO, pageable);
		List<TeamAdvertiseTO> advertiseList = new ArrayList<>();
		for (TeamAdvertise teamAdvertise : page) {
			TeamAdvertiseTO teamAdvertiseTO = new TeamAdvertiseTO();
			BeanUtils.copyProperties(teamAdvertise, teamAdvertiseTO);
			advertiseList.add(teamAdvertiseTO);
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("advertises", advertiseList);
		returnMap.put("page", PageUtil.getPageData(page));
		return new ResponseBody(returnMap);
	}

	// -------------------Web Methods ----------------------------

	/**
	 * 所有团队公告
	 * 
	 * @param teamAdvTO
	 * @param pageable
	 * @return
	 */
	@PostMapping("/query")
	public ResponseBody getAll(@RequestBody(required = false) TeamAdvertiseTO teamAdvTO,
			@PageableDefault(value = 15, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
		Page<TeamAdvertise> teamAdvPage = teamAdvService.query(teamAdvTO, pageable);
		List<TeamAdvertiseTO> teamAdvTOList = new ArrayList<>();
		for (TeamAdvertise teamAdvertise : teamAdvPage) {
			TeamAdvertiseTO advTO = new TeamAdvertiseTO();
			BeanUtils.copyProperties(teamAdvertise, advTO);
			advTO.setTeamLabel(teamAdvertise.getTeam().getLabel());
			advTO.setStateCode(teamAdvertise.getState().getStateCode());
			teamAdvTOList.add(advTO);
		}
		Map<String, Object> returnMap = new HashMap<>();
		returnMap.put("advertises", teamAdvTOList);
		returnMap.put("page", PageUtil.getPageData(teamAdvPage));
		return new ResponseBody(returnMap);
	}

	/**
	 * 据Id删除
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/delete")
	public ResponseBody delete(@RequestParam(required = true) Integer id) {
		TeamAdvertise teamAdv = teamAdvService.findOne(id);
		if (null == teamAdv) {
			throw new FailReturnObject(ExceptionResultEnum.TEAM_ADVERTISE_ID_ERROR);
		}
		teamAdvService.delete(teamAdv);
		return new ResponseBody("删除成功");
	}

}

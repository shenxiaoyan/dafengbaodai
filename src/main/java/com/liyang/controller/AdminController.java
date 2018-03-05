//package com.liyang.controller;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.liyang.domain.otherinsureinterestperson.OtherInsureInterestPerson;
//import com.liyang.domain.otherinsureinterestperson.OtherInsureInterestPersonRepository;
//import com.liyang.domain.submitproposal.SubmitProposal;
//import com.liyang.domain.submitproposal.SubmitProposalRepository;
//import com.liyang.domain.upgradeapply.UpgradeApply;
//import com.liyang.helper.ResponseBody;
//import com.liyang.service.AdminService;
//
//@RestController
//@RequestMapping("/rest2/dafeng")
//public class AdminController {
//	
//	@Autowired
//	private AdminService adminService;
//	
//	@Autowired
//	private OtherInsureInterestPersonRepository a ;
//	
//	/*
//	 * 查看所有粉丝升级
//	 */
//	@RequestMapping(value="/queryUpgrade",method=RequestMethod.GET)
//	public ResponseBody queryUpgrade(){
//		List<UpgradeApply> list = adminService.queryUpgrade();
//		ResponseBody response =  new ResponseBody(0,"ok");
//		response.setData(list);
//		return response;
//	}	
//	
//	/*
//	 * 同意粉丝认证
//	 */
//	@RequestMapping(value="/acceptUpgrade",method=RequestMethod.POST)
//	public ResponseBody acceptUpgrade(@RequestBody @Valid UpgradeApply apply){
//		adminService.accpetUpgrade(apply);
//		return new ResponseBody(0,"ok");
//	}
//	
////	@RequestMapping(value="/zyftest",method=RequestMethod.GET)
////	public void test(){
////		List<OtherInsureInterestPerson> list = a.findAll();
////		System.out.println(list);
////	}
//}

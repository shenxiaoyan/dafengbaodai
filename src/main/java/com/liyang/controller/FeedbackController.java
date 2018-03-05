package com.liyang.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.feedback.Feedback;
import com.liyang.helper.ResponseBody;
import com.liyang.service.FeedbackService;
import com.liyang.service.FileUploadService;


/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/feedback")
public class FeedbackController {

	@Autowired
	FeedbackService feedbackService;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	FileUploadService fileUploadService;

	@RequestMapping(value = "/submitFB", method = RequestMethod.POST)
	public ResponseBody upload(@RequestBody Map<String, Object> requestBody, HttpServletRequest request) {
		String content = (String) requestBody.get("content");
		String url = "";
		if (requestBody.get("imgUrl") != null) {
			url = requestBody.get("imgUrl").toString();
		}
		// 封装反馈意见
		Feedback feedback = new Feedback();
		feedback.setContent(content);
		feedback.setSubmitTime(new Date());
		feedback.setSubmitCustomer(customerRepository.findByToken(request.getHeader("token")));
		feedback.setImgURL(url);
		feedbackService.save(feedback);

		ResponseBody responseBody = new ResponseBody(0, "ok");
		return responseBody;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseBody search(@RequestParam(value = "beginDate", required = false) String begin,
			@RequestParam(value = "endDate", required = false) String end,
			@PageableDefault(value = 15, sort = { "submitTime" }, direction = Direction.DESC) Pageable pageable) {

		ResponseBody body = new ResponseBody();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = null;
		Date endDate = null;
		if (!StringUtils.isEmpty(begin)) {
			try {
				beginDate = sdf.parse(begin);
			} catch (ParseException e) {
				e.printStackTrace();
				body.setErrorCode(100);
				body.setErrorInfo("beginDate格式错误，请按yyyy-MM-dd格式查询");
				return body;
			}
		}
		if (!StringUtils.isEmpty(end)) {
			try {
				endDate = sdf.parse(end);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(endDate);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				endDate = calendar.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
				body.setErrorCode(100);
				body.setErrorInfo("endDate格式错误，请按yyyy-MM-dd格式查询");
				return body;
			}
		}
		Page<Feedback> resultPage = feedbackService.search(beginDate, endDate, pageable);
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		for (Feedback feedback : resultPage) {
			Map<String, Object> feedbackMap = new HashMap<String, Object>();
			feedbackMap.put("content", feedback.getContent());
			feedbackMap.put("submitTime", sdf2.format(feedback.getSubmitTime()));
			String imgURL = feedback.getImgURL();
			if (imgURL == null) {
				imgURL = "";
			} else {
				imgURL = imgURL.substring(1, feedback.getImgURL().length() - 1).replaceAll(" ", "");
			}
			feedbackMap.put("imgURL", "".equals(imgURL) ? new String[0] : imgURL.split(","));
			feedbackMap.put("submitCustomer",
					feedback.getSubmitCustomer() == null ? null : feedback.getSubmitCustomer().getNickname());
			resultList.add(feedbackMap);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("feedbacks", resultList);
		returnMap.put("size", resultPage.getSize());
		returnMap.put("totalElements", resultPage.getTotalElements());
		returnMap.put("totalPages", resultPage.getTotalPages());
		returnMap.put("number", resultPage.getNumber());
		body.setErrorCode(0);
		body.setErrorInfo("ok");
		body.setData(returnMap);
		return body;
	}

}

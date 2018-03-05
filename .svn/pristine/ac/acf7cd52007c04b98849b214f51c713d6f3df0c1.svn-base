package com.liyang.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liyang.domain.banner.Banner;
import com.liyang.domain.banner.BannerForSearch;
import com.liyang.domain.banner.BannerForUpdate;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.BannerService;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng/banner")
public class BannerController {

	@Autowired
	private BannerService bannerService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseBody save(@RequestBody Banner banner) {
		bannerService.save(banner);
		return new ResponseBody(0, "success");
	}

	@RequestMapping(value = "/onshelf", method = RequestMethod.PATCH)
	public ResponseBody onshelf(@RequestBody BannerForUpdate updateBean) {
		Banner banner = bannerService.findById(updateBean.getId());
		if (banner == null) {
			throw new FailReturnObject(ExceptionResultEnum.BANNER_ID_ERROR);
		}
		bannerService.updateOnshelf(banner);
		return new ResponseBody(0, "success");
	}

	@RequestMapping(value = "/offshelf", method = RequestMethod.PATCH)
	public ResponseBody offshelf(@RequestBody BannerForUpdate updateBean) {
		Banner banner = bannerService.findById(updateBean.getId());
		if (banner == null) {
			throw new FailReturnObject(ExceptionResultEnum.BANNER_ID_ERROR);
		}
		bannerService.updateOffshelf(banner);
		return new ResponseBody(0, "success");
	}

	@RequestMapping(value = "/findAllEnabledForMobile", method = RequestMethod.GET)
	public ResponseBody findAllEnabled() {// 移动端获取全部上架的banner
		List<Banner> banners = bannerService.findAllEnabled();
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		for (Banner banner : banners) {
			Map<String, Object> bannerMap = new HashMap<String, Object>();
			bannerMap.put("id", banner.getId());
			bannerMap.put("name", banner.getName());
			bannerMap.put("imgURL", banner.getImgURL());
			bannerMap.put("weight", banner.getWeight());
			bannerMap.put("state", banner.getState());
			returnList.add(bannerMap);
		}
		ResponseBody body = new ResponseBody(0, "success");
		body.setData(returnList);
		return body;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseBody search(HttpServletRequest request,
			@PageableDefault(value = 15, sort = { "lastModifiedAt" }, direction = Direction.DESC) Pageable pageable) {
		BannerForSearch search = new BannerForSearch();
		search.setName(request.getParameter("name"));
		search.setStateCode(request.getParameter("stateCode"));
		Page<Banner> result = bannerService.search(search, pageable);

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Banner banner : result) {
			Map<String, Object> bannerMap = new HashMap<String, Object>();
			bannerMap.put("id", banner.getId());
			bannerMap.put("name", banner.getName());
			bannerMap.put("imgURL", banner.getImgURL());
			bannerMap.put("weight", banner.getWeight());
			bannerMap.put("state", banner.getState());
			bannerMap.put("createdAt", sdf.format(banner.getCreatedAt()));
			bannerMap.put("lastModifiedAt", sdf.format(banner.getLastModifiedAt()));
			returnList.add(bannerMap);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("content", returnList);
		returnMap.put("size", result.getSize());
		returnMap.put("totalElements", result.getTotalElements());
		returnMap.put("totalPages", result.getTotalPages());
		returnMap.put("number", result.getNumber());
		ResponseBody body = new ResponseBody(0, "ok");
		body.setData(returnMap);
		return body;
	}

	@RequestMapping(value = "/update", method = RequestMethod.PATCH)
	public ResponseBody update(@RequestBody BannerForUpdate forUpdate) {
		Banner banner = bannerService.findById(forUpdate.getId());
		if (banner == null) {
			throw new FailReturnObject(ExceptionResultEnum.BANNER_ID_ERROR);
		}
		bannerService.updateName(banner, forUpdate);
		return new ResponseBody(0, "success");
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseBody delete(@RequestParam Integer id) {
		Banner banner = bannerService.findById(id);
		if (banner == null) {
			throw new FailReturnObject(ExceptionResultEnum.BANNER_ID_ERROR);
		}
		bannerService.delete(banner);
		return new ResponseBody(0, "success");
	}

	@RequestMapping(value = "/orderImg", method = RequestMethod.PATCH)
	public ResponseBody orderImg(@RequestBody BannerForUpdate updateBean) {
		if (updateBean.getIds().size() > Banner.ONSHELF_MAX) {
			throw new FailReturnObject(ExceptionResultEnum.BANNER_ONSHELF_NUMBER_ERROR);
		}
		for (int i = 0; i < updateBean.getIds().size(); i++) {
			Banner banner = bannerService.findById(updateBean.getIds().get(i));
			if (!"ONSHELF".equals(banner.getState().getStateCode())) {
				throw new FailReturnObject(ExceptionResultEnum.BANNER_STATE_ERROR);
			}
			bannerService.updateWeight(banner, i + 1);
		}
		return new ResponseBody(0, "success");
	}
}

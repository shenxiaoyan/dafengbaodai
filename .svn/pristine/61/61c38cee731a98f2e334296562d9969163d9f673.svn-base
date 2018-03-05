package com.liyang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.liyang.domain.product.Product;
import com.liyang.domain.product.ProductTO;
import com.liyang.domain.productcompany.ProductCompany;
import com.liyang.domain.productcompany.ProductCompanyTO;
import com.liyang.helper.ResponseBody;
import com.liyang.service.ProductCompanyService;
import com.liyang.service.ProductService;
import com.liyang.util.PageUtil;

/**
 * @author Adam
 * @version 创建时间：2018年2月1日 上午10:06:47
 */
@RestController
@RequestMapping("/dafeng/product")
public class ProductComtroller {

	@Autowired
	ProductCompanyService proCompanyService;
	@Autowired
	ProductService productService;

	// -------------------Web ProductCompany Methods ----------------------------

	/**
	 * 新增产品所属保险公司
	 * 
	 * @param proCompany
	 * @return
	 */
	@PostMapping("/company/add")
	public ResponseBody addCompany(@RequestBody(required = true) ProductCompany proCompany) {
		proCompany.validate();
		proCompanyService.save(proCompany);
		return new ResponseBody("添加成功");
	}

	/**
	 * 产品公司查询
	 * 
	 * @param proCompany
	 * @param pabealbe
	 * @return
	 */
	@PostMapping("/company/query")
	public ResponseBody query(@RequestBody(required = false) ProductCompanyTO proCompanyTO,
			@PageableDefault(value = 15, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
		Page<ProductCompany> proCompanyPage = proCompanyService.query(proCompanyTO, pageable);
		List<ProductCompanyTO> comProductTOList = proCompanyService
				.copyListToProductCompanyTO(proCompanyPage.getContent());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("productCompanies", comProductTOList);
		resultMap.put("page", PageUtil.getPageData(proCompanyPage));
		return new ResponseBody(resultMap);
	}

	@GetMapping("/company/reverse")
	public ResponseBody reverseProductCompanyState(@RequestParam(required = true) Integer id) {
		ProductCompany proCompany = proCompanyService.findOne(id);
		proCompany = proCompanyService.updateState(proCompany);
		return new ResponseBody("设为" + proCompany.getState().getLabel() + "成功");
	}

	// -------------------Web Product Methods ----------------------------

	@PostMapping("/add")
	public ResponseBody addProduct(@RequestBody(required = true) ProductTO productTO) {
		ProductCompany proCompany = proCompanyService.findOne(productTO.getProductCompanyId());
		Product product = new Product();
		BeanUtils.copyProperties(productTO, product);
		product.setProductCompany(proCompany);
		product.validate();
		productService.save(product);
		return new ResponseBody("产品新增成功");
	}

	/**
	 * 产品列表、查询
	 * 
	 * @param productTO
	 * @param pageable
	 * @return
	 */
	@PostMapping("/query")
	public ResponseBody query(@RequestBody(required = false) ProductTO productTO,
			@PageableDefault(value = 15, sort = "lastModifiedAt", direction = Direction.DESC) Pageable pageable) {
		Page<Product> productPage = productService.query(productTO, pageable);
		List<ProductTO> productTOList = productService.copyListProperties(productPage.getContent());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("products", productTOList);
		resultMap.put("page", PageUtil.getPageData(productPage));
		return new ResponseBody(resultMap);
	}

	/**
	 * 更新
	 * 
	 * @param productTO
	 * @return
	 */
	@PostMapping("/update")
	public ResponseBody update(@RequestBody(required = true) ProductTO productTO) {
		Product product = productService.findOne(productTO.getId());
		productService.update(product, productTO);
		return new ResponseBody("设置成功");
	}

	/**
	 * 状态切换
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/reverse")
	public ResponseBody reverseProductState(@RequestParam(required = true) Integer id) {
		Product product = productService.findOne(id);
		product = productService.updateState(product);
		return new ResponseBody(product.getState().getLabel() + "成功");
	}

}

package com.liyang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.domain.jiandenonlocalidcard.NonlocalIdcard;
import com.liyang.domain.jiandenonlocalidcard.NonlocalIdcardRepository;
import com.liyang.util.ExcelUtil;

/**
 * @author Administrator
 *
 */
@Service
public class NonlocalIdcardService {
	
	@Autowired
	NonlocalIdcardRepository nonlocIdRepository;
	
	public String batchImport(MultipartFile file) {
		List<NonlocalIdcard> list = ExcelUtil.readExcel(file, NonlocalIdcard.class);
		//入库前是否删除库中已有数据，待确认
//		nonlocIdRepository.deleteAll();
		nonlocIdRepository.save(list);
		return "成功新增"+list.size()+"条数据";
	}
}

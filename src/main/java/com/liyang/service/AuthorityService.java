package com.liyang.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.authority.Authority;
import com.liyang.domain.authority.AuthorityRepository;

/**
 * @author Administrator
 *
 */
@Service
public class AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	@PostConstruct
	public void init() {
		// List<Authority> findAll = authorityRepository.findAll();
		// if(findAll == null || findAll.isEmpty()){
		if (authorityRepository.count() <= 0) {
			authorityRepository.save(new Authority("前端用户管理"));
			authorityRepository.save(new Authority("后台权限管理"));
			authorityRepository.save(new Authority("询价记录管理"));
			authorityRepository.save(new Authority("出单记录管理"));
			authorityRepository.save(new Authority("保单记录管理"));
			authorityRepository.save(new Authority("系统公告"));
		}
	}
}

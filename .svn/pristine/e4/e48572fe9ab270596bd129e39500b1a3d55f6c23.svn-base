package com.liyang.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liyang.domain.account.Account;
import com.liyang.domain.account.AccountRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.upgradeapply.UpgradeApply;
import com.liyang.domain.upgradeapply.UpgradeApplyRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.DisplayException;
import com.liyang.util.FailReturnObject;

/**
 * @author Administrator
 *
 */
@Service
public class AdminService {
	
	@Autowired
	private UpgradeApplyRepository upgradeApplyRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<UpgradeApply> queryUpgrade(){
		return upgradeApplyRepository.query();
	}
	
	
	@Transactional
	public void accpetUpgrade(UpgradeApply apply){
		List<Customer> list= customerRepository.getByPhone(apply.getPhone());
		if(list.size() == 0){
			throw new FailReturnObject(ExceptionResultEnum.ADMIN_NOUSERFOUND_ERROR);
//			throw new DisplayException("系统中没有这个用户");
		}
		if(list.get(0).getTag() == 1){
			throw new FailReturnObject(ExceptionResultEnum.ADMIN_ACCPETUPGRADE_ERROR);
//			throw new DisplayException("该用户已经通过实名认证");
		}
		changeApplyState(apply);
		Account account = saveAccount(apply);
		changeUserState(apply , account);
	}
	
	/**
	 * 修改认证后的申请状态
	 * @param apply
	 */
	public void changeApplyState(UpgradeApply apply){
		UpgradeApply applyReal = upgradeApplyRepository.queryById(apply.getId());
		applyReal.setAuditTime(new Date());
		applyReal.setStatus(1);
		
		upgradeApplyRepository.save(applyReal);
	}
	
	/**
	 * 修改实名认证后的粉丝状态
	 * @param apply
	 * @param account
	 */
	private void changeUserState(UpgradeApply apply , Account account){
		List<Customer> list = customerRepository.getByPhone(apply.getPhone());
		if(list.size() == 0){
			throw new FailReturnObject(ExceptionResultEnum.ADMIN_NOUSERFOUND_ERROR);
//			throw new DisplayException("系统中没有这个用户");
		}
		list.get(0).setAccount(account);
		list.get(0).setTag(1);
	}
	
	/**
	 * 新建实名认证后的账户
	 * @param apply
	 * @return
	 */
	private Account saveAccount(UpgradeApply apply){
			Account account = new Account();
			account.setBalance(0);
			account.setCreateTime(new Date());
			account.setIdentity(apply.getIdentify());
			account.setPayAccountId(apply.getPayAccountId());
			account.setPayAccountPlatform(apply.getPayAccountPlatform());
			account.setPhone(apply.getPhone());
			account.setRealName(apply.getRealName());
			account.setStatus(0);
			account.setTotalBalance(0);
			accountRepository.save(account);
			
			return account;
	}
}

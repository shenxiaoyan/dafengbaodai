package com.liyang.domain.claimPolicyRewards;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorAct;


/**
 * 理赔
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="claim_policy_reward_act")
public class ClaimPolicyRewardAct extends AbstractAuditorAct<ClaimPolicyRewardState>{
	
	public ClaimPolicyRewardAct(){
		super();
	}
	
	public ClaimPolicyRewardAct(String label, String actCode, Integer sort, ActGroup actGroup){
		super(label, actCode, sort, actGroup);
	}
	
	
}

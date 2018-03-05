package com.liyang.domain.claimPolicyRewards;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorState;

/**
 * 理赔状态
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="claim_policy_reward_state")
public class ClaimPolicyRewardState extends AbstractAuditorState<ClaimPolicyReward, ClaimPolicyRewardAct>{
//	<ClaimPolicyReward, ClaimPolicyRewardAct>
	
	public ClaimPolicyRewardState(){
		super();
	}
	
	public ClaimPolicyRewardState(String label, Integer sort, String stateCode){
		super(label, sort, stateCode);
	}
}

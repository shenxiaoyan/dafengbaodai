package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.claimPolicyRewards.ClaimPolicyReward;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardAct;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardLog;
import com.liyang.domain.claimPolicyRewards.ClaimPolicyRewardState;

/**
 * @author Administrator
 *
 */
@Component
public class ClaimPolicyRewardRestEventListener extends AuditorRestEventListener<ClaimPolicyReward, ClaimPolicyRewardState, ClaimPolicyRewardAct, ClaimPolicyRewardLog>{
	
}

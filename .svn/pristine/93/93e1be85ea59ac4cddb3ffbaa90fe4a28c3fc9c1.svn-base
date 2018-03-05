package com.liyang.client.xiaoma;

import com.liyang.client.BaseServiceObserveNode;
import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.client.IServiceObserve;
import com.liyang.domain.submitproposal.SubmitProposal;
import com.liyang.domain.submitproposal.SubmitProposalRepository;

/**
 * @author Administrator
 *
 */
public class ServiceObserveDbPersistSubmitProposal extends BaseServiceObserveNode implements IServiceObserve {
	SubmitProposalRepository submitProposalRepository;

	public ServiceObserveDbPersistSubmitProposal(SubmitProposalRepository submitProposalRepository) {
		this.submitProposalRepository = submitProposalRepository;
	}

	@Override
	public IMessage notifyBeforeCall(IMessage message) {
		return null;
	}

	@Override
	public IResult notifyAfterCall(IMessage message, IResult result) {
		ResultSubmitProposal finalRes = (ResultSubmitProposal) result;
		if (finalRes != null && null != finalRes.getSubmitProposal()) {
			// 保存提交核保数据
			SubmitProposal sp = submitProposalRepository.save(finalRes.getSubmitProposal());
			finalRes.setSubmitProposal(sp);
		}
		return finalRes;
	}

	@Override
	public void notifyException(IMessage message, IResult result, Exception exception) {

	}

}

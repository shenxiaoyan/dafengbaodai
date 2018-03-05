package com.liyang.client.xiaoma;

import com.liyang.client.BaseServiceObserveNode;
import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.client.IServiceObserve;
import com.liyang.domain.querylatestpolicy.QueryLatestPolicyRepository;

/**
 * @author Administrator
 *
 */
public class ServiceObserveDbPersistQueryLatestPolicy extends BaseServiceObserveNode implements IServiceObserve {
	private QueryLatestPolicyRepository queryLatestPolicyRepository;

	public ServiceObserveDbPersistQueryLatestPolicy(QueryLatestPolicyRepository queryLatestPolicyRepository) {
		this.setQueryLatestPolicyRepository(queryLatestPolicyRepository);
	}

	@Override
	public IMessage notifyBeforeCall(IMessage message) {
		return null;
	}

	@Override
	public IResult notifyAfterCall(IMessage message, IResult result) {
		ResultQueryLatestPolicy finalResult = (ResultQueryLatestPolicy) result;
		if (null != finalResult && null != finalResult.getQueryLatestPolicy()) {
			getQueryLatestPolicyRepository().save(finalResult.getQueryLatestPolicy());
		}
		return finalResult;
	}

	@Override
	public void notifyException(IMessage message, IResult result, Exception exception) {
		((MessageQueryLatestPolicy)message).logger.error("查询续保callService异常："+exception.getMessage());
	}

	/**
	 * @return the queryLatestPolicyRepository
	 */
	public QueryLatestPolicyRepository getQueryLatestPolicyRepository() {
		return queryLatestPolicyRepository;
	}

	/**
	 * @param queryLatestPolicyRepository
	 *            the queryLatestPolicyRepository to set
	 */
	public void setQueryLatestPolicyRepository(QueryLatestPolicyRepository queryLatestPolicyRepository) {
		this.queryLatestPolicyRepository = queryLatestPolicyRepository;
	}

}

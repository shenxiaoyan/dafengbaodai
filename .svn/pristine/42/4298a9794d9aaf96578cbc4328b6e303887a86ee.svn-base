package com.liyang.client.xiaoma;

import com.liyang.client.BaseServiceObserveNode;
import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.client.IServiceObserve;
import com.liyang.domain.querypayment.QueryPayRepository;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
public class ServiceObserveDbPersistQueryPayState extends BaseServiceObserveNode implements IServiceObserve {

	QueryPayRepository queryPayRepository;

	public ServiceObserveDbPersistQueryPayState(QueryPayRepository queryPayRepository) {
		this.queryPayRepository = queryPayRepository;
	}

	@Override
	public IMessage notifyBeforeCall(IMessage message) {
		return null;
	}

	@Override
	public IResult notifyAfterCall(IMessage message, IResult result) {
		ResultQueryPayState finalRes = (ResultQueryPayState) result;
		if (finalRes != null && null != finalRes.getQueryPay()) {
			MessageQueryPayState msg = (MessageQueryPayState) message;
			queryPayRepository.save(finalRes.getQueryPay());
			String response = (String) finalRes.getRawResponse();
			msg.getLogger().info("查询支付直接返回：" + JSONObject.fromObject(response).toString());
		}
		return finalRes;
	}

	@Override
	public void notifyException(IMessage message, IResult result, Exception exception) {

	}

}

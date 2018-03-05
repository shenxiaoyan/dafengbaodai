package com.liyang.client;

import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.util.FailReturnObject;

/**
 * 
 * @author Administrator
 *
 */
public abstract class BaseService implements IService {
	protected String partUrl;
	protected IClient client;
	private IServiceObserve serviceObserve;
	private IResponseSupplier responseSupplier;
	boolean skipAutoValidate;

	public BaseService(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier,
			boolean skipAutoValidate) {
		this.client = client;
		this.serviceObserve = serviceObserve;
		if (responseSupplier != null) {
			this.responseSupplier = responseSupplier;
			this.responseSupplier.setService(this);
		}
		this.skipAutoValidate = skipAutoValidate;
	}

	public BaseService(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier) {
		this(client, serviceObserve, responseSupplier, false);
	}

	/**
	 * @return the partUrl
	 */
	@Override
	public String getPartUrl() {
		return partUrl;
	}

	/**
	 * @param partUrl
	 *            the partUrl to set
	 */
	@Override
	public void setPartUrl(String partUrl) {
		this.partUrl = partUrl;
	}

	/**
	 * @return the client
	 */
	@Override
	public IClient getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	@Override
	public void setClient(IClient client) {
		this.client = client;
	}

	@Override
	public IResult callService(IMessage generalMessage) throws Exception {
		IResult result = null;
		Object response = null;
		if (serviceObserve != null) {
			IMessage msg = serviceObserve.notifyBeforeCall(generalMessage);
			if (msg != null) {
				generalMessage = msg;
			}
		}
		try {
			if (!skipAutoValidate) {
				detailValidate(generalMessage);
			}
			response = detailCallService(generalMessage);
			result = (IResult) parseResult(response, generalMessage);
			if (result != null) {
				result.setRawResponse(response);
			}
			if (serviceObserve != null) {
				IResult res = serviceObserve.notifyAfterCall(generalMessage, result);
				if (res != null) {
					result = res;
				}
			}
		} catch (Exception exception) {
			System.out.println("【callService方法异常】！！！"+"result:"+result+"  exceptionMsg:"+exception.getMessage());
			if (serviceObserve != null) {
				serviceObserve.notifyException(generalMessage, result, exception);
			}
			throw new FailReturnObject(333, exception.getMessage());
		}
		return result;
	}

	/**
	 * 将验证抽出来，子类继承后可重写该方法，修改默认验证方法或者不验证
	 * @param message
	 * @throws ExceptionTiananParamInvliad
	 */
	public void detailValidate(IMessage message) throws ExceptionTiananParamInvliad {
		message.validate();
	}

	@Override
	public String getUrl() {
		String url = "";
		if (null != partUrl && partUrl.toLowerCase().startsWith("http")) {
			url = partUrl;
		} else {
			url = client.getBaseUrl() + partUrl;
		}
		return url;
	}

	public Object detailCallService(IMessage generalMessage) throws Exception {
		Object result = null;
		if (responseSupplier == null) {
			throw new Exception("请提供具体的请求响应提供者");
		}
		System.out.println(getClass().getName()+" execute detailCallService：");
		result = responseSupplier.getResponse(generalMessage);
		return result;
	}

	/**
	 * @return the serviceObserve
	 */
	public IServiceObserve getServiceObserve() {
		return serviceObserve;
	}

	/**
	 * @param serviceObserve
	 *            the serviceObserve to set
	 */
	public void setServiceObserve(IServiceObserve serviceObserve) {
		this.serviceObserve = serviceObserve;
	}

	/**
	 * @return the responsSupplier
	 */
	public IResponseSupplier getResponsSupplier() {
		return responseSupplier;
	}

	/**
	 * @param responsSupplier
	 *            the responsSupplier to set
	 */
	public void setResponsSupplier(IResponseSupplier responsSupplier) {
		this.responseSupplier = responsSupplier;
	}

}

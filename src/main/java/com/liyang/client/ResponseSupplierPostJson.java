package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public class ResponseSupplierPostJson extends BaseResponseSupplier implements IResponseSupplier {

	public ResponseSupplierPostJson(IService service) {
		super(service);
	}

	public ResponseSupplierPostJson() {
	}

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		if (null == this.service) {
			throw new Exception("请提供请求service对象");
		}
		return postJson(generalMessage);
	}

	protected Object postJson(IMessage generalMessage) throws Exception {
		String url = service.getUrl();
		Object params = service.parseParameters(generalMessage);
		Object headers = service.parseHeaders(generalMessage);
		Object response = null;
		response = service.getClient().postJson(url, (String) params, headers);
		return response;
	}

}

package com.liyang.client;

/**
 * @author Administrator
 *
 */
public class ResponseSupplierPostForm extends BaseResponseSupplier implements IResponseSupplier {

	public ResponseSupplierPostForm(IService service) {
		super(service);
	}

	public ResponseSupplierPostForm() {
	}

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		if (null == this.service) {
			throw new Exception("请提供请求service对象");
		}
		return postData(generalMessage);
	}

	protected Object postData(IMessage generalMessage) throws Exception {
		String url = this.service.getUrl();
		Object params = this.service.parseParameters(generalMessage);
		Object headers = this.service.parseHeaders(generalMessage);
		Object response = null;
		response = this.service.getClient().postData(url, params, headers);
		return response;
	}

}

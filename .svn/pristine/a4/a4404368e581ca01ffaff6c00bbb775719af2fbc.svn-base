package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public class ResponseSupplierGetForm extends BaseResponseSupplier implements IResponseSupplier {

	public ResponseSupplierGetForm(IService service) {
		super(service);
	}

	public ResponseSupplierGetForm() {
	}

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		if(null == this.service){
			throw new Exception("请提供请求service对象");
		}
		return getData(generalMessage);
	}

	protected Object getData(IMessage generalMessage) throws Exception {
		String url = this.service.getUrl();
		Object params = this.service.parseParameters(generalMessage);
		Object headers = this.service.parseHeaders(generalMessage);
		Object response = null;
		response = this.service.getClient().getData(url, params, headers);
		return response;
	}

}

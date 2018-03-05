package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public class ResponseSupplierPostFile extends BaseResponseSupplier implements IResponseSupplier {

	public ResponseSupplierPostFile(IService service) {
		super(service);
	}
	
	public ResponseSupplierPostFile() {
	}

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {
		if(null == this.service){
			throw new Exception("请提供请求service对象");
		}
		return postFile(generalMessage);
	}

	protected Object postFile(IMessage generalMessage) throws Exception {
		String url = this.service.getUrl();
		Object params = this.service.parseParameters(generalMessage);
		Object headers = this.service.parseHeaders(generalMessage);
		Object response = null;
		response = this.service.getClient().postFile(url, params, headers);
		return response;
	}

}

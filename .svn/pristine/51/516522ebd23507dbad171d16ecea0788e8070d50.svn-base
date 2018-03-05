package com.liyang.client;

/**
 * 
 * @author Administrator
 *
 */
public abstract class BaseResponseSupplier implements IResponseSupplier {
	protected IService service;

	public BaseResponseSupplier(IService service) {
		this.service = service;
	}

	public BaseResponseSupplier() {
	}

	@Override
	public IService getService() {
		return service;
	}

	@Override
	public IResponseSupplier setService(IService service) {
		this.service = service;
		return this;
	}

}

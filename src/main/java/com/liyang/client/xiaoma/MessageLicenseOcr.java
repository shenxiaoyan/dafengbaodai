package com.liyang.client.xiaoma;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.liyang.client.IMessage;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class MessageLicenseOcr implements IMessage {
	private MultipartFile imgFile;
	private Logger logger;

	public MessageLicenseOcr(MultipartFile imgFile, Logger logger) {
		this.imgFile = imgFile;
		this.setLogger(logger);
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {

	}

	@Override
	public void initDefaultValue() throws Exception {

	}

	/**
	 * @return the imgFile
	 */
	public MultipartFile getImgFile() {
		return imgFile;
	}

	/**
	 * @param imgFile
	 *            the imgFile to set
	 */
	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}

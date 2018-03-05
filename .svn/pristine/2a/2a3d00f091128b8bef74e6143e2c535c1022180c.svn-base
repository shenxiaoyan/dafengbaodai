package com.liyang.client.tianan.dto;

import org.springframework.util.StringUtils;

import com.liyang.client.IDto;
import com.liyang.client.tianan.enums.FileTypeEnums;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * 附件信息
 * 
 * @author Administrator
 *
 */
public class AttachmentInfoDto implements IDto {
	private final Integer fileSize;

	private final String fileType;

	private final String fileName;

	private final String fileByteStr;

	/**
	 * 附件大小
	 * 
	 * @return the fileSize
	 */
	public Integer getFileSize() {
		return fileSize;
	}

	/**
	 * 附件类型
	 * 
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * 附件名称
	 * 
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 附件字符串
	 * 
	 * @return the fileByteStr
	 */
	public String getFileByteStr() {
		return fileByteStr;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		if (this.getFileSize()==0) {
			throw new ExceptionTiananParamInvliad("附件大小不能为0");
		}
		if (StringUtils.isEmpty(this.getFileType())) {
			throw new ExceptionTiananParamInvliad("附件类型不能为空");
		}
		if (StringUtils.isEmpty(this.getFileName())) {
			throw new ExceptionTiananParamInvliad("附件名称不能为空");
		}else{
			if (!FileTypeEnums.isExist(this.fileType)) {
				throw new ExceptionTiananParamInvliad("附件类型不匹配");
			}
		}
		if (StringUtils.isEmpty(this.getFileByteStr())) {
			throw new ExceptionTiananParamInvliad("附件字符串不能为空");
		}
		
	
		
	}

	public AttachmentInfoDto(Integer fileSize, String fileType, String fileName, String fileByteStr)
			throws ExceptionTiananParamInvliad {
		super();
		this.fileSize = fileSize;
		this.fileType = fileType;
		this.fileName = fileName;
		this.fileByteStr = fileByteStr;
		validate();
	}

}

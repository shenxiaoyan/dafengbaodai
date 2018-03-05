package com.liyang.client.tianan;

import java.util.List;

import org.springframework.util.StringUtils;

import com.liyang.client.BaseRequestHeader;
import com.liyang.client.IMessage;
import com.liyang.client.tianan.dto.AttachmentInfoDto;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;

/**
 * @author Administrator
 *
 */
public class MessageUploadAttach implements IMessage {
	// public MessageUploadAttach() throws ExceptionTiananInitFailed {
	// BaseRequestHeader requestHeader =
	// FacadeService.getInstance().getDefaultRequestHeader();
	// setRequestHead(requestHeader);
	// }

	private final BaseRequestHeader requestHead;

	private final String proposalNo;

	private final List<AttachmentInfoDto> attachmentList;

	/**
	 * 请求头信息
	 * 
	 * @return the requestHead
	 */
	public BaseRequestHeader getRequestHead() {
		return requestHead;
	}

	/**
	 * 主投保单号
	 * 
	 * @return the proposalNo
	 */
	public String getProposalNo() {
		return proposalNo;
	}

	/**
	 * 附件信息集合
	 * 
	 * @return the attachmentList
	 */
	public List<AttachmentInfoDto> getAttachmentList() {
		return attachmentList;
	}

	@Override
	public void validate() throws ExceptionTiananParamInvliad {
		 if (StringUtils.isEmpty(this.getRequestHead())) {
				throw new ExceptionTiananParamInvliad("请求头不能为空");
			}
		 if (StringUtils.isEmpty(this.getProposalNo())) {
				throw new ExceptionTiananParamInvliad("投保单号不能为空");
			}
		 if (this.getAttachmentList().size()==0) {
				throw new ExceptionTiananParamInvliad("附件信息不能为空");
			}else{
				for (AttachmentInfoDto attachmentInfoDto : this.getAttachmentList()) {
					attachmentInfoDto.validate();
				}
			}
	}

	@Override
	public void initDefaultValue() {

	}

	public MessageUploadAttach(String baseUrl, String tradeNo, String proposalNo, List<AttachmentInfoDto> attachmentList)
			throws ExceptionTiananInitFailed, ExceptionTiananParamInvliad {
		if (StringUtils.isEmpty(tradeNo)) {
			throw new ExceptionTiananInitFailed("tradeNo为空");
		}
		BaseRequestHeader requestHeader = FacadeService.getInstance(baseUrl).getDefaultRequestHeader(tradeNo);
		this.requestHead = requestHeader;
		initDefaultValue();
		this.proposalNo = proposalNo;
		this.attachmentList = attachmentList;
		validate();
	}

}

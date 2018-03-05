package com.liyang.util;

import org.springframework.context.ApplicationEvent;

import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.base.AbstractWorkflowLog;

/**
 * @author Administrator
 *
 */
public class WechatImageAsyncFetchEvent extends ApplicationEvent {

	private AbstractWorkflowEntity source;
	private AbstractWorkflowFile file;

	public WechatImageAsyncFetchEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub

	}

	public WechatImageAsyncFetchEvent(AbstractWorkflowEntity source, AbstractWorkflowFile file) {
		super(source);
		this.source = source;
		this.file = file;

	}

	@Override
	public AbstractWorkflowEntity getSource() {
		return source;
	}

	public void setSource(AbstractWorkflowEntity source) {
		this.source = source;
	}

	public AbstractWorkflowFile getFile() {
		return file;
	}

	public void setFile(AbstractWorkflowFile file) {
		this.file = file;
	}

}

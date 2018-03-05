package com.liyang.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.util.CommonUtil;

/**
 * @author Administrator
 *
 */
public class CustomContent implements IContent{
	
	private Object data;
	private String desc;
	private Ext ext;
	private String sound;
	
	@JsonProperty("Data")
	public Object getData() {
		return data;
	}

	@JsonProperty("Data")
	public void setData(Object data) {
		this.data = data;
	}
	
	@JsonProperty("Desc")
	public String getDesc() {
		return desc;
	}
	@JsonProperty("Desc")
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@JsonProperty("Ext")
	public String getExt() {
		return CommonUtil.prettyPrint(ext);
	}
	@JsonProperty("Ext")
	public void setExt(Ext ext) {
		this.ext = ext;
	}
	@JsonProperty("Sound")
	public String getSound() {
		return sound;
	}
	@JsonProperty("Sound")
	public void setSound(String sound) {
		this.sound = sound;
	}
	
	static public class Ext{
		private String type;
		private String value;
		private String entityName;
		private String entityType;
		private Integer entityId;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getEntityName() {
			return entityName;
		}
		public void setEntityName(String entityName) {
			this.entityName = entityName.toLowerCase();
		}
		public String getEntityType() {
			return entityType;
		}
		public void setEntityType(String entityType) {
			this.entityType = entityType;
		}
		public Integer getEntityId() {
			return entityId;
		}
		public void setEntityId(Integer entityId) {
			this.entityId = entityId;
		}

		
		
	}
	
	
}

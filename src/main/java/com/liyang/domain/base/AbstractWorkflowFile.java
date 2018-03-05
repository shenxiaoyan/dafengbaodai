package com.liyang.domain.base;


import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liyang.annotation.Info;
import com.liyang.domain.department.Department;
import com.liyang.domain.departmenttype.Departmenttype;
/**
 * 工作流
 * @author Administrator
 *
 * @param <E>
 * @param <A>
 * @param <L>
 */
@MappedSuperclass
public class AbstractWorkflowFile<E extends AbstractWorkflowEntity,A extends AbstractWorkflowAct, L extends AbstractWorkflowLog> extends BaseEntity {
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "entity_id")
	@Info(label="实体ID",placeholder="",tip="",help="",secret="")				
	private E entity;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "act_id")
	@Info(label="行为ID",placeholder="",tip="",help="",secret="")				
	private A act;
	
	@ManyToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="log_id")
	@Info(label="日志",placeholder="",tip="",help="",secret="")				
	private L log;
	
	
	@Column(name="original_file_name",length = 500)
	@Info(label="原文件名",placeholder="",tip="",help="",secret="")				
	private String originalFileName;
	
	@Column(name="new_file_name")
	@Info(label="新文件名",placeholder="",tip="",help="",secret="")				
	private String newFileName;

	@Column(name="file_size")
	@Info(label="文件大小（KB）",placeholder="",tip="",help="",secret="")				
	private Long fileSize;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Info(label="组织类型",placeholder="",tip="上传人的组织类型",help="",secret="只要是这个文件给谁看的时候有用到，目前能想到的是贷款人传文件公司看，公司传文件资方看")				
	private Departmenttype uploaderDepartmenttype;
	
	@Column(name="file_type")
	@Info(label="文件类型",placeholder="",tip="",help="",secret="文件类型，如PNG")				
	private String fileType;
	
	@Column(name="small_image")
	@Info(label="小图",placeholder="",tip="",help="",secret="")				
	private String smallImage;

	@Column(name="middle_image")
	@Info(label="中图",placeholder="",tip="",help="",secret="")				
	private String middleImage;

	@Column(name="large_image")
	@Info(label="大图",placeholder="",tip="",help="",secret="")				
	private String largeImage;
	
	@Column(name="upload_type")
	@Info(label="上传类型",placeholder="",tip="",help="",secret="")				
	private String uploadType;
	//顶级目录
	@Info(label="一级目录",placeholder="",tip="",help="",secret="")				
	private String topcategory="-";
	//子级目录
	@Info(label="二级目录",placeholder="",tip="",help="",secret="")				
	private String subcategory="-";
	
	
	@Column(name="app_code")
	@Info(label="来源的应用",placeholder="",tip="",help="",secret="")			
	private String appCode;
	
	@Column(name="client")
	@Info(label="来源终端",placeholder="",tip="",help="",secret="")			
	private String client;
	
	@Column(name="imei")
	@Info(label="来源设备",placeholder="",tip="",help="",secret="")			
	private String imei;

	@Column(name="ip")
	@Info(label="来源IP",placeholder="",tip="",help="",secret="如果是 web则只有IP没有imei")			
	private String ip;
	
	
	
	
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Departmenttype getUploaderDepartmenttype() {
		return uploaderDepartmenttype;
	}
	public void setUploaderDepartmenttype(Departmenttype uploaderDepartmenttype) {
		this.uploaderDepartmenttype = uploaderDepartmenttype;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getTopcategory() {
		return topcategory;
	}
	public void setTopcategory(String topcategory) {
		this.topcategory = topcategory;
	}
	public String getSmallImage() {
		return smallImage;
	}
	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}
	public String getMiddleImage() {
		return middleImage;
	}
	public void setMiddleImage(String middleImage) {
		this.middleImage = middleImage;
	}
	public String getLargeImage() {
		return largeImage;
	}
	public void setLargeImage(String largeImage) {
		this.largeImage = largeImage;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public A getAct() {
		return act;
	}
	public void setAct(A act) {
		this.act = act;
	}
	public L getLog() {
		return log;
	}
	public void setLog(L log) {
		this.log = log;
	}
	public E getEntity() {
		return entity;
	}
	public void setEntity(E entity) {
		this.entity = entity;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getNewFileName() {
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	

}

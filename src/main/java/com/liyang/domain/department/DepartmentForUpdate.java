package com.liyang.domain.department;

/**
 * 部门更新实体
 * 
 * @author huanghengkun
 * @create 2017年12月28日
 */
public class DepartmentForUpdate {

	private Integer	typeId;
    private String label;
    private String address;
    private String attactPerson;
    private String attactPhone;
	private Integer parentId;
    private String description;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAttactPerson() {
		return attactPerson;
	}

	public void setAttactPerson(String attactPerson) {
		this.attactPerson = attactPerson;
	}

	public String getAttactPhone() {
		return attactPhone;
	}

	public void setAttactPhone(String attactPhone) {
		this.attactPhone = attactPhone;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}

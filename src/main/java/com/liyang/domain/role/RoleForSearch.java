package com.liyang.domain.role;

import java.util.Date;

public class RoleForSearch {
    private Integer id;//编号
    private String  organName;//机构名称
    private String roleName;//角色名称
    private String roleCode;//角色英文字段
    private String stateCode;
    private Date lastModifyAt;//最后更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Date getLastModifyAt() {
        return lastModifyAt;
    }

    public void setLastModifyAt(Date lastModifyAt) {
        this.lastModifyAt = lastModifyAt;
    }
}

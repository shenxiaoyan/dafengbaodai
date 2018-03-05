package com.liyang.domain.departmenttype;

import java.util.Date;

import org.springframework.data.rest.core.config.Projection;


/**
 * 类说明
 * @author Administrator
 *
 */
@Projection(name="departmenttypeProjection",types={Departmenttype.class})
public interface DepartmenttypeProjection {
	Integer getId();
	String getLabel();
	Date getCreatedAt();
	Date getLastModifiedAt();
	String getContactName();
	String getContactPhone();
	String getContactAddress();
	String getCode();
	DepartmenttypeState getState();
}

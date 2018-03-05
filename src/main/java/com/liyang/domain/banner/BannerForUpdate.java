package com.liyang.domain.banner;

import java.util.List;

/**
 * 更新banner
 * @author Administrator
 *
 */
public class BannerForUpdate {
	//用于上架时传递需要上架的banner_id
	private List<Integer> ids;

	private String name;

	private Integer id;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

package com.liyang.domain.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liyang.annotation.Info;
/**
 * @author Administrator
 *
 * @param <E>
 * @param <A>
 */
@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class AbstractAuditorState<E extends AbstractAuditorEntity,A extends AbstractAuditorAct> extends BaseEntity implements Comparable<AbstractAuditorState>{


	public AbstractAuditorState(String label, Integer sort, String stateCode) {
		this.setLabel(label);
		this.sort = sort;
		this.stateCode = stateCode;
	}
	public AbstractAuditorState(){
		super();
	}

	@JsonIgnore
	@ManyToMany
	@JoinTable(joinColumns = { @JoinColumn(name = "state_id") }, inverseJoinColumns = {
			@JoinColumn(name = "act_id") })
	private Set<A> acts = new HashSet<A>();
	
	@JsonIgnore
	@OneToMany(mappedBy="state")
	private Set<E> entities;

	@Info(label="排序",placeholder="",tip="",help="",secret="")			
	private Integer sort=0;

	@Column(name="state_code")
	@Info(label="状态code",placeholder="",tip="",help="",secret="")			
	private String stateCode;
	
	@Column(name="state_group")
	@Info(label="状态分组",placeholder="",tip="",help="",secret="暂时还没用到")			
	private StateGroup stateGroup;
	public StateGroup getStateGroup() {
		return stateGroup;
	}
	public void setStateGroup(StateGroup stateGroup) {
		this.stateGroup = stateGroup;
	}
	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String code) {
		this.stateCode = code;
	}

	public Set<A> getActs() {
		return acts;
	}

	public void setActs(Set<A> acts) {
		this.acts = acts;
	}



	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Set<E> getEntities() {
		return entities;
	}

	public void setEntities(Set<E> entities) {
		this.entities = entities;
	}

	@Override
	public int compareTo(AbstractAuditorState o) {
		// TODO Auto-generated method stub
		if(getSort() > o.getSort()){
			return 1;
		}else if(getSort() == o.getSort()){
			return 0;
		}else{
			return -1;
		}
	}


	public enum StateGroup{
		
	}
}

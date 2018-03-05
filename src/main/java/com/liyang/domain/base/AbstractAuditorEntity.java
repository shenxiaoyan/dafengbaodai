package com.liyang.domain.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.beans.BeanWrapperImpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.message.EnumOperationMessageType;
import com.liyang.service.AbstractAuditorService;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObject;

/**
 * @author Administrator
 *
 * @param <S>
 * @param <A>
 * @param <L>
 */
@MappedSuperclass
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public abstract class AbstractAuditorEntity< S extends AbstractAuditorState, A extends AbstractAuditorAct , L extends AbstractAuditorLog>
		extends BaseEntity {
	
	/**
	 * 获取service
	 * @return
	 */
	@Transient
	public abstract  AbstractAuditorService<?, S, A, L> getService();

	/**
	 * 设置service
	 * @param service
	 */
	public abstract void setService(AbstractAuditorService service);

	/**
	 * LogInstance
	 * @return
	 */
	@Transient
	public abstract L getLogInstance();
	
	/**
	 * LogRepository
	 * @return
	 */
	@Transient
	public abstract LogRepository getLogRepository();

	/**
	 * LogRepository
	 * @param logRepository
	 */
	public abstract void setLogRepository(LogRepository logRepository);

	@JsonIgnore
	@OneToMany(mappedBy = "entity", cascade = CascadeType.ALL)
	private Set<L> logs = new HashSet<L>();

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "state_id", nullable = false)
	@Info(label="状态",placeholder="",tip="",help="",secret="")
//	@NotNull(message="状态不能设为空")
	private S state;

	@Transient
	@JsonIgnore
	@Info(label="行为",placeholder="",tip="",help="",secret="")	
	private String act;

	@Transient
	@JsonIgnore
	@Info(label="通知",placeholder="",tip="",help="",secret="")	
	private String notice;
		

	@Transient
	@JsonIgnore
	private Map map;
	
	@Transient
	@JsonIgnore
	private S beforeState; 

	@Transient
	@JsonIgnore
	private Set linkedSet;
	
	@Transient
	@JsonIgnore
	private String linkedKey;

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="last_act_id")
	private A lastAct;
	
	@JsonIgnore
	@Transient
	private List<BaseAct> currentUserCanActList;
	
	
	@Transient
	@JsonIgnore
	public S getBeforeState() {
		return beforeState;
	}
	@JsonIgnore
	public void setBeforeState(S beforeState) {
		this.beforeState = beforeState;
	}

	@Transient
	public Set getLinkedSet() {
		return linkedSet;
	}

	public void setupLinkedSet(String property) {
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(this);
		if (beanWrapperImpl.isReadableProperty(property)) {
			Object propertyValue = beanWrapperImpl.getPropertyValue(property);
			if (propertyValue != null) {
				Set newSet = (Set) propertyValue;
				Object set1 = newSet.stream().map(e -> new B(((BaseEntity) e).getId(), ((BaseEntity) e).getLabel()))
						.collect(Collectors.toSet());

				this.linkedSet = (Set) set1;
			}
		}

	}
	@Transient
	public String getLinkedKey() {
		return linkedKey;
	}

	public void setLinkedKey(String linkedKey) {
		this.linkedKey = linkedKey;
	}

	@Transient
	public Map getMap() {
		return this.map;
	}

	public void setupMap() {
		if (this.map == null) {
			this.map = CommonUtil.transBean2Map(this);
		}
	}

	public Set<L> getLogs() {
		return logs;
	}

	public void setLogs(Set<L> logs) {
		this.logs = logs;
	}
	
	public S getState() {
		return state;
	}

	public void setState(S string) {
		this.state = string;
	}

	@Transient
	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	
	/**
	 * 最新操作
	 * @return
	 */
	public A getLastAct() {
		return lastAct;
	}

	public void setLastAct(A lastAct) {
		this.lastAct = lastAct;
	}

	@Transient
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		
		this.notice = notice;
	}
	

	@Transient
	public List<BaseAct> getCurrentUserCanActList() {
		if(this.currentUserCanActList!=null){
			return this.currentUserCanActList;
		}
		// return (List<A>) CommonUtil.currentUserCanActList(this);
		Set<A> acts = getState().getActs();
		String roleCode = CommonUtil.getCurrentUserRoleCode();
		if (acts == null) {
			return new ArrayList<BaseAct>();
		}
		List<Integer> ids = acts.stream().map(a -> a.getId()).collect(Collectors.toList());
		List<A> candoActs = getService().findByIdInAndRolesRoleCode(ids, roleCode);
		
		ArrayList<BaseAct> arrayList = new ArrayList<BaseAct>();
			
		if (candoActs != null && !candoActs.isEmpty()) {
			for (A a : candoActs) {
				BaseAct act = new BaseAct();
				act.setId(a.getId());
				act.setLabel(a.getLabel());
				act.setSort(a.getSort());
				act.setActCode(a.getActCode());
				act.setActGroup(a.getActGroup());
				act.setImportance(a.getImportance());
				act.setMessageType(a.getMessageType());
				arrayList.add(act);
			}
		}
	
		Collections.sort(arrayList);
		
		this.currentUserCanActList = arrayList;
		
		return this.currentUserCanActList;
	}

	private static class B {
		private Integer id;
		private String label;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public B(Integer id, String label) {
			super();
			this.id = id;
			this.label = label;
		}

	}
	
	protected static class LocalState implements Comparable<LocalState> {
		private Integer id;
		private String label;
		private Integer sort;
		private String stateCode;
		private Boolean done=false;
		private List<LocalAct> acts;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}

		public String getStateCode() {
			return stateCode;
		}

		public void setStateCode(String stateCode) {
			this.stateCode = stateCode;
		}

		public Boolean getDone() {
			return done;
		}

		public void setDone(Boolean done) {
			this.done = done;
		}

		public List<LocalAct> getActs() {
			return acts;
		}

		public void setActs(List<LocalAct> acts) {
			this.acts = acts;
		}

		@Override
		public int compareTo(LocalState o) {
			if (getSort() > o.getSort()) {
				return 1;
			} else if (getSort() == o.getSort()) {
				return 0;
			} else {
				return -1;
			}
		}

		@Override
		public String toString() {
//			return "State [id=" + id + ", label=" + label + ", stateCode=" + stateCode + ", done="
//					+ done + ", acts=" + acts + "]";
			StringBuilder sb = new StringBuilder();
			sb.append("State).append([id=") .append(id) .append(",").append("label=") 
			.append(label) .append(",").append("stateCode=") .append(stateCode) 
			.append(",").append("done=").append(done).append(",").append("acts=") .append(acts) .append("]");
			return sb.toString();
		}

	}

	protected static class BaseAct implements Comparable<BaseAct> {
		private Integer id;
		private String label;
		private String actCode;
		private String iconClass;
		private String btnClass;
		private Integer sort;
		private Boolean importance;
		@Enumerated(EnumType.STRING)
		private ActGroup actGroup;
		@Enumerated(EnumType.STRING)
		private EnumOperationMessageType messageType;

		public String getIconClass() {
			return iconClass;
		}

		public void setIconClass(String iconClass) {
			this.iconClass = iconClass;
		}

		public String getBtnClass() {
			return btnClass;
		}

		public void setBtnClass(String btnClass) {
			this.btnClass = btnClass;
		}

		public Boolean getImportance() {
			return importance;
		}

		public void setImportance(Boolean importance) {
			this.importance = importance;
		}

		public ActGroup getActGroup() {
			return actGroup;
		}

		public void setActGroup(ActGroup actGroup) {
			this.actGroup = actGroup;
		}

		public EnumOperationMessageType getMessageType() {
			return messageType;
		}

		public void setMessageType(EnumOperationMessageType messageType) {
			this.messageType = messageType;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getActCode() {
			return actCode;
		}

		public void setActCode(String actCode) {
			this.actCode = actCode;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}

		

		@Override
		public int compareTo(BaseAct o) {
			if (getSort() > o.getSort()) {
				return 1;
			} else if (getSort() == o.getSort()) {
				return 0;
			} else {
				return -1;
			}
		}

		@Override
		public String toString() {
//			return "Act [id=" + id + ", label=" + label + ", actCode=" + actCode + "actGroup=" + actGroup + "]";
			StringBuilder sb = new StringBuilder();
			sb.append("Act").append("[id=")
			.append(id) .append(",").append("label=") .append(label) .append(",").append("actCode=")
			.append(actCode) .append("actGroup=") .append(actGroup) .append("]");
			return sb.toString();
		}

	}

	protected static class LocalAct extends BaseAct{
		private Boolean done = false;
		public Boolean getDone() {
			return done;
		}

		public void setDone(Boolean done) {
			this.done = done;
		}
	}
	public List<BaseAct> injectCurrentUserCanActList() {
		if(this.currentUserCanActList!=null){
			return this.currentUserCanActList;
		}
		// return (List<A>) CommonUtil.currentUserCanActList(this);
		if(getState() == null){
			throw new FailReturnObject(3101, "实体:"+ getClass().getSimpleName()+",id:"+getId()+"的状态为null", ReturnObject.Level.DISPLAY);
		}

		Set<A> acts = getState().getActs();
		String roleCode = CommonUtil.getCurrentUserRoleCode();
		if (acts == null) {
			return new ArrayList<BaseAct>();
		}
		List<Integer> ids = acts.stream().map(a -> a.getId()).collect(Collectors.toList());
		List<A> candoActs = getService().findByIdInAndRolesRoleCode(ids, roleCode);

		ArrayList<BaseAct> arrayList = new ArrayList<BaseAct>();

		if (candoActs != null && !candoActs.isEmpty()) {
			for (A a : candoActs) {
				BaseAct act = new BaseAct();
				act.setId(a.getId());
				act.setIconClass(a.getIconClass());
				act.setBtnClass(a.getBtnClass());
				act.setLabel(a.getLabel());
				act.setSort(a.getSort());
				act.setActCode(a.getActCode());
				act.setActGroup(a.getActGroup());
				act.setImportance(a.getImportance());
				act.setMessageType(a.getMessageType());
				arrayList.add(act);
			}
		}

		Collections.sort(arrayList);

		this.currentUserCanActList = arrayList;

		return this.currentUserCanActList;
	}
}

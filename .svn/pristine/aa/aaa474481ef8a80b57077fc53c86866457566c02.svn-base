package com.liyang.domain.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.Cacheable;
import javax.persistence.ConstraintMode;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.junit.Ignore;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.liyang.annotation.Info;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.role.Role;
import com.liyang.message.EnumOperationMessageType;
import com.liyang.message.Message;
import com.liyang.service.FileUploadService.OssFile;
import com.liyang.service.FileUploadService.OssImage;
import com.liyang.util.CommonUtil;

/**
 * 工作流
 * @author Administrator
 *
 * @param <W>
 * @param <S>
 * @param <A>
 * @param <L>
 * @param <F>
 */
@MappedSuperclass
public abstract class AbstractWorkflowEntity<W extends AbstractWorkflow, S extends AbstractWorkflowState, A extends AbstractWorkflowAct, L extends AbstractWorkflowLog, F extends AbstractWorkflowFile>
		extends AbstractAuditorEntity<S, A, L> {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_id")
	@JsonIgnore
	private W workflow;

	@OneToMany(mappedBy = "entity")
	@JsonIgnore
	private Set<F> files = new HashSet<F>();
	
	@Transient
	@JsonIgnore
	private OssImage fileObject;

	@Transient
	@JsonIgnore
	private String[] wechatFiles = new String[0];

	public String[] getWechatFiles() {
		return wechatFiles;
	}

	public void setWechatFiles(String[] wechatFiles) {
		this.wechatFiles = wechatFiles;
	}

	@Transient
	private LinkedHashMap<String,LocalState> stateActList = new LinkedHashMap<String,LocalState>();

	@Transient
	private Map<?, ?> fileCategoryTree;

	@Transient
	private OssFile filePackage;
	@Transient
	public OssFile getFilePackage() {
		return filePackage;
	}

	public void setFilePackage(OssFile filePackage) {
		this.filePackage = filePackage;
	}

	public void setupFileCategoryTree() {
		this.fileCategoryTree = CommonUtil.fileCategoryToTree((Set<AbstractWorkflowFile>) getFiles());
	}
	@Transient
	public Map<?, ?> getFileCategoryTree() {
		return fileCategoryTree;
	}
	// 顶级目录
	@Transient
	@Info(label = "目录", placeholder = "", tip = "", help = "", secret = "")
	private String topcategory = "-";
	// 子级目录
	@Transient
	@Info(label = "子目录", placeholder = "", tip = "", help = "", secret = "")
	private String subcategory = "-";
	
	@Transient
	public String getTopcategory() {
		return topcategory;
	}

	public void setTopcategory(String topcategory) {
		this.topcategory = topcategory;
	}
	@Transient
	public String getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}

	@Transient
	public OssImage getFileObject() {
		return fileObject;
	}

	public void setFileObject(OssImage fileObject) {
		this.fileObject = fileObject;
	}

	public Set<F> getFiles() {
		return files;
	}

	public void setFiles(Set<F> files) {
		this.files = files;
	}

	public W getWorkflow() {
		return workflow;
	}

	public void setWorkflow(W abstractWorkflowTemplate) {
		this.workflow = abstractWorkflowTemplate;
	}

	public void setStateActList(LinkedHashMap<String,LocalState>stateActList) {
		this.stateActList = stateActList;
	}

	@Transient
	public LinkedHashMap<String,LocalState> getStateActList() {
		return stateActList;
	}

	public void injectStateActList() {
		setupFileCategoryTree();

		ArrayList<LocalState> stateList = new ArrayList<LocalState>();

		if (getWorkflow() == null) {
			return;
		}
		Set<S> states = getWorkflow().getStates();
		if (states == null) {
			return;
		}

		for (S abstractWorkflowState : states) {
			LocalState state = new LocalState();
			state.setId(abstractWorkflowState.getId());
			state.setLabel(abstractWorkflowState.getLabel());
			state.setSort(abstractWorkflowState.getSort());
			state.setStateCode(abstractWorkflowState.getStateCode());
			Set<A> acts = (Set<A>) abstractWorkflowState.getActs();
			ArrayList<LocalAct> actList = new ArrayList<LocalAct>();
			if (acts != null && !acts.isEmpty()) {
				for (A a : acts) {
					if (a.getActGroup().equals(ActGroup.OPERATE) || a.getActGroup().equals(ActGroup.FILE_OPERATE)) {
						LocalAct act = new LocalAct();
						act.setId(a.getId());
						act.setLabel(a.getLabel());
						act.setSort(a.getSort());
						act.setActCode(a.getActCode());
						act.setActGroup(a.getActGroup());
						act.setImportance(a.getImportance());
						act.setMessageType(a.getMessageType());
						actList.add(act);
					}
				}
			}
			Collections.sort(actList);
			state.setActs(actList);
			stateList.add(state);
		}
		Collections.sort(stateList);

		if (!getLogs().isEmpty()) {
			Map<AbstractAuditorState, Map<AbstractWorkflowAct, List<L>>> collect = getLogs().stream().filter(
					l -> l.getActGroup().equals(ActGroup.OPERATE) || l.getActGroup().equals(ActGroup.FILE_OPERATE))
					.collect(Collectors.groupingBy(L::getBeforeState, Collectors.groupingBy(L::getAct)));

			for (Entry<AbstractAuditorState, Map<AbstractWorkflowAct, List<L>>> s : collect.entrySet()) {
				AbstractAuditorState logState = s.getKey();
				Map<AbstractWorkflowAct, List<L>> actMapLogs = s.getValue();

				LocalState currentState = null;
				for (LocalState state : stateList) {
					if (state.getId().equals(logState.getId())) {
						currentState = state;
					}
				}
				for (Entry<AbstractWorkflowAct, List<L>> m : actMapLogs.entrySet()) {
					// m.getKey().setDone(true);

					if (currentState != null) {
						for (LocalAct act : currentState.getActs()) {
							if (act.getId().equals(m.getKey().getId())) {
								act.setDone(true);
							}
						}
						if (m.getKey().getTargetState() != null) {
							currentState.setDone(true);

						}

					}
				}

			}
		}
		
		LinkedHashMap<String,LocalState> linkedMap = new LinkedHashMap<>();
		for (LocalState s : stateList) {
			linkedMap.put(s.getStateCode(), s);
		}
		

		setStateActList(linkedMap);

	}

}

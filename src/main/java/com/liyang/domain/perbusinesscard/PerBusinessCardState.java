package com.liyang.domain.perbusinesscard;

import com.liyang.domain.base.AbstractAuditorState;
import com.liyang.domain.base.AbstractWorkflowState;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="per_busCard_state")
public class PerBusinessCardState extends AbstractAuditorState<PerBusinessCard,PerBusinessCardAct> {
    public PerBusinessCardState(String label, int sort, String stateCode) {
        // TODO Auto-generated constructor stub
        super(label, sort, stateCode);
    }
    public PerBusinessCardState() {
        // TODO Auto-generated constructor stub
        super();
    }
}

package com.liyang.domain.moments;

import com.liyang.domain.base.AbstractAuditorState;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="moments_state")
public class MomentsState extends AbstractAuditorState<Moments,MomentsAct>{
    public MomentsState(String label, int sort, String stateCode) {
        // TODO Auto-generated constructor stub
        super(label, sort, stateCode);
    }
    public MomentsState() {
        // TODO Auto-generated constructor stub
        super();
    }
}

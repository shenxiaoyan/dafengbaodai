package com.liyang.domain.perbusinesscard;

import com.liyang.domain.base.AbstractAuditorLog;
import com.liyang.domain.base.AbstractWorkflowLog;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="per_buscard_log")
public class PerBusinessCardLog extends AbstractAuditorLog<PerBusinessCard,  PerBusinessCardState, PerBusinessCardAct> {
}

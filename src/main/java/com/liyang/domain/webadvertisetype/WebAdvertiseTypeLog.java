package com.liyang.domain.webadvertisetype;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;

@Entity
@Table(name="web_advertise_type_log")
public class WebAdvertiseTypeLog extends AbstractAuditorLog<WebAdvertiseType, WebAdvertiseTypeState, WebAdvertiseTypeAct>{

	private static final long serialVersionUID = 2967514590607900304L;

}

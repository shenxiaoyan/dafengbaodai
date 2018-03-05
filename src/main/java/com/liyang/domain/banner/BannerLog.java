package com.liyang.domain.banner;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.liyang.domain.base.AbstractAuditorLog;

/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "banner_log")
public class BannerLog extends AbstractAuditorLog<Banner, BannerState, BannerAct> {

}

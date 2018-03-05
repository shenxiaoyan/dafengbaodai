package com.liyang.listener;

import org.springframework.stereotype.Component;

import com.liyang.domain.menu.Menu;
import com.liyang.domain.menu.MenuAct;
import com.liyang.domain.menu.MenuLog;
import com.liyang.domain.menu.MenuState;
/**
 * @author Administrator
 *
 */
@Component
public class MenuRestEventListener extends AuditorRestEventListener<Menu,MenuState,MenuAct,MenuLog> {

}

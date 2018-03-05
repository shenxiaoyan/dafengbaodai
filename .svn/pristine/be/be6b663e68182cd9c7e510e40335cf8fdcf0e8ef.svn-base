package com.liyang.client;

import java.util.ArrayList;
import java.util.List;

/**
 * 复合模式下的单独观察者类
 * 
 * @author Administrator
 *
 */
public abstract class BaseServiceObserveNode implements IServiceObserve {

	public BaseServiceObserveNode() {
	}

	@Override
	public void addObserve(IServiceObserve observe) {

	}

	@Override
	public void clearObserves() {

	}

	@Override
	public int countObserves() {
		return 1;
	}

	@Override
	public List<IServiceObserve> getObserves() {
		List<IServiceObserve> result = new ArrayList<IServiceObserve>();
		if (!result.contains(this)) {
			result.add(this);
		}
		return result;
	}

}

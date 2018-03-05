package com.liyang.client;

import java.util.ArrayList;
import java.util.List;

/**
 * 复合模式下的观察者类的群
 * 
 * @author Administrator
 *
 */
public abstract class BaseServiceObserveGroup implements IServiceObserve {
	List<IServiceObserve> observes = new ArrayList<IServiceObserve>();

	@Override
	public IMessage notifyBeforeCall(IMessage message) {
		IMessage result = message;
		if (observes != null && observes.size() > 0) {
			for (IServiceObserve observe : observes) {
				IMessage tempMsg = observe.notifyBeforeCall(message);
				if (tempMsg != null) {
					message = tempMsg;
					result = message;
				}
			}
		}
		return result;
	}

	@Override
	public IResult notifyAfterCall(IMessage message, IResult result) {
		IResult res = result;
		if (observes != null && observes.size() > 0) {
			for (IServiceObserve observe : observes) {
				IResult tempRes = observe.notifyAfterCall(message, result);
				if (tempRes != null) {
					result = tempRes;
					res = result;
				}
			}
		}
		return res;
	}

	@Override
	public void notifyException(IMessage message, IResult result, Exception exception) {
		if (observes != null && observes.size() > 0) {
			for (IServiceObserve observe : observes) {
				observe.notifyException(message, result, exception);
			}
		}
	}

	public BaseServiceObserveGroup(List<IServiceObserve> observes) {
		if (observes != null && observes.size() > 0) {
			for (IServiceObserve observe : observes) {
				addObserve(observe);
			}
		}
	}

	@Override
	public void addObserve(IServiceObserve observe) {
		if (observe != null) {
			if (!observes.contains(observe)) {
				observes.add(observe);
			}
		}
	}

	@Override
	public void clearObserves() {
		if (observes == null) {
			return;
		}
		observes.clear();
	}

	@Override
	public int countObserves() {
		if (observes == null) {
			return 0;
		}
		return observes.size();
	}

	@Override
	public List<IServiceObserve> getObserves() {
		return this.observes;
	}

}

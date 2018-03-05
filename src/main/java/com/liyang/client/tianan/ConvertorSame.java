package com.liyang.client.tianan;

import com.liyang.client.IClassConvertor;

/**
 * @author Administrator
 *
 */
public class ConvertorSame implements IClassConvertor {

	@Override
	public Object convert(Object... objects) {
		Object result = null;

		if (objects == null) {
			return result;
		}
		result = objects[0];

		return result;
	}

}

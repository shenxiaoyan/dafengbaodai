package com.liyang.client.tianan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyang.client.BaseServiceObserveNode;
import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.client.IServiceObserve;
import com.liyang.domain.carModel.CarModel;
import com.liyang.domain.carModel.CarModelRepository;
import com.liyang.domain.carModel.CarModelVehicleModel;
import com.liyang.util.Log4jUtil;

/**
 * @author Administrator
 *
 */
public class ServiceObserveDbPersistQueryCarModel extends BaseServiceObserveNode implements IServiceObserve {

	private final static Logger logger = LoggerFactory.getLogger(ServiceObserveDbPersistQueryCarModel.class);
	
	private CarModelRepository carModelRepository;

	public ServiceObserveDbPersistQueryCarModel(CarModelRepository carModelRepository) {
		this.carModelRepository = carModelRepository;
	}

	@Override
	public IMessage notifyBeforeCall(IMessage message) {
		logger.info("天安车型查询提交报文：" + JSON.toJSONString(message));
		return null;
	}

	@Override
	public IResult notifyAfterCall(IMessage message, IResult result) {
		logger.info("天安车型查询返回报文：" + JSON.toJSONString(result));
		MessageQueryCarModel detailMessage = (MessageQueryCarModel) message;
		String tradeNo = detailMessage.getRequestHead().getTradeNo();
		CarModel carModel = saveToDB(result, message, tradeNo);
		//将tradeNo和id放入parmas里
		result.putParmas("tradeNo", detailMessage.getRequestHead().getTradeNo());
		result.putParmas("id", carModel.getId());
		return result;
	}

	@Override
	public void notifyException(IMessage message, IResult result, Exception exception) {
		logger.info("天安车型查询异常：" + Log4jUtil.formatException(exception));
	}

	private CarModel saveToDB(IResult result, IMessage message, String tradeNo) {
		ResultQueryCarModel detailResult = (ResultQueryCarModel) result;
		CarModel carModel = parseResult(detailResult, message);
		// 关联唯一编号，用于展示车架号，发动机号
		carModel.setTradeNo(tradeNo);
		carModel = carModelRepository.save(carModel);
		//解决VehicleModelList无法关联的问题
		if (carModel.getVehicleModelList() != null) {
			for (CarModelVehicleModel model : carModel.getVehicleModelList()) {
				model.setCarModel(carModel);
			}
		}
		carModel = carModelRepository.save(carModel);
		return carModel;
	}

	private CarModel parseResult(ResultQueryCarModel result, IMessage message) {
		CarModel carModel = null;
		// 利用fastjson转换
		String jsonString = JSON.toJSONString(result);
		carModel = JSON.parseObject(jsonString, new TypeReference<CarModel>() {
		});
		carModel.setRequestMessage(JSON.toJSONString(message));
		return carModel;
	}

}

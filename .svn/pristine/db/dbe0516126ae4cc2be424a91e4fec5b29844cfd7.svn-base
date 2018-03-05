package com.liyang.client.tianan;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.liyang.client.BaseService;
import com.liyang.client.IClient;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;
import com.liyang.client.IResult;
import com.liyang.client.IService;
import com.liyang.client.IServiceObserve;
import com.liyang.client.ResponseSupplierPostJson;

/**
 * 车型查询
 * 
 * @author jacksunny
 *
 */
public class ServiceQueryCarModel extends BaseService implements IService {
	/**
	 * 车型查询
	 * 
	 * @param client
	 * @param serviceObserve
	 */
	public ServiceQueryCarModel(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier,
			boolean skipAutoValidate) {
		super(client, serviceObserve, responseSupplier, skipAutoValidate);
		setPartUrl("tianan_cpf/access/car/queryCarModel.mvc");
	}

	public ServiceQueryCarModel(IClient client, IServiceObserve serviceObserve, IResponseSupplier responseSupplier) {
		this(client, serviceObserve, responseSupplier, false);
	}

	public ServiceQueryCarModel(IClient client, IServiceObserve serviceObserve) {
		this(client, serviceObserve, new ResponseSupplierPostJson());
	}

	/**
	 * 车型查询
	 * 
	 * @param client
	 */
	public ServiceQueryCarModel(IClient client) {
		this(client, null, new ResponseSupplierPostJson());
	}

	@Override
	public IResult parseResult(Object response, IMessage generalMessage) {
		ResultQueryCarModel result = new ResultQueryCarModel();
		String responseText = (String) response;
		if (StringUtils.isEmpty(responseText)) {
			return result;
		}
		// JsonParser parser = new JsonParser();
		// JsonObject json = (JsonObject) parser.parse(responseText);
		// String resultCode =
		// json.get("resultDTO").getAsJsonObject().get("resultCode").getAsString();
		// String resultMess =
		// json.get("resultDTO").getAsJsonObject().get("resultMess").getAsString();
		// BaseResultDto resultDTO = new BaseResultDto(resultCode, resultMess);
		// result.setResultDTO(resultDTO);
		result = JSON.parseObject(responseText, new TypeReference<ResultQueryCarModel>() {
		});
		return result;
	}

	@Override
	public Object parseParameters(IMessage generalMessage) {
		String jsonString = "";

		if (null == generalMessage) {
			return jsonString;
		}
		MessageQueryCarModel message = (MessageQueryCarModel) generalMessage;
		// JSONObject obj = new JSONObject();
		// if (null != message.getRequestHead()) {
		// obj.element("requestHead",
		// JSONObject.fromObject(message.getRequestHead()));
		// }
		// if (!StringUtils.isEmpty(message.getCityCode())) {
		// obj.element("cityCode", message.getCityCode());
		// }
		// if (!StringUtils.isEmpty(message.getBrandName())) {
		// obj.element("brandName", message.getBrandName());
		// }
		// if (!StringUtils.isEmpty(message.getEnginNo())) {
		// obj.element("enginNo", message.getEnginNo());
		// }
		// if (!StringUtils.isEmpty(message.getEnrollDate())) {
		// obj.element("enrollDate", message.getEnrollDate());
		// }
		// if (!StringUtils.isEmpty(message.getPurchaseDate())) {
		// obj.element("purchaseDate", message.getPurchaseDate());
		// }
		// if (!StringUtils.isEmpty(message.getStartDate())) {
		// obj.element("startDate", message.getStartDate());
		// }
		// if (!StringUtils.isEmpty(message.getFrameNo())) {
		// obj.element("frameNo", message.getFrameNo());
		// }
		// if (!StringUtils.isEmpty(message.getLicenseNo())) {
		// obj.element("licenseNo", message.getLicenseNo());
		// }
		// if (!StringUtils.isEmpty(message.getPage())) {
		// obj.element("page", message.getPage());
		// }
		// if (!StringUtils.isEmpty(message.getRows())) {
		// obj.element("rows", message.getRows());
		// }
		// jsonString = obj.toString();
		// jsonString = JSONObject.fromObject(message).toString();
		jsonString = JSON.toJSONString(message);
		System.out.println(jsonString);
		return jsonString;
	}

	@Override
	public Object parseHeaders(IMessage message) {
		return null;
	}

}

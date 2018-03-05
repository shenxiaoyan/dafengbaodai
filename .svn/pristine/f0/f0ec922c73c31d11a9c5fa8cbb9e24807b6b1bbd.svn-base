package com.liyang.client.tianan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.liyang.client.BaseRequestHeader;
import com.liyang.client.IClassConvertor;
import com.liyang.client.IClient;
import com.liyang.client.IServiceObserve;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;

/**
 * @author Administrator
 *
 */
public class FacadeService {
	
	static FacadeService instance = null;
	static FactoryRequestHeader factoryRequestHeader = new FactoryRequestHeader();
	static MessageGetSign lastMessageGetSign = null;
	static String lastSign = null;
	private static IClient client;

	public static FacadeService getInstance() throws ExceptionTiananInitFailed {
		if (instance == null) {
			instance = new FacadeService();
			setClient(new ClientTianan(null));
			ServiceGetSign service = new ServiceGetSign(getClient());
			String url = null;
			MessageGetSign generalMessage = new MessageGetSignTest(url);
			ResultGetSign resultGetSign = (ResultGetSign) service.callService(generalMessage);
			if (resultGetSign != null) {
				lastMessageGetSign = generalMessage;
				lastSign = resultGetSign.getSign();
			} else {
				throw new ExceptionTiananInitFailed("获取签名失败" + generalMessage.toString());
			}
		}
		return instance;
	}
	
	public static FacadeService getInstance(String tiananBaseUrl) throws ExceptionTiananInitFailed {
		if (instance == null) {
			instance = new FacadeService();
			setClient(new ClientTianan(tiananBaseUrl));
			ServiceGetSign service = new ServiceGetSign(getClient());
			String url = null;
			MessageGetSign generalMessage = null;
			ResultGetSign resultGetSign = null;
			try {
				if (!tiananBaseUrl.endsWith("cpf/")) {
					generalMessage = new MessageGetSignProduction(url);
					resultGetSign = (ResultGetSign) service.callMockDevService(generalMessage);
				}else {
					generalMessage = new MessageGetSignTest(url);
					resultGetSign = (ResultGetSign) service.callMockTestService(generalMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resultGetSign != null) {
				lastMessageGetSign = generalMessage;
				lastSign = resultGetSign.getSign();
			} else {
				throw new ExceptionTiananInitFailed("获取签名失败" + generalMessage.toString());
			}
		}
		return instance;
	}
	
	public BaseRequestHeader getDefaultRequestHeader(String tradeNo) {
		BaseRequestHeader result = null;
		result = factoryRequestHeader.createRequestHeader(lastMessageGetSign, lastSign, tradeNo);
		return result;
	}

	public ResultInsureConfirmation callServiceInsureConfirmation(MessageInsureConfirmation message) throws Exception {
		return callServiceInsureConfirmation(message, new ConvertorSame(), null);
	}

	public ResultInsureConfirmation callServiceInsureConfirmation(MessageInsureConfirmation message,
			IClassConvertor convertor, IServiceObserve serviceObserve) throws Exception {
		ResultInsureConfirmation result = new ResultInsureConfirmation();
		ServiceInsureConfirmation service = new ServiceInsureConfirmation(client, serviceObserve);
		result = (ResultInsureConfirmation) service.callService(message);
		result = (ResultInsureConfirmation) convertor.convert(result);
		return result;
	}

	public ResultPremiumAccurate callServicePremiumAccurate(MessagePremiumAccurate message) throws Exception {
		return callServicePremiumAccurate(message, new ConvertorSame(), null);
	}

	public ResultPremiumAccurate callServicePremiumAccurate(MessagePremiumAccurate message, IClassConvertor convertor,
			IServiceObserve serviceObserve) throws Exception {
		ResultPremiumAccurate result = new ResultPremiumAccurate();
		ServicePremiumAccurate service = new ServicePremiumAccurate(client, serviceObserve);
		result = (ResultPremiumAccurate) service.callService(message);
		result = (ResultPremiumAccurate) convertor.convert(result);
		return result;
	}

	public ResultQueryCarModel callServiceQueryCarModel(MessageQueryCarModel message) throws Exception {
		return callServiceQueryCarModel(message, new ConvertorSame(), null);
	}

	public ResultQueryCarModel callServiceQueryCarModel(MessageQueryCarModel message, IClassConvertor convertor,
			IServiceObserve serviceObserve) throws Exception {
		ResultQueryCarModel result = new ResultQueryCarModel();
		ServiceQueryCarModel service = new ServiceQueryCarModel(client, serviceObserve);
		result = (ResultQueryCarModel) service.callService(message);
		result = (ResultQueryCarModel) convertor.convert(result);
		return result;
	}

	public ResultQueryProposal callServiceQueryProposal(MessageQueryProposal message) throws Exception {
		return callServiceQueryProposal(message, new ConvertorSame(), null);
	}

	public ResultQueryProposal callServiceQueryProposal(MessageQueryProposal message, IClassConvertor convertor,
			IServiceObserve serviceObserve) throws Exception {
		ResultQueryProposal result = new ResultQueryProposal();
		ServiceQueryProposal service = new ServiceQueryProposal(client, serviceObserve);
		result = (ResultQueryProposal) service.callService(message);
		result = (ResultQueryProposal) convertor.convert(result);
		return result;
	}

	public ResultRequestPay callServiceRequestPay(MessageRequestPay message) throws Exception {
		return callServiceRequestPay(message, new ConvertorSame(), null);
	}

	public ResultRequestPay callServiceRequestPay(MessageRequestPay message, IClassConvertor convertor,
			IServiceObserve serviceObserve) throws Exception {
		ResultRequestPay result = new ResultRequestPay();
		ServiceRequestPay service = new ServiceRequestPay(client, serviceObserve);
		result = (ResultRequestPay) service.callService(message);
		result = (ResultRequestPay) convertor.convert(result);
		return result;
	}

	public ResultUploadAttach callServiceUploadAttach(MessageUploadAttach message) throws Exception {
		return callServiceUploadAttach(message, new ConvertorSame(), null);
	}

	public ResultUploadAttach callServiceUploadAttach(MessageUploadAttach message, IClassConvertor convertor,
			IServiceObserve serviceObserve) throws Exception {
		ResultUploadAttach result = new ResultUploadAttach();
		ServiceUploadAttach service = new ServiceUploadAttach(client, serviceObserve);
		result = (ResultUploadAttach) service.callService(message);
		result = (ResultUploadAttach) convertor.convert(result);
		return result;
	}

	/**
	 * @return the client
	 */
	public static IClient getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public static void setClient(IClient client) {
		FacadeService.client = client;
	}

}

package com.liyang.client.tianan;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.liyang.client.BaseServiceObserveNode;
import com.liyang.client.CreateEnquiryJsonParseFactory;
import com.liyang.client.ICreateEnquiryJsonParseAdapter;
import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.client.IServiceObserve;
import com.liyang.client.tianan.dto.CheckDto;
import com.liyang.client.tianan.dto.CombosDataDto;
import com.liyang.client.tianan.dto.ItemKindDataDto;
import com.liyang.client.tianan.enums.AdditionalEnums;
import com.liyang.client.tianan.enums.QuotationTypeEnums;
import com.liyang.domain.advertise.Advertise;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryState;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.domain.offerresult.OfferResultDataResultCheckList;
import com.liyang.domain.offerresult.OfferResultErrorMsg;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.enums.InsureCompanyEnum;
import com.liyang.service.XinGeService;
import com.liyang.util.CityCodeUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.Log4jUtil;
import com.liyang.util.PushAdvertiseUtil;

import net.sf.json.JSONObject;

/**
 * 天安精确报价观察者类
 * 
 * @author huanghengkun
 * @create 2017年12月17日
 */
public class ServiceObserveDbPersistPremiumAccurate extends BaseServiceObserveNode implements IServiceObserve {

	private final static Logger logger = LoggerFactory.getLogger(ServiceObserveDbPersistPremiumAccurate.class);

	private XinGeService xinGeService;
	private AdvertiseRepository advertiseRepository;
	private OfferResultRepository offerResultRepository;
	private CreateEnquiryRepository createEnquiryRepository;
	private CreateEnquiryStateRepository createEnquiryStateRepository;

	public ServiceObserveDbPersistPremiumAccurate(XinGeService xinGeService, AdvertiseRepository advertiseRepository,
			OfferResultRepository offerResultRepository, CreateEnquiryRepository createEnquiryRepository,
			CreateEnquiryStateRepository createEnquiryStateRepository) {
		super();
		this.xinGeService = xinGeService;
		this.advertiseRepository = advertiseRepository;
		this.offerResultRepository = offerResultRepository;
		this.createEnquiryRepository = createEnquiryRepository;
		this.createEnquiryStateRepository = createEnquiryStateRepository;
	}

	@Override
	public IMessage notifyBeforeCall(IMessage message) {
		logger.info("天安精确报价提交报文：" + JSON.toJSONString(message));

		return null;
	}

	@Override
	public IResult notifyAfterCall(IMessage message, IResult result) {
		logger.info("天安精确报价返回报文：" + JSON.toJSONString(result));

		MessagePremiumAccurate detailMessage = (MessagePremiumAccurate) message;
		ResultPremiumAccurate detailResult = (ResultPremiumAccurate) result;

		CreateEnquiry createEnquiry = createEnquiryRepository
				.findByOfferUnique(detailMessage.getRequestHead().getTradeNo());

		if (createEnquiry == null) {
			throw new FailReturnObject(ExceptionResultEnum.CREQUERY_NOIDINFO_ERROR);
		}

		// 记录响应
		createEnquiry.setResponseResult(JSON.toJSONString(result));
		createEnquiry = createEnquiryRepository.save(createEnquiry);

		Set<OfferResult> set = createEnquiry.getOfferResult();
		if (set != null && set.size() > 0) {
			Iterator<OfferResult> it = set.iterator();
			while (it.hasNext()) {
				OfferResult offerResult = it.next();
				if (offerResult.getData().getResult().getInsuranceCompanyId().intValue() == InsureCompanyEnum.TIANAN
						.getId().intValue()) {
					offerResult.setSuccessful("1".equals(detailResult.getDealFlag()));
					OfferResultErrorMsg errormsg = new OfferResultErrorMsg();
					errormsg.setCode(detailResult.getResultDTO().getResultCode());
					errormsg.setMessage(detailResult.getResultDTO().getResultMess());
					offerResult.setErrorMsg(errormsg);
					// offerResult.getData().getRequestHeader()
					// .setOriReqHeaderStr(JSON.toJSONString(detailMessage.getRequestHead()));
					if (detailResult.getResultDTO().isSuccess()) {
						setResultDataResult(detailMessage, detailResult, offerResult.getData().getResult());
					} else {
						JSONObject errorMsg = new JSONObject();
						errorMsg.put("message", detailResult.getErrorMess());
						offerResult.getData().getResult().setErrorMsg(errorMsg.toString());
					}
					CreateEnquiryState createEnquiryState = createEnquiryStateRepository
							.findByStateCode("ENQUIRY_RESULT");
					offerResult.getCreateEnquiry().setState(createEnquiryState);
					offerResult = offerResultRepository.save(offerResult);
					pushAdvertise(offerResult);
				}
			}

		}
		return null;

	}

	private void setResultDataResult(MessagePremiumAccurate message, ResultPremiumAccurate result,
			OfferResultDataResult resultDataResult) {
		if (!StringUtils.isEmpty(result.getErrorMess())) {
			JSONObject errorMsg = new JSONObject();
			errorMsg.put("message", result.getErrorMess());
			resultDataResult.setErrorMsg(errorMsg.toString());
		}
		resultDataResult.setActualValue(result.getActualValue());
		resultDataResult.setCommercialNum(result.getCommercialNum());
		resultDataResult.setTrafficInsuranceNum(result.getTrafficInsuranceNum());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (QuotationTypeEnums.compulsoryInsurance.getCode().equals(message.getQuotationType())) {
			// 单交强
			try {
				Date startDate = sdf.parse(result.getStartDate());
				Date endDate = sdf.parse(result.getEndDate());
				// 小马数据是long/1000
				resultDataResult.setForceInsuranceStartTime(startDate.getTime() / 1000);
				// 小马数据是long/1000
				resultDataResult.setForceInsuranceEndTime(endDate.getTime() / 1000);
			} catch (ParseException e) {
				logger.error("天安强制险日期格式化异常 startDate:" + result.getStartDate() + "  endDate:" + result.getEndDate());
			} catch (NullPointerException e) {
				logger.error("天安强制险日期空指针异常");
			}
		} else if (QuotationTypeEnums.businessInsurance.getCode().equals(message.getQuotationType())) {
			// 单商业
			try {
				Date startDate = sdf.parse(result.getStartDate());
				Date endDate = sdf.parse(result.getEndDate());
				// 小马数据是long/1000
				resultDataResult.setInsuranceStartTime(startDate.getTime() / 1000);
				// 小马数据是long/1000
				resultDataResult.setInsuranceEndTime(endDate.getTime() / 1000);
			} catch (ParseException e) {
				logger.error("天安商业险日期格式化异常  startDate" + result.getStartDate() + "  endDate:" + result.getEndDate());
			} catch (NullPointerException e) {
				logger.error("天安强制险日期空指针异常");
			}
		} else {
			// 混合
			try {
				Date startDate = sdf.parse(result.getStartDate());
				Date endDate = sdf.parse(result.getEndDate());
				// 小马数据是long/1000
				resultDataResult.setForceInsuranceStartTime(startDate.getTime() / 1000);
				// 小马数据是long/1000
				resultDataResult.setForceInsuranceEndTime(endDate.getTime() / 1000);
			} catch (ParseException e) {
				logger.error("天安强制险日期格式化异常  startDate" + result.getStartDate() + "  endDate:" + result.getEndDate());
			} catch (NullPointerException e) {
				logger.error("天安强制险日期空指针异常");
			}
			try {
				Date startDateBus = sdf.parse(result.getStartDateBus());
				Date endDateBus = sdf.parse(result.getEndDateBus());
				// 小马数据是long/1000
				resultDataResult.setInsuranceStartTime(startDateBus.getTime() / 1000);
				// 小马数据是long/1000
				resultDataResult.setInsuranceEndTime(endDateBus.getTime() / 1000);
			} catch (ParseException e) {
				logger.error("天安商业险日期格式化异常  startDateBus" + result.getStartDateBus() + "  endDateBus:"
						+ result.getEndDateBus());
			} catch (NullPointerException e) {
				logger.error("天安商业险日期空指针异常");
			}
		}
		double taxPrice = result.getPcCarShipTaxInfoDto() == null ? 0
				: result.getPcCarShipTaxInfoDto().getSumTax().doubleValue() * 100;
		// TaxPrice单位为分,只记录总计价格
		resultDataResult.setTaxPrice(Long.valueOf((long) taxPrice));
		JSONObject offerDetail = convertOfferDetail(result);
		if (offerDetail == null) {
			resultDataResult.setOfferDetail(null);
			resultDataResult.setOriginalPrice(0L);
			resultDataResult.setForcePremium(0L);
			resultDataResult.setCurrentPrice(0L);
		} else {
			resultDataResult.setOfferDetail(offerDetail.toString());
			resultDataResult.setOriginalPrice(Long.valueOf((long) (offerDetail.getDouble("originalPrice") * 100)));
			// 天安暂无出单价、低价区分
			resultDataResult.setCurrentPrice(Long.valueOf((long) (offerDetail.getDouble("originalPrice") * 100)));
			resultDataResult.setForcePremium(Long.valueOf((long) (offerDetail.getJSONObject("forcePremium").getDouble("quotesPrice") * 100)));
		}
		// orderId=tradeNo
		resultDataResult.setOfferId(message.getRequestHead().getTradeNo());
		resultDataResult.setCarPremiumCaculateNo(result.getCarPremiumCaculateNo());
		resultDataResult.setRePremiumMessage(result.getRePremiumMessage());
		String modelJson = JSON.toJSONString(result.getCarModel());
		resultDataResult.setModelJson("null".equals(modelJson) ? null : modelJson);
		resultDataResult.setInsuredStatus(result.getInsuredStatus());
		if (result.getCheckList() != null && result.getCheckList().size() > 0) {
			Set<OfferResultDataResultCheckList> checkList = new HashSet<>();
			for (CheckDto checkDto : result.getCheckList()) {
				OfferResultDataResultCheckList item = new OfferResultDataResultCheckList(checkDto);
				item.setResult(resultDataResult);
				checkList.add(item);
			}
			resultDataResult.setCheckList(checkList);
		}

		/////// 其他一些数据，兼容小马
		resultDataResult.setCityCode(message.getCityCode());
		resultDataResult.setCityName(CityCodeUtil.getCityName(message.getCityCode()));
		// 暂手动固定折扣为1.0，天安报价接口-商业险优惠系数列表 未返回
		resultDataResult.setCommercialDiscount("1.0");
	}

	@Override
	public void notifyException(IMessage message, IResult result, Exception exception) {
		logger.info("天安精确报价异常：" + Log4jUtil.formatException(exception));
	}

	private void pushAdvertise(OfferResult offerResult) {
		String offerId = offerResult.getData().getResult().getOfferId();
		List<Advertise> advertiseList = advertiseRepository.findByOfferIdAndZnxType(offerId, 1);
		if (null == advertiseList || advertiseList.isEmpty()) {
			Advertise advertis = generateOfferResultAdvertise(offerResult, offerId);
			// 报价结果推送到指定平台
			Customer customer = offerResult.getCreateEnquiry().getCustomer();
			Map<String, Object> handerDataMap = new HashMap<String, Object>();
			handerDataMap.put("Id", offerResult.getId());
			handerDataMap.put("name", "offerResult");
			handerDataMap.put("create_enquiry_id", offerResult.getCreateEnquiry().getId());
			PushAdvertiseUtil.pushAdvertToAppPlatform(xinGeService, customer, handerDataMap, advertis.getTitle());
		}
	}

	private Advertise generateOfferResultAdvertise(OfferResult offerResult, String offerId) {

		CreateEnquiry createEnquiry = offerResult.getCreateEnquiry();
		// JSONObject enqParamsJsoObj = createEnquiry.getCreateEnquiryParams();
		ICreateEnquiryJsonParseAdapter adapter = new CreateEnquiryJsonParseFactory(createEnquiry).createAdapter();
		String licenseNumber = (String) adapter.getLicenseNumber();
		String ownerName = (String) adapter.getOwnerName();
		// titile
		StringBuffer titileBuf = new StringBuffer();
		titileBuf.append("【报价通知】").append(licenseNumber).append("有新的报价结果");
		// content
		double price = 0;
		if (offerResult.getSuccessful()) {
			JSONObject priceJson = offerResult.getData().getResult().getOfferDetail();
			double originalPrice = priceJson.getDouble("originalPrice");
			double forcePrice = priceJson.getJSONObject("forcePremium").getDouble("quotesPrice");
			double taxPrice = priceJson.getJSONObject("taxPrice").getDouble("quotesPrice");
			price = originalPrice + forcePrice + taxPrice;
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		String priceString = df.format(price);

		StringBuffer contentBuf = new StringBuffer();
		contentBuf.append("车牌：").append(licenseNumber).append("\n车主：").append(ownerName);
		contentBuf.append("\n保险公司：").append(offerResult.getData().getResult().getInsuranceCompanyName());
		if (price <= 0.00001) {
			contentBuf.append("\n报价结果： 报价失败");
		} else {
			contentBuf.append("\n保费总额： ").append(priceString).append("元");
		}

		Advertise advertise = new Advertise();
		advertise.setTitle(titileBuf.toString());
		advertise.setContent(contentBuf.toString());
		advertise.setIsRead(0);
		advertise.setType(2);
		advertise.setZnxType(1);
		advertise.setToken(createEnquiry.getCustomer().getToken());
		advertise.setOfferId(offerId);
		advertise.setCreateEnqId(String.valueOf(createEnquiry.getId()));
		// 防止站内信有多条
		// OfferResult aaa =
		// offerResultRepository.findOfferResultByOfferId(offerId2);
		// if(aaa == null){
		advertise = advertiseRepository.save(advertise);

		// }
		return advertise;
	}

	private JSONObject convertOfferDetail(ResultPremiumAccurate result) {
		List<CombosDataDto> list = result.getCombosList();
		if (list != null && list.size() > 0) {
			// 商业险出单价
			double originalPrice = 0;
			// 交强险低价
			double ciBasePrice = 0;
			JSONObject json = new JSONObject();
			JSONArray insurances = new JSONArray();
			// 不计免赔数额
			double additionalPrice = 0;
			// 交强险是否投保,0-不投保 1投保
			String forcePremiumIsToubao = "0";
			for (CombosDataDto dto : list) {
				// 交强险的comboNo固定为JQXCOMBO001
				if ("JQXCOMBO001".equals(dto.getComboNo())) {
					// 交强险不需要放入insurances中
					// for (ItemKindDataDto item : dto.getItemKindList()) {
					// insurances.add(parseItemKindDataDto(item));
					// }
					ciBasePrice = dto.getSumPremium();
					// 交强险投保,设置为1
					forcePremiumIsToubao = "1";
				} else {// 商业险的信息
					for (ItemKindDataDto item : dto.getItemKindList()) {
						if (AdditionalEnums.isExist(item.getKindCode())) {
							// 天安的不计免赔的险种单独列出来了，匹配小马格式，将这些不计免赔的信息从insurances中剔除，保费计入additionalPrice
							additionalPrice += item.getPremium().doubleValue();
						} else {
							insurances.add(parseItemKindDataDto(item));
						}
					}
					// 对商业主险做判断,查询是否有不计免赔
					for (Object insurance : insurances) {
						String insuranceId = (String) ((JSONObject) insurance).get("insuranceId");
						// 不计免赔险的险种Id
						String additionalCode = AdditionalEnums.findCodeByMainInsurance(insuranceId);
						if (!StringUtils.isEmpty(additionalCode)) {
							for (ItemKindDataDto item : dto.getItemKindList()) {
								if (additionalCode.equals(item.getKindCode())) {
									((JSONObject) insurance).put("compensation", true);
									break;
								}
							}
						}
					}
					// originalPrice仍为其他险种总价+不计免赔总价
					originalPrice = dto.getSumPremium();
				}
			}

			json.put("insurances", insurances);
			json.put("additionalPrice", additionalPrice);
			double taxPrice = result.getPcCarShipTaxInfoDto() == null ? 0
					: result.getPcCarShipTaxInfoDto().getSumTax().doubleValue();
			// 交强险低价=交强险保费+车船税
			ciBasePrice += taxPrice;
			json.put("originalPrice", originalPrice);
			// 天安底价、出单价暂设置相同
			json.put("currentPrice", originalPrice);
			json.put("ciBasePrice", ciBasePrice);
			// 封装forcePremium
			JSONObject forcePremiumJson = new JSONObject();
			forcePremiumJson.put("isToubao", forcePremiumIsToubao);
			forcePremiumJson.put("quotesPrice", ciBasePrice - taxPrice);
			json.put("forcePremium", forcePremiumJson);
			// 封装taxPrice
			JSONObject taxPriceJson = new JSONObject();
			taxPriceJson.put("isToubao", "1");
			taxPriceJson.put("quotesPrice", taxPrice);
			json.put("taxPrice", taxPriceJson);

			return json;
		} else {
			return null;
		}

	}

	private JSONObject parseItemKindDataDto(ItemKindDataDto item) {
		JSONObject itemJson = new JSONObject();
		// 险种编号
		itemJson.put("insuranceId", item.getKindCode());
		// 险种中文名称
		itemJson.put("insuranceName", item.getKindName());
		// 是否投保
		itemJson.put("isToubao", "1");
		// 是否不计免赔0-否1-是
		itemJson.put("compensation", false);
		// 保费,带单位
		itemJson.put("quotesPrice", item.getPremium());
		// 保额(车损险显示车损定价,其余显示保额)
		itemJson.put("amountStr", parseMoney(item.getAmount()));
		// 保额，数字
		itemJson.put("price", item.getAmount());
		return itemJson;
	}

	/**
	 * 简单的数字转换，转换整万数字 eg 10000->1万
	 * 
	 * @param amount
	 * @return
	 */
	private String parseMoney(Double amount) {
		if (amount == null) {
			return "";
		}
		double wanDouble = amount.doubleValue() / 10000;
		int wanInt = (int) wanDouble;
		if (wanInt == wanDouble) {
			return wanInt + "万";
		} else {
			return amount.toString();
		}
	}

}

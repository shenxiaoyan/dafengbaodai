package com.liyang.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.liyang.client.IMessage;
import com.liyang.client.IResult;
import com.liyang.client.tianan.MessagePremiumAccurate;
import com.liyang.client.tianan.TypeDate;
import com.liyang.client.tianan.dto.CarInfoDto;
import com.liyang.client.tianan.dto.CombosDataDto;
import com.liyang.client.tianan.dto.ExtendInfoDTO;
import com.liyang.client.tianan.dto.VehicleJingyouDto;
import com.liyang.client.tianan.enums.ApiSupplierEnums;
import com.liyang.client.tianan.exception.ExceptionTiananInitFailed;
import com.liyang.client.tianan.exception.ExceptionTiananParamInvliad;
import com.liyang.client.tianan.thread.PremiumAccurateThread;
import com.liyang.domain.advertise.AdvertiseRepository;
import com.liyang.domain.api.tianan.IApiParams;
import com.liyang.domain.api.tianan.PremiumAccurateApiParams;
import com.liyang.domain.carModel.CarModel;
import com.liyang.domain.carModel.CarModelRepository;
import com.liyang.domain.carModel.CarModelVehicleJingyou;
import com.liyang.domain.carModel.CarModelVehicleModel;
import com.liyang.domain.createenquiry.CreateEnquiry;
import com.liyang.domain.createenquiry.CreateEnquiryActRepository;
import com.liyang.domain.createenquiry.CreateEnquiryRepository;
import com.liyang.domain.createenquiry.CreateEnquiryStateRepository;
import com.liyang.domain.customer.Customer;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.insurercompany.InsureCompany;
import com.liyang.domain.insurercompany.InsureCompanyRepository;
import com.liyang.domain.offerresult.OfferResult;
import com.liyang.domain.offerresult.OfferResultData;
import com.liyang.domain.offerresult.OfferResultDataRequestHeader;
import com.liyang.domain.offerresult.OfferResultDataResult;
import com.liyang.domain.offerresult.OfferResultRepository;
import com.liyang.domain.platform.Platform;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.enums.InsureCompanyEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.XinGeService;
import com.liyang.util.FailReturnObject;
import com.liyang.util.RequestUtill;

/**
 * 天安接口，精确报价
 * 
 * @author Jmj
 *
 */
@RestController
// @RequestMapping("/dafeng")
public class PremiumAccurateController implements IApiController {

	@Value("${tianan.base.url}")
	private String tiananBaseUrl;

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private PlatformRepository platformRepository;
	@Autowired
	private CreateEnquiryRepository createEnquiryRepository;
	@Autowired
	private CreateEnquiryStateRepository createEnquiryStateRepository;
	@Autowired
	private CreateEnquiryActRepository createEnquiryActRepository;
	@Autowired
	private XinGeService xinGeService;
	@Autowired
	private AdvertiseRepository advertiseRepository;
	@Autowired
	private OfferResultRepository offerResultRepository;
	@Autowired
	private InsureCompanyRepository insureCompanyRepository;
	@Autowired
	private CarModelRepository carModelRepository;

	private final static Logger logger = LoggerFactory.getLogger(PremiumAccurateController.class);

	// @RequestMapping(value = "premiumAccurate", method = RequestMethod.POST)
	public ResponseBody mobilePremiumAccurate(PremiumAccurateApiParams tiananParams, HttpServletRequest request) {
		logger.info("天安询价开始---------------");
		IMessage message = null;
		try {
			message = buildMessage(tiananParams, request);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof ExceptionTiananInitFailed) {
				throw new FailReturnObject(ExceptionResultEnum.API_TIANAN_INITFAILED, ":" + e.getMessage());
			} else if (e instanceof ExceptionTiananParamInvliad) {
				throw new FailReturnObject(ExceptionResultEnum.API_TIANAN_PARAMINVLIAD, ":" + e.getMessage());
			} else {
				throw new FailReturnObject(ExceptionResultEnum.API_TIANAN_ERROR, ":" + e.getMessage());
			}
		}

		// 创建询价记录
		CreateEnquiry createEnquiry = new CreateEnquiry();
		createEnquiry.setOfferUnique(tiananParams.getTradeNo());
		createEnquiry.setState(createEnquiryStateRepository.findByStateCode("INENQUIRY"));
		createEnquiry.setWorkflow(createEnquiryActRepository.findByActCode("create").getStartWorkflow());
		String token = request.getHeader("token");
		Customer customer = customerRepository.findByToken(token);
		Platform platform = RequestUtill.getMobileAppPlatform(request, platformRepository);
		createEnquiry.setCustomer(customer);
		createEnquiry.setPlatform(platform);
		createEnquiry.setMobilePhone(tiananParams.getMobilePhone());
		ApiSupplierEnums apiSupplier = ApiSupplierEnums.TIANAN;
		createEnquiry.setApiSupplier(apiSupplier);
		createEnquiry.setLicenseNumber(tiananParams.getLicenseNumber());
		createEnquiry.setOwnerName(tiananParams.getOwnerName());
		createEnquiry.setCreateEnquiryParams(JSON.toJSONString(message));
		createEnquiry.setRbCode(tiananParams.getRbCode());
		createEnquiry = createEnquiryRepository.save(createEnquiry);

		// 测试用
		// OfferResult offerResult =
		// offerResultRepository.findById(Integer.valueOf(1000));
		// offerResult.setCreateEnquiry(createEnquiry);
		// offerResultRepository.save(offerResult);

		// 预先创建报价记录
		OfferResultDataRequestHeader offResDatRequestHeader = new OfferResultDataRequestHeader();
		offResDatRequestHeader.setApplicationId(request.getHeader("applicationId"));
		offResDatRequestHeader.setOfferUnique(tiananParams.getTradeNo());
		OfferResultDataResult offResDatResult = new OfferResultDataResult();
		offResDatResult.setInsuranceCompanyId(InsureCompanyEnum.TIANAN.getId());
		InsureCompany company = insureCompanyRepository.findByInsurerCompanyId(InsureCompanyEnum.TIANAN.getId());
		offResDatResult.setInsuranceCompanyName(company.getName());

		OfferResultData offerResultData = new OfferResultData();
		offerResultData.setRequestHeader(offResDatRequestHeader);
		offerResultData.setResult(offResDatResult);
		OfferResult offerResult = new OfferResult();
		offerResult.setData(offerResultData);
		offerResult.setCreateEnquiry(createEnquiry);
		offerResult.setPlatform(platform);
		offerResult = offerResultRepository.save(offerResult);

		PremiumAccurateThread command = new PremiumAccurateThread(tiananBaseUrl, tiananParams, createEnquiryRepository,
				xinGeService, advertiseRepository, offerResultRepository, createEnquiryStateRepository, message);
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("premiumAccurateThreadPool").build();
		ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(1024), threadFactory, new ThreadPoolExecutor.AbortPolicy());
		singleThreadPool.execute(command);
		logger.info("天安询价结束,线程启动---------------");
		singleThreadPool.shutdown();

		// Thread thread = new Thread(new PremiumAccurateThread(tiananBaseUrl,
		// tiananParams, createEnquiryRepository,
		// xinGeService, advertiseRepository, offerResultRepository,
		// createEnquiryStateRepository, message));
		// thread.start();

		Map<String, Object> map = new HashMap<>();
		map.put("createEnquiryId", createEnquiry.getId());
		return new ResponseBody(map);
	}

	@Override
	public IMessage buildMessage(IApiParams parmas, HttpServletRequest request) throws Exception {
		PremiumAccurateApiParams detailParams = (PremiumAccurateApiParams) parmas;
		String tradeNo = detailParams.getTradeNo();

		String quotationType = detailParams.getQuotationType();
		String cityCode = detailParams.getCityCode();
		// String businessNature = detailParams.getBusinessNature(); 取默认值50
		// String checkNo = detailParams.getCheckNo();
		// String checkCode = detailParams.getCheckCode();
		TypeDate startDate = null;
		try {
			startDate = new TypeDate(detailParams.getStartDate());
		} catch (ExceptionTiananParamInvliad e) {
			throw new ExceptionTiananParamInvliad("起保日期(交强):" + e.getMessage());
		}
		TypeDate endDate = null;
		try {
			endDate = new TypeDate(detailParams.getEndDate());
		} catch (ExceptionTiananParamInvliad e) {
			throw new ExceptionTiananParamInvliad("终保日期(交强):" + e.getMessage());
		}
		CarInfoDto carInfoDto = null;
		List<CombosDataDto> combosList = null;
		List<ExtendInfoDTO> extendInfoList = new ArrayList<>();
		// List<CheckDto> checkList = null;

		//////////////////// CarInfoDto///////////////////
		Integer id = detailParams.getCarModelId();
		String rbCode = detailParams.getRbCode();
		if (id == null || StringUtils.isEmpty(rbCode)) {
			throw new ExceptionTiananParamInvliad("carModelId或者rbCode为空");
		}
		CarModel carModel = carModelRepository.findOne(id);
		if (carModel == null) {
			throw new ExceptionTiananParamInvliad("carModelId错误");
		}
		Set<CarModelVehicleModel> carModelList = carModel.getVehicleModelList();
		Iterator<CarModelVehicleModel> it = carModelList.iterator();
		CarModelVehicleModel model = null;
		while (it.hasNext()) {
			CarModelVehicleModel temp = it.next();
			if (rbCode.equals(temp.getRbcode())) {
				model = temp;
			}
		}
		if (model == null) {
			throw new ExceptionTiananParamInvliad("rbCode错误");
		}
		JSONObject carModelRequest = JSON.parseObject(carModel.getRequestMessage());

		String licenseNo = detailParams.getLicenseNumber();
		String engineNo = carModelRequest.getString("enginNo");
		String frameNo = carModelRequest.getString("frameNo");
		String brandName = model.getBrandName();
		TypeDate enrollDate = null;
		try {
			enrollDate = new TypeDate(carModelRequest.getString("enrollDate"));
		} catch (ExceptionTiananParamInvliad e) {
			throw new ExceptionTiananParamInvliad("初次登记日期获取异常");
		}
		String ecdemicFlag = model.getEcdemicVehicleFlag();
		int seatCount = Integer.valueOf(model.getSeatCount());
		String carOwnerIdentifyNumber = detailParams.getIdNumber();
		Double actualValue = model.getActualValue();
		Double purchasePrice = model.getPurchasePrice();
		String importFlag = model.getImportFlag();
		// 字符转换
		if ("进口".equals(importFlag) || "进口车".equals(importFlag)) {
			importFlag = "A";
		} else if ("国产".equals(importFlag) || "国产车".equals(importFlag)) {
			importFlag = "B";
		} else if ("合资".equals(importFlag) || "合资车".equals(importFlag)) {
			importFlag = "C";
		}
		CarModelVehicleJingyou jingyouDto = model.getVehicleJingyouDto();
		VehicleJingyouDto vehicleJingyouDto = null;
		if (jingyouDto != null) {
			vehicleJingyouDto = new VehicleJingyouDto(jingyouDto.getVehicleCode(), jingyouDto.getVehicleName(),
					jingyouDto.getBrandName(), jingyouDto.getPrice(), jingyouDto.getFamilyName(),
					jingyouDto.getPriceType());
		} else {
			vehicleJingyouDto = new VehicleJingyouDto();
		}

		///// 选填项
		String carOwner = detailParams.getOwnerName();
		Double exhaustScale = model.getExhaustCapacity();
		String carName = model.getCarName();
		String hyModelCode = model.getHyModelCode();
		String noticeType = model.getNoticeType();
		Double wholeWeight = model.getVehicleWeight();
		// 吨转千克
		Double tonCount = Double.valueOf(model.getVehicleTonnage().floatValue() * 1000);

		String transferFlag = detailParams.getTransferFlag();
		Date transferDate = detailParams.getTransferDate();

		CarInfoDto.Builder carbuilder = new CarInfoDto.Builder(engineNo, licenseNo, frameNo, brandName, rbCode,
				enrollDate, ecdemicFlag, seatCount, carOwnerIdentifyNumber, actualValue, purchasePrice, importFlag,
				vehicleJingyouDto).carOwner(carOwner).exhaustScale(exhaustScale).carName(carName)
						.hyModelCode(hyModelCode).noticeType(noticeType).wholeWeight(wholeWeight).tonCount(tonCount);
		if (!StringUtils.isEmpty(transferFlag)) {
			carbuilder.transferFlag(transferFlag);
		}
		if (transferDate != null) {
			carbuilder.transferDate(new TypeDate(transferDate));
		}
		carInfoDto = carbuilder.build();
		//////////////////// CarInfoDto////////////////////////

		///////////////////// combosList////////////////////////

		combosList = new ArrayList<>();
		CombosDataDto combosDataDto = new CombosDataDto();
		combosDataDto.setItemKindList(detailParams.getItemKindList());
		if (StringUtils.isEmpty(detailParams.getSchemeName())) {
			throw new ExceptionTiananParamInvliad("未提供方案名");
		}
		combosDataDto.setComboNo(detailParams.getSchemeName());
		combosList.add(combosDataDto);
		///////////////////// combosList////////////////////////

		// String thirdOperatorCode = detailParams.getThirdOperatorCode();
		// String thirdOperatorName = detailParams.getThirdOperatorName();
		// String isLoanCar = detailParams.getIsLoanCar();
		// String beneficiaryName = detailParams.getBeneficiaryName();
		// String paymentMode = detailParams.getPaymentMode();

		String mobilePhone = detailParams.getMobilePhone();
		String token = request.getHeader("token");
		Customer customer = customerRepository.findByToken(token);
		Platform platform = RequestUtill.getMobileAppPlatform(request, platformRepository);

		MessagePremiumAccurate.Builder builder = new MessagePremiumAccurate.Builder(tiananBaseUrl, tradeNo,
				quotationType, cityCode, startDate, endDate, carInfoDto, combosList, extendInfoList, mobilePhone,
				customer, platform);

		TypeDate startDateBus = null;
		if (detailParams.getStartDateBus() != null) {
			try {
				startDateBus = new TypeDate(detailParams.getStartDateBus());
			} catch (ExceptionTiananParamInvliad e) {
				throw new ExceptionTiananParamInvliad("起保日期(商业):" + e.getMessage());
			}
			builder.startDateBus(startDateBus);
		}
		TypeDate endDateBus = null;
		if (detailParams.getEndDateBus() != null) {
			try {
				endDateBus = new TypeDate(detailParams.getEndDateBus());
			} catch (ExceptionTiananParamInvliad e) {
				throw new ExceptionTiananParamInvliad("终保日期(商业):" + e.getMessage());
			}
			builder.endDateBus(endDateBus);
		}

		MessagePremiumAccurate message = builder.build();
		return message;
	}

	@Override
	public ResponseBody response(IResult result) throws Exception {
		return null;
	}

}

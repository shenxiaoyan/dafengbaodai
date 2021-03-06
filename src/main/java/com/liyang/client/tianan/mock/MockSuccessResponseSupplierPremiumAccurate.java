package com.liyang.client.tianan.mock;

import com.liyang.client.BaseResponseSupplier;
import com.liyang.client.IMessage;
import com.liyang.client.IResponseSupplier;

/**
 * @author Administrator
 *
 */
public class MockSuccessResponseSupplierPremiumAccurate extends BaseResponseSupplier implements IResponseSupplier {

	private String response = "{\"actualValue\":47242,\"carPremiumCaculateNo\":\"8554a6eea7a84bfba3952a4ac63c5ab3\",\"claimAdjust\":0,\"combosList\":[{\"comboNo\":\"PACK1511737350796\",\"elrLevelAll\":\"b1\",\"elrLevelCOM\":\"b1\",\"elrLevelCTP\":\"\",\"engageList\":[{\"engageCode\":\"T007\",\"engageContent\":\"保险人实行全年昼夜案件受理服务，接到报案后及时进行事故处理。报案电话：95505。\",\"engageTitle\":\"信息查询-3\"},{\"engageCode\":\"T004\",\"engageContent\":\"本保单的承保理赔信息可通过登陆www.tianan-insurance.com、致电95505或直接在营业网点查询。若对查询结果有异议，可通过网上留言或致电95505投诉。\",\"engageTitle\":\"信息查询-1\"},{\"engageCode\":\"T163\",\"engageContent\":\"本保单项下的保险车辆为非营运用车，若从事营运活动，出险后保险人不负责赔偿责任。\",\"engageTitle\":\"非营运\"},{\"engageCode\":\"T014\",\"engageContent\":\"出险后请保留第一现场，并立即向95505和交警队报案，未经我公司同意擅自撤离现场，我公司有权增加绝对免赔率或拒绝赔偿。\",\"engageTitle\":\"保留现场提示-2\"},{\"engageCode\":\"T043\",\"engageContent\":\"如发生保险责任范围内的人身伤亡事故，双方同意按《最高人民法院关于审理人身损害赔偿案件适用法律若干问题的解释》（法释[2003]20号）的有关规定执行。\",\"engageTitle\":\"人伤适用解释\"},{\"engageCode\":\"T090\",\"engageContent\":\"如出险后，保险人发现投保人故意或者因重大过失未履行如实告知义务，足以影响保险人决定是否同意承保或者提高保险费率的，保险人有权解除合同。\",\"engageTitle\":\"如实告知免责\"}],\"exclusive\":\"A6\",\"isciCarModelDTO\":{\"brand\":\"上汽通用五菱\",\"brandCode\":\"SQD\",\"carName\":\"五菱LZW6442JVF舒适型\",\"categoryCode\":\"6\",\"categoryName\":\"客车\",\"configType\":\"UC\",\"deptCode\":\"0\",\"deptName\":\"国产\",\"noticeType\":\"LZW6442JVF\",\"series\":\"宏光\",\"seriesCode\":\"SQDHH\",\"tradeCode\":\"M0685\",\"tradeName\":\"上汽通用五菱汽车股份有限公司\"},\"itemKindList\":[{\"adjustRate\":0,\"amount\":500000,\"basePremium\":1602,\"benchMarkPremium\":1602,\"deductableFlag\":\"0\",\"deductible\":0,\"deductibleRate\":0,\"discount\":56.25,\"discountPremium\":901.13,\"grossPremium\":0,\"itemCode\":\"\",\"itemDetailName\":\"\",\"itemNo\":0,\"kindCode\":\"68\",\"kindName\":\"第三者责任险\",\"modeCode\":\"\",\"modeName\":\"\",\"netPremium\":0,\"optionalFlag\":\"\",\"premium\":901.13,\"quantity\":0,\"rate\":0,\"riskCode\":\"\",\"shortRate\":\"\",\"unitAmount\":0,\"unitInsured\":0},{\"adjustRate\":0,\"amount\":47242,\"basePremium\":1416.64,\"benchMarkPremium\":1416.64,\"deductableFlag\":\"0\",\"deductible\":0,\"deductibleRate\":0,\"discount\":56.25,\"discountPremium\":796.86,\"grossPremium\":0,\"itemCode\":\"\",\"itemDetailName\":\"\",\"itemNo\":0,\"kindCode\":\"63\",\"kindName\":\"机动车损失险\",\"modeCode\":\"\",\"modeName\":\"\",\"netPremium\":0,\"optionalFlag\":\"\",\"premium\":796.86,\"quantity\":0,\"rate\":0,\"riskCode\":\"\",\"shortRate\":\"\",\"unitAmount\":0,\"unitInsured\":0}],\"levelAll\":\"A\",\"levelCOM\":\"A\",\"levelCTP\":\"\",\"quotationBINo\":\"QBIC732017018201487756\",\"riskCode\":\"\",\"serialNo\":\"\",\"sumAmount\":547242,\"sumBenchMarkPremium\":3018.64,\"sumDiscount\":56.25,\"sumDiscountPremium\":0,\"sumPremium\":1697.99,\"sumSubPremium\":0}],\"commercialNum\":0,\"dealFlag\":\"1\",\"dealMessage\":\"套餐精确报价成功！\",\"endDate\":\"2019-02-08\",\"endDateBus\":\"2019-02-08\",\"insuredStatus\":\"0\",\"pcCarShipTaxInfoDto\":{\"lateFee\":0,\"previousPay\":0,\"sumTax\":0,\"taxActual\":0},\"peccancyAdjust\":0,\"resultDTO\":{\"resultCode\":\"SUCCESS\",\"resultMess\":\"统一平台调用成功\"},\"startDate\":\"2018-02-09\",\"startDateBus\":\"2018-02-09\",\"trafficInsuranceNum\":0,\"userYear\":\"2\"}";

	@Override
	public Object getResponse(IMessage generalMessage) throws Exception {

		return response;
	}

}

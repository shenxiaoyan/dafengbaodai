package com.liyang.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.liyang.domain.insuranceresult.InsuranceResult;
import com.liyang.enums.ExceptionResultEnum;

/**
 * @author Administrator
 *
 */
public class ExcelUtil {
	
//	/**
//	 * 根据文件名，下载excel模板
//	 * @param response
//	 * @param fileName 保存在项目中的excel模板名
//	 */
//	public static void downloadExcelModel(HttpServletResponse response,String fileName) {
//		String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
//		File file = new File(path+fileName);
//		String downloadName = null;
//		try {
//			downloadName = URLEncoder.encode(fileName, "UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		response.reset();
//		response.setContentType("application/octet-stream");
//		response.setHeader("Content-Disposition", "attachment;filename="+downloadName);
//		InputStream ins = null;
//		OutputStream outs = null;
//		try {
//			ins = new FileInputStream(file);
//			outs = response.getOutputStream();
//			byte[] bs = new byte[1024];
//			int len = -1;
//			while ((len=ins.read(bs))!=-1) {
//				outs.write(bs, 0, len);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new FailReturnObject(ExceptionResultEnum.FILEDOWNLOAD_ERROR);
//		}
//		try {
//			outs.close();
//			ins.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 根据文件名后缀，创建不同Workbook
	 * @param is
	 * @param excelFilename
	 * @return
	 * @throws IOException
	 */
	public static Workbook createWorkbook(InputStream is, String excelFilename) throws IOException{
		if (excelFilename.endsWith(".xls")) {
			return new HSSFWorkbook(is);
		}else if (excelFilename.endsWith(".xlsx")) {
			return new XSSFWorkbook(is);
		}else {
			throw new FailReturnObject(ExceptionResultEnum.EXCEL_UPLOAD_FORMAT_ERROR);
		}
	}
	
	/**
	 * 根据不同workbook，创建不同公式读取器
	 * @param workbook
	 * @return
	 */
	public static FormulaEvaluator createFormulaEvaluator(Workbook workbook) {
		if (workbook instanceof HSSFWorkbook) {
			return new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
		}else if (workbook instanceof XSSFWorkbook) {
			return new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
		}else {
			return null;
		}
	}
	
	
	
	public static Date formatDate(String source, String formatStr) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		return format.parse(source);
	}
	
	/**
	 * 从单元格中获取String值，可为空
	 * @param cell
	 * @return
	 */
	public static String getStringCellValue(Cell cell) {
		if (null == cell) {
			return null;
		}else if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
			return String.valueOf((int)cell.getNumericCellValue()).trim();
		}else if (CellType.STRING.equals(cell.getCellTypeEnum())) {
			return cell.getStringCellValue().trim();
		}else if (CellType.BLANK.equals(cell.getCellTypeEnum())) {
			return null;
		}else {
			throw new FailReturnObject(421, "第"+(cell.getRowIndex()+1)+"行"+(cell.getColumnIndex()+1)+"列，单元格格式解析失败");
		}
	}
	
	/**
	 * 从单元格中获取String值，空值抛错
	 * @param cell
	 * @return
	 */
	public static String getNotBlankStringCellValue(Cell cell) {
		String value;
		if (null == cell) {
			throw new FailReturnObject(421,"单元格为空");
		}else if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
			value = String.valueOf((int)cell.getNumericCellValue());
		}else if (CellType.STRING.equals(cell.getCellTypeEnum())) {
			value = cell.getStringCellValue();
		}else if (CellType.BLANK.equals(cell.getCellTypeEnum())) {
			throw new FailReturnObject(421,"单元格为空");
		}else {
			throw new FailReturnObject(421, "单元格格式解析失败");
		}
		if (StringUtils.isEmpty(value)) {
			throw new FailReturnObject(421,"单元格值为空");
		}
		return value.trim();
	}
	
	/**
	 * 从文本或日期单元格中读取Date
	 * @param cell
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateCellValue(Cell cell){
		if (null == cell) {
			return null;
		}else if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			}else {
				throw new FailReturnObject(421,"第"+(cell.getRowIndex()+1)+"行"+(cell.getColumnIndex()+1)+"列，单元格值日期读取错误");
			}
		}else if(CellType.STRING.equals(cell.getCellTypeEnum())){
			try {
				if (cell.getStringCellValue().contains("-")) {
					return formatDate(cell.getStringCellValue(), "yyyy-MM-dd");
				}else if(cell.getStringCellValue().contains("/")){
					return formatDate(cell.getStringCellValue(), "yyyy/MM/dd");
				}else {
					throw new ParseException(null, 0);
				}
			} catch (ParseException e) {
				throw new FailReturnObject(421, "第"+(cell.getRowIndex()+1)+"行"+(cell.getColumnIndex()+1)+"列，单元格值日期格式错误");
			}
		}else if(CellType.BLANK.equals(cell.getCellTypeEnum())){
			return null;
		}else {
			throw new FailReturnObject(421, "第"+(cell.getRowIndex()+1)+"行"+(cell.getColumnIndex()+1)+"列，单元格值日期格式无法读取");
		}
	}
	
	
	/**
	 * 从单元格中获取double值，保留两位小数
	 * @param cell
	 * @return
	 */
	public static Double getDoubleCellValue(Cell cell, FormulaEvaluator formulaEvaluator) {
		double value;
		if (null == cell) {
			value = 0;
		}else if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
			BigDecimal b = new BigDecimal(cell.getNumericCellValue());
			value = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}else if (CellType.STRING.equals(cell.getCellTypeEnum())) {
			try {
				String stringValue = cell.getStringCellValue();
				value = Double.valueOf(stringValue);
			} catch (Exception e) {
				throw new FailReturnObject(421, "第"+(cell.getRowIndex()+1)+"行"+(cell.getColumnIndex()+1)+"列，数值读取失败");
			}
			BigDecimal b = new BigDecimal(value);
			value = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}else if (CellType.BLANK.equals(cell.getCellTypeEnum())) {
			value = 0.0;
		}else if (CellType.FORMULA.equals(cell.getCellTypeEnum())) {
			value = formulaEvaluator.evaluate(cell).getNumberValue();
		}else {
			throw new FailReturnObject(421, "第"+(cell.getRowIndex()+1)+"行"+(cell.getColumnIndex()+1)+"列，单元格格式解析失败");
		}
		return value;
	}
	
	
	
	public static <T> void downloadExcel(String title, String[] headers, String[] codes, Class<T> clazz, List<T> dataSet,
			HttpServletResponse response) throws Exception {
		String downloadName = null;
		try {
			downloadName = URLEncoder.encode(title + ".xls", "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + downloadName);
		OutputStream outs = null;
		try {
			outs = response.getOutputStream();
			ExcelUtil.exportExcel(title, headers, codes, clazz, dataSet, outs);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailReturnObject(ExceptionResultEnum.FILEDOWNLOAD_ERROR);
		}
		try {
			outs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用导出excel
	 * 
	 * @param title：sheet名
	 * @param headers：列名
	 * @param codes:列名对应的属性
	 * @param clazz:对应class
	 * @param dataset：放入javabean数据集合
	 * @param out：流对象
	 */
	public static <T> void exportExcel(String title, String[] headers, String[] codes, Class<T> clazz, List<T> dataSet,
			OutputStream out) throws Exception {
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setDefaultColumnWidth(15);
		// TODO 设置格式
		// HSSFCellStyle style = wb.createCellStyle();
		// 创建表头标题
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 创建数据行
		if (null != dataSet) {
			Iterator<T> it = dataSet.iterator();
			int index = 0;
			while (it.hasNext()) {
				index++;
				row = sheet.createRow(index);
				T cpr = it.next();
				for (int i = 0; i < headers.length; i++) {
					cell = row.createCell(i);
					Method method = clazz.getMethod("get" + CommonUtil.toUpperCaseFirstOne(codes[i]));
					Object value = method.invoke(cpr);
					if (value instanceof Double) {
						setCellDoubleValue(cell, (Double) value);
					} else if (value instanceof Date) {
						setCellStringDateValue(cell, (Date) value);
					} else if (value instanceof String) {
						setCellStringValue(cell, (String) value);
					} else {
						cell.setCellType(CellType.BLANK);
					}
				}
			}
		}
		try {
			wb.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	public static void setCellStringValue(HSSFCell cell, String str) {
		if (str == null) {
			cell.setCellType(CellType.BLANK);
		}else {
			cell.setCellValue(str);
		}
	}
	
	public static void setCellDoubleValue(HSSFCell cell, Double doubleValue) {
		if (doubleValue == null) {
			cell.setCellType(CellType.BLANK);
		}else {
			cell.setCellValue(doubleValue);
		}
	}
	
	public static void setCellStringDateValue(HSSFCell cell, Date date) {
		if (date == null) {
			cell.setCellType(CellType.BLANK);
		}else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String signDate = format.format(date);
			cell.setCellValue(signDate);
		}
	}
	
	/**
	 * excel值读取至对象属性
	 * @param excelFile	上传excel文件
	 * @param clz	javaBean的class类型
	 * @return	javaBean集合
	 */
	public static <T> List<T> readExcel(MultipartFile excelFile, Class<T> clz){
		String fileName = excelFile.getOriginalFilename();
		InputStream ins = null;
		Workbook wb = null;
		try {
			ins = excelFile.getInputStream();
			wb = ExcelUtil.createWorkbook(ins, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<T> list = new ArrayList<>();
		if (wb.getNumberOfSheets() <1 ) {
			throw new FailReturnObject(ExceptionResultEnum.EXCEL_SHEET_ANALIZE_ERROR);
		}
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (null != sheet) {
				int totalRows = sheet.getPhysicalNumberOfRows();
				int totalCells = 0;
				if (totalRows >= 3 && sheet.getRow(2) != null) {
					totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
				}
				//获取表单中的set方法属性
				String[] nameOfSetMethod = new String[totalCells];
				Method [] setMethods = new Method[totalCells];
				try {
					for (int j = 0; j < totalCells; j++) {
						nameOfSetMethod[j] = sheet.getRow(1).getCell(j).getStringCellValue();
						String first = nameOfSetMethod[j].substring(0, 1);
						nameOfSetMethod[j] = nameOfSetMethod[j].replaceFirst(first, first.toUpperCase());
						System.out.println(nameOfSetMethod[j]);
						Method[] allMethod = clz.getMethods();
						for (Method method : allMethod) {
							if (method.getName().contains("set"+nameOfSetMethod[j])) {
								setMethods[j] = method;
								break;
							}
						}
					}
				} catch (Exception e) {
					throw new FailReturnObject(ExceptionResultEnum.EXCEL_MODULE_ANALIZE_ERROR);
				}
				for (int r = 2; r < totalRows; r++) {
					Row row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					T t = null;
					try {
						t = clz.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					for (int c = 0; c < totalCells; c++) {
						Cell cell = row.getCell(c);
						try {
							if (CellType.STRING.equals(cell.getCellTypeEnum())) {
								String value = cell.getStringCellValue();
								setMethods[c].invoke(t, value);
							}else if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									setMethods[c].invoke(t, cell.getDateCellValue());
								}else {
									setMethods[c].invoke(t, cell.getNumericCellValue());
								}
							}else {
//								setMethods[c].invoke(t, new Object[0]);
								throw new FailReturnObject(209, "第"+(r+1)+"行，第"+(c+1)+"列数据为空，请检查");
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new FailReturnObject(209, "第"+(r+1)+"行，第"+(c+1)+"列，数据为空或无法读取，请检查");
						}
					}
					list.add(t);
				}
			}
		}
		return list;
	}
	
	public static void exportInsResult(String title, String[] headers, List<InsuranceResult> insResultList,	OutputStream outs) {
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		sheet.setDefaultColumnWidth(15);
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell;
		for (int i = 0; i < headers.length; i++) {
			cell = row.createCell(i);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		//创建数据行
		Iterator<InsuranceResult> it = insResultList.iterator();
		int index = 0;
		while(it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			InsuranceResult insResult = it.next();
			for (int i = 0; i < headers.length; i++) {
				cell = row.createCell(i);
				switch (i) {
				case 0:
					setCellStringValue(cell, insResult.getData().getOrderId());
					break;
				case 1:
					setCellStringValue(cell, insResult.getData().getBiPolicyNo());
					break;
				case 2:
					setCellStringValue(cell, insResult.getData().getCiPolicyNo());
					break;
				case 3:
					setCellStringValue(cell, insResult.getSubmitProposal().getOfferResult().getData().getResult().getInsuranceCompanyName());
					break;
				case 4:
					setCellStringValue(cell, insResult.getData().getLicenseNumber());
					break;
				case 5:
					setCellStringValue(cell, insResult.getSubmitProposal().getParams().getOwnerName());
					break;
				case 6:
					setCellDoubleValue(cell, insResult.getSubmitProposal().getOfferResult().getData().getResult().getOfferDetail().getJSONObject("forcePremium").getDouble("quotesPrice"));
					break;
				case 7:
					setCellDoubleValue(cell, insResult.getSubmitProposal().getOfferResult().getData().getResult().getOfferDetail().getJSONObject("taxPrice").getDouble("quotesPrice"));
					break;
				case 8:
					setCellDoubleValue(cell, insResult.getSubmitProposal().getOfferResult().getData().getResult().getOfferDetail().getJSONObject("forcePremium").getDouble("quotesPrice")+
							insResult.getSubmitProposal().getOfferResult().getData().getResult().getOfferDetail().getJSONObject("taxPrice").getDouble("quotesPrice"));
					break;
				case 9:
					setCellStringDateValue(cell, insResult.getCreatedAt());
					break;
				case 10:
					setCellStringValue(cell, insResult.getSubmitProposal().getParams().getCustomerPhone());
					break;
				case 11:
					setCellStringValue(cell, insResult.getSubmitProposal().getParams().getContactName());
					break;
				case 12 :
					setCellStringValue(cell, insResult.getSubmitProposal().getParams().getContactPhone());
					break;
				case 13:
					setCellStringValue(cell, insResult.getSubmitProposal().getParams().getContactAddress().getContactAddressDetail());
					break;
				case 14:
					setCellStringValue(cell, insResult.getSubmitProposal().getParams().getDafengContactName());
					break;
				case 15:
					setCellStringValue(cell, insResult.getSubmitProposal().getParams().getDafengContactPhone());
					break;
				case 16:
					setCellStringValue(cell, insResult.getSubmitProposal().getParams().getDafengAddress());
					break;
				default:
					break;
				}
			}
		}
		try {
			wb.write(outs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}

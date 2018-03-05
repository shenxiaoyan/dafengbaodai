package com.liyang.handle;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyang.domain.exception.Exception;
import com.liyang.domain.exception.ExceptionRepository;
import com.liyang.helper.CustomerDisableException;
import com.liyang.helper.DisplayException;
import com.liyang.helper.IdentifyErrorException;
import com.liyang.util.FailReturnObject;
import com.liyang.util.ReturnObjectImpl;
import com.mysql.jdbc.MysqlDataTruncation;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
/**
 * @author Administrator
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	ExceptionRepository exceptionRepository;

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(java.lang.Exception.class)
	@ResponseBody
	public Object handleBizExp(java.lang.Exception ex) throws java.lang.Exception {
		System.out.println(new Date()+"------------------------------全局捕获异常------------------------------------");
		System.out.println("堆栈信息如下：");
		ex.printStackTrace();
		try {
			if(ex instanceof IdentifyErrorException){
				return new com.liyang.helper.ResponseBody(100 , ex.getMessage());
			}else if(ex instanceof DisplayException){
				return new com.liyang.helper.ResponseBody(100 , ex.getMessage());
			}else if(ex instanceof CustomerDisableException){
				return new com.liyang.helper.ResponseBody(555 , "用户已被禁用");
			}else if (ex instanceof MySQLIntegrityConstraintViolationException) {
				return new com.liyang.helper.ResponseBody(356, "已存在相同信息");
			}else if (ex instanceof DataIntegrityViolationException) {
				return new com.liyang.helper.ResponseBody(10, "违反数据库字段约束");
			}else{
				if (ex instanceof FailReturnObject) {
					ReturnObjectImpl returnObjectImpl = new ReturnObjectImpl();
					returnObjectImpl.setActionStatus(((FailReturnObject) ex).getActionStatus());
					returnObjectImpl.setErrorCode(((FailReturnObject) ex).getErrorCode());
					returnObjectImpl.setErrorInfo(((FailReturnObject) ex).getErrorInfo());
					return returnObjectImpl;
				} else {
		
					Exception exception = new Exception();
					exception.setActionStatus("FAIL");
					exception.setErrorCode(-1);
					StringBuilder sb = new StringBuilder();
					sb.append(ex.getClass().toString())
					.append("\n")
					.append(ex.getMessage())
					.append("\n")
					.append(ex.getCause())
					.append("\n")
					.append(Arrays.toString(ex.getStackTrace()));
					exception.setErrorInfo(sb.toString());
					exceptionRepository.save(exception);
					ReturnObjectImpl returnObjectImpl = new ReturnObjectImpl();
					returnObjectImpl.setActionStatus(null);
					returnObjectImpl.setErrorCode(100);
					returnObjectImpl.setErrorInfo(ex.getMessage());
					return returnObjectImpl;
				}
			}
		} catch (java.lang.Exception e) {
			return new com.liyang.helper.ResponseBody(555, "全局异常捕获中发生异常！！！");
		}
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public com.liyang.helper.ResponseBody  httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex){
		ex.printStackTrace();
		return new com.liyang.helper.ResponseBody(10, "错误的数据提交，请检查参数！");
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public com.liyang.helper.ResponseBody  constraintViolationExceptionHandler(ConstraintViolationException e){
		logger.error("违反实体字段约束\n"+getTraceInfo(e));
		Set<ConstraintViolation<?>> set = e.getConstraintViolations();
		if (!set.isEmpty()) {
			StringBuffer message = new StringBuffer("");
			for (ConstraintViolation<?> constraintViolation : set) {
				message.append(constraintViolation.getMessage() + "、");
			}
			return new com.liyang.helper.ResponseBody(20, message.toString());
		}else {
			return new com.liyang.helper.ResponseBody(20, e.getMessage());
		}
	}
	
	@ExceptionHandler(MysqlDataTruncation.class)
	@ResponseBody
	public com.liyang.helper.ResponseBody mysqlDataTruncationHandler(MysqlDataTruncation ex){
		logger.error(getTraceInfo(ex));
		return new com.liyang.helper.ResponseBody(30, "违反数据库字段约束！");
	}
	
	/**
	 * 收集堆栈信息
	 * @author Adam
	 */
	public static String getTraceInfo(java.lang.Exception e){
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}
	
}











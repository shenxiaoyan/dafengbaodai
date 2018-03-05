package com.liyang.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import com.liyang.domain.base.AbstractAuditorAct;
import com.liyang.domain.base.AbstractAuditorAct.ActGroup;
import com.liyang.domain.base.AbstractWorkflow;
import com.liyang.domain.base.AbstractWorkflowAct;
import com.liyang.domain.base.AbstractWorkflowAct.ActType;
import com.liyang.domain.base.AbstractWorkflowEntity;
import com.liyang.domain.base.AbstractWorkflowFile;
import com.liyang.domain.base.AbstractWorkflowLog;
import com.liyang.domain.base.AbstractWorkflowState;
import com.liyang.domain.base.FileRepository;
import com.liyang.domain.base.StateRepository;
import com.liyang.domain.role.Role;
import com.liyang.domain.user.User;
import com.liyang.domain.user.UserRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.message.ImageInfo;
import com.liyang.service.FileUploadService.OssFile;
import com.liyang.service.FileUploadService.OssImage;
import com.liyang.util.CommonUtil;
import com.liyang.util.FailReturnObject;
import com.liyang.util.SuccessReturnObject;
import com.liyang.util.WechatImageAsyncFetchEvent;

/**
 * @author Administrator
 *
 * @param <T>
 * @param <W>
 * @param <A>
 * @param <S>
 * @param <L>
 * @param <F>
 */
public abstract class AbstractWorkflowService<T extends AbstractWorkflowEntity, 
		W extends AbstractWorkflow, A extends AbstractWorkflowAct, S extends AbstractWorkflowState, 
		L extends AbstractWorkflowLog, F extends AbstractWorkflowFile> extends AbstractAuditorService<T, S, A, L> {

	@Autowired
	private TIMService timService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	protected FileRepository<F> fileRepository;

	@Transient
	abstract public F getFileLogInstance();

	@Autowired
	protected ApplicationContext applicationContext;

	@Autowired
	private StateRepository<S> stateRepository;

	@Autowired
	protected WechatGetService wechatGetService;
	
	
	@Autowired
	private FileUploadService fileUploadService;

	protected static final String IMAGE_URL_TEMPLATE = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
	
	@Value("${aliyun.file.url}")
	private String aliyunFileUrl;

	@Transient
	public FileRepository<F> getFileRepository() {
		return fileRepository;
	}

	@Override
	public A getAct(T entity, String code, Role role) {
		A act = super.getAct(entity, code, role);
		if (!AbstractAuditorAct.ActGroup.OPERATE.equals(act.getActGroup())) {
			return act;
		}
		if (entity.getWorkflow() == null) {
			if (act.getActType().equals(ActType.START)) {
				return act;
			} else {
				throw new FailReturnObject(ExceptionResultEnum.ABSTRACT_WORKFLOW_NOTASKSTART_ERROR);
//				throw new FailReturnObject(650, "没有任务以这个动作启动");
			}
		} else {
			if (act.getActType().equals(ActType.START)) {
				throw new FailReturnObject(ExceptionResultEnum.ABSTRACT_WORKFLOW_START_ERROR);
//				throw new FailReturnObject(1300, "工作流已经启动");
			} else {
				return act;
			}
		}
	}

	@Override
	public T doBeforeAct(T entity, A act, Object linked, User fromUser) {
		super.doBeforeAct(entity, act, linked, fromUser);

		if(act.getActGroup().equals(ActGroup.FILE_PACKAGE)){
			downloadAllFilesAnduploadToAliyun(entity);
		}
		
		if (act.getTargetState() != null) {
			entity.setState(act.getTargetState());
		}

		if (entity.getWorkflow() == null && AbstractWorkflowAct.ActType.START.equals(act.getActType())) {
			entity.setWorkflow(act.getStartWorkflow());
		}
		if (AbstractWorkflowAct.ActType.END.equals(act.getActType())) {
			if (act.getNextWorkflow() != null) {
				entity.setWorkflow(act.getNextWorkflow());
			} else {
				entity.setWorkflow(null);
			}
		}

		return entity;
	}

	@Override
	public void doActLog(T entity, Object linked) {
		L log = buildLogByEntity(entity, linked);
		if (entity.getFileObject() != null) {
			log = fillFileObjectToLog(entity, log);
		}
		log = fillmultiWechatImageToLog(entity, log);

		getLogRepository().save(log);
	}

	@Override
	protected L buildLogByEntity(T entity, Object linked) {
		// TODO Auto-generated method stub
		L log = super.buildLogByEntity(entity, linked);
		log.setMessageType(((AbstractWorkflowAct) entity.getLastAct()).getMessageType());
		log.setWorkflow(entity.getWorkflow());
		return log;
	}

	public L fillmultiWechatImageToLog(T entity, L log) {
		String[] wechatFiles = entity.getWechatFiles();
		for (String fileName : wechatFiles) {
			F fileLogInstance = getFileLogInstance();
			fileLogInstance.setEntity(entity);
			fileLogInstance.setAct(log.getAct());
			if (entity.getSubcategory() != null) {
				fileLogInstance.setSubcategory(entity.getSubcategory());
			}
			if (entity.getTopcategory() != null) {
				fileLogInstance.setTopcategory(entity.getTopcategory());
			}
			if (CommonUtil.getCurrentUserDepartment() != null) {
				fileLogInstance.setUploaderDepartmenttype(CommonUtil.getCurrentUserDepartment().getType());
			}
			fileLogInstance.setOriginalFileName(
					String.format(IMAGE_URL_TEMPLATE, wechatGetService.getCacheTokenTotal(), fileName));
			fileLogInstance.setUploadType("image");
			fileLogInstance.setLog(log);
			fileLogInstance.setClient(log.getClient());
			fileLogInstance.setAppCode(log.getAppCode());
			fileLogInstance.setIp(log.getIp());
			F file = fileRepository.save(fileLogInstance);
			// log.getFiles().add(file);
			applicationContext.publishEvent(new WechatImageAsyncFetchEvent(entity, file));
		}

		return log;
	}

	private L fillFileObjectToLog(T entity, L log) {
		OssImage fileObject = entity.getFileObject();
		F fileLogInstance = getFileLogInstance();
		fileLogInstance.setEntity(entity);
		fileLogInstance.setAct(log.getAct());
		if (fileObject.getSubcategory() != null) {
			fileLogInstance.setSubcategory(fileObject.getSubcategory());
		}
		if (fileObject.getTopcategory() != null) {
			fileLogInstance.setTopcategory(fileObject.getTopcategory());
		}
		fileLogInstance.setFileSize(fileObject.getFileSize());
		fileLogInstance.setFileType(fileObject.getFileType());
		fileLogInstance.setNewFileName(fileObject.getNewFileName());
		fileLogInstance.setOriginalFileName(fileObject.getOriginalFileName());
		fileLogInstance.setUploadType(fileObject.getUploadType());
		fileLogInstance.setIp(log.getIp());
		fileLogInstance.setClient(log.getClient());
		fileLogInstance.setAppCode(log.getAppCode());
		if (CommonUtil.getCurrentUserDepartment() != null) {
			fileLogInstance.setUploaderDepartmenttype(CommonUtil.getCurrentUserDepartment().getType());
		}

		if (fileObject.getImageInfo() != null) {
			ImageInfo[] imageInfo = fileObject.getImageInfo();
			for (ImageInfo imageInfo2 : imageInfo) {
				if (imageInfo2.getType().equals(1)) {
					fileLogInstance.setLargeImage(imageInfo2.getUrl());
				} else if (imageInfo2.getType().equals(2)) {
					fileLogInstance.setMiddleImage(imageInfo2.getUrl());
				} else if (imageInfo2.getType().equals(3)) {
					fileLogInstance.setSmallImage(imageInfo2.getUrl());
				}
			}
		}
		fileLogInstance.setLog(log);
		log.getFiles().add(fileLogInstance);
		return log;
	}

	public OssFile downloadAllFilesAnduploadToAliyun(T entity) {

		Set<AbstractWorkflowFile> files = entity.getFiles();
		
		if(files.isEmpty()){
			return null;
		}
		
		ZipOutputStream zipOutputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		SuccessReturnObject successReturnObject = null;
		try {
			zipOutputStream = new ZipOutputStream(new BufferedOutputStream(byteArrayOutputStream));

			for (AbstractWorkflowFile abstractWorkflowFile : files) {
				if(abstractWorkflowFile.getLog() == null){
					continue;
				}
				String url = aliyunFileUrl + abstractWorkflowFile.getNewFileName();
				ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url),
						HttpMethod.GET);
				// 发送请求
				ClientHttpResponse response = request.execute();
				InputStream is = response.getBody();
				BufferedInputStream bis = new BufferedInputStream(is);
				try {
					zipOutputStream.putNextEntry(new ZipEntry(abstractWorkflowFile.getTopcategory() + "/"
							+ abstractWorkflowFile.getSubcategory() + "/" + abstractWorkflowFile.getNewFileName()));

					int tag;
					while ((tag = bis.read()) != -1) {
						zipOutputStream.write(tag);
					}
				} finally {
					bis.close();
					response.close();
				}

			}

		} catch (IOException | URISyntaxException e) {
			throw new FailReturnObject(ExceptionResultEnum.ABSTRACT_WORKFLOW_NOFILE_ERROR);
//			throw new FailReturnObject(3321, "文件没有发现");
		} finally {
			if (zipOutputStream != null) {
				try {
					zipOutputStream.close();
				} catch (IOException e) {
					throw new FailReturnObject(ExceptionResultEnum.ABSTRACT_WORKFLOW_ZIP_ERROR);
//					throw new FailReturnObject(4618, "zip流关闭错误");
				}
				ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
				successReturnObject = fileUploadService.uploadByIOStream(byteArrayInputStream);
				OssFile file = (OssFile)successReturnObject.getResult();
				F fileLogInstance = getFileLogInstance();
				fileLogInstance.setEntity(entity);
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
				String dateString = formatter.format(new Date());
				fileLogInstance.setSubcategory(dateString);
				fileLogInstance.setTopcategory("文档打包");
				
				if (CommonUtil.getCurrentUserDepartment() != null) {
					fileLogInstance.setUploaderDepartmenttype(CommonUtil.getCurrentUserDepartment().getType());
				}
				fileLogInstance.setNewFileName(file.getNewFileName());
				fileLogInstance.setFileSize(file.getFileSize());
				fileLogInstance.setFileType("ZIP");
				fileLogInstance.setUploadType("file");
				fileLogInstance.setAppCode(request.getHeader("app_code"));
				fileLogInstance.setClient(request.getHeader("client"));
				fileLogInstance.setIp(request.getRemoteHost());
				
				fileRepository.save(fileLogInstance);
				entity.setFilePackage(file);
				
			}
		}

		return (OssFile)successReturnObject.getResult();
	}

}

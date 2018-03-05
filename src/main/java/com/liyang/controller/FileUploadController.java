package com.liyang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyang.domain.customer.CustomerRepository;
import com.liyang.domain.platform.PlatformRepository;
import com.liyang.domain.submitproposal.SubmitProposalFile;
import com.liyang.domain.submitproposal.SubmitProposalFileRepository;
import com.liyang.enums.ExceptionResultEnum;
import com.liyang.helper.ResponseBody;
import com.liyang.service.AuthorityJudgeService;
import com.liyang.service.FileUploadService;
import com.liyang.util.Base64;
import com.liyang.util.FailReturnObject;
import com.liyang.util.HttpUtil;
import com.liyang.util.SuccessReturnObject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/dafeng")
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private SubmitProposalFileRepository submitproposalFileRepository;

	@Autowired
	AuthorityJudgeService authorityJudgeService;

	@Autowired
	PlatformRepository platformRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Value("${xmcxapi.apikey}")
	private String xmcxApiKey;

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public ResponseBody headUpload(@RequestParam(value = "file") MultipartFile head) {
		ResponseBody result = new ResponseBody();

		SuccessReturnObject return1 = fileUploadService.uploadByFileTypeName(head, "image");
		try {
			result.setErrorCode(0);
			result.setData(return1.getResult());
			result.setErrorInfo("ok");
		} catch (Exception e) {
			result = new ResponseBody(ExceptionResultEnum.FILEUPLOAD_PICTURE_FAIL_ERROE);
		}
		return result;
	}







	@RequestMapping(value = "/base64Upload", method = RequestMethod.POST)
	public ResponseBody base64Upload(@RequestBody String base64code, @RequestParam(value = "filename") String filename,
			@RequestParam(value = "maxSize", required = false) Integer maxSize) throws Exception {// maxSize单位为字节
		// base64转成MultipartFile
		byte[] bytes = Base64.decodeBase64(base64code);
		MultipartFile file = new MockMultipartFile(filename, bytes);
		// 文件大小限定
		if (!StringUtils.isEmpty(maxSize) && file.getSize() > maxSize) {
			throw new FailReturnObject(100, "本次上传文件限制为" + maxSize + "字节.当前文件大小为" + file.getSize() + "字节");
		}
		return headUpload(file);
	}

	@RequestMapping(value = "/xiaomaUpload", method = RequestMethod.POST)
	public ResponseBody handleUploadProcess(@RequestParam(value = "file") MultipartFile file,
			@RequestParam(value = "fileType", required = false) String fileType,
			@RequestParam(value = "applicationId", required = false) String applicationId, HttpServletRequest request)
					throws Exception {

		String token = request.getHeader("token");

		// 第三方平台是否具有访问权
		if (applicationId != null) {
			authorityJudgeService.authorityJudge(applicationId);
		}

		// 文件处理与上传s
		// 强势规定文件类型为图片
		fileType = "image"; 
		SuccessReturnObject succeessObject = fileUploadService.uploadByFileTypeName(file, fileType);

		// 将文件信息保存数据库
		// 文件处理结果Json
		JSONObject resultObject = JSONObject.fromObject(succeessObject).getJSONObject("result"); 
		imageFileSave(resultObject, applicationId, token);

		System.out.println("上传文件到阿里云");
		// 文件上传到小马接口
		String resultStr = HttpUtil.postFile(file,
				"http://182.92.24.162:8088/xmcxapi/webService/enquiry/upToOrderImages?api_key=" + xmcxApiKey + "");

		// 小马返回数据封装成指定的格式,返回
		ObjectMapper objectMapper = new ObjectMapper();
		ResponseBody responseBody = new ResponseBody();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = objectMapper.readValue(resultStr, Map.class);
		if (null != resultMap.get("successful") && true == (Boolean) resultMap.get("successful")) {
			responseBody.setErrorCode(0);
			responseBody.setErrorInfo("OK");
			// 为的是安卓返回特定数据
			dataMap.put("path", resultMap.get("data"));
			responseBody.setData(dataMap);
		} else {
			responseBody.setErrorCode(100);
			responseBody.setData(null);
			responseBody.setErrorInfo("文件上传错误,请重新上传");
		}
		return responseBody;

	}

	public void imageFileSave(JSONObject resultObject, String applicationId, String token) {

		// 获取上传图像信息的相关对象与数组
		JSONArray imageInfoArray = resultObject.getJSONArray("imageInfo");

		// 设置相关图像信息至提交核保文件中
		SubmitProposalFile submitProposalFile = new SubmitProposalFile();
		submitProposalFile.setSmallImage(imageInfoArray.getJSONObject(0).getString("url"));
		submitProposalFile.setMiddleImage(imageInfoArray.getJSONObject(1).getString("url"));
		submitProposalFile.setLargeImage(imageInfoArray.getJSONObject(2).getString("url"));
		submitProposalFile.setOriginalFileName(resultObject.getString("originalFileName"));
		submitProposalFile.setNewFileName(resultObject.getString("newFileName"));
		submitProposalFile.setFileSize(resultObject.getLong("fileSize"));
		submitProposalFile.setFileType(resultObject.getString("fileType"));
		submitProposalFile.setUploadType(resultObject.getString("uploadType"));
		// 设备来源记录，未写，有需要添加

		// 关联用户,平台信息
		// String userToken = request.getHeader("token");
		String userToken = token;
		if (userToken == null) {
			// 关联平台
			if (applicationId == null) {
				throw new FailReturnObject(ExceptionResultEnum.FILEUPLOAD_APPLICATIONID_ERROR);
				// throw new FailReturnObject(100, "请带上applicationId,重新上传文件");
			} else {
				submitProposalFile.setPlatform(platformRepository.findByApplicationId(applicationId));
			}
		} else {
			// 关联用户
			submitProposalFile.setCustomer(customerRepository.findByToken(userToken));
		}

		// 保存对象
		submitproposalFileRepository.save(submitProposalFile);
	}
}

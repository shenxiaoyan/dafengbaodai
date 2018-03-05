package com.liyang.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.tencent.xinge.ClickAction;
import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.Style;
import com.tencent.xinge.TagTokenPair;
import com.tencent.xinge.TimeInterval;
import com.tencent.xinge.XingeApp;

/**
 * @author Administrator
 *
 */
public class XingeUtils {
	private static XingeApp xinge;
	private static Message message;
	private  static MessageIOS messageIOS;
	
	

	/**
	 * 单个设备下发透传消息
	 * @param xinge
	 * @param tokenAndroid
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceMessage(XingeApp xinge,String tokenAndroid) {
		Message message = new Message();
		message.setTitle("title");
		message.setContent("content");
		message.setType(Message.TYPE_MESSAGE);
		message.setExpireTime(86400);
		JSONObject ret = xinge.pushSingleDevice(tokenAndroid, message);
		return ret;
	}

	/**
	 * 单个设备下发通知消息  安卓
	 * @param message
	 * @param tokenAndroid
	 * @param xinge
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceNotification(Message message,String tokenAndroid,XingeApp xinge) {
		JSONObject ret = xinge.pushSingleDevice(tokenAndroid, message);
		return ret;
	}

	/**
	 * 单个设备下发通知Intent
	 * setIntent()的内容需要使用intent.toUri(Intent.URI_INTENT_SCHEME)方法来得到序列化后的Intent(自定义参数也包含在Intent内）
	 * 终端收到后可通过intent.parseUri()来反序列化得到Intent
	 * @param message
	 * @param tokenAndroid
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceNotificationIntent(Message message,String tokenAndroid) {
		JSONObject ret = xinge.pushSingleDevice(tokenAndroid, message);
		return ret;
	}

	/**
	 * 单个设备静默通知(iOS7以上)
	 * @param messageIOS
	 * @param tokenIOS
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceMessageIOS(MessageIOS messageIOS,String tokenIOS) {
		MessageIOS remoteMessageIOS = new MessageIOS();
		remoteMessageIOS.setType(MessageIOS.TYPE_REMOTE_NOTIFICATION);
		return xinge.pushSingleDevice(tokenIOS, messageIOS, XingeApp.IOSENV_DEV);
	}

	/**
	 * 单个设备下发通知消息iOS
	 * 没有问题
	 * @param messageIOS
	 * @param tokenIOS
	 * @param xinge
	 * @return
	 */
	public static JSONObject demoPushSingleDeviceNotificationIOS(MessageIOS messageIOS,String tokenIOS,XingeApp xinge, String iosEnv) {
		JSONObject ret = null; 
		if ("prod".equals(iosEnv)) {
			ret = xinge.pushSingleDevice(tokenIOS, messageIOS,XingeApp.IOSENV_PROD);
		}else {
			ret = xinge.pushSingleDevice(tokenIOS, messageIOS,XingeApp.IOSENV_DEV);
		}
		return ret;
	}

	/**
	 * 下发单个账号
	 * @return
	 */
	public static  JSONObject demoPushSingleAccount() {
		Message message = new Message();
		message.setExpireTime(86400);
		message.setTitle("title");
		message.setContent("content");
		message.setType(Message.TYPE_MESSAGE);
		JSONObject ret = xinge.pushSingleAccount(0, "joelliu", message);
		return ret;
	}

	/**
	 * 下发多个账号
	 * @return
	 */
	public static JSONObject demoPushAccountList() {
		Message message = new Message();
		message.setExpireTime(86400);
		message.setTitle("title");
		message.setContent("content");
		message.setType(Message.TYPE_MESSAGE);
		List<String> accountList = new ArrayList<String>();
		accountList.add("joelliu");
		JSONObject ret = xinge.pushAccountList(0, accountList, message);
		return ret;
	}

	/**
	 * 下发IOS单个账号
	 * @return
	 */
	public  static JSONObject demoPushSingleAccountIOS() {
		MessageIOS message = new MessageIOS();
		message.setExpireTime(86400);
		message.setAlert("ios test");
		message.setBadge(1);
		message.setSound("beep.wav");
		TimeInterval acceptTime1 = new TimeInterval(0, 0, 23, 59);
		message.addAcceptTime(acceptTime1);
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("key1", "value1");
		custom.put("key2", 2);
		message.setCustom(custom);

		JSONObject ret = xinge.pushSingleAccount(0, "joelliu", messageIOS, XingeApp.IOSENV_DEV);
		return ret;
	}

	/**
	 * 下发IOS多个账号
	 * @return
	 */
	public static JSONObject demoPushAccountListIOS() {
		List<String> accountList = new ArrayList<String>();
		accountList.add("joelliu");
		JSONObject ret = xinge.pushAccountList(0, accountList, messageIOS, XingeApp.IOSENV_DEV);
		return ret;
	}

	/**
	 * 下发所有设备
	 * @param message
	 * @return
	 */
	public  static JSONObject demoPushAllDevice(Message message) {
		JSONObject ret = xinge.pushAllDevice(0, message);
		return ret;
	}

	/**
	 * 下发标签选中设备
	 * @param message
	 * @return
	 */
	public static JSONObject demoPushTags(Message message) {
		List<String> tagList = new ArrayList<String>();
		tagList.add("joelliu");
		tagList.add("phone");
		JSONObject ret = xinge.pushTags(0, tagList, "OR", message);
		return ret;
	}

	/**
	 * 大批量下发给帐号 
	 * iOS 请构造MessageIOS消息
	 * @return
	 */
	public static JSONObject demoPushAccountListMultiple() {
		Message message = new Message();
		message.setExpireTime(86400);
		message.setTitle("title");
		message.setContent("content");
		message.setType(Message.TYPE_NOTIFICATION);

		JSONObject ret = xinge.createMultipush(message);
		if (ret.getInt("ret_code") != 0)
			return ret;
		else {
			JSONObject result = new JSONObject();

			List<String> accountList1 = new ArrayList<String>();
			accountList1.add("joelliu1");
			accountList1.add("joelliu2");
			// ...
			result.append("all",
					xinge.pushAccountListMultiple(ret.getJSONObject("result").getLong("push_id"), accountList1));

			List<String> accountList2 = new ArrayList<String>();
			accountList2.add("joelliu3");
			accountList2.add("joelliu4");
			// ...
			result.append("all",
					xinge.pushAccountListMultiple(ret.getJSONObject("result").getLong("push_id"), accountList2));
			return result;
		}
	}

	/** 
	 * 大批量下发给设备
	 * iOS 请构造MessageIOS消息
	 * @return
	 */
	public static JSONObject demoPushDeviceListMultiple() {
		Message message = new Message();
		message.setExpireTime(86400);
		message.setTitle("title");
		message.setContent("content");
		message.setType(Message.TYPE_NOTIFICATION);

		JSONObject ret = xinge.createMultipush(message);
		if (ret.getInt("ret_code") != 0)
			return ret;
		else {
			JSONObject result = new JSONObject();

			List<String> deviceList1 = new ArrayList<String>();
			deviceList1.add("joelliu1");
			deviceList1.add("joelliu2");
			// ...
			result.append("all",
					xinge.pushDeviceListMultiple(ret.getJSONObject("result").getLong("push_id"), deviceList1));

			List<String> deviceList2 = new ArrayList<String>();
			deviceList2.add("joelliu3");
			deviceList2.add("joelliu4");
			// ...
			result.append("all",
					xinge.pushDeviceListMultiple(ret.getJSONObject("result").getLong("push_id"), deviceList2));
			return result;
		}
	}

	/**
	 * 查询消息推送状态
	 * @return
	 */
	public static JSONObject demoQueryPushStatus() {
		List<String> pushIdList = new ArrayList<String>();
		pushIdList.add("390");
		pushIdList.add("389");
		JSONObject ret = xinge.queryPushStatus(pushIdList);
		return ret;
	}

	/**
	 * 查询设备数量
	 * @return
	 */
	public  static JSONObject demoQueryDeviceCount() {
		JSONObject ret = xinge.queryDeviceCount();
		return ret;
	}

	/**
	 * 查询标签
	 * @return
	 */
	public static JSONObject demoQueryTags() {
		JSONObject ret = xinge.queryTags();
		return ret;
	}

	/**
	 * 查询某个tag下token的数量
	 * @return
	 */
	public static JSONObject demoQueryTagTokenNum() {
		JSONObject ret = xinge.queryTagTokenNum("tag");
		return ret;
	}

	/**
	 * 查询某个token的标签
	 * @return
	 */
	public static JSONObject demoQueryTokenTags() {
		JSONObject ret = xinge.queryTokenTags("token");
		return ret;
	}

	/**
	 * 取消定时任务
	 * @return
	 */
	public static JSONObject demoCancelTimingPush() {
		JSONObject ret = xinge.cancelTimingPush("32");
		return ret;
	}

	/**
	 * 设置标签
	 * @return
	 */
	public static JSONObject demoBatchSetTag() {
		List<TagTokenPair> pairs = new ArrayList<TagTokenPair>();

		// 切记把这里的示例tag和示例token修改为你的真实tag和真实token
		pairs.add(new TagTokenPair("tag1", "token00000000000000000000000000000000001"));
		pairs.add(new TagTokenPair("tag2", "token00000000000000000000000000000000001"));

		JSONObject ret = xinge.BatchSetTag(pairs);
		return ret;
	}

	/**
	 * 删除标签
	 * @return
	 */
	public static JSONObject demoBatchDelTag() {
		List<TagTokenPair> pairs = new ArrayList<TagTokenPair>();

		// 切记把这里的示例tag和示例token修改为你的真实tag和真实token
		pairs.add(new TagTokenPair("tag1", "token00000000000000000000000000000000001"));
		pairs.add(new TagTokenPair("tag2", "token00000000000000000000000000000000001"));

		JSONObject ret = xinge.BatchDelTag(pairs);

		return ret;
	}

	/**
	 * 查询某个token的信息
	 * @return
	 */
	public static JSONObject demoQueryInfoOfToken() {
		JSONObject ret = xinge.queryInfoOfToken("token");
		return ret;
	}

	/**
	 * 查询某个account绑定的token
	 * @return
	 */
	public JSONObject demoQueryTokensOfAccount() {
		JSONObject ret = xinge.queryTokensOfAccount("nickName");
		return ret;
	}

	/**
	 * 删除某个account绑定的token
	 * @return
	 */
	public static JSONObject demoDeleteTokenOfAccount() {
		JSONObject ret = xinge.deleteTokenOfAccount("nickName", "token");
		return ret;
	}

	/**
	 * 删除某个account绑定的所有token
	 * @return
	 */
	public static JSONObject demoDeleteAllTokensOfAccount() {
		JSONObject ret = xinge.deleteAllTokensOfAccount("nickName");
		return ret;
	}

}
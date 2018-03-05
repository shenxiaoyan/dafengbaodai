package com.liyang.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.liyang.domain.user.User;

/**
 * web端发送通知，webSocket
 * @author Adam
 *
 */
@ServerEndpoint("/websocket/{userId}")
@Component
public class MyWebSocket {
	private static int onlineCount = 0;
	//与客户端的连接会话，通过此向客户端发送数据
	private Session session;
	private Integer userId;
	private static Hashtable<Integer, MyWebSocket> webSockets = new Hashtable<>();
//	private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<>();
	
	/**
	 *连接建立成功调用 
	 * @param session
	 */
	@OnOpen
	public void onOpen(@PathParam("userId")Integer userId, Session session){
		addOnlineCount();
		this.session = session;
		this.userId = userId;
		webSockets.put(userId, this);
		System.out.println("新连接加入，userId："+userId);
	}
	
	/**
	 * 连接关闭调用
	 */
	@OnClose
	public void onClose(){
		subOnlineCount();
		webSockets.remove(userId);
		System.out.println("连接关闭，userId:"+userId);
	}
	
	/**
	 * 收到客户端消息后调用，向所有连接用户发送消息
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(String message, Session session){
		System.out.println("来自浏览器消息："+message);
		for (Entry<Integer, MyWebSocket> entry : webSockets.entrySet()) {
			try {
				Map<String, Object> messageMap = new HashMap<>();
				messageMap.put("title", "测试");
				messageMap.put("content", message);
				messageMap.put("url", "跳转链接");
				messageMap.put("refreshFlag", 0);
				String messageTxt = JSON.toJSONString(messageMap);
				entry.getValue().sendMessage(messageTxt);
			} catch (IOException e) {
				System.out.println("----------消息发送异常----------");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 异常时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("------------------websocket异常--------------------");
		error.printStackTrace();
	}
	
	/**
	 * 自定义消息发送，给本连接发送消息
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
//		同步发送
		this.session.getBasicRemote().sendText(message);
//		异步发送
//		this.session.getAsyncRemote().sendText(message);
	}
	
	/**
	 * 
	 * @param message
	 * @param userList
	 */
	public static void sendMessage(SocketMessage message, List<User> userList) {
		for (Entry<Integer, MyWebSocket> entry : webSockets.entrySet()) {
			try {
				for (User user : userList) {
					if (user.getId() == entry.getKey()) {
						entry.getValue().sendMessage(JSON.toJSONString(message));
					}
				}
			} catch (IOException e) {
				System.out.println("----------web消息通知发送异常----------");
				e.printStackTrace();
			}
		}
	}
	
	public static void sendMessage(SocketMessage message, User user) {
		for (Entry<Integer, MyWebSocket>  entry : webSockets.entrySet()) {
			if (user.getId() == entry.getKey()) {
				try {
					entry.getValue().sendMessage(JSON.toJSONString(message));
				} catch (IOException e) {
					System.out.println("----------web消息通知发送异常----------");
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	
	
	public static synchronized int getOnlineCount(){
		return onlineCount;
	}
	
	public static synchronized void addOnlineCount(){
		onlineCount++;
	}
	
	public static synchronized void  subOnlineCount(){
		onlineCount--;
	}
	
	public static Map<Integer, MyWebSocket> getWebSockets(){
		return webSockets;
	}
	
}









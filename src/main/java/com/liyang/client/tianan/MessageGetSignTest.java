package com.liyang.client.tianan;

import com.liyang.client.IMessage;

/**
 * @author Administrator
 *
 */
public class MessageGetSignTest extends MessageGetSign implements IMessage {
	
	public static String DEFAULT_TOKEN_FOR_SIGN_TEST = "agkrcl7ofdib6tlttn79";
//	public static String DEFAULT_SIGN_FOR_TEST = "DGxnjs7TToYS2v8tfASHzCzBAqcjeW+tzyK2oECl98vBe/WUsYpTfSJ68W9QdkTjWBpKuh0wrdV1fXzAWm8UhrcZnA3LS6XtVnVZKWNeGGTKerzekHBz1jiOGBypAuATELn1nLKy7T4=";
	
	public MessageGetSignTest(String url) {
		super("yxdTest", url, DEFAULT_TOKEN_FOR_SIGN_TEST);
	}

}

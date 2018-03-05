package com.liyang.util;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.Arrays;
import org.json.JSONException;
import org.json.JSONObject;

import com.liyang.config.TIMKey;
import com.tls.base64_url.base64_url;

/**
 * @author Administrator
 *
 */
public class TIMSignature {

	public static GenTLSSignatureResult generate(String username) {
		GenTLSSignatureResult result;
		try {
			result = genTLSSignatureEx(TIMKey.sdkAppId, username, TIMKey.privStr);
			if (0 == result.urlSig.length()) {
				throw new FailReturnObject(900, "GenTLSSignatureEx failed: " + result.errMessage);
			}
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static CheckTLSSignatureResult check(String sig, String username) throws JSONException {
		CheckTLSSignatureResult checkResult;
		try {
			checkResult = checkTLSSignatureEx(sig, TIMKey.sdkAppId, username, TIMKey.pubStr);
			if (checkResult.verifyResult == false) {
				throw new FailReturnObject(910, "CheckTLSSignature failed: " + checkResult.errMessage);
			}
			return checkResult;
		} catch (DataFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public static class GenTLSSignatureResult {
		public String errMessage;
		public String urlSig;
		public int expireTime;
		public int initTime;

		public GenTLSSignatureResult() {
			errMessage = "";
			urlSig = "";
		}

		@Override
		public String toString() {
			return "GenTLSSignatureResult [errMessage=" + errMessage + ", urlSig=" + urlSig + ", expireTime="
					+ expireTime + ", initTime=" + initTime + "]";
		}

	}

	public static class CheckTLSSignatureResult {
		public String errMessage;
		public boolean verifyResult;
		public int expireTime;
		public int initTime;

		public CheckTLSSignatureResult() {
			errMessage = "";
			verifyResult = false;
		}

		@Override
		public String toString() {
			return "CheckTLSSignatureResult [errMessage=" + errMessage + ", verifyResult=" + verifyResult
					+ ", expireTime=" + expireTime + ", initTime=" + initTime + "]";
		}
	}

	/**
	 * @brief 生成 tls 票据，精简参数列表，有效期默认为 180 天
	 * @param skdAppid
	 *            应用的 sdkappid
	 * @param identifier
	 *            用户 id
	 * @param privStr
	 *            私钥文件内容
	 * @return
	 * @throws IOException
	 */
	public static GenTLSSignatureResult genTLSSignatureEx(long skdAppid, String identifier, String privStr)
			throws IOException {
		return genTLSSignatureEx(skdAppid, identifier, privStr, 3600 * 24 * 180);
	}

	/**
	 * @brief 生成 tls 票据，精简参数列表
	 * @param skdAppid
	 *            应用的 sdkappid
	 * @param identifier
	 *            用户 id
	 * @param privStr
	 *            私钥文件内容
	 * @param expire
	 *            有效期，以秒为单位，推荐时长一个月
	 * @return
	 * @throws IOException
	 */
	public static GenTLSSignatureResult genTLSSignatureEx(long skdAppid, String identifier, String privStr, long expire)
			throws IOException {

		GenTLSSignatureResult result = new GenTLSSignatureResult();

		Security.addProvider(new BouncyCastleProvider());
		Reader reader = new CharArrayReader(privStr.toCharArray());
		JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
		PEMParser parser = new PEMParser(reader);
		Object obj = parser.readObject();
		parser.close();
		PrivateKey privKeyStruct = converter.getPrivateKey((PrivateKeyInfo) obj);

		String jsonString = "{" + "\"TLS.account_type\":\"" + 0 + "\"," + "\"TLS.identifier\":\"" + identifier + "\","
				+ "\"TLS.appid_at_3rd\":\"" + 0 + "\"," + "\"TLS.sdk_appid\":\"" + skdAppid + "\","
				+ "\"TLS.expire_after\":\"" + expire + "\"," + "\"TLS.version\": \"201512300000\"" + "}";

		String time = String.valueOf(System.currentTimeMillis() / 1000);
		String serialString = "TLS.appid_at_3rd:" + 0 + "\n" + "TLS.account_type:" + 0 + "\n" + "TLS.identifier:"
				+ identifier + "\n" + "TLS.sdk_appid:" + skdAppid + "\n" + "TLS.time:" + time + "\n"
				+ "TLS.expire_after:" + expire + "\n";

		try {
			// Create Signature by SerialString
			Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
			signature.initSign(privKeyStruct);
			signature.update(serialString.getBytes(Charset.forName("UTF-8")));
			byte[] signatureBytes = signature.sign();

			String sigTLS = Base64.encodeBase64String(signatureBytes);

			// Add TlsSig to jsonString
			JSONObject jsonObject = new JSONObject(jsonString);
			jsonObject.put("TLS.sig", (Object) sigTLS);
			jsonObject.put("TLS.time", (Object) time);
			jsonString = jsonObject.toString();

			// compression
			Deflater compresser = new Deflater();
			compresser.setInput(jsonString.getBytes(Charset.forName("UTF-8")));

			compresser.finish();
			byte[] compressBytes = new byte[512];
			int compressBytesLength = compresser.deflate(compressBytes);
			compresser.end();
			String userSig = new String(
					base64_url.base64EncodeUrl(Arrays.copyOfRange(compressBytes, 0, compressBytesLength)));

			result.urlSig = userSig;
		} catch (Exception e) {
			e.printStackTrace();
			result.errMessage = "generate usersig failed";
		}

		return result;
	}

	public static CheckTLSSignatureResult checkTLSSignatureEx(String urlSig, long sdkAppid, String identifier,
			String publicKey) throws DataFormatException, JSONException {

		CheckTLSSignatureResult result = new CheckTLSSignatureResult();
		Security.addProvider(new BouncyCastleProvider());

		// DeBaseUrl64 urlSig to json
		Base64 decoder = new Base64();

		byte[] compressBytes = base64_url.base64DecodeUrl(urlSig.getBytes(Charset.forName("UTF-8")));

		// Decompression
		Inflater decompression = new Inflater();
		decompression.setInput(compressBytes, 0, compressBytes.length);
		byte[] decompressBytes = new byte[1024];
		int decompressLength = decompression.inflate(decompressBytes);
		decompression.end();

		String jsonString = new String(Arrays.copyOfRange(decompressBytes, 0, decompressLength));

		// Get TLS.Sig from json
		JSONObject jsonObject = new JSONObject(jsonString);
		String sigTLS = jsonObject.getString("TLS.sig");

		// debase64 TLS.Sig to get serailString
		byte[] signatureBytes = decoder.decode(sigTLS.getBytes(Charset.forName("UTF-8")));

		try {
			String strSdkAppid = jsonObject.getString("TLS.sdk_appid");
			String sigTime = jsonObject.getString("TLS.time");
			String sigExpire = jsonObject.getString("TLS.expire_after");

			if (Integer.parseInt(strSdkAppid) != sdkAppid) {
				result.errMessage = new String(
						"sdkappid " + strSdkAppid + " in tls sig not equal sdkappid " + sdkAppid + " in request");
				return result;
			}

			if (System.currentTimeMillis() / 1000 - Long.parseLong(sigTime) > Long.parseLong(sigExpire)) {
				result.errMessage = new String("TLS sig is out of date");
				return result;
			}

			// Get Serial String from json
			String serialString = "TLS.appid_at_3rd:" + 0 + "\n" + "TLS.account_type:" + 0 + "\n" + "TLS.identifier:"
					+ identifier + "\n" + "TLS.sdk_appid:" + sdkAppid + "\n" + "TLS.time:" + sigTime + "\n"
					+ "TLS.expire_after:" + sigExpire + "\n";

			Reader reader = new CharArrayReader(publicKey.toCharArray());
			PEMParser parser = new PEMParser(reader);
			JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
			Object obj = parser.readObject();
			parser.close();
			PublicKey pubKeyStruct = converter.getPublicKey((SubjectPublicKeyInfo) obj);

			Signature signature = Signature.getInstance("SHA256withECDSA", "BC");
			signature.initVerify(pubKeyStruct);
			signature.update(serialString.getBytes(Charset.forName("UTF-8")));
			boolean bool = signature.verify(signatureBytes);
			result.expireTime = Integer.parseInt(sigExpire);
			result.initTime = Integer.parseInt(sigTime);
			result.verifyResult = bool;
		} catch (Exception e) {
			e.printStackTrace();
			result.errMessage = "Failed in checking sig";
		}

		return result;
	}

}

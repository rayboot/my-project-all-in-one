package com.openflow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

public class OpenFlow {

	private String URL = "http://202.102.221.53:8021/bap/service";
	private String number = "";
	private String SRCORGID = "8";// 8
	private String SRCSYSID = "1008";// 1008
	private String PWD = "147258";//
	private String acctId = "";
	private String offeringProductIdx = "";
	private String productId = "";
	private String areaCode = "";
	private String groupId = "";
	private String offeringProductId = "";

	public String getJsonData(String url, String data) {

		Integer statusCode = -1;
		StringBuffer result = new StringBuffer();
		try {

			DefaultHttpClient client = new DefaultHttpClient();

			HttpPost post = new HttpPost(url);

			StringEntity entity = new StringEntity(data, "UTF-8");
			entity.setContentType("application/json;charset=UTF-8");
			post.setEntity(entity);
			post.setHeader("accept", "application/json");
			post.setHeader("Content-Type", "application/json;charset=UTF-8");
			HttpResponse response = client.execute(post);
			statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entityRsp = response.getEntity();
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					entityRsp.getContent(), HTTP.UTF_8));
			String tempLine = rd.readLine();
			while (tempLine != null) {
				result.append(tempLine);
				tempLine = rd.readLine();
			}
			if (entityRsp != null) {
				entityRsp.consumeContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public String getProduct() {
		String result = "";
		String jsonData = "{\"SvcCont\": [{\"PARAMS\":{\"QueryCustOfr\":{\"loginType\":\"4\",\"latnId\":\""
				+ areaCode
				+ "\",\"serviceNbr\":\""
				+ number
				+ "\",\"type\":\"2\"}}";
		jsonData += ",\"PUB_REQ\": {\"TYPE\": \"ADD_TEST_BEAN\"}}],";
		jsonData += last("FUNC01008");
		jsonData += "}";
		//System.out.println("获取生产关系请求报文：" + jsonData);
		result = getJsonData(URL, jsonData);
		//System.out.println("获取生产关系回复报文:" + result);
		return result;
	}

	public String login() {
		String result = "";
		String jsonData = "{\"SvcCont\": [{\"PARAMS\":{\"Login\":{\"loginType\":\"4\",\"latnId\":\""
				+ areaCode
				+ "\",\"serviceNbr\":\""
				+ number
				+ "\",\"passWord\":\"\"}}";
		jsonData += ",\"PUB_REQ\": {\"TYPE\": \"ADD_TEST_BEAN\"}}],";
		jsonData += last("FUNC01001");
		jsonData += "}";
		//System.out.println("获取用户信息请求报文" + jsonData);
		result = getJsonData(URL, jsonData);
		//System.out.println("获取用户信息回复报文:" + result);
		return result;
	}

	public String openFlow() {
		String resultStr = "";
		StringBuffer sb = new StringBuffer();
		sb.append("{\"SvcCont\": [{ \"PARAMS\": {\"OfferAccept\": {\"attrOfferingSets\":");
		try {
			String cust = login();
			JSONObject jsonObject2 = new JSONObject(cust);
			JSONArray jsonArray2 = jsonObject2.getJSONArray("SvcCont");
			JSONObject svcCont2 = new JSONObject(jsonArray2.getString(0));
			JSONObject resultJson2 = new JSONObject(
					svcCont2.getString("result"));
			String resultCode2 = resultJson2.getString("resultCode");
			if (resultCode2 == null || !resultCode2.equals("0")) {
				System.out.println("开通流量包失败:" + number + "用户信息:" + cust);
			} else {
				String product = getProduct();
				JSONObject jsonObject = new JSONObject(product);
				JSONArray jsonArray = jsonObject.getJSONArray("SvcCont");
				JSONObject svcCont = new JSONObject(jsonArray.getString(0));
				JSONObject resultJson = new JSONObject(
						svcCont.getString("result"));
				String resultCode = resultJson.getString("resultCode");
				if (resultCode == null || !resultCode.equals("0")) {
					System.out.println("开通流量包失败:" + number + "生产关系信息:"
							+ product);
				}
				JSONObject resultData = new JSONObject(
						resultJson.getString("resultData"));
				JSONObject queryCustOfr = new JSONObject(
						resultData.getString("queryCustOfr"));
				JSONArray orderedOfferingSetsArray = queryCustOfr
						.getJSONArray("orderedOfferingSets");
				sb.append(
						"[{\"attrOfferingSet\": {\"attrOfferingCode\": \"GROUP_ID\",\"attrOfferingName\": \"\",\"attrOfferingValue\": \""
								+ groupId + "\"}},")
						.append("{\"attrOfferingSet\": {\"attrOfferingCode\": \"DISCT_RULE_ID\",\"attrOfferingName\": \"\",\"attrOfferingValue\": \"\"}}],");
				int i = orderedOfferingSetsArray.length();
				JSONObject orderedOfferingSet01 = new JSONObject(
						orderedOfferingSetsArray.getString(i - 1));
				JSONObject orderedOfferingSet02 = new JSONObject(
						orderedOfferingSet01.getString("orderedOfferingSet"));
				JSONArray prodSets = orderedOfferingSet02
						.getJSONArray("prodSets");
				JSONObject prodSet1 = new JSONObject(prodSets.getString(0));
				JSONObject prodSet2 = new JSONObject(
						prodSet1.getString("prodSet"));

				acctId = prodSet2.getString("acctId");
				offeringProductIdx = prodSet2.getString("offeringProductId");
				productId = prodSet2.getString("productId");// 1054389858
				String offeringId = orderedOfferingSet02
						.getString("offeringId");
				sb.append("\"custInfo\":{");

				JSONObject resultData2 = new JSONObject(
						resultJson2.getString("resultData"));
				JSONObject login = new JSONObject(
						resultData2.getString("login"));
				sb.append("\"acctId\":\"" + acctId + "\",");
				if (resultData2.getString("login").contains("certCode"))
					sb.append("\"certCode\":\"" + login.getString("certCode")
							+ "\",");
				else
					sb.append("\"certCode\":\"\",");
				if (resultData2.getString("login").contains("certName"))
					sb.append("\"certName\":\"" + login.getString("certName")
							+ "\",");
				else
					sb.append("\"certName\":\"\",");
				if (resultData2.getString("login").contains("certType"))
					sb.append("\"certType\":\"" + login.getString("certType")
							+ "\",");
				else
					sb.append("\"certType\":\"\",");
				if (resultData2.getString("login").contains("contactNumber"))
					sb.append("\"contactNumber\":\""
							+ login.getString("contactNumber") + "\",");
				else
					sb.append("\"contactNumber\":\"\",");
				if (resultData2.getString("login").contains("cusName"))
					sb.append("\"cusName\":\"" + login.getString("cusName")
							+ "\",");
				else
					sb.append("\"cusName\":\"\",");
				if (resultData2.getString("login").contains("custAddress"))
					sb.append("\"custAddress\":\""
							+ login.getString("custAddress") + "\",");
				else
					sb.append("\"custAddress\":\"\",");
				if (resultData2.getString("login").contains("custId"))
					sb.append("\"custId\":\"" + login.getString("custId")
							+ "\",");
				else
					sb.append("\"custId\":\"\",");
				sb.append("\"contactName\":\"\",");
				sb.append("\"email\":\"\",");
				sb.append("\"latnId\":\"" + login.getString("latnId") + "\",");
				sb.append("\"serivceNbr\":\"" + number + "\",");
				sb.append("\"serviceType\":\"4\"");

				sb.append("},");
				sb.append("\"effectDate\":\"0\",");
				sb.append("\"inactiveDate\":\"2\",");
				sb.append("\"isRealOrder\":\"0\",");
				sb.append("\"latnId\":\"" + areaCode + "\",");//
				sb.append("\"offeringProductId\":\"" + offeringProductId
						+ "\",");
				sb.append(
						"\"productSets\":[{\"productSet\":{\"offeringProductId\":\""
								+ offeringProductIdx + "\",")
						.append("\"productId\":\"" + productId
								+ "\",\"serviceNbr\":\"" + number + "\"}}]}},")
						.append("\"PUB_REQ\": {\"TYPE\": \"ADD_TEST_BEAN\"}}],");
				sb.append(last("SUBS01002")).append("}");
				//System.out.println("开通请求报文:"+sb.toString());
				resultStr = getJsonData(URL, sb.toString());
				//System.out.println("结果是:" + resultStr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultStr;
	}

	public String last(String code) {
		String result = "";
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern("yyyyMMddHHMMss");
		String str = SRCSYSID + "&" + code + "&" + PWD + "&"
				+ format.format(date).toString();
		String sign = getSign(str);
		result = "\"TcpCont\": {\"LatnCd\": \"" + areaCode
				+ "\",\"ServiceCode\": \"" + code + "\",\"SrcOrgID\": \""
				+ SRCORGID + "\",\"SrcSysID\": \"" + SRCSYSID
				+ "\",\"SrcSysSign\": \"" + sign + "\","
				+ "\"TransactionID\": \"" + format.format(date).toString()
				+ "\"}";
		return result;
	}

	public String getSign(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}
	
	public void openflowBy300(String num,String area){
		this.number=num;
		this.areaCode=area;
		this.groupId = "1813220";
		this.offeringProductId = "797080084";
		String lastString = this.openFlow();
		try{
			JSONObject jsonObject = new JSONObject(lastString);
			JSONArray jsonArray = jsonObject.getJSONArray("SvcCont");
			JSONObject svcCont = new JSONObject(jsonArray.getString(0));
			JSONObject resultJson = new JSONObject(
					svcCont.getString("result"));
			String resultCode = resultJson.getString("resultCode");
			if(resultCode == null || !resultCode.equals("0")){
				System.out.println("开通300流量包失败:"+number);
			}
			if(resultCode != null && resultCode.equals("0")){
				System.out.println(number);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void openflowBy150(String num,String area){
		this.number=num;
		this.areaCode=area;
		this.groupId = "1895429";
		this.offeringProductId = "797844086";
		String lastString = this.openFlow();
		try{
			JSONObject jsonObject = new JSONObject(lastString);
			JSONArray jsonArray = jsonObject.getJSONArray("SvcCont");
			JSONObject svcCont = new JSONObject(jsonArray.getString(0));
			JSONObject resultJson = new JSONObject(
					svcCont.getString("result"));
			String resultCode = resultJson.getString("resultCode");
			if(resultCode == null || !resultCode.equals("0")){
				System.out.println("开通150流量包失败:"+number);
			}
			if(resultCode != null && resultCode.equals("0")){
				System.out.println(number);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void openflowBy60(String num,String area){
		this.number=num;
		this.areaCode=area;
		this.groupId = "1895426";
		this.offeringProductId = "797844085";
		String lastString = this.openFlow();
		try{
			JSONObject jsonObject = new JSONObject(lastString);
			JSONArray jsonArray = jsonObject.getJSONArray("SvcCont");
			JSONObject svcCont = new JSONObject(jsonArray.getString(0));
			JSONObject resultJson = new JSONObject(
					svcCont.getString("result"));
			String resultCode = resultJson.getString("resultCode");
			if(resultCode == null || !resultCode.equals("0")){
				System.out.println("开通60流量包失败:"+number);
			}
			if(resultCode != null && resultCode.equals("0")){
				System.out.println(number);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OpenFlow open = new OpenFlow();
		//18019904741
		//18956030485
		//18949842723
		//18056004896
		//18955182906
		//18955195519
		//安庆 556
		//宣称 563

		open.openflowBy300("18955182906","551");
		open.openflowBy150("18955182906","551");
		open.openflowBy60("18955182906","551");

	}

}

package com.fang.operateexcel.utils;

import android.os.Environment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fang.operateexcel.bean.XmlItem;

import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class XmlUtil {
//	public static void main(String[] args) throws Exception {
//		//请注意使用正常的xml
//		//xml->json
////            String jsonStr = xmlToJson("D:\\NewFile.xml", null);
////            System.out.println(jsonStr);
//
//		//json->xml
//		String xmlstr = jsonToXml("{\"biz_content\":{\"signdata\":\"rHjD6Rc3Fgq8mPS6B48qPs/DPJnZZPN6QQJQodY3+hK6PWCF3/2oi3DJPnFEXgKDrXX5rHT7q/I0nQPAruuBbQRfErnenQNvPpbf/lXl690qtye0/ZEuDs0ByFdFAGffQalB+Ij3lLUMDPz2GfUDlolPjY2/YkBXGGPJoVPtCio=\",\"userobj\":{\"uid\":\"mayintao000\",\"realtype\":\"DC\",\"cn\":\"法人用户2\",\"tokenid\":\"\",\"usertype\":\"2\",\"link_person_name\":\"联系人2\",\"isreal\":\"true\",\"telephonenumber\":\"13*******21\",\"mail\":\"ceshi@123.com\",\"idcardtype\":\"10\",\"createtime\":\"20150618191221\",\"extproperties\":[\"address=广东省广州市天河区天河北路371号802室\",\"legal_code=440***********033\",\"ent_type=-1\",\"link_person_code=350************14\",\"origin=gdbs\",\"card_type_two_num=-1\",\"cert_ca=-1\",\"accout_type=2\",\"account_uid=2\",\"comm_code=-1\",\"unit_type=-1\",\"legal_id_type=10\",\"landline=-1\",\"tax_code=-1\",\"cert_notafter=-1\",\"card_type_one_num=-1\",\"local_user=-1\",\"legal_person=郑奋明\",\"link_person_type=10\",\"card_type_three=-1\",\"card_type_two=-1\",\"card_type_three_num=-1\",\"cert_data=-1\",\"area=guangzhou\",\"uversion=3.0\",\"cert_notbefore=-1\",\"card_type_one=-1\",\"user_typeext=2\"],\"idcardnumber\":\"11***************23\",\"useridcode\":\"38c97fa1ee2e43d4a664cffc4554cde4\",\"creditable_level_of_account_way\":\"L2@YSS@2088******653||L0@IDV@44088******75||L3@GW@44088******75\",\"creditable_level_of_account\":\"L3\"},\"pareobj\":{\"uid\":\"mayintao\",\"realtype\":\"DC\",\"cn\":\"单位用户2\",\"tokenid\":\"\",\"usertype\":\"2\",\"link_person_name\":\"联系人2\",\"isreal\":\"true\",\"telephonenumber\":\"13*******21\",\"mail\":\"ceshi@123.com\",\"idcardtype\":\"50\",\"createtime\":\"20150618191221\",\"extproperties\":[\"address=广东省广州市东山区\",\"legal_id_type=-1\",\"link_person_type=-1\",\"legal_code=-1\",\"origin=gdbs\",\"tax_code=-1\",\"legal_person=-1\",\"area=shenzhen\",\"link_person_code=-1\",\"user_typeext=2\",\"uversion=1.0\"],\"idcardnumber\":\"456787654\",\"useridcode\":\"75c91fagrr2e67d4a169cfmc8735ctrf\",\"creditable_level_of_account_way\":\"L2@YSS@2088******653||L0@IDV@44088******75||L3@GW@44088******75\",\"creditable_level_of_account\":\"L3\"},\"user_creditable_level\":{\"creditable_level_of_account_way\":\"L2@YSS@2088******653||L0@IDV@44088******75||L3@GW@44088******75\",\"creditable_level_of_account_way_list\":[{\"auth_time\":\"2018-02-28 16:45:26\",\"uniqueid\":\"106a50e64764486f93fb612122a1a7ae\",\"user_name\":\"郭**\",\"auth_identification\":\"2088******653\",\"identity_level\":\"L2\",\"credential_no\":\"44088******75\",\"way_code\":\"YSS\"},{\"auth_time\":null,\"uniqueid\":\"106a50e64764486f93fb612122a1a7ae\",\"user_name\":\"郭**\",\"auth_identification\":\"44088******75\",\"identity_level\":\"L0\",\"credential_no\":\"44088******75\",\"way_code\":\"IDV\"},{\"auth_time\":\"2018-02-13 17:12:31\",\"uniqueid\":\"106a50e64764486f93fb612122a1a7ae\",\"user_name\":\"郭**\",\"auth_identification\":\"44088******75\",\"identity_level\":\"L3\",\"credential_no\":\"44088******75\",\"way_code\":\"GW\"}],\"creditable_level_of_account\":\"L3\"}},\"time_stamp\":\"20200821\",\"version \":\"v1\",\"sign\":\"MEYCIQC3xjKlksBVwrxf0MFT7eQqYgYKWtgzNBi6mhS2tbqkPgIhAOfQnz4lG5JWMNhGw5v7CicfBAexD85PbYoevDyRA8hv \"}");
//		System.out.println(xmlstr);
//		createXMLFile(formatXML(xmlstr), "测试");
//	}

	/**
	 * json转xml<br>
	 * 方 法 名：jsonToXml <br>
	 * @param
	 * @return String
	 */
	public static String jsonToXml(List<XmlItem> xmlItemList){
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<resources>");
			jsonToXmlstr2(xmlItemList,buffer);
			buffer.append("</resources>");
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * json转str<br>
	 * 方 法 名：jsonToXmlstr <br>
	 * @param jObj
	 * @param buffer
	 * @return String
	 */
	public static String jsonToXmlstr(JSONObject jObj, StringBuffer buffer ){
		Set<Entry<String, Object>>  se = jObj.entrySet();
		for( Iterator<Entry<String, Object>>   it = se.iterator();  it.hasNext(); )
		{
			Entry<String, Object> en = it.next();
			if(en.getValue() != null && en.getValue().getClass().getName().equals("com.alibaba.fastjson.JSONObject")){
				buffer.append("<"+en.getKey()+">");
				JSONObject jo = jObj.getJSONObject(en.getKey());
				jsonToXmlstr(jo,buffer);
				buffer.append("</"+en.getKey()+">");
			}else if(en.getValue() != null && en.getValue().getClass().getName().equals("com.alibaba.fastjson.JSONArray")){
				if (en.getKey().equals("extproperties")) {
					JSONArray ja = jObj.getJSONArray(en.getKey());
					Iterator<Object> it1 = ja.iterator();
					List<String> list=new ArrayList<String>();
					while (it1.hasNext()) {
						String ob = (String) it1.next();
						System.out.println(ob);
					}
				}else {
					JSONArray jarray = jObj.getJSONArray(en.getKey());
					for (int i = 0; i < jarray.size(); i++) {
						buffer.append("<"+en.getKey()+">");
						JSONObject jsonobject =  jarray.getJSONObject(i);
						jsonToXmlstr(jsonobject,buffer);
						buffer.append("</"+en.getKey()+">");
					}
				}

			}else if(en.getValue() != null && en.getValue().getClass().getName().equals("java.lang.String")){
				buffer.append("<"+en.getKey()+">"+en.getValue());
				buffer.append("</"+en.getKey()+">");
			}else{
				buffer.append("<"+en.getKey()+">"+"");
				buffer.append("</"+en.getKey()+">");
			}

		}
		return buffer.toString();
	}

	public static String jsonToXmlstr2(List<XmlItem> bean, StringBuffer buffer ){
		for(int i=0;i<bean.size();i++){
			if(bean!= null && !bean.get(i).getName().equals("")){
				buffer.append("<string name=\"" + bean.get(i).getName()+"\">" +
						bean.get(i).getContent() + "</string>");
				buffer.append("\n");
			}
		}
		return buffer.toString();
	}

	/**
	 * 将已经格式化的xml字符串写入xml文件
	 * @param xmlStr
	 * @return
	 */
	public static boolean createXMLFile(String xmlStr,String xmlName){
		File dir = new File(Environment.getExternalStorageDirectory() + "/operateexcel/");
		//文件夹不存在创建
		if (!dir.exists()) {
			//创建文件夹
			dir.mkdirs();
		}

		boolean flag = false;
		try {
			XMLWriter output = null;
			//OutputFormat   format   =   OutputFormat.createPrettyPrint();
			//format.setSuppressDeclaration(true);
			// format.setEncoding("UTF-8");

			//如果上面设置的xml编码类型为GBK，则应当用FileWriter来构建xml文件，否则会出现中文连码问题
                /*outpt = new XMLWriter(
                        new FileWriter(
                                new File("D:/myeclipse/Workspaces/fusionChartsDemoTest/WebRoot/xml/"+xmlName+".xml")) ,
                                    format);
                  */

			//如果上面设置的xml编码类型为utf-8，则应当用FileOutputStream来构建xml文件，否则还是会出现问题
			output = new XMLWriter(
					new FileOutputStream(
							new File(Environment.getExternalStorageDirectory()+"/operateexcel/" +xmlName+".xml")));
			output.setEscapeText(false);
			output.write( xmlStr );
			output.close();
			return flag = true;

		} catch (IOException e) {
			e.printStackTrace();
			return flag;
		}

	}
}

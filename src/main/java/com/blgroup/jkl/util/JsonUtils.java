package com.blgroup.jkl.util;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

/**
 * 
 * @author JZM
 * 
 */
public class JsonUtils {

	public static String jsontoXml(String json) {

		XMLSerializer serializer = new XMLSerializer();
		JSON jsonObject = JSONSerializer.toJSON(json);
		return serializer.write(jsonObject);

	}

	public static String xmltoJson(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		return xmlSerializer.read(xml).toString();
	}



}

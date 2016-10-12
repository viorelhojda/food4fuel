package com.atraxo.f4f.xml;

import java.io.StringReader;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XMLUtil {
	
	private static final Logger LOGGER = Logger.getLogger(XMLUtil.class);
	
	private XMLUtil(){
		//private constructor
	}
	
	public static Document getDocFromXml(String response){
		Document doc = null;
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(new StringReader(response));
		} catch (DocumentException e) {
			LOGGER.error("Could not get document from xml", e);
		}

		return doc;
	}


	
}

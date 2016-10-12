package com.atraxo.f4f.xml;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLParser {
	private static final Logger LOGGER = Logger.getLogger(XMLParser.class);
	
	private static final String PARENT_FOLDER_PATH = "/xml/";
	private static final String XML_EMAIL_CONTENT = "emailContent.xml";
	
	
	private Document getDoc(String fileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		InputStream is = getClass().getResourceAsStream(fileName);
		return reader.read(is);
	}
	
	public EmailXML parseEmailXml(String xmlTag) {
		EmailXML email = new EmailXML();
		
		Document doc = null;
		try {
			doc = getDoc(PARENT_FOLDER_PATH + XML_EMAIL_CONTENT);
		} catch (DocumentException e) {
			LOGGER.error("Could not parse email xml", e);
		}
		
		if( doc != null ){
			Element rootEl = doc.getRootElement();
			Element emailEl = rootEl.element(xmlTag);
			if ( emailEl != null ) {
				String fromText = emailEl.elementText("from");
				String toText = emailEl.elementText("to");
				String ccText = emailEl.elementText("cc");
				String bccText = emailEl.elementText("bcc");
				String subjectText = emailEl.elementText("subject");
				String contentText = emailEl.elementText("content");

				email.setSender(fromText);
				email.setBcc(bccText);
				email.setCc(ccText);
				email.setReceiver(toText);
				email.setSubject(subjectText);
				email.setContent(contentText);
			}
		}
		return email;
	}
	
	
}

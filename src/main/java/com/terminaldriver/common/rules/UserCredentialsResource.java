package com.terminaldriver.common.rules;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UserCredentialsResource extends ExternalResource {

	private static final Logger logger = LoggerFactory.getLogger(UserCredentialsResource.class);
	
	private String userid;
	
	private String password;
	
	public UserCredentialsResource() {
		this.userid = null;
		this.password = null;
	}
	
	public UserCredentialsResource fromVMArgs() {
		this.userid = System.getProperty("userid");
		this.password = System.getProperty("password");
		return this;
	}
	
	/**
	 * load credentials from file
	 * @param anImportFile
	 * @return
	 */
	public UserCredentialsResource fromXML(String anImportFile) {
		try {
			File fXmlFile = new File(anImportFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			NodeList tList = doc.getElementsByTagName("root");
			Node tScopeNode = tList.item(0);
			Element tElement = (Element) tScopeNode;

			// varUserName und varPassword setzen
			this.userid = tElement.getElementsByTagName("user").item(0).getTextContent();
			this.password = tElement.getElementsByTagName("pass").item(0).getTextContent();
			logger.info("user / password successfully extracted from file.");
		} catch (IOException e) {
			logger.error("Error while reading user credentials!", e);
			return this;
		} catch (SAXException e) {
			logger.error("Error while parsing user credentials!", e);
			return this;
		} catch (ParserConfigurationException e) {
			logger.error("Error while parsing user credentials!", e);
			return this;
		}
		return this;
	}
	
	public String getUser() {
		return this.userid;
	}
	
	public String getPassword() {
		return this.password;
	}
}

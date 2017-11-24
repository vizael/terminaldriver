package com.terminaldriver.common.rules;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

public class SharedDataResource extends ExternalResource {

	private static final Logger logger = LoggerFactory.getLogger(SharedDataResource.class);
	
	File importFile;
	
	Map<String, String> shared;
	
	private void shareddata() {
	}
	
	/**
	 * Use shared data from xml source.
	 * 
	 * @param anImportFile
	 * @return
	 */
	public SharedDataResource fromXML(String anImportFile) {
		importFile = new File(anImportFile);
		
		this.shared = new HashMap<>();
		
		if (!this.importFile.exists()) {
			logger.warn("Variable file does not exists: " + this.importFile.getAbsolutePath());
			return this;
		}

		// load export file
		Document doc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(this.importFile);
			doc.getDocumentElement().normalize();

		} catch (IOException e) {
			logger.error("Can not read variable file: " + this.importFile.getAbsolutePath());
			return this;
		} catch (ParserConfigurationException e) {
			logger.error("Can not parse variable file: " + this.importFile.getAbsolutePath() + " because: " + e.getMessage());
			return this;
		} catch (SAXException e) {
			logger.error("Can not parse variable file: " + this.importFile.getAbsolutePath() + " because: " + e.getMessage());
			return this;
		}
		// nach vereinbarung gibt es nur ein erstes Tag "variables"
		Node nVarNode = doc.getElementsByTagName("variables").item(0);

		NodeList nVarChildren = nVarNode.getChildNodes();

		int countKeys = 0;
		// transfer values to var system
		for (int temp = 0; temp < nVarChildren.getLength(); temp++) {
			Node nNode = nVarChildren.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String tNodeName = eElement.getNodeName();
				String tNodeValue = eElement.getTextContent();
				// Variable uebertragen wenn der Key noch nicht vorhanden
				// ist.
				this.shared.putIfAbsent(tNodeName, tNodeValue);
				countKeys++;
			}
		}
		logger.info("created key/value from shared file: " + countKeys);
		
		return this;
	}
	
	public SharedDataResource fromProperties(String anImportFile) {
		importFile = new File(anImportFile);
		return this;
	}
	
	public Map<String, String> getData() {
		return this.shared;
	}
}

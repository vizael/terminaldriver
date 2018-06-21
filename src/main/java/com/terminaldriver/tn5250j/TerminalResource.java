package com.terminaldriver.tn5250j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create a TerminalResource for JUnit Access.
 *  
 * @author doerges
 *
 */
public class TerminalResource extends ExternalResource {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(TerminalResource.class);
	
	/**
	 * Terminal
	 */
	private TerminalDriver driver;
	
	/**
	 * Host-Terminal
	 */
	String host;
	
	/**
	 * Port-Terminal
	 */
	int port;

	/**
	 * CodePage
	 */
	String codePage = null;

	/**
	 * SSL Type
	 */
	String sslType = null;
	
	/**
	 * Set to true if connection should stay open after testcase.
	 */
	boolean reusableConnection = false;
	
	/**
	 * Standard constructor
	 */
	public TerminalResource() {
		host = "";
		port = 0;
	}
	
	/**
	 * FluentInterface: Define host
	 * 
	 * @param aHost hostname
	 * @return TerminalResource object
	 */
	public TerminalResource withHost(String aHost) {
		this.host = aHost;
		return this;
	}
	
	/**
	 * FluentInterface: Define port
	 * @param aPort a port we want to connect to
	 * @return TerminalResource object
	 */
	public TerminalResource withPort(int aPort) {
		this.port = aPort;
		return this;
	}
	
	/**
	 * FluentInterface: Define CodePage
	 * @param aCodePage the codepage for the connection
	 * @return TerminalResource object
	 */
	public TerminalResource withCodePage(String aCodePage) {
		this.codePage = aCodePage;
		return this;
	}
	
	/**
	 * FluentInterface: Define SSL Type
	 * @param aSSLType the kind of ssl wen need
	 * @return TerminalResource object
	 */
	public TerminalResource withSSLType(String aSSLType) {
		this.sslType = aSSLType;
		return this;
	}
	
	/**
	 * FluentInterface: Hold connection after TestCase. 
	 * @param aDecision flag if the connection should be held after testcase finished
	 * @return TerminalResource object
	 */
	public TerminalResource withReusableConnection(boolean aDecision) {
		this.reusableConnection = aDecision;
		return this;
	}
	
	/**
	 * initialize resource before testcase.
	 */
	@Override
	protected void before() throws Throwable {
		if (reusableConnection) {
			return;
		}
		connect();
	}
	
	public void connect() throws Throwable {
		this.driver =  new TerminalDriver();
		
		Map<String, Object> configs = new HashMap<String, Object>();
		
		// create the config
		if (this.codePage != null) {
			configs.put("codePage", this.codePage);
		}
		
		if (this.sslType != null) {
			configs.put("SSL_TYPE", this.sslType);
		}
		
		// connect to system with custom config
		this.driver.connectTo(host, port, configs);
	}
	
	/**
	 * Clean up resource after testcase.
	 */
	@Override
	protected void after() {
		if (reusableConnection) {
			return;
		}
		disconnect();
	}
	
	public void disconnect() {
		try {
			this.driver.close();
		} catch (IOException e) {
			logger.error("Cannot close terminal driver!", e);
		}
	}
	
	/**
	 * Get driver instance.
	 * 
	 * @return TerminalDriver object
	 */
	public TerminalDriver getDriver() {
		return this.driver;
	}
}

[![Build Status](https://travis-ci.org/vebqa/terminaldriver.svg?branch=master)](https://travis-ci.org/vebqa/terminaldriver)

# terminaldriver
A screen driver for junit automation.
Provides an implementation of TerminalDriver to control a 5250 session.  Provides annotations to facilitate creation of 'Page Objects'.


To use with maven:

      <dependencies>
        <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.12</version>
          <scope>test</scope>
        </dependency>
	      <dependency>
    	    <groupId>com.github.vebqa</groupId>
	        <artifactId>terminaldriver-tn5250j</artifactId>
    	    <version>0.0.2</version>
	      </dependency>
      </dependencies>
      

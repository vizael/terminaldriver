package com.terminaldriver.common.logger;

import java.io.Closeable;
import java.io.IOException;

import com.terminaldriver.common.TerminalDriverChangeListener;
import com.terminaldriver.tn5250j.TerminalDriver;
import com.terminaldriver.tn5250j.annotation.ScreenAttribute;
import com.terminaldriver.tn5250j.obj.ScreenField;

public class BufferingTDChangeListener implements TerminalDriverChangeListener, Closeable {

	StringBuilder keyBuffer= new StringBuilder();
	boolean bufferKeys = true;
	boolean inputInhibitedDelta = true;
	Boolean lastInhibited;
	TerminalDriverChangeListener wrappedListener;

	public BufferingTDChangeListener(TerminalDriverChangeListener wrappedListener) {
		this.wrappedListener=wrappedListener;
	}

	public void fieldSetString(final TerminalDriver driver, final ScreenField screenField, final String value) {
		if(screenField != null && ScreenAttribute.getAttrEnum(screenField.getAttr().charAt(0)).isNonDisplay() ){
			wrappedListener.fieldSetString(driver, screenField, value.replaceAll("[^\\s]", "*"));
		}else{
			wrappedListener.fieldSetString(driver, screenField, value);
		}
				
	}

	public void sendKeys(final TerminalDriver driver, final String keys) {
		if(keys.length()==1){
			ScreenField field = driver.getScreenFieldAt(driver.getSession().getScreen().getCurrentPos());
			if(field != null && ScreenAttribute.getAttrEnum(field.getAttr().charAt(0)).isNonDisplay() ){
				keyBuffer.append("*");
			}else{
				keyBuffer.append(keys);
			}
		}else{
			keyBuffer.append(keys);
		}
		if(!bufferKeys || (keys.startsWith("[") && keys.endsWith("]"))){
			wrappedListener.sendKeys(driver, keyBuffer.toString());
			keyBuffer.setLength(0);
		}
	}

	public void screenSizeChanged(final TerminalDriver driver, final int cols, final int rows) {
		wrappedListener.screenSizeChanged(driver, cols, rows);
	}

	public void screenPartialsUpdate(final TerminalDriver driver, final int row1, final int col1, final int row2,
			final int col2) {

		wrappedListener.screenPartialsUpdate(driver, row1, col1, row2, col2);
	}

	public void screenChanged(final TerminalDriver driver) {
		flushKeys(driver);
		wrappedListener.screenChanged(driver);
		lastInhibited=null;
	}

	public void note(final String note) {
		wrappedListener.note(note);
	}

	/**
	 * Filter duplicate updates to inputinhibited
	 */
	public void inputInhibited(boolean inhibited) {
		flushKeys(null);
		if(!inputInhibitedDelta || !Boolean.valueOf(inhibited).equals(lastInhibited)){
			wrappedListener.inputInhibited(inhibited);
		}
		lastInhibited=inhibited;
	}
	
	public void cursorMoved(TerminalDriver driver, int row, int col){
		wrappedListener.cursorMoved(driver, row, col);
	}
	
	void flushKeys(TerminalDriver driver){
		if(keyBuffer.length()>0){
			wrappedListener.sendKeys(driver, keyBuffer.toString());
			keyBuffer.setLength(0);
		}
	}

	public void close() throws IOException {
		if(wrappedListener instanceof Closeable){
			((Closeable) wrappedListener).close();
		}
	}
	
	public void setBufferKeys(boolean bufferKeys){
		flushKeys(null);
		this.bufferKeys=bufferKeys;
	}
}
package com.terminaldriver.tn5250j.obj;

public enum Key{
	ATTENTION("[attn]"),
	SYSTEM_REQUEST("[sysreq]"),
	RESET("[reset]"),
	CLEAR("[clear]"),
	HELP("[help]"),
	PG_UP("[pgup]"),
	PG_DOWN("[pgdown]"),
	ROLL_LEFT("[rollleft]"),
	ROLL_RIGHT("[rollright]"),
	FIELD_EXIT("[fldext]"),
	FIELD_PLUS("[field+]"),
	FIELD_MINUS("[field-]"),
	BOF("[bof]"),
	ENTER("[enter]"),
	HOME("[home]"),
	INSERT("[insert]"),
	BACKSPACE("[backspace]"),
	BACKTAB("[backtab]"),
	UP("[up]"),
	DOWN("[down]"),
	LEFT("[left]"),
	RIGHT("[right]"),
	DELETE("[delete]"),
	TAB("[tab]"),
	EOF("[eof]"),
	ERASE_EOF("[eraseeof]"),
	ERASE_FIELD("[erasefld]"),
	F1("[pf1]"),
	F2("[pf2]"),
	F3("[pf3]"),
	F4("[pf4]"),
	F5("[pf5]"),
	F6("[pf6]"),
	F7("[pf7]"),
	F8("[pf8]"),
	F9("[pf9]"),
	F10("[pf10]"),
	F11("[pf11]"),
	F12("[pf12]"),
	F13("[pf13]"),
	F14("[pf14]"),
	F15("[pf15]"),
	F16("[pf16]");
	
	private String code;
	Key(final String code){this.code= code;}
	@Override
	public String toString(){return code;}
}
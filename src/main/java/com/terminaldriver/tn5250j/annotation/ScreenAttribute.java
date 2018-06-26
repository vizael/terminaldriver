package com.terminaldriver.tn5250j.annotation;

public enum ScreenAttribute {
	
	//https://www.ibm.com/support/knowledgecenter/ssw_i5_54/apis/dsm1f.htm
	UNSET(null,"Unset", "x00"),
	GRN(" ","Green", "x20"),
	GRN_RI("!","Green/Reverse Image", "x21"),
	WHT("\"","White", "x22"),
	WHT_RI("#","White/Reverse Image", "x23"),
	GRN_UL("$","Green/Underscore", "x24"),
	GRN_UL_RI("%","Green/Underscore/Reverse Image", "x25"),
	WHT_UL("&","White/Underscore", "x26"),
	ND("'","Nondisplay", "x27"),
	RED("(","Red", "x28"),
	RED_RI(")","Red/Reverse Image", "x29"),
	RED_BL("*","Red/Blink", "x2A"),
	RED_RI_BL("+","Red/Reverse Image/Blink", "x2B"),
	RED_UL("`","Red/Underscore", "x2C"),
	RED_UL_RI("-","Red/Underscore/Reverse Image", "x2D"),
	RED_UL_BL(".","Red/Underscore/Blink", "x2E"),
	ND_2F("/","Nondisplay", "x2F"),
	TRQ_CS("0","Turquoise/Column Separators", "x30"),
	TRQ_CS_RI("1","Turquoise/Column Separators/Reverse Image", "x31"),
	YLW_CS("2","Yellow/Column Separators", "x32"),
	YLW_CS_RI("3","Yellow/Column Separators/Reverse Image", "x33"),
	TRQ_UL("4","Turquoise/Underscore", "x34"),
	TRQ_UL_RI("5","Turquoise/Underscore/Reverse Image", "x35"),
	YLW_UL("6","Yellow/Underscore", "x36"),
	ND_37("7","Nondisplay", "x37"),
	PNK("8","Pink", "x38"),
	PNK_RI("9","Pink/Reverse Image", "x39"),
	BLU(":","Blue", "x3A"),
	BLU_RI(";","Blue/Reverse Image", "x3B"),
	PNK_UL("<","Pink/Underscore", "x3C"),
	PNK_UL_RI("=","Pink/Underscore/Reverse Image", "x3D"),
	BLU_UL(">","Blue/Underscore", "x3E"),
	ND_3F("?","Nondisplay", "x3F");

	private String code;
	private String hex;
	private String description;
	
	public String getCode() {
		return this.code;
	}

	public String getHexCode() {
		return this.hex;
	}
	
    public String getDescription() {
		return this.description;
	}
	private ScreenAttribute(final String code,final String desc, final String hexCode) {
		this.code = code;
		this.description=desc;
		this.hex = hexCode;
	}
	
    public String getColor(){
    	if(code != null && !"Nondisplay".equals(description)){
    		if(description.contains("Reverse")){
    			return "reverse" + description.split("/")[0];
    		}else{
	    		return description.split("/")[0].toLowerCase();
	    	}
    	}
    	return "";
    }
    public boolean isUnderLine(){
    	return description.contains("Underscore");
    }
    public boolean isNonDisplay(){
    	return "Nondisplay".equals(description);
    }
    
	public static ScreenAttribute getAttrEnum(final char currentAttr){
		for (ScreenAttribute attr : ScreenAttribute.values()) {
			if (attr.getCode() != null && currentAttr == attr.getCode().charAt(0)) {
				return attr;
			}
		}
		return UNSET;
	}
	
	public static ScreenAttribute getAttrEnum(final String hexCode){
		for (ScreenAttribute attr : ScreenAttribute.values()) {
			if (attr.getHexCode() != null && hexCode.contentEquals(attr.getHexCode())) {
				return attr;
			}
		}
		return UNSET;
	}
	
}

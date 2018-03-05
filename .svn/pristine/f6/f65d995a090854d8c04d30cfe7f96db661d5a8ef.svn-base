package com.liyang.util;

/**
 * @author Administrator
 *
 */
public enum FileFormat {
	//不同图像文件格式的文件头
    JPEG("FFD8FF"), PNG("89504E47"),GIF("47494638"), TIFF("49492A00"),BMP("424D"),    
    // Word/Excel.  
    XLS_DOC("D0CF11E0"), XLSX_DOCX("504B0304"),VSD("d0cf11e0a1b11ae10000"),WPS("d0cf11e0a1b11ae10000"),  
    //torrent 
    TORRENT("6431303A637265617465"),WPD("FF575043"),EPS("252150532D41646F6265"),PDF("255044462D312E"), TXT("75736167"),
    //Quicken. 
    QDF("AC9EBD8F"),  
    //Windows Password. 
    PWL("E3828596"),  
//    // ZIP Archive. 
    ZIP("504B3030"),  
    //RAR Archive. 
    RAR("52617221"),  
    //MF Archive.  
    MF("4D616E69666573742D56"),  
    //EXE Archive.   
    EXE("4D5A9000030000000400"),  
  
    WAV("57415645"),AVI("41564920"),RAM("2E7261FD"),RM("2E524D46"), MP4("00000020667479706d70"),MP3("49443303000000002176"),FLV("464C5601050000000900");  
    
	private String value = "";  
    
    private FileFormat(String value) {  
        this.value = value;  
    }  
  
    public String getValue() {  
        return value;  
    }  
    public void setValue(String value) {  
        this.value = value;  
    }  
  
}  


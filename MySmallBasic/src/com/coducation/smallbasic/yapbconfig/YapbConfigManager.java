package com.coducation.smallbasic.yapbconfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;

// YAPB configuation (파일에 작성된) 내용을 문자열로 읽고 쓰는 프로그램
// yapb.config 파일을 열고 닫는 코드를 추가해야 함
public class YapbConfigManager {

	// token 토큰 : 더이상 쪼갤수 없는 단어 
	// delimeters 토큰 구분 문자: 열기/닫기 중괄호, 이퀄, 콤마, 각종 공백
	private final static String delimeters = "{}=, \t\n"; 
	private static final String CONFIG_FILE_PATH = System.getProperty("user.dir") + "/sbparser/yapb.config"; // sbparser.exe가 있는 위치에 생성
	private Preferences preferences;
	String Path = System.getProperty("user.dir") + "/sbparser/yapb.config"; // 현재 프로젝트 경로 가져오기

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<String> arrList;

		if (false) {
			arrList = lex ( scanner );
			for (String token : arrList)
				System.out.println(token);
			YapbConfiguration yapbConfig = parse( arrList );
			System.out.println( prettyPrint(yapbConfig) );
		}
		else test1();
	}
	
	public YapbConfigManager() {
        preferences = Preferences.userRoot().node(getClass().getName());
    }

	// YAPB Configuration 토큰들을 추출하는 함수
	public static ArrayList<String> lex( Scanner scanner ) {

		ArrayList<String> arrList = new ArrayList<String>();
		
		while ( scanner.hasNext() ) {
			String line = scanner.nextLine();

			StringTokenizer stz = 
					new StringTokenizer(line, YapbConfigManager.delimeters, true);

			while ( stz.countTokens() > 0 ) {
				String token = stz.nextToken();
				
				// 토큰이 공백이 아닌 경우만 저장
				if (" ".equals(token) || "\t".equals(token) || "\n".equals(token))
					;
				else {			
					arrList.add(token);
				}
			}	      
		} 	
		
		return arrList;
	}
	
	// YAPB Configuration 토큰들의 구조를 분석하여 YapbConfiguration 객체를 만드는 함수
	public static YapbConfiguration parse( ArrayList<String> tokens ) {
		YapbConfiguration yapbConfig = new YapbConfiguration();
		yapbConfig.keyValueMap = new HashMap<String,String>();
		
		int index = 0;
		
		// title: Configuration
		if (index < tokens.size() == false) { 
			System.err.println("No title");
			return null;
		}
		
		yapbConfig.title =  tokens.get(index);
		
		index++;
		
		// {
		if (index < tokens.size() == false || "{".equals(tokens.get(index)) == false ) {
			System.err.println("Expected { but not found.");
			return null;
		}
		
		while (true) {
			index++;
			
			// key = value     key와 value를 모두 문자열로 취급 
			if (index+2 < tokens.size() == false || "=".equals(tokens.get(index+1)) == false ) {
				System.err.println("Expected key = valuse but something wrong: " + index);
				return null;
			}
			
			yapbConfig.keyValueMap.put(tokens.get(index), tokens.get(index+2));
			
			index = index + 3;
			
			// ; 또는 }
			if (index < tokens.size() == false) {
				System.err.println("Expected ; or } but found the end of tokens.");
				return null;
			}
			
			if (",".equals(tokens.get(index)) == false && "}".equals(tokens.get(index)) == false) {
				System.err.println("Expected ; or } but not found: " + tokens.get(index));
				return null;
			}
			
			// ;이면 반복하고, }이면 종료
			String thisToken = tokens.get(index);
			
			if ("}".equals(thisToken))
				break;
			
		}
		
		return yapbConfig;
	}
	
	// YapbConfiguration 객체를 문자열로 바꾸는 함수
	public static String prettyPrint( YapbConfiguration yapbConfig ) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(yapbConfig.title + " {" + "\n");
		
		// Set<HashMap.Entry<String,String>> set = yapbConfig.keyValueMap.entrySet();
		HashMap<String,String> set = yapbConfig.keyValueMap;
		
		int index = 0;
		
		// yapb.config 속성 순서대로 불러와 value 값을 재설정한다.
		for(String p : property) {
			sb.append(p + " = " + set.get(p));
			
			String closure;
			
			if(index + 1 == set.size())
				closure = "";
			else
				closure = ",";
			
			sb.append(closure + "\n");
			
			index++;
		}

		sb.append("}" + "\n");
		
		return sb.toString();
	}
	
	public void loadConfig() throws IOException {
		YapbConfigManager config = new YapbConfigManager();

		config.setConfigData(yapbconfigData);
	}
	
	public void saveConfig() throws IOException {
        String configData = preferences.get("config", "");
        try (FileWriter writer = new FileWriter(CONFIG_FILE_PATH)) {
            writer.write(configData);
        }
    }
	
	 public String getConfigData() {
	        return preferences.get("config", "");
	    }
	
	public void setConfigData(String configData) {
	    preferences.put("config", configData);
	    try {
	        saveConfig();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void setConfigData(String configData, String configName, String OriginFlag, String flag) {
	    String updatedConfigData = configData.replace(configName + " = " + OriginFlag , configName + " = " + flag);
	    preferences.put("config", updatedConfigData);
	    try {
	        saveConfig();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	// YAPB Configuration 속성을 바꾸는 함수 
	public void set(YapbConfiguration yapbConfig, String key, String value) {
		yapbConfig.keyValueMap.put(key, value);
	}
	
	//yapb.config 파일을 불러와서 내부 데이터(문자열)을 뽑아오는 함수
	public String getConfigFile(BufferedReader br) throws IOException {
		loadConfig();
		File configFile = new File(Path);
		String configData = "";
		
		try {
			br = new BufferedReader(new FileReader(configFile));
			String s = null;
			while((s = br.readLine()) != null) {
				configData += s;
			}
			s = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return configData;
	}
	
	
	// yapb.config 파일에 write하는 함수
	public void printConfigFile(String configData) {
		preferences.put("config", configData);
		 BufferedWriter writer; // FileWriter 선언

		try {
			writer = new BufferedWriter(new FileWriter(Path)); // 파일이 있을경우 덮어쓰기
			writer.write(configData);
			writer.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	// yapb.config의 config_TABSTATE 값 변경 후 저장하는 함수
	public void configConversion(String OriginTabFlag, String tabFlag, String OriginDisplayFlag, String displayFlag) {
//		ArrayList<String> tokens;
//		tokens = lex(new Scanner(configData)); // 토큰 추출
//		YapbConfiguration yapbConfig = parse(tokens); // YapbConfiguration 객체를 만듦
//		
		// 객체에서 config_TABSTATE의 value를 변경
		String configData = getConfigData();
		setConfigData(configData, "config_DISPLAY", OriginDisplayFlag, displayFlag);
		configData = getConfigData();
		setConfigData(configData, "config_TABSTATE", OriginTabFlag, tabFlag);
		
		//set(yapbConfig, "config_TABSTATE", tabFlag);
		//set(yapbConfig, "config_DISPLAY", displayFlag);
		
		// config 객체를 문자열로 변환 후 파일에 저장
		//String modifiedConfig = prettyPrint(yapbConfig);
		//printConfigFile(modifiedConfig);
		
		//return modifiedConfig
		
	}
	
	// yapb.config 파일의 속성 순서
	private final static ArrayList<String> property = 
			new ArrayList<>(Arrays.asList("config_SIMPLE", "config_R_LEVEL", 
					"config_GS_LEVEL", "config_DEBUG", "config_DISPLAY", "config_PRESENTATION",
					"config_ALGORITHM", "config_COLLECT", "config_TABSTATE"));

	private final static String yapbconfigData = "Configuration {\n"
			+ "  config_SIMPLE = True,\n"
			+ "  config_R_LEVEL = 1,\n"
			+ "  config_GS_LEVEL = 9, \n"
			+ "  config_DEBUG = False, \n"
			+ "  config_DISPLAY = False,\n"
			+ "  config_PRESENTATION = 0,\n"
			+ "  config_ALGORITHM = 3,\n"
			+ "  config_COLLECT = False,\n"
			+ "  config_TABSTATE = True\n"
			+ "}";

	public static void test1() {
		ArrayList<String> tokens;
		tokens = lex(new Scanner(yapbconfigData));
		YapbConfiguration yapbConfig = parse( tokens );
		System.out.println( prettyPrint(yapbConfig) );
	}
}
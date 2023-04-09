package com.coducation.smallbasic.syntax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextExtract {

	public static void main(String[] args) throws IOException {
	    
		// args[0]: smallbasic-program-list.txt 경로
		// args[1]: .sb 파일들을 담을 경로
		// 경로 불러오기
		int numOfSBFilesTried = 0;
		int numOfSBFilesDownloaded = 0;
		
		// 경로 불러오기
		String rootPath = args[1]; 
		// System.getProperty("user.dir");
		
		// smallbasic id가 저장된 text 파일
		File file = new File(args[0]);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		InputStream is = null;
		
		// smallbasic id가 저장된 text 파일
		Pattern pattern = Pattern.compile("<div id=\"codeListing\">(.*?)</div>", Pattern.DOTALL);
		
		String str;
		while((str = bufferedReader.readLine()) != null) {
			try {			    
			        numOfSBFilesTried++;
			        
				URL url = new URL("http://smallbasic.com/program/?" + str);
				URLConnection urlConnection = url.openConnection();
				is = urlConnection.getInputStream();
				byte[] buffer = new byte[1024];
				StringBuilder result = new StringBuilder();
				int readBytes;
				
				// url 데이터를 문자열로 가져온다
				while((readBytes = is.read(buffer)) != -1) {
					String part = new String(buffer, 0, readBytes);
					result.append(part);
				}
				
				String extract = "";
				// <div id=\"codeListing\"> 해당 태그 내의 코드만 추출
				Matcher matcher = pattern.matcher(result);
				
				while(matcher.find()) {
					extract = matcher.group(1).trim();
				}
				
				// 코드에 남아있는 <br /> 태그 제거
				extract = extract.replaceAll("<br />", "");
				
				// 파일 저장
				 if(!(extract.equals("error"))) {
					String newFile = rootPath + "\\" + str+".sb";
					FileWriter fileWriter = new FileWriter(newFile);
					fileWriter.write(extract);
					fileWriter.close();
					
					// 파일 다운로드 확인용 출력
					System.out.println(str);

					numOfSBFilesDownloaded++;
				 }
				 else {
					// 에러 발생한 파일 확인용 출력
					System.out.println(str + " : error");
				 }
				
			} catch(MalformedURLException e) {
				System.err.println(e);
			}
		}
		
		System.out.println("Total: " + numOfSBFilesTried);
		System.out.println("Downloads: " + numOfSBFilesDownloaded);
		System.out.println("Errors: " + (numOfSBFilesTried - numOfSBFilesDownloaded));
	}
}

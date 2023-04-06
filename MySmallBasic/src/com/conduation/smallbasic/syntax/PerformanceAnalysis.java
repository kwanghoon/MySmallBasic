package com.conduation.smallbasic.syntax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PerformanceAnalysis {
	private static BufferedReader bufferedReader;
	private static SyntaxCompletionDataManager dataManager;
	private static ArrayList<String> searchList;
	private static File file;
	
	/*
	 * Terminal : T
	 * Nonterminal : NT
	 */
	
	public static void main(String[] args) throws IOException {

		// args[0]: smallbasic-tutorial-list-yapb-data-collection.txt 경로
		// args[1]: smallbasic-program-list-yapb-data-colletion_results.txt 경로
		buildSyntaxData(args[0]); // 튜토리얼 프로그램에서 수집된 구문 완성 후보를 list로 만듦
		
		searchForSyntax(searchList, args[1]); // 스몰베이직 프로그램에서 얻은 데이터 구문 완성 후보 목록에 포함되어 있는지 확인
		
	}
	
	public static void buildSyntaxData(String path) throws IOException {
		// 파일에서 구문 추출
		file = new File(path);
		bufferedReader = new BufferedReader(new FileReader(file));
		
		String str;
		String str_arr[];
		boolean integer = false;
		searchList = new ArrayList<>();
		while((str = bufferedReader.readLine()) != null) {
			// 라인의 첫 문자가 숫자이면 실행
			integer = Character.isDigit(str.charAt(0));
			
			if(integer) {
				// 공백을 기준으로 split하여 list에 저장
				str_arr = str.split("\\s");
				
				// 상태 분류 리스트에 맞춰 데이터 저장
				for(int i = 1; i < str_arr.length - 1; i++) {
					if(str_arr[i].equals("Terminal")) {
						str_arr[i] = "T";
					} 
					else if(str_arr[i].equals("Nonterminal")) {
						str_arr[i] = "NT";
					}
				}
				
				// 만든 구문 완성 후보 목록에 포함되어있는지 검사할 튜토리얼 프로그램 구문 완성 후보 list로 저장
				searchList.add(String.join(" ", str_arr));
			}
		}
	} // buildSyntaxData end
	
	public static void searchForSyntax(ArrayList<String> list, String path) throws IOException {
		// 저장한 list 검색 및 존재 여부 출력
		dataManager = new SyntaxCompletionDataManager(path);
		int total = 0, not = 0;
		
		boolean flag;
		int state, value, size = 0;
		// 저장해놓은 튜토리얼 프로그램의 구문 완성 후보만큼 실행
		for(int i = 0; i < list.size(); i++) {
			total++;
			
			flag = false;
			value = 0;
			String[] line = list.get(i).split(" ");
			// 파싱 상태 번호와 빈도를 제외한 구문 완성 후보만을 뽑아 저장
			String[] arr = Arrays.copyOfRange(line, 1, line.length - 1);
			ArrayList<String> arrList = new ArrayList<String>(Arrays.asList(arr));
			
			state = Integer.parseInt(line[0]);
			
			if(dataManager.getMap().get(state) != null) {
				size = dataManager.getMap().get(state).size(); // 해당 상태에 대한 후보 개수
				value = dataManager.searchForSyntaxCompletion(arrList, state); // 찾은 후보들 중 몇 번째에 해당하는지
				
				if(value != 0) flag = true; // 후보가 0번째에 해당한다면 목록에 없는 것
			}
			
			String result = "";
			if(flag) {
				result = state + " " + String.join(" ", arr) + " Found " + value + " " + size;
			} else {
				result = state + " " + String.join(" ", arr) + " NotFound";
				not++;
			}
			
			System.out.println(result);
		}
		
		System.out.println("\nTotal: " + total);
		System.out.println("Found: " + (total - not));
		System.out.println("NotFound: " + not);
	} // searchForSyntaxCompletion end
	
}

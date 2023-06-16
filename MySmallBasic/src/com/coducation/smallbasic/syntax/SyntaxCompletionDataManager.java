package com.coducation.smallbasic.syntax;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SyntaxCompletionDataManager {
   private static HashMap<Integer, ArrayList<Pair>> map;
   private static BufferedReader bufferedReader;
   private static File file;
   private static int MAX_STATE = 0;
   
   /*
    * Terminal : T
    * Nonterminal : NT
    */

   public static void main(String[] args) throws IOException {
	  // map = new HashMap<>();
	  // String s;
	  /*
	  Scanner sc = new Scanner(System.in);
	  do {
		  s = sc.next();
		  buildSyntaxCompletionData(s);
	  } while(!s.equals("end"));
	  */
	   
	  // args[0]: smallbasic-program-list-yapb-data-colletion_results.txt 경로
      buildSyntaxCompletionData(args[0]); // 스몰베이직 프로그램에서 얻은 데이터 구문 완성 후보를 해쉬맵으로 만듦
      
      listForSyntaxCompletion(); // 만든 목록을 출력
      
   }
   
   public SyntaxCompletionDataManager(String args) throws IOException {
      buildSyntaxCompletionData(args);
   }
   
   public static void buildSyntaxCompletionData(String path) throws IOException {
      // 파일에서 상태 추출
      file = new File(path);
      
      bufferedReader = new BufferedReader(new FileReader(file));
      
      // 상태 번호, 상태에 대한 텍스트에 저장된 문장
      int state = 0;
      int state_count;
            
      String str;
      String str_arr[];
      String stateWord;
      map = new HashMap<>();
            
      while((str = bufferedReader.readLine()) != null) {
    	  // 공백을 기준으로 라인의 첫 단어만 추출
    	  stateWord = str.split(" ")[0];
         
          // 라인의 첫 단어가 State이면 if문 실행, 그렇지 않으면 else문 실행
    	  if(stateWord.equals("State")) {
    		  state = Integer.parseInt(str.split("\\s")[1]);
        	  MAX_STATE = state;
    	  }
    	  else {
            // " : "을 기준으로 split하여 전달받은 구문 구조와 빈도수 저장
    		state_count = Integer.parseInt(str.split("\\s:\\s")[1]);
    		String syntaxList = str.split(" : ")[0];
    		syntaxList = syntaxList.substring(1, syntaxList.length()-1); // 대괄호 문자 삭제 "[", "]"
            str_arr = syntaxList.split(", ");
            
            ArrayList<String> result = new ArrayList<>();
            // 나눈 구문 문자열들을 리스트에 저장
            for(String item : str_arr) { 
                 result.add(item);
            }
                  
            // 파싱 상태가 map에 존재하지 않으면 새로 생성 후 추가, 존재하면 값 추가
            if(!map.containsKey(state)) {
            	map.put(state, new ArrayList<>());
            	map.get(state).add(new Pair(result, state_count));
            } else {
            	 map.get(state).add(new Pair(result, state_count));
            }
         }
      }
   } // buildSyntaxCompletionData end
   
   public static HashMap<Integer, ArrayList<Pair>> getMap() {
      return map;
   }
   
   // PerformanceAnalysis.java에서 사용, 구문이 만든 해쉬맵에 존재하는지 확인
   public static int searchForSyntaxCompletion(ArrayList<String> arr, int state) {
        int search_state = state;
       int value = 0; // 후보 번호
       
       // 전달받은 상태에 대해 전달받은 후보 구문이 몇 번째 구문에 해당하는지 찾는다.
      Iterator<Pair> keys = map.get(search_state).iterator();
      int count = 0;
      while(keys.hasNext()) {
         Pair key = keys.next();
         count++;
         if(key.getFirst().equals(arr)) {
            // 구문이 존재하면 후보 번호 설정
            value = count;
            break;
         }
       }
      
      return value;
   } // searchForSyntaxCompletion end
   
   // smallBasic 구문 완성 검색 시 사용, 상태를 전달받음
   public ArrayList<Pair> searchForSyntaxCompletion(ArrayList<Integer> stateList) {
      ArrayList<Pair> arr = new ArrayList<Pair>(); // 각 구문 후보에 대한 빈도수도 저장해주어야 하기에 pair 사용(정렬 위해)
      
      for(int state : stateList) {
         ArrayList<Pair> pair = map.get(state);
         if(pair == null) continue; // 상태 2와 같은 사이즈가 없는 경우 pass
         for(int i = 0; i < pair.size(); i++) {
            // 전달받은 상태에 대한 후보 구문들을 공백으로 나누어 리스트에 저장
            arr.add(new Pair(pair.get(i).getFirst(), pair.get(i).getSecond()));
         }   
      }
      
      return arr;
   } // searchForSyntaxCompletion end
   
   // 구문 후보 map 에서 구문 후보들만을 뽑아 내림차순 정렬 후 array로 변환하여 리턴 
   public ArrayList<String> mapToArray(Map<ArrayList<String>, Integer> sortList) {
      // 내림차순 정렬
      List<ArrayList<String>> keySet = new ArrayList<>(sortList.keySet());
      keySet.sort((o1, o2) -> sortList.get(o2).compareTo(sortList.get(o1)));
      
      // array로 변환 후 리턴
      ArrayList<String> list = new ArrayList<>();
      for( ArrayList<String> key : keySet){
         list.add(String.join(" ", key));
      }
      
      return list;
   }
   
   // 만든 해쉬맵 확인을 위한 출력
   public static void listForSyntaxCompletion() {
        int user_state = 0;
        // final int MAX_STATE = 118;  // 0 ~ 118
        
        while (user_state <= MAX_STATE) {
           System.out.println("State " + user_state);
           
           if (map.get(user_state) != null) {
            for(int i = 0; i < map.get(user_state).size(); i++) {
               System.out.println(map.get(user_state).get(i).getFirst() + " : " + map.get(user_state).get(i).getSecond());
            }
           }
           
           user_state = user_state + 1;
        }
   } // listForSyntaxCompletion end
   
   public static class Pair implements Comparable<Pair>{
       private ArrayList<String> first;
       private int second;
       
       public Pair(ArrayList<String> first, int second) {
          this.first = first;
          this.second = second;
       }
       
       public void setFirst(ArrayList<String> first) {
          this.first = first;
       }
       
       public void setSecond(int second) {
          this.second = second;
       }
       
       public ArrayList<String> getFirst() {
          return first;
       }
       
       public int getSecond() {
          return second;
          }

      public int compareTo(Pair p) {
         return Integer.compare(p.second, this.second);
      }
       
    } // Pair end
}
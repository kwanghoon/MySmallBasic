package com.coducation.smallbasic.syncomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.coducation.smallbasic.gui.MySmallBasicGUI;
import com.syntax.SyntaxCompletionDataManager;
import com.syntax.SyntaxCompletionDataManager.Pair;

public class SocketCommunication {
	private static final int PORT = 50000;
	
	private static String message;  // socket 전송 메세지
	private static Socket link = null;
	private static BufferedReader input= null;
	private static PrintWriter output = null;
	private static InetAddress host = null;
	private static SyntaxCompletionDataManager syntaxManager;
	private static MySmallBasicGUI frame; // 확인 필요
	
	private static ArrayList<String> list = null;
	private static int position = 0;
	private static boolean isReceive = false;
	private static boolean connect = true;
	private static boolean state_receive = false;
	
	static String collection_path = "";
	
	static {
		try {
			// smallbasic-program-list-yapb-data-colletion_results.txt 경로 파라미터로 전달
			syntaxManager = new SyntaxCompletionDataManager("C:\\Users\\Hwangsooyeon\\git\\SmallBasicDataCollection\\data\\smallbasic-program-list-yapb-data-colletion_results.txt"); // 경로 넣어줘야 함
		} catch (IOException e) {
			System.out.println("Error: Load in SyntaxCompletionDataManager");
			e.printStackTrace();
		}
	}
	
	public SocketCommunication(JTextArea textArea) {
		
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException uhEx) {
			// 호스트 ip를 찾지 못하면 메시지 창을 띄우면서 종료
			JOptionPane.showMessageDialog(null, "Host ID not found!", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
		}
			
			// tab 키가 눌리면 서버 접속
			accessServer1(host);
			
			if(connect) {
			// 사용자가 입력한 커서 앞 text를 가져온다.
			position = textArea.getCaretPosition();
			message = textArea.getText();
			message = message.substring(0, position);
			
			// text의 길이 반환
			int textLength = message.length();
				
			String sendMessage = "" + textLength + " True";
			
			// 서버에게 텍스트 길이 전달
			output.print(sendMessage);
			output.flush();
			
			// 커서 앞 텍스트 길이, true 전달 후 서버 접속 끊기
			closingConnecting1();

			
			// 다시 서버 접속, 커서 앞의 텍스트 보내기
			accessServer1(host);
			
			output.print(message);
			output.flush();
			
			// 접속 끊기
			closingConnecting1();
			
			
			// 서버 접속, 커서 뒤의 텍스트를 보낸다.
			accessServer1(host);
			
			message = textArea.getText();
			message = message.substring(position, textArea.getText().length());
			
			output.print(message);
			output.flush();
			
			// 연결 끊기
			closingConnecting1();
			
			
			// 서버로부터 문자열 수신 및 출력
			accessServer1(host);
			
			list = new ArrayList<>();
			String receiveMessage = null;
			String[] state_list = null;
			int messageStateIdx;
			isReceive = false;
			try {
				while((receiveMessage = input.readLine()) != null) {
					char subStr = '\u0000'; // 파싱 상태 저장 변수
					// 서버로부터 파싱 상태만을 받았는지, 구문 후보를 받았는지 확인
					if(!"".equals(receiveMessage)) {
						// 문자열을 전달받으면 문자열로부터 후보를 뽑아낸다.
						// white 문자열 제거
						receiveMessage = receiveMessage.replace("white ", "");
						messageStateIdx = receiveMessage.indexOf(" ") + 1;
						subStr = receiveMessage.charAt(messageStateIdx); // 첫 공백을 기준으로 다음 문자를 뽑아낸다.
						//state_receive = subStr.matches("\\d*");
						state_receive = Character.isDigit(subStr); // 다음 문자가 숫자가 전달되었는지 확인(파싱 상태만 전달받았는지)
						if(state_receive) state_list = receiveMessage.split(" "); // 파싱 상태를 전달받았다면 상태들을 뽑아온다.
					} else continue;
						
					// 이전 문자열이 공백이면 state 0을 반환
					if("SuccessfullyParsed".equals(receiveMessage)) {
						subStr = '0';
						state_receive = true;
						/*{
							isReceive = false;
							break;
						}*/
					}
					else if("LexError".equals(receiveMessage)) {
						JOptionPane.showMessageDialog(textArea, "incorrect syntax: LexError!", "LexError", JOptionPane.ERROR_MESSAGE);
						isReceive = false;
						break;
					}
					else if("ParseError".equals(receiveMessage)) {
						JOptionPane.showMessageDialog(textArea, "incorrect syntax: ParseError!",  "ParseError", JOptionPane.ERROR_MESSAGE);
						isReceive = false;
						break;
					}
					
					isReceive = true;
					// 상태가 전달되면 맵으로부터 후보를 뽑는다.
					if(state_receive) {
						ArrayList<Integer> newStateList = new ArrayList<>();
						
						// 실시간 서버로부터 구문 후보를 받아올 때 "SuccessfullyParsed" 처리
						if(subStr == '0' && state_list == null) {
							newStateList.add(0);
						} else {
							for(String s : state_list) {
								newStateList.add(Integer.parseInt(s));
							}
						}
						
						Map<ArrayList<String>, Integer> sortList = new HashMap<>(); // 정렬과 중복 확인 위해 map 사용
						
						ArrayList<Pair> dupCheckList = new ArrayList<Pair>(); // 파싱 상태에 대한 구문 후보 중복 체크용 리스트
						dupCheckList = syntaxManager.searchForSyntaxCompletion(newStateList); // 전달받은 상태에 대한 후보 구문들을 리스트로 저장
						
						// list에 담겨있지 않은 구문 후보만 저장
						for(Pair pair : dupCheckList) {
							if(!sortList.containsKey(pair.getFirst())) {
								sortList.put(pair.getFirst(), pair.getSecond());
							}
						}
						
						list = syntaxManager.mapToArray(sortList);
					}
					else {
						list.add(receiveMessage);
					}
				} // while
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			closingConnecting1();
		} // end if
	}
	
	public boolean getState() {
		return state_receive;
	}
	
	public ArrayList<String> getList() {
		return list;
	}
	
	public int getPosition() {
		return position;
	}
	
	public boolean getIsReceive() {
		return isReceive;
	}
	
	public boolean getIsConnect() {
		return connect;
	}
	
	private static void closingConnecting1() {
		// 접속 끊기
		try {
			if(link != null) {
				link.close();
				link = null;
			}
		} catch(IOException ioEx) {
			System.out.println("Connecting error with server");
		}
	}
	
	// 서버와 연결
	private static void accessServer1(InetAddress host) {
		try {
			
			link = new Socket(host, PORT);
			
			input = new BufferedReader(new InputStreamReader(link.getInputStream()));
			
			output = new PrintWriter(link.getOutputStream());
			
			connect = true;
		}
		catch(IOException ioEx) {
			System.out.println("Connection refused with server");
			System.out.println(ioEx.getMessage());
			connect = false;
		}
	}
}

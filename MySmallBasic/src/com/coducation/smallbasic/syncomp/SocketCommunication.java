package com.coducation.smallbasic.syncomp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import com.syntax.SyntaxCompletionDataManager;

public class SocketCommunication {
	private static final int PORT = 50000;
	
	private static String message;  // socket 전송 메세지
	private static Socket link = null;
	private static BufferedReader input= null;
	private static PrintWriter output = null;
	private static InetAddress host = null;
	private static SyntaxCompletionDataManager syntaxManager;
	
	private static ArrayList<String> list = null;
	private static int position = 0;
	private static boolean isReceive = false;
	private static boolean connect = true;
	private static boolean state_receive = false;
	
	static {
		try {
			syntaxManager = new SyntaxCompletionDataManager();
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
			isReceive = false;
			try {
				while((receiveMessage = input.readLine()) != null) {
					String subStr = "";
					// 서버로부터 파싱 상태만을 받아올 때 실행
					if(!"".equals(receiveMessage)) {
						subStr = receiveMessage.substring(receiveMessage.indexOf(" ") + 1); // 첫 공백을 기준으로 다음 문자를 뽑아낸다.
						state_receive = subStr.matches("\\d*"); // 다음 문자가 숫자가 전달되었는지 확인(파싱 상태만 전달받았는지)
					}
						
					// 이전 문자열이 공백이면 state 0을 반환
					if("SuccessfullyParsed".equals(receiveMessage)) {
						if(state_receive) receiveMessage = "State 0";
						else {
							isReceive = false;
							break;
						}
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
						list = syntaxManager.searchForSyntaxCompletion(subStr); // 전달받은 상태에 대한 후보 구문들을 리스트로 저장
					}
					else {
						// 문자열을 전달받으면 문자열로부터 후보를 뽑아낸다.
						// white 문자열 제거
						receiveMessage = receiveMessage.replace("white ", "");
						list.add(receiveMessage);
					}
				}
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

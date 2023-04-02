package com.coducation.smallbasic.gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import com.coducation.smallbasic.syncomp.SocketCommunication;

public class MySmallBasicSyntaxItems {
	public static final String NEWLINE = System.getProperty("line.separator");
	private static JPanel contentPane;
	private TextAreaMaker textAreaMaker;
	private JPopupMenu popupmenu;
	private JScrollPopupMenu scrollPopupmenu;
	private SocketCommunication SC;
	
	private boolean isReceive = false;
	private boolean isConnect = false;
	private boolean state_receive;
	private int position = 0;
	private int cursorPosition = 0;
	private Rectangle2D rectangle = null;
	
	private Matcher matcher_ID;
	private Matcher matcher_NUM;
	private Matcher matcher_STR;
	
	// textArea에 PopupMenu 추가 Tab 누를 시 popupmenu 출력
	public MySmallBasicSyntaxItems(JPanel panel, TextAreaMaker textArea) {
		this.contentPane = panel;
		this.textAreaMaker = textArea;
		
		textAreaMaker.getTextArea().addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}

			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_TAB) {
					SC = new SocketCommunication(textAreaMaker.getTextArea());
					isConnect = SC.getIsConnect();
					
					popupmenu = new JPopupMenu();
					scrollPopupmenu = new JScrollPopupMenu();
					
					if(isConnect) { // 서버와 연결이 되어 있는지 확인, 연결시에만 실행
						
						isReceive = SC.getIsReceive();
						position = SC.getPosition();
						state_receive = SC.getState();
						
						ArrayList<String> list = SC.getList(); // 서버를 통해 받아온 후보 구문들
						ArrayList<Integer> cursorList = new ArrayList<>(); // 후보 구문에 대한 각 커서 위치
						
						String list_temp;
						// popupmenu에 문자열 추가
						for(int i = 0; i < list.size(); i++) {
							// 만약 서버로부터 파싱 상태만 받아온다면
							JMenuItem menuitem;
							if(state_receive) {
								// Popupmenu 이벤트 발생 시 textArea에 출력될 문자열
								System.out.println(list.get(i));
								list_temp = list.get(i);
								list_temp = list_temp.replace("NT CRStmtCRs ", "Enter ");
								list_temp = list_temp.replace("CR", "Enter ");
								list_temp = list_temp.replaceAll(" NT\\s[^\\s]*", "blank");
								
								list_temp = list_temp.replaceAll("T ", "");
								// 공백이 중복해서 나오면 제거
								list_temp = list_temp.replaceAll("\\s+", " ");
								list_temp = list_temp.replace("Enter ", "\n");
								list_temp = list_temp.replaceAll("[\\s+]?[.][\\s+]?", ".");
								// 커서를 Nonterminal 위치로 변경
								int setcursor = list_temp.indexOf("blank");
								list_temp = list_temp.replaceAll("blank", "");
								
								// Popupmenu에 띄울 문자열
								list.set(i, list.get(i).replaceAll("NT ", ""));
								list.set(i, list.get(i).replaceAll("T ", ""));
								list.set(i, list.get(i).replaceAll("[\\s+]?[.][\\s+]?", "."));
								
								// popupmenu에 띄우는 문자열과 textArea에 띄우는 문자열을 다르게 설정함
								menuitem = new JMenuItem(list.get(i));
								
								list.set(i, list_temp);
								
								cursorList.add(setcursor);
							} 
							else {
								// 서버로부터 문자열로 후보를 받아온다면
								// "..."이 있으면 처음 "..." 위치로 커서 위치 변경
								System.out.println(list.get(i));
								list_temp = list.get(i);
								list_temp = list_temp.replace("NT CRStmtCRs ", "Enter ");
								list_temp = list_temp.replace("CR", "Enter ");
								list_temp = list_temp.replaceAll("NT\\s[^\\s]*", "blank");
								list_temp = list_temp.replaceAll(" T ", "");
								
								// 공백이 중복해서 나오면 제거
								list_temp = list_temp.replaceAll("\\s+", " ");
								list_temp = list_temp.replace("Enter ", "\n");
								list_temp = list_temp.replaceAll("[\\s+]?[.][\\s+]?", ".");
								
								// 커서를 Nonterminal 위치로 변경
								int setcursor = list_temp.indexOf("blank");
								list_temp = list_temp.replaceAll("blank", "");
								
								// Popupmenu에 띄울 문자열
								list.set(i, list.get(i).replaceAll("NT ", ""));
								list.set(i, list.get(i).replaceAll("T ", ""));
								list.set(i, list.get(i).replaceAll("[\\s+]?[.][\\s+]?", "."));
								
								// popupmenu에 띄우는 문자열과 textArea에 띄우는 문자열을 다르게 설정함
								menuitem = new JMenuItem(list.get(i));
								
								list.set(i, list_temp);
								
								cursorList.add(setcursor);
							}
								
							String itemHeader = list.get(i); // item action에 대한 추가할 문자열
							
							menuitem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									cursorPosition = textAreaMaker.getTextArea().getCaretPosition(); // 문자열 추가 전의 커서 위치
									textAreaMaker.getTextArea().insert(itemHeader, position); // 커서 위치에 item 추가
									
									int listIndex = list.indexOf(itemHeader);
									if(cursorList.get(listIndex) != -1) {
										// 추가한 item에서 NT 위치에 커서를 재설정
										textAreaMaker.getTextArea().setCaretPosition(position + cursorList.get(listIndex));
									}
									// 선택한 구문 후보 중 ID, NUM, STR이 존재하면 사용자로부터 문자열을 입력받는다.
									String listStr = list.get(listIndex);
									
									int matcherIdx = 10;	// 문자열 위치
									int start; // 취소 시 커서 취소할 시작 위치
									int end; // 취소로 삭제되는 마지막 위치
									boolean flag = true;	
									String input = "input"; 	// 사용자 입력
									String pattern = null; 	// 정규식
									String matcherName = null; // 정규식에 대한 문자열(ID, NUM, STR)
									int inputAfterCursor = textAreaMaker.getTextArea().getCaretPosition();

									
									// 추가한 item에 ID, NUM, STR이 존재하는 동안 실행
									while(flag && input != null) {
										matcher_ID = Pattern.compile("ID").matcher(listStr);
										matcher_NUM = Pattern.compile("NUM").matcher(listStr);
										matcher_STR = Pattern.compile("STR").matcher(listStr);
											
										if(flag = matcher_ID.find()) {
											matcherIdx = matcher_ID.start();
											pattern = "[\\_a-zA-Z][\\_a-zA-Z0-9]*";
											matcherName = "ID";
										}
										else if(flag = matcher_NUM.find()) {
											matcherIdx = matcher_NUM.start();
											pattern = "([0-9]*[.])?[0-9]+";
											matcherName = "NUM";
										}
										else if(flag = matcher_STR.find()) {
											matcherIdx = matcher_STR.start();
											pattern = "\"[^\"]*\"";
											matcherName = "STR";
										}
										if(flag) { // 만약 수정을 요구하는 문자열이 존재한다면 실행
											textAreaMaker.getTextArea().setSelectionStart(cursorPosition + matcherIdx);
			                                textAreaMaker.getTextArea().setSelectionEnd(cursorPosition + matcherIdx + matcherName.length());

											input = JOptionPane.showInputDialog(matcherName + ": " + pattern);
												
											while(input != null) {
												// 정규식에 맞게 입력되면 해당 위치에 삽입
												if(Pattern.matches(pattern, input)) {
													inputAfterCursor += (input.length() - matcherName.length());
													cursorPosition += matcherIdx;
													textAreaMaker.getTextArea().replaceRange(input, cursorPosition, cursorPosition + matcherName.length());
													listStr = listStr.replaceFirst(matcherName, input); // 수정된 문자열로 저장
													break;
												}
												// 정규식에 맞지 않으면 재입력 요구 다이얼로그 출력
												input = JOptionPane.showInputDialog(matcherName + ": " + pattern);
											}
											if(input == null) {
												// input이 null인 경우 취소
												// 지금까지 입력된 문자열들은 보존, 취소된 위치부터 공백으로 표시
												// 커서 위치는 취소된 위치에
												end = listStr.length() + cursorPosition;
												start = listStr.indexOf(matcherName) + cursorPosition;
												textAreaMaker.getTextArea().replaceRange("", start, end);
												textAreaMaker.getTextArea().setCaretPosition(start);
											}
										}
										
										if(input != null && !input.equals("input")) {
			                                textAreaMaker.getTextArea().setCaretPosition(inputAfterCursor);
			                            }

									}// while end
								}
									
							}); // end ActionListener
							popupmenu.add(menuitem);
							scrollPopupmenu.addImpl(menuitem, popupmenu, i);
								
						} // end for
						
						scrollPopupmenu.setComponentPopupMenu(popupmenu);
						contentPane.add(popupmenu);
					}
				}
			} // end keyPressed

			public void keyReleased(KeyEvent e) {
				// 서버로부터 받은 문자열을 popupmenu로 출력
				int lineNum = 1;
				int columnNum = 0;
				int caretpos = 0;
				int keyCode = e.getKeyCode();
				if( isReceive && isConnect && (keyCode == KeyEvent.VK_TAB)) {
					// 커서 위치를 원래 위치로 변경
					try {
					// tab키로 인한 공백 제거
					textAreaMaker.getTextArea().replaceRange("", position, position+1);
			            
			        caretpos = textAreaMaker.getTextArea().getCaretPosition();
					}
					catch(Exception ex){
			    	ex.printStackTrace();
					}
					
					rectangle = new Rectangle();
					try {
						rectangle = ((JTextArea)(e.getSource())).modelToView2D(caretpos); // 커서 위치를 x, y로 변경
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					
					// textArea가 수정된 적이 없을 때의 popupmenu 출력
					if(textAreaMaker.getTextArea().getParent().getX() <= 15) {
						scrollPopupmenu.show(textAreaMaker.getTextArea(), (int)rectangle.getX() + 43 , (int)rectangle.getY() + 20);
					}
					else {
						// popupmenu가 나타날 위치 설정, tab키를 누른 위치
						scrollPopupmenu.show(textAreaMaker.getTextArea(), (int)rectangle.getX() + 3 , (int)rectangle.getY() + 20);
					}
				}
			}
		});
		textAreaMaker.getTextArea().setComponentPopupMenu(popupmenu);
	}
}

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
					
					if(isConnect) {
						
						isReceive = SC.getIsReceive();
						position = SC.getPosition();
						state_receive = SC.getState();
						
						ArrayList<String> list = SC.getList();
						ArrayList<Integer> cursorList = new ArrayList<>();
						
						int i;
						// 서버로부터 파싱 상태만 받아온다면 i = 0, 아니라면 i = 1
						if(state_receive) i = 0;
						else {
							i = 1;
							cursorList.add(0);
						}
						
						// popupmenu에 문자열 추가
						for(; i < list.size(); i++) {
							// 만약 서버로부터 파싱 상태만 받아온다면
							JMenuItem menuitem;
							if(state_receive) {
								// Terminal, Nonterminal 치환
								list.set(i, list.get(i).replaceAll("CRStmtCRs", "Enter Enter"));
								list.set(i, list.get(i).replaceAll("CR", "Enter Enter"));
								list.set(i, list.get(i).replaceAll("NT\\s(.*?) ", "blank"));
								list.set(i, list.get(i).replaceAll("T ", ""));
								
								// 커서 Nonterminal 위치로 변경
								list.set(i, list.get(i).replaceAll("\\s+", " "));
								list.set(i, list.get(i).replace("Enter ", System.getProperty("line.separator")));
								
								list.set(i, list.get(i).replaceAll("[.] ", "."));
								list.set(i, list.get(i).replaceAll(" [.]", "."));
								
								int setcursor = list.get(i).indexOf("blank");
								list.set(i, list.get(i).replaceAll("blank", ""));
								
								cursorList.add(setcursor);
								
								menuitem = new JMenuItem(list.get(i));
							} 
							else {
								// 서버로부터 문자열로 후보를 받아온다면
								// "..."이 있으면 처음 "..." 위치로 커서 위치 변경
								list.set(i, list.get(i).replace("...", "blank"));
								list.set(i, list.get(i).replaceAll("\\s", ""));
								
								int setcursor = list.get(i).indexOf("blank");
								cursorList.add(setcursor);
								
								menuitem = new JMenuItem(list.get(i));
							}
								
							int stridx = list.get(i).length();
								
							String itemHeader = list.get(i).substring(0, stridx);
							
							menuitem.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									cursorPosition = textAreaMaker.getTextArea().getCaretPosition();
									textAreaMaker.getTextArea().insert(itemHeader, position);
									// textAreaMaker.getTextArea().append(itemHeader);
									int listIndex = list.indexOf(itemHeader);
									if(cursorList.get(listIndex) != -1) {
										textAreaMaker.getTextArea().setCaretPosition(position + cursorList.get(listIndex));
									}
									
									// 선택한 구문 후보 중 ID, NUM, STR이 존재하면 사용자로부터 문자열을 입력받는다.
									String listStr = list.get(listIndex);
									
									int matcherIdx = 10;	// 문자열 위치
									boolean flag = true;	
									String input = "input"; 	// 사용자 입력
									String pattern = null; 	// 정규식
									String matcherName = null; // 정규식에 대한 문자열(ID, NUM, STR)
									
									while (flag && input != null) {
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
											if(flag) {
												input = JOptionPane.showInputDialog(matcherName + ": " + pattern);
												while(input != null) {
													// 정규식에 맞게 입력되면 해당 위치에 삽입
													if(Pattern.matches(pattern, input)) {
														cursorPosition += matcherIdx;
														textAreaMaker.getTextArea().replaceRange(input, cursorPosition, cursorPosition + matcherName.length());
														
														listStr = listStr.replaceFirst(matcherName, input);
														break;
													}
													input = JOptionPane.showInputDialog(matcherName + ": " + pattern);
												}
											}
										}
									}
								}
									
							}); // end ActionListener
							popupmenu.add(menuitem);
							scrollPopupmenu.addImpl(menuitem, scrollPopupmenu, i);
								
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
			            
			        lineNum = textAreaMaker.getTextArea().getLineOfOffset(caretpos);
			        columnNum = caretpos - textAreaMaker.getTextArea().getLineStartOffset(lineNum) + 1;
			        lineNum += 1;
			            
					}
					catch(Exception ex){
			    	ex.printStackTrace();
					}
					
					rectangle = new Rectangle();
					try {
						rectangle = ((JTextArea)(e.getSource())).modelToView2D(caretpos);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
					// scrollPopupmenu.show(textAreaMaker.getTextArea(), (int)(columnNum * 5) + 25, lineNum * 15);
					scrollPopupmenu.show(textAreaMaker.getTextArea(), (int)rectangle.getX() + 3 , (int)rectangle.getY() + 20);
				}
			}
		});
		textAreaMaker.getTextArea().setComponentPopupMenu(popupmenu);
	}
}

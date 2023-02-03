package com.coducation.smallbasic.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

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
						//cursorList.add(0);
						
						int i;
						// 서버로부터 파싱 상태만 받아온다면 i = 0, 아니라면 i = 1
						if(state_receive) i = 0;
						else i = 1;
						
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
								list.set(i, list.get(i).replaceAll("\\s", ""));
								int setcursor = list.get(i).indexOf("blank");
								list.set(i, list.get(i).replaceAll("blank", ""));
								
								cursorList.add(setcursor);
								
								list.set(i, list.get(i).replace("Enter", System.getProperty("line.separator")));
								
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
									textAreaMaker.getTextArea().append(itemHeader);
									int listIndex = list.indexOf(itemHeader);
									if(cursorList.get(listIndex) != -1) {
										textAreaMaker.getTextArea().setCaretPosition(position + cursorList.get(listIndex));
									}
									
									// 선택한 구문 후보 중 ID, NUM, STR이 존재하면 사용자로부터 문자열을 입력받는다.
									PriorityQueue<Integer> priorityQueueLowest = new PriorityQueue<>();
									int wordIdx;
									if((wordIdx = list.get(listIndex).indexOf("ID")) >= 0) priorityQueueLowest.add(wordIdx);
									if((wordIdx = list.get(listIndex).indexOf("NUM")) >= 0) priorityQueueLowest.add(wordIdx);
									if((wordIdx = list.get(listIndex).indexOf("STR")) >= 0) priorityQueueLowest.add(wordIdx);
									
									//while(priorityQueueLowest.size() > 0) {
										// 앞에 위치해 있는 단어부터 입력받는다.
										
										
									//}
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
				int keyCode = e.getKeyCode();
				if( isReceive && isConnect && (keyCode == KeyEvent.VK_TAB)) {
					// 커서 위치를 원래 위치로 변경
					try {
					// tab키로 인한 공백 제거
					textAreaMaker.getTextArea().replaceRange("", position, position+1);
			            
			        int caretpos = textAreaMaker.getTextArea().getCaretPosition();
			            
			        lineNum = textAreaMaker.getTextArea().getLineOfOffset(caretpos);
			        columnNum = caretpos - textAreaMaker.getTextArea().getLineStartOffset(lineNum) + 1;
			        lineNum += 1;
			            
					}
					catch(Exception ex){
			    	ex.printStackTrace();
					}
				scrollPopupmenu.show(textAreaMaker.getTextArea(), (int)(columnNum * 5) + 25, lineNum * 15);
				}
			}
		});
		textAreaMaker.getTextArea().setComponentPopupMenu(popupmenu);
	}
}

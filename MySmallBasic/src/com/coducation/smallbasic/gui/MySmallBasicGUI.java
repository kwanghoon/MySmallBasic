package com.coducation.smallbasic.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import com.coducation.smallbasic.syncomp.SocketCommunication;

public class MySmallBasicGUI extends JFrame implements MySmallBasicDebuggerClientModel {
	private static JPanel contentPane;
	private static MySmallBasicGUI frame;
	private JButton newButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton saveAsButton;
	private JButton runButton;
	private JButton debugButton;
	private TextAreaMaker textAreaMaker;
	private JPopupMenu popupmenu;
	private JScrollPopupMenu scrollPopupmenu;
	private MySmallBasicSyntaxItems syntaxItems;

	// 디버그 관련 변수
	private MySmallBasicDebugger debugger;
	private Thread debuggerThread;
	private JPanel debugPanel;
	private JToolBar debugToolBar;
	private MonitoringTable monitoringTable;
	
	// 저장관련 변수
	private boolean isTempFile = true; // 임시파일인지
	private boolean isNew = true;		//새로 만든 파일인지
	private String filePath = TEMP_PATH; // 파일의 경로
	private boolean isTextAreaChanged = false;

	// blockly 관련 변수
	private JButton blockButton;
	private final static File path = new File("blockly/demos/code/index.html");


	// 나중에 반드시 임시로 저장할 경로를 설정해주세요!
	final static String TEMP_PATH = System.getProperty("user.dir") + "/resource/tmp.sb";

	public static void main(String[] args) {
		MySmallBasicGUI frame = new MySmallBasicGUI();
		frame.setVisible(true);
	}

	public MySmallBasicGUI() {
		
		//gui 창 종료시 이벤트
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				if (debugger != null)
					debugger.exit();
				System.exit(0);
			}
		});
		setTitle("MySmallBasic");
		setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "/resource/GUI/SB_icon.png"));
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// 텍스트 편집창
		textAreaMaker = new TextAreaMaker(contentPane, this);
		textAreaMaker.getTextArea().getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				isTextAreaChanged = true;
			}

			public void insertUpdate(DocumentEvent arg0) {
				isTextAreaChanged = true;
			}

			public void removeUpdate(DocumentEvent arg0) {
				isTextAreaChanged = true;
			}
		});
		
		// 서버 연결 확인 후 textArea에 PopupMenu 추가
		syntaxItems = new MySmallBasicSyntaxItems(contentPane, textAreaMaker);
		
		// 툴바가 들어갈 panel 설정
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		toolBar.setToolTipText("");

		
		// 버튼추가
		
		// new button
		newButton = addButton("새로만들기", "/resource/GUI/new.png", toolBar, 50);
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (debugger != null)
					return;

				textAreaMaker.getTextArea().selectAll();
				textAreaMaker.getTextArea().replaceSelection("");

				filePath = TEMP_PATH; // 임시 tmp 파일경로

				isTempFile = true;
				isNew = true;
				isTextAreaChanged = true;

				textAreaMaker.clearBreakPointInfo();
			}
		});

		// open button
		openButton = addButton("열기", "/resource/GUI/open.png", toolBar, 50);
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (debugger != null)
					return;

				// 파일선택창
				FileDialog dialog = new FileDialog(frame, "열기", FileDialog.LOAD);
				dialog.setDirectory(".");
				dialog.setVisible(true);
				if (dialog.getFile() == null)
					return;
				filePath = dialog.getDirectory() + dialog.getFile();

				try(FileReader fr = new FileReader(filePath);
					BufferedReader reader = new BufferedReader(fr)) {
					JTextArea textArea = textAreaMaker.getTextArea();
					textArea.setText("");
					String line;
					while ((line = reader.readLine()) != null) {
						textArea.append(line + "\n");
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				isTempFile = false;
				isNew = false;
				isTextAreaChanged = false;

				textAreaMaker.clearBreakPointInfo();
			}
		});

		// save button
		saveButton = addButton("저장", "/resource/GUI/save.png", toolBar, 50);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debugger != null)
					return;

				save();
			}
		});

		// saveAs button
		saveAsButton = addButton("다른 이름으로 저장", "/resource/GUI/saveAs.png", toolBar, 50);
		saveAsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debugger != null)
					return;

				saveAs();
			}
		});

		toolBar.addSeparator();

		// 세로로 표현하기 위해 패널에 버튼을 추가
		JPanel webPanel = new JPanel();
		toolBar.add(webPanel);
		webPanel.setLayout(new GridLayout(2, 1, 0, 0));
		// fetch button
		JButton fetchButton = new JButton("가져오기");
		fetchButton.setIcon(resizeImg(System.getProperty("user.dir") + "/resource/GUI/import.png", 25, 25));
		fetchButton.setIconTextGap(2);
		webPanel.add(fetchButton);
		// publish button
		JButton publishButton = new JButton("출판");
		publishButton.setIcon(resizeImg(System.getProperty("user.dir") + "/resource/GUI/export.png", 25, 25));
		publishButton.setIconTextGap(2);
		webPanel.add(publishButton);

		toolBar.addSeparator();

		// cut button
		JButton cutButton = addButton("잘라내기", "/resource/GUI/cut.png", toolBar, 50);
		cutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debugger != null)
					return;

				textAreaMaker.getTextArea().cut();
				textAreaMaker.getTextArea().repaint();
			}
		});
		// copy button
		JButton copyButton = addButton("복사", "/resource/GUI/copy.png", toolBar, 50);
		copyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debugger != null)
					return;

				textAreaMaker.getTextArea().copy();
				textAreaMaker.getTextArea().repaint();
			}
		});
		// paste button
		JButton pasteButton = addButton("붙이기", "/resource/GUI/paste.png", toolBar, 50);
		pasteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debugger != null)
					return;

				textAreaMaker.getTextArea().paste();
				textAreaMaker.getTextArea().repaint();
			}
		});

		// undo and redo
		// 세로로 정렬하기 위해 패널에 추가
		JPanel doPanel = new JPanel();
		doPanel.setLayout(new GridLayout(2, 1, 0, 0));
		toolBar.add(doPanel);
		
		JButton undoButton = new JButton("되돌리기");
		undoButton.setIcon(resizeImg(System.getProperty("user.dir") + "/resource/GUI/undo.png", 25, 25));
		undoButton.setIconTextGap(2);
		doPanel.add(undoButton);

		JButton redoButton = new JButton("다시하기");
		redoButton.setIcon(resizeImg(System.getProperty("user.dir") + "/resource/GUI/redo.png", 25, 25));
		redoButton.setIconTextGap(2);
		doPanel.add(redoButton);

		// redo, undo 기능구현
		UndoManager undoManager = new UndoManager();
		textAreaMaker.getTextArea().getDocument().addUndoableEditListener(new UndoableEditListener() {
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
				undoButton.setEnabled(undoManager.canUndo());
				redoButton.setEnabled(undoManager.canRedo());
			}
		});
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					undoManager.undo();
				} catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
				undoButton.setEnabled(undoManager.canUndo());
				redoButton.setEnabled(undoManager.canRedo());
			}
		});
		redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					undoManager.redo();
				} catch (CannotRedoException cre) {
					cre.printStackTrace();
				}
				undoButton.setEnabled(undoManager.canUndo());
				redoButton.setEnabled(undoManager.canRedo());
			}
		});

		toolBar.addSeparator();

		// run button
		runButton = addButton("실행", "/resource/GUI/play.png", toolBar, 50);
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debugger != null)
					return;

				fileCheckForRun();
				new MySmallBasicDebugger(filePath, false, null, null);
			}
		});

		// debug button
		debugButton = addButton("디버그", "/resource/GUI/debug.png", toolBar, 50);
		debugButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debugger != null)
					return;

				debugPanel = new JPanel();
				debugPanel.setLayout(new BorderLayout());

				// 디버그 툴바 추가
				debugToolBar = new JToolBar();
				debugToolBar.setToolTipText("디버그 메뉴");
				debugPanel.add(debugToolBar, BorderLayout.NORTH);
				debugToolBar.setSize(1024, 20);

				// 변수 모니터링 창 추가
				monitoringTable = new MonitoringTable();
				debugPanel.add(monitoringTable, BorderLayout.CENTER);
				contentPane.add(debugPanel, BorderLayout.EAST);

				// 디버그관련 버튼
				JButton stepButton = addButton("다음줄", "/resource/GUI/step_over.png", debugToolBar, 20);
				stepButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						synchronized (debuggerThread) {
							textAreaMaker.removeHightLightLine();
							debugger.step();
							debuggerThread.notify();
						}
					}
				});
				JButton continueButton = addButton("계속", "/resource/GUI/step_into.png", debugToolBar, 20);
				continueButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						synchronized (debuggerThread) {
							textAreaMaker.removeHightLightLine();
							debugger.continueDebugging();
							debuggerThread.notify();
						}
					}
				});
				JButton exitButton = addButton("종료", "/resource/GUI/debugExit.png", debugToolBar, 20);
				exitButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textAreaMaker.removeHightLightLine();
						debugger.exit();
					}
				});

				debugModeRun();
			}
		});

		//block button
		/* 오픈소프트웨어 개발자 대회출품을 위해 잠시 주석처리합니다
		blockButton = addButton("block", "/resource/Blockly/block.png", toolBar, 50);
		blockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (debugger != null)
					return;

				try { 
					Desktop.getDesktop().browse(path.toURI()); 
				} catch (IOException e1) { 
					e1.printStackTrace(); 
				}
			}
		});
		*/
		setSize(1024, 800);
	}

	// 이미지 크기 변경
	private ImageIcon resizeImg(String path, int width, int height) {
		ImageIcon originIcon = new ImageIcon(path);
		Image changedImg = originIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(changedImg);
	}

	// 툴바에 버튼 추가
	private JButton addButton(String text, String path, JToolBar toolBar, int size) {
		JButton button = new JButton(text);
		button.setIcon(resizeImg(System.getProperty("user.dir") + path, size, size));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setIconTextGap(2);
		toolBar.add(button);

		return button;
	}

	// save function
	private void save() {
		// 임시 파일-다른이름으로 저장 필요
		if (isTempFile) {
			saveAs();
		}
		// 기존 경로가 있는 파일
		else {
			saveFile(filePath);
		}

		isTextAreaChanged = false;
	}

	// saveAs function
	private void saveAs() {
		// 경로 선택창
		FileDialog dialog = new FileDialog(frame, "다른 이름으로 저장", FileDialog.SAVE);
		dialog.setDirectory(".");
		dialog.setVisible(true);
		if (dialog.getFile() == null)
			return;
		filePath = dialog.getDirectory() + dialog.getFile();

		saveFile(filePath);

		isTextAreaChanged = false;
		isTempFile = false;
		isNew = false;
	}
	//save file
	private void saveFile(String fileName){		
		try(FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osr = new OutputStreamWriter(fos, "utf-8");
			BufferedWriter writer = new BufferedWriter(osr)) {
			writer.write(textAreaMaker.getTextArea().getText());
		} catch (Exception e2) {
			e2.printStackTrace();
		}		
	}

	private void fileCheckForRun() {

		// 내용이 변경되었으면 저장
		if (isTextAreaChanged || isNew) {
			// 임시 파일로 작성한 경우
			if (isTempFile) {
				saveFile(filePath);

				isTextAreaChanged = false;
				isNew = false;
			}
			// 변경된 내용이 있으면 저장
			else
				save();
		}
	}

	// 디버깅 모드를 위해 필요한 메소드
	@Override
	public void debugModeRun() {
		fileCheckForRun();

		textAreaMaker.getTextArea().setEditable(false);
		debugger = new MySmallBasicDebugger(filePath, true, this, textAreaMaker.getBreakPoints());
		debuggerThread = new Thread(debugger);
		debuggerThread.start();
	}

	@Override
	public void normalReturn() {

		textAreaMaker.removeHightLightLine();
		textAreaMaker.getTextArea().setEditable(true);

		debugger = null;
		debuggerThread.interrupt();

		// 디버그관련 gui 제거
		contentPane.remove(debugPanel);
		debugToolBar = null;
		monitoringTable = null;

		contentPane.revalidate();
		contentPane.repaint();

	}

	@Override
	public void abnormalReturn() {
		// 비정상 예외 정보 알려주기 추가할것...
		System.out.println("abnormal return");

		textAreaMaker.removeHightLightLine();
		textAreaMaker.getTextArea().setEditable(true);

		debugger = null;
		debuggerThread.interrupt();

		// 디버그관련 gui 제거
		contentPane.remove(debugPanel);
		debugToolBar = null;
		monitoringTable = null;

		contentPane.revalidate();
		contentPane.repaint();

	}

	@Override
	public void stopState(int stopLine, HashMap<String, String> variableMap) {

		textAreaMaker.hightLightLine(stopLine);
		monitoringTable.renewValueInfo(variableMap);
		try {
			synchronized (debuggerThread) {
				debuggerThread.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.coducation.smallbasic.gui;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.coducation.smallbasic.MySmallBasicMain;

public class SmallBasicGUI extends JFrame {

	private static JPanel contentPane;
	private static SmallBasicGUI frame;
	private JButton newButton;
	private JButton openButton;
	private JButton saveButton;
	private JButton saveAsButton;
	private JButton runButton;
	private JTextArea textArea;
	private TextAreaMaker textAreaMaker;

	// 저장관련 변수
	private boolean isNew = true; // 새로운 파일인지? 경로가 있는 파일인지
	private boolean isTemp = true; // 임시파일인가?
	private String filePath = TEMP_PATH; // 파일의 경로

	final static String TEMP_PATH = System.getProperty("user.dir") + "/resource/tmp.sb";

	public static void main(String[] args) {
		SmallBasicGUI frame = new SmallBasicGUI();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public SmallBasicGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		toolBar.setToolTipText("");
		contentPane.add(toolBar, BorderLayout.NORTH);

		// 파일
		// new button
		newButton = addButton("새로만들기", "/resource/GUI/new.png", toolBar, 50);
		// open button
		openButton = addButton("열기", "/resource/GUI/open.png", toolBar, 50);
		// save button
		saveButton = addButton("저장", "/resource/GUI/save.png", toolBar, 50);
		// saveAs button
		saveAsButton = addButton("다른 이름으로 저장", "/resource/GUI/saveAs.png", toolBar, 50);

		toolBar.addSeparator();

		// 세로로 표현하기 위해 패널에 버튼을 추가
		JPanel webPanel = new JPanel();
		toolBar.add(webPanel);
		webPanel.setLayout(new GridLayout(2, 1, 0, 0));
		// fetch button - 이미지 적당한거 못찾음
		JButton fetchButton = new JButton("가져오기");
		fetchButton.setIcon(resizeImg(System.getProperty("user.dir") + "/resource/GUI/saveAs.png", 25, 25));
		fetchButton.setIconTextGap(2);
		webPanel.add(fetchButton);
		// publish button - 이미지 적당한 거 못찾음
		JButton publishButton = new JButton("출판");
		publishButton.setIcon(resizeImg(System.getProperty("user.dir") + "/resource/GUI/saveAs.png", 25, 25));
		publishButton.setIconTextGap(2);
		webPanel.add(publishButton);

		toolBar.addSeparator();

		// cut button
		JButton cutButton = addButton("잘라내기", "/resource/GUI/cut.png", toolBar, 50);
		// copy button
		JButton copyButton = addButton("복사", "/resource/GUI/copy.png", toolBar, 50);
		// paste button
		JButton pasteButton = addButton("붙이기", "/resource/GUI/paste.png", toolBar, 50);

		// 세로로 정렬하기 위해 패널에 추가
		// undo and redo
		JPanel doPanel = new JPanel();
		doPanel.setLayout(new GridLayout(2, 1, 0, 0));
		toolBar.add(doPanel);
		// button - 이미지 적당한거 못찾음
		JButton undoButton = new JButton("취소");
		undoButton.setIcon(resizeImg(System.getProperty("user.dir") + "/resource/GUI/undo.png", 25, 25));
		undoButton.setIconTextGap(2);
		doPanel.add(undoButton);

		JButton redoButton = new JButton("다시");
		redoButton.setIcon(resizeImg(System.getProperty("user.dir") + "/resource/GUI/redo.png", 25, 25));
		redoButton.setIconTextGap(2);
		doPanel.add(redoButton);

		toolBar.addSeparator();

		// run button
		runButton = addButton("실행", "/resource/GUI/play.png", toolBar, 50);

		// textArea와 lineNumber, scroll을 만들어서 인자로 넣어준 패널에 추가함
		// 아래처럼 getTextArea해서 얻어와서 다른거 구현하면됨
		textAreaMaker = new TextAreaMaker(contentPane);
		textArea = textAreaMaker.getTextArea();

		setSize(1024, 800);
	}

	public ImageIcon resizeImg(String path, int width, int height) {
		ImageIcon originIcon = new ImageIcon(path);
		Image changedImg = originIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

		return new ImageIcon(changedImg);
	}

	// 버튼
	public JButton addButton(String text, String path, JToolBar toolBar, int size) {
		JButton button = new JButton(text);
		button.setIcon(resizeImg(System.getProperty("user.dir") + path, size, size));
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setIconTextGap(2);
		toolBar.add(button);

		// 버튼기능
		button.addActionListener(new ButtonEvent());

		return button;
	}

	//버튼 이벤트
	class ButtonEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			// 새로만들기
			if (e.getSource().equals(newButton)) {
				textArea.selectAll();
				textArea.replaceSelection("");

				// 임시 tmp 파일경로...
				filePath = TEMP_PATH;

				// 저장관련 변수
				isTemp = true;
				isNew = true;
			}

			// 저장
			else if (e.getSource().equals(saveButton)) {
				save();
			}

			// 다른이름으로 저장
			if (e.getSource().equals(saveAsButton)) {
				saveAs();

				isTemp = false;
				isNew = false;
			}

			// 열기
			else if (e.getSource().equals(openButton)) {
				// 파일선택창
				FileDialog dialog = new FileDialog(frame, "열기", FileDialog.LOAD);
				dialog.setDirectory(".");
				dialog.setVisible(true);
				if (dialog.getFile() == null)
					return;
				filePath = dialog.getDirectory() + dialog.getFile();

				try {
					BufferedReader reader = new BufferedReader(new FileReader(filePath));
					textArea.setText("");
					String line;
					while ((line = reader.readLine()) != null) {
						textArea.append(line + "\n");
					}
					reader.close();
				} catch (Exception e2) {
				}

				// 저장관련 변수 설정
				isTemp = false;
				isNew = false;
			}

			// 실행
			else if (e.getSource().equals(runButton)) {
				// 임시 파일로 작성한 경우.. 따로 저장안하고 실행
				if (isTemp) {
					try {
						BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
						writer.write(textArea.getText());
						writer.close();
					} catch (Exception e2) {
					}
				}
				// 변경된 내용이 있으면 저장
				else
					save();

				try {
					init();

					ProcessBuilder pb = new ProcessBuilder(shellCmd, "/c", "start java.exe", javaCmd, "-gui", filePath);

					for (String c : pb.command())
						System.out.println(c);

					Map<String, String> env = pb.environment();

					classpath.append(HOME + "/bin");
					addJarFile(HOME + "/lib");

					env.put("CLASSPATH", classpath.toString());

					for (Map.Entry<String, String> entry : env.entrySet()) {
						System.out.println(entry.getKey() + " : " + entry.getValue());
					}

					pb.directory(new File(HOME));
					Process p = pb.start();

					// p.waitFor();

					InputStream is = p.getErrorStream();
					Scanner scan = new Scanner(is);
					while (scan.hasNext()) {
						System.out.print("ERROR: ");
						System.out.println(scan.nextLine());
					}

					System.out.println("Exit value: " + p.exitValue());
				}
				// 예외를 던지게 짜여있으므로 일단 잡음
				catch (IOException e1) {
					e1.printStackTrace();
				}

			}

		}

	}

	// 저장하는 기능
	private void save() {
		// 새로운 파일-다른이름으로 저장 필요
		if (isNew) {
			saveAs();
		}
		// 기존 경로가 있는 파일
		else {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
				writer.write(textArea.getText());
				writer.close();
			} catch (Exception e2) {
			}
		}
	}

	// 다른이름으로 저장하는 기능
	private void saveAs() {
		// 경로 선택창
		FileDialog dialog = new FileDialog(frame, "다른 이름으로 저장", FileDialog.SAVE);
		dialog.setDirectory(".");
		dialog.setVisible(true);
		if (dialog.getFile() == null)
			return;
		filePath = dialog.getDirectory() + dialog.getFile();

		// 쓰기
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
			writer.write(textArea.getText());
			writer.close();
		} catch (Exception e2) {
		}
	}

	// 다른 프로세스로 실행하기 위해 필요한 것
	private static String shellCmd, HOME, javaCmd, cwd;
	private static StringBuilder classpath = new StringBuilder();

	public static void init() {

		String osName = System.getProperty("os.name");
		String osNameMatch = osName.toLowerCase();

		//HOME = "C:/Users/user/git/MySmallBasic/MySmallBasic";
		 HOME = "./";
		javaCmd = "com.coducation.smallbasic.MySmallBasicMain";

		shellCmd = "";

		if (osNameMatch.contains("linux")) {
			shellCmd = "";
		} else if (osNameMatch.contains("windows")) {
			shellCmd = "cmd.exe";
		} else if (osNameMatch.contains("mac os") || osNameMatch.contains("macos") || osNameMatch.contains("darwin")) {
			shellCmd = "";
		} else {
			shellCmd = ""; // Windows OS by default
		}

		cwd = System.getProperty("user.dir");
	}
	public static void addJarFile(String path) {

		File jar = new File(path);

		if (jar.isDirectory() == true) {
			File[] jars = jar.listFiles();
			
			for (int i = 0; i < jars.length; i++) {
				if (jars[i].isDirectory() == true) { // 폴더일 경우
					addJarFile(path + "\\" + jars[i].getName()); // 재귀호출
				}
				else {
					if (jars[i].getName().endsWith(".jar")) {
						classpath.append(";");
						classpath.append(HOME + "/lib/" + jars[i].getName());
					}
				}
			}
		}
	}
}

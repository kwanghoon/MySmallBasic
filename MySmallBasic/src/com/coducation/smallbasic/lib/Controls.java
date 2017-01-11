package com.coducation.smallbasic.lib;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Controls {
	static {
		controls = new HashMap<String, JComponent>();
	}

	public static void AddButton(ArrayList<Value> args) {
		// caption, left, top
		// GraphicsWindow에 버튼을 추가한 창을 반환

		if (args.size() == 3) {
			JButton btn;
			int left, top;

			// caption check
			if (args.get(0) instanceof StrV || args.get(0) instanceof DoubleV)
				btn = new JButton(args.get(0).toString());
			else
				throw new InterpretException("Unexpected type " + args.get(0));

			// left check(x co-ordinate)
			if (args.get(1) instanceof DoubleV) {
				left = (int) ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				left = (int) ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));

			// top check(y co-ordinate)
			if (args.get(2) instanceof DoubleV) {
				top = (int) ((DoubleV) args.get(2)).getValue();
			} else if (args.get(2) instanceof StrV && ((StrV) args.get(2)).isNumber()) {
				top = (int) ((StrV) args.get(2)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(2));

			Font font = GraphicsWindow.settingFont();
			btn.setFont(font);
			btn.setSize(btn.getPreferredSize());
			btn.setLocation(left, top);

			// HashMap에도 추가하는 부분 넣기
			panel.add(btn);

		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetButtonCaption(ArrayList<Value> args) {
		// buttonName
		// buttonName에 해당하는 버튼의 caption을 반환

		if (args.size() == 1) {
			String buttonName;

			if (args.get(0) instanceof StrV) {
				// controls가 비어있는 경우 처리
				// controls에 buttonName이 존재하지 않는 경우처리
				// contorls에 buttonName이 존재하지만 Button이 아닌 경우 처리
				// controls에 buttonName이 존재하고 Button인 경우 처리
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			return null;
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetButtonCaption(ArrayList<Value> args) {
		// buttonName, caption
		// buttonName에 해당하는 버튼의 caption을 설정함, 반환값 없음

		if (args.size() == 2) {
			String caption;
			if (args.get(0) instanceof StrV) {
				// controls의 크기가 0인 경우
				// buttonName이 controls에 존재하지 않는 경우
				// buttonName의 controls가 존재하지만 button이 아닌 경우
				// buttonName의 controls가 존재하고 button인 경우

			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof StrV) {
				caption = ((StrV) args.get(1)).getValue();
			} else if (args.get(1) instanceof DoubleV) {
				caption = ((DoubleV) args.get(1)).toString();
			}

			else
				throw new InterpretException("Unexpected type " + args.get(1));
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void AddTextBox(ArrayList<Value> args) {
		// left, top
		// GraphicsWindow에 텍스트박스를 추가한 창을 반환
		if (args.size() == 2) {
			JTextField tf;
			int left, top;

			// left check(x co-ordinate)
			if (args.get(0) instanceof DoubleV) {
				left = (int) ((DoubleV) args.get(0)).getValue();
			} else if (args.get(0) instanceof StrV && ((StrV) args.get(0)).isNumber()) {
				left = (int) ((StrV) args.get(0)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			// top check(y co-ordinate)
			if (args.get(1) instanceof DoubleV) {
				top = (int) ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				top = (int) ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));

			Font font = GraphicsWindow.settingFont();
			tf = new JTextField();
			tf.setFont(font);
			tf.setSize(160, 20);
			tf.setLocation(left, top);

			// HashMap에도 tf 추가하는 부분 넣기
			panel.add(tf);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void AddMultiLineTextBox(ArrayList<Value> args) {
		// left, top
		// GraphicsWiindow에 텍스트박스를 추가한 창을 반환
		if (args.size() == 2) {
			JTextArea ta;
			JScrollPane scroll;
			int left, top;

			if (args.get(0) instanceof DoubleV) {
				left = (int) ((DoubleV) args.get(0)).getValue();
			} else if (args.get(0) instanceof StrV && ((StrV) args.get(0)).isNumber()) {
				left = (int) ((StrV) args.get(0)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {
				top = (int) ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				top = (int) ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));

			Font font = GraphicsWindow.settingFont();
			ta = new JTextArea(5, 27);
			ta.setFont(font);
			ta.setLocation(left, top);
			scroll = new JScrollPane(ta);

			// controls에 추가
			panel.add(scroll);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetTextBoxText(ArrayList<Value> args) {
		// textBoxName
		// textBoxName에 해당하는 텍스트 박스의 내용을 반환
		if (args.size() == 1) {
			if (args.get(0) instanceof StrV) {

			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			return null;
		} else
			throw new InterpretException("Unexpected # of args " + args.size());

	}

	public static void SetTextBoxText(ArrayList<Value> args) {
		// textBoxName, text
		// textBoxName에 해당하는 텍스트 박스의 내용을 text로 설정함
		if (args.size() == 2) {

			if (args.get(0) instanceof StrV) {

			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof StrV) {

			} else if (args.get(1) instanceof DoubleV) {

			} else
				throw new InterpretException("Unexpected type " + args.get(1));
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Remove(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 것을 삭제함, 반환값 없음
		if (args.size() == 1) {
			if (args.get(0) instanceof StrV) {

			} else
				throw new InterpretException("Unexpected type " + args.get(0));
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Move(ArrayList<Value> args) {
		// control, x, y
		// control을 새로운 x, y로 옮김
		if (args.size() == 3) {
			if (args.get(0) instanceof StrV) {

			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {

			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {

			} else
				throw new InterpretException("Unexpected type " + args.get(1));

			if (args.get(2) instanceof DoubleV) {

			} else if (args.get(2) instanceof StrV && ((StrV) args.get(2)).isNumber()) {

			} else
				throw new InterpretException("Unexpected type " + args.get(2));
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetSize(ArrayList<Value> args) {
		// control, width, height
		// control의 너비와 높이를 width와 height로 설정
		if (args.size() == 3) {

			if (args.get(0) instanceof StrV) {

			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {

			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {

			} else
				throw new InterpretException("Unexpected type " + args.get(1));

			if (args.get(2) instanceof DoubleV) {

			} else if (args.get(2) instanceof StrV && ((StrV) args.get(2)).isNumber()) {

			} else
				throw new InterpretException("Unexpected type " + args.get(2));
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void HideControl(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 control을 숨김
		if (args.size() == 1) {
			if (args.get(0) instanceof StrV) {

			} else
				throw new InterpretException("Unexpected type " + args.get(0));
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void ShowControl(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 control을 보임
		if (args.size() == 1) {
			if (args.get(0) instanceof StrV) {

			} else
				throw new InterpretException("Unexpected type " + args.get(0));
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	private static HashMap<String, JComponent> controls;

	private static GraphicsWindow.Panel panel;

	public static Value LastClickedButton;
	public static Value LastTypedTextBox;

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
}

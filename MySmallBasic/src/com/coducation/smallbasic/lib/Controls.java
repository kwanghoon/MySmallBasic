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
import com.sun.org.apache.xpath.internal.Arg;

public class Controls {
	public static Value AddButton(ArrayList<Value> args) {
		// caption, left, top
		// GraphicsWindow에 버튼을 추가한 창을 반환

		if (args.size() == 3) {
			String caption;
			int left, top;

			// caption check
			if (args.get(0) instanceof StrV || args.get(0) instanceof DoubleV)
				caption = args.get(0).toString();
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

			String id = GraphicsWindow.AddButton(caption, left, top);
			
			return new StrV(id);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetButtonCaption(ArrayList<Value> args) {
		// buttonName
		// buttonName에 해당하는 버튼의 caption을 반환

		if (args.size() == 1) {
			String buttonName;

			if (args.get(0) instanceof StrV) {
				buttonName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			String caption = GraphicsWindow.GetButtonCaption(buttonName);
			
			return new StrV(caption);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetButtonCaption(ArrayList<Value> args) {
		// buttonName, caption
		// buttonName에 해당하는 버튼의 caption을 설정함, 반환값 없음

		if (args.size() == 2) {
			String buttonName;
			String caption;
			if (args.get(0) instanceof StrV) {
				buttonName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof StrV) {
				caption = ((StrV) args.get(1)).getValue();
			} else if (args.get(1) instanceof DoubleV) {
				caption = ((DoubleV) args.get(1)).toString();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));
			
			GraphicsWindow.SetButtonCaption(buttonName, caption);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value AddTextBox(ArrayList<Value> args) {
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

			String id = GraphicsWindow.AddTextBox(left, top);
			
			return new StrV(id);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value AddMultiLineTextBox(ArrayList<Value> args) {
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

			String id = GraphicsWindow.AddMultiLineTextBox(left, top);
			
			return new StrV(id);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetTextBoxText(ArrayList<Value> args) {
		// textBoxName
		// textBoxName에 해당하는 텍스트 박스의 내용을 반환
		if (args.size() == 1) {
			String textBoxName;
			if (args.get(0) instanceof StrV) {
				textBoxName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			String text = GraphicsWindow.GetTextBoxText(textBoxName);
			
			return new StrV(text);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());

	}

	public static void SetTextBoxText(ArrayList<Value> args) {
		// textBoxName, text
		// textBoxName에 해당하는 텍스트 박스의 내용을 text로 설정함
		if (args.size() == 2) {
			String textBoxName;
			String text;
			if (args.get(0) instanceof StrV) {
				textBoxName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof StrV) {
				text = ((StrV) args.get(1)).getValue();
			} else if (args.get(1) instanceof DoubleV) {
				text = ((DoubleV) args.get(1)).toString();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));
			
			GraphicsWindow.SetTextBoxText(textBoxName, text);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Remove(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 것을 삭제함, 반환값 없음
		if (args.size() == 1) {
			String controlName;
			if (args.get(0) instanceof StrV) {
				controlName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			GraphicsWindow.ControlsRemove(controlName);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Move(ArrayList<Value> args) {
		// control, x, y
		// control을 새로운 x, y로 옮김
		if (args.size() == 3) {
			String control;
			int x, y;
			
			if (args.get(0) instanceof StrV) {
				control = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {
				x = (int) ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				x = (int) ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));

			if (args.get(2) instanceof DoubleV) {
				y = (int) ((DoubleV) args.get(2)).getValue();
			} else if (args.get(2) instanceof StrV && ((StrV) args.get(2)).isNumber()) {
				y = (int) ((StrV) args.get(2)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(2));
			
			GraphicsWindow.ControlsMove(control, x, y);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetSize(ArrayList<Value> args) {
		// control, width, height
		// control의 너비와 높이를 width와 height로 설정
		if (args.size() == 3) {
			String control;
			int width, height;
			
			if (args.get(0) instanceof StrV) {
				control = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {
				width = (int) ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				width = (int) ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));

			if (args.get(2) instanceof DoubleV) {
				height = (int) ((DoubleV) args.get(2)).getValue();
			} else if (args.get(2) instanceof StrV && ((StrV) args.get(2)).isNumber()) {
				height = (int) ((StrV) args.get(2)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(2));
			
			GraphicsWindow.SetSize(control, width, height);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void HideControl(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 control을 숨김
		if (args.size() == 1) {
			String controlName;
			if (args.get(0) instanceof StrV) {
				controlName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			GraphicsWindow.HideControl(controlName);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void ShowControl(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 control을 보임
		if (args.size() == 1) {
			String controlName;
			if (args.get(0) instanceof StrV) {
				controlName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			GraphicsWindow.ShowControl(controlName);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value LastClickedButton = new StrV("");
	public static Value LastTypedTextBox = new StrV("");
	
	public static Value ButtonClicked;
	public static Value TextTyped;

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
}

package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.Value;

public class Controls {
	public static void AddButton(ArrayList<Value> args) {
		// caption, left, top
		// GraphicsWindow에 버튼을 추가한 창을 반환

		if (args.size() == 3) {

		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetButtonCaption(ArrayList<Value> args) {
		// buttonName
		// buttonName에 해당하는 버튼의 caption을 반환

		if (args.size() == 1) {
			return null;
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetButtonCaption(ArrayList<Value> args) {
		// buttonName, caption
		// buttonName에 해당하는 버튼의 caption을 설정함, 반환값 없음
		
		if(args.size() == 2) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void AddTextBox(ArrayList<Value> args) {
		// left, top
		// GraphicsWindow에 텍스트박스를 추가한 창을 반환
		if(args.size() == 2) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void AddMultiLineTextBox(ArrayList<Value> args) {
		// left, top
		// GraphicsWiindow에 텍스트박스를 추가한 창을 반환
		if(args.size() == 2) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetTextBoxText(ArrayList<Value> args) {
		// textBoxName
		// textBoxName에 해당하는 텍스트 박스의 내용을 반환
		if(args.size() == 1) {
			return null;
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
		
	}

	public static void SetTextBoxText(ArrayList<Value> args) {
		// textBoxName, text
		// textBoxName에 해당하는 텍스트 박스의 내용을 text로 설정함
		if(args.size() == 2) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Remove(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 것을 삭제함, 반환값 없음
		if(args.size() == 1) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Move(ArrayList<Value> args) {
		// control, x, y
		// control을 새로운 x, y로 옮김
		if(args.size() == 1) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetSize(ArrayList<Value> args) {
		// control, width, height
		// control의 너비와 높이를 width와 height로 설정
		if(args.size() == 3) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void HideControl(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 control을 숨김
		if(args.size() == 1) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void ShowControl(ArrayList<Value> args) {
		// controlName
		// controlName에 해당하는 control을 보임
		if(args.size() == 1) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value LastClickedButton;
	public static Value LastTypedTextBox;

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
}

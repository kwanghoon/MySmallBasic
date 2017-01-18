package com.coducation.smallbasic.lib;

import java.awt.MouseInfo;
import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Mouse {
	public static void HideCursor(ArrayList<Value> args) {
		// 화면상의 커서를 숨김
		if(args.size() == 0) {
			GraphicsWindow.HideCursor();
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void ShowCursor(ArrayList<Value> args) {
		// 화면상의 커서를 보여줌
		if(args.size() == 0) {
			GraphicsWindow.ShowCursor();
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}
	
	private static Value defaultMouseX = new DoubleV(MouseInfo.getPointerInfo().getLocation().getX());
	private static Value defaultMouseY = new DoubleV(MouseInfo.getPointerInfo().getLocation().getY());
	public static Value MouseX;
	public static Value MouseY;
	public static Value IsLeftButtonDown = new StrV("False");
	public static Value IsRightButtonDown = new StrV("False");

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {
		if("MouseX".equalsIgnoreCase(fieldName))
			MouseX = new StrV(defaultMouseX.toString());
		if("MouseY".equalsIgnoreCase(fieldName))
			MouseY = new StrV(defaultMouseY.toString());
	}
}

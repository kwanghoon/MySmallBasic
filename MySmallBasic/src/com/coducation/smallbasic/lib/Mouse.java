package com.coducation.smallbasic.lib;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
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
	
	private static void mouseXSetting(int mousex) {
		try {
			Robot robot = new Robot();
			MouseX = new DoubleV(mousex);
			robot.mouseMove(mousex, (int) MouseY.getNumber());
		} catch (AWTException e) {
		}
	}
	private static void mouseYSetting(int mousey) {
		try {
			Robot robot = new Robot();
			MouseY = new DoubleV(mousey);
			robot.mouseMove((int) MouseX.getNumber(), mousey);
		} catch (AWTException e) {
		}
	}
	
	private static Value defaultMouseX = new DoubleV(MouseInfo.getPointerInfo().getLocation().getX());
	private static Value defaultMouseY = new DoubleV(MouseInfo.getPointerInfo().getLocation().getY());
	public static Value MouseX = defaultMouseX;
	public static Value MouseY = defaultMouseY;
	public static Value IsLeftButtonDown = new StrV("False");
	public static Value IsRightButtonDown = new StrV("False");

	public static void notifyFieldAssign(String fieldName) {
		if("MouseX".equalsIgnoreCase(fieldName)) {
			int mousex;
			if(MouseX instanceof StrV && ((StrV) MouseX).isNumber()) {
				mousex = (int) ((StrV) MouseX).parseDouble();
			}
			else if(MouseX instanceof DoubleV) {
				mousex = (int) MouseX.getNumber();
			}
			else
				mousex = 0;
			mouseXSetting(mousex);
		}
		else if("MouseY".equalsIgnoreCase(fieldName)) {
			int mousey;
			if(MouseY instanceof StrV && ((StrV) MouseY).isNumber()) {
				mousey = (int) ((StrV) MouseY).parseDouble();
			}
			else if(MouseX instanceof DoubleV) {
				mousey = (int) MouseY.getNumber();
			}
			else
				mousey = 0;
			mouseYSetting(mousey);
		}
	}

	public static void notifyFieldRead(String fieldName) {
	}
}

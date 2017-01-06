package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.Value;

public class Mouse {
	public static void HideCursor(ArrayList<Value> args) {
		// 화면상의 커서를 숨김
	}
	
	public static void ShowCursor(ArrayList<Value> args) {
		// 화면상의 커서를 보여줌
	}
	
	
	private static Value MouseX;
	private static Value MouseY;
	private static Value IsLeftButtonDown;
	private static Value IsRightButtonDown;
}

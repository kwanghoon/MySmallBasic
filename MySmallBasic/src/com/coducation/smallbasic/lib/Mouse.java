package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.Value;

public class Mouse {
	public static void HideCursor(ArrayList<Value> args) {
		// 화면상의 커서를 숨김
		if(args.size() == 0) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void ShowCursor(ArrayList<Value> args) {
		// 화면상의 커서를 보여줌
		if(args.size() == 0) {
			
		}
		else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value MouseX;
	public static Value MouseY;
	public static Value IsLeftButtonDown;
	public static Value IsRightButtonDown;

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
}

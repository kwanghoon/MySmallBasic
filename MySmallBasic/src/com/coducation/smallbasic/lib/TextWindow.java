package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.Scanner;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class TextWindow {
	public static void Clear() {
		// 텍스트 창을 지움
		
	}

	public static void Hide() {
		// 텍스트 창을 숨김
		
	}

	public static void Pause() {
		// 끝내기 전 사용자의 입력을 기다림

	}

	public static void PauseIfVisible() {
		// 텍스트 창이 이미 열려 있을 때만 사용자의 입력을 기다림

	}

	public static void PauseWithoutMessage() {
		// 끝내기 전 사용자의 입력을 기다림
		
	}

	public static Value Read(ArrayList<Value> args) {
		// 텍스트 창에서 한줄의 텍스트를 읽음 사용자가 "Enter" 키를 누를때까지 종료하지 않음
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		StrV value = new StrV(line);
		
		return value;
	}

	public static Value ReadNumber(ArrayList<Value> args) {
		// 텍스트 창에서 하나의 수를 읽음 사용자가 "Enter" 키를 누를때까지 종료하지 않음
		Scanner scan = new Scanner(System.in);
		double number = scan.nextDouble();
		DoubleV value = new DoubleV(number);
		
		return value;
	}

	public static void Show() {
		// 텍스트 창을 보여주며 상호작용을 할 수 있도록 함
		
	}

	public static void Write(ArrayList<Value> args) {
		// 텍스트 창에 텍스트나 숫자를 씀 줄바꿈 표시 문자가 출력값에 포함되지 않음
		for(int i =0;i<args.size();i++) {
			System.out.print(args.get(i).toString());
		}
	}

	public static void WriteLine(ArrayList<Value> args) {
		for(Value v:args) {
			System.out.print(v.toString());
		}
		System.out.println();
		// 텍스트 창에 텍스트나 숫자를 씀 줄바꿈 표시 문자가 출력값에 포함됨
		
	}
	public static Value BackgroundColor = new StrV("#000000"); // black
	public static Value CursorLeft = new DoubleV(0);
	public static Value CursorTop = new DoubleV(0);
	public static Value ForegroundColor = new StrV("#FFFFFF"); // white
	public static Value Left = new DoubleV(0);
	public static Value Title = new StrV("");
	public static Value Top = new DoubleV(0);
	
	public static void notifyFieldAssign(String fieldName) {
		
	}
}
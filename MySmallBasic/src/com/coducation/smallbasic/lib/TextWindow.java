package com.coducation.smallbasic.lib;

import java.util.Scanner;

import com.coducation.smallbasic.DoubleV;
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

	public static Value Read() {
		// 텍스트 창에서 한줄의 텍스트를 읽음 사용자가 "Enter" 키를 누를때까지 종료하지 않음
		Scanner scan = new Scanner(System.in);
		String line = scan.nextLine();
		StrV value = new StrV(line);
		
		return value;
	}

	public static Value ReadNumber() {
		// 텍스트 창에서 하나의 수를 읽음 사용자가 "Enter" 키를 누를때까지 종료하지 않음
		Scanner scan = new Scanner(System.in);
		double number = scan.nextDouble();
		DoubleV value = new DoubleV(number);
		
		return value;
	}

	public static void Show() {
		// 텍스트 창을 보여주며 상호작용을 할 수 있도록 함
		
	}

	public static void Write(Value arg) {
		// 텍스트 창에 텍스트나 숫자를 씀 줄바꿈 표시 문자가 출력값에 포함되지 않음
		System.out.print(arg.toString());
	}

	public static void WriteLine(Value arg) {
		// 텍스트 창에 텍스트나 숫자를 씀 줄바꿈 표시 문자가 출력값에 포함됨
		System.out.println(arg.toString());
	}
}
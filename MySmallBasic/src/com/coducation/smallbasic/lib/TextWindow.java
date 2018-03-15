package com.coducation.smallbasic.lib;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

public class TextWindow {

	private static final Value defaultForegroundColor = new StrV("WHITE"); // white
	private static final Value defaultBackgroundColor = new StrV("BLACK"); // black
	private static final Value defaultCursorLeft = new DoubleV(0);
	private static final Value defaultCursorTop = new DoubleV(0);
	private static final Value defaultLeft = new DoubleV(0);
	private static final Value defaultTitle = new StrV("");
	private static final Value defaultTop = new DoubleV(0);
	
	private static boolean visible = true;
	
	public static Value ForegroundColor = defaultForegroundColor;
	public static Value BackgroundColor = defaultBackgroundColor; 
	public static Value CursorLeft = defaultCursorLeft;
	public static Value CursorTop = defaultCursorTop;
	public static Value Left = defaultLeft;
	public static Value Title = defaultTitle;
	public static Value Top = defaultTop;
	
	private static int cursorX = 0;
	private static int cursorY = 0;
	
	private static final String FIRST_ESC_CHAR = "\u001b";
	private static final String SECOND_ESC_CHAR = "[";
	
	static {
		
		AnsiConsole.systemInstall();
		
	}
	
	public static Value Show() {
		// 텍스트 창을 보여주며 상호작용을 할 수 있도록 함
		
		return null;
	}
	
	public static Value Hide() {
		// 텍스트 창을 숨김
		
		return null;
	}
	
	public static Value Clear(ArrayList<Value> args) {
		
		if (args.size() == 0) {
			
			AnsiConsole.out.println(ansi().eraseScreen());
			
		} else

			throw new InterpretException("Clear : Unexpected # of args: " + args.size());
		
		return null;
	}

	public static Value Pause(ArrayList<Value> args) {
		// 끝내기 전 사용자의 입력을 기다림
		// 프로그램  실행이 끝난 후 "press any key to continue.."라는 문자열이 한번 더 출력되고 
		// 그 후, 아무 키나 누르면 프로그램이 종료된다.
		
		if (args.size() == 0) {
			
			System.out.print("Press any key to continue...");
			new Scanner(System.in).nextLine();
			
		} else

			throw new InterpretException("Pause : Unexpected # of args: " + args.size());
		
		return null;
	}

	public static Value PauseIfVisible(ArrayList<Value> args) {
		// 텍스트 창이 이미 열려 있을 때만 사용자의 입력을 기다림
		// 콘솔 창이 Hide된 상태가 아니라면, Pause()와 동일한 기능인 것 같다.

		if (args.size() == 0) {
			
			if(visible) {
				
				System.out.print("Press any key to continue...");
				new Scanner(System.in).nextLine();
				
			}
			
		} else

			throw new InterpretException("Pause : Unexpected # of args: " + args.size());
		
		return null;
	}

	public static Value PauseWithoutMessage(ArrayList<Value> args) {
		// 끝내기 전 사용자의 입력을 기다림
		// 프로그램 실행이 끝난 후 "press any key to continue.."라는 문자열이 출력되지 않으나
		// 아무키나 입력하고 나면 "press any key to continue.."가 출력되고
		// 한번 더 아무 키나 입력하면 프로그램 종료
		
		if (args.size() == 0) {
			
			new Scanner(System.in).nextLine();
			System.out.print("Press any key to continue...");
			
		} else

			throw new InterpretException("Pause : Unexpected # of args: " + args.size());
		
		return null;
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

	public static void Write(ArrayList<Value> args) {
		// 텍스트 창에 텍스트나 숫자를 씀 줄바꿈 표시 문자가 출력값에 포함되지 않음
		for(int i =0;i<args.size();i++) {
			System.out.print(args.get(i).toString());
			cursorX++;
		}
		System.out.flush();
	}

	public static void WriteLine(ArrayList<Value> args) {
		
		// 텍스트 창에 텍스트나 숫자를 씀 줄바꿈 표시 문자가 출력값에 포함됨
		for(Value v:args) {
			System.out.print(v.toString());
		}
		
		/*for(int i =0;i<args.size();i++) {
			System.out.print(args.get(i).toString());
			cursorX += args.get(i).toString().length();
		}*/
		
		System.out.println();
		cursorX = 0;
		cursorY++;
		
	}
	
	public static void notifyFieldAssign(String fieldName) {
		
		if(fieldName.equalsIgnoreCase("ForegroundColor")) {
			
			String v;
			
			if (ForegroundColor instanceof StrV) {
				
				v = ((StrV)ForegroundColor).getValue().toUpperCase();
				
			} else
				
				throw new InterpretException("ForegroundColor: Unexpected value " + ForegroundColor.toString());
			
			ForegroundColor = new StrV(v);
			try {
				AnsiConsole.out.print(ansi().fg(valueOf(v)));
			} catch(Throwable e) {
				System.err.println("Error: TextWindow." + fieldName + " = " + v);
			}
			
		} else if (fieldName.equalsIgnoreCase("BackgroundColor")) {
			
			String v;
			
			if (BackgroundColor instanceof StrV) {
				
				v = ((StrV)BackgroundColor).getValue().toUpperCase();
				
			} else
				
				throw new InterpretException("BackgroundColor: Unexpected value " + BackgroundColor.toString());
			
			BackgroundColor = new StrV(v);
			try {
				AnsiConsole.out.print(ansi().bg(valueOf(v)));
			} catch(Throwable e) {
				System.err.println("Error: TextWindow." + fieldName + " = " + v);
			}
			
		} else if (fieldName.equalsIgnoreCase("CursorLeft")) {
			
			int cursorLeft;
			
			if (CursorLeft instanceof DoubleV) {
				
				cursorLeft = (int)((DoubleV)CursorLeft).getValue();
				CursorLeft = new DoubleV(cursorLeft);
				
			} else if (CursorLeft instanceof StrV && ((StrV)CursorLeft).isNumber()) {
				
				cursorLeft = (int)((StrV)CursorLeft).parseDouble();
				CursorLeft = new DoubleV(cursorLeft);
				
			} else 
				
				throw new InterpretException("CursorLeft: Unexpected value" + CursorLeft.toString());

			cursorX = cursorLeft; 
			try {
				AnsiConsole.out.print(ansi().cursor(cursorY, cursorX)); // 다시 확인 필요
			} catch(Throwable e) {
				System.err.println("Error: TextWindow." + fieldName + " = " + cursorX);
			}
			
		} else if (fieldName.equalsIgnoreCase("CursorTop")){
			
			int cursorTop;
			
			if (CursorTop instanceof DoubleV) {
				
				cursorTop = (int)((DoubleV)CursorTop).getValue();
				CursorTop = new DoubleV(cursorTop);
				 
				
			} else if (CursorTop instanceof StrV && ((StrV)CursorTop).isNumber()) {
				
				cursorTop = (int)((StrV)CursorTop).parseDouble();
				CursorTop = new DoubleV(cursorTop);
				
			} else 
				
				throw new InterpretException("CursorTop: Unexpected value" + CursorTop.toString());
			
			cursorY = cursorTop;
			try {
				AnsiConsole.out.print(ansi().cursor(cursorY, cursorX)); // 다시 확인 필요
			} catch(Throwable e) {
				System.err.println("Error: TextWindow." + fieldName + " = " + cursorY);
			}
			
		} else if (fieldName.equalsIgnoreCase("Left") || fieldName.equalsIgnoreCase("Top")) {
			
			int left;
			int top;

			if (Left instanceof DoubleV) 
				left = (int) ((DoubleV) Left).getValue();
			else if (Left instanceof StrV && ((StrV) Left).isNumber())
				left = (int) ((StrV) Left).parseDouble();
			else
				left = (int) ((DoubleV) defaultLeft).getValue();

			if (Top instanceof DoubleV)
				top = (int) ((DoubleV) Top).getValue();
			else if (Top instanceof StrV && ((StrV) Top).isNumber())
				top = (int) ((StrV) Top).parseDouble();
			else
				top = (int) ((DoubleV) defaultTop).getValue();

			// console.setLocation(left, top);  <-- 콘솔 창의 위치 설정
			
		} else if (fieldName.equalsIgnoreCase("Title")) {
			
			// console.setTitle(Title.toString()); <-- 콘솔 창의 제목 설정 
			
			String v;
			
			if (Title instanceof StrV) {
				
				v = ((StrV)Title).getValue();
				
			} else
				
				throw new InterpretException("Title: Unexpected value " + Title.toString());
			
			Title = new StrV(v);
			
		}
		
	}
	
	public static void notifyFieldRead(String fieldName) {
		
		if(fieldName.equalsIgnoreCase("ForegroundColor")) {
			
			String v = ((StrV)ForegroundColor).getValue();
			
			ForegroundColor = new StrV(v);
			
		} else if (fieldName.equalsIgnoreCase("BackgroundColor")) {
			
			String v = ((StrV)BackgroundColor).getValue();
			
			BackgroundColor = new StrV(v);
			
		} else if (fieldName.equalsIgnoreCase("CursorLeft")) {
			
			CursorLeft = new DoubleV(cursorX);
			
		} else if (fieldName.equalsIgnoreCase("CursorTop")) {
			
			CursorTop = new DoubleV(cursorY);
			
		}
		
	}
}
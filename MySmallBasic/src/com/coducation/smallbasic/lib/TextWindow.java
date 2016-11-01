package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.Value;

public class TextWindow {
	public static void WriteLine(ArrayList<Value> args) {
		for(Value v:args) {
			System.out.print(v.toString());
		}
		System.out.println();
	}
	public static void Write(ArrayList<Value> args) {
		for(Value v:args) {
			System.out.print(v.toString());
		}
	}
	public static Value Read() {
		
		return null;
	}
}

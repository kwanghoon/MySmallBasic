package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Value;

public class Math {
	public static Value Abs(ArrayList<Value> args) {
		// args i번째 Value가 DoubleV인 경우에만 절대값으로 바꿔 줄 수 있음
		return new DoubleV(java.lang.Math.abs(((DoubleV)args.get(0)).getValue()));
	}
	public static Value ArcCos() {
		return null;
	}
	public static Value ArcSin() {
		return null;
	}
	public static Value ArcTan() {
		return null;
	}
	public static Value Ceiling() {
		return null;
	}
	public static Value Cos() {
		return null;
	}
	public static Value Floor() {
		return null;
	}
	public static Value GetDegrees() {
		return null;
	}
	public static Value GetRadians() {
		return null;
	}
	public static Value GetRandomNumber(ArrayList<Value> args) {
		return null;
	}
	public static Value Log() {
		return null;
	}
	public static Value Max() {
		return null;
	}
	public static Value Min() {
		return null;
	}
	public static Value NaturalLog() {
		return null;
	}
	public static Value Power() {
		return null;
	}
	public static Value Remainder(ArrayList<Value> args) {
		return new DoubleV(((DoubleV)args.get(0)).getValue() % ((DoubleV)args.get(1)).getValue());
	}
	public static Value Round() {
		return null;
	}
	public static Value Sin() {
		return null;
	}
	public static Value SquareRoot() {
		return null;
	}
	public static Value Tan() {
		return null;
	}
	public static Value Pi = new DoubleV(3.14159265358979);
	
	public static void notifyFieldAssign(String fieldName) {
		
	}
	
	public static void notifyFieldRead(String fieldName) {
		
	}
}

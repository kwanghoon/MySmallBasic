package com.coducation.smallbasic.lib;

import java.awt.Toolkit;
import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Value;

public class Desktop {
	public static Value Width;
	public static Value Height;
	
	static {
		Width = new DoubleV(
					Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		Height = new DoubleV(
					Toolkit.getDefaultToolkit().getScreenSize().getHeight());
	}
	
	public static void SetWallPaper(ArrayList<Value> args) {
		// Do nothing
		// Need to fix!!
	}
	
	public static void notifyFieldAssign(String fieldName) {
		//비어두기 
	}

	public static void notifyFieldRead(String fieldName) {
		if ("Width".equalsIgnoreCase(fieldName)) {
			Width = new DoubleV(
					Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		} else if("Height".equalsIgnoreCase(fieldName)) {
			Height = new DoubleV(
					Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		}
	}
}

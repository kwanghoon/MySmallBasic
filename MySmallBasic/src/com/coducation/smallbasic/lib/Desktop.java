package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Value;

public class Desktop {
	public static Value Width;
	public static Value Height;
	
	static {
		Width = new DoubleV(1280);  // Need to fix!!
		Height = new DoubleV(640);
	}
	
	public static void SetWallPaper(ArrayList<Value> args) {
		// Do nothing
		// Need to fix!!
	}
	
	public static void notifyFieldAssign(String fieldName) {
		//비어두기 
	}

	public static void notifyFieldRead(String fieldName) {
		//비어두기 
	}
}

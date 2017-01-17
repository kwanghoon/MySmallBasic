package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class ImageList {

	public static Value LoadImage(ArrayList<Value> args){
		
		String str_arg;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("LoadImage : Unexpected arg");

			}

		} else

			throw new InterpretException("LoadImage : Unexpected # of args: " + args.size());

		return null;

	}
	
	public static Value GetWidthOfImage(ArrayList<Value> args){
		return null;
	}
	
	public static Value GetHeightOfImage(ArrayList<Value> args){
		return null;
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
	
}

package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.Eval;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.Value;

public class Assert {

	public static Value assertion(ArrayList<Value> args) {
		if (args.size() == 2) {
			Value str_arg = args.get(0);
			Value bool_arg = args.get(1);
			if (Eval.isTrue(bool_arg)) {  // TODO: The reference to Eval.isTrue should disappear.
				// do nothing
			}
			else {
				throw new InterpretException("Assert.assertion fail : " + str_arg + " == " + bool_arg);
			}
		}
		else {
			throw new InterpretException("Assert.assertion : # of args == " + args.size());
		}
		return null;
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
}

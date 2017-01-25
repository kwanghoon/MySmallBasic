package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.Calendar;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Eval;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Program {

	public static Value ArgumentCount;
	public static Value Directory;

	
	//Program.Delay(milliSeconds)
	//Delays program execution by the specified amount of MilliSeconds. 
	public static Value Delay(ArrayList<Value> args) {
		
		double dbl_arg;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Delay : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Delay : Unexpected arg");

			}

		} else

			throw new InterpretException("Delay : Unexpected # of args : " + args.size());
		
		try {
			
			Thread.sleep((long)dbl_arg);
			
		} catch (InterruptedException e) { /* Ignore */ }
		
		return null;

	}

	//Program.End()
	//Ends the program. 
	public static Value End(ArrayList<Value> args) {
		
		if (args.size() == 0) {
			
			System.exit(0);

		} else

			throw new InterpretException("End : Unexpected # of args : " + args.size());
		
		return null;
		
	}

	public static Value GetArgument(ArrayList<Value> args) {
		
		double dbl_arg;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("GetArgument : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("GetArgument : Unexpected arg");

			}

		} else

			throw new InterpretException("GetArgument : Unexpected # of args: " + args.size());
		
		String[] argument = Eval.getProgramArgs();
		
		return new StrV(argument[(int)dbl_arg]);
		
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

		if ("ArgumentCount".equalsIgnoreCase(fieldName)){
			
			ArgumentCount = new DoubleV(Eval.getProgramArgs().length);
			
		} else if ("Directory".equalsIgnoreCase(fieldName)) {
			
			Directory = new StrV(System.getProperty("user.dir"));
		
		}
			
	}
	
}
package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Math {
	
	public static Value Abs(ArrayList<Value> args) {
		
		// args i번째 Value가 DoubleV인 경우에만 절대값으로 바꿔 줄 수 있음
		
		return new DoubleV(java.lang.Math.abs(((DoubleV)args.get(0)).getValue()));
		
	}
	
	public static Value ArcCos(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. ArcCos(0.5)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. ArcCos("0.5")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("ArcCos: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("ArcCos: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("ArcCos: Unexpected # of args: " + args.size());

		ret = java.lang.Math.acos(dbl_arg);

		return new DoubleV(ret);
		
	}
	
	public static Value ArcSin(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. ArcSin(0.5)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. ArcSin("0.5")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("ArcSin: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("ArcSin: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("ArcSin: Unexpected # of args: " + args.size());

		ret = java.lang.Math.asin(dbl_arg);

		return new DoubleV(ret);
	
	}
	
	public static Value ArcTan(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. ArcTan(0.5)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. ArcTan("0.5")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("ArcTan: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("ArcTan: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("ArcTan: Unexpected # of args: " + args.size());

		ret = java.lang.Math.atan(dbl_arg);

		return new DoubleV(ret);
	
	}
	
	public static Value Ceiling(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. Ceiling(0.0001)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. Ceiling("0.0001")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("Ceiling: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("Ceiling: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("Ceiling: Unexpected # of args: " + args.size());

		ret = java.lang.Math.ceil(dbl_arg);

		return new DoubleV(ret);
	
	}
	
	public static Value Cos(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. cos(Math.Pi/6)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. cos(), cos("abc")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("Cos: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("Cos: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("Cos: Unexpected # of args: " + args.size());

		ret = java.lang.Math.cos(dbl_arg);

		return new DoubleV(ret);
	}
	
	public static Value Floor(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. Floor(0.9999)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. Floor("0.9999")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("Floor: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("Floor: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("Floor: Unexpected # of args: " + args.size());

		ret = java.lang.Math.floor(dbl_arg);

		return new DoubleV(ret);
		
	}
	
	// Converts a given angle in radians to degrees.
	public static Value GetDegrees(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. 

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. 

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("GetDegrees: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("GetDegrees: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("GetDegrees: Unexpected # of args: " + args.size());

		ret = java.lang.Math.toDegrees(dbl_arg);

		return new DoubleV(ret);
		
	}
	
	//Converts a given angle in degrees to radians. 
	public static Value GetRadians(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. 

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. 

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("GetRadians: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("GetRadians: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("GetRadians: Unexpected # of args: " + args.size());

		ret = java.lang.Math.toRadians(dbl_arg);

		return new DoubleV(ret);
		
	}
	
	// Gets a random number between 1 and the specified maxNumber (inclusive). 
	public static Value GetRandomNumber(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. GetRandomNumber(100) :  1 이상 100 이하

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. GetRandomNumber("100")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("GetRadians: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("GetRadians: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("GetRadians: Unexpected # of args: " + args.size());

		ret = (int)(dbl_arg * java.lang.Math.random() + 1 );

		return new DoubleV(ret);
		
	}
	
	// Gets the logarithm (base 10) value of the given number. 
	public static Value Log(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. Log(100)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. Log("100")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("Log: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("Log: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("Log: Unexpected # of args: " + args.size());

		ret = java.lang.Math.log10(dbl_arg);

		return new DoubleV(ret);
		
	}
	
	public static Value Max() {
		
		return null;
		
	}
	
	public static Value Min() {
		
		return null;
	
	}
	
	// Gets the natural logarithm value of the given number. 
	public static Value NaturalLog(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. NaturalLog(2.74)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. NaturalLog("2.74")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("NaturalLog: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("NaturalLog: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("NaturalLog: Unexpected # of args: " + args.size());

		ret = java.lang.Math.log(dbl_arg);

		return new DoubleV(ret);
	
	}
	
	public static Value Power() {
		
		return null;
		
	}
	
	public static Value Remainder(ArrayList<Value> args) {
		
		return new DoubleV(((DoubleV)args.get(0)).getValue() % ((DoubleV)args.get(1)).getValue());
	
	}
	
	public static Value Round(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. Round(32.233) or Round(32.566)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. Round("32.233") or Round("32.566")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("Round: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("Round: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("Round: Unexpected # of args: " + args.size());

		ret = java.lang.Math.round(dbl_arg);

		return new DoubleV(ret);
	
	}
	
	public static Value Sin(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. sin(Math.Pi/6)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. sin(Math.Pi/"6"), sin("abc")     <<   Error !  

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("Sin: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("Sin: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("Sin: Unexpected # of args: " + args.size());

		ret = java.lang.Math.sin(dbl_arg);

		return new DoubleV(ret);
		
	}
	
	public static Value SquareRoot(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. 

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. 

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("SquareRoot: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("SquareRoot: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("SquareRoot: Unexpected # of args: " + args.size());

		ret = java.lang.Math.sqrt(dbl_arg);

		return new DoubleV(ret);
		
	}
	
	public static Value Tan(ArrayList<Value> args) {
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				// e.g. tan(30)

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				// e.g. tan("30"), tan("abc")

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("Tan: Unexpected StrV arg : " + arg);

				}

			}

			else {

				throw new InterpretException("Tan: Unexpected arg");

			}

		}

		else 

			throw new InterpretException("Tan: Unexpected # of args: " + args.size());

		ret = java.lang.Math.tan(dbl_arg);

		return new DoubleV(ret);
	
	}
	
	public static Value Pi = new DoubleV(java.lang.Math.PI);
	
	public static void notifyFieldAssign(String fieldName) {
		
	}
	
	public static void notifyFieldRead(String fieldName) {
		
	}
	
}

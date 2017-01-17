package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Math {

	public final static Value Pi = new DoubleV(java.lang.Math.PI);

	// Gets the absolute value of the given number.
	// For example, -32.233 will return 32.233. 
	public static Value Abs(ArrayList<Value> args) {

		// asdfasdf
		
		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Abs : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Abs : Unexpected arg");

			}

		} else

			throw new InterpretException("Abs : Unexpected # of args: " + args.size());

		ret = java.lang.Math.abs(dbl_arg);

		return new DoubleV(ret);

	}

	// Gets an integer that is greater than or equal to the specified decimal number.
	// For example, 32.233 will return 33. 
	public static Value Ceiling(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch (NumberFormatException e) {

					throw new InterpretException("Ceiling : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Ceiling : Unexpected arg");

			}

		} else

			throw new InterpretException("Ceiling : Unexpected # of args: " + args.size());

		ret = java.lang.Math.ceil(dbl_arg);

		return new DoubleV(ret);

	}

	// Gets an integer that is less than or equal to the specified decimal number.
	// For example, 32.233 will return 32. 
	public static Value Floor(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Floor : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Floor : Unexpected arg");

			}

		} else

			throw new InterpretException("Floor : Unexpected # of args: " + args.size());

		ret = java.lang.Math.floor(dbl_arg);

		return new DoubleV(ret);

	}

	// Gets the natural logarithm value of the given number.
	public static Value NaturalLog(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("NaturalLog: Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("NaturalLog: Unexpected arg");

			}

		} else

			throw new InterpretException("NaturalLog: Unexpected # of args: " + args.size());

		ret = java.lang.Math.log(dbl_arg);

		return new DoubleV(ret);

	}

	// Gets the logarithm (base 10) value of the given number.
	public static Value Log(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Log : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Log : Unexpected arg");

			}

		} else

			throw new InterpretException("Log : Unexpected # of args: " + args.size());

		ret = java.lang.Math.log10(dbl_arg);

		return new DoubleV(ret);

	}
	
	// Gets the cosine of the given angle in radians. 
	public static Value Cos(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Cos : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Cos : Unexpected arg");

			}

		} else

			throw new InterpretException("Cos : Unexpected # of args: " + args.size());

		ret = java.lang.Math.cos(dbl_arg);

		return new DoubleV(ret);
	}
	
	// Gets the sine of the given angle in radians. 
	public static Value Sin(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Sin : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Sin : Unexpected arg");

			}

		} else

			throw new InterpretException("Sin : Unexpected # of args: " + args.size());

		ret = java.lang.Math.sin(dbl_arg);

		return new DoubleV(ret);

	}
	
	// Gets the tangent of the given angle in radians. 
	public static Value Tan(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Tan : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Tan : Unexpected arg");

			}

		} else

			throw new InterpretException("Tan : Unexpected # of args: " + args.size());

		ret = java.lang.Math.tan(dbl_arg);

		return new DoubleV(ret);

	}
	
	// Gets the angle in radians, given the sin value. 
	public static Value ArcSin(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("ArcSin : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("ArcSin : Unexpected arg");

			}

		} else

			throw new InterpretException("ArcSin : Unexpected # of args: " + args.size());

		ret = java.lang.Math.asin(dbl_arg);

		return new DoubleV(ret);

	}
	
	// Gets the angle in radians, given the cosine value. 
	public static Value ArcCos(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("ArcCos : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("ArcCos : Unexpected arg");

			}

		} else

			throw new InterpretException("ArcCos : Unexpected # of args: " + args.size());

		ret = java.lang.Math.acos(dbl_arg);

		return new DoubleV(ret);

	}

	// Gets the angle in radians, given the tangent value. 
	public static Value ArcTan(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("ArcTan : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("ArcTan : Unexpected arg");

			}

		} else

			throw new InterpretException("ArcTan : Unexpected # of args: " + args.size());

		ret = java.lang.Math.atan(dbl_arg);

		return new DoubleV(ret);

	}

	// Converts a given angle in radians to degrees.
	public static Value GetDegrees(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("GetDegrees : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("GetDegrees : Unexpected arg");

			}

		} else

			throw new InterpretException("GetDegrees : Unexpected # of args: " + args.size());

		ret = java.lang.Math.toDegrees(dbl_arg);

		return new DoubleV(ret);

	}

	// Converts a given angle in degrees to radians.
	public static Value GetRadians(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("GetRadians : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("GetRadians : Unexpected arg");

			}

		} else

			throw new InterpretException("GetRadians : Unexpected # of args: " + args.size());

		ret = java.lang.Math.toRadians(dbl_arg);

		return new DoubleV(ret);

	}
	
	// Gets the square root of a given number. 
	public static Value SquareRoot(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("SquareRoot : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("SquareRoot : Unexpected arg");

			}

		} else

			throw new InterpretException("SquareRoot : Unexpected # of args: " + args.size());

		ret = java.lang.Math.sqrt(dbl_arg);

		return new DoubleV(ret);

	}
	
	// Raises the baseNumber to the specified power. 
	public static Value Power(ArrayList<Value> args) {

		double dbl_arg0;
		double dbl_arg1;

		double ret;

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg0 = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg0 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Power : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Power : Unexpected arg");

			}

			if (args.get(1) instanceof DoubleV) {

				dbl_arg1 = ((DoubleV) args.get(1)).getValue();

			} else if (args.get(1) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg1 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Power : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Power : Unexpected arg");

			}

		} else

			throw new InterpretException("Power : Unexpected # of args: " + args.size());

		ret = java.lang.Math.pow(dbl_arg0, dbl_arg1);

		return new DoubleV(ret);

	}
	
	// Rounds a given number to the nearest integer.
	// For example 32.233 will be rounded to 32.0 while 32.566 will be rounded to 33. 
	public static Value Round(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch (NumberFormatException e) {

					throw new InterpretException("Round : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Round : Unexpected arg");

			}

		} else

			throw new InterpretException("Round : Unexpected # of args: " + args.size());

		ret = java.lang.Math.round(dbl_arg);

		return new DoubleV(ret);

	}

	// Compares two numbers and returns the greater of the two. 
	public static Value Max(ArrayList<Value> args) {

		double dbl_arg0;
		double dbl_arg1;

		double ret;

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg0 = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg0 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Max : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Max : Unexpected arg");

			}

			if (args.get(1) instanceof DoubleV) {

				dbl_arg1 = ((DoubleV) args.get(1)).getValue();

			} else if (args.get(1) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg1 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Max : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Max : Unexpected arg");

			}

		}

		else

			throw new InterpretException("Max : Unexpected # of args: " + args.size());

		ret = java.lang.Math.max(dbl_arg0, dbl_arg1);

		return new DoubleV(ret);

	}

	// Compares two numbers and returns the smaller of the two. 
	public static Value Min(ArrayList<Value> args) {

		double dbl_arg0;
		double dbl_arg1;

		double ret;

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg0 = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg0 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Min : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Min : Unexpected arg");

			}

			if (args.get(1) instanceof DoubleV) {

				dbl_arg1 = ((DoubleV) args.get(1)).getValue();

			} else if (args.get(1) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg1 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Min : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Min : Unexpected arg");

			}

		} else

			throw new InterpretException("Max : Unexpected # of args: " + args.size());

		ret = java.lang.Math.min(dbl_arg0, dbl_arg1);

		return new DoubleV(ret);

	}

	// Divides the first number by the second and returns the remainder. 
	public static Value Remainder(ArrayList<Value> args) {

		double dbl_arg0;
		double dbl_arg1;

		double ret;

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg0 = ((DoubleV) args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg0 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Remainder : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Remainder : Unexpected arg");

			}

			if (args.get(1) instanceof DoubleV) {

				dbl_arg1 = ((DoubleV) args.get(1)).getValue();

			} else if (args.get(1) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg1 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException("Remainder : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("Remainder : Unexpected arg");

			}

		} else

			throw new InterpretException("Remainder : Unexpected # of args: " + args.size());

		ret = dbl_arg0 % dbl_arg1;

		return new DoubleV(ret);

	}
	
	// Gets a random number between 1 and the specified maxNumber (inclusive).
	public static Value GetRandomNumber(ArrayList<Value> args) {

		double dbl_arg;

		double ret;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV) args.get(0)).getValue();

			}

			else if (args.get(0) instanceof StrV) {

				String arg = ((StrV) args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch (NumberFormatException e) {

					throw new InterpretException("GetRandomNumber : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("GetRandomNumber : Unexpected arg");

			}

		} else

			throw new InterpretException("GetRandomNumber : Unexpected # of args: " + args.size());

		ret = (int) (dbl_arg * java.lang.Math.random() + 1);

		return new DoubleV(ret);

	}

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}

}

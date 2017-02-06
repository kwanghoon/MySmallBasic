package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Text {

	// Text.Append(text1, text2)
	// Appends two text inputs and returns the result as another text.
	// This operation is particularly useful when dealing with unknown text in variables
	// which could accidentally be treated as numbers and get added, instead of getting appended.
	public static Value Append(ArrayList<Value> args) {

		String str_arg0 = new String();
		String str_arg1 = new String();

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				str_arg0 = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("Append : Unexpected 1st argument");

			}

			if (args.get(1) instanceof DoubleV) {

				str_arg1 = args.get(1).toString();

			} else if (args.get(1) instanceof StrV) {

				str_arg1 = ((StrV) args.get(1)).getValue();

			} else {

				throw new InterpretException("Append : Unexpected 2nd argument");

			}

		} else {

			throw new InterpretException("Append : Unexpected # of args: " + args.size());

		}
		
		return new StrV(str_arg0.concat(str_arg1));
		
	}

	// Text.GetLength(text)
	// Gets the length of the given text.
	public static Value GetLength(ArrayList<Value> args) {

		String str_arg = new String();

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				str_arg = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("GetLentgh: Unexpected arg");

			}

		} else {

			throw new InterpretException("GetLength: Unexpected # of args: " + args.size());

		}
		
		return new DoubleV(str_arg.length());
		
	}

	// Text.IsSubText(text, subText)
	// Gets whether or not a given subText is a subset of the larger text.
	public static Value IsSubText(ArrayList<Value> args) {

		String str_arg0 = new String();
		String str_arg1 = new String();

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				str_arg0 = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("IsSubText : Unexpected 1st argument");

			}

			if (args.get(1) instanceof DoubleV) {

				str_arg1 = args.get(1).toString();

			} else if (args.get(1) instanceof StrV) {

				str_arg1 = ((StrV) args.get(1)).getValue();

			} else {

				throw new InterpretException("IsSubText : Unexpected 2nd argument");

			}

		} else {

			throw new InterpretException("IsSubText : Unexpected # of args: " + args.size());

		}
			
		for (int i = 0; i < str_arg0.length() - str_arg1.length() + 1; i++) {

			if (str_arg0.substring(i, str_arg1.length() + i).equals(str_arg1))
				
				return new StrV("True");

		}

		return new StrV("False");

	}

	// Text.EndsWith(text, subText)
	// Gets whether or not a given text ends with the specified subText.
	public static Value EndsWith(ArrayList<Value> args) {

		String str_arg0 = new String();
		String str_arg1 = new String();

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				str_arg0 = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("EndsWith : Unexpected 1st argument");

			}

			if (args.get(1) instanceof DoubleV) {

				str_arg1 = args.get(1).toString();

			} else if (args.get(1) instanceof StrV) {

				str_arg1 = ((StrV) args.get(1)).getValue();

			} else {

				throw new InterpretException("EndsWith : Unexpected 2nd argument");

			}

		} else {

			throw new InterpretException("EndsWith : Unexpected # of args: " + args.size());

		}
			
		if (str_arg0.endsWith(str_arg1)) 
			
			return new StrV("True");

		else 
			
			return new StrV("False");
		
	}

	// Text.StartsWith(text, subText)
	// Gets whether or not a given text starts with the specified subText.
	public static Value StartsWith(ArrayList<Value> args) {

		String str_arg0 = new String();
		String str_arg1 = new String();

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				str_arg0 = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("StartsWith : Unexpected 1st argument");

			}

			if (args.get(1) instanceof DoubleV) {

				str_arg1 = args.get(1).toString();

			} else if (args.get(1) instanceof StrV) {

				str_arg1 = ((StrV) args.get(1)).getValue();

			} else {

				throw new InterpretException("StartsWith : Unexpected 2nd argument");

			}

		} else {

			throw new InterpretException("StartsWith : Unexpected # of args: " + args.size());

		}
			
		if (str_arg0.startsWith(str_arg1)) 
			
			return new StrV("True");

		else 
			
			return new StrV("False");

	}
	
	//Text.GetSubText(text, start, length)
	//Gets a sub-text from the given text.
	public static Value GetSubText(ArrayList<Value> args) throws Throwable {

		try {
			
			String str_arg0;
			double dbl_arg1;
			double dbl_arg2;

			if (args.size() == 3) {

				if (args.get(0) instanceof DoubleV) {

					str_arg0 = args.get(0).toString();

				} else if (args.get(0) instanceof StrV) {

					str_arg0 = ((StrV) args.get(0)).getValue();

				} else {

					throw new InterpretException("GetSubText : Unexpected 1st argument");

				}

				if (args.get(1) instanceof DoubleV) {

					dbl_arg1 = ((DoubleV) args.get(1)).getValue();

				} else if (args.get(1) instanceof StrV) {

					String arg = ((StrV) args.get(1)).getValue();

					try {

						dbl_arg1 = Double.parseDouble(arg);

					} catch (NumberFormatException e) {

						throw new InterpretException(" GetSubText : Unexpected StrV arg : " + arg);

					}

				} else {

					throw new InterpretException("GetSubText : Unexpected 2nd arg");

				}

				if (args.get(2) instanceof DoubleV) {

					dbl_arg2 = ((DoubleV) args.get(2)).getValue();

				} else if (args.get(2) instanceof StrV) {

					String arg = ((StrV) args.get(2)).getValue();

					try {

						dbl_arg2 = Double.parseDouble(arg);

					} catch (NumberFormatException e) {

						throw new InterpretException("GetSubText : Unexpected StrV arg : " + arg);

					}

				} else {

					throw new InterpretException("GetSubText : Unexpected 3rd arg");

				}

			} else {

				throw new InterpretException("GetSubText : Unexpected # of args: " + args.size());
				
			}
			
			return new StrV(str_arg0.substring((int)(dbl_arg1 - 1), (int)(dbl_arg1 + dbl_arg2 - 1))) ;

			
		} catch ( Throwable e ) {
			
			return null ;
			
		}
		
	}
	
	//Text.GetSubTextToEnd(text, start)
	//Gets a sub-text from the given text from a specified position to the end.
	public static Value GetSubTextToEnd(ArrayList<Value> args) {

		String str_arg0;
		double dbl_arg1;

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				str_arg0 = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("GetSubText : Unexpected 1st argument");

			}

			if (args.get(1) instanceof DoubleV) {

				dbl_arg1 = ((DoubleV) args.get(1)).getValue();

			} else if (args.get(1) instanceof StrV) {

				String arg = ((StrV) args.get(1)).getValue();

				try {

					dbl_arg1 = Double.parseDouble(arg);

				} catch (NumberFormatException e) {

					throw new InterpretException(" GetSubText : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("GetSubText : Unexpected 2nd arg");

			}

		} else {

			throw new InterpretException("GetSubText : Unexpected # of args: " + args.size());
		
		}
			
		return new StrV(str_arg0.substring((int)(dbl_arg1 - 1))) ;
		
	}

	//Text.GetIndexOf(text, subText)
	//Finds the position where a sub-text appears in the specified text. 
	public static Value GetIndexOf(ArrayList<Value> args) {
		
		String str_arg0;
		String str_arg1;

		if (args.size() == 2) {

			if (args.get(0) instanceof DoubleV) {

				str_arg0 = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("GetIndexOf : Unexpected 1st argument");

			}

			if (args.get(1) instanceof DoubleV) {

				str_arg1 = args.get(1).toString();

			} else if (args.get(1) instanceof StrV) {

				str_arg1 = ((StrV) args.get(1)).getValue();

			} else {

				throw new InterpretException("GetIndexOf : Unexpected 2nd argument");

			}

		} else {

			throw new InterpretException("GetIndexOf : Unexpected # of args: " + args.size());
		
		}
			
		return new DoubleV(str_arg0.indexOf(str_arg1) + 1);
		
	}

	//Text.ConvertToLowerCase(text)
	//Converts the given text to lower case. 
	public static Value ConvertToLowerCase(ArrayList<Value> args) {
		
		String str_arg;

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				str_arg = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("ConvertToLowerCase : Unexpected arg");

			}

		} else {

			throw new InterpretException("ConvertToLowerCase : Unexpected # of args: " + args.size());

		}
			
		return new StrV(str_arg.toLowerCase());
		
	}

	//Text.ConvertToUpperCase(text)
	//Converts the given text to upper case. 
	public static Value ConvertToUpperCase(ArrayList<Value> args) {

		String str_arg = new String();

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				str_arg = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("ConvertToUpperCase : Unexpected arg");

			}

		} else {

			throw new InterpretException("ConvertToUpperCase : Unexpected # of args: " + args.size());

		}
			
		return new StrV(str_arg.toUpperCase());
		
	}

	//Text.GetCharacter(characterCode)
	//Given the unicode character code, gets the corresponding character,
	//which can then be used with regular text. 
	public static Value GetCharacter(ArrayList<Value> args) {
		
		double dbl_arg;

		if (args.size() == 1) {

			if(args.get(0) instanceof DoubleV) {

				dbl_arg = ((DoubleV)args.get(0)).getValue();

			} else if (args.get(0) instanceof StrV) {

				String arg = ((StrV)args.get(0)).getValue();

				try {

					dbl_arg = Double.parseDouble(arg);

				}

				catch(NumberFormatException e) {

					throw new InterpretException("GetCharacter : Unexpected StrV arg : " + arg);

				}

			} else {

				throw new InterpretException("GetCharacter : Unexpected arg");

			}

		} else {

			throw new InterpretException("GetCharacter : Unexpected # of args: " + args.size());
		
		}
			
		return new StrV(String.valueOf((char)(dbl_arg)));
		
	}

	//Text.GetCharacterCode(character)
	//Given a unicode character, gets the corresponding character code. 
	public static Value GetCharacterCode(ArrayList<Value> args) {
		
		String str_arg = new String();

		if (args.size() == 1) {

			if (args.get(0) instanceof DoubleV) {

				str_arg = args.get(0).toString();

			} else if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("GetCharacterCode : Unexpected arg");

			}

		} else {

			throw new InterpretException("GetCharacterCode : Unexpected # of args: " + args.size());

		}
			
		return new DoubleV(str_arg.charAt(0));
		
	}

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}

}

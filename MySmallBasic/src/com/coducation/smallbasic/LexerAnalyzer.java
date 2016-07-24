package com.coducation.smallbasic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LexerAnalyzer
{
	public LexerAnalyzer(FileReader fr)
	{
		this.br = new BufferedReader(fr);
		this.strarr = new ArrayList<String>();
		this.Lexer = new ArrayList<ArrayList<SyntaxPair>>();
	}
	
	public void Lexing() throws IOException
	{
		while(true)
		{
			String read_string;
			if((read_string = br.readLine()) != null)
			{
					if(!read_string.equals("")) // Empty line is not added.
					strarr.add(read_string + "\n"); 	
			}
			else
				break;
		}
		
		for(int index = 0; index < strarr.size(); index++) // Lexing Routine, START_FILE TO END_FILE
		{
			String line = strarr.get(index);
			String I = "";
			ArrayList<SyntaxPair> Tokenized_word = new ArrayList<SyntaxPair>();
			int i_index = 0;
			Token CurrToken = Token.NONE;
			
			while(i_index < line.length()) // repeat from first character to final, in one line. 
			{
				char ch = line.charAt(i_index);
				
				if(ch == '\n') // END_LINE => Token "CR"
				{
					I = "\n"; // Fixable.
					CurrToken = Token.CR;
					Tokenized_word.add(new SyntaxPair(I, CurrToken));
					break;
				}
				else if(ch == '\'') // Comment => Skip..
				{
					break;
				} 
				// ( ) { } , = : + - * / [ ] 
				else if(ch == '(' || ch == ')' || ch == '{' || ch == '}' ||  ch == ',' || ch == '=' || ch == ':' || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '[' || ch == ']')
					{
					I = I + ch;
					
					switch(ch)
					{
						case '(':
							CurrToken = Token.OPEN_PARA;
							break;
						case ')':
							CurrToken = Token.CLOSE_PARA;
							break;
						case '{':
							CurrToken = Token.OPEN_BRACE;
							break;
						case '}':
							CurrToken = Token.CLOSE_BRACE;
							break;
						case ',':
							CurrToken = Token.COMMA;
							break;
						case '=':
							CurrToken = Token.ASSIGN;
							break;
						// HOW CAN LEXERANALYZER DISTINGUISH ASSIGN AND EQUAL???
						case ':':
							CurrToken = Token.COLON;
							break;
						case '+':
							CurrToken = Token.PLUS;
							break;
						case '-':
							CurrToken = Token.MINUS;
							break;
						// HOW CAN LEXERANALYZER DISTINGUISH MINUS AND UNARY_MINUS???
						case '*':
							CurrToken = Token.MULTIPLY;
							break;
						case '/':
							CurrToken = Token.DIVIDE;
							break;
						case '[':
							CurrToken = Token.OPEN_BRAKET;
							break;
						case ']':
							CurrToken = Token.CLOSE_BRAKET;
							break;
						default:
						{
							System.err.println("Unexpected Token : " + I);
							System.exit(0);
						}
					}
					i_index++;
				}
				// >= > 
				else if(ch == '>')
				{
					I = I + ch;
					i_index++;
					ch = line.charAt(i_index);
					// '>=' 
					if(ch == '=')
					{
						I = I + ch;
						CurrToken = Token.GREATER_EQUAL;
						i_index++;
					}
					// '>'
					else
					{
						CurrToken = Token.GREATER_THAN;
					}
				}
				// <= <> < 
				else if(ch == '<')
				{
					I = I + ch;
					i_index++;
					ch = line.charAt(i_index);
					if(ch == '=')
					{
						I = I + ch;
						CurrToken = Token.LESS_EQUAL;
						i_index++;
					}
					else if(ch == '>')
					{
						I = I + ch;
						CurrToken = Token.NOT_EQUAL;
						i_index++;
					}
					else
					{
						CurrToken = Token.LESS_THAN;
					}
				}
				//문자열 확인
				else if(ch == '"')
				{
					I = I + ch;
					do
					{
						i_index++;
						ch = line.charAt(i_index);
						I = I + ch;
					}while(ch != '"');
					
					i_index++;
					
					CurrToken = Token.STR_LIT;
				}
				//.<0-9> <Digit>.<Digit> . 
				else if((ch >= '0' && ch <= '9') || ch == '.' )
				{
					boolean dotonce = false;
					if(ch == '.')
					{
						dotonce = true;
						i_index++;
						I = I + ch;
						ch = line.charAt(i_index);

						
						if(ch >= '0' && ch <= '9') // .<Digit>
						{
							do
							{
								I = I+ch;
								ch = line.charAt(i_index);
								
								if(dotonce == true && ch == '.')
								{
									System.err.println("Overlapped dot error.");
									System.exit(0);	
								}
								i_index++;
							}while(ch >= '0' && ch <= '9');
							
							CurrToken = Token.NUM_LIT;
						}
						else // Common dot operator.
						{
							CurrToken = Token.DOT;
						}
					}
					else // <Digit>
					{
						do
						{
							i_index++;
							I = I+ch;
							if(i_index != line.length())
							{
								ch = line.charAt(i_index);
								if(ch == '.')
								{
									if(dotonce == false)
									{
										dotonce = true;
									}
									else
									{
										System.err.println("Overlapped dot error.");
										System.exit(0);
									}
								}
							}
							else
								break;
							
						}while(ch >= '0' && ch <= '9' || ch == '.');	
						CurrToken = Token.NUM_LIT;
					}
				}
				// ID, KeyWord
				else if(ch == '_' || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <='Z'))
				{
					do
					{
						I = I+ch;
						i_index++;
						if(i_index != line.length())
							ch = line.charAt(i_index);
						else 
							break;
					}while(ch == '_' || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <='Z') || (ch >= '0' && ch <= '9'));
					
					switch(I)
					{
						case "If":
							CurrToken = Token.IF;
							break;
						case "Then":
							CurrToken = Token.THEN;
							break;
						case "ElseIf":
							CurrToken = Token.ELSEIF;
							break;
						case "Else":
							CurrToken = Token.ELSE;
							break;
						case "EndIf":
							CurrToken = Token.ENDIF;
							break;
						case "While":
							CurrToken = Token.WHILE;
							break;
						case "EndWhile":
							CurrToken = Token.ENDWHILE;
							break;
						case "For":
							CurrToken = Token.FOR;
							break;
						case "To":
							CurrToken = Token.TO;
							break;
						case "Step":
							CurrToken = Token.STEP;
							break;
						case "EndFor":
							CurrToken = Token.ENDFOR;
							break;
						case "Label":
							CurrToken = Token.LABEL;
							break;
						case "Goto":
							CurrToken = Token.GOTO;
							break;
						case "Sub":
							CurrToken = Token.SUB;
							break;
						case "EndSub":
							CurrToken = Token.ENDSUB;
							break;
						default:
							CurrToken = Token.ID;
					}
				}
				// WhiteSpace -> Skip
				else if(ch == '\t' || ch == ' ') 
				{
					i_index++;
					continue;
				}

				else
				{
					System.err.println("Lexing Error.");
					System.exit(0);
				}
				Tokenized_word.add(new SyntaxPair(I, CurrToken));
				I = "";
			}
			Lexer.add(Tokenized_word);
		}
		
		// Add EOF TokenInfo.
		SyntaxPair EOF = new SyntaxPair("EOF", Token.END_FILE);
		ArrayList<SyntaxPair> END_FILE = new ArrayList<SyntaxPair>();
		END_FILE.add(EOF);
		Lexer.add(END_FILE);	
		// TODO : Optimizing.
		
		
		for(int i = 0; i < Lexer.size() ; i++) // Print out Test Code. - Word
		{
			ArrayList<SyntaxPair> temp = Lexer.get(i);
			
			for(int j = 0; ; j++)
			{
				if(j == temp.size())
					break;
				System.out.print( temp.get(j).getSyntax() + " ");
			}
		}	
		System.out.println("\n");	
		for(int i = 0; i < Lexer.size() ; i++) // Print out Test Code. - TokenInfo
		{
			ArrayList<SyntaxPair> temp = Lexer.get(i);
			
			for(int j = 0; ; j++)
			{
				if(j == temp.size())
					break;
				System.out.print("<" + temp.get(j).getTokenInfo() + "> " );
			}
			System.out.println("");
		}	
	}
	private BufferedReader br;
	private ArrayList<String> strarr;
	private ArrayList<ArrayList<SyntaxPair>> Lexer; // Test
}

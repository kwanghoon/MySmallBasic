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
	
	public ArrayList<ArrayList<SyntaxPair>> Lexing() throws IOException
	{
		boolean Skip_CR = false; // Add, if Skip_CR is true and Next Line Token Only <CR>, then ignore it.
		
		while(true)
		{
			String read_string;
			if((read_string = br.readLine()) != null)
			{
					// delete Condition Statement.
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
				int front_index = i_index+1;
				
				if(ch == '\n' || ch == '\'') // END_LINE or Comment => Token "CR"
				{
					I = "\n";
					CurrToken = Token.CR;
					Tokenized_word.add(new SyntaxPair(I, CurrToken, front_index, index+1));
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
						case ':':
							CurrToken = Token.COLON;
							break;
						case '+':
							CurrToken = Token.PLUS;
							break;
						case '-':
							CurrToken = Token.MINUS;
							break;
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
				//臾몄옄�뿴 �솗�씤
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
				Skip_CR = false; // Added.
				Tokenized_word.add(new SyntaxPair(I, CurrToken, front_index, index+1));
				I = "";
			}
			
			// Condition Statement Added.
			if(!Skip_CR) Lexer.add(Tokenized_word);
			Skip_CR = true; // Because, Every statements have <CR> at last.
		}
		
		// Add End of Tokens
		SyntaxPair EOT = new SyntaxPair("END_OF_FILE", Token.END_OF_TOKENS,-1, -1);
		ArrayList<SyntaxPair> END_FILE = new ArrayList<SyntaxPair>();
		END_FILE.add(EOT);
		Lexer.add(END_FILE);	
		
		
		for(int i = 0; i < Lexer.size() ; i++) // Print out Test Code. - Word
		{
			int count = 0;
			ArrayList<SyntaxPair> temp = Lexer.get(i);
			System.out.print("[Line :" + temp.get(count).getLine_index() + "] ");
			
			for(int j = 0; ; j++)
			{
				if(j == temp.size())
					break;
				if(temp.get(j).getSyntax() != "\n") System.out.print( temp.get(j).getSyntax() + " ");
				else System.out.print( temp.get(j).getSyntax() );
			}
			
			System.out.println();
			count++;
		}
		
		System.out.println("\n---");
		
		for(int i = 0; i < Lexer.size() ; i++) // Print out Test Code. - TokenInfo
		{
			int count = 0;
			ArrayList<SyntaxPair> temp = Lexer.get(i);
			System.out.print("[Line :" + temp.get(count).getLine_index() + "] ");
			
			for(int j = 0; ; j++)
			{
				if(j == temp.size())
					break;
				System.out.print("<" + temp.get(j).getTokenInfo() + "> " );
			}
			System.out.println("");
			count++;
		}
		return Lexer;	
	}

	public int get_size() // Added, return Lexer.size, that means ammount of lines.
	{
		return Lexer.size();
	}
	
	private BufferedReader br;
	private ArrayList<String> strarr;
	private ArrayList<ArrayList<SyntaxPair>> Lexer; // Test
}

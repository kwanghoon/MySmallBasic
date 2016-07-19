package com.coducation.smallbasic;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.lang.*;

public class LexerAnalyzer
{
	public LexerAnalyzer(FileReader fr)
	{
		this.fr = fr;
		this.br = new BufferedReader(fr);
		this.strarr = new ArrayList<String>();
		this.Lexer = new ArrayList<ArrayList<String>>();
	}
	
	public void Lexing() throws IOException
	{
		while(true)
		{
			String read_string;
			if((read_string = br.readLine()) != null)
			{
					if(!read_string.equals("")) //공백 라인 무시.
					strarr.add(read_string); 
			}
			else
				break;
		}
		
		for(int index = 0; index < strarr.size(); index++) // Lexing Routine.
		{
			String line = strarr.get(index);
			String I = "";
			ArrayList<String> Tokenized_word = new ArrayList<String>();
			int i_index = 0;
			
			while(i_index < line.length()) //한라인에서 라인 끝까지 실행 반복 부분.
			{
				char ch = line.charAt(i_index);
				
				//개행, 주석 체크 후 패스
				if(ch == '\n' || ch == '\'')
				{
					break;
				}
				//단문자 토큰 분리
				else if(ch == '(' || ch == ')' || ch == '{' || ch == '}' ||  ch == ',' || ch == '=' || ch == ':' || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '[' || ch == ']')
					{
					I = I + ch;
					Tokenized_word.add(I);
					i_index++;
				}
				//다문자 토큰 분리
				else if(ch == '>')
				{
					I = I + ch;
					i_index++;
					ch = line.charAt(i_index);
					// '>=' 
					if(ch == '=')
					{
						I = I + ch;
						Tokenized_word.add(I);
						i_index++;
					}
					// '>'
					else
					{
						Tokenized_word.add(I);
					}
				}
				//위와 동일
				else if(ch == '<')
				{
					i_index++;
					ch = line.charAt(i_index);
					if(ch == '=')
					{
						I = I + ch;
						Tokenized_word.add(I);
						i_index++;
					}
					else if(ch == '>')
					{
						I = I + ch;
						Tokenized_word.add(I);
						i_index++;
					}
					else
					{
						Tokenized_word.add(I);
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
					Tokenized_word.add(I);
				}
				//숫자 및 '.' 확인
				else if((ch >= '0' && ch <= '9') || ch == '.' )
				{
					if(ch == '.')
					{
						i_index++;
						I = I + ch;
						ch = line.charAt(i_index);
						
						if(ch >= '0' && ch <= '9') // .<Digit>
						{
							do
							{
								I = I+ch;
								ch = line.charAt(i_index);
								i_index++;
							}while(ch >= '0' && ch <= '9');
							Tokenized_word.add(I);
						}
						else
						{
							Tokenized_word.add(I);
						}
					}
					else // <Digit>
					{
						do
						{
							i_index++;
							I = I+ch;
							if(i_index != line.length())
								ch = line.charAt(i_index);
							else
								break;
						}while(ch >= '0' && ch <= '9');
						
						Tokenized_word.add(I);
					}
				}
				// name, keyword
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
					Tokenized_word.add(I);
				}
				// WhiteSpace -> Skip
				else if(ch == '\t' || ch == ' ') 
				{
					i_index++;
					continue;
				}
				//예외 추가부분 후에 작성.
				//TODO : 예외 처리 클래스 작성.
				else
				{
					// Throw Error...?
				}

				I = "";
			}
			Lexer.add(Tokenized_word);
		}
		
		
		for(int i = 0; i < Lexer.size() ; i++) // Print out Test Code.
		{
			ArrayList<String> temp = Lexer.get(i);
			
			for(int j = 0; ; j++)
			{
				if(j == temp.size())
					break;
				
				System.out.print( temp.get(j) + " ");
			}
			System.out.println();
		}	
	}
	private FileReader fr;
	private BufferedReader br;
	private ArrayList<String> strarr;
	private ArrayList<ArrayList<String>> Lexer;
}
	

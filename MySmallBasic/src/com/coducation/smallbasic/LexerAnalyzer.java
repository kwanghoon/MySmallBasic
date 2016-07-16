package com.coducation.smallbasic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
					if(!read_string.equals("")) // 빈 줄 무시.
					strarr.add(read_string); 
			}
			else
				break;
		}
		
		for(int index = 0; index < strarr.size(); index++) // Lexing Routine.
		{
			String line = strarr.get(index); // 문장 하나를 가져옴.
			String I = ""; // 토크나이즈된 문자열, 초기는 엡실론으로 설정.
			ArrayList<String> Tokenized_word = new ArrayList<String>(); // 토크나이즈된 문자열이 저장될 어레이리스트.
			int i_index = 0; // line 한 문자씩 탐색할 index변수, 0부터 시작한다.
			
			while(i_index < line.length()) // 라인의 끝까지 실행한다.
			{
				char ch = line.charAt(i_index);
				
				if(ch == '\n')
				{
					break;
				}
				else if(ch == '(' || ch == ')' || ch == '{' || ch == '}' ||  ch == ',' || ch == '=' || ch == ':' || ch == '+' || ch == '-' || ch == '*' || ch == '/')
				{
					I = I + ch; // 문자 저장.
					Tokenized_word.add(I); // 추가.
					i_index++; // 인덱스 ++
					
				}
				else if(ch == '>')
				{
					I = I + ch; // 일단 I에 문자열 저장...
					i_index++;
					ch = line.charAt(i_index);
					if(ch == '=')
					{
						I = I + ch; // 또 저장, 여기까지 도달하면 I = ">="
						Tokenized_word.add(I);
						i_index++;
					}
					else // 그 다음문자가 =가 아닌경우. => 단순토큰 ">"
					{
						Tokenized_word.add(I);
					}
				}
				else if(ch == '<')
				{
					i_index++;
					ch = line.charAt(i_index);
					if(ch == '=')
					{
						I = I + ch; // 또 저장, 여기까지 도달하면 I = "<="
						Tokenized_word.add(I);
						i_index++;
					}
					else if(ch == '>')
					{
						I = I + ch; // 또 저장, 여기까지 도달하면 I = "<>"
						Tokenized_word.add(I);
						i_index++;
					}
					else // 여기에 도달하면, I = "<"
					{
						Tokenized_word.add(I);
					}
				}
				else if(ch == '"') // 문자열 패턴.
				{
					I = I + ch;
					do
					{
						i_index++;
						ch = line.charAt(i_index);
						I = I + ch;
					}while(ch != '"');
					
					i_index++;
					Tokenized_word.add(I); // "Hello World!" 식으로 저장.
				}
				else if((ch >= '0' && ch <= '9') || ch == '.' )
				{
					if(ch == '.') // .!@#$
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
							}while(ch >= '0' && ch <= '9');
							Tokenized_word.add(I);
						}
						else // 일반적인 . 연산자
						{
							Tokenized_word.add(I); // 추가.
						}
					}
					else // <Digit>
					{
						do
						{
							i_index++;
							I = I+ch;
							ch = line.charAt(i_index);
						}while(ch >= '0' && ch <= '9');
						
						Tokenized_word.add(I);
					}
				}
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
				else if(ch == '\t' || ch == ' ') // WhiteSpace -> Skip
				{
					i_index++;
					continue;
				}
				else // 렉싱 실패.
				{
					// Throw Error...?
				}

				I = ""; // 입력문자열 초기화.
			}
			Lexer.add(Tokenized_word);
			// TODO: 주석 생략 가능하게 추가.
		}
		
		for(int i = 0; i < Lexer.size() ; i++) // Print out Test Code.
		{
			ArrayList<String> temp = Lexer.get(i);
			
			for(int j = 0; ; j++)
			{
				if(j == temp.size())
					break;
				
				System.out.print("'" + temp.get(j) + "' ");
			}
			System.out.println();
		}	
		
	}
	
	private FileReader fr;
	private BufferedReader br;
	private ArrayList<String> strarr;
	private ArrayList<ArrayList<String>> Lexer;

}

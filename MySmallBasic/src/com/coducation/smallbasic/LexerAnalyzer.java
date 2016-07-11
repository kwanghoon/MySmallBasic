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
				strarr.add(read_string); 
			}
			else
				break;
		}
		
		for(int index = 0; index < strarr.size(); index++) // Lexing Routine.
		{
			String tempstring = strarr.get(index);
			String Token = "";
			ArrayList<String> new_Lexing = new ArrayList<String>();
			int i_index = 0;
			
			while(i_index < tempstring.length())
			{
				char ch = tempstring.charAt(i_index);
				if(ch != '.'&& ch != '(' && ch != ')' && ch != '\n' && ch != ' ' && ch != '"' && ch != ',' && ch != '=' && ch != '>' && ch != '<')
				{
					Token = Token + ch;
					i_index++;
				}
				else if(ch == '\n')
				{
					break;
				}
				else
				{
					if(Token != "")
					{
						new_Lexing.add(Token);
					}
					new_Lexing.add(ch + "");
					Token = "";
					i_index++;
				}
			}
			if(Token != "")
				new_Lexing.add(Token);
			Lexer.add(new_Lexing);
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

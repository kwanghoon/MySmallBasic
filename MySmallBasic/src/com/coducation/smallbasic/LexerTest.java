package com.coducation.smallbasic;

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class LexerTest 
{
	@Test
	public void main() throws IOException
	{
		String[] args = 
		{
				"ParsingTest.sb"
				/*"01_HelloWorld.sb",
				"02_FontYellowColor.sb",
				"03_Variables.sb",
				"04_Temperature.sb",
				"05_If.sb",
				"06_Goto.sb",
				"07_For.sb",
				"08_ForStep.sb",
				"09_While.sb",
				"10_GraphicWindow.sb",
				"11_GraphicWindowConfig.sb",
				"12_DrawLine.sb",
				"13_LineColor.sb",
				"14_LineThickness.sb",
				"15_Rectangle.sb",
				"16_Ellipse.sb",
				"17_Circle.sb",
				"18_Random.sb",
				"19_Fractal.sb",
				"20_Subroutine.sb",
				"21_Array.sb",
				"22_ArrayIndex.sb",
				"23_MultiDimArray.sb",
				"24_Event.sb",
				"25_Events.sb",
				"26_Flickr.sb",
				"Bricks.sb",
				"Testris.sb"*/
		};
		
		for(String f : args)
		{
			System.out.println("----------------------------------------------");
			System.out.println(f);
			FileReader fr = new FileReader("Sample\\" + f);
			LexerAnalyzer Lexing = new LexerAnalyzer(fr);

			Lexing.Lexing();
		}
	}
}

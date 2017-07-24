package com.coducation.smallbasic.gui;

import java.awt.Rectangle;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

//LineNumberComponent에서 textArea에 대한 정보 얻기
public class LineNumberModel
{
	JTextArea textArea;
	public LineNumberModel(JTextArea textArea)
	{
		this.textArea = textArea;
	}
	//총 몇줄인가?
	public int getNumberLines() 
	{
		return textArea.getLineCount();
	}
	
	//주어진 줄에 대한 사각형 리턴 - 높이를 얻기위해 필요
	public Rectangle getLineRect(int line) 
	{
		try
		{
			return textArea.modelToView(textArea.getLineStartOffset(line));
		} 
		catch (BadLocationException e) 
		{
			e.printStackTrace();
			return new Rectangle();
		}
	}
}
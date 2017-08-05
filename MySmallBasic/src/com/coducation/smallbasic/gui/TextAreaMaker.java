package com.coducation.smallbasic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;


public class TextAreaMaker 
{
	//글이 쓰일 textArea
	private JTextArea textArea = new JTextArea();
	private LineNumberModel lineNumberModel = new LineNumberModel(textArea);
	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);
	
	public static float fontSize = 20.0f;
	
	public TextAreaMaker(JPanel panel, JFrame frame) 
	{
		//폰트사이즈 설정
		textArea.setFont(textArea.getFont().deriveFont(fontSize));
		
		//스크롤에 textArea, lineNumber 넣기
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setRowHeaderView(lineNumberComponent);			//라인넘버넣기
		panel.add(scroller, BorderLayout.CENTER);
		
		//마우스 리스너 연결
		frame.addMouseListener(lineNumberComponent);
		
		//안에 바뀔때 마다부르기
		textArea.getDocument().addDocumentListener
		(
				new DocumentListener() 
				{
					public void changedUpdate(DocumentEvent arg0) 
					{
						lineNumberComponent.adjustWidth();
					}

					public void insertUpdate(DocumentEvent arg0) 
					{
						lineNumberComponent.adjustWidth();
					}
					public void removeUpdate(DocumentEvent arg0) 
					{
						lineNumberComponent.adjustWidth();
					}
		});
	}
	public JTextArea getTextArea()
	{
		return textArea;
	}
	//디버거에게 넘겨줄 breakPoints 정보
	public Set<Integer> getBreakPoints()
	{
		return lineNumberComponent.getBreakPoints();
	}

	public void hightLightLine(int lineNum)
	{
		textArea.getHighlighter().removeAllHighlights();
		try {
			textArea.getHighlighter().addHighlight(
					textArea.getLineStartOffset(lineNum-1), textArea.getLineEndOffset(lineNum-1), 
					new DefaultHighlighter.DefaultHighlightPainter(Color.PINK));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void removeHightLightLine()
	{
		textArea.getHighlighter().removeAllHighlights();
	}
}
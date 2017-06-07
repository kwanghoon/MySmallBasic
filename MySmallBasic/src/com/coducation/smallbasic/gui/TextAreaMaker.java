package com.coducation.smallbasic.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class TextAreaMaker 
{
	//글이 쓰일 textArea
	private JTextArea textArea = new JTextArea();
	private LineNumberModel lineNumberModel = new LineNumberModel(textArea);
	private LineNumberComponent lineNumberComponent = new LineNumberComponent(lineNumberModel);
	
	public static float fontSize = 20.0f;
	
	public TextAreaMaker(JPanel panel) 
	{
		//폰트사이즈 설정
		textArea.setFont(textArea.getFont().deriveFont(fontSize));
		
		//스크롤넣기
		JScrollPane scroller = new JScrollPane(textArea);
		scroller.setRowHeaderView(lineNumberComponent);			//라인넘버넣기
		panel.add(scroller, BorderLayout.CENTER);
		lineNumberComponent.setAlignment(LineNumberComponent.CENTER_ALIGNMENT);
		
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
}
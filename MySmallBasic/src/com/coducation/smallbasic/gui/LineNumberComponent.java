package com.coducation.smallbasic.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

// 라인넘버를 그린다. 반드시 JscrollPane에 추가되어져야한다. 
public class LineNumberComponent extends JPanel implements MouseListener {
	
	// 간격관련 변수
	private static final int HORIZONTAL_PADDING = (int) TextAreaMaker.fontSize / 3;
	private static final int VERTICAL_PADDING = (int) TextAreaMaker.fontSize / 2;

	// 디버그 dot 관련 변수
	private static final int DOT_PADDING = 3;
	private static final int DOT_WIDTH = (int) TextAreaMaker.fontSize;	// 디버그멈출 dot이 위치한 칸의 너비
	private static boolean isDotCheck = false;
	private static int dotLine = 0;
	
	// textarea 정보 가져오는 곳
	private LineNumberModel lineNumberModel;

	public LineNumberComponent() {
		super();
		addMouseListener(this);
	}

	public LineNumberComponent(LineNumberModel model) {
		this();
		setLineNumberModel(model);
	}

	public void setLineNumberModel(LineNumberModel model) {
		lineNumberModel = model;
		if (model != null) {
			adjustWidth();
		}
		repaint();
	}

	// 상황에 맞게 다시 그림
	public void adjustWidth() {
		int max = lineNumberModel.getNumberLines();
		if (getGraphics() == null) {
			return;
		}

		int width = getGraphics().getFontMetrics().stringWidth(String.valueOf(max)) + 2 * HORIZONTAL_PADDING;
		JComponent c = (JComponent) getParent();
		if (c == null) {
			return;
		}
		Dimension dimension = c.getPreferredSize();

		if (c instanceof JViewport) {// do some climbing up the component tree
										// to get the main JScrollPane view
			JViewport view = (JViewport) c;
			Component parent = view.getParent();
			if (parent != null && parent instanceof JScrollPane) {
				JScrollPane scroller = (JScrollPane) view.getParent();
				dimension = scroller.getViewport().getView().getPreferredSize();

			}

		}

		if (width > getPreferredSize().width || width < getPreferredSize().width) {
			setPreferredSize(new Dimension(width + 2 * HORIZONTAL_PADDING + DOT_WIDTH, dimension.height));
			revalidate();
			repaint();
		}

	}

	// 주어진 정보로 그리기
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (lineNumberModel == null) {
			return;
		}

		Graphics2D g2d = (Graphics2D) g;

		//breakpoint칸 그리기
		g.setColor(new Color(173, 216, 230));
		g2d.fillRect(0, 0, DOT_WIDTH, getHeight());
		
		//숫자 칸 그리기
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(getBackground());
		g2d.fillRect(DOT_WIDTH, 0, getWidth(), getHeight());
		g.setColor(getForeground());

		// 모든 라인넘버 그리기
		for (int i = 0; i < lineNumberModel.getNumberLines(); i++) {
			Rectangle rect = lineNumberModel.getLineRect(i);
			String text = String.valueOf(i + 1);
			int yPosition = rect.y + rect.height - VERTICAL_PADDING;
			int xPosition = DOT_WIDTH + (getPreferredSize().width - DOT_WIDTH) / 2
					- g.getFontMetrics().stringWidth(text) / 2;

			g2d.setFont(g2d.getFont().deriveFont(TextAreaMaker.fontSize - 1));
			g2d.drawString(String.valueOf(i + 1), xPosition, yPosition);
			
			//breakpoint 그리기
			if(isDotCheck && i == dotLine)
			{
				g.setColor(new Color(115, 156, 6));
				g2d.fillOval(0 + DOT_PADDING, rect.y + DOT_PADDING, 
						DOT_WIDTH - 2*DOT_PADDING, DOT_WIDTH - 2*DOT_PADDING);
				g.setColor(new Color(41, 64, 82));
				g2d.drawOval(0 + DOT_PADDING, rect.y + DOT_PADDING, 
						DOT_WIDTH - 2*DOT_PADDING, DOT_WIDTH - 2*DOT_PADDING);
				g.setColor(getForeground());
			}
		}
	}

	// 마우스 이벤트 처리
	public void mouseClicked(MouseEvent e) {
		
		if(e.getClickCount() != 2)
			return;
		
		// 모든 라인넘버 찾기
		for (int i = 0; i < lineNumberModel.getNumberLines(); i++) 
		{
			Rectangle rect = lineNumberModel.getLineRect(i);
			if(e.getY() > rect.y && e.getY() < rect.y + rect.height)
			{
				if(isDotCheck)
				{
					if(dotLine == i)
					{
						isDotCheck = false;
						repaint();
					}
					else
					{
						dotLine = i;
						repaint();
					}
				}
				else
				{
					isDotCheck = true;
					dotLine = i;
					repaint();
				}
				break;
			}
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}

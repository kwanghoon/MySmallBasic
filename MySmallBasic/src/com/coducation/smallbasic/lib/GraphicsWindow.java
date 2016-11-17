package com.coducation.smallbasic.lib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Eval;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class GraphicsWindow {
	public static void Clear(ArrayList<Value> args) {
		// 그래픽 창에 표시된 모든 것을 지움

	}

	public static void DrawBoundText(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 길이 범위 안에서 글자를 표시함

	}

	public static void DrawEllipse(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 타원형을 그림

	}

	public static void DrawImage(ArrayList<Value> args) {
		// 지정한 위치의 그림 파일을 불러와 그래픽 창의 지정한 위치에 표시함

	}

	public static void DrawLine(ArrayList<Value> args) {
		// 한 지점에서 다른 지점으로 직선을 그림
		if (frame == null)
			Show(args);
		panel.DrawLine(args);
	}

	public static void DrawRectangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 사각형을 그림

	}

	public static void DrawResizedImage(ArrayList<Value> args) {
		// 지정한 위치의 그림 파일을 불러와 지정한 크기로 바꿔 그래픽 창의 지정한 위치에 표시

	}

	public static void DrawText(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 길이의 범위 안에서 글자를 표시함

	}

	public static void DrawTriangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정된 펜으로 삼각형을 그림

	}

	public static void FillEllipse(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 타원형을 그림
		if (frame == null)
			Show(args);
		panel.FillEllipse(args);
	}

	public static void FillRectangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 사각형을 그림

	}

	public static void FillTriangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 삼각형을 그림

	}

	public static Value GetColorFromRGB(ArrayList<Value> args) {
		// 빨강, 초록, 파랑을 조합해 색을 만듬

		return null;
	}

	public static Value GetPixel(ArrayList<Value> args) {
		// 지정한 x와 y 좌표의 픽셀 색상값을 rgb 형식으로 가져옴

		return null;
	}

	public static Value GetRandomColor(ArrayList<Value> args) {
		// 표현 가능한 임의의 색을 가져옴

		return null;
	}

	public static void Hide(ArrayList<Value> args) {
		// 그래픽 창을 숨김

	}

	public static void SetPixel(ArrayList<Value> args) {
		// 지정한 색을 사용하여 지정한 x와 y 좌표상에 점을 찍음

	}

	public static void Show(ArrayList<Value> args) {
		// 그래픽 창을 표시함
		frame = new Frame();
	}

	public static void ShowMessage(ArrayList<Value> args) {
		// 메시지 상자를 표시함

	}

	private static class Frame extends JFrame {
		Frame() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			int width = (int) ((DoubleV) Width).getValue();
			int height = (int) ((DoubleV) Height).getValue();

			panel = new Panel(width, height);
			getContentPane().add(panel);

			pack();
			setVisible(true);
		}
	}

	private static class Panel extends JPanel implements MouseListener {
		public Panel(int width, int height) {
			cmdList = new ArrayList<>();
			addMouseListener(this);
			setPreferredSize(new Dimension(width, height));
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (Cmd cmd : cmdList) {
				switch (cmd.cmd) {
				case NOCOMMAND:
					break;
				case DRAWLINE:
					DrawLineCmd dlc = (DrawLineCmd) cmd;
					g.drawLine(dlc.x1, dlc.y1, dlc.x2, dlc.y2);
					break;
				case FILLELLIPSE:
					FillEllipseCmd fec = (FillEllipseCmd) cmd;
					String color = ((StrV) BrushColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.fillOval(fec.x, fec.y, fec.w, fec.h);
					break;
				}
			}
		}

		final int NOCOMMAND = 0;
		final int DRAWLINE = 1;
		final int DRAWRECTANGLE = 2;
		final int FILLELLIPSE = 3;

		ArrayList<Cmd> cmdList;

		public void DrawLine(ArrayList<Value> args) {
			DrawLineCmd cmd = new DrawLineCmd();

			cmd.cmd = DRAWLINE;

			cmd.x1 = (int) ((DoubleV) args.get(0)).getValue();
			cmd.y1 = (int) ((DoubleV) args.get(1)).getValue();
			cmd.x2 = (int) ((DoubleV) args.get(2)).getValue();
			cmd.y2 = (int) ((DoubleV) args.get(3)).getValue();

			cmdList.add(cmd);

			repaint();
		}

		public void FillEllipse(ArrayList<Value> args) {
			FillEllipseCmd cmd = new FillEllipseCmd();

			cmd.cmd = FILLELLIPSE;

			cmd.x = (int) ((DoubleV) args.get(0)).getValue();
			cmd.y = (int) ((DoubleV) args.get(1)).getValue();
			cmd.w = (int) ((DoubleV) args.get(2)).getValue();
			cmd.h = (int) ((DoubleV) args.get(3)).getValue();

			cmdList.add(cmd);

			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (MouseDown != null) {
				MouseX = new DoubleV(e.getX());
				MouseY = new DoubleV(e.getY());

				Eval.eval(MouseDown);
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	private static class Cmd {
		int cmd;
	}

	private static class DrawLineCmd extends Cmd {
		int x1, y1, x2, y2;
	}

	private static class FillEllipseCmd extends Cmd {
		int x, y, w, h;
	}

	private static Frame frame = null;
	private static Panel panel = null;

	public static Value BackgroundColor = new StrV("#FFFFFF"); // white
	public static Value BrushColor = new StrV("#6A5ACD");
	public static Value CanResize;
	public static Value FontBold;
	public static Value FontItalic;
	public static Value FontName;
	public static Value FontSize;
	public static Value Height = new DoubleV(480);
	public static Value LastKey;
	public static Value LastText;
	public static Value Left;
	public static Value MouseX;
	public static Value MouseY;
	public static Value PenColor = new StrV("#000000"); // black
	public static Value PenWidth;
	public static Value Title;
	public static Value Top;
	public static Value Width = new DoubleV(640);

	public static Value KeyDown;
	public static Value KeyUp;
	public static Value MouseDown;
	public static Value MouseMove;
	public static Value MouseUp;
	public static Value TextInput;
}
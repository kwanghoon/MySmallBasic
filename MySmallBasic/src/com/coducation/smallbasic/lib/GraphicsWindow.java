package com.coducation.smallbasic.lib;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Eval;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class GraphicsWindow {
	public static void Clear(ArrayList<Value> args) {
		// 그래픽 창에 표시된 모든 것을 지움
		panel.cmdList.clear();
		panel.repaint();
	}

	public static void DrawBoundText(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 길이 범위 안에서 글자를 표시함
		// x, y, text
		if (frame == null)
			Show(args);
		panel.DrawBoundText(args);
	}

	public static void DrawEllipse(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 타원형을 그림
		// x, y, width, height
		if (frame == null)
			Show(args);
		panel.DrawEllipse(args);
	}

	public static void DrawImage(ArrayList<Value> args) {
		// 지정한 위치의 그림 파일을 불러와 그래픽 창의 지정한 위치에 표시함
		// imageName, x, y
		if (frame == null)
			Show(args);
		panel.DrawImage(args);
	}

	public static void DrawLine(ArrayList<Value> args) {
		// 한 지점에서 다른 지점으로 직선을 그림
		// x1, y1, x2, y2
		if (frame == null)
			Show(args);
		panel.DrawLine(args);
	}

	public static void DrawRectangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 사각형을 그림
		// x, y, width, height
		if (frame == null)
			Show(args);
		panel.DrawRectangle(args);
	}

	public static void DrawResizedImage(ArrayList<Value> args) {
		// 지정한 위치의 그림 파일을 불러와 지정한 크기로 바꿔 그래픽 창의 지정한 위치에 표시
		// imageName, x, y, width, height
		if (frame == null)
			Show(args);
		panel.DrawResizedImage(args);
	}

	public static void DrawText(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 길이의 범위 안에서 글자를 표시함
		// x, y, text
		if (frame == null)
			Show(args);
		panel.DrawText(args);
	}

	public static void DrawTriangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정된 펜으로 삼각형을 그림
		// x1, y1, x2, y2, x3, y3
		if (frame == null)
			Show(args);
		panel.DrawTriangle(args);
	}

	public static void FillEllipse(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 타원형을 그림
		// x, y, width, height
		if (frame == null)
			Show(args);
		panel.FillEllipse(args);
	}

	public static void FillRectangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 사각형을 그림
		// x, y, width, height
		if (frame == null)
			Show(args);
		panel.FillRectangle(args);
	}

	public static void FillTriangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 삼각형을 그림
		// x1, y1, x2, y2, x3, y3
		if (frame == null)
			Show(args);
		panel.FillTriangle(args);
	}

	public static Value GetColorFromRGB(ArrayList<Value> args) {
		// 빨강, 초록, 파랑을 조합해 색을 만듬
		// red, green, blue
		if (args.size() == 3) {
			boolean isInteger[] = { false, false, false };
			int values[] = new int[3];

			for (int i = 0; i < args.size(); i++) {
				if (args.get(i) instanceof DoubleV) {
					isInteger[i] = true;
					values[i] = (int) ((DoubleV) args.get(i)).getValue();
				} else if (args.get(i) instanceof StrV) {
					if (((StrV) args.get(i)).isNumber()) {
						isInteger[i] = true;
						values[i] = (int) ((StrV) args.get(i)).parseDouble();
					}
				} else {
					throw new InterpretException("Unexpected type " + args.get(i));
				}
			}

			// 모든 값이 숫자일 경우 색을 만들어냄
			if (isInteger[0] && isInteger[1] && isInteger[2]) {
				String red = Integer.toHexString(values[0]);
				String green = Integer.toHexString(values[1]);
				String blue = Integer.toHexString(values[2]);

				return new StrV("#" + red + green + blue);
			}
		} else {
			throw new InterpretException("Unexpected # of args " + args.size());
		}
		return null;
	}

	public static Value GetPixel(ArrayList<Value> args) {
		// 지정한 x와 y 좌표의 픽셀 색상값을 rgb 형식으로 가져옴
		// x, y
		if (frame == null)
			Show(args);
		StrV color = (StrV) panel.GetPixel(args);

		return color;
	}

	public static Value GetRandomColor(ArrayList<Value> args) {
		// 표현 가능한 임의의 색을 가져옴
		if (frame == null)
			Show(args);
		Random r = new Random(Calendar.getInstance().get(Calendar.MILLISECOND));
		int i = r.nextInt(colorInfo.length);
		return hexColor(new StrV(colorInfo[i]), defaultPenColor);
	}

	public static void Hide(ArrayList<Value> args) {
		// 그래픽 창을 숨김
		if (frame == null)
			Show(args);
		frame.setVisible(false);
	}

	public static void SetPixel(ArrayList<Value> args) {
		// 지정한 색을 사용하여 지정한 x와 y 좌표상에 점을 찍음
		// x, y, color
		if (args.size() == 3) {
			if (frame == null)
				Show(args);
			panel.SetPixel(args);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Show(ArrayList<Value> args) {
		// 그래픽 창을 표시함
		if (frame != null)
			frame.setVisible(true);
		else
			frame = new Frame();
	}

	public static void ShowMessage(ArrayList<Value> args) {
		// 메시지 상자를 표시함
		// text, title
		if (frame == null)
			Show(args);

		String title = "", text = "";
		if (args.size() == 2) {
			if (args.get(0) instanceof StrV)
				text = ((StrV) args.get(0)).getValue();
			else if (args.get(0) instanceof DoubleV)
				text = ((DoubleV) args.get(0)).toString();

			if (args.get(1) instanceof StrV)
				title = ((StrV) args.get(1)).getValue();
			else if (args.get(1) instanceof DoubleV)
				title = ((DoubleV) args.get(1)).toString();
		} else
			throw new InterpretException("Unexpected # of args " + args.size());

		JOptionPane.showMessageDialog(frame, text, title, JOptionPane.INFORMATION_MESSAGE);

	}

	private static class Frame extends JFrame {
		Frame() {
			setTitle(Title.toString());
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			int width = (int) ((DoubleV) Width).getValue();
			int height = (int) ((DoubleV) Height).getValue();

			panel = new Panel(width, height);
			getContentPane().add(panel);

			pack();
			setVisible(true);
		}
	}

	private static class Panel extends JPanel implements MouseListener, KeyListener, MouseMotionListener {
		public Panel(int width, int height) {
			this.setOpaque(true);
			cmdList = new ArrayList<>();
			pixelList = new ArrayList<>();
			addMouseListener(this);
			addKeyListener(this);
			setPreferredSize(new Dimension(width, height));

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			String backColor = ((StrV) BackgroundColor).getValue();
			setBackground(new Color(Integer.parseInt(backColor.substring(1), 16)));
			String color;

			for (Cmd cmd : cmdList) {
				((Graphics2D) g).setStroke(new BasicStroke((float) ((DoubleV) PenWidth).getValue()));
				switch (cmd.cmd) {
				case NOCOMMAND:
					break;
				case DRAWBOUNDTEXT:

					break;
				case DRAWELLIPSE:
					DrawEllipseCmd dec = (DrawEllipseCmd) cmd;
					color = ((StrV) PenColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.drawOval(dec.x, dec.y, dec.w, dec.h);
					break;
				case DRAWIMAGE:
					DrawImageCmd dic = (DrawImageCmd) cmd;
					ImageIcon icon = new ImageIcon(dic.imageName);
					Image img = icon.getImage();
					g.drawImage(img, dic.x, dic.y, this);
					break;
				case DRAWLINE:
					DrawLineCmd dlc = (DrawLineCmd) cmd;
					color = ((StrV) PenColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.drawLine(dlc.x1, dlc.y1, dlc.x2, dlc.y2);
					break;
				case DRAWRECTANGLE:
					DrawRectangleCmd drc = (DrawRectangleCmd) cmd;
					color = ((StrV) PenColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.drawRect(drc.x, drc.y, drc.w, drc.h);
					break;
				case DRAWRESIZEDIMAGE:
					DrawResizedImageCmd dric = (DrawResizedImageCmd) cmd;
					ImageIcon iconResized = new ImageIcon(dric.imageName);
					Image imgResized = iconResized.getImage();
					g.drawImage(imgResized, dric.x, dric.y, dric.w, dric.h, this);
					break;
				case DRAWTEXT:
					DrawTextCmd dtc = (DrawTextCmd) cmd;
					color = ((StrV) PenColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.drawString(dtc.text, dtc.x, dtc.y);
					break;
				case DRAWTRIANGLE:
					DrawTriangleCmd dtrc = (DrawTriangleCmd) cmd;
					color = ((StrV) PenColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.drawPolygon(dtrc.x, dtrc.y, 3);
					break;

				case FILLELLIPSE:
					FillEllipseCmd fec = (FillEllipseCmd) cmd;
					color = ((StrV) BrushColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.fillOval(fec.x, fec.y, fec.w, fec.h);
					break;
				case FILLRECTANGLE:
					FillRectangleCmd frc = (FillRectangleCmd) cmd;
					color = ((StrV) BrushColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.fillRect(frc.x, frc.y, frc.w, frc.h);
					break;
				case FILLTRIANGLE:
					FillTriangleCmd ftc = (FillTriangleCmd) cmd;
					color = ((StrV) BrushColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.fillPolygon(ftc.x, ftc.y, 3);
					break;
				}
			}
			if (pixelList.size() != 0) {
				for (Pixel pixel : pixelList) {
					for (int i = 0; i < (int) ((DoubleV) Height).getValue(); i++) {
						for (int j = 0; j < (int) ((DoubleV) Width).getValue(); j++) {
							if (pixel.x == j && pixel.y == i) {
								String pColor = pixel.color.getValue();
								if (pColor.contains("#")) {
									// hex 상태
									g.setColor(new Color(Integer.parseInt(pColor.substring(1), 16)));
								} else {
									// Red, Blue, ...
									pColor = colorMap.get(pColor.toUpperCase());
									g.setColor(new Color(Integer.parseInt(pColor.substring(1), 16)));
								}
								g.drawLine(j, i, j, i);
								break;
							}
						}
					}
				}
			}
		}

		public Value GetPixel(ArrayList<Value> args) {
			try {
				if (args.size() == 2) {
					Robot robot = new Robot();
					boolean isInteger[] = { false, false };
					int values[] = new int[2];

					for (int i = 0; i < args.size(); i++) {
						if (args.get(i) instanceof DoubleV) {
							isInteger[i] = true;
							values[i] = (int) ((DoubleV) args.get(i)).getValue();
						} else if (args.get(i) instanceof StrV) {
							if (((StrV) args.get(i)).isNumber()) {
								isInteger[i] = true;
								values[i] = (int) ((StrV) args.get(i)).parseDouble();
							}
						} else
							throw new InterpretException("Unexpected type " + args.get(i));
					}

					if (isInteger[0] && isInteger[1]) {
						GraphicsConfiguration gc = getGraphicsConfiguration();
						Rectangle r = gc.getBounds();
						Color color = robot.getPixelColor(r.x + values[0], r.y + values[1]);

						String red = Integer.toHexString(color.getRed());
						String green = Integer.toHexString(color.getGreen());
						String blue = Integer.toHexString(color.getBlue());

						StrV rgbColor = new StrV("#" + red + green + blue);

						return rgbColor;
					}
				} else
					throw new InterpretException("Unexpected # of args " + args.size());
			} catch (AWTException e) {
				e.printStackTrace();
			}
			return null;
		}

		public void SetPixel(ArrayList<Value> args) {
			int values[] = new int[2];
			boolean isInteger[] = { false, false };

			for (int i = 0; i < 2; i++) {
				if (args.get(i) instanceof DoubleV) {
					isInteger[i] = true;
					values[i] = (int) ((DoubleV) args.get(i)).getValue();
				} else if (args.get(i) instanceof StrV) {
					if (((StrV) args.get(i)).isNumber()) {
						isInteger[i] = true;
						values[i] = (int) ((StrV) args.get(i)).parseDouble();
					}
				} else
					throw new InterpretException("Unexpected type " + args.get(i));
			}

			if (isInteger[0] && isInteger[1] && args.get(2) instanceof StrV) {
				Pixel pixel = new Pixel();
				pixel.x = values[0];
				pixel.y = values[1];
				pixel.color = (StrV) args.get(2);

				pixelList.add(pixel);
			}
		}

		ArrayList<Pixel> pixelList;

		private static class Pixel {
			StrV color;
			int x;
			int y;
		}

		final int NOCOMMAND = 0;
		final int DRAWBOUNDTEXT = 1;
		final int DRAWELLIPSE = 2;
		final int DRAWIMAGE = 3;
		final int DRAWLINE = 4;
		final int DRAWRECTANGLE = 5;
		final int DRAWRESIZEDIMAGE = 6;
		final int DRAWTEXT = 7;
		final int DRAWTRIANGLE = 8;

		final int FILLELLIPSE = 9;
		final int FILLRECTANGLE = 10;
		final int FILLTRIANGLE = 11;

		ArrayList<Cmd> cmdList;

		public void DrawBoundText(ArrayList<Value> args) {
			DrawBoundTextCmd cmd = new DrawBoundTextCmd();

			if (args.size() == 3) {
				cmd.cmd = DRAWBOUNDTEXT;

				int[] values = new int[2];
				boolean[] isInteger = new boolean[2];

				for (int i = 0; i < 2; i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						} else {
							isInteger[i] = false;
							throw new InterpretException("Unexpected type " + args.get(i));
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}

				if (isInteger[0] && isInteger[1]) {
					cmd.x = values[0];
					cmd.y = values[1];
				}

				if (args.get(2) instanceof StrV)
					cmd.text = ((StrV) args.get(2)).getValue();
				else if (args.get(2) instanceof DoubleV)
					cmd.text = ((DoubleV) args.get(2)).toString();

				cmdList.add(cmd);

				repaint();
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void DrawEllipse(ArrayList<Value> args) {
			DrawEllipseCmd cmd = new DrawEllipseCmd();

			if (args.size() == 4) {
				cmd.cmd = DRAWELLIPSE;

				boolean[] isInteger = new boolean[4];
				int[] values = new int[4];

				for (int i = 0; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}

				}
				if (isInteger[0] && isInteger[1] && isInteger[2] && isInteger[3]) {
					cmd.x = values[0];
					cmd.y = values[1];
					cmd.w = values[2];
					cmd.h = values[3];

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void DrawImage(ArrayList<Value> args) {
			DrawImageCmd cmd = new DrawImageCmd();

			if (args.size() == 3) {
				cmd.cmd = DRAWIMAGE;

				int[] values = new int[2];
				boolean[] isInteger = new boolean[2];

				for (int i = 1; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i - 1] = true;
						values[i - 1] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i - 1] = true;
							values[i - 1] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i - 1] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}

				if (args.get(0) instanceof StrV && isInteger[0] && isInteger[1]) {
					cmd.imageName = ((StrV) args.get(0)).getValue();
					cmd.x = values[0];
					cmd.y = values[1];

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void DrawLine(ArrayList<Value> args) {
			DrawLineCmd cmd = new DrawLineCmd();

			if (args.size() == 4) {
				cmd.cmd = DRAWLINE;

				int[] values = new int[4];
				boolean[] isInteger = new boolean[4];

				for (int i = 0; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}
				if (isInteger[0] && isInteger[1] && isInteger[2] && isInteger[3]) {
					cmd.x1 = values[0];
					cmd.y1 = values[1];
					cmd.x2 = values[2];
					cmd.y2 = values[3];

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void DrawRectangle(ArrayList<Value> args) {
			DrawRectangleCmd cmd = new DrawRectangleCmd();

			if (args.size() == 4) {
				cmd.cmd = DRAWRECTANGLE;

				int[] values = new int[4];
				boolean[] isInteger = new boolean[4];

				for (int i = 0; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}

				if (isInteger[0] && isInteger[1] && isInteger[2] && isInteger[3]) {
					cmd.x = values[0];
					cmd.y = values[1];
					cmd.w = values[2];
					cmd.h = values[3];

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void DrawResizedImage(ArrayList<Value> args) {
			DrawResizedImageCmd cmd = new DrawResizedImageCmd();

			if (args.size() == 5) {
				cmd.cmd = DRAWRESIZEDIMAGE;

				int[] values = new int[4];
				boolean[] isInteger = new boolean[4];

				for (int i = 1; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i - 1] = true;
						values[i - 1] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i - 1] = true;
							values[i - 1] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i - 1] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}

				if (args.get(0) instanceof StrV && isInteger[0] && isInteger[1] && isInteger[2] && isInteger[3]) {
					cmd.imageName = ((StrV) args.get(0)).getValue();
					cmd.x = values[0];
					cmd.y = values[1];
					cmd.w = values[2];
					cmd.h = values[3];

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void DrawText(ArrayList<Value> args) {
			DrawTextCmd cmd = new DrawTextCmd();

			if (args.size() == 3) {

				cmd.cmd = DRAWTEXT;
				int[] values = new int[2];
				boolean[] isInteger = new boolean[2];

				for (int i = 1; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}

				if (args.get(0) instanceof StrV && isInteger[0] && isInteger[1]) {
					cmd.x = values[0];
					cmd.y = values[1];
					cmd.text = ((StrV) args.get(2)).getValue();

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void DrawTriangle(ArrayList<Value> args) {
			DrawTriangleCmd cmd = new DrawTriangleCmd();

			if (args.size() == 6) {
				cmd.cmd = DRAWTRIANGLE;

				int[] values = new int[6];
				boolean[] isInteger = new boolean[6];

				for (int i = 0; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}
				if (isInteger[0] && isInteger[1] && isInteger[2] && isInteger[3] && isInteger[4] && isInteger[5]) {
					cmd.x[0] = values[0];
					cmd.y[0] = values[1];
					cmd.x[1] = values[2];
					cmd.y[1] = values[3];
					cmd.x[2] = values[4];
					cmd.y[2] = values[5];

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void FillEllipse(ArrayList<Value> args) {
			FillEllipseCmd cmd = new FillEllipseCmd();

			if (args.size() == 4) {
				cmd.cmd = FILLELLIPSE;

				int[] values = new int[4];
				boolean[] isInteger = new boolean[4];

				for (int i = 0; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}

				if (isInteger[0] && isInteger[1] && isInteger[2] && isInteger[3]) {

					cmd.x = (int) ((DoubleV) args.get(0)).getValue();
					cmd.y = (int) ((DoubleV) args.get(1)).getValue();
					cmd.w = (int) ((DoubleV) args.get(2)).getValue();
					cmd.h = (int) ((DoubleV) args.get(3)).getValue();

					cmdList.add(cmd);

					repaint();
				}
			} else
				throw new InterpretException("Unexpected # of args " + args.size());
		}

		public void FillRectangle(ArrayList<Value> args) {
			FillRectangleCmd cmd = new FillRectangleCmd();

			if (args.size() == 4) {
				cmd.cmd = FILLRECTANGLE;

				int[] values = new int[4];
				boolean[] isInteger = new boolean[4];

				for (int i = 0; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}
				if (isInteger[0] && isInteger[1] && isInteger[2] && isInteger[3]) {
					cmd.x = values[0];
					cmd.y = values[1];
					cmd.w = values[2];
					cmd.h = values[3];

					cmdList.add(cmd);

					repaint();
				}
			} else
				throw new InterpretException("Unexpected # of args " + args.size());
		}

		public void FillTriangle(ArrayList<Value> args) {
			FillTriangleCmd cmd = new FillTriangleCmd();

			if (args.size() == 6) {
				cmd.cmd = FILLTRIANGLE;

				int[] values = new int[6];
				boolean[] isInteger = new boolean[6];

				for (int i = 0; i < args.size(); i++) {
					if (args.get(i) instanceof DoubleV) {
						isInteger[i] = true;
						values[i] = (int) ((DoubleV) args.get(i)).getValue();
					} else if (args.get(i) instanceof StrV) {
						if (((StrV) args.get(i)).isNumber()) {
							isInteger[i] = true;
							values[i] = (int) ((StrV) args.get(i)).parseDouble();
						}
					} else {
						isInteger[i] = false;
						throw new InterpretException("Unexpected type " + args.get(i));
					}
				}
				if (isInteger[0] && isInteger[1] && isInteger[2] && isInteger[3] && isInteger[4] && isInteger[5]) {
					cmd.x[0] = values[0];
					cmd.y[0] = values[1];
					cmd.x[1] = values[2];
					cmd.y[1] = values[3];
					cmd.x[2] = values[4];
					cmd.y[2] = values[5];

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
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
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {

		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		public void addNotify() {
			super.addNotify();
			requestFocus();
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (KeyDown != null) {
				LastKey = new StrV(e.getKeyChar());

				Eval.eval(KeyDown);
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

	private static class Cmd {
		int cmd;
	}

	private static class DrawBoundTextCmd extends Cmd {
		int x, y, w;
		String text;
	}

	private static class DrawEllipseCmd extends Cmd {
		int x, y, w, h;
	}

	private static class DrawImageCmd extends Cmd {
		int x, y;
		String imageName;
	}

	private static class DrawLineCmd extends Cmd {
		int x1, y1, x2, y2;
	}

	private static class DrawRectangleCmd extends Cmd {
		int x, y, w, h;
	}

	private static class DrawResizedImageCmd extends Cmd {
		String imageName;
		int x, y, w, h;
	}

	private static class DrawTextCmd extends Cmd {
		int x, y;
		String text;
	}

	private static class DrawTriangleCmd extends Cmd {
		int x[] = new int[3];
		int y[] = new int[3];
	}

	private static class FillEllipseCmd extends Cmd {
		int x, y, w, h;
	}

	private static class FillRectangleCmd extends Cmd {
		int x, y, w, h;
	}

	private static class FillTriangleCmd extends Cmd {
		int x[] = new int[3];
		int y[] = new int[3];
	}

	private static Frame frame = null;
	private static Panel panel = null;

	private static final Value defaultBrushColor = new StrV("#6A5ACD");
	private static final Value defaultPenColor = new StrV("#000000");
	private static final Value defaultBackgroundColor = new StrV("#FFFFFF");

	public static Value BackgroundColor = GraphicsWindow.defaultBackgroundColor; // white
	public static Value BrushColor = GraphicsWindow.defaultBrushColor;
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
	public static Value PenColor = defaultPenColor; // black
	public static Value PenWidth = new DoubleV(2);
	public static Value Title = new StrV("Small Basic Graphics Window");
	public static Value Top;
	public static Value Width = new DoubleV(640);

	public static Value KeyDown;
	public static Value KeyUp;
	public static Value MouseDown;
	public static Value MouseMove;
	public static Value MouseUp;
	public static Value TextInput;

	private static String[] colorInfo = { "IndianRed", "#CD5C5C", "LightCoral", "#F08080", "Salmon", "#FA8072",
			"DarkSalmon", "#E9967A", "LightSalmon", "#FFA07A", "Crimson", "#DC143C", "Red", "#FF0000", "FireBrick",
			"#B22222", "DarkRed", "#8B0000", "Pink", "#FFC0CB", "LightPink", "#FFB6C1", "HotPink", "#FF69B4",
			"DeepPink", "#FF1493", "MediumVioletRed", "#C71585", "PaleVioletRed", "#DB7093", "LightSalmon", "#FFA07A",
			"Coral", "#FF7F50", "Tomato", "#FF6347", "OrangeRed", "#FF4500", "DarkOrange", "#FF8C00", "Orange",
			"#FFA500", "Gold", "#FFD700", "Yellow", "#FFFF00", "LightYellow", "#FFFFE0", "LemonChiffon", "#FFFACD",
			"LightGoldenrodYellow", "#FAFAD2", "PapayaWhip", "#FFEFD5", "Moccasin", "#FFE4B5", "PeachPuff", "#FFDAB9",
			"PaleGoldenrod", "#EEE8AA", "Khaki", "#F0E68C", "DarkKhaki", "#BDB76B", "Lavender", "#E6E6FA", "Thistle",
			"#D8BFD8", "Plum", "#DDA0DD", "Violet", "#EE82EE", "Orchid", "#DA70D6", "Fuchsia", "#FF00FF", "Magenta",
			"#FF00FF", "MediumOrchid", "#BA55D3", "MediumPurple", "#9370DB", "BlueViolet", "#8A2BE2", "DarkViolet",
			"#9400D3", "DarkOrchid", "#9932CC", "DarkMagenta", "#8B008B", "Purple", "#800080", "Indigo", "#4B0082",
			"SlateBlue", "#6A5ACD", "DarkSlateBlue", "#483D8B", "MediumSlateBlue", "#7B68EE", "GreenYellow", "#ADFF2F",
			"Chartreuse", "#7FFF00", "LawnGreen", "#7CFC00", "Lime", "#00FF00", "LimeGreen", "#32CD32", "PaleGreen",
			"#98FB98", "LightGreen", "#90EE90", "MediumSpringGreen", "#00FA9A", "SpringGreen", "#00FF7F",
			"MediumSeaGreen", "#3CB371", "SeaGreen", "#2E8B57", "ForestGreen", "#228B22", "Green", "#008000",
			"DarkGreen", "#006400", "YellowGreen", "#9ACD32", "OliveDrab", "#6B8E23", "Olive", "#808000",
			"DarkOliveGreen", "#556B2F", "MediumAquamarine", "#66CDAA", "DarkSeaGreen", "#8FBC8F", "LightSeaGreen",
			"#20B2AA", "DarkCyan", "#008B8B", "Teal", "#008080", "Aqua", "#00FFFF", "Cyan", "#00FFFF", "LightCyan",
			"#E0FFFF", "PaleTurquoise", "#AFEEEE", "Aquamarine", "#7FFFD4", "Turquoise", "#40E0D0", "MediumTurquoise",
			"#48D1CC", "DarkTurquoise", "#00CED1", "CadetBlue", "#5F9EA0", "SteelBlue", "#4682B4", "LightSteelBlue",
			"#B0C4DE", "PowderBlue", "#B0E0E6", "LightBlue", "#ADD8E6", "SkyBlue", "#87CEEB", "LightSkyBlue", "#87CEFA",
			"DeepSkyBlue", "#00BFFF", "DodgerBlue", "#1E90FF", "CornflowerBlue", "#6495ED", "MediumSlateBlue",
			"#7B68EE", "RoyalBlue", "#4169E1", "Blue", "#0000FF", "MediumBlue", "#0000CD", "DarkBlue", "#00008B",
			"Navy", "#000080", "MidnightBlue", "#191970", "Cornsilk", "#FFF8DC", "BlanchedAlmond", "#FFEBCD", "Bisque",
			"#FFE4C4", "NavajoWhite", "#FFDEAD", "Wheat", "#F5DEB3", "BurlyWood", "#DEB887", "Tan", "#D2B48C",
			"RosyBrown", "#BC8F8F", "SandyBrown", "#F4A460", "Goldenrod", "#DAA520", "DarkGoldenrod", "#B8860B", "Peru",
			"#CD853F", "Chocolate", "#D2691E", "SaddleBrown", "#8B4513", "Sienna", "#A0522D", "Brown", "#A52A2A",
			"Maroon", "#800000", "White", "#FFFFFF", "Snow", "#FFFAFA", "Honeydew", "#F0FFF0", "MintCream", "#F5FFFA",
			"Azure", "#F0FFFF", "AliceBlue", "#F0F8FF", "GhostWhite", "#F8F8FF", "WhiteSmoke", "#F5F5F5", "Seashell",
			"#FFF5EE", "Beige", "#F5F5DC", "OldLace", "#FDF5E6", "FloralWhite", "#FFFAF0", "Ivory", "#FFFFF0",
			"AntiqueWhite", "#FAEBD7", "Linen", "#FAF0E6", "LavenderBlush", "#FFF0F5", "MistyRose", "#FFE4E1",
			"Gainsboro", "#DCDCDC", "LightGray", "#D3D3D3", "Silver", "#C0C0C0", "DarkGray", "#A9A9A9", "Gray",
			"#808080", "DimGray", "#696969", "LightSlateGray", "#778899", "SlateGray", "#708090", "DarkSlateGray",
			"#2F4F4F", "Black", "#000000" };

	private static HashMap<String, String> colorMap;

	static {
		colorMap = new HashMap<String, String>();
		for (int i = 0; i < colorInfo.length; i += 2) {
			colorMap.put(colorInfo[i].toUpperCase(), colorInfo[i + 1]);
		}
	}

	public static void notifyFieldAssign(String fieldName) {
		if (frame == null)
			Show(new ArrayList<Value>());

		if ("BrushColor".equalsIgnoreCase(fieldName)) {
			if (BrushColor instanceof StrV) {
				String v = ((StrV) BrushColor).getValue();
				BrushColor = hexColor((StrV) BrushColor, GraphicsWindow.defaultBrushColor);
			} else
				throw new InterpretException("BrushColor: Unexpected value" + BrushColor.toString());
		} else if ("PenColor".equalsIgnoreCase(fieldName)) {
			if (PenColor instanceof StrV) {
				String v = ((StrV) PenColor).getValue();
				PenColor = hexColor((StrV) PenColor, GraphicsWindow.defaultPenColor);
			} else
				throw new InterpretException("PenColor: Unexpected value" + BrushColor.toString());
		} else if ("BackgroundColor".equalsIgnoreCase(fieldName)) {
			if (BackgroundColor instanceof StrV) {
				String v = ((StrV) BackgroundColor).getValue();
				BackgroundColor = hexColor((StrV) BackgroundColor, GraphicsWindow.defaultBackgroundColor);
			} else
				throw new InterpretException("BackgroundColor: Unexpected value" + BrushColor.toString());
		} else {
		}
	}

	public static Value hexColor(StrV strv, Value defColor) {
		String v = strv.getValue();
		// 1) #xxxxxx
		if (v.charAt(0) == '#') {
			try {
				Integer.parseInt(v.substring(1), 16);
				return strv;
			} catch (NumberFormatException e) {
				return defColor;
			}
		}
		// 2) Blue, Red, ...
		else {
			String hex = colorMap.get(v.toUpperCase());

			if (hex == null)
				return defColor;
			else
				return new StrV(hex);
		}

	}

	public static void notifyFieldRead(String fieldName) {
		if (frame == null)
			Show(new ArrayList<Value>());
	}
}
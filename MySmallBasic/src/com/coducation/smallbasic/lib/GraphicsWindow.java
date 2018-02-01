package com.coducation.smallbasic.lib;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Eval;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;
import com.coducation.smallbasic.util.Pair;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class GraphicsWindow {	
	public static void Clear(ArrayList<Value> args) {
		// 그래픽 창에 표시된 모든 것을 지움
		panel.cmdList.clear();
		controlMap.clear();
		if (container != null) {
			container.removeAll();
			container.revalidate();
			container.repaint();
		}
		panel.repaint();
	}
	
	public static void DrawBoundText(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 길이 범위 안에서 글자를 표시함
		// x, y, width, text
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.DrawBoundText(args);
	}

	public static void DrawEllipse(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 타원형을 그림
		// x, y, width, height
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.DrawEllipse(args);
	}

	public static void DrawImage(ArrayList<Value> args) {
		// 지정한 위치의 그림 파일을 불러와 그래픽 창의 지정한 위치에 표시함
		// imageName, x, y
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.DrawImage(args);
	}

	public static void DrawLine(ArrayList<Value> args) {
		// 한 지점에서 다른 지점으로 직선을 그림
		// x1, y1, x2, y2
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.DrawLine(args);
	}

	public static void DrawRectangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 펜으로 사각형을 그림
		// x, y, width, height
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.DrawRectangle(args);
	}

	public static void DrawResizedImage(ArrayList<Value> args) {
		// 지정한 위치의 그림 파일을 불러와 지정한 크기로 바꿔 그래픽 창의 지정한 위치에 표시
		// imageName, x, y, width, height
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.DrawResizedImage(args);
	}

	public static void DrawText(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정한 길이의 범위 안에서 글자를 표시함
		// x, y, text
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.DrawText(args);
	}

	public static void DrawTriangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 지정된 펜으로 삼각형을 그림
		// x1, y1, x2, y2, x3, y3
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.DrawTriangle(args);
	}

	public static void FillEllipse(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 타원형을 그림
		// x, y, width, height
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.FillEllipse(args);
	}

	public static void FillRectangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 사각형을 그림
		// x, y, width, height
		if (frame == null)
			Show(new ArrayList<Value>());
		panel.FillRectangle(args);
	}

	public static void FillTriangle(ArrayList<Value> args) {
		// 그래픽 창의 지정한 위치에 색이 채워진 삼각형을 그림
		// x1, y1, x2, y2, x3, y3
		if (frame == null)
			Show(new ArrayList<Value>());
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
			Show(new ArrayList<Value>());
		StrV color = (StrV) panel.GetPixel(args);

		return color;
	}

	public static Value GetRandomColor(ArrayList<Value> args) {
		// 표현 가능한 임의의 색을 가져옴
		if (args.size() == 0) {
			if (frame == null)
				Show(new ArrayList<Value>());
			Random r = new Random(Calendar.getInstance().get(Calendar.MILLISECOND));
			int i = r.nextInt(colorInfo.length);
			return hexColor(new StrV(colorInfo[i]), defaultPenColor);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Hide(ArrayList<Value> args) {
		// 그래픽 창을 숨김
		if (args.size() == 0) {
			if (frame == null)
				Show(new ArrayList<Value>());
			frame.setVisible(false);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetPixel(ArrayList<Value> args) {
		// 지정한 색을 사용하여 지정한 x와 y 좌표상에 점을 찍음
		// x, y, color
		if (args.size() == 3) {
			if (frame == null)
				Show(new ArrayList<Value>());
			panel.SetPixel(args);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Show(ArrayList<Value> args) {
		// 그래픽 창을 표시함
		if (args.size() == 0) {
			if (frame != null)
				frame.setVisible(true);
			else {
				frame = new Frame();
				frame.setAlwaysOnTop(false);
			}
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void ShowMessage(ArrayList<Value> args) {
		// 메시지 상자를 표시함
		// text, title
		if (frame == null)
			Show(new ArrayList<Value>());

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

		JOptionPane.showMessageDialog(frame, text, title, JOptionPane.PLAIN_MESSAGE);

	}

	private static class Frame extends JFrame {
		Frame() {
			setTitle(Title.toString());
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setAlwaysOnTop(true);

			if (((StrV) CanResize).getValue().equalsIgnoreCase("true"))
				setResizable(true);
			else
				setResizable(false);

			int left = getLeft();
			int top = getTop();

			setLocation(left, top);

			int width;
			int height;

			if (Width instanceof DoubleV)
				width = (int) ((DoubleV) Width).getValue();
			else if (Width instanceof StrV && ((StrV) Width).isNumber())
				width = (int) ((StrV) Width).parseDouble();
			else
				width = 0;

			if (Height instanceof DoubleV)
				height = (int) ((DoubleV) Height).getValue();
			else if (Height instanceof StrV && ((StrV) Height).isNumber())
				height = (int) ((StrV) Height).parseDouble();
			else
				height = 0;

			panel = new Panel(width, height);
			getContentPane().add(panel);

			pack();
			setVisible(true);
		}
	}

	private static class Panel extends JPanel implements MouseListener, KeyListener, MouseMotionListener {
		public Panel(int width, int height) {
			this.setOpaque(true);
			this.setLayout(null);

			cmdList = new ArrayList<>();

			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
			setFocusTraversalKeysEnabled(false);
			setPreferredSize(new Dimension(width, height));
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			String backColor = ((StrV) BackgroundColor).getValue();
			setBackground(new Color(Integer.parseInt(backColor.substring(1), 16)));
			String color;
			double zoomX = 1;
			double zoomY = 1;
			double rotate = 0;

			ArrayList<Cmd> _cmdList = (ArrayList<Cmd>) cmdList.clone();

			for (int layer = 2; layer <= 3; layer++) {
				for (Cmd cmd : _cmdList) { // clone 시 오버헤드 발생할 수 있음
					if(cmd.getLayer() != layer)
						continue;
					if (cmd.show) {
						if (cmd.scaleX != 1)
							zoomX = cmd.scaleX;
						if (cmd.scaleY != 1)
							zoomY = cmd.scaleY;
						if (cmd.degree != 0)
							rotate = cmd.degree;

						switch (cmd.cmd) {
						case NOCOMMAND:
							break;
						case DRAWBOUNDTEXT:
							DrawBoundTextCmd dbtc = (DrawBoundTextCmd) cmd;
							FontMetrics dbtcMetrics = g2.getFontMetrics(dbtc.font);
							color = ((StrV) hexColor((StrV) dbtc.brushcolor, new StrV("#000000"))).toString();
							
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) dbtc.opacity));
							g2.rotate(java.lang.Math.toRadians(dbtc.degree));
							g2.scale(dbtc.scaleX, dbtc.scaleY);
							g2.setFont(dbtc.font);
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.drawString(dbtc.text, (int) dbtc.x, (int) dbtc.y + dbtcMetrics.getHeight());
							g2.rotate(java.lang.Math.toRadians(-dbtc.degree));
							break;
						case DRAWELLIPSE:
							DrawEllipseCmd dec = (DrawEllipseCmd) cmd;
							color = ((StrV) hexColor((StrV) dec.pencolor, new StrV("#000000"))).toString();
							
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) dec.opacity));
							g2.rotate(java.lang.Math.toRadians(dec.degree), (dec.x + dec.w / 2) * zoomX,
									(dec.y + dec.h / 2) * zoomY);
							g2.scale(dec.scaleX, dec.scaleY);
							g2.setStroke(new BasicStroke((float) ((DoubleV) dec.penwidth).getValue()));
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.drawOval((int) dec.x, (int) dec.y, dec.w, dec.h);
							g2.rotate(java.lang.Math.toRadians(-dec.degree), dec.x + dec.w / 2, dec.y + dec.h / 2);
							break;
						case DRAWIMAGE:
							DrawImageCmd dic = (DrawImageCmd) cmd;
							Image img = getImage(dic.imageName);
							if (img != null) {
								g2.setComposite(
										AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) dic.opacity));
								g2.rotate(java.lang.Math.toRadians(dic.degree),
										(dic.x + img.getWidth(this) / 2) * zoomX,
										(dic.y + img.getHeight(this) / 2) * zoomY);
								g2.scale(dic.scaleX, dic.scaleY);
								g2.drawImage(img, (int) dic.x, (int) dic.y, this);
								g2.rotate(java.lang.Math.toRadians(-dic.degree), dic.x + img.getWidth(this) / 2,
										dic.y + img.getHeight(this) / 2);
							}
							break;
						case DRAWLINE:
							DrawLineCmd dlc = (DrawLineCmd) cmd;
							color = ((StrV) hexColor((StrV) dlc.pencolor, new StrV("#000000"))).toString();
							
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) dlc.opacity));
							g2.rotate(java.lang.Math.toRadians(dlc.degree), ((dlc.x1 + dlc.x2) / 2) * zoomX,
									((dlc.y1 + dlc.y2) / 2) * zoomY);
							g2.scale(dlc.scaleX, dlc.scaleY);
							g2.setStroke(new BasicStroke((float) ((DoubleV) dlc.penwidth).getValue()));
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.drawLine(dlc.x1, dlc.y1, dlc.x2, dlc.y2);
							g2.rotate(java.lang.Math.toRadians(-dlc.degree), (dlc.x1 + dlc.x2) / 2,
									(dlc.y1 + dlc.y2) / 2);
							break;
						case DRAWRECTANGLE:
							DrawRectangleCmd drc = (DrawRectangleCmd) cmd;
							color = ((StrV) hexColor((StrV) drc.pencolor, new StrV("#000000"))).toString();
							
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) drc.opacity));
							g2.rotate(java.lang.Math.toRadians(drc.degree), (drc.x + drc.w / 2) * zoomX,
									(drc.y + drc.h / 2) * zoomY);
							g2.scale(drc.scaleX, drc.scaleY);
							g2.setStroke(new BasicStroke((float) ((DoubleV) drc.penwidth).getValue()));
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.drawRect((int) drc.x, (int) drc.y, drc.w, drc.h);
							g2.rotate(java.lang.Math.toRadians(-drc.degree), drc.x + drc.w / 2, drc.y + drc.h / 2);
							break;
						case DRAWRESIZEDIMAGE:
							DrawResizedImageCmd dric = (DrawResizedImageCmd) cmd;
							Image imgResized = getImage(dric.imageName);
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) dric.opacity));
							g2.rotate(java.lang.Math.toRadians(dric.degree),
									(dric.x + imgResized.getWidth(this) / 2) * zoomX,
									(dric.y + imgResized.getHeight(this) / 2) * zoomY);
							g2.scale(dric.scaleX, dric.scaleY);
							g2.drawImage(imgResized, (int) dric.x, (int) dric.y, dric.w, dric.h, this);
							g2.rotate(java.lang.Math.toRadians(-dric.degree), dric.x + imgResized.getWidth(this) / 2,
									dric.y + imgResized.getHeight(this) / 2);
							break;
						case DRAWTEXT:
							DrawTextCmd dtc = (DrawTextCmd) cmd;
							FontMetrics dtcMetrics = g2.getFontMetrics(dtc.font);
							color = ((StrV) dtc.brushcolor).getValue();
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) dtc.opacity));
							g2.rotate(java.lang.Math.toRadians(dtc.degree),
									dtc.x + dtcMetrics.stringWidth(dtc.text) / 2,
									dtc.y + (dtcMetrics.getHeight() * 1.5) / 2);
							g2.scale(dtc.scaleX, dtc.scaleY);
							g2.setFont(dtc.font);
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.drawString(dtc.text, (int) dtc.x, (int) dtc.y + dtcMetrics.getHeight());
							g2.rotate(java.lang.Math.toRadians(-dtc.degree),
									dtc.x + dtcMetrics.stringWidth(dtc.text) / 2,
									dtc.y + (dtcMetrics.getHeight() * 1.5) / 2);
							break;
						case DRAWTRIANGLE:
							DrawTriangleCmd dtrc = (DrawTriangleCmd) cmd;
							color = ((StrV) hexColor((StrV) dtrc.pencolor, new StrV("#000000"))).toString();
							
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) dtrc.opacity));
							Point dt = getCircumcenter(dtrc.xs[0], dtrc.ys[0], dtrc.xs[1], dtrc.ys[1], dtrc.xs[2],
									dtrc.ys[2]);
							g2.rotate(java.lang.Math.toRadians(dtrc.degree), dt.getX() * zoomX, dt.getY() * zoomY);
							g2.scale(dtrc.scaleX, dtrc.scaleY);
							g2.setStroke(new BasicStroke((float) ((DoubleV) dtrc.penwidth).getValue()));
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.drawPolygon(dtrc.xs, dtrc.ys, 3);
							g2.rotate(java.lang.Math.toRadians(-dtrc.degree), dt.getX(), dt.getY());
							break;

						case FILLELLIPSE:
							FillEllipseCmd fec = (FillEllipseCmd) cmd;
							color = ((StrV) hexColor((StrV) fec.brushcolor, new StrV("#000000"))).toString();
							
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) fec.opacity));
							g2.rotate(java.lang.Math.toRadians(fec.degree), (fec.x + fec.w / 2) * zoomX,
									(fec.y + fec.h / 2) * zoomY);
							g2.scale(fec.scaleX, fec.scaleY);
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.fillOval((int) fec.x, (int) fec.y, fec.w, fec.h);
							g2.rotate(java.lang.Math.toRadians(-fec.degree), fec.x + fec.w / 2, fec.y + fec.h / 2);
							break;
						case FILLRECTANGLE:
							FillRectangleCmd frc = (FillRectangleCmd) cmd;
							color = ((StrV) hexColor((StrV) frc.brushcolor, new StrV("#000000"))).toString();
							
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) frc.opacity));
							g2.rotate(java.lang.Math.toRadians(frc.degree), (frc.x + frc.w / 2) * zoomX,
									(frc.y + frc.h / 2) * zoomY);
							g2.scale(frc.scaleX, frc.scaleY);
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.fillRect((int) frc.x, (int) frc.y, frc.w, frc.h);
							g2.rotate(java.lang.Math.toRadians(-frc.degree), frc.x + frc.w / 2, frc.y + frc.h / 2);
							break;
						case FILLTRIANGLE:
							FillTriangleCmd ftc = (FillTriangleCmd) cmd;
							color = ((StrV) hexColor((StrV) ftc.brushcolor, new StrV("#000000"))).toString();
							
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) ftc.opacity));
							Point ft = getCircumcenter(ftc.xs[0], ftc.ys[0], ftc.xs[1], ftc.ys[1], ftc.xs[2],
									ftc.ys[2]);
							g2.rotate(java.lang.Math.toRadians(ftc.degree), ft.getX() * zoomX, ft.getY() * zoomY);
							g2.scale(ftc.scaleX, ftc.scaleY);
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.fillPolygon(ftc.xs, ftc.ys, 3);
							g2.rotate(java.lang.Math.toRadians(-ftc.degree), ft.getX(), ft.getY());
							break;
						case SETPIXEL:
							SetPixelCmd spc = (SetPixelCmd) cmd;
							color = ((StrV) hexColor((StrV) spc.color, new StrV("#000000"))).toString();
							g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) spc.opacity));
							g2.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
							g2.setStroke(new BasicStroke(1));
							g2.drawLine((int) spc.x, (int) spc.y, (int) spc.x, (int) spc.y);
							break;
						}
						if (zoomX != 1) {
							g2.scale((double) 1 / zoomX, 1);
							zoomX = 1;
						}
						if (zoomY != 1) {
							g2.scale(1, (double) 1 / zoomY);
							zoomY = 1;
						}
						if (rotate != 0) {
							rotate = 0;
						}
					}
				}
			}
		}

		public Value GetPixel(ArrayList<Value> args) {
			if (args.size() == 2) {
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
					BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics2D g = img.createGraphics();
					this.paint(g);

					int[] arr = img.getRaster().getPixel(values[0], values[1], new int[4]);
					String color = Integer.toHexString(arr[0]) + Integer.toHexString(arr[1])
							+ Integer.toHexString(arr[2]);
					StrV rgbColor = new StrV("#" + color);

					return rgbColor;
				}
			} else
				throw new InterpretException("Unexpected # of args " + args.size());
			return null;
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

		final int SETPIXEL = 12;

		ArrayList<Cmd> cmdList;

		public void DrawBoundText(ArrayList<Value> args) {
			DrawBoundTextCmd cmd = new DrawBoundTextCmd();

			if (args.size() == 4) {
				cmd.cmd = DRAWBOUNDTEXT;

				int[] values = new int[3];
				boolean[] isInteger = new boolean[3];

				for (int i = 0; i < 3; i++) {
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

				if (isInteger[0] && isInteger[1] && isInteger[2]) {
					cmd.x = values[0];
					cmd.y = values[1];
					cmd.w = values[2];

					cmd.font = settingFont();
					cmd.brushcolor = BrushColor;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;
				}

				if (args.get(3) instanceof StrV)
					cmd.text = ((StrV) args.get(3)).getValue();
				else if (args.get(3) instanceof DoubleV)
					cmd.text = ((DoubleV) args.get(3)).toString();

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

					cmd.pencolor = PenColor;
					cmd.penwidth = PenWidth;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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
					String imageName = ((StrV) args.get(0)).getValue();

					imageName = isUrlOrPath(imageName);

					cmd.imageName = imageName;
					cmd.x = values[0];
					cmd.y = values[1];

					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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

					cmd.pencolor = PenColor;
					cmd.penwidth = PenWidth;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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

					cmd.pencolor = PenColor;
					cmd.penwidth = PenWidth;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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
					String imageName = ((StrV) args.get(0)).getValue();

					imageName = isUrlOrPath(imageName);

					cmd.imageName = imageName;
					cmd.x = values[0];
					cmd.y = values[1];
					cmd.w = values[2];
					cmd.h = values[3];

					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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
				int[] values = new int[3];
				boolean[] isInteger = new boolean[3];

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

				if (isInteger[0] && isInteger[1]) {
					cmd.x = values[0];
					cmd.y = values[1];
					cmd.text = args.get(2).toString();

					cmd.font = settingFont();
					cmd.brushcolor = BrushColor;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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
					cmd.xs[0] = values[0];
					cmd.ys[0] = values[1];
					cmd.xs[1] = values[2];
					cmd.ys[1] = values[3];
					cmd.xs[2] = values[4];
					cmd.ys[2] = values[5];

					cmd.pencolor = PenColor;
					cmd.penwidth = PenWidth;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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

					cmd.brushcolor = BrushColor;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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

					cmd.brushcolor = BrushColor;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

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
					cmd.xs[0] = values[0];
					cmd.ys[0] = values[1];
					cmd.xs[1] = values[2];
					cmd.ys[1] = values[3];
					cmd.xs[2] = values[4];
					cmd.ys[2] = values[5];

					cmd.brushcolor = BrushColor;
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public void SetPixel(ArrayList<Value> args) {
			SetPixelCmd cmd = new SetPixelCmd();

			if (args.size() == 3) {

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
					cmd.cmd = SETPIXEL;

					cmd.x = values[0];
					cmd.y = values[1];
					cmd.color = (StrV) args.get(2);
					cmd.show = true;
					cmd.opacity = 1;
					cmd.degree = 0;
					cmd.scaleX = 1;
					cmd.scaleY = 1;

					cmdList.add(cmd);

					repaint();
				}
			} else {
				throw new InterpretException("Unexpected # of args " + args.size());
			}
		}

		public ArrayList<Cmd> getCmdList() {
			return cmdList;
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

				MouseButtonDown(e.getButton());

				Eval.eval(MouseDown);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (MouseUp != null) {
				MouseX = new DoubleV(e.getX());
				MouseY = new DoubleV(e.getY());

				MouseButtonDown(e.getButton());

				Eval.eval(MouseUp);
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Mouse.MouseX = new DoubleV(MouseInfo.getPointerInfo().getLocation().getX());
			Mouse.MouseY = new DoubleV(MouseInfo.getPointerInfo().getLocation().getY());

			if (MouseMove != null) {
				MouseX = new DoubleV(e.getX());
				MouseY = new DoubleV(e.getY());

				MouseButtonDown(e.getButton());

				Eval.eval(MouseMove);
			}
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
				String lastKey = keyMap.get(e.getKeyCode());

				if (lastKey != null) {
					lastKey = SetLastKey(e.getKeyLocation(), lastKey);
				}
				LastKey = new StrV(lastKey);

				char text = e.getKeyChar();

				if (isPrintableChar(text)) {
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
						LastText = new StrV("");
					else
						LastText = new StrV(String.valueOf(text));
				}

				Eval.eval(KeyDown);
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (KeyUp != null) {
				String lastKey = keyMap.get(e.getKeyCode());

				if (lastKey != null) {
					lastKey = SetLastKey(e.getKeyLocation(), lastKey);
				}
				LastKey = new StrV(lastKey);

				char text = e.getKeyChar();

				if (isPrintableChar(text)) {
					if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
						LastText = new StrV("");
					else
						LastText = new StrV(String.valueOf(text));
				}

				Eval.eval(KeyUp);
			}
		}

	}

	private static String isUrlOrPath(String imageName) {
		if (imageName.contains("http")) {
			ArrayList<Value> tmp = new ArrayList<>();
			tmp.add(new StrV(imageName));

			imageName = ((StrV) ImageList.LoadImage(tmp)).getValue();
		}

		return imageName;
	}

	private static Image getImage(String image) {
		Image img = null;
		if (image.contains(".")) {
			ImageIcon icon = new ImageIcon(image);
			img = icon.getImage();
		} else {
			img = ImageList.getImage(image);
		}

		return img;
	}

	private static int getLeft() {
		int left;

		if (Left instanceof DoubleV)
			left = (int) ((DoubleV) Left).getValue();
		else if (Left instanceof StrV && ((StrV) Left).isNumber())
			left = (int) ((StrV) Left).parseDouble();
		else
			throw new InterpretException("Unexpected type " + Left);

		return left;
	}

	private static int getTop() {
		int top;

		if (Top instanceof DoubleV)
			top = (int) ((DoubleV) Top).getValue();
		else if (Top instanceof StrV && ((StrV) Top).isNumber())
			top = (int) ((DoubleV) Top).getValue();
		else
			throw new InterpretException("Unexpected type " + Top);

		return top;
	}

	private static String SetLastKey(int location, String lastKey) {
		if (lastKey.equals("Win")) {
			if (location == 2)
				return "L" + lastKey;
			else if (location == 3)
				return "R" + lastKey;
		} else if (lastKey.equals("Shift")) {
			if (location == 2)
				return "Left" + lastKey;
			else if (location == 3)
				return "Right" + lastKey;
		} else if (lastKey.equals("Ctrl")) {
			if (location == 2)
				return "Left" + lastKey;
			else if (location == 1)
				return "Right" + lastKey;
		}

		return lastKey;
	}

	private static Point getCircumcenter(double x1, double y1, double x2, double y2, double x3, double y3) {
		double x, y;

		double midX1 = (x2 + x3) / 2.0;
		double midY1 = (y2 + y3) / 2.0;
		double midX3 = (x2 + x1) / 2.0;
		double midY3 = (y2 + y1) / 2.0;

		double slope12 = -1 / ((x2 - x1) / (y2 - y1));
		double slope23 = -1 / ((x3 - x2) / (y3 - y1));

		double b12 = midY1 - slope12 * midX1;
		double b23 = midY3 - slope23 * midX3;

		x = ((b12 - b23) / (slope23 - slope12));
		y = ((slope12 * x) + b12);

		Point returnP = new Point();
		returnP.x = (int) x;
		returnP.y = (int) y;

		return returnP;
	}

	private static boolean isPrintableChar(char c) {
		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);

		return c != KeyEvent.VK_DELETE && c != KeyEvent.CHAR_UNDEFINED && block != null
				&& block != Character.UnicodeBlock.SPECIALS;
	}

	// font: used by GraphicsWindow & Controls
	private static boolean fontBold() {
		StrV bold = (StrV) FontBold;
		String boolBold = bold.getValue();

		if (boolBold.equalsIgnoreCase("True"))
			return true;
		else
			return false;
	}

	private static boolean fontItalic() {
		StrV italic = (StrV) FontItalic;
		String boolItalic = italic.getValue();

		if (boolItalic.equalsIgnoreCase("True"))
			return true;
		else
			return false;
	}

	private static Font settingFont() {
		Font font;

		boolean bold = fontBold();
		boolean italic = fontItalic();
		String fontName = ((StrV) FontName).getValue();
		int fontSize;

		if (FontSize instanceof DoubleV)
			fontSize = (int) ((DoubleV) FontSize).getValue();
		else if (FontSize instanceof StrV && ((StrV) FontSize).isNumber())
			fontSize = (int) ((StrV) FontSize).parseDouble();
		else
			throw new InterpretException("Unexpected type of " + FontSize);

		if (bold && italic)
			font = new Font(fontName, Font.BOLD | Font.ITALIC, fontSize);
		else if (bold)
			font = new Font(fontName, Font.BOLD, fontSize);
		else if (italic)
			font = new Font(fontName, Font.ITALIC, fontSize);
		else
			font = new Font(fontName, Font.PLAIN, fontSize);

		return font;
	}
	// End of font

	private static abstract class Cmd {
		boolean show;
		int cmd;
		double x, y;
		float opacity;
		double scaleX, scaleY;
		double degree;
		int layer;

		Cmd() {
			layer = 2; // 2nd layer for draw... and fill... method
		}
		
		void setLayerforShapesOrControls() {
			layer = 3;
		}
		
		int getLayer() {
			return layer;
		}
		
		void Move(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	private static class DrawBoundTextCmd extends Cmd {
		int w;
		String text;

		Font font;
		Value brushcolor;
	}

	private static class DrawEllipseCmd extends Cmd {
		int w, h;

		Value pencolor;
		Value penwidth;
	}

	private static class DrawImageCmd extends Cmd {
		String imageName;
	}

	private static class DrawLineCmd extends Cmd {
		int x1, y1, x2, y2;

		Value pencolor;
		Value penwidth;

		@Override
		void Move(double x, double y) {
			this.x = x;
			this.y = y;

			int dx = x1 - (int) x;
			int dy = y1 - (int) y;

			x1 = (int) x;
			y1 = (int) y;
			x2 = x2 - dx;
			y2 = y2 - dy;
		}
	}

	private static class DrawRectangleCmd extends Cmd {
		int w, h;

		Value pencolor;
		Value penwidth;
	}

	private static class DrawResizedImageCmd extends Cmd {
		String imageName;
		int w, h;
	}

	private static class DrawTextCmd extends Cmd {
		String text;

		Font font;
		Value brushcolor;
	}

	private static class DrawTriangleCmd extends Cmd {
		int xs[] = new int[3];
		int ys[] = new int[3];

		Value pencolor;
		Value penwidth;

		@Override
		void Move(double x, double y) {
			this.x = x;
			this.y = y;

			int dx = xs[0] - (int) x;
			int dy = ys[0] - (int) y;

			xs[0] = (int) x;
			ys[0] = (int) y;
			xs[1] = xs[1] - dx;
			ys[1] = ys[1] - dy;
			xs[2] = xs[2] - dx;
			ys[2] = ys[2] - dy;
		}
	}

	private static class FillEllipseCmd extends Cmd {
		int w, h;

		Value brushcolor;
	}

	private static class FillRectangleCmd extends Cmd {
		int w, h;

		Value brushcolor;
	}

	private static class FillTriangleCmd extends Cmd {
		int xs[] = new int[3];
		int ys[] = new int[3];

		Value brushcolor;

		@Override
		void Move(double x, double y) {
			this.x = x;
			this.y = y;

			int dx = xs[0] - (int) x;
			int dy = ys[0] - (int) y;

			xs[0] = (int) x;
			ys[0] = (int) y;
			xs[1] = xs[1] - dx;
			ys[1] = ys[1] - dy;
			xs[2] = xs[2] - dx;
			ys[2] = ys[2] - dy;
		}
	}

	private static class SetPixelCmd extends Cmd {
		Value color;

		@Override
		void Move(double x, double y) {
		}
	}

	private static Frame frame = null;
	private static Panel panel = null;

	private static final Value defaultBrushColor = new StrV("#6A5ACD");
	private static final Value defaultPenColor = new StrV("#000000");
	private static final Value defaultBackgroundColor = new StrV("#FFFFFF");
	private static final Value defaultCanResize = new StrV("True");
	private static final Value defaultTitle = new StrV("Small Basic Graphics Window");
	private static final Value defaultHeight = new DoubleV(480);
	private static final Value defaultWidth = new DoubleV(640);
	private static final Value defaultLeft = new DoubleV(51.2);
	private static final Value defaultTop = new DoubleV(51.2);
	private static final Value defaultFontBold = new StrV("True");
	private static final Value defaultFontItalic = new StrV("False");
	private static final Value defaultFontName = new StrV("Tahoma");
	private static final Value defaultFontSize = new DoubleV(12);
	private static final Value defaultPenWidth = new DoubleV(2);

	public static Value BackgroundColor = GraphicsWindow.defaultBackgroundColor; // white
	public static Value BrushColor = GraphicsWindow.defaultBrushColor;
	public static Value CanResize = GraphicsWindow.defaultCanResize;
	public static Value FontBold = GraphicsWindow.defaultFontBold;
	public static Value FontItalic = GraphicsWindow.defaultFontItalic;
	public static Value FontName = GraphicsWindow.defaultFontName;
	public static Value FontSize = GraphicsWindow.defaultFontSize;
	public static Value Height = GraphicsWindow.defaultHeight;
	public static Value LastKey;
	public static Value LastText;
	public static Value Left = GraphicsWindow.defaultLeft;
	public static Value PenColor = GraphicsWindow.defaultPenColor; // black
	public static Value PenWidth = GraphicsWindow.defaultPenWidth;
	public static Value Title = GraphicsWindow.defaultTitle;
	public static Value Top = GraphicsWindow.defaultTop;
	public static Value Width = GraphicsWindow.defaultWidth;
	private static final boolean isMouseIn = (Mouse.MouseY.getNumber() >= Top.getNumber()
			&& Mouse.MouseY.getNumber() <= Top.getNumber() + Height.getNumber())
			&& (Mouse.MouseX.getNumber() >= Left.getNumber() + 7
					&& Mouse.MouseX.getNumber() <= Left.getNumber() + Width.getNumber());

	private static final Value defaultMouseX = new DoubleV(
			isMouseIn ? (int) Mouse.MouseX.getNumber() - Left.getNumber() - 7 : 0);
	private static final Value defaultMouseY = new DoubleV(
			isMouseIn ? (int) Mouse.MouseY.getNumber() - Top.getNumber() - 30 : 0);

	public static Value MouseX = GraphicsWindow.defaultMouseX;
	public static Value MouseY = GraphicsWindow.defaultMouseY;

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

	private static int[] int_keyInfo = { KeyEvent.VK_0, KeyEvent.VK_RIGHT_PARENTHESIS, KeyEvent.VK_1,
			KeyEvent.VK_EXCLAMATION_MARK, KeyEvent.VK_2, KeyEvent.VK_AT, KeyEvent.VK_3, KeyEvent.VK_NUMBER_SIGN,
			KeyEvent.VK_4, KeyEvent.VK_DOLLAR, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_CIRCUMFLEX, KeyEvent.VK_7,
			KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_LEFT_PARENTHESIS, KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3,
			KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8, KeyEvent.VK_F9,
			KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C,
			KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J,
			KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q,
			KeyEvent.VK_R, KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X,
			KeyEvent.VK_Y, KeyEvent.VK_Z, KeyEvent.VK_NUMPAD0, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2,
			KeyEvent.VK_NUMPAD3, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD7,
			KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9, KeyEvent.VK_NUM_LOCK, KeyEvent.VK_ADD, KeyEvent.VK_SUBTRACT,
			KeyEvent.VK_MULTIPLY, KeyEvent.VK_DIVIDE, KeyEvent.VK_DECIMAL, KeyEvent.VK_TAB, KeyEvent.VK_CAPS_LOCK,
			KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL, KeyEvent.VK_WINDOWS, KeyEvent.VK_ALT,
			KeyEvent.VK_INPUT_METHOD_ON_OFF, KeyEvent.VK_SPACE, KeyEvent.VK_CONTEXT_MENU, KeyEvent.VK_BACK_SPACE,
			KeyEvent.VK_BACK_SLASH, KeyEvent.VK_ENTER, KeyEvent.VK_BACK_QUOTE, KeyEvent.VK_UP, KeyEvent.VK_DOWN,
			KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_KP_UP, KeyEvent.VK_KP_DOWN, KeyEvent.VK_KP_LEFT,
			KeyEvent.VK_KP_RIGHT, KeyEvent.VK_INSERT, KeyEvent.VK_HOME, KeyEvent.VK_PAGE_UP, KeyEvent.VK_DELETE,
			KeyEvent.VK_END, KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_COMMA, KeyEvent.VK_PERIOD, KeyEvent.VK_SLASH,
			KeyEvent.VK_SEMICOLON, KeyEvent.VK_COLON, KeyEvent.VK_OPEN_BRACKET, KeyEvent.VK_CLOSE_BRACKET,
			KeyEvent.VK_ESCAPE, KeyEvent.VK_SCROLL_LOCK, KeyEvent.VK_PAUSE, KeyEvent.VK_EQUALS, KeyEvent.VK_MINUS,
			KeyEvent.VK_CLEAR, KeyEvent.VK_QUOTE };

	private static String[] keyInfo = { "D0", "D0", "D1", "D1", "D2", "D2", "D3", "D3", "D4", "D4", "D5", "D6", "D6",
			"D7", "D8", "D9", "D9", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "System", "F11", "F12", "A",
			"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z", "NumPad0", "NumPad1", "NumPad2", "NumPad3", "NumPad4", "NumPad5", "NumPad6", "NumPad7",
			"NumPad8", "NumPad9", "NumLock", "Add", "Subtract", "Multiply", "Divide", "Decimal", "Tab", "Capital",
			"Shift", "Ctrl", "Win", "Alt", "HanjaMode", "Space", "Apps", "Back", "Oem5", "Return", "Oem3", "Up", "Down",
			"Left", "Right", "Up", "Down", "Left", "Right", "Insert", "Home", "PageUp", "Delete", "End", "PageDown",
			"OemComma", "OemPeriod", "OemQuestion", "Oem1", "Oem1", "OemOpenBrackets", "Oem6", "Escape", "Scroll",
			"Pause", "OemPlus", "OemMinus", "Clear", "OemQuotes" };

	private static HashMap<String, String> colorMap;
	private static HashMap<Integer, String> keyMap;

	static {
		colorMap = new HashMap<String, String>();
		keyMap = new HashMap<Integer, String>();
		for (int i = 0; i < colorInfo.length; i += 2) {
			colorMap.put(colorInfo[i].toUpperCase(), colorInfo[i + 1]);
		}
		for (int i = 0; i < keyInfo.length; i++) {
			keyMap.put(int_keyInfo[i], keyInfo[i]);
		}
	}

	public static void notifyFieldAssign(String fieldName) {
		if (frame == null)
			Show(new ArrayList<Value>());

		if ("BrushColor".equalsIgnoreCase(fieldName)) {
			if (BrushColor instanceof StrV) {
				String v = ((StrV) BrushColor).getValue();
				BrushColor = hexColor((StrV) BrushColor, new StrV("#000000"));
			} else
				throw new InterpretException("BrushColor: Unexpected value" + BrushColor.toString());
		} else if ("PenColor".equalsIgnoreCase(fieldName)) {
			if (PenColor instanceof StrV) {
				String v = ((StrV) PenColor).getValue();
				PenColor = hexColor((StrV) PenColor, new StrV("#000000"));
			} else
				throw new InterpretException("PenColor: Unexpected value" + BrushColor.toString());
		} else if ("BackgroundColor".equalsIgnoreCase(fieldName)) {
			if (BackgroundColor instanceof StrV) {
				String v = ((StrV) BackgroundColor).getValue();
				BackgroundColor = hexColor((StrV) BackgroundColor, new StrV("#000000"));
			} else
				throw new InterpretException("BackgroundColor: Unexpected value" + BrushColor.toString());
		} else if ("CanResize".equalsIgnoreCase(fieldName)) {
			if (CanResize instanceof StrV && CanResize.toString().equalsIgnoreCase("True"))
				frame.setResizable(true);
			else
				frame.setResizable(false);
		} else if ("Height".equalsIgnoreCase(fieldName) || "Width".equalsIgnoreCase(fieldName)) {
			int width;
			int height;

			if (Width instanceof DoubleV)
				width = (int) ((DoubleV) Width).getValue();
			else if (Width instanceof StrV && ((StrV) Width).isNumber())
				width = (int) ((StrV) Width).parseDouble();
			else
				width = 0;

			if (Height instanceof DoubleV)
				height = (int) ((DoubleV) Height).getValue();
			else if (Height instanceof StrV && ((StrV) Height).isNumber())
				height = (int) ((StrV) Height).parseDouble();
			else
				height = 0;

			panel.setPreferredSize(new Dimension(width, height));
			frame.pack();
		} else if ("Title".equalsIgnoreCase(fieldName)) {
			frame.setTitle(Title.toString());
		} else if ("Left".equalsIgnoreCase(fieldName) || "Top".equalsIgnoreCase(fieldName)) {
			int left;
			int top;

			if (Left instanceof DoubleV)
				left = (int) ((DoubleV) Left).getValue();
			else if (Left instanceof StrV && ((StrV) Left).isNumber())
				left = (int) ((StrV) Left).parseDouble();
			else
				left = (int) ((DoubleV) defaultLeft).getValue();

			if (Top instanceof DoubleV)
				top = (int) ((DoubleV) Top).getValue();
			else if (Top instanceof StrV && ((StrV) Top).isNumber())
				top = (int) ((StrV) Top).parseDouble();
			else
				top = (int) ((DoubleV) Top).getValue();

			frame.setLocation(left, top);
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
	// End of GraphicsWindow Library

	// Supporting Shapes Library
	private static final String rectIdLabel = "Rectangle";
	private static final String ellipIdLabel = "Ellipse";
	private static final String triIdLabel = "Triangle";
	private static final String lineIdLabel = "Line";
	private static final String textIdLabel = "Text";
	private static final String imageIdLabel = "Image";

	private static int rectId = 1;
	private static int ellipId = 1;
	private static int triId = 1;
	private static int lineId = 1;
	private static int textId = 1;
	private static int imageId = 1;

	private static HashMap<String, ArrayList<Cmd>> shapeMap = new HashMap<>();

	static String AddRectangle(int width, int height) {
		if (frame == null)
			Show(new ArrayList<Value>());

		ArrayList<Value> grArgs = new ArrayList<Value>();
		grArgs.add(new DoubleV(0));
		grArgs.add(new DoubleV(0));
		grArgs.add(new DoubleV(width));
		grArgs.add(new DoubleV(height));

		GraphicsWindow.FillRectangle(grArgs);
		Cmd fillCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		fillCmd.setLayerforShapesOrControls();
		GraphicsWindow.DrawRectangle(grArgs);
		Cmd drawCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		drawCmd.setLayerforShapesOrControls();
		
		ArrayList<Cmd> cmds = new ArrayList<>();
		cmds.add(fillCmd);
		cmds.add(drawCmd);

		String id = rectIdLabel + rectId;
		rectId++;

		shapeMap.put(id, cmds);

		return id;
	}

	static String AddEllipse(int width, int height) {
		if (frame == null)
			Show(new ArrayList<Value>());

		ArrayList<Value> grArgs = new ArrayList<>();
		grArgs.add(new DoubleV(0));
		grArgs.add(new DoubleV(0));
		grArgs.add(new DoubleV(width));
		grArgs.add(new DoubleV(height));

		GraphicsWindow.FillEllipse(grArgs);
		Cmd fillCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		fillCmd.setLayerforShapesOrControls();
		GraphicsWindow.DrawEllipse(grArgs);
		Cmd drawCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		drawCmd.setLayerforShapesOrControls();
		
		ArrayList<Cmd> cmds = new ArrayList<>();
		cmds.add(fillCmd);
		cmds.add(drawCmd);

		String id = ellipIdLabel + ellipId;
		ellipId++;

		shapeMap.put(id, cmds);

		return id;
	}

	static String AddTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		if (frame == null)
			Show(new ArrayList<Value>());

		ArrayList<Value> grArgs = new ArrayList<>();
		grArgs.add(new DoubleV(x1));
		grArgs.add(new DoubleV(y1));
		grArgs.add(new DoubleV(x2));
		grArgs.add(new DoubleV(y2));
		grArgs.add(new DoubleV(x3));
		grArgs.add(new DoubleV(y3));

		GraphicsWindow.FillTriangle(grArgs);
		Cmd fillCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		fillCmd.setLayerforShapesOrControls();
		GraphicsWindow.DrawTriangle(grArgs);
		Cmd drawCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		drawCmd.setLayerforShapesOrControls();

		ArrayList<Cmd> cmds = new ArrayList<>();
		cmds.add(fillCmd);
		cmds.add(drawCmd);

		String id = triIdLabel + triId;
		triId++;

		shapeMap.put(id, cmds);

		return id;
	}

	static String AddLine(int x1, int y1, int x2, int y2) {
		if (frame == null)
			Show(new ArrayList<Value>());

		ArrayList<Value> grArgs = new ArrayList<>();
		grArgs.add(new DoubleV(x1));
		grArgs.add(new DoubleV(y1));
		grArgs.add(new DoubleV(x2));
		grArgs.add(new DoubleV(y2));

		GraphicsWindow.DrawLine(grArgs);
		Cmd drawCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		drawCmd.setLayerforShapesOrControls();

		ArrayList<Cmd> cmds = new ArrayList<>();
		cmds.add(drawCmd);

		String id = lineIdLabel + lineId;
		lineId++;

		shapeMap.put(id, cmds);

		return id;
	}

	static String AddImage(String imageName) {
		if (frame == null)
			Show(new ArrayList<Value>());

		ArrayList<Value> grArgs = new ArrayList<>();
		grArgs.add(new StrV(imageName));
		grArgs.add(new DoubleV(0));
		grArgs.add(new DoubleV(0));

		GraphicsWindow.DrawImage(grArgs);
		Cmd drawCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		drawCmd.setLayerforShapesOrControls();

		ArrayList<Cmd> cmds = new ArrayList<>();
		cmds.add(drawCmd);

		String id = imageIdLabel + imageId;
		imageId++;

		shapeMap.put(id, cmds);

		return id;
	}

	static String AddText(String text) {
		if (frame == null)
			Show(new ArrayList<Value>());

		ArrayList<Value> grArgs = new ArrayList<>();
		grArgs.add(new DoubleV(0));
		grArgs.add(new DoubleV(0));
		grArgs.add(new StrV(text));

		GraphicsWindow.DrawText(grArgs);
		Cmd drawCmd = panel.getCmdList().get(panel.getCmdList().size() - 1);
		drawCmd.setLayerforShapesOrControls();

		ArrayList<Cmd> cmds = new ArrayList<>();
		cmds.add(drawCmd);

		String id = textIdLabel + textId;
		textId++;

		shapeMap.put(id, cmds);

		return id;
	}

	static void SetText(String shapeName, String text) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);

		if (cmds != null && cmds.size() == 1) {
			if (cmds.get(0) instanceof DrawTextCmd)
				((DrawTextCmd) cmds.get(0)).text = text;
		}
		panel.repaint();
	}

	static void ShapesRemove(String shape) {
		ArrayList<Cmd> cmds = shapeMap.remove(shape);
		JComponent comp = controlMap.get(shape);

		if (cmds != null) {
			for (Cmd cmd : cmds) {
				panel.getCmdList().remove(cmd);
			}
			panel.repaint();
		} else if (comp != null)
			ControlsRemove(shape);
	}

	static void ShapesMove(String shape, double x, double y) {
		ArrayList<Cmd> cmds = shapeMap.get(shape);
		JComponent comp = controlMap.get(shape);

		if (cmds != null) {
			for (Cmd cmd : cmds) {
				cmd.Move(x, y);
			}
			panel.repaint();
		} else if (comp != null)
			ControlsMove(shape, (int) x, (int) y);
	}

	static void Rotate(String shapeName, double angle) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);

		if (cmds != null) {
			for (Cmd cmd : cmds) {
				cmd.degree = angle;
			}
			panel.repaint();
		}
	}

	static void Zoom(String shapeName, double scaleX, double scaleY) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);

		if (cmds != null) {
			if (scaleX < 0.1)
				scaleX = 0.1;
			else if (scaleX > 20)
				scaleX = 20;

			if (scaleY < 0.1)
				scaleY = 0.1;
			else if (scaleY > 20)
				scaleY = 20;

			for (Cmd cmd : cmds) {
				cmd.scaleX = scaleX;
				cmd.scaleY = scaleY;
			}
			panel.repaint();
		}
	}

	static void Animate(String shapeName, double x, double y, int duration) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);

		if (cmds != null) {
			if (duration < 0 || duration > 100000000)
				throw new InterpretException("Not a valid number : " + duration);

			if (duration == 0) {
				for (Cmd cmd : cmds) {
					if (cmd instanceof DrawLineCmd) {
						DrawLineCmd lineCmd = (DrawLineCmd) cmd;
						lineCmd.Move(lineCmd.x1 + x, lineCmd.y1 + y);
						lineCmd.x = x;
						lineCmd.y = y;
					} else if (cmd instanceof DrawTriangleCmd) {
						DrawTriangleCmd triCmd = (DrawTriangleCmd) cmd;
						triCmd.Move(triCmd.xs[0] + x, triCmd.ys[0] + y);
						triCmd.x = x;
						triCmd.y = y;
					} else if (cmd instanceof FillTriangleCmd) {
						FillTriangleCmd triCmd = (FillTriangleCmd) cmd;
						triCmd.Move(triCmd.xs[0] + x, triCmd.ys[0] + y);
						triCmd.x = x;
						triCmd.y = y;
					} else
						cmd.Move(x, y);
					panel.repaint();
				}
			} else {
				MyActionListener animate_action = new MyActionListener(cmds, x, y, duration);
				javax.swing.Timer animate = new javax.swing.Timer(100, animate_action);
				animate_action.timer = animate;
				animate.start();
			}
		}
	}

	static class MyActionListener implements ActionListener {
		int i = 1;
		ArrayList<Cmd> cmds;
		int times;
		double x, y;
		public javax.swing.Timer timer;

		public MyActionListener(ArrayList<Cmd> cmds, double x, double y, int duration) {
			this.cmds = cmds;
			this.x = x;
			this.y = y;
			this.times = duration / 100;
		}

		ArrayList<Pair<Double, Double>> a_pair = new ArrayList<>();

		@Override
		public void actionPerformed(ActionEvent e) {
			int n = 0;
			for (Cmd cmd : cmds) {
				if (cmd instanceof DrawLineCmd) {
					DrawLineCmd lineCmd = (DrawLineCmd) cmd;
					double a_x;
					double a_y;

					if (i == 1) {
						a_x = (x - lineCmd.x) / times;
						a_y = (y - lineCmd.y) / times;
						Pair<Double, Double> pair = new Pair<>(a_x, a_y);
						a_pair.add(pair);
					} else {
						a_x = a_pair.get(n).getFst();
						a_y = a_pair.get(n).getSec();
					}

					lineCmd.Move(lineCmd.x1 + a_x, lineCmd.y1 + a_y);
					lineCmd.x = x;
					lineCmd.y = y;
				} else if (cmd instanceof DrawTriangleCmd) {
					DrawTriangleCmd triCmd = (DrawTriangleCmd) cmd;
					double a_x;
					double a_y;

					if (i == 1) {
						a_x = (x - triCmd.x) / times;
						a_y = (y - triCmd.y) / times;
						Pair<Double, Double> pair = new Pair<>(a_x, a_y);
						a_pair.add(pair);
					} else {
						a_x = a_pair.get(n).getFst();
						a_y = a_pair.get(n).getSec();
					}

					triCmd.Move(triCmd.xs[0] + a_x, triCmd.ys[0] + a_y);
					triCmd.x = x;
					triCmd.y = y;
				} else if (cmd instanceof FillTriangleCmd) {
					FillTriangleCmd triCmd = (FillTriangleCmd) cmd;
					double a_x;
					double a_y;

					if (i == 1) {
						a_x = (x - triCmd.x) / times;
						a_y = (y - triCmd.y) / times;
						Pair<Double, Double> pair = new Pair<>(a_x, a_y);
						a_pair.add(pair);
					} else {
						a_x = a_pair.get(n).getFst();
						a_y = a_pair.get(n).getSec();
					}

					triCmd.Move(triCmd.xs[0] + a_x, triCmd.ys[0] + a_y);
					triCmd.x = x;
					triCmd.y = y;
				} else {
					double a_x;
					double a_y;

					if (i == 1) {
						a_x = (x - cmd.x) / times;
						a_y = (y - cmd.y) / times;
						Pair<Double, Double> pair = new Pair<>(a_x, a_y);
						a_pair.add(pair);
					} else {
						a_x = a_pair.get(n).getFst();
						a_y = a_pair.get(n).getSec();
					}

					cmd.Move(cmd.x + a_x, cmd.y + a_y);
				}
				n++;
			}
			panel.repaint();
			i++;

			if (i > times) {
				timer.stop();
			}
		}
	}

	static double GetLeft(String shapeName) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);

		if (cmds != null) {
			return cmds.get(0).x;
		}

		return 0;
	}

	static double GetTop(String shapeName) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);

		if (cmds != null) {
			return cmds.get(0).y;
		}

		return 0;
	}

	static double GetOpacity(String shapeName) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);

		if (cmds != null) {
			return cmds.get(0).opacity * 100;
		}

		return 0;
	}

	static void SetOpacity(String shapeName, double opacity) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);

		if (cmds != null) {
			for (Cmd cmd : cmds) {
				if (opacity >= 0 && opacity <= 100)
					cmd.opacity = (float) (opacity / 100);
				else if (opacity < 0)
					cmd.opacity = 0;
				else if (opacity > 100)
					cmd.opacity = 1;
			}
		}
	}

	static void HideShape(String shapeName) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);
		JComponent comp = controlMap.get(shapeName);

		if (cmds != null) {
			for (Cmd cmd : cmds) {
				cmd.show = false;

			}
			panel.repaint();
		} else if (comp != null)
			HideControl(shapeName);
	}

	static void ShowShape(String shapeName) {
		ArrayList<Cmd> cmds = shapeMap.get(shapeName);
		JComponent comp = controlMap.get(shapeName);

		if (cmds != null) {
			for (Cmd cmd : cmds) {
				cmd.show = true;
			}
			panel.repaint();
		} else if (comp != null)
			ShowControl(shapeName);
	}
	// End of Supporting Shapes Library

	// Supporting Controls Library
	private static final String btnIdLabel = "Button";
	private static final String txtBoxIdLabel = "TextBox";

	private static int btnId = 1;
	private static int txtBoxId = 1;

	private static HashMap<String, JComponent> controlMap = new HashMap<>();

	private static Container container;

	static String AddButton(String caption, int left, int top) {
		if (frame == null)
			Show(new ArrayList<Value>());

		if (caption.equals(""))
			caption = " ";

		JButton btn = new JButton(caption);

		btn.setFont(settingFont());
		btn.setForeground(new Color(Integer.parseInt(BrushColor.toString().substring(1), 16)));
		btn.setBackground(new Color(220, 220, 220)); // <-- 버튼 기본 색 설정
		btn.setSize(btn.getPreferredSize());
		btn.setLocation(left, top);
		btn.addActionListener(new mActionListener());
		panel.add(btn);

		container = btn.getParent();
		container.revalidate();
		container.repaint();

		String id = btnIdLabel + btnId;
		btnId++;

		controlMap.put(id, btn);

		return id;
	}

	static String AddTextBox(int left, int top) {
		if (frame == null)
			Show(new ArrayList<Value>());

		JTextField tf = new JTextField(13);

		tf.setFont(settingFont());
		tf.setForeground(new Color(Integer.parseInt(BrushColor.toString().substring(1), 16)));
		tf.setSize(tf.getPreferredSize());
		tf.setLocation(left, top);
		tf.getDocument().addDocumentListener(new mDocumentListener());

		panel.add(tf);

		container = tf.getParent();
		container.revalidate();
		container.repaint();

		String id = txtBoxIdLabel + txtBoxId;
		txtBoxId++;

		controlMap.put(id, tf);

		return id;
	}

	static String AddMultiLineTextBox(int left, int top) {
		if (frame == null)
			Show(new ArrayList<Value>());

		JTextArea ta = new JTextArea(5, 18);

		JScrollPane scroll = new JScrollPane(ta);

		scroll.setFont(settingFont());
		scroll.setForeground(new Color(Integer.parseInt(BrushColor.toString().substring(1), 16)));
		scroll.setSize(scroll.getPreferredSize());
		scroll.setLocation(left, top);
		ta.getDocument().addDocumentListener(new mDocumentListener());

		panel.add(scroll);

		container = ta.getParent();
		container.revalidate();
		container.repaint();

		String id = txtBoxIdLabel + txtBoxId;
		txtBoxId++;

		controlMap.put(id, ta);

		return id;
	}

	static String GetButtonCaption(String buttonName) {
		JComponent comp = controlMap.get(buttonName);
		String caption = "";

		if (comp != null && comp instanceof JButton) {
			caption = ((JButton) comp).getText();
		}

		return caption;
	}

	static void SetButtonCaption(String buttonName, String caption) {
		JComponent comp = controlMap.get(buttonName);

		if (comp != null && comp instanceof JButton) {
			JButton btn = (JButton) comp;
			btn.setText(caption);
			btn.setSize(btn.getPreferredSize());
		}
	}

	static String GetTextBoxText(String textBoxName) {
		JComponent comp = controlMap.get(textBoxName);
		String text = "";

		if (comp != null) {
			if (comp instanceof JTextField) {
				text = ((JTextField) comp).getText();
			} else if (comp instanceof JTextArea) {
				text = ((JTextArea) comp).getText();
			}
		}

		return text;
	}

	static void SetTextBoxText(String textBoxName, String text) {
		JComponent comp = controlMap.get(textBoxName);

		if (comp != null) {
			if (comp instanceof JTextField) {
				JTextField tf = (JTextField) comp;
				tf.setText(text);
			} else if (comp instanceof JTextArea) {
				JTextArea ta = (JTextArea) comp;
				ta.setText(text);
			}
		}
	}

	static void ControlsRemove(String control) {
		JComponent comp = controlMap.get(control);
		ArrayList<Cmd> cmd = shapeMap.get(control);

		if (comp != null) {
			controlMap.remove(control);

			container = comp.getParent();
			container.remove(comp);
			container.revalidate();
			container.repaint();
		} else if (cmd != null)
			ShapesRemove(control);
	}

	static void ControlsMove(String control, int x, int y) {
		JComponent comp = controlMap.get(control);
		ArrayList<Cmd> cmd = shapeMap.get(control);

		if (comp != null) {
			comp.setLocation(x, y);
		} else if (cmd != null)
			ShapesMove(control, x, y);
	}

	static void SetSize(String control, int width, int height) {
		JComponent comp = controlMap.get(control);

		if (comp != null) {
			comp.setSize(width, height);

			container = comp.getParent();
			container.repaint();
		}
	}

	static void HideControl(String control) {
		JComponent comp = controlMap.get(control);
		ArrayList<Cmd> cmd = shapeMap.get(control);

		if (comp != null) {
			comp.setVisible(false);
		} else if (cmd != null)
			HideShape(control);
	}

	static void ShowControl(String control) {
		JComponent comp = controlMap.get(control);
		ArrayList<Cmd> cmd = shapeMap.get(control);

		if (comp != null) {
			comp.setVisible(true);
		} else if (cmd != null)
			ShowShape(control);
	}

	private static class mActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (Controls.ButtonClicked != null) {
				JButton button = (JButton) e.getSource();

				if (controlMap.containsValue(button)) {
					Controls.LastClickedButton = new StrV(getKeyFromValue(button));
				}
				Eval.eval(Controls.ButtonClicked);
			}
		}
	}

	private static class mDocumentListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {

		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			if (Controls.TextTyped != null) {
				JComponent comp = getCompFromDoc(e.getDocument());
				if (comp != null) {
					Controls.LastTypedTextBox = new StrV(getKeyFromValue(comp));
				}
				Eval.eval(Controls.TextTyped);
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if (Controls.TextTyped != null) {
				JComponent comp = getCompFromDoc(e.getDocument());
				if (comp != null) {
					Controls.LastTypedTextBox = new StrV(getKeyFromValue(comp));
				}
				Eval.eval(Controls.TextTyped);
			}
		}

	}

	private static String getKeyFromValue(JComponent comp) {
		if (comp instanceof JButton || comp instanceof JTextField || comp instanceof JTextArea) {
			for (String str : controlMap.keySet()) {
				if (controlMap.get(str).equals(comp))
					return str;
			}
		}

		return "";
	}

	private static JComponent getCompFromDoc(Document doc) {
		for (JComponent comp : controlMap.values()) {
			if (comp instanceof JTextField) {
				JTextField tf = (JTextField) comp;

				if (tf.getDocument().equals(doc))
					return tf;
			} else if (comp instanceof JTextArea) {
				JTextArea ta = (JTextArea) comp;

				if (ta.getDocument().equals(doc))
					return ta;
			}
		}
		return null;
	}
	// End of Supporting Controls Library

	// Supporting Mouse Library
	static Toolkit tk = Toolkit.getDefaultToolkit();
	static Cursor cursor;

	static void HideCursor() {
		if (frame == null)
			Show(new ArrayList<Value>());

		cursor = tk.createCustomCursor(tk.createImage(""), new Point(), null);
		panel.setCursor(cursor);
	}

	static void ShowCursor() {
		if (frame == null)
			Show(new ArrayList<Value>());

		cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		panel.setCursor(cursor);
	}

	private static void MouseButtonDown(int button) {
		String right = "False";
		String left = "False";

		if (button == MouseEvent.BUTTON1) {
			left = "True";
		}
		if (button == MouseEvent.BUTTON3) {
			right = "True";
		}

		Mouse.IsLeftButtonDown = new StrV(left);
		Mouse.IsRightButtonDown = new StrV(right);
	}
	// End of Supporting Mouse Library
	
	// Supporting Video Library

	private static final String videoIdLabel = "Video";
	private static int videoId = 1;

	private static EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private static HashMap<String, EmbeddedMediaPlayerComponent> videoMap = new HashMap<>();
	private static Container videoContainer;

	static String addVideo(String path) {
		if (frame == null)
			Show(new ArrayList<Value>());
		
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		EmbeddedMediaPlayer videoPlayer = mediaPlayerComponent.getMediaPlayer();
		
		mediaPlayerComponent.setSize(mediaPlayerComponent.getPreferredSize());
		mediaPlayerComponent.setLocation(0, 0);
		
		panel.add(mediaPlayerComponent);

		videoContainer = mediaPlayerComponent.getParent();
		videoContainer.revalidate();
		videoContainer.repaint();
		
		String id = videoIdLabel + videoId;
		videoId++;

		videoMap.put(id, mediaPlayerComponent);
		
		return id;
	}
	
	static void addVideoControl(String videoName) {
		// path 입력 필드, path 전송 버튼
		// play, pause, stop 버튼
	}
	
	static void setPath(String videoName, String videoPath) {
		EmbeddedMediaPlayerComponent videoComp = videoMap.get(videoName);
		
		if (videoComp != null) {
			videoComp.getMediaPlayer().setPlaySubItems(true);
			videoComp.getMediaPlayer().playMedia(videoPath);
		}
	}
	
	static void setSize(String videoName, int width, int height) {
		EmbeddedMediaPlayerComponent videoComp = videoMap.get(videoName);
		
		if (videoComp != null) {
			videoComp.setSize(width, height);
			
			videoContainer = videoComp.getParent();
			videoContainer.repaint();
		}
	}
	
	static void setLocation(String videoName, int x, int y) {
		EmbeddedMediaPlayerComponent videoComp = videoMap.get(videoName);
		
		if (videoComp != null) {
			videoComp.setLocation(x, y);
		}
	}
	// End of Supporting Video Library
	
}
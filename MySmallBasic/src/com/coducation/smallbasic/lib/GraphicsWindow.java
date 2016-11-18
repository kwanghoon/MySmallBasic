package com.coducation.smallbasic.lib;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Eval;
import com.coducation.smallbasic.InterpretException;
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
		if (frame == null)
			Show(args);
		panel.DrawEllipse(args);
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
			
			String color;
			
			for (Cmd cmd : cmdList) {
				switch (cmd.cmd) {
				case NOCOMMAND:
					break;
				case DRAWLINE:
					DrawLineCmd dlc = (DrawLineCmd) cmd;
					color = ((StrV) PenColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.drawLine(dlc.x1, dlc.y1, dlc.x2, dlc.y2);
					break;
				case DRAWELLIPSE:
					DrawEllipseCmd dec = (DrawEllipseCmd) cmd;
					color = ((StrV) PenColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.drawOval(dec.x, dec.y, dec.w, dec.h);
					break;
				case FILLELLIPSE:
					FillEllipseCmd fec = (FillEllipseCmd) cmd;
					color = ((StrV) BrushColor).getValue();
					g.setColor(new Color(Integer.parseInt(color.substring(1), 16)));
					g.fillOval(fec.x, fec.y, fec.w, fec.h);
					break;
				}
			}
		}

		final int NOCOMMAND = 0;
		final int DRAWLINE = 1;
		final int DRAWRECTANGLE = 2;
		final int DRAWELLIPSE = 3;
		final int FILLELLIPSE = 10;

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
		
		public void DrawEllipse(ArrayList<Value> args) {
			DrawEllipseCmd cmd = new DrawEllipseCmd();

			cmd.cmd = DRAWELLIPSE;

			cmd.x = (int) ((DoubleV) args.get(0)).getValue();
			cmd.y = (int) ((DoubleV) args.get(1)).getValue();
			cmd.w = (int) ((DoubleV) args.get(2)).getValue();
			cmd.h = (int) ((DoubleV) args.get(3)).getValue();

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
	
	private static class DrawEllipseCmd extends Cmd {
		int x, y, w, h;
	}

	private static class FillEllipseCmd extends Cmd {
		int x, y, w, h;
	}

	private static Frame frame = null;
	private static Panel panel = null;

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
	
	private static final Value defaultBrushColor = new StrV("#6A5ACD");
	private static final Value defaultPenColor = new StrV("#000000");
	private static final Value defaultBackgroundColor = new StrV("#FFFFFF");
	
	private static String[] colorInfo = {
			"IndianRed", "#CD5C5C", 
			"LightCoral", "#F08080", 
			"Salmon", "#FA8072", 
			"DarkSalmon", "#E9967A", 
			"LightSalmon", "#FFA07A", 
			"Crimson", "#DC143C", 
			"Red", "#FF0000", 
			"FireBrick", "#B22222", 
			"DarkRed", "#8B0000", 
			"Pink", "#FFC0CB", 
			"LightPink", "#FFB6C1", 
			"HotPink", "#FF69B4", 
			"DeepPink", "#FF1493", 
			"MediumVioletRed", "#C71585", 
			"PaleVioletRed", "#DB7093", 
			"LightSalmon", "#FFA07A", 
			"Coral", "#FF7F50", 
			"Tomato", "#FF6347", 
			"OrangeRed", "#FF4500", 
			"DarkOrange", "#FF8C00", 
			"Orange", "#FFA500", 
			"Gold", "#FFD700", 
			"Yellow", "#FFFF00", 
			"LightYellow", "#FFFFE0", 
			"LemonChiffon", "#FFFACD", 
			"LightGoldenrodYellow", "#FAFAD2", 
			"PapayaWhip", "#FFEFD5", 
			"Moccasin", "#FFE4B5", 
			"PeachPuff", "#FFDAB9", 
			"PaleGoldenrod", "#EEE8AA", 
			"Khaki", "#F0E68C", 
			"DarkKhaki", "#BDB76B", 
			"Lavender", "#E6E6FA", 
			"Thistle", "#D8BFD8", 
			"Plum", "#DDA0DD", 
			"Violet", "#EE82EE", 
			"Orchid", "#DA70D6", 
			"Fuchsia", "#FF00FF", 
			"Magenta", "#FF00FF", 
			"MediumOrchid", "#BA55D3", 
			"MediumPurple", "#9370DB", 
			"BlueViolet", "#8A2BE2", 
			"DarkViolet", "#9400D3", 
			"DarkOrchid", "#9932CC", 
			"DarkMagenta", "#8B008B", 
			"Purple", "#800080", 
			"Indigo", "#4B0082", 
			"SlateBlue", "#6A5ACD", 
			"DarkSlateBlue", "#483D8B", 
			"MediumSlateBlue", "#7B68EE", 
			"GreenYellow", "#ADFF2F", 
			"Chartreuse", "#7FFF00", 
			"LawnGreen", "#7CFC00", 
			"Lime", "#00FF00", 
			"LimeGreen", "#32CD32", 
			"PaleGreen", "#98FB98", 
			"LightGreen", "#90EE90", 
			"MediumSpringGreen", "#00FA9A", 
			"SpringGreen", "#00FF7F", 
			"MediumSeaGreen", "#3CB371", 
			"SeaGreen", "#2E8B57", 
			"ForestGreen", "#228B22", 
			"Green", "#008000", 
			"DarkGreen", "#006400", 
			"YellowGreen", "#9ACD32", 
			"OliveDrab", "#6B8E23", 
			"Olive", "#808000", 
			"DarkOliveGreen", "#556B2F", 
			"MediumAquamarine", "#66CDAA", 
			"DarkSeaGreen", "#8FBC8F", 
			"LightSeaGreen", "#20B2AA", 
			"DarkCyan", "#008B8B", 
			"Teal", "#008080", 
			"Aqua", "#00FFFF", 
			"Cyan", "#00FFFF", 
			"LightCyan", "#E0FFFF", 
			"PaleTurquoise", "#AFEEEE", 
			"Aquamarine", "#7FFFD4", 
			"Turquoise", "#40E0D0", 
			"MediumTurquoise", "#48D1CC", 
			"DarkTurquoise", "#00CED1", 
			"CadetBlue", "#5F9EA0", 
			"SteelBlue", "#4682B4", 
			"LightSteelBlue", "#B0C4DE", 
			"PowderBlue", "#B0E0E6", 
			"LightBlue", "#ADD8E6", 
			"SkyBlue", "#87CEEB", 
			"LightSkyBlue", "#87CEFA", 
			"DeepSkyBlue", "#00BFFF", 
			"DodgerBlue", "#1E90FF", 
			"CornflowerBlue", "#6495ED", 
			"MediumSlateBlue", "#7B68EE", 
			"RoyalBlue", "#4169E1", 
			"Blue", "#0000FF", 
			"MediumBlue", "#0000CD", 
			"DarkBlue", "#00008B", 
			"Navy", "#000080", 
			"MidnightBlue", "#191970", 
			"Cornsilk", "#FFF8DC", 
			"BlanchedAlmond", "#FFEBCD", 
			"Bisque", "#FFE4C4", 
			"NavajoWhite", "#FFDEAD", 
			"Wheat", "#F5DEB3", 
			"BurlyWood", "#DEB887", 
			"Tan", "#D2B48C", 
			"RosyBrown", "#BC8F8F", 
			"SandyBrown", "#F4A460", 
			"Goldenrod", "#DAA520", 
			"DarkGoldenrod", "#B8860B", 
			"Peru", "#CD853F", 
			"Chocolate", "#D2691E", 
			"SaddleBrown", "#8B4513", 
			"Sienna", "#A0522D", 
			"Brown", "#A52A2A", 
			"Maroon", "#800000", 
			"White", "#FFFFFF", 
			"Snow", "#FFFAFA", 
			"Honeydew", "#F0FFF0", 
			"MintCream", "#F5FFFA", 
			"Azure", "#F0FFFF", 
			"AliceBlue", "#F0F8FF", 
			"GhostWhite", "#F8F8FF", 
			"WhiteSmoke", "#F5F5F5", 
			"Seashell", "#FFF5EE", 
			"Beige", "#F5F5DC", 
			"OldLace", "#FDF5E6", 
			"FloralWhite", "#FFFAF0", 
			"Ivory", "#FFFFF0", 
			"AntiqueWhite", "#FAEBD7", 
			"Linen", "#FAF0E6", 
			"LavenderBlush", "#FFF0F5", 
			"MistyRose", "#FFE4E1", 
			"Gainsboro", "#DCDCDC", 
			"LightGray", "#D3D3D3", 
			"Silver", "#C0C0C0", 
			"DarkGray", "#A9A9A9", 
			"Gray", "#808080", 
			"DimGray", "#696969", 
			"LightSlateGray", "#778899", 
			"SlateGray", "#708090", 
			"DarkSlateGray", "#2F4F4F", 
			"Black", "#000000"
	};
	
	private static HashMap<String,String> colorMap;
	
	static {
		colorMap = new HashMap<String,String>();
		for(int i=0; i<colorInfo.length; i+=2) {
			colorMap.put(colorInfo[i].toUpperCase(), colorInfo[i+1]);
		}
	}
	
	public static void notifyFieldAssign(String fieldName) {
		if (frame == null)
			Show(new ArrayList<Value>());
		
		if ("BrushColor".equalsIgnoreCase(fieldName)) {
			if (BrushColor instanceof StrV) {
				String v = ((StrV)BrushColor).getValue();
				BrushColor = hexColor((StrV)BrushColor, GraphicsWindow.defaultBrushColor);
			}
			else 
				throw new InterpretException("BrushColor: Unexpected value" + BrushColor.toString());
		}
		else if ("PenColor".equalsIgnoreCase(fieldName)) {
			if (PenColor instanceof StrV) {
				String v = ((StrV)PenColor).getValue();
				PenColor = hexColor((StrV)PenColor, GraphicsWindow.defaultPenColor);
			}
			else 
				throw new InterpretException("PenColor: Unexpected value" + BrushColor.toString());
		}
		else if ("BackgroundColor".equalsIgnoreCase(fieldName)) {
			if (BackgroundColor instanceof StrV) {
				String v = ((StrV)BackgroundColor).getValue();
				BackgroundColor = hexColor((StrV)BackgroundColor, GraphicsWindow.defaultBackgroundColor);
			}
			else 
				throw new InterpretException("BackgroundColor: Unexpected value" + BrushColor.toString());
		}
		else {
		}
	}
	
	public static Value hexColor(StrV strv, Value defColor) {
		String v = strv.getValue();
		// 1) #xxxxxx
		if (v.charAt(0) == '#') {
			try {
				Integer.parseInt(v.substring(1), 16);
				return strv;
			}
			catch(NumberFormatException e) {
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
}
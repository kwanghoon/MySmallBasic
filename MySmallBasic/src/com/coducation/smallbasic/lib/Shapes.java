package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Shapes {
	
	public static Value AddRectangle(ArrayList<Value> args) {
		// width, height
		// GraphicsWiindow에 사각형을 추가한 창을 반환
		if (args.size() == 2) {
			int width, height;

			if (args.get(0) instanceof DoubleV) {
				width = (int) ((DoubleV) args.get(0)).getValue();
			} else if (args.get(0) instanceof StrV && ((StrV) args.get(0)).isNumber()) {
				width = (int) ((StrV) args.get(0)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {
				height = (int) ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				height = (int) ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));
					
			String id = GraphicsWindow.AddRectangle(width, height);
			
			return new StrV(id);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value AddEllipse(ArrayList<Value> args) {
		// width, height
		// GraphicsWindow에 원을 추가한 창을 반환
		if (args.size() == 2) {
			int width, height;

			if (args.get(0) instanceof DoubleV) {
				width = (int) ((DoubleV) args.get(0)).getValue();
			} else if (args.get(0) instanceof StrV && ((StrV) args.get(0)).isNumber()) {
				width = (int) ((StrV) args.get(0)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {
				height = (int) ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				height = (int) ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));
			
			String id = GraphicsWindow.AddEllipse(width, height);
			
			return new StrV(id);			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value AddTriangle(ArrayList<Value> args) {
		// x1, y1, x2, y2, x3, y3
		// GraphicsWindow에 삼각형을 추가한 창을 반환
		if (args.size() == 6) {
			int[] values = new int[args.size()];

			for (int i = 0; i < args.size(); i++) {
				if (args.get(i) instanceof DoubleV) {
					values[i] = (int) ((DoubleV) args.get(i)).getValue();
				} else if (args.get(i) instanceof StrV && ((StrV) args.get(i)).isNumber()) {
					values[i] = (int) ((StrV) args.get(i)).parseDouble();
				} else
					throw new InterpretException("Unexpected type " + args.get(i));
			}

			String id = GraphicsWindow.AddTriangle(values[0], values[1], values[2], values[3], values[4], values[5]);
			
			return new StrV(id);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value AddLine(ArrayList<Value> args) {
		// x1, y1, x2, y2
		// GraphicsWindow에 선을 추가한 창을 반환
		if (args.size() == 4) {
			int[] values = new int[args.size()];

			for (int i = 0; i < args.size(); i++) {
				if (args.get(i) instanceof DoubleV) {
					values[i] = (int) ((DoubleV) args.get(i)).getValue();
				} else if (args.get(i) instanceof StrV && ((StrV) args.get(i)).isNumber()) {
					values[i] = (int) ((StrV) args.get(i)).parseDouble();
				} else
					throw new InterpretException("Unexpected type " + args.get(i));
			}
			
			String id = GraphicsWindow.AddLine(values[0], values[1], values[2], values[3]);
			
			return new StrV(id);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value AddImage(ArrayList<Value> args) {
		// imageName
		// GraphicsWindow에 이미지를 추가한 창을 반환
		if (args.size() == 1) {
			String name;
			if (args.get(0) instanceof StrV) {
				name = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			String id = GraphicsWindow.AddImage(name);
			
			return new StrV(id);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value AddText(ArrayList<Value> args) {
		// text
		// GraphicsWindow에 text를 추가한 창을 반환
		if (args.size() == 1) {
			String text;
			if (args.get(0) instanceof StrV || args.get(0) instanceof DoubleV) {
				text = args.get(0).toString();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			String id = GraphicsWindow.AddText(text);
			
			return new StrV(id);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetText(ArrayList<Value> args) {
		// shapeName, text
		// shapeName에 해당하는 shape에 text를 설정함
		if (args.size() == 2) {
			String name;
			String text;
			if (args.get(0) instanceof StrV) {
				name = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof StrV || args.get(1) instanceof DoubleV) {
				text = args.get(1).toString();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));
			
			GraphicsWindow.SetText(name, text);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Remove(ArrayList<Value> args) {
		// shapeName
		// shapeName에 해당하는 shape를 삭제함
		if (args.size() == 1) {
			if (args.get(0) instanceof StrV) {
				String shape = ((StrV) args.get(0)).getValue();
				
				GraphicsWindow.ShapesRemove(shape);
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Move(ArrayList<Value> args) {
		// shapeName, x, y
		// shapeName에 해당하는 shape를 새로운 x, y로 이동
		if (args.size() == 3) {
			String shape = "";
			int x, y;
			
			if (args.get(0) instanceof StrV) {
				shape = ((StrV) args.get(0)).getValue();
			} else if(args.get(0) == null) {
				
			}
			else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {
				x = (int) ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				x = (int) ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));

			if (args.get(2) instanceof DoubleV) {
				y = (int) ((DoubleV) args.get(2)).getValue();
			} else if (args.get(2) instanceof StrV && ((StrV) args.get(2)).isNumber()) {
				y = (int) ((StrV) args.get(2)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(2));
			
			GraphicsWindow.ShapesMove(shape, x, y);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Rotate(ArrayList<Value> args) {
		// shapeName, angle
		// shapeName에 해당하는 shape를 angle만큼 회전
		if (args.size() == 2) {
			String shapeName;
			double angle;
			
			if (args.get(0) instanceof StrV) {
				shapeName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));

			if (args.get(1) instanceof DoubleV) {
				angle = ((DoubleV) args.get(1)).getValue();
			} else if (args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				angle = ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));
			
			GraphicsWindow.Rotate(shapeName, angle);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Zoom(ArrayList<Value> args) {
		// shapeName, scaleX, scaleY
		// shapeName에 해당하는 shape에 줌 레벨을 설정
		if (args.size() == 3) {
			String shapeName;
			double scaleX, scaleY;
			
			if(args.get(0) instanceof StrV) {
				shapeName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			if(args.get(1) instanceof DoubleV) {
				scaleX = ((DoubleV) args.get(1)).getValue();
			} else if(args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				scaleX = ((StrV) args.get(1)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(1));
			
			if(args.get(2) instanceof DoubleV) {
				scaleY = ((DoubleV) args.get(2)).getValue();
			} else if(args.get(2) instanceof StrV && ((StrV) args.get(2)).isNumber()) {
				scaleY = ((StrV) args.get(2)).parseDouble();
			} else
				throw new InterpretException("Unexpected type " + args.get(2));
			
			GraphicsWindow.Zoom(shapeName, scaleX, scaleY);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void Animate(ArrayList<Value> args) {
		// shapeName, x, y, duration
		// shapeName에 해당하는 shape를 새로운 x, y로 움직임
		if (args.size() == 4) {
			String shapeName;
			int[] values = new int[args.size()-1];
			if (args.get(0) instanceof StrV) {
				shapeName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			for(int i = 1; i<args.size();i++) {
				if(args.get(i) instanceof DoubleV) {
					values[i-1] = (int) ((DoubleV) args.get(i)).getValue();
				}
				else if(args.get(i) instanceof StrV && ((StrV) args.get(i)).isNumber()) {
					values[i-1] = (int) ((StrV) args.get(i)).parseDouble();
				}
				else
					throw new InterpretException("Unexpected type " + args.get(i));
			}
			
			GraphicsWindow.Animate(shapeName, values[0], values[1], values[2]);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetLeft(ArrayList<Value> args) {
		// shapeName
		// shapeName에 해당하는 shape의 left를 가져옴
		if (args.size() == 1) {
			String shapeName;
			if (args.get(0) instanceof StrV) {
				shapeName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			double left = GraphicsWindow.GetLeft(shapeName);
			
			return new DoubleV(left);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetTop(ArrayList<Value> args) {
		// shapeName
		// shapeName에 해당하는 shape의 top을 가져옴
		if (args.size() == 1) {
			String shapeName;
			if (args.get(0) instanceof StrV) {
				shapeName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			double top = GraphicsWindow.GetTop(shapeName);
			
			return new DoubleV(top);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static Value GetOpacity(ArrayList<Value> args) {
		// shapeName
		// shapeName에 해당하는 shape의 opaque를 반환(0과 100사이)
		if (args.size() == 1) {
			String shapeName;
			if (args.get(0) instanceof StrV) {
				shapeName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			double opacity = GraphicsWindow.GetOpacity(shapeName);
			
			return new DoubleV(opacity);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void SetOpacity(ArrayList<Value> args) {
		// shapeName, level
		// shapeName에 해당하는 shape의 opaque 레벨 설정(0과 100 사이)
		if (args.size() == 2) {
			String shapeName;
			double level;
			
			if (args.get(0) instanceof StrV) {
				shapeName = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			if(args.get(1) instanceof DoubleV) {
				level = ((DoubleV) args.get(1)).getValue();
			}
			else if(args.get(1) instanceof StrV && ((StrV) args.get(1)).isNumber()) {
				level = ((StrV) args.get(1)).parseDouble();
			}
			else
				throw new InterpretException("Unexpected type " + args.get(1));
			
			GraphicsWindow.SetOpacity(shapeName, level);
			
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void HideShape(ArrayList<Value> args) {
		// shapeName
		// shapeName의 shape를 숨김
		if (args.size() == 1) {
			String name;
			if (args.get(0) instanceof StrV) {
				name = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			GraphicsWindow.HideShape(name);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void ShowShape(ArrayList<Value> args) {
		// shapeName
		// shapeName의 shape를 보여줌
		if (args.size() == 1) {
			String name;
			if (args.get(0) instanceof StrV) {
				name = ((StrV) args.get(0)).getValue();
			} else
				throw new InterpretException("Unexpected type " + args.get(0));
			
			GraphicsWindow.ShowShape(name);
		} else
			throw new InterpretException("Unexpected # of args " + args.size());
	}

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
}
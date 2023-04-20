package com.coducation.smallbasic.lib;

import java.util.*;

import com.coducation.smallbasic.*;

public class Chart {

	public static Value Plot(ArrayList<Value> args) {
		String id;
		String key = "";
		double[] x_value = {0, 1, 2};
		double[] y_value = {0, 1, 2};
		String opt = "";
		if(args.size() == 4) {
			if(args.get(1) instanceof ArrayV) {
				if(args.get(2) instanceof ArrayV) {
					key = args.get(0).toString();
					int size = ((ArrayV)args.get(1)).size();
					x_value = new double[size];
					y_value = new double[size];
					opt = args.get(3).toString();
					for(int i = 0; i< size; i++) {
						x_value[i] = ((ArrayV)args.get(1)).get(i+"").getNumber();
						y_value[i] = ((ArrayV)args.get(2)).get(i+"").getNumber();
					}
				} 
			}
		} else if(args.size() == 3) {
			if(args.get(1) instanceof ArrayV) {
				if(args.get(2) instanceof ArrayV) {
					key = args.get(0).toString();
					int size = ((ArrayV)args.get(1)).size();
					x_value = new double[size];
					y_value = new double[size];
					for(int i = 0; i< size; i++) {
						x_value[i] = ((ArrayV)args.get(1)).get(i+"").getNumber();
						y_value[i] = ((ArrayV)args.get(2)).get(i+"").getNumber();
					}
				} 
			}
		} else if(args.size() == 2) {
			if(args.get(1) instanceof ArrayV) {
				key = args.get(0).toString();
				int size = ((ArrayV)args.get(1)).size();
				x_value = new double[size];
				y_value = new double[size];
				for(int i = 0; i< size; i++) {
					x_value[i] = i;
					y_value[i] = ((ArrayV)args.get(1)).get(i+"").getNumber();
				}
			}
		} else if(args.size() == 1) {
			key = args.get(0).toString();
		} else
			throw new InterpretException("Error in # of Arguments:  " + args.size());
		
		GraphicsWindow.AddLine(key, x_value, y_value, opt);
		id = GraphicsWindow.GetChartID();

		return new StrV(id);
	}
	
	public static Value Bar(ArrayList<Value> args) {
		String id;
		String key = "";
		String[] x_value = {"0", "1", "2"};
		double[] y_value = {0, 1, 2};
		String opt = "";
		if(args.size() == 4) {
			if(args.get(1) instanceof ArrayV) {
				if(args.get(2) instanceof ArrayV) {
					key = args.get(0).toString();
					int size = ((ArrayV)args.get(1)).size();
					x_value = new String[size];
					y_value = new double[size];
					opt = args.get(3).toString();
					for(int i = 0; i< size; i++) {
						x_value[i] = ((ArrayV)args.get(1)).get(i+"").toString();
						y_value[i] = ((ArrayV)args.get(2)).get(i+"").getNumber();
					}
				} 
			}
		} else if(args.size() == 3) {
			if(args.get(1) instanceof ArrayV) {
				if(args.get(2) instanceof ArrayV) {
					key = args.get(0).toString();
					int size = ((ArrayV)args.get(1)).size();
					x_value = new String[size];
					y_value = new double[size];
					for(int i = 0; i< size; i++) {
						x_value[i] = ((ArrayV)args.get(1)).get(i+"").toString();
						y_value[i] = ((ArrayV)args.get(2)).get(i+"").getNumber();
					}
				} 
			}
		} else if(args.size() == 2) {
			if(args.get(1) instanceof ArrayV) {
				key = args.get(0).toString();
				int size = ((ArrayV)args.get(1)).size();
				x_value = new String[size];
				y_value = new double[size];
				for(int i = 0; i< size; i++) {
					x_value[i] = i+"";
					y_value[i] = ((ArrayV)args.get(1)).get(i+"").getNumber();
				}
			}
		} else if(args.size() == 1) {
			key = args.get(0).toString();
		} else
			throw new InterpretException("Error in # of Arguments:  " + args.size());
		
		GraphicsWindow.AddBar(key, x_value, y_value, opt);
		id = GraphicsWindow.GetChartID();

		return new StrV(id);
	}


	public static Value SetData(ArrayList<Value> args) {
		if(args.size() == 4){
			if(args.get(0) instanceof StrV) {
				if(args.get(1) instanceof StrV) {
					if(args.get(2) instanceof ArrayV) {
						if(args.get(3) instanceof ArrayV) {
							String name = args.get(0).toString();
							String key = args.get(1).toString();
							int size = ((ArrayV)args.get(2)).size();
							String[] x_value = new String[size];
							double[] y_value = new double[size];
							for(int i = 0; i< size; i++) {
								x_value[i] = ((ArrayV)args.get(2)).get(i+"").toString();
								y_value[i] = ((ArrayV)args.get(3)).get(i+"").getNumber();
							}
							GraphicsWindow.SetData(name, key, x_value, y_value);
						} else 
							throw new InterpretException("Not Array Value for y_value" + args.get(3));
					} else 
						throw new InterpretException("Not Array Value for x_value" + args.get(2));
				}
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));

		} else
			throw new InterpretException("Error in # of Arguments: " + args.size());

		return null;
	}
	
	public static Value AddData(ArrayList<Value> args) {
		String name = args.get(0).toString();
		String key = args.get(1).toString();
		String[] x_value = {"0", "1", "2"};
		double[] y_value = {0, 1, 2};
		String opt = "";
		if(args.size() == 5) {
			int size = ((ArrayV)args.get(2)).size();
			x_value = new String[size];
			y_value = new double[size];
			opt = args.get(4).toString();
			for(int i = 0; i< size; i++) {
				x_value[i] = ((ArrayV)args.get(2)).get(i+"").toString();
				y_value[i] = ((ArrayV)args.get(3)).get(i+"").getNumber();
			}
		} else if(args.size() == 4) {
			if(args.get(0) instanceof StrV) {
				if(args.get(1) instanceof StrV) {
					if(args.get(2) instanceof ArrayV) {
						if(args.get(3) instanceof ArrayV) {
							int size = ((ArrayV)args.get(2)).size();
							x_value = new String[size];
							y_value = new double[size];
							for(int i = 0; i< size; i++) {
								x_value[i] = ((ArrayV)args.get(2)).get(i+"").toString();
								y_value[i] = ((ArrayV)args.get(3)).get(i+"").getNumber();
							}
							
						} else 
							throw new InterpretException("Not Array Value for y_value" + args.get(3));
					} else 
						throw new InterpretException("Not Array Value for x_value" + args.get(2));
				}
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));

		} else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		GraphicsWindow.AddData(name, key, x_value, y_value, opt);

		return null;
	}
	
	public static Value Title(ArrayList<Value> args) {
		if(args.size() == 2){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				String title = args.get(1).toString();
				GraphicsWindow.AddTitle(name, title);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		} else
			throw new InterpretException("Error in # of Arguments: " + args.size());

		return null;
	}
	public static Value XLabel(ArrayList<Value> args) {
		if(args.size() == 2){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				String x_label = args.get(1).toString();
				GraphicsWindow.AddXLabel(name, x_label);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		} else
			throw new InterpretException("Error in # of Arguments: " + args.size());

		return null;
	}
	public static Value YLabel(ArrayList<Value> args) {
		if(args.size() == 2){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				String y_label = args.get(1).toString();
				GraphicsWindow.AddYLabel(name, y_label);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		} else
			throw new InterpretException("Error in # of Arguments: " + args.size());

		return null;
	}

	public static Value ShowLegend(ArrayList<Value> args) {
		if(args.size() == 1){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				GraphicsWindow.AddLegend(name);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		} else
			throw new InterpretException("Error in # of Arguments: " + args.size());

		return null;
	}
	
	public static Value HideLegend(ArrayList<Value> args) {
		if(args.size() == 1){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				GraphicsWindow.RemoveLegend(name);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		} else
			throw new InterpretException("Error in # of Arguments: " + args.size());

		return null;
	}
	
	public static Value SetSize(ArrayList<Value> args){
		if(args.size() == 3){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				int w = (int)args.get(1).getNumber();
				int h = (int)args.get(2).getNumber();
				GraphicsWindow.SetChartSize(name,w,h);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}
	
	public static Value GetWidth(ArrayList<Value> args){
		double width;
		if(args.size() == 1){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				width = GraphicsWindow.GetChartWidth(name);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return new DoubleV(width);
	}
	
	public static Value GetHeight(ArrayList<Value> args){
		double height;
		if(args.size() == 1){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				height = GraphicsWindow.GetChartHeight(name);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return new DoubleV(height);
	}

	public static Value SetLocation(ArrayList<Value> args){
		if(args.size() == 3){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				int x = (int)args.get(1).getNumber();
				int y = (int)args.get(2).getNumber();
				GraphicsWindow.SetChartLocation(name,x,y);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}
	
	public static Value GetX(ArrayList<Value> args){
		double x;
		if(args.size() == 1){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				x = GraphicsWindow.GetChartX(name);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return new DoubleV(x);
	}
	
	public static Value GetY(ArrayList<Value> args){
		double y;
		if(args.size() == 1){
			if(args.get(0) instanceof StrV) {
				String name = args.get(0).toString();
				y = GraphicsWindow.GetChartY(name);
			} else 
				throw new InterpretException("Not String Value for chartName" + args.get(0));
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return new DoubleV(y);
	}

}
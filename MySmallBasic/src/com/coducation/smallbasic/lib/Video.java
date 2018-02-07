package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Video {


	public static Value Play(ArrayList<Value> args){
		new NativeDiscovery().discover();
		String id;
		GraphicsWindow.AddCanvas();
		if(args.size() == 0){
			id = GraphicsWindow.GetID();
		}else if(args.size() == 1){
			Value arg = args.get(0);
			String s = arg.toString();
			if(arg instanceof StrV){
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					GraphicsWindow.AddVideo(s);
				}
			});
			id = GraphicsWindow.GetID();

		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	
		return new StrV(id);
	}

	public static Value SetSize(ArrayList<Value> args){
		if(args.size() == 3){
			String name = ((StrV) args.get(0)).getValue();
			int x = (int)args.get(1).getNumber();
			int y = (int)args.get(2).getNumber();

			GraphicsWindow.SetVideoSize(name,x,y);
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}

	public static Value SetLocation(ArrayList<Value> args){
		if(args.size() == 3){
			String name = ((StrV) args.get(0)).getValue();
			int x = (int)args.get(1).getNumber();
			int y = (int)args.get(2).getNumber();

			GraphicsWindow.SetVideoLocation(name,x,y);
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}

	public static Value Pause(ArrayList<Value> args){
		if(args.size() == 1){
			String name = ((StrV) args.get(0)).getValue();
			System.out.println(name);
			GraphicsWindow.VideoPause(name);
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}

	public static Value Rewind(ArrayList<Value> args){
		if(args.size() == 1){
			String name = ((StrV) args.get(0)).getValue();
			GraphicsWindow.VideoRewind(name);
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}

	public static Value Skip(ArrayList<Value> args){
		if(args.size() == 1){
			String name = ((StrV) args.get(0)).getValue();
			GraphicsWindow.VideoSkip(name);
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}
	
	public static Value Stop(ArrayList<Value> args){
		if(args.size() == 1){
			String name = ((StrV) args.get(0)).getValue();
			GraphicsWindow.VideoStop(name);
		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}

	public static Value SetPath(ArrayList<Value> args){
		if(args.size() == 2){
			String name = ((StrV) args.get(0)).getValue();
			String path = args.get(1).toString();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					GraphicsWindow.SetVideoPath(name,path);
				}
			});

		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());
		return null;
	}
}


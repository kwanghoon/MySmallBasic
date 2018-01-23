package com.coducation.smallbasic.lib;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;


import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Video {


	public static Value Play(ArrayList<Value> args){

		if(args.size() == 0){
			new NativeDiscovery().discover();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new GraphicsWindow();
					GraphicsWindow.AddVideo(args);
				}
			});

		}
		else if(args.size() == 1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
			new NativeDiscovery().discover();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new GraphicsWindow();
					GraphicsWindow.AddVideo(args);
				}
			});

		}
		else if(args.size() == 2) {
			Value arg = args.get(0);
			if(arg instanceof StrV){
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
			String s = (String)((StrV)args.get(1)).getValue();
			if(s.equals("controls")) {
				new NativeDiscovery().discover();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new GraphicsWindow();
						GraphicsWindow.AddVideo(args);
					}
				});
			}else 
				throw new InterpretException("Not String Value for filePath" + arg);


		}else
			throw new InterpretException("Error in # of Arguments: " + args.size());

		return null;
	}

}


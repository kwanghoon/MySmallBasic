package com.coducation.smallbasic.lib;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class ImageList {
	
	private static HashMap<String, Image> image_list = new HashMap<>();
	
	private static int key = 1 ;

	public static Value LoadImage(ArrayList<Value> args){
		
		String str_arg;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("LoadImage : Unexpected arg");

			}

		} else {

			throw new InterpretException("LoadImage : Unexpected # of args: " + args.size());
			
		}
		
		URL url = null;
		Image img = null;
		boolean isURL = false;

		try {
			
			url = new URL(str_arg);
			isURL = true;
			
		} catch (MalformedURLException e) { /* Ignore */ } 
		
		if (isURL) {
			
			img = new ImageIcon(url).getImage();
			image_list.put("ImageList" + key, img);
			
		} else {
			
			img = new ImageIcon(str_arg).getImage();
			image_list.put("ImageList" + key, img);
			
		}
			
		return new StrV("ImageList" + key++);

	}
	
	public static Value GetWidthOfImage(ArrayList<Value> args){
		
		String str_arg;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("GetWidthOfImage : Unexpected arg");

			}

		} else

			throw new InterpretException("GetWidthOfImage : Unexpected # of args: " + args.size());
		
		return new DoubleV(image_list.get(str_arg).getWidth(null));
		
	}
	
	public static Value GetHeightOfImage(ArrayList<Value> args){
		
		String str_arg;
		
		if (args.size() == 1) {
			
			if (args.get(0) instanceof StrV) {

				str_arg = ((StrV) args.get(0)).getValue();

			} else {

				throw new InterpretException("GetWidthOfImage : Unexpected arg");

			}

		} else

			throw new InterpretException("GetWidthOfImage : Unexpected # of args: " + args.size());
		
		return new DoubleV(image_list.get(str_arg).getHeight(null));
		
	}
	
	public static Image getImage(String image_key) {
		
		return image_list.get(image_key);
		
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
	
}

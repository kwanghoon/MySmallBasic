package com.coducation.smallbasic.lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Sound {
	
	public static Value PlayClick(ArrayList<Value> args){
		if(args.size()==0){
		sound("resource\\click.wav");
		return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value PlayClickAndWait(ArrayList<Value> args){
		if(args.size()==0){
			sound("resource\\click.wav");
			return null;
			}
			else
				throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value PlayChime(ArrayList<Value> args){
		if(args.size()==0){
			sound("resource\\chime.wav");
			return null;
			}
			else
				throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value PlayChimeAndWait(ArrayList<Value> args){
		if(args.size()==0){
			sound("resource\\chime.wav");
			return null;
			}
			else
				throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value PlayChimes(ArrayList<Value> args){
		if(args.size()==0){
			sound("resource\\chimes.wav");
			return null;
			}
			else
				throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value PlayChimesAndWait(ArrayList<Value> args){
		if(args.size()==0){
			sound("resource\\chimes.wav");
			return null;
			}
			else
				throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value PlayBellRing(ArrayList<Value> args){
		if(args.size()==0){
			sound("resource\\bellring.wav");
			return null;
			}
			else
				throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value PlayBellRingAndWait(ArrayList<Value> args){
		if(args.size()==0){
			sound("resource\\bellring.wav");
			return null;
			}
			else
				throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//악보...
	public static Value PlayMusic(ArrayList<Value> args){
		return null;
	}
	
	public static Value Play(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
		return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value PlayAndWait(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
		return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value Pause(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
		return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static Value Stop(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				String s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
		return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static void sound(String audioFilePath) {
		File audioFile = new File(audioFilePath);
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			AudioFormat format = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
			audioLine.open(format);
			audioLine.start();

			byte[] bytesBuffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
				audioLine.write(bytesBuffer, 0, bytesRead);
			}
			audioLine.drain();
			audioLine.close();
			audioStream.close();


		} catch (UnsupportedAudioFileException ex) {
			System.out.println("The specified audio file is not supported.");
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		} catch (IOException ex) {
			System.out.println("Error playing the audio file.");
			ex.printStackTrace();
		}       
		
	}
	
	public static void notifyFieldAssign(String fieldName) {
		//비어두기 
	}

	public static void notifyFieldRead(String fieldName) {
		//비어두기 
	}
	
	private static final int BUFFER_SIZE = 4096;
}

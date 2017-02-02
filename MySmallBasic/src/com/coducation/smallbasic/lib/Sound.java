package com.coducation.smallbasic.lib;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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


import javazoom.jl.decoder.JavaLayerException;

import javazoom.jl.player.Player;


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
			String s;
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
			try {
				
				if(status){
					input = new FileInputStream(s);
					m = new MP3(input);
					m.play();
					status = false;
				
				}else{
					m.play();
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	public static Value PlayAndWait(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			String s;
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);

			FileInputStream input;
			try {
				input = new FileInputStream(s);
				m = new MP3(input);
				m.play();
				try {
					Thread.sleep(500000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	public static Value Pause(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			String s;
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
			m.pause();
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
			m.stop();
			status = true;
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
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
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
	private static MP3 m ;
	private static FileInputStream input;
	private static boolean status = true;
}


class MP3{

	private final static int NORMAL = 0;
	private final static int PLAY = 1;
	private final static int PAUSE = 2;
	private final static int FINISH = 3;

	private final Player player;
	private int playerStatus = NORMAL;

	public MP3(final InputStream input) throws JavaLayerException {
		this.player = new Player(input);
	}

	public void play() throws JavaLayerException {
		synchronized (this) {
			switch (playerStatus) {
			case NORMAL:
				Runnable r = new Runnable() {
					public void run() {
						playInternal();
					}
				};
				Thread t = new Thread(r);
				t.setDaemon(true);
				t.setPriority(Thread.MAX_PRIORITY);
				playerStatus = PLAY;
				t.start();
				break;
			case PAUSE:
				resume();
				break;
			default:
				break;
			}
		}
	}


	public boolean pause() {
		synchronized (this) {
			if (playerStatus == PLAY) {
				playerStatus = PAUSE;
			}
			return playerStatus == PAUSE;
		}
	}

	
	public boolean resume() {
		synchronized (this) {
			if (playerStatus == PAUSE) {
				playerStatus = PLAY;
				this.notify();
			}
			return playerStatus == PLAY;
		}
	}

	
	public void stop() {
		synchronized (this) {
			playerStatus = FINISH;
			this.notify();
		}
	}

	private void playInternal() {
		while (playerStatus != FINISH) {
			try {
				if (!player.play(1)) {
					break;
				}
			} catch (final JavaLayerException e) {
				break;
			}
			synchronized (this) {
				while (playerStatus == PAUSE) {
					try {
						this.wait();
					} catch (final InterruptedException e) {
						break;
					}
				}
			}
		}
		close();
	}

	//종료
	public void close() {
		synchronized (this) {
			playerStatus = FINISH;
		}
		try {
			player.close();
		} catch (final Exception e) {
		}
	}
}
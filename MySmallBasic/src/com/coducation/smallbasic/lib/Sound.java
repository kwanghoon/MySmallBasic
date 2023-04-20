package com.coducation.smallbasic.lib;
 
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jfugue.player.Player;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;
 
 
public class Sound {
 
	public static Value PlayClick(ArrayList<Value> args){
		if(args.size()==0){
			if(st != null)
			{
				st.interrupt();
			}
			st = new SoundThread("resource\\click.wav");
			st.start();
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
			if(st != null)
			{
				st.interrupt();
			}
			st = new SoundThread("resource\\chime.wav");
			st.start();
 
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
			if(st != null)
			{
				st.interrupt();
			}
			st = new SoundThread("resource\\chimes.wav");
			st.start();
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
			if(st != null)
			{
				st.interrupt();
			}
			st = new SoundThread("resource\\bellring.wav");
			st.start();
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
 
	public static Value PlayMusic(ArrayList<Value> args){
	      if(args.size() == 1) {
	         String notes = args.get(0).toString().trim();
	         String[] arr = notes.split(" ");
	         String o = arr[0].toLowerCase();
	         Player player = new Player();
	         if(o.contains("o") && arr.length > 1) {
	            String octave = String.valueOf(o.charAt(1));
	            String result = "";
	            for(int i = 1; i < arr.length; i++) {
	               String note = String.valueOf(arr[i].charAt(0));
	               String dur = String.valueOf(arr[i].charAt(1));
	               String duration = notesDuration(dur);
	               result += note+octave+duration+" ";
	            }
	            player.play(result);
	            //System.out.println(result);
	         }else {
	            if(arr.length == 1) {
	               if(o.contains("o")) {
	                  return null;
	               }else {
	                  String note = String.valueOf(o.charAt(0));
	                  String dur = String.valueOf(o.charAt(1));
	                  String duration = notesDuration(dur);
	                  player.play(note+"1"+duration);
	               }
	            }else {
	               String result = "";
	               for(int i = 0; i < arr.length; i++) {
	                  String note = String.valueOf(arr[i].charAt(0));
	                  String dur = String.valueOf(arr[i].charAt(1));
	                  String duration = notesDuration(dur);
	                  result += note+"1"+duration+" ";
	               }
	               player.play(result);
	            }
	            
	         }
	      }
	      else {
	         throw new InterpretException("Error in # of Arguments: " + args.size());
	      }
	      return null;
	   }
 
	public static Value Play(ArrayList<Value> args) throws URISyntaxException{
		if(args.size()==1){
			Value arg = args.get(0);
			String s;
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
 
			if(m.getPause() == PAUSE)
			{
				m.Start();
			}
			else
			{
				m = new MP3(s);
				m.Start();
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
 
			m = new MP3(s);
			m.Start();
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
			m.Pause();
			return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
 
	public static Value Stop(ArrayList<Value> args){
		if(args.size()==1){
			Value arg = args.get(0);
			String s;
			if(arg instanceof StrV){
				StrV str_arg = (StrV) arg;
				s = str_arg.getValue();
			}
			else 
				throw new InterpretException("Not String Value for filePath" + arg);
 
			m.Stop();
			return null;
		}
		else
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static String notesDuration(String notes) {
	      switch(notes) {
	      case "1":
	         return "hi";
	      case "2":
	         return "qi";
	      case "3":
	         return "q";
	      default:
	         return "is";
	      }
	   }
 
	public static void sound(String audioFilePath) 
	{
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
 
	private final static int BUFFER_SIZE = 4096;
	private static MP3 m = new MP3();
	private final static int PAUSE = 2;
	private static SoundThread st;
	
	
}
 
 
class MP3{
 
	private Clip clip;
	private File wavFile;
	private AudioInputStream ais;
	private boolean isPlay = false;
	private final static int PAUSE = 2;
	private int isPause = 1;
 
	public MP3(){}
 
	public MP3(String s)
	{
		try {
			URL url = new URL(s);
			ais = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch(MalformedURLException e1){
			wavFile = new File(s);
			try {
				ais = AudioSystem.getAudioInputStream(wavFile);
				clip = AudioSystem.getClip();
				clip.open(ais);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
 
			e.printStackTrace();
		}
	}
 
	public void Start()
	{
		if(!isPlay)
		{
			try
			{
				clip.start();
				isPlay = true;
			}catch(Exception e){}
		}
	}
 
	public void Pause()
	{
		if(isPlay)
		{
			clip.stop();
			isPause = PAUSE;
			isPlay = false;
		}
	}
 
 
	public void Stop()
	{
		if(isPlay)
		{
			clip.setMicrosecondPosition(0);
			clip.stop();
			isPlay = false;
		}
	}
 
	public int getPause()
	{
		return isPause;
	}
 
}
 
/*
 * Bricks.sb 예제에서 block 제거 시 나는 소리가 재생되는 동안 게임이 진행안되는 부분 수정
 * Play...() 메소드에서 이용 
 */
 
class SoundThread extends Thread{
 
	String filename;
	Clip clip;
 
	public SoundThread(String filename)
	{
		this.filename = filename;
	}
 
	public void run() 
	{
			try 
			{
				File file = new File(filename);
				AudioInputStream ais = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(ais);
				clip.start();
				Thread.sleep(clip.getMicrosecondLength()/1000); //음악재생동안 sleep   
				clip.stop();
			} 
			catch (UnsupportedAudioFileException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} 
			catch (LineUnavailableException e) 
			{
				e.printStackTrace();
			}       
			catch (InterruptedException e) 
			{
				if(clip != null)
				{
					clip.stop();
				}
			}
		}
}
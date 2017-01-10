package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Sound {
	
	public static Value PlayClick(ArrayList<Value> args){
		return null;
	}
	
	public static Value PlayClickAndWait(){
		return null;
	}
	
	public static Value PlayChime(){
		return null;
	}
	
	public static Value PlayChimeAndWait(){
		return null;
	}
	
	public static Value PlayChimes(){
		return null;
	}
	
	public static Value PlayChimesAndWait(){
		return null;
	}
	
	public static Value PlayBellRing(){
		return null;
	}
	
	public static Value PlayBellRingAndWait(){
		return null;
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
	
	public static void notifyFieldAssign(String fieldName) {
		//비어두기 
	}

	public static void notifyFieldRead(String fieldName) {
		//비어두기 
	}
}

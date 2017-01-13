package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.Value;


public class Turtle 
{
	static
	{
		Speed = new DoubleV(5);
		Angle = new DoubleV(0);
		X = new DoubleV(320);
		Y = new DoubleV(240);
		
		//private
		isPenDown = true;
		isTurtleShow = true;
	}
	
	//Turtle operations
	public static Value Speed;
	public static Value Angle;
	public static Value X;
	public static Value Y;
	
	private static boolean isPenDown;
	private static boolean isTurtleShow;
	
	//Turtle.Show() - show the turtle
	public static void Show(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{
			isTurtleShow = true;
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//Turtle.Hide() - hide the turtle
	public static void Hide(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{
			isTurtleShow = false;
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}	

	//Turtle.PenDown() - set the pen down
	public static void PenDown(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{
			isPenDown = true;
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}	

	//Turtle.PenUp() - liftt the pen up
	public static void PenUp(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{
			isPenDown = false;
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//Turtle.Move(distance)
	public static void Move(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 1) 
		{
			//parameter
			Value arg1 = args.get(0);	//distance
			
			//check arg_type
			if (!(arg1 instanceof DoubleV))
				throw new InterpretException("Not Number Value for distance " + arg1);
			
			
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//Turtle.MoveTo(x, y)
	public static void MoveTo(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 2) 
		{
			//parameter
			Value arg1 = args.get(0);	//x
			Value arg2 = args.get(1);	//y
			
			//check arg_type
			if (!(arg1 instanceof DoubleV))
				throw new InterpretException("Not Number Value for x " + arg1);
			else if (!(arg2 instanceof DoubleV)) 
				throw new InterpretException("Not Number Value for y " + arg2);
			
			
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//Turtle.Turn(angle) - if angle is positive, turn to right
	public static void Turn(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 1) 
		{
			//parameter
			Value arg1 = args.get(0);	//angle
			
			//check arg_type
			if (!(arg1 instanceof DoubleV))
				throw new InterpretException("Not Number Value for distance " + arg1);
			
			
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//Turtle.TurnRight() - Turns the turtle 90 degrees to the right 
	public static void TurnRight(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{	
			
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//Turtle.TurnLeft() - Turns the turtle 90 degrees to the left
	public static void TurnLeft(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{	
			
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
}

package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.ArrayV;
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
		isCalled = false;
		isPenDown = true;
		isTurtleShow = true;
		
		delayTime = 7;
		angleUnit = 1;
		distanceUnit = 2;
	}
	
	//Turtle operations
	public static Value Speed;
	public static Value Angle;
	public static Value X;
	public static Value Y;
	
	private static boolean isPenDown;
	// show turtle image
	private static boolean isTurtleShow;
	// at least once called
	private static boolean isCalled;
	
	// related with speed
	private static int delayTime;
	// rotating angle at a time
	private static int angleUnit;
	// move distance at a time
	private static int distanceUnit;
	
	//Turtle.Show() - show the turtle - 미구현(turtle)
	public static void Show(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{
			initialCallCheck();
			
			isTurtleShow = true;			
			//repaint?
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//Turtle.Hide() - hide the turtle - 미구현(turtle)
	public static void Hide(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{
			initialCallCheck();
			
			isTurtleShow = false;
			//repaint?
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

	//Turtle.PenUp() - lift the pen up
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
			//check parameter_type
			if(!numTypeCheck(args.get(0)))	
				throw new InterpretException("Not Number Value for x " + args.get(0));
			
			double distance = Double.parseDouble(args.get(0).toString());
			
			initialCallCheck();
			
			move(distance);
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//Turtle.MoveTo(x, y) - 미구현
	public static void MoveTo(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 2) 
		{
			//parameter
			Value arg1 = args.get(0);	//x
			Value arg2 = args.get(1);	//y
			
			//check parameter_type
			if(!numTypeCheck(arg1))	
				throw new InterpretException("Not Number Value for x " + arg1);
			else if(!numTypeCheck(arg2))
				throw new InterpretException("Not Number Value for y" + arg2);
			double x = Double.parseDouble(arg1.toString());
			double y = Double.parseDouble(arg2.toString());
			
			// calculate angle			
			
			double dx = x - ((DoubleV)X).getValue();
			double dy = y - ((DoubleV)Y).getValue();			   
			double rad= java.lang.Math.atan2(dx, dy);
			double degree = (rad*180)/java.lang.Math.PI ;
			if(degree < 0)
				degree = 180 - degree;
		
			//calculate distance
			double distance = java.lang.Math.sqrt(
					java.lang.Math.pow(java.lang.Math.abs(dx), 2)
					 + java.lang.Math.pow(java.lang.Math.abs(dy), 2));
			
			initialCallCheck();
			turn(degree);
			move(distance);
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
			//check parameter_type
			if(!numTypeCheck(args.get(0)))	
				throw new InterpretException("Not Number Value for angle " + args.get(0));
			
			double angle = Double.parseDouble(args.get(0).toString());
			
			initialCallCheck();
			turn(angle);		
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
			initialCallCheck();
			
			turn(90);
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
			initialCallCheck();
			
			turn(-90);
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//move distance -- turtle이미지 미구현
	private static void move(double distance)
	{
		double movedV = 0;
		double curX = ((DoubleV)X).getValue();
		double curY = ((DoubleV)Y).getValue();
		double initialX = curX;
		double initialY = curY;
		double angle = ((DoubleV)Angle).getValue();

		// distanceUnit at a once
		double step;
		
		if(distance < 0)
		{
			step = -distanceUnit;
			distance = -distance;
		}
		else
			step = distanceUnit;
	
		//move
		while(movedV != distance)
		{
			if(movedV + step <= distance)
			{
				curX = curX + step * java.lang.Math.sin(angle * java.lang.Math.PI / 180);
				curY = curY - step * java.lang.Math.cos(angle * java.lang.Math.PI / 180);
				movedV += distanceUnit;
			}
			else
			{
				curX = initialX + distance * java.lang.Math.sin(angle * java.lang.Math.PI / 180);
				curY = initialY - distance * java.lang.Math.cos(angle * java.lang.Math.PI / 180);
				movedV = distance;
			}
			
			//거북이 그림 옮기기... 구현할 것
			
			if(isPenDown)
				drawLine(curX, curY);
			
			//set cur x, y
			((DoubleV)X).setValue(curX);
			((DoubleV)Y).setValue(curY);
				
			// turning speed
			try 
			{
				Thread.sleep( (long)(delayTime + (11 - ((DoubleV)Speed).getValue()) ) );
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	// drawLine to (x, y). used in method move
	private static void drawLine(double x, double y)
	{
		ArrayList<Value> args = new ArrayList<Value> ();
		args.add(0, X);
		args.add(1, Y);
		args.add(2, new DoubleV(x));
		args.add(3, new DoubleV(y));
		GraphicsWindow.DrawLine(args);
	}
	//turn right with angle - 패널과 연결 필요
	private static void turn(double angle)
	{
		double rotatedV = 0;
		double curAngle = ((DoubleV)Angle).getValue();
		double initialAngle = curAngle;
		boolean isPositive = true;
		if(angle < 0)
		{
			angle = -angle;
			isPositive = false;
		}
		while(rotatedV < angle)
		{
			//turn
			if(rotatedV + angleUnit <= angle)
			{
				rotatedV += angleUnit;		
				if(isPositive)
					curAngle += angleUnit;
				else
					curAngle -= angleUnit;
			}
			else
			{
				double  tmp = angle - rotatedV;
				rotatedV = angle;
				
				if(isPositive)
					curAngle += tmp;
				else
					curAngle -= tmp;
			}	
			
			//num check
			if(curAngle >= 360)
				curAngle -= 360;
			else if(curAngle < 0)
				curAngle += 360;
		
			//set real angle
			((DoubleV)Angle).setValue(curAngle);
					
			//turtle repaint....
			//그래픽윈도우랑 해야하는부분...
				
			// turning speed
			try 
			{
				Thread.sleep( (long)(delayTime + (11 - ((DoubleV)Speed).getValue()) ) );
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	//if num_type, return true
	private static boolean numTypeCheck(Value value)
	{
		if(value instanceof ArrayV)
			return false;
		try 
		{  
			Double.parseDouble(value.toString());      
			return true;
		} 
		catch (NumberFormatException e) 
		{      
			return false;   
		}
	}
	// if initial call, setting about turtle
	private static void initialCallCheck()
	{
		if(!isCalled)
		{
			isCalled = true;
		}
	}

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}	
}

package com.coducation.smallbasic.lib;

import java.util.ArrayList;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
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
		
		angleUnit = 1;
		distanceUnit = 1;
		turtleDistanceUnit = 1.05;
	}
	
	//Turtle Properties
	public static Value Speed;
	public static Value Angle;
	public static Value X;
	public static Value Y;
	
	private static boolean isPenDown;
	// show turtle image
	private static boolean isTurtleShow;
	// at least once called
	private static boolean isCalled;
	
	// rotating angle at a time
	private static int angleUnit;
	// move distance at a time
	private static double distanceUnit;
	private static double turtleDistanceUnit;
	// turtle image id
	private static StrV turtleID;
	// turtle image width / 2
	private static double turtleWidthDivTwo;
	//turtle image height / 2
	private static double turtleHeightDivTwo;
	
	//Turtle.Show() - show the turtle
	public static void Show(ArrayList<Value> args)
	{
		//check args_number
		if (args.size() == 0) 
		{
			initialCallCheck();
			
			if(!isTurtleShow)
			{
				//repaint
				ArrayList<Value> arg = new ArrayList<Value> ();
				arg.add(0, turtleID);
				Shapes.ShowShape(arg);
				
				isTurtleShow = true;		
			}
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
			initialCallCheck();
			
			if(isTurtleShow)
			{
				//repaint
				ArrayList<Value> arg = new ArrayList<Value> ();
				arg.add(0, turtleID);
				Shapes.HideShape(arg);
				
				isTurtleShow = false;
			}
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
	//Turtle.MoveTo(x, y)
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
			double degree = java.lang.Math.atan(dy/dx) * (180.0/java.lang.Math.PI);			 
		    if(dx< 0.0)
		        degree += 180.0;
		    else if(dy<0.0) 
		    	degree += 360.0;
		    degree += 90;
		    
		    //calculate turnValue
		    double turnValue = degree - ((DoubleV)Angle).getValue();
		    if(turnValue >= 360)
		    	turnValue = turnValue % 360;	 
		    if(turnValue > 180)
		    	turnValue -= 360;
		    
		    
			//calculate distance
			double distance = java.lang.Math.sqrt(
					java.lang.Math.pow(java.lang.Math.abs(dx), 2)
					 + java.lang.Math.pow(java.lang.Math.abs(dy), 2));
			
			initialCallCheck();
			turn(turnValue);
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
	
	//move distance
	private static void move(double distance)
	{
		double movedV = 0;
		double turtleMovedV = 0;
		
		//current line's x, y
		double curX = ((DoubleV)X).getValue();
		double curY = ((DoubleV)Y).getValue();
		//turtle's x, y
		double turtleX = curX;
		double turtleY = curY;
		
		double initialX = curX;
		double initialY = curY;
		
		double angle = ((DoubleV)Angle).getValue();
		// distanceUnit at a once
		double step;
		double turtleStep;
		
		if(distance < 0)
		{
			step = -distanceUnit;
			turtleStep = -turtleDistanceUnit;
			distance = -distance;
		}
		else
		{
			step = distanceUnit;
			turtleStep = turtleDistanceUnit;
		}
	
		//move
		while(movedV != distance)
		{
			//calculate line step
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
			
			//calculate turtle step
			if(turtleMovedV < distance)
			{
				if(turtleMovedV + step <= distance)
				{
					turtleX = turtleX + turtleStep * java.lang.Math.sin(angle * java.lang.Math.PI / 180);
					turtleY = turtleY - turtleStep * java.lang.Math.cos(angle * java.lang.Math.PI / 180);
					turtleMovedV += turtleDistanceUnit;
				}
				else
				{
					turtleX = initialX + distance * java.lang.Math.sin(angle * java.lang.Math.PI / 180);
					turtleY = initialY - distance * java.lang.Math.cos(angle * java.lang.Math.PI / 180);
					turtleMovedV = distance;
				}
			}
			
			//draw line		
			if(isPenDown)
				drawLine(curX, curY);
			
			//turtle_image move
			if(isTurtleShow)
				repaintTurtle(turtleX, turtleY);
			
			//set cur x, y
			((DoubleV)X).setValue(curX);
			((DoubleV)Y).setValue(curY);
			
			// turning speed
			try 
			{
				Thread.sleep(getSleepTime());
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
	//turn right with angle
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
			//calculate turn_angle
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
		
			//set real angle
			((DoubleV)Angle).setValue(curAngle);
					
			//repaint turtle
			rotateTurtleImg();
				
			// turning speed
			try 
			{
				Thread.sleep(getSleepTime() / 4);
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
			
			//loadImage
			ArrayList<Value> args = new ArrayList<Value> ();
			args.add(0, new StrV(System.getProperty("user.dir") + "/resource/Turtle.png"));			
			StrV turtleImageListID = (StrV)ImageList.LoadImage(args);
			
			//get size
			args.clear();
			args.add(0, turtleImageListID);
			turtleWidthDivTwo = ((DoubleV)ImageList.GetWidthOfImage(args)).getValue() / 2;
			turtleHeightDivTwo = ((DoubleV)ImageList.GetHeightOfImage(args)).getValue() / 2;
			
			//add to Shapes
			args.clear();
			args.add(0, new StrV(System.getProperty("user.dir") + "/resource/Turtle.png"));
			turtleID = (StrV)Shapes.AddImage(args);
			
			repaintTurtle();
			rotateTurtleImg();
		}
	}
	// repaint Turtle image in (X, Y)
	private static void repaintTurtle()
	{
		repaintTurtle(((DoubleV)X).getValue(), ((DoubleV)Y).getValue());	
	}
	//repaint Turtle image in (x, y)
	private static void repaintTurtle(double x, double y)
	{
		ArrayList<Value> args = new ArrayList<Value> ();
		args.add(0, turtleID);
		args.add(1, new DoubleV(x - turtleWidthDivTwo));
		args.add(2, new DoubleV(y - turtleHeightDivTwo));
		Shapes.Move(args);	
	}
	// rotate turtle
	private static void rotateTurtleImg()
	{
		ArrayList<Value> args = new ArrayList<Value> ();
		args.add(0, turtleID);
		args.add(Angle);
		Shapes.Rotate(args);	
	}
	// get sleepTime
	private static long getSleepTime()
	{
		int speed = (int)((DoubleV)Speed).getValue();
		
		//speed 10 ~ 6
		if(speed > 5)
			return (10 - speed) + 1;
		//speed 5 ~ 2
		else if(speed > 1)
			return 10 * (int)java.lang.Math.pow(2, 5 - speed);
		//speed 1
		else
			return 210;
	}
	public static void notifyFieldAssign(String fieldName) 
	{
		// property Speed
		if(fieldName.equals("Speed"))
		{
			int input = (int)(((DoubleV)Speed).getValue());
			
			if(input > 10)
				input = 10;
			else if(input < 1)
				input = 1;
			((DoubleV)Speed).setValue(input);
		}
		// property Angle
		else if(fieldName.equals("Angle"))
		{
			if(isCalled)
				rotateTurtleImg();
		}
		//property X
		else if(fieldName.equals("X"))
		{
			if(isCalled && isTurtleShow)
				repaintTurtle();
		}
		//property Y
		else if(fieldName.equals("Y"))
		{
			if(isCalled && isTurtleShow)
				repaintTurtle();
		}
	}

	public static void notifyFieldRead(String fieldName) {
	}	
}
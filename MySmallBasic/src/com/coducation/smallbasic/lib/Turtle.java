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
		
		delayTime = 4;
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
	
	// related with speed
	private static int delayTime;
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
	}	//Turtle.Turn(angle) - if angle is positive, turn to right
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
			{
				repaintTurtle(turtleX, turtleY);
			}	
			
			//set cur x, y
			((DoubleV)X).setValue(curX);
			((DoubleV)Y).setValue(curY);
			
			// turning speed
			try 
			{
				double speed = ((DoubleV)Speed).getValue();
				Thread.sleep((long)(delayTime + ((10 - speed))));
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
					
			//turtle repaint....
			rotateTurtleImg();
				
			// turning speed
			try 
			{
				Thread.sleep( (long)(delayTime - 3 + (11 - ((DoubleV)Speed).getValue()) ) );
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
	// if initial call, setting about turtle - 거북이미지로 바꿀것
	private static void initialCallCheck()
	{
		if(!isCalled)
		{
			isCalled = true;
			
			//laod turtle image
			
			//imageList와 Shapes 완료되었을 때, 할 부분
			//이미지리스트로 이미지 가져오기.
			//ImageList.LoadImage(경로)
			//Shapes.AddImage( 이미지)
			// 이미지 좌표 조절 : 좌측상단 -> 중앙
			//ImageList.GetWidthOfImage(..)
			
			//임시
			ArrayList<Value> args = new ArrayList<Value> ();
			args.add(0, new DoubleV(10));
			args.add(1, new DoubleV(20));
			turtleID = (StrV) Shapes.AddEllipse(args);
			turtleWidthDivTwo = 5;
			turtleHeightDivTwo = 10;
			
			repaintTurtle();		
		}
	}
	// repaint Turtle image in (X, Y)
	private static void repaintTurtle()
	{
		ArrayList<Value> args = new ArrayList<Value> ();
		args.add(0, turtleID);
		args.add(1, new DoubleV(((DoubleV)X).getValue() - turtleWidthDivTwo));
		args.add(2, new DoubleV(((DoubleV)Y).getValue() - turtleHeightDivTwo));
		Shapes.Move(args);	
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
	// rotate turtle - 다른라이브러리구현 완료되면 구현할 것
	private static void rotateTurtleImg()
	{
		//X, Y방향으로 회전
		//그래픽윈도우 회전과 이미지리스트가 완료될때 구현하기
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
			// Angle방향을 바라보도록 설정
			// ImageList와 GraphicsWindow 완료되면 구현
		}

	}

	public static void notifyFieldRead(String fieldName) {

	}	
}
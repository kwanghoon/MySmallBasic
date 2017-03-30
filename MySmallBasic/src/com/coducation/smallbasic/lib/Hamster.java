package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;

import org.roboid.runtime.Runner;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Hamster 
{	
	//Rotate(speed) - 시계방향으로 speed(-100 ~ 100 [%], 0: 정지)로 회전
		public static void Rotate(ArrayList<Value> args)
		{
			if (args.size() == 1) 	
				MultiRotate(changeToMultiArgs(args));
			else 		
				throw new InterpretException("Error in # of Arguments: " + args.size());
		}
		//MultiRotate(hamsterID, speed)
		public static void MultiRotate(ArrayList<Value> args)
		{
			if (args.size() == 2) 
			{
				initialCallCheck();
				
				int hamsterID;
				double speed;
				
				// args 타입체크
				try 
				{
					// 첫번째 인자
					String arg = args.get(0).toString();
					hamsterID = (int)Double.parseDouble(arg);
					// 두번재 인자
					arg = args.get(1).toString();
					speed = Double.parseDouble(arg);
				}
				catch (NumberFormatException e) 
				{      
					throw new InterpretException("Unexpected type " + args.get(0));
				}
				
				// 연결된 햄스터인지 확인
				if(hamsterID >= connectedHamsterNum || hamsterID < 0)
					return;	// 연결 안 된ID_ do Nothing
						
				hamster.get(hamsterID).wheels(speed, -speed);			
			}
			else 		
				throw new InterpretException("Error in # of Arguments: " + args.size());		
		}
		//IsObstacleInDistance(view) - return StrV("True" of "False")
		// view(0 ~ 255, 초기 값: 0) 이내에 장애물이 있는지 확인한다._ 255이면 시력이 좋다.
		public static StrV IsObstacleInDistance(ArrayList<Value> args)
		{
			if(args.size() == 1)
			{
				return MultiIsObstacleInDistance(changeToMultiArgs(args));
			}
			else 		
				throw new InterpretException("Error in # of Arguments: " + args.size());
		}
		// IsObstacleInDistance(hamsterID, view)- return StrV("True" of "False")	
		public static StrV MultiIsObstacleInDistance(ArrayList<Value> args)
		{
			if (args.size() == 2) 
			{
				initialCallCheck();
				
				int hamsterID;
				double distance;
				
				// args 타입체크
				try 
				{
					// 첫번째 인자
					String arg = args.get(0).toString();
					hamsterID = (int)Double.parseDouble(arg);

					// 두번재 인자
					arg = args.get(1).toString();
					distance = Double.parseDouble(arg);
				}
				catch (NumberFormatException e) 
				{      
					throw new InterpretException("Unexpected type " + args.get(0));
				}
				
				// 연결된 햄스터인지 확인
				if(hamsterID >= connectedHamsterNum || hamsterID < 0)
					return null;	// 연결 안 된ID_ do Nothing
						
				if(hamster.get(hamsterID).leftProximity() >= 255 - distance
						|| hamster.get(hamsterID).rightProximity() >= 255 - distance)
					return new StrV("True");
				else 
					return new StrV("False");
			}
			else 		
				throw new InterpretException("Error in # of Arguments: " + args.size());		
		}

	//=================operations=====================================
	
	public static void AddHamster(ArrayList<Value> args)
	{
		//AddHamster()
		if (args.size() == 0) 
		{
			hamster.add(new org.roboid.hamster.Hamster());
			connectedHamsterNum++;
		}
		//AddHamster(portName)
		else if (args.size() == 1)
		{			String portName = args.get(0).toString();
			hamster.add(new org.roboid.hamster.Hamster(portName));
			connectedHamsterNum++;
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	
		Runner.waitUntilReady();
	}

	//AddHamsters(hamsterNum)
	public static void AddHamsters(ArrayList<Value> args)
	{
		if (args.size() == 1) 
		{
			int hamsterNum;
			
			//숫자인지 체크
			try 
			{
				String arg = args.get(0).toString();
				hamsterNum = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터 추가
			for(int i = 0; i < hamsterNum; i++)
			{
				hamster.add(new org.roboid.hamster.Hamster());
				connectedHamsterNum++;
			}
			
			Runner.waitUntilReady();
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}	

	public static void Wheel(ArrayList<Value> args)
	{
		//Wheel(speed)
		if (args.size() == 1) 	
			MultiWheel(changeToMultiArgs(args));
		//Wheel(leftSpeed, rightSpeed)
		else if(args.size() == 2)
			MultiWheel(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	public static void MultiWheel(ArrayList<Value> args)
	{
		//MultiWheel(hamsterID, speed)
		if (args.size() == 2) 
		{
			initialCallCheck();
			
			int hamsterID;
			double speed;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번재 인자
				arg = args.get(1).toString();
				speed = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 연결된 햄스터인지 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 바퀴속도 변경
			hamster.get(hamsterID).wheels(speed);			
		}
		//MultiWheel(hamsterID, leftSpeed, rightSpeed)
		else if(args.size() == 3)
		{
			initialCallCheck();
			
			int hamsterID;
			double leftSpeed, rightSpeed;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번재 인자
				arg = args.get(1).toString();
				leftSpeed = Double.parseDouble(arg);
				//세번째 인자
				arg = args.get(2).toString();
				rightSpeed = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 연결된 햄스터인지 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 바퀴속도 변경
			hamster.get(hamsterID).wheels(leftSpeed, rightSpeed);		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//LeftWheel(speed)
	public static void LeftWheel(ArrayList<Value> args)
	{
		if (args.size() == 1) 
			MultiLeftWheel(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//MultiLeftWheel(hamsterID, speed)
	public static void MultiLeftWheel(ArrayList<Value> args)
	{
		if (args.size() == 2) 
		{
			initialCallCheck();
			
			int hamsterID;
			double speed;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번재 인자
				arg = args.get(1).toString();
				speed = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 연결된 햄스터인지 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 바퀴속도 변경
			hamster.get(hamsterID).leftWheel(speed);			
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}
	//RighttWheel(speed)
	public static void RightWheel(ArrayList<Value> args)
	{
		if (args.size() == 1) 
			MultiRightWheel(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//MultiRightWheel(hamsterID, speed)
	public static void MultiRightWheel(ArrayList<Value> args)
	{
		if (args.size() == 2) 
		{
			initialCallCheck();
			
			int hamsterID;
			double speed;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번재 인자
				arg = args.get(1).toString();
				speed = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 연결된 햄스터인지 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 바퀴속도 변경
			hamster.get(hamsterID).rightWheel(speed);			
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}
	//Stop()
	public static void Stop(ArrayList<Value> args)
	{
		if (args.size() == 0) 
			MultiStop(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//MultiStop(hamsterID)
	public static void MultiStop(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();
			
			int hamsterID;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 바퀴속도 멈추기
			hamster.get(hamsterID).stop();		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//LineTracerMode(mode)
	public static void LineTracerMode(ArrayList<Value> args)
	{
		if (args.size() == 1) 
			MultiLineTracerMode(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//LineTracerMode(hamsterID, mode)
	public static void MultiLineTracerMode(ArrayList<Value> args)
	{
		if (args.size() == 2)
		{
			initialCallCheck();
			
			int hamsterID;
			int mode;
			
			// args 타입체크
			//첫번재 인자
			try 
			{
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			//두번째 인자
			try 
			{
				String arg = args.get(1).toString();
				mode = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{  
				try
				{
					//두번째 인자가 string일 경우
					mode = lineTracerModeMap.get(args.get(1).toString().toLowerCase()).intValue();
				}
				catch (NullPointerException exception)
				{
					throw new InterpretException("Invalid LineTracerMode String " + args.get(1));
				}
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 라인트레이서모드 변경
			hamster.get(hamsterID).lineTracerMode(mode);		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}		
	//LineTracerSpeed(speed)
	public static void LineTracerSpeed(ArrayList<Value> args)
	{
		if (args.size() == 1) 
			MultiLineTracerSpeed(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//MultiLineTracerSpeed(hamsterID, speed)
	public static void MultiLineTracerSpeed(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();
			
			int hamsterID;
			double speed;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번째 인자
				arg = args.get(1).toString();
				speed = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 트레이스모드 스피드 변경
			hamster.get(hamsterID).lineTracerSpeed(speed);		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//BoardForward()
	public static void BoardForward(ArrayList<Value> args)
	{
		if (args.size() == 0) 
			MultiBoardForward(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}
	//MultiBoardForward(hamsterID)
	public static void MultiBoardForward(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();
			
			int hamsterID;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			hamster.get(hamsterID).boardForward();		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}
	//BoardLeft()
	public static void BoardLeft(ArrayList<Value> args)
	{
		if (args.size() == 0) 
			MultiBoardLeft(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}
	//MultiBoardLeft(hamsterID)
	public static void MultiBoardLeft(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();
			
			int hamsterID;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			hamster.get(hamsterID).boardLeft();		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//BoardRight()
	public static void BoardRight(ArrayList<Value> args)
	{
		if (args.size() == 0) 
			MultiBoardRight(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}
	//MultiBoardRight(hamsterID)
	public static void MultiBoardRight(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();
			
			int hamsterID;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			hamster.get(hamsterID).boardRight();		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}	
	//LEDs(color)
	//LEDs(leftColor, rightColor)
	public static void LEDs(ArrayList<Value> args)
	{
		if (args.size() == 1 || args.size() == 2) 
			MultiLEDs(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}
	//MultiLEDs (hamsterID, color)
	//MultiLEDs (hamsterID, leftColor, rightColor)
	public static void MultiLEDs(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();
			
			int hamsterID;
			int color;
			
			// args 타입체크
			// 첫번째 인자
			try 
			{
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			//두번째 인자
			try 
			{
				String arg = args.get(1).toString();
				color = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
			
				//두번째 인자가 string일 경우
				try
				{
					color = LEDcolorMap.get(args.get(1).toString().toLowerCase()).intValue();
				}
				catch (NullPointerException exception)
				{
					throw new InterpretException("Invalid color String " + args.get(1));
				}
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			hamster.get(hamsterID).leds(color);	
		}
		else if(args.size() == 3)
		{
			initialCallCheck();
			
			int hamsterID;
			int color[] = new int[2];
			
			// args 타입체크
			// 첫번째 인자
			try 
			{
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			//두번째, 세번째 인자
			for(int i = 0; i < color.length; i++)
			{
				String arg = args.get(i + 1).toString();
				
				try 
				{
					color[i] = (int)Double.parseDouble(arg);
				}
				catch (NumberFormatException e) 
				{      
					//인자가 string일 경우
					try
					{
						color[i] = LEDcolorMap.get(arg).intValue();
					}
					catch (NullPointerException exception)
					{
						throw new InterpretException("Invalid color String " + args.get(1));
					}
				}	
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			hamster.get(hamsterID).leds(color[0], color[1]);	
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	//LeftLED(color)
	public static void LeftLED(ArrayList<Value> args)
	{
		if (args.size() == 1) 
			MultiLeftLED(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}//MultiLeftLED(hamsterID, color)
	public static void MultiLeftLED(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();
			
			int hamsterID;
			int color;
			
			// args 타입체크
			// 첫번째 인자
			try 
			{
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			//두번째 인자
			try 
			{
				String arg = args.get(1).toString();
				color = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				//두번째 인자가 string일 경우
				try
				{
					color = LEDcolorMap.get(args.get(1).toString().toLowerCase()).intValue();
				}
				catch (NullPointerException exception)
				{
					throw new InterpretException("Invalid color String " + args.get(1));
				}
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			hamster.get(hamsterID).leftLed(color);	
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}	
	//RightLED(color)
	public static void RightLED(ArrayList<Value> args)
	{
		if (args.size() == 1) 
			MultiRightLED(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}//MultiLeftLED(hamsterID, color)
	public static void MultiRightLED(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();
			
			int hamsterID;
			int color;
			
			// args 타입체크
			// 첫번째 인자
			try 
			{
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			//두번째 인자
			try 
			{
				String arg = args.get(1).toString();
				color = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				//두번째 인자가 string일 경우
				try
				{
					color = LEDcolorMap.get(args.get(1).toString().toLowerCase()).intValue();
				}
				catch (NullPointerException exception)
				{
					throw new InterpretException("Invalid color String " + args.get(1));
				}
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			hamster.get(hamsterID).rightLed(color);	
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}	
	//Beep()
	public static void Beep(ArrayList<Value> args)
	{
		if (args.size() == 0) 
			MultiBeep(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}
	//MultiBeep(hamsterID)
	public static void MultiBeep(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();
			
			int hamsterID;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			hamster.get(hamsterID).beep();		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}	
	//Buzzer(hz)
	public static void Buzzer(ArrayList<Value> args)
	{
		if (args.size() == 1) 
			MultiBuzzer(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}
	//MultiBuzzer(hamsterID, hz)
	public static void MultiBuzzer(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();
			
			int hamsterID;
			double hz;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번째 인자
				arg = args.get(1).toString();
				hz = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 버저 소리의 음높이 주파수 지정
			hamster.get(hamsterID).buzzer(hz);		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}		
	//Tempo(bpm)
	public static void Tempo(ArrayList<Value> args)
	{
		if (args.size() == 1) 
			MultiTempo(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}
	//MultiTempo(hamsterID, bpm)
	public static void MultiTempo(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();
			
			int hamsterID;
			double bpm;
			
			// args 타입체크
			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번째 인자
				arg = args.get(1).toString();
				bpm = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			
			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing
			
			// 템포 바꾸기
			hamster.get(hamsterID).tempo(bpm);		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}	
	public static void Note(ArrayList<Value> args)
	 {
	  //Note(pitch)
	  if (args.size() == 1) 
	   MultiNote(changeToMultiArgs(args));
	  //Note(pitch,beats)
	  else if(args.size() == 2)
	   MultiNote(changeToMultiArgs(args));
	  else   
	   throw new InterpretException("Error in # of Arguments: " + args.size()); 
	 }
	
	public static void MultiNote(ArrayList<Value> args)
	{
		//Note(hamsterID,pitch)
		if(args.size() == 2)
		{
			initialCallCheck();

			int hamsterID;
			int pitch = 0;
			String arg;
		

			try 
			{
				// 첫번째 인자
				arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			try 
			{
				//두번째 인자
				arg = args.get(1).toString();
				pitch = (int)Double.parseDouble(arg);
			}
			//두번재 인자 -> String일때
			catch (NumberFormatException e) 
			{      
				for(int i = 0; i < noteMap.size() ;i++)
					pitch = noteMap.get(arg.toLowerCase()).intValue();
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	

			hamster.get(hamsterID).note(pitch);	
		}
		//Note(hamsterID,pitch, beats)
		else if (args.size() == 3)
		{
			initialCallCheck();

			int hamsterID;
			int pitch = 0 ;
			double beats;
			String arg;

			try 
			{
				// 첫번째 인자
				arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

				// 세번째 인자
				arg = args.get(2).toString();
				beats = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}
			try 
			{
				//두번째 인자
				arg = args.get(1).toString();
				pitch = (int)Double.parseDouble(arg);
			}
			//두번재 인자 -> String일때
			catch (NumberFormatException e) 
			{      
				for(int i = 0; i < noteMap.size() ;i++)
					pitch = noteMap.get(arg.toLowerCase()).intValue();
				System.out.println(pitch);
			}


			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	// 연결 안 된ID_ do Nothing


			hamster.get(hamsterID).note(pitch,beats);		
		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//IOModeA(mode)
	public static void IOModeA(ArrayList<Value> args)
	{
		if(args.size() == 1)
			MultiIOModeA(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//IOModeA(hamsterID,mode)
	public static void MultiIOModeA(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();

			int hamsterID;
			int mode;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번째 인자
				arg = args.get(1).toString();
				mode = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	

			hamster.get(hamsterID).ioModeA(mode);	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}


	//IOModeB(mode)
	public static void IOModeB(ArrayList<Value> args)
	{
		if(args.size() == 1)
			MultiIOModeB(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//IOModeB(hamsterID,mode)
	public static void MultiIOModeB(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();

			int hamsterID;
			int mode;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번째 인자
				arg = args.get(1).toString();
				mode = (int)Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	

			hamster.get(hamsterID).ioModeB(mode);	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//OutputA(value)
	public static void OutputA(ArrayList<Value> args)
	{
		if(args.size() == 1)
			MultiOutputA(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//OutputA(hamsterID,value)
	public static void MultiOutputA(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();

			int hamsterID;
			double value;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번째 인자
				arg = args.get(1).toString();
				value = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	

			hamster.get(hamsterID).outputA(value);	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//OutputB(value)
	public static void OutputB(ArrayList<Value> args)
	{
		if(args.size() == 1)
			MultiOutputB(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//OutputB(hamsterID,value)
	public static void MultiOutputB(ArrayList<Value> args)
	{
		if(args.size() == 2)
		{
			initialCallCheck();

			int hamsterID;
			double value;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);
				// 두번째 인자
				arg = args.get(1).toString();
				value = Double.parseDouble(arg);
			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return;	

			hamster.get(hamsterID).outputB(value);	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//SignalStrength()
	public static Value SignalStrength(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiSignalStrength(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//SignalStrength(hamsterID)
	public static Value MultiSignalStrength(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).signalStrength());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	

	}

	//LeftProximity()
	public static Value LeftProximity(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiLeftProximity(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//LeftProximity(hamsterID)
	public static Value MultiLeftProximity(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;

			return new DoubleV(hamster.get(hamsterID).rightProximity());		

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	

	}

	//RightProximity()
	public static Value RightProximity(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiRightProximity(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//RightProximity(hamsterID)
	public static Value MultiRightProximity(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).rightProximity());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	

	}

	//LeftFloor()
	public static Value LeftFloor(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiLeftFloor(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//LeftFloor(hamsterID)
	public static Value MultiLeftFloor(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).leftFloor());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	

	}

	//RightFloor()
	public static Value RightFloor(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiRightFloor(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//RightFloor(hamsterID)
	public static Value MultiRightFloor(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).rightFloor());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	

	}

	//AccelerationX()
	public static Value AccelerationX(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiAccelerationX(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//AccelerationX(hamsterID)
	public static Value MultiAccelerationX(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).accelerationX());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	

	}

	//AccelerationY()
	public static Value AccelerationY(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiAccelerationY(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//AccelerationY(hamsterID)
	public static Value MultiAccelerationY(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).accelerationY());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//AccelerationZ()
	public static Value AccelerationZ(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiAccelerationZ(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//AccelerationZ(hamsterID)
	public static Value MultiAccelerationZ(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).accelerationZ());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//Light()
	public static Value Light(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiLight(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//Light(hamsterID)
	public static Value MultiLight(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).light());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//Temperature()
	public static Value Temperature(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiTemperature(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//Temperature(hamsterID)
	public static Value MultiTemperature(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).temperature());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//InputA()
	public static Value InputA(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiInputA(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());

	}

	//InputA(hamsterID)
	public static Value MultiInputA(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).inputA());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//InputB()
	public static Value InputB(ArrayList<Value> args)
	{
		if(args.size() == 0)
			return MultiInputB(changeToMultiArgs(args));
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	//InputB(hamsterID)
	public static Value MultiInputB(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();

			int hamsterID;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				hamsterID = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			// 햄스터연결 확인
			if(hamsterID >= connectedHamsterNum || hamsterID < 0)
				return null;	

			return new DoubleV(hamster.get(hamsterID).inputB());	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());	
	}

	//Wait(milliseconds)
	public static void Wait(ArrayList<Value> args)
	{
		if(args.size() == 1)
		{
			initialCallCheck();


			int milliseconds;

			try 
			{
				// 첫번째 인자
				String arg = args.get(0).toString();
				milliseconds = (int)Double.parseDouble(arg);

			}
			catch (NumberFormatException e) 
			{      
				throw new InterpretException("Unexpected type " + args.get(0));
			}

			Runner.wait(milliseconds);	

		}
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//싱글모드 파라미터를 멀티모드 파라미터로 바꾼값을 리턴하는 메소드
	private static ArrayList<Value> changeToMultiArgs(ArrayList<Value> args)
	{
		ArrayList<Value> multiArgs = new ArrayList<Value> ();
		multiArgs.add(0, new DoubleV(0));
		
		for(int i = 1; i < args.size()+1; i++)
		{
			multiArgs.add(i, args.get(i-1));
		}
		
		return multiArgs;		
	}

	//이 클래스가 최초로 호출되었는지 확인_햄스터 메소드 앞에 반드시 넣을 것
	private static void initialCallCheck()
	{
		if(connectedHamsterNum == 0)
		{
			hamster.add(new org.roboid.hamster.Hamster());
			connectedHamsterNum++;
				
			Runner.waitUntilReady();
		}
	}
	
	// 햄스터 리스트
	private static ArrayList<org.roboid.hamster.Hamster> hamster;
	// 연결된 햄스터 수
	private static int connectedHamsterNum;
	
	// String상수 맵
	private static HashMap<String, Integer> lineTracerModeMap;
	private static HashMap<String, Integer> LEDcolorMap;
	private static HashMap<String, Integer> noteMap;
	private static HashMap<String, Integer> ioModeMap;
	
	//상수 정보
	private static String[] lineTracerModeInfo = 
		{"Off", "BlackLeftSensor", "BlackRightSensor", "BlackBothSensors", 
				"BlackTurnLeft", "BlackTurnRight", "BlackMoveForward", "BlackUTurn",
				"WhiteLeftSensor", "WhiteRightSensor", "WhiteBothSensors", "WhiteTurnLeft",
				"WhiteTurnRight", "WhiteMoveForward", "WhiteUTurn"};
	
	private static String[] LEDcolorInfo = 
		{"Off", "Blue", "Green", "Cyan", "Red",
				"Magenta", "Yellow", "White"};
	private static String[] noteInfo =
		{"off", "A_0", "B_0", "C_1","D_1", "E_1", "F_1", "G_1", "A_1", "B_1",
				"C_2","D_2", "E_2", "F_2","G_2", "A_2", "B_2", "C_3","D_3","E_3",
				"F_3","G_3","A_3","B_3","C_4","D_4","E_4","F_4","G_4","A_4","B_4",
				"C_5","D_5","E_5","F_5","G_5","A_5","B_5","C_6","D_6","E_6","F_6",
				"G_6","A_6","B_6","C_7","D_7","E_7","F_7","G_7","A_7","B_7","C_8",
				"A_SHARP_0","B_FLAT_0","C_SHARP_1","D_FLAT_1","D_SHARP_1","E_FLAT_1",
				"F_SHARP_1","G_FLAT_1","G_SHARP_1","A_FLAT_1","A_SHARP_1","B_FLAT_1",
				"C_SHARP_2","D_FLAT_2","D_SHARP_2","E_FLAT_2","F_SHARP_2","G_FLAT_2",
				"G_SHARP_2","A_FLAT_2","A_SHARP_2","B_FLAT_2","C_SHARP_3","D_FLAT_3",
				"D_SHARP_3","E_FLAT_3","F_SHARP_3","G_FLAT_3","G_SHARP_3","A_FLAT_3",
				"A_SHARP_3","B_FLAT_3","C_SHARP_4","D_FLAT_4","D_SHARP_4","E_FLAT_4",
				"F_SHARP_4","G_FLAT_4","G_SHARP_4","A_FLAT_4","A_SHARP_4","B_FLAT_4",
				"C_SHARP_5","D_FLAT_5","D_SHARP_5","E_FLAT_5","F_SHARP_5","G_FLAT_5",
				"G_SHARP_5","A_FLAT_5","A_SHARP_5","B_FLAT_5","C_SHARP_6","D_FLAT_6",
				"D_SHARP_6","E_FLAT_6","F_SHARP_6","G_FLAT_6","G_SHARP_6","A_FLAT_6",
				"A_SHARP_6","B_FLAT_6","C_SHARP_7","D_FLAT_7","D_SHARP_7","E_FLAT_7",
				"F_SHARP_7","G_FLAT_7","G_SHARP_7","A_FLAT_7","A_SHARP_7","B_FLAT_7"	
		};

	private static int[] num =
		{0,1,3,4,6,8,9,11,13,15,16,18,20,21,23,25,27,28,30,32,33,35,37,39,40,42,
				44,45,47,49,51,52,54,56,57,59,61,63,64,66,68,69,71,73,75,76,78,
				80,81,83,85,87,88,2,2,5,5,7,7,10,10,12,12,14,14,17,17,19,19,22,22,
				24,24,26,26,29,29,31,31,34,34,36,36,38,38,41,41,43,43,46,46,48,48,
				50,50,53,53,55,55,58,58,60,60,62,62,65,65,67,67,70,70,72,72,74,74,
				77,77,79,79,82,82,84,84,86,86};

	private static String[] ioModeInfo = 
		{
				"ANALOG_INPUT","DIGITAL_INPUT","DIGITAL_OUTPUT","PWM_OUTPUT","SERVO_OUTPUT"
		};
	
	private static int[] ioModenum = {0,1,10,9,8};
	
	static
	{
		hamster = new ArrayList<org.roboid.hamster.Hamster>();
		connectedHamsterNum = 0;
		
		lineTracerModeMap = new HashMap<String, Integer>();
		for(int i = 0; i < lineTracerModeInfo.length ; i++)
			lineTracerModeMap.put(lineTracerModeInfo[i].toLowerCase(), i);
	
		LEDcolorMap = new HashMap<String, Integer>();
		for(int i = 0; i < LEDcolorInfo.length ; i++)
			LEDcolorMap.put(LEDcolorInfo[i].toLowerCase(), i);
		
		noteMap = new HashMap<String,Integer>();
		for(int i = 0; i<noteInfo.length;i++)
			noteMap.put(noteInfo[i].toLowerCase(), num[i]);
		
		ioModeMap = new HashMap<String, Integer>();
		for(int i = 0; i<ioModeInfo.length;i++)
			ioModeMap.put(ioModeInfo[i].toLowerCase(), ioModenum[i]);
	}
}


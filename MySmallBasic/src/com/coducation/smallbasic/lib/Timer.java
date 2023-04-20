package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.Object;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.Eval;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Timer {
	//isPause는 Resume 메소드가 Pasue되어 있을 때만 작동하게 하기 위해 Pause중인지를 판단하는 멤버
	private static javax.swing.Timer timer;
	private static final int defaultInterval=100000000;
	private static boolean isPause;

	static {
		// Timer.Tick을 초기화하는 문장을 적지 않으면 콘솔창 뒤의 배경만 지속됨.
		/*
		   Timer.Interval은 default값 100000000으로 콘솔에 출력되나
		   Timer.Tick같은 Events는 WriteLine불가함 WriteLine에서 이런 경우를 
		    다뤄야 할 것 같음.
		 */    
		/* 
		   그리고 처음 시작할 때도 Interval만큼 기다렸다가 Tick을 실행함. 이는 Java의
		   Timer와 동일함.
		   Interval의 default값인 100000000이상은 사용 할 수 없는 값임, 만약
		   그런 값으로 Interval을 할당하거나 혹은 Interval값을 초기화를 해주지 않으면 Timer가
		   생성되지 않음.
		   Timer는 Interval값과 Tick만 할당 해주면 실행이 되기 때문에
		   notifyFieldAssign에서 Interval값이 default값보다 작고 && Tick이 할당
		    되었는지를 체크하고 Timer를 생성해야 할 것 같음.
		    Documentation Reference에는 Interval이 10~100000000값만 된다고 하는데
		    실제 Sb에서는 10이하의 실수들도 10과 비슷한 속도로 사용가능한 것 같음.
		 */ 
		Interval = new DoubleV(defaultInterval);
		Tick = null;
		timer = null;
		isPause=false;
	}


	public static Value Interval;
	public static Value Tick;


	public static void Pause(ArrayList<Value> args) {
		// Timer가 null이면 Pause는 아무 일도 하지 않음. 
		if(timer != null){
			if(args.size() == 0) {
				timer.stop();
				isPause=true;
			}
			else {
				throw new InterpretException("Invalid number of args: " + args.size());
			}
		}
	}

	public static void Resume(ArrayList<Value> args) {
		//Pause가 되어 있을 때만 Timer를 재시작 하고 Pause가 안되어 있거나 timer가 null이면 아무일도 하지 않음.
		//timer가 실행되고 있을 때만 Resume실행
		if(timer != null && isPause == true){
			if(args.size()==0) {
				timer.restart();
			}
			else {
				throw new InterpretException("Invalid number of args: " + args.size());
			}
		}
	}

	private static int getInterval() {
		if(Interval instanceof DoubleV) {
			return (int) ((DoubleV) Interval).getValue();
		}
		else if(Interval instanceof StrV && ((StrV) Interval).isNumber()) {
			return (int) ((StrV) Interval).parseDouble();
		}
		else {
			throw new InterpretException("Invalid Interval: " + Interval);
		}
	}

	public static void notifyFieldAssign(String fieldName) {
		//Tick이 설정이 안된 경우는 TextWindow에서 해결, Tick이 설정이 안되면 Timer가 생성되면 안된다.

		if(fieldName.equalsIgnoreCase("Interval")) {
			if(timer == null) {
				if(((DoubleV) Interval).getValue() < defaultInterval) {
					timer = new javax.swing.Timer(getInterval(), new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(Tick != null) {
								Eval.eval(Tick);
							}
						}});
					//중간에 Interval을 수정하는 경우를 setDelay로 해결함
					timer.start();
				}
			}
			else {
				timer.setDelay(getInterval());
			}
		}
	}

	public static void notifyFieldRead(String fieldName) {

	}
}

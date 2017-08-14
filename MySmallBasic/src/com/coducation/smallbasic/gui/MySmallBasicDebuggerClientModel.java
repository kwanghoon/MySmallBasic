package com.coducation.smallbasic.gui;

import java.util.ArrayList;
import java.util.HashMap;

import com.coducation.smallbasic.Value;

public interface MySmallBasicDebuggerClientModel 
{
	//디버그모드로 실행
	public abstract void debugModeRun();
	//디버깅되는 프로그램의 정상 종료
	public abstract void normalReturn();
	//디버깅되는 프로그램의 비정상 종료
	public abstract void abnormalReturn();
	//디버그 멈춘 상태에 호출되는 메소드
	public abstract void stopState(int stopLine, HashMap<String, String> variableMap);
}

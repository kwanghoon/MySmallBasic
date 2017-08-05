package com.coducation.smallbasic.gui;

public interface MySmallBasicDebuggerClientModel 
{
	//디버그모드로 실행
	public abstract void debugModeRun();
	//디버깅되는 프로그램의 정상 종료
	public abstract void normalReturn();
	//디버깅되는 프로그램의 비정상 종료
	public abstract void abnormalReturn();
}

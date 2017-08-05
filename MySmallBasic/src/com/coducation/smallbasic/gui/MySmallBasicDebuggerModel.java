package com.coducation.smallbasic.gui;

import java.util.Set;

public abstract class MySmallBasicDebuggerModel {
	protected MySmallBasicDebuggerClientModel debuggerClient;
	protected Set<Integer> breakPoints;

	public MySmallBasicDebuggerModel(MySmallBasicDebuggerClientModel debuggerClient, String filePath,Set<Integer> breakPoints) {
		this.debuggerClient = debuggerClient;
		this.breakPoints = breakPoints;
	}

	// 디버거프로그램 시작
	public abstract void run();
	// 한줄 진행
	public abstract void step();
	// 다음 breakpoint까지 진행
	public abstract void continueDebugging();
	//디버거 종료
	public abstract void stop();
	public Set<Integer> getBreakPoints() {
		return breakPoints;
	}
}

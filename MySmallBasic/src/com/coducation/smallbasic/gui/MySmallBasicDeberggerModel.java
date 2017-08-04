package com.coducation.smallbasic.gui;

import java.util.Set;

public abstract class MySmallBasicDeberggerModel {
	MySmallBasicDeberggerClientModel deberggerClient;
	Set<Integer> breakPoints;

	public MySmallBasicDeberggerModel(MySmallBasicDeberggerClientModel deberggerClient, Set<Integer> breakPoints) {
		this.deberggerClient = deberggerClient;
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

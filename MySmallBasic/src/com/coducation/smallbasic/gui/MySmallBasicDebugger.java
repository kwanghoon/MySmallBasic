package com.coducation.smallbasic.gui;

import java.util.Set;

import org.jdiscript.JDIScript;
import org.jdiscript.util.VMLauncher;

public class MySmallBasicDebugger extends MySmallBasicDebuggerModel implements Runnable
{
	private JDIScript jdiScript;
	
	private String HOME = "./";
	
	public MySmallBasicDebugger(MySmallBasicDebuggerClientModel debuggerClient, String filePath, Set<Integer> breakPoints)
	{
		super(debuggerClient, filePath, breakPoints);
		
		String main = "com.coducation.smallbasic.MySmallBasicMain -gui " + filePath;
		StringBuilder options = new StringBuilder("-cp ");
		options.append(HOME + "bin");
		MySmallBasicGUI.addJarFile(options, HOME, HOME + "//lib");

		jdiScript = new JDIScript(new VMLauncher(options.toString(), main).start());
		jdiScript.threadDeathRequest();
	}

	// 디버거프로그램 시작
	public void run()
	{
		jdiScript.run();
	}
	// 한줄 진행
	public void step()
	{
		
	}
	// 다음 breakpoint까지 진행
	public void continueDebugging()
	{
		
	}
	//디버거 종료
	public void stop()
	{
		jdiScript.vm().exit(0);
		debuggerClient.normalReturn();
	}
}

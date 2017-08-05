package com.coducation.smallbasic.gui;

import java.util.Set;

import org.jdiscript.JDIScript;
import org.jdiscript.util.VMLauncher;

import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.Value;

public class MySmallBasicDebugger extends MySmallBasicDebuggerModel implements Runnable {
	private JDIScript jdiScript;
	private int previousLineNum = Integer.MAX_VALUE;
	private boolean isStepState = false;

	private String HOME = "./";

	public MySmallBasicDebugger(MySmallBasicDebuggerClientModel debuggerClient, String filePath,
			Set<Integer> breakPoints) {
		super(debuggerClient, filePath, breakPoints);

		String main = "com.coducation.smallbasic.MySmallBasicMain -gui " + filePath;
		StringBuilder options = new StringBuilder("-cp ");
		options.append(HOME + "bin");
		MySmallBasicGUI.addJarFile(options, HOME, HOME + "//lib");

		jdiScript = new JDIScript(new VMLauncher(options.toString(), main).start());

		// breakpoint info
		String breakPointClass = "com.coducation.smallbasic.Eval";
		String breakPointMethod = "eval";

		jdiScript.onMethodInvocation(breakPointClass, breakPointMethod,
				"(Lcom/coducation/smallbasic/BasicBlockEnv;Lcom/coducation/smallbasic/Env;Lcom/coducation/smallbasic/Stmt;)V",
				methodEvent -> {

					// 스몰 베이직 프로그램의 lineNumber 구하기
					int lineNum = Integer.MAX_VALUE;

					try {
						Field lineno = jdiScript.vm().classesByName("com.coducation.smallbasic.ExprStmt").get(0)
								.fieldByName("lineno");
						Value exprStmt = ((ObjectReference) methodEvent.thread().frame(0).getArgumentValues().get(2))
								.getValue(lineno); // eval 메소드의 3번째 파라미터 가져오기
						lineNum = Integer.parseInt(exprStmt.toString());

					} catch (IncompatibleThreadStateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// stop 할 위치 검사
					if (previousLineNum != lineNum && lineNum != 0 && (isStepState || breakPoints.contains(lineNum))) {
						debuggerClient.stopState(lineNum);
					}
					previousLineNum = lineNum;
				});
	}

	// 디버거프로그램 시작
	public void run() {
		jdiScript.run();
	}

	// 한줄 진행
	public void step() {
		isStepState = true;
	}

	// 다음 breakpoint까지 진행
	public void continueDebugging() {
		isStepState = false;
	}

	// 디버거 종료
	public void exit() {
		jdiScript.vm().exit(0);
		debuggerClient.normalReturn();
	}
}

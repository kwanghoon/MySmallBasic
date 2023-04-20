package com.coducation.smallbasic.gui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import org.jdiscript.JDIScript;
import org.jdiscript.handlers.OnVMDisconnect;
import org.jdiscript.util.VMSocketAttacher;

import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.DoubleValue;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.VMDisconnectedException;
import com.sun.jdi.Value;
import com.sun.jdi.event.BreakpointEvent;

public class MySmallBasicDebugger extends MySmallBasicDebuggerModel implements Runnable {
	private JDIScript jdiScript;
	private int previousLineNum = Integer.MAX_VALUE;
	private boolean isStepState = false;

	// 실행관련 변수
	private static String shellCmd, javaCmd, HOME, cwd, classPathDelimeter;
	private static List<String> cmds;
	private static StringBuilder classpath = new StringBuilder();

	public MySmallBasicDebugger(String filePath, boolean isDebugMode, MySmallBasicDebuggerClientModel debuggerClient,
			Set<Integer> breakPoints) {
		super(debuggerClient, filePath, breakPoints);

		// 프로세스 준비
		init(isDebugMode, filePath);
		
		ProcessBuilder pb;
		pb = new ProcessBuilder(cmds);

//		if (isDebugMode) {
//			pb = new ProcessBuilder(shellCmd, "/c", "start java.exe",
//					"-agentlib:jdwp=transport=dt_socket,address=localhost:7070,server=y,suspend=y", javaCmd, "-gui",
//					filePath);
//		} else {
//			pb = new ProcessBuilder(cmds);
//		}

//		for (String c : pb.command())
//			System.out.println(c);

		Map<String, String> env = pb.environment();
		classpath.append(HOME + "/bin");
		addJarFile(classpath, HOME, HOME + "/lib");
		env.put("CLASSPATH", classpath.toString());

//		for (Map.Entry<String, String> entry : env.entrySet())
//			System.out.println(entry.getKey() + " : " + entry.getValue());

		pb.directory(new File(HOME));
		
		Process p;
		
		try {
			p = pb.start();
			
//			printStream(p);
//			p.waitFor();
//		  
//			InputStream is = p.getErrorStream(); Scanner scan = new Scanner(is);
//			while (scan.hasNext()) { System.out.print("ERROR: ");
//			System.out.println(scan.nextLine()); }
//			System.out.println("Exit value: " + p.exitValue());
		} catch (IOException e2) {
			e2.printStackTrace();
		} 
//			catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		// 원래 실행하는 부분에 있었던 코드...	 

		if (!isDebugMode)
			return;

		// ==============디버그모드=================================

		jdiScript = new JDIScript(new VMSocketAttacher("localhost", 7070, 10000).attach());

		String breakPointClass = "com.coducation.smallbasic.Eval";
		String breakPointMethod = "eval";

		// 정상종료할때
		jdiScript.vmDeathRequest(handler -> {
			debuggerClient.normalReturn();
		});

		// jdiScript의 filler사용?

		// breakPoint에서 멈추고 정보 출력
		jdiScript.onMethodInvocation(breakPointClass, breakPointMethod,
				"(Lcom/coducation/smallbasic/BasicBlockEnv;Lcom/coducation/smallbasic/Env;Lcom/coducation/smallbasic/Stmt;)V",
				methodEvent -> {
					// 스몰 베이직 프로그램의 lineNumber 구하기
					int lineNum = Integer.MAX_VALUE;

					try {
						Field linenoField = jdiScript.vm().classesByName("com.coducation.smallbasic.Stmt").get(0)
								.fieldByName("lineno");
						Value lineno = ((ObjectReference) methodEvent.thread().frame(0).getArgumentValues().get(2))
								.getValue(linenoField); // eval 메소드의 3번째 파라미터
														// 가져오기
						lineNum = Integer.parseInt(lineno.toString());

					} catch (IncompatibleThreadStateException e1) {
						//e1.printStackTrace();
					}

					// stop 할 위치 검사
					if (previousLineNum != lineNum && lineNum != 0 && (isStepState || breakPoints.contains(lineNum))) {

						LinkedHashMap<String, String> variableMap = new LinkedHashMap<>();

						// 변수정보 가져오기
						try {

							// Env.map (HashMap 타입) 참조 구하기
							Field mapField = jdiScript.vm().classesByName("com.coducation.smallbasic.Env").get(0)
									.fieldByName("map");
							Value map = ((ObjectReference) methodEvent.thread().frame(0).getArgumentValues().get(1))
									.getValue(mapField);

							// MySmallBasic 변수값 가져오기
							try {
								// target_vm에서 사용할 메소드
								Method keySetMethod = jdiScript.vm().classesByName("java.util.HashMap").get(0)
										.methodsByName("keySet").get(0);
								Method iteratorMethod = jdiScript.vm().classesByName("java.util.Set").get(0)
										.methodsByName("iterator").get(0);
								Method hasNextMethod = jdiScript.vm().classesByName("java.util.Iterator").get(0)
										.methodsByName("hasNext").get(0);
								Method nextMethod = jdiScript.vm().classesByName("java.util.Iterator").get(0)
										.methodsByName("next").get(0);
								Method getMethod = jdiScript.vm().classesByName("java.util.HashMap").get(0)
										.methodsByName("get").get(0);

								List<Value> parameter = new LinkedList<Value>(); // 메소드의
																					// 파라미터

								// Env의 map을 가져와서 iterator로 접근
								Value keySet = ((ObjectReference) map).invokeMethod(methodEvent.thread(), keySetMethod,
										parameter, ObjectReference.INVOKE_SINGLE_THREADED);
								Value iterator = ((ObjectReference) keySet).invokeMethod(methodEvent.thread(),
										iteratorMethod, parameter, ObjectReference.INVOKE_SINGLE_THREADED);
								Value hasNext = ((ObjectReference) iterator).invokeMethod(methodEvent.thread(),
										hasNextMethod, parameter, ObjectReference.INVOKE_SINGLE_THREADED);

								// map의 모든 요소에 접근
								while (Boolean.parseBoolean(hasNext.toString())) {

									// key값
									Value keyInstance = ((ObjectReference) iterator).invokeMethod(methodEvent.thread(),
											nextMethod, parameter, ObjectReference.INVOKE_SINGLE_THREADED);
									String key = keyInstance.toString().substring(1,
											keyInstance.toString().length() - 1);

									// Value 객체 구하기
									parameter.add(keyInstance);
									Value valueInstance = ((ObjectReference) map).invokeMethod(methodEvent.thread(),
											getMethod, parameter, ObjectReference.INVOKE_SINGLE_THREADED);

									// Value Inastance 구하기
									int startIdx = 12; // end index of "instance
														// of"
									int endIdx = valueInstance.toString().indexOf("(id");
									String valueClass = valueInstance.toString().substring(startIdx, endIdx); // Value의
																												// 실제객체이름

									// Value별 다른 처리
									if (valueClass.equals("com.coducation.smallbasic.ArrayV")) {
										
										getArrayV_Value(valueInstance, methodEvent, key, variableMap);
									} else if (valueClass.equals("com.coducation.smallbasic.DoubleV")) {
										getDoubleV_Value(valueInstance, methodEvent, key, variableMap);
									} else if (valueClass.equals("com.coducation.smallbasic.StrV")) {
										getStrV_Value(valueInstance, methodEvent, key, variableMap);
									}

									parameter.clear();
									hasNext = ((ObjectReference) iterator).invokeMethod(methodEvent.thread(),
											hasNextMethod, parameter, ObjectReference.INVOKE_SINGLE_THREADED);
								}
							} catch (InvalidTypeException e) {
								e.printStackTrace();
							} catch (ClassNotLoadedException e) {
								e.printStackTrace();
							} catch (InvocationException e) {
								e.printStackTrace();
							}
						} catch (IncompatibleThreadStateException e) {
							e.printStackTrace();
						}

						debuggerClient.stopState(lineNum, variableMap);
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

		// 디버거 멈춰있을 때 콘솔창을 닫아버린 경우 vm이 disconnection되어서 에러발생--- 논의필요.....
		try {
			jdiScript.vm().exit(0);
		} catch (VMDisconnectedException e) {
		}

		debuggerClient.normalReturn();
	}

	// ===================디버거모드에서 스몰베이직의 변수값을 가져오기 위해 필요한 메소드=========================
	// 가져와서 변수를 variableMap에 넣는 역할을 하는 메소드들

	// com.sun.jdi.Value -> com.coducaition.smallbasic.strV.toString
	private void getStrV_Value(Value valueInstance, BreakpointEvent methodEvent, String key,
			LinkedHashMap<String, String> variableMap) throws InvalidTypeException, ClassNotLoadedException,
			IncompatibleThreadStateException, InvocationException {
		Method getValueMethod = jdiScript.vm().classesByName("com.coducation.smallbasic.StrV").get(0)
				.methodsByName("getValue").get(0);

		List<Value> parameter = new LinkedList<Value>();
		parameter.clear();

		Value strValue = ((ObjectReference) valueInstance).invokeMethod(methodEvent.thread(), getValueMethod, parameter,
				ObjectReference.INVOKE_SINGLE_THREADED);

		String value = strValue.toString().substring(1, strValue.toString().length() - 1);
		variableMap.put(key, value);
	}

	// com.sun.jdi.Value -> com.coducaition.smallbasic.DoubleV.toString
	private void getDoubleV_Value(Value valueInstance, BreakpointEvent methodEvent, String key,
			LinkedHashMap<String, String> variableMap) throws InvalidTypeException, ClassNotLoadedException,
			IncompatibleThreadStateException, InvocationException {
		Method getValueMethod = jdiScript.vm().classesByName("com.coducation.smallbasic.DoubleV").get(0)
				.methodsByName("getValue").get(0);
		List<Value> parameter = new LinkedList<Value>();
		parameter.clear();
		DoubleValue doubleValue = (DoubleValue) ((ObjectReference) valueInstance).invokeMethod(methodEvent.thread(),
				getValueMethod, parameter, ObjectReference.INVOKE_SINGLE_THREADED);

		String value = String.valueOf(doubleValue.doubleValue());
		variableMap.put(key, value);
	}

	private void getArrayV_Value(Value valueInstance, BreakpointEvent methodEvent, String key,
			LinkedHashMap<String, String> variableMap) throws InvalidTypeException, ClassNotLoadedException,
			IncompatibleThreadStateException, InvocationException {
		// ArrayV의 arrmap을 가져와서 iterator로 배열에 접근
		Field arrmapField = jdiScript.vm().classesByName("com.coducation.smallbasic.ArrayV").get(0)
				.fieldByName("arrmap");

		Method arrKeySetMethod = jdiScript.vm().classesByName("java.util.LinkedHashMap").get(0).methodsByName("keySet")
				.get(0);
		Method arrIteratorMethod = jdiScript.vm().classesByName("java.util.Set").get(0).methodsByName("iterator")
				.get(0);
		Method arrHasNextMethod = jdiScript.vm().classesByName("java.util.Iterator").get(0).methodsByName("hasNext")
				.get(0);
		Method arrNextMethod = jdiScript.vm().classesByName("java.util.Iterator").get(0).methodsByName("next").get(0);
		Method arrGetMethod = jdiScript.vm().classesByName("java.util.LinkedHashMap").get(0).methodsByName("get")
				.get(0);
		
		//Pair<A,B>에서 값 가져기 위한 Field정의
		Field keyNameField = jdiScript.vm().classesByName("com.coducation.smallbasic.ArrayV$Pair").get(0)
				.fieldByName("a");
		Field valueField = jdiScript.vm().classesByName("com.coducation.smallbasic.ArrayV$Pair").get(0)
				.fieldByName("b");
		

		Value arrmap = ((ObjectReference) valueInstance).getValue(arrmapField);

		List<Value> parameter = new LinkedList<Value>();
		parameter.clear();
		Value arrKeySet = ((ObjectReference) arrmap).invokeMethod(methodEvent.thread(), arrKeySetMethod, parameter,
				ObjectReference.INVOKE_SINGLE_THREADED);
		Value arrIterator = ((ObjectReference) arrKeySet).invokeMethod(methodEvent.thread(), arrIteratorMethod,
				parameter, ObjectReference.INVOKE_SINGLE_THREADED);
		Value arrHasNext = ((ObjectReference) arrIterator).invokeMethod(methodEvent.thread(), arrHasNextMethod,
				parameter, ObjectReference.INVOKE_SINGLE_THREADED);

		// 배열의 원소에 접근
		while (Boolean.parseBoolean(arrHasNext.toString())) {
			// key값
			parameter.clear();
			Value arrKeyInstance = ((ObjectReference) arrIterator).invokeMethod(methodEvent.thread(), arrNextMethod,
					parameter, ObjectReference.INVOKE_SINGLE_THREADED);

			//Pair - Value 구하기
			parameter.add(arrKeyInstance);
			Value arrPairValueInstance = ((ObjectReference) arrmap).invokeMethod(methodEvent.thread(), arrGetMethod,
					parameter, ObjectReference.INVOKE_SINGLE_THREADED);		
			Value arrValueInstance = ((ObjectReference) arrPairValueInstance).getValue(valueField);
			
			//Pair - key이름 구하기
			String arrKey = ((ObjectReference) arrPairValueInstance).getValue(keyNameField).toString().
												substring(1, arrKeyInstance.toString().length() - 1);
			
			//Value의 실제객체 이름 구하기
			int arrEndIdx = arrValueInstance.toString().indexOf("(id");
			String arrValueClass = arrValueInstance.toString().substring(12, arrEndIdx);
																							
			if (arrValueClass.equals("com.coducation.smallbasic.ArrayV")) {
				getArrayV_Value(arrValueInstance, methodEvent, new String(key + '[' + arrKey + ']'), variableMap);
			} else if (arrValueClass.equals("com.coducation.smallbasic.StrV")) {
				getStrV_Value(arrValueInstance, methodEvent, new String(key + '[' + arrKey + ']'), variableMap);
			} else if (arrValueClass.equals("com.coducation.smallbasic.DoubleV")) {
				getDoubleV_Value(arrValueInstance, methodEvent, new String(key + '[' + arrKey + ']'), variableMap);
			}

			parameter.clear();
			arrHasNext = ((ObjectReference) arrIterator).invokeMethod(methodEvent.thread(), arrHasNextMethod, parameter,
					ObjectReference.INVOKE_SINGLE_THREADED);
		}

	}

	// =============================실행을 위해 필요한 메소드=====================================================
	private static void init(boolean isDebugMode, String filePath) {

		String osName = System.getProperty("os.name");
		String osNameMatch = osName.toLowerCase();

		// HOME = "C:/Users/user/git/MySmallBasic/MySmallBasic";
		HOME = "./";
		javaCmd = "com.coducation.smallbasic.MySmallBasicMain";

		shellCmd = "";

		cmds = new ArrayList<String>();
		
		if (osNameMatch.contains("linux")) {
			classPathDelimeter = ":";
			
			if(isDebugMode) {
				cmds.add("gnome-terminal");
				cmds.add("-x");
				cmds.add("java");
				cmds.add("-agentlib:jdwp=transport=dt_socket,address=localhost:7070,server=y,suspend=y");
				cmds.add(javaCmd);
				cmds.add("-gui");		
				cmds.add(filePath);				
			}
			else {
				cmds.add("gnome-terminal");
				cmds.add("-x");
				cmds.add("java");
				cmds.add("-cp");
				cmds.add("./lib/*:./bin");
				cmds.add(javaCmd);
				cmds.add("-gui");		
				cmds.add(filePath);
			}
		} else if (osNameMatch.contains("windows")) {			
			classPathDelimeter = ";";
			if(isDebugMode) {
				cmds.add("cmd.exe");
				cmds.add("/c");
				cmds.add("start java.exe");
				cmds.add("-agentlib:jdwp=transport=dt_socket,address=localhost:7070,server=y,suspend=y");
				cmds.add(javaCmd);
				cmds.add("-gui");
				cmds.add(filePath);
			}
			else {
				cmds.add("cmd.exe");
				cmds.add("/c");
				cmds.add("start java.exe");
				cmds.add(javaCmd);
				cmds.add("-gui");
				cmds.add(filePath);
			}
		} else if (osNameMatch.contains("mac os") || osNameMatch.contains("macos") || osNameMatch.contains("darwin")) {
			shellCmd = "";
			classPathDelimeter = ":";
		} else {
			shellCmd = ""; // Windows OS by default
		}

		cwd = System.getProperty("user.dir");
	}

	private static void addJarFile(StringBuilder classpath, String home, String path) {
		File jar = new File(path);

		if (jar.isDirectory() == true) {
			File[] jars = jar.listFiles();

			for (int i = 0; i < jars.length; i++) {
				if (jars[i].isDirectory() == true) { // 폴더일 경우
					addJarFile(classpath, home, path + "\\" + jars[i].getName()); // 재귀호출
				} else {
					if (jars[i].getName().endsWith(".jar")) {
						classpath.append(classPathDelimeter);
						classpath.append(home + "lib/" + jars[i].getName());
					}
				}
			}
		}
	}
	
//    private void printStream(Process process) throws IOException, InterruptedException {
//    	process.waitFor();
//	    try (InputStream psout = process.getInputStream()) {
//	        copy(psout, System.out);
//	    }
//    }
//
//	public void copy(InputStream input, OutputStream output) throws IOException {
//	    byte[] buffer = new byte[1024];
//	    int n = 0;
//	    while ((n = input.read(buffer)) != -1) {
//	        output.write(buffer, 0, n);
//	    }
//	}

}

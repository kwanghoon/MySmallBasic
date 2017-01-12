package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Stack
{
	static 
	{
		stacks = new HashMap<Value, java.util.Stack<Value>>();
	}
	
	/* stackName인자로 모든 type이 가능하므로 수정
	 * hashMap stacks - 현재 동일성검사로 인한 문제발생.(동등성을 체크해야한다...) 
	 * Value들의 equals, hashCode메소드 오버라이딩 or 다른방법.. - 다른 클래스에서 hashCode가 사용되는지..
	 * equals재정의는 필요(StrV, ArrayV)
	 * 더 나은 방법 모색중...
	 */
	private static HashMap<Value, java.util.Stack<Value>> stacks;
	
	
	//Stack.PushValue(stackName, value) - push in stack
	public static Value PushValue(ArrayList<Value> args) 
	{
		//check args_number
		if (args.size() == 2) 
		{
			//parameter
			Value stackName = args.get(0);	//arg1
			Value value = args.get(1);	//arg2
			
			java.util.Stack<Value> stack = stacks.get(stackName);
				
			//if stackName is not in stacks..
			if (stack==null) 
			{
				stacks.put(stackName, new java.util.Stack<Value>());
				stack = stacks.get(stackName);
			}
				
			//push
			stack.push(value);
				
			//return nothing
			return null;
		} 
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//Stack.GetCount(stackName)
	//return number of items in stack
	public static Value GetCount(ArrayList<Value> args) 
	{	
		//check args_number
		if (args.size() == 1) 
		{
			//parameter
			Value stackName = args.get(0);	//arg1
			
			java.util.Stack<Value> stack = stacks.get(stackName);
				
			//if stackName is not in stacks..
			if (stack==null) 
			{
				// not make new stack with name. just return 0
				return new DoubleV(0);
			}
			else
				return new DoubleV(stack.size());
		} 
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}
	
	//Stack.PopValue(stackName)
	//return popValue
	public static Value PopValue(ArrayList<Value> args) 
	{
		//check args_number
		if (args.size() == 1) 
		{
			//parameter
			Value stackName = args.get(0);	//arg1

			java.util.Stack<Value> stack = stacks.get(stackName);
				
			//if stackName is not in stacks..
			if (stack==null) 
			{
				//just return null - ?
				return null;
			}
			else
			{
				Value item = stack.pop();
					
				//if stack_size is zero
				if(stack.size() == 0)
				{
					//remove in stacks for saving memory
					stacks.remove(stackName);
				}	
				
				return item;
			}
		} 
		else 		
			throw new InterpretException("Error in # of Arguments: " + args.size());
	}

	public static void notifyFieldAssign(String fieldName) {
		
	}
	
	public static void notifyFieldRead(String fieldName) {
		
	}
}
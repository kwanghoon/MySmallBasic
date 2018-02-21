package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class List {
	
	private static HashMap<String, LinkedList<Value>> lists = new HashMap();
	
	//List.Add(listName, [value...])
	public static Value Add(ArrayList<Value> args){
		
		//check args
		if(args.size() == 0)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			lists.put(listName, new LinkedList<Value>());
		LinkedList<Value> list = lists.get(listName);
		
		//add values
		for(int i = 1; i < args.size(); i++){
			Value value = args.get(i);
			list.add(value);
		}
		
		return null;
	}
	//List.Concat(listName1,listName2, [listName3...])
	public static Value Concat(ArrayList<Value> args){
		//check args
		if(args.size() < 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check listName1
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			lists.put(listName, new LinkedList<Value>());
		LinkedList<Value> list = lists.get(listName);
		
		//add listName2 to listName1
		for(int i = 1; i < args.size(); i++){
			//get listeNmae2
			String addedListName = args.get(i).toString();
			LinkedList<Value> addedList = null;
			if(!lists.containsKey(listName))
				continue;
			addedList = lists.get(addedListName);
			
			list.addAll(addedList);
		}
		
		return null;
	}
	//List.Head(listName, [newListName])
	//return : listName's Head
	public static Value Head(ArrayList<Value> args){
		//check args
		if(args.size() == 0 || args.size() > 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return new StrV("[]");
		LinkedList<Value> list = lists.get(listName);
		
		Value head = list.getFirst();
		
		//check newListName
		if(args.size() == 2){
			//check newListName
			String newListName = args.get(1).toString();	//listName
			if(lists.containsKey(newListName))
				lists.get(newListName).clear();
			else
				lists.put(newListName, new LinkedList<Value>());
			
			LinkedList<Value> newList = lists.get(newListName);
			newList.add(head);
		}				
		return (Value)(new StrV('[' + head.toString() + ']'));
	}
	//List.Tail(listName, [newListName])
	//return : listName's Tail
	public static Value Tail(ArrayList<Value> args){
		//check args
		if(args.size() == 0 || args.size() > 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return new StrV("[]");
		LinkedList<Value> list = lists.get(listName);
		if(list.size() == 1)
			return new StrV("[]");
		
		Value head = list.getFirst();
		
		//check newListName
		LinkedList<Value> newList = null;
		if(args.size() == 2){
			//check newListName
			String newListName = args.get(1).toString();	//listName
			if(lists.containsKey(newListName))
				lists.get(newListName).clear();
			else
				lists.put(newListName, new LinkedList<Value>());
			
			newList = lists.get(newListName);
		}	
		
		//get Tail
		StringBuilder ret = new StringBuilder("[");
		Iterator<Value> iter = list.iterator();
		iter.next();	//head
		
		//2
		Value value = iter.next();
		ret.append(value.toString());
		if(newList != null)
			newList.add(value);
		//3~n
		while(iter.hasNext()){
			value = iter.next();
			ret.append(',' + value.toString());
			
			if(newList != null)
				newList.add(value);
		}
		ret.append(']');
		
		//if newList is empty, cancel in the list
		if(newList != null && newList.size() == 0)
			lists.remove(newList);
		
		return (Value)(new StrV(ret.toString()));
	}	
	//List.IsEmpty(listName)
	//return : "True" or "False"
	public static Value IsEmpty(ArrayList<Value> args){
		//check args
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return (Value)(new StrV("True"));
		LinkedList<Value> list = lists.get(listName);
		
		if(list.isEmpty())
			return (Value)(new StrV("True"));
		else
			return (Value)(new StrV("False"));
	}
	//List.Clear(listName)
	public static Value Clear(ArrayList<Value> args){
		//check args
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return null;
		lists.remove(listName);
		
		return null;
	}
	//List.Contains(listName, value)
	public static Value Contains(ArrayList<Value> args){
		//check args
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return (Value)(new StrV("False"));
		LinkedList<Value> list = lists.get(listName);
		
		//check value
		String value = args.get(1).toString();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			if(value.equals(iter.next().toString()))
				return (Value)(new StrV("True"));
		}
		
		return (Value)(new StrV("False"));
	}
	//List.Copy(listName, newListName)
	public static Value Copy(ArrayList<Value> args){
		//check args
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return null;
		LinkedList<Value> list = lists.get(listName);
		
		//check newListName
		String newListName = args.get(1).toString();	//listName
		if(lists.containsKey(newListName))
			lists.remove(newListName);
		else
			lists.put(newListName, new LinkedList<Value>());
		
		//copy
		lists.put(newListName, (LinkedList<Value>) list.clone());
		
		return null;
	}
	//List.Count(listName)
	//return : count of list element
	public static Value Count(ArrayList<Value> args){
		//check args
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return new DoubleV(0);
		LinkedList<Value> list = lists.get(listName);
		
		return new DoubleV(list.size());
	}
	//List.GetAt(listName, index)
	//return : listName's element of index
	public static Value GetAt(ArrayList<Value> args){
		//check args
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return null;	//empty list
		LinkedList<Value> list = lists.get(listName);
		
		//get index
		int index;
		try{
			index = Integer.parseInt(args.get(1).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		
		Value value = null;
		//java index start : 0, smallbasic index start : 1 
		try{
			value = list.get(index-1);
		}catch(IndexOutOfBoundsException e){
			return null;	//out of bound
		}
		
		return value;	
	}
	//List.IndexOf(listName, value)
	//return : index of value in list.  if not contains, return ""
	public static Value IndexOf(ArrayList<Value> args){
		//check args
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return (Value)(new StrV(""));
		LinkedList<Value> list = lists.get(listName);
				
		//check value
		String value = args.get(1).toString();
		for(int i = 0; i < list.size(); i++){
			if(value.equals(list.get(i).toString()))
				return (Value)(new DoubleV(i+1));	//smallbasic index is start at 1
		}
			
		return (Value)(new StrV(""));
	}
	//List.InsertAt(listName, index, value)
	public static Value InsertAt(ArrayList<Value> args){
		//check args
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check index
		int index;
		try{
			index = Integer.parseInt(args.get(1).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return null; //empty list;
		LinkedList<Value> list = lists.get(listName);
		
		//list index check
		if(list.size() < index)
			return null; //out of bound
		
		//check value
		Value value = args.get(2);
		list.add(index-1, value);
		
		return null;
	}
	//List.Remove(listName, match)
	public static Value Remove(ArrayList<Value> args){
		//check args
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return (Value)(new StrV("False"));
		LinkedList<Value> list = lists.get(listName);
		
		//check match
		String match = args.get(1).toString();
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			if(match.equals(iter.next().toString())){
				iter.remove();
				return null;
			}
		}
		
		return null;
	}	
	//List.RemoveAt(listName, index)
	public static Value RemoveAt(ArrayList<Value> args){
		//check args
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check index
		int index;
		try{
			index = Integer.parseInt(args.get(1).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return null; //empty list
		LinkedList<Value> list = lists.get(listName);
		
		//list index check
		if(list.size() < index)
			return null; //out of bound
		
		//remove
		list.remove(index-1);
		
		return null;
	}	
	//List.Reverse(listName)
	public static Value Reverse(ArrayList<Value> args){
		//check args
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return null;
		LinkedList<Value> list = lists.get(listName);
		
		//reverse
		Collections.reverse(list);
		
		return null;
	}
	//List.SetAt(listName, index, value)
	public static Value SetAt(ArrayList<Value> args){
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check index
		int index;
		try{
			index = Integer.parseInt(args.get(1).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return null; //empty list
		LinkedList<Value> list = lists.get(listName);
				
		//list index check
		if(list.size() < index)
			return null; // out of bound
		
		//value
		Value value = args.get(2);
		list.set(index-1, value);
		
		return null;
	}
	//List.SubList(listName, fromIndex, toIndex, [newListName])
	//return : subList
	public static Value SubList(ArrayList<Value> args){
		//check args
		if(args.size() != 3 && args.size() != 4)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check start, length
		int fromIndex, toIndex;
		try{
			fromIndex = Integer.parseInt(args.get(1).toString());
			toIndex = Integer.parseInt(args.get(2).toString());		
			
			if(fromIndex > toIndex)
				return null;	//fomrIndex is bigger than toImdex
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return null;  //empty list
		LinkedList<Value> list = lists.get(listName);
		
		//new ListName
		LinkedList<Value> newList = null;
		if(args.size() == 4){
			String newListName = args.get(3).toString();
			if(!lists.containsKey(newListName))
				lists.put(newListName, new LinkedList<Value>());
			newList = lists.get(newListName);			
		}
		
		//get subList
		StringBuilder ret = new StringBuilder("[");
		java.util.List<Value> sub = list.subList(fromIndex-1, toIndex-1);
		if(sub.size() != 0){
			//index 1
			Iterator<Value> iter = sub.iterator();
			Value next = iter.next();
			ret.append(next.toString());
			if(newList != null)
				newList.add(next);
			
			//index 2~
			if(iter.hasNext()){
				next = iter.next();
				ret.append(", " + next.toString());
				if(newList != null)
					newList.add(next);
			}
				
		}else{
			lists.remove(newList);	//because empty
		}
		ret.append("]");
		
		
		return null;
	}
	//List.ToArray(listName)
	public static Value ToArray(ArrayList<Value> args){
		//check args
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName))
			return new ArrayV();
		LinkedList<Value> list = lists.get(listName);
		
		//insert element
		ArrayV ret = new ArrayV();
		int index = 1;
		Iterator<Value> iter = list.iterator();
		while(iter.hasNext()){
			ret.put(Integer.toString(index), iter.next());
			index++;
		}
		
		return ret;
	}	
	//List.Print(listName)
	public static Value Print(ArrayList<Value> args){
		
		//check args
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//check listName
		String listName = args.get(0).toString();	//listName
		if(!lists.containsKey(listName)){
			System.out.println("[]");
			return null;
		}
		LinkedList<Value> list = lists.get(listName);		
		
		//print list values
		System.out.print('[');
		Iterator<Value> iter = list.iterator();
		if(iter.hasNext()){
			Value value = iter.next();
			System.out.print(value);
		}
		while(iter.hasNext()){
			Value value = iter.next();
			System.out.print(","+ value);
		}
		System.out.println(']');
		
		return null;
	}

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
	
}

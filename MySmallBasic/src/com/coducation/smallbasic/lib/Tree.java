package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Tree {

	private static HashMap<String, TreeNode> trees = new HashMap<>();
	private static int treeKey = 1;

	// Tree.Create([rootValue, [childTreeRef]])
	// return tree ref
	public static Value Create(ArrayList<Value> args) {
		String treeRef = getTreeKey();	//new treeID
		
		if (args.size() == 0) {	//Create()
			trees.put(treeRef, new TreeNode(treeRef));
			
		}else if(args.size() == 1){	//Create(rootValue)
			Value rootValue = args.get(0);
			trees.put(treeRef, new TreeNode(treeRef, rootValue));
		}else{	//Create(rootValue, childTree, ....)
			Value rootValue = args.get(0);
			TreeNode treeNode = new TreeNode(treeRef, rootValue);
			
			//childTree
			for(int i = 1; i < args.size(); i++){
				String childTreeRef = args.get(i).toString();
				if(!trees.containsKey(childTreeRef)){
					throw new InterpretException("Error in childTree of Arguments: " + childTreeRef + " is invalid tree");
				}
				TreeNode childTree = trees.get(childTreeRef);
				treeNode.addChild(childTree);
			}
			
			trees.put(treeRef, treeNode);
		}
		return new StrV(treeRef);
	}
	//Tree.AddChild(treeRef, [childTreeRef...])
	public static Value AddChild(ArrayList<Value> args){
		//args size check
		if(args.size() == 0){
			throw new InterpretException("Error in # of Arguments: " + args.size());
		}
		
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			throw new InterpretException("Error in tree of Arguments: " + treeRef + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeRef);
		
		//add childTree
		for(int i = 1; i < args.size(); i++){
			String childTreeRef = args.get(i).toString();
			if(!trees.containsKey(childTreeRef)){
				throw new InterpretException("Error in childTree of Arguments: " + childTreeRef + " is invalid tree");
			}
			TreeNode childTree = trees.get(childTreeRef);
			treeNode.addChild(childTree);
		}
		
		return args.get(0);
	}
	//Tree.AddChildAt(treeRef, index, childTreeRef)
	public static Value AddChildAt(ArrayList<Value> args){
		//args size check
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			throw new InterpretException("Error in tree of Arguments: " + treeRef + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeRef);
		
		//get index
		int index;
		try{
			index = Integer.parseInt(args.get(1).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		
		//get childTreeRef
		String childTreeRef = args.get(2).toString();
		if(!trees.containsKey(childTreeRef)){
			throw new InterpretException("Error in childTree of Arguments: " + childTreeRef + " is invalid tree");
		}
		TreeNode childTree = trees.get(childTreeRef);
		
		treeNode.addChildAt(index, childTree);
		
		return args.get(0);
	}
	//Tree.RemoveChild(treeRef, childTreeRef)
	public static Value RemoveChild(ArrayList<Value> args){
		//args size check
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			throw new InterpretException("Error in tree of Arguments: " + treeRef + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeRef);
				
		//remove childTree
		String childTreeRef = args.get(1).toString();
		if(!trees.containsKey(childTreeRef)){
			throw new InterpretException("Error in childTree of Arguments: " + childTreeRef + " is invalid tree");
		}
		TreeNode childTree = trees.get(childTreeRef);
		treeNode.removeChild(childTree);
		
		return args.get(0);		
	}
	//Tree.RemoveChildAt(treeRef, index)
	public static Value RemoveChildAt(ArrayList<Value> args){
		//args size check
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			throw new InterpretException("Error in tree of Arguments: " + treeRef + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeRef);
				
		//get index
		int index;
		try{
			index = Integer.parseInt(args.get(1).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		treeNode.removeChildAt(index);
		
		return args.get(0);		
	}
	//Tree.GetValue(treeRef)
	//return : rootValue
	public static Value GetValue(ArrayList<Value> args) {
		//args size check
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
				
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			throw new InterpretException("Error in tree of Arguments: " + treeRef + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeRef);
		
		return treeNode.getValue();
	}
	//Tree.SetValue(treeRef, value)
	//return treeRef
	public static Value SetValue(ArrayList<Value> args){
		//args size check
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
						
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			throw new InterpretException("Error in tree of Arguments: " + treeRef + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeRef);
		
		Value value = args.get(1);
		
		treeNode.setValue(value);
		
		return args.get(0);
	}
	//Tree.GetChild(treeRef)
	//return childTree Array
	public static Value GetChild(ArrayList<Value> args){
		//args size check
		if(args.size() != 1)
				throw new InterpretException("Error in # of Arguments: " + args.size());
								
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			throw new InterpretException("Error in tree of Arguments: " + treeRef + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeRef);
		
		ArrayList<TreeNode> childList = treeNode.getChild();
		Iterator<TreeNode> iter = childList.iterator();
		ArrayV childArray = new ArrayV();
		int index = 1;
		while(iter.hasNext()){
			childArray.put(Integer.toString(index++), iter.next().getValue());
		}
		
		return childArray;
	}
	//Tree.GetChildAt(treeRef, index)
	public static Value GetChildAt(ArrayList<Value> args) {
		
		
		return null;
	}
	//Tree.Print(treeRef)
	public static Value Print(ArrayList<Value> args) {
		//args size check
		if(args.size() != 1){
			throw new InterpretException("Error in # of Arguments: " + args.size());
		}
		
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			throw new InterpretException("Error in tree of Arguments: " + treeRef + " is invalid tree");
		}
		TreeNode tree = trees.get(treeRef);
		
		tree.print();
		System.out.println();
		
		return null;
	}

	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}

	private static String getTreeKey() {
		return "$Tree" + (treeKey++);
	}
}

// tree structure
class TreeNode {
	private String treeRef;
	private Value value;
	private ArrayList<TreeNode> child = new ArrayList<TreeNode>();

	TreeNode(String treeRef) {
		this.treeRef = treeRef;
	}

	TreeNode(String treeRef, Value value) {
		this.treeRef = treeRef;
		this.value = value;
	}

	TreeNode(String treeRef, Value value, ArrayList<TreeNode> child) {
		this.treeRef = treeRef;
		this.value = value;
		this.child = child;
	}

	void addChild(TreeNode treeNode) {
		child.add(treeNode);
	}
	void addChildAt(int index, TreeNode treeNode){
		if(index < 1){
			throw new InterpretException("Error in child index of Arguments: index shoud be bigger than 1");
		}else if(child.size() == index-1){
			child.add(treeNode);
		}else if(child.size() < index){
			throw new InterpretException("Error in child index of Arguments: " + index + " is out of bounds");
		}else{
			child.add(index-1, treeNode);
		}
	}

	void removeChild(TreeNode treeNode) {
		child.remove(treeNode);
	}
	void removeChildAt(int index){
		if(index < 1 || child.size() < index){
			throw new InterpretException("Error in child index of Arguments: " + index + " is out of bounds");
		}
		child.remove(index-1);
	}

	void removeChildByIndex(int index) {
		child.remove(index);
	}

	void clearChild() {
		child.clear();
	}

	void print() {
		System.out.print("(" + value);
		Iterator<TreeNode> iter = child.iterator();
		while (iter.hasNext()) {
			System.out.print(":");
			iter.next().print();
		}
		System.out.print(")");
	}
	Value getValue(){
		return value;
	}
	void setValue(Value value){
		this.value = value;
	}
	ArrayList<TreeNode> getChild(){
		return child;
	}
}
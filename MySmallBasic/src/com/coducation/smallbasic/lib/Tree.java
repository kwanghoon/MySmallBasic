package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Tree {

	private static HashMap<String, TreeNode> trees = new HashMap<>();

	// Tree.Create(treeName, [rootValue, [childTreeValue...]])
	public static Value Create(ArrayList<Value> args) {		
		if (args.size() == 0) {
			throw new InterpretException("Error in # of Arguments: " + args.size());	
		}
		//treeName
		String treeName = args.get(0).toString();
		TreeNode newTree = new TreeNode();
		if(trees.containsKey(treeName))
			trees.remove(treeName);
		trees.put(treeName, newTree);
		
		//rootValue
		if(args.size() >= 2){
			Value rootValue = args.get(1);
			newTree.setValue(rootValue);
		}
		//childTree Value
		if(args.size() > 2){
			for(int i = 2; i < args.size(); i++){
				Value childTreeValue = args.get(i);
				newTree.addChild(new TreeNode(childTreeValue));
			}
		}
		
		return null;
	}
	//Tree.Clear(treeName, [treeName...])
	public static Value Clear(ArrayList<Value> args) {		
		if (args.size() == 0) {
			throw new InterpretException("Error in # of Arguments: " + args.size());	
		}
		
		//clear
		for(int i = 0; i < args.size(); i++){
			String treeName = args.get(i).toString();
			if(trees.containsKey(treeName))
				trees.remove(treeName);
		}		
		return null;
	}	
	//Tree.AddChild(treeName, [childTreeName...])
	public static Value AddChild(ArrayList<Value> args){
		//args size check
		if(args.size() == 0){
			throw new InterpretException("Error in # of Arguments: " + args.size());
		}
		
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			throw new InterpretException("Error in tree of Arguments: " + treeName + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeName);
		
		//add childTree
		for(int i = 1; i < args.size(); i++){
			String childTreeName = args.get(i).toString();
			if(trees.containsKey(childTreeName)){
				TreeNode childTree = trees.get(childTreeName);
				treeNode.addChild(childTree);
			}
			
		}
		
		return null;
	}
	//Tree.AddChildAt(treeName, index, childTreeName)
	public static Value AddChildAt(ArrayList<Value> args){
		//args size check
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			throw new InterpretException("Error in tree of Arguments: " + treeName + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeName);
		
		//get index
		int index;
		try{
			index = Integer.parseInt(args.get(1).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		
		//get childTreeName
		String childTreeName = args.get(2).toString();
		if(!trees.containsKey(childTreeName)){
			throw new InterpretException("Error in childTree of Arguments: " + childTreeName + " is invalid tree");
		}
		TreeNode childTree = trees.get(childTreeName);
		
		treeNode.addChildAt(index, childTree);		
		return null;
	}
	//Tree.RemoveChild(treeName, childTreeName, [childTreeName...])
	public static Value RemoveChild(ArrayList<Value> args){
		//args size check
		if(args.size() == 0)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			return null;
		}
		TreeNode treeNode = trees.get(treeName);
				
		//remove childTree
		for(int i = 1; i < args.size(); i++){
			String childTreeName = args.get(i).toString();
			if(!trees.containsKey(childTreeName)){
				return null;
			}
			TreeNode childTree = trees.get(childTreeName);
			treeNode.removeChild(childTree);
		}
		
		return null;		
	}
	//Tree.RemoveChildAt(treeName, index)
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
		
		return null;		
	}
	//Tree.RemoveAllChildren(treeName)
	public static Value RemoveAllChildren(ArrayList<Value> args){
		//args size check
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree
		String treeRef = args.get(0).toString();
		if(!trees.containsKey(treeRef)){
			return null;
		}
		TreeNode treeNode = trees.get(treeRef);
		
		treeNode.removeAllChildren();
		
		return null;		
	}	
	//Tree.GetValue(treeName)
	//return : rootValue
	public static Value GetValue(ArrayList<Value> args) {
		//args size check
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
				
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			return new StrV("");
		}
		TreeNode treeNode = trees.get(treeName);
		
		return treeNode.getValue();
	}
	//Tree.SetValue(treeName, value)
	//return treeName
	public static Value SetValue(ArrayList<Value> args){
		//args size check
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
						
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			throw new InterpretException("Error in tree of Arguments: " + treeName + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeName);
		
		Value value = args.get(1);		
		treeNode.setValue(value);
		
		return args.get(0);
	}
	//Tree.GetChildAt(treeName, index, newTreeName)
	public static Value GetChildAt(ArrayList<Value> args) {
		//args size check
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
										
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			throw new InterpretException("Error in tree of Arguments: " + treeName + " is invalid tree");
		}
		TreeNode treeNode = trees.get(treeName);
		
		//get index
		int index;
		try{
			index = Integer.parseInt(args.get(1).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(1) + " is not number");
		}
		
		//get
		TreeNode child = treeNode.getChildAt(index);
		if(child != null){
			String newTreeName = args.get(2).toString();
			if(trees.containsKey(newTreeName))
				trees.remove(treeName);
			trees.put(newTreeName, child);
		}
				
		return null;
	}
	//Tree.GetNumberOfChildren(treeName)
	public static Value GetNumberOfChildren(ArrayList<Value> args) {
		//args size check
		if(args.size() != 1){
			throw new InterpretException("Error in # of Arguments: " + args.size());
		}
				
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			return new DoubleV(0);
		}
		TreeNode tree = trees.get(treeName);
		
		return new DoubleV(tree.getNumberOfChildren());
	}
	//Tree.Print(treeName)
	public static Value Print(ArrayList<Value> args) {
		//args size check
		if(args.size() != 1){
			throw new InterpretException("Error in # of Arguments: " + args.size());
		}
		
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			System.out.println("()");
			return null;
		}
		
		//print
		TreeNode tree = trees.get(treeName);
		tree.print();
		System.out.println();
		
		return null;
	}
	//Tree.Copy(treeName, newTreeName)
	public static Value Copy(ArrayList<Value> args) {
		//args size check
		if(args.size() != 2){
			throw new InterpretException("Error in # of Arguments: " + args.size());
		}
		
		//get tree
		String treeName = args.get(0).toString();
		if(!trees.containsKey(treeName)){
			return null;
		}
		TreeNode tree = trees.get(treeName);
		
		//copy
		TreeNode newTree = null;
		try {
			newTree = tree.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set new Name
		String newTreeName = args.get(1).toString();
		if(trees.containsKey(newTreeName))
			trees.remove(newTreeName);
		trees.put(newTreeName, newTree);
		return null;
	}
	//Tree.Show(treeName)
	public static Value Show(ArrayList<Value> args){
		//args size check
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree
		String treeName = args.get(0).toString();
		if(trees.containsKey(treeName))
			trees.get(treeName).show(treeName);

		return null;
	}
	//Tree.Hide(treeName)
	public static Value Hide(ArrayList<Value> args){
		//args size check
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree
		String treeName = args.get(0).toString();
		if(trees.containsKey(treeName))
			trees.get(treeName).hide(treeName);
		
		return null;
	}
	//Tree.SetLocation(treeName, x, y)
	public static Value SetLocation(ArrayList<Value> args){
		//args size check
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree, x, y
		String treeName = args.get(0).toString();
		int x, y;
		try{
			x = Integer.parseInt(args.get(1).toString());
			y = Integer.parseInt(args.get(2).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(2) + " is not number");
		}
		
		//setLocation
		if(trees.containsKey(treeName))
			trees.get(treeName).setLotation(treeName, x, y);
		
		return null;
	}
	//Tree.SetSize(treeName, width, height)
	public static Value SetSize(ArrayList<Value> args){
		//args size check
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get tree, width, height
		String treeName = args.get(0).toString();
		int width, height;
		try{
			width = Integer.parseInt(args.get(1).toString());
			height = Integer.parseInt(args.get(2).toString());
		}catch(NumberFormatException e){
			throw new InterpretException("Error in index of Arguments: " + args.get(2) + " is not number");
		}
		
		//setLocation
		if(trees.containsKey(treeName))
			trees.get(treeName).setSize(treeName, width, height);
		
		return null;
	}
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
}

// tree structure
class TreeNode implements Cloneable{
	private Value value;
	private ArrayList<TreeNode> child = new ArrayList<TreeNode>();

	TreeNode() {
	}
	TreeNode(Value value){
		this.value = value;
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
			return;
		}
		child.remove(index-1);
	}
	void removeAllChildren() {
		child.clear();
	}
	int getNumberOfChildren(){
		return child.size();
	}
	TreeNode getChildAt(int index){
		if(index < 1 || child.size() < index){
			return null;
		}
		return child.get(index-1);
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
	public TreeNode clone() throws CloneNotSupportedException{
		TreeNode obj = (TreeNode) super.clone();
		obj.child = new ArrayList<TreeNode>();
		for(int i = 0; i < child.size(); i++){
			obj.child.add(child.get(i).clone());
		}
		
		return obj;	
	}
	//call GraphicsWindow method
	void show(String treeID){
		org.graphstream.graph.Graph graph = new SingleGraph(treeID);
		Node n = graph.addNode("t1");
		n.addAttribute("ui.label", value.toString());
		int count = 2;
		
		Iterator<TreeNode> iter = child.iterator();
		while(iter.hasNext()){
			iter.next().drawTree(graph, "t1", "t"+count);
			count++;
		}
		
		GraphicsWindow.AddGraph(graph.getId(), graph);
	}
	void hide(String treeID){
		GraphicsWindow.HideGraph(treeID);
	}
	void setLotation(String treeID, int x, int y){
		if(!GraphicsWindow.hasGraphContain(treeID))
			show(treeID);
		
		GraphicsWindow.SetGraphLocation(treeID, x, y);
	}
	void setSize(String treeID, int width, int height){
		if(!GraphicsWindow.hasGraphContain(treeID))
			show(treeID);
		
		GraphicsWindow.SetGraphSize(treeID, width, height);
	}
	private void drawTree(org.graphstream.graph.Graph graph, String parentNodeName, String thisNodeName){
		Node n = graph.addNode(thisNodeName);
		n.addAttribute("ui.label", value.toString());		
		graph.addEdge(parentNodeName + "-" + thisNodeName, parentNodeName, thisNodeName);
		
		int count = 1;
		Iterator<TreeNode> iter = child.iterator();
		while(iter.hasNext()){
			iter.next().drawTree(graph, thisNodeName, thisNodeName + '_' +count);
			count++;
		}		
	}
}
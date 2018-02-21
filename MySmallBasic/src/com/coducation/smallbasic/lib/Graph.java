package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.graphstream.graph.implementations.MultiGraph;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Graph {
	private static HashMap<String, DirectedGraph> graphs = new HashMap<>();
	
	//Graph.InsertVertex(graphName, [vertexName..])
	public static Value InsertVertex(ArrayList<Value> args){
		//args size check
		if(args.size() < 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			graphs.put(graphName, new DirectedGraph(graphName));
		DirectedGraph graph = graphs.get(graphName);
		
		//add vertex
		for(int i = 1; i < args.size(); i++){
			String vertexName = args.get(i).toString();
			if(!graph.hasVertex(vertexName))
				graph.addVertex(vertexName);
		}
						
		return null;
	}
	//Graph.RemoveVertex(graphName, [vertexName...])
	public static Value RemoveVertex(ArrayList<Value> args){
		//args size check
		if(args.size() < 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
				
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return null;	//empty graph
		DirectedGraph graph = graphs.get(graphName);
		
		//remove vertex
		for(int i = 1; i < args.size(); i++){
			String vertexName = args.get(i).toString();
			if(graph.hasVertex(vertexName))
				graph.removeVertex(vertexName);
		}
		
		return null;
	}
	//Graph.InsertEdge(graphName, fromVertexName, toVertexName, edgeName, [label])
	public static Value InsertEdge(ArrayList<Value> args){
		//args size check
		if(args.size() != 4 && args.size() != 5)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return null;	//empty graph
		DirectedGraph graph = graphs.get(graphName);
		
		//insert
		String fromVertexName = args.get(1).toString();
		String toVertexName = args.get(2).toString();
		String edgeName = args.get(3).toString();
		
		if(args.size() == 4)
			graph.insertEdge(fromVertexName, toVertexName, edgeName);
		else
		{
			Value label = args.get(4);
			graph.insertEdge(fromVertexName, toVertexName, edgeName, label);
		}
		
		return null;
	}
	//Graph.SetVertexLabel(graphName, vertexName, label)
	public static Value SetVertexLabel(ArrayList<Value> args){
		//args size check
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return null;
		DirectedGraph graph = graphs.get(graphName);
		
		//set label
		String vertexName = args.get(1).toString();
		Value label = args.get(2);
		graph.setVertexLabel(vertexName, label);
		
		return null;
	}
	//Graph.GetVertexLabel(graphName, vertexName)
	//return : vertex label
	public static Value GetVertexLabel(ArrayList<Value> args){
		//args size check
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return new StrV("");
		DirectedGraph graph = graphs.get(graphName);
		
		//get label
		String vertexName = args.get(1).toString();
		return graph.getVertexLabel(vertexName);
	}
	//Graph.SetEdgeLabel(graphName, edgeName, label)
	public static Value SetEdgeLabel(ArrayList<Value> args){
		//args size check
		if(args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return null;
		DirectedGraph graph = graphs.get(graphName);
		
		//set label
		String edgeName = args.get(1).toString();
		Value label = args.get(2);
		graph.setEdgeLabel(edgeName, label);
		
		return null;
	}
	//Graph.GetEdgeLabel(graphName, edgeName)
	//return : edge label
	public static Value GetEdgeLabel(ArrayList<Value> args){
		//args size check
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return new StrV("");
		DirectedGraph graph = graphs.get(graphName);
		
		//get label
		String edgeName = args.get(1).toString();
		return graph.getEdgeLabel(edgeName);
	}
	//Graph.RemoveEdge(graphName, edgeName)
	//Graph.RemoveEdge(graphName, fromVertexName, toVertexName)   - delete one edge
	public static Value RemoveEdge(ArrayList<Value> args){
		//args size check
		if(args.size() != 2 && args.size() != 3)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return null;
		DirectedGraph graph = graphs.get(graphName);
		
		
		if(args.size() == 2){	//edgeName
			String edgeName = args.get(1).toString();
			graph.removeEdge(edgeName);
		}else{	//fromVertex, toVertex
			String fromVertexName = args.get(1).toString();
			String toVertexName = args.get(2).toString();
			graph.removeEdge(fromVertexName, toVertexName);
		}
		
		return null;
	}
	//Graph.NumberOfEdge(graphName, fromVertexName)
	//return : number of edge
	public static Value NumberOfEdge(ArrayList<Value> args){
		//args size check
		if(args.size() != 2)
			throw new InterpretException("Error in # of Arguments: " + args.size());
				
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return new StrV("");
		DirectedGraph graph = graphs.get(graphName);
		
		String fromVertexName = args.get(1).toString();
		int ret = graph.getNumberOfEdge(fromVertexName);
		
		if(ret == -1)
			return new DoubleV(0);
		else
			return new DoubleV(ret);
	}
	//Graph.GetEdgeAt(graphName, fromVertexName, toVertexName, index)
	//Graph.GetEdgeAt(graphName, fromVertexName, index)
	//return : edge name
	public static Value GetEdgeAt(ArrayList<Value> args){
		
		if(args.size() != 3 && args.size() != 4)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return new StrV("");
		DirectedGraph graph = graphs.get(graphName);
		
		//get fromVertex
		String fromVertexName = args.get(1).toString();
		
		if(args.size() == 3){
			//get index
			int index;
			try{
				index = Integer.parseInt(args.get(2).toString());
			}catch(NumberFormatException e){
				throw new InterpretException("Error in index of Arguments: " + args.get(2) + " is not number");
			}
			
			return graph.getEdgeAt(fromVertexName, index);
		}else{
			//get toVertexName
			String toVertexName = args.get(2).toString();
			
			//get index
			int index;
			try{
				index = Integer.parseInt(args.get(3).toString());
			}catch(NumberFormatException e){
				throw new InterpretException("Error in index of Arguments: " + args.get(3) + " is not number");
			}
			
			return graph.getEdgeAt(fromVertexName, toVertexName, index);
		}
	}
	//Graph.GetAllVertex(graphName)
	//return : all vertex name array in graph
	public static Value GetAllVertex(ArrayList<Value> args){
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return new ArrayV();
		DirectedGraph graph = graphs.get(graphName);
		
		//get vertices and make array
		Set<String> vertices = graph.getVertices();
		ArrayV ret = new ArrayV();
		Iterator<String> iter = vertices.iterator();
		int count = 1;
		while(iter.hasNext()){
			ret.put(Integer.toString(count++), new StrV(iter.next()));
		}
		
		return ret;
	}
	//Graph.GetNeighbors(graphName, fromVertexName)
	//return : toVertex name array -- 중복허용????
	public static Value GetNeighbors(ArrayList<Value> args){
		return null;
	}
	
	//Graph.Show(graphName)
	public static Value Show(ArrayList<Value> args){
		//args size check
		if(args.size() != 1)
			throw new InterpretException("Error in # of Arguments: " + args.size());
		
		//get graph
		String graphName = args.get(0).toString();
		if(!graphs.containsKey(graphName))
			return null;
		DirectedGraph graph = graphs.get(graphName);
		graph.show();
		return null;
	}
	public static void notifyFieldAssign(String fieldName) {
	}

	public static void notifyFieldRead(String fieldName) {
	}
}

class DirectedGraph{
	private HashMap<String, Vertex> vertices = new HashMap<>();
	private HashMap<String, Edge> edges = new HashMap<>();
	private MultiGraph graphlib;
	DirectedGraph(String id){
		graphlib = new MultiGraph(id);
	}
	void addVertex(String vertexName){
		vertices.put(vertexName, new Vertex(vertexName));
		graphlib.addNode(vertexName);
	}
	boolean hasVertex(String vertexName){
		return vertices.containsKey(vertexName);
	}
	void removeVertex(String vertexName){
		Vertex v = vertices.get(vertexName);
		
		//remove edge
		ArrayList<Edge> neighbors = v.getEdges();
		Iterator<Edge> iter = neighbors.iterator();
		while(iter.hasNext()){
			String id = iter.next().getID();
			edges.remove(id);
		}
		//remove vertex
		vertices.remove(vertexName);
		//graphlib
		graphlib.removeNode(vertexName);
	}
	void insertEdge(String fromVertexName, String toVertexName, String edgeName){
		//invalid vertex
		if(!vertices.containsKey(fromVertexName) || !vertices.containsKey(toVertexName))
			return;
		//invalid edgeName
		if(edges.containsKey(edgeName))
			throw new InterpretException("Error in edgeName: already exists name");
		//insert
		Vertex fromVertex = vertices.get(fromVertexName);
		Vertex toVertex = vertices.get(toVertexName);
		Edge insertEdge = new Edge(edgeName, fromVertex, toVertex);
		fromVertex.addEdge(insertEdge);
		//toVertex.addNeighbor(insertEdge); because of directed graph
		edges.put(edgeName, insertEdge);
		
		graphlib.addEdge(edgeName, fromVertexName, toVertexName);
	}
	void insertEdge(String fromVertexName, String toVertexName, String edgeName, Value label){
		insertEdge(fromVertexName, toVertexName, edgeName);
		Edge insertEdge = edges.get(edgeName);
		insertEdge.setLabel(label);
	}
	void removeEdge(String edgeName){
		if(edges.containsKey(edgeName)){
			Edge edge = edges.get(edgeName);
			edge.getFromVertex().removeEdge(edge);	//vertex's edge remove
			edges.remove(edgeName);
			graphlib.removeEdge(edgeName);
		}
	}
	void removeEdge(String fromVertexName, String toVertexName){
		if(vertices.containsKey(fromVertexName) && vertices.containsKey(toVertexName)){
			Vertex fromVertex = vertices.get(fromVertexName);
			Vertex toVertex = vertices.get(toVertexName);
			Iterator iter = fromVertex.getEdges().iterator();
			while(iter.hasNext()){
				Edge edge = (Edge)iter.next();
				
				//check toVertex
				if(edge.getToVertex() == toVertex){
					iter.remove();					
					edges.remove(edge.getID());
					graphlib.removeEdge(edge.getID());
					break;
				}
			}
		}
	}
	void setVertexLabel(String vertexName, Value label){
		if(vertices.containsKey(vertexName))
			vertices.get(vertexName).setLabel(label);
	}
	Value getVertexLabel(String vertexName){
		if(vertices.containsKey(vertexName))
			return vertices.get(vertexName).getLabel();
		else
			return new StrV("");
	}
	void setEdgeLabel(String edgeName, Value label){
		if(edges.containsKey(edgeName))
			edges.get(edgeName).setLabel(label);
	}
	Value getEdgeLabel(String edgeName){
		if(edges.containsKey(edgeName))
			return edges.get(edgeName).getLabel();
		else
			return new StrV("");
	}
	int getNumberOfEdge(String fromVertex){
		if(vertices.containsKey(fromVertex))
			return vertices.get(fromVertex).getEdges().size();
		else 
			return -1;
	}
	Value getEdgeAt(String fromVertexName, String toVertexName, int index) {
		//fromVertex/index check
		if(!hasVertex(fromVertexName) || index < 1)
			return new StrV("");
		Vertex fromVertex = vertices.get(fromVertexName);
		int count = 1;
		
		//search
		Iterator<Edge> iter = fromVertex.getEdges().iterator();
		while(iter.hasNext()){
			Edge edge = iter.next();
			if(toVertexName.equals(edge.getToVertex().getID())){
				if(count == index)
					return new StrV(edge.getID());
				else 
					count++;
			}
		}
		
		return new StrV("");
	}
	Value getEdgeAt(String fromVertexName, int index) {
		//fromVertex/index check
		if(!hasVertex(fromVertexName) || index < 1)
			return new StrV("");
				
		Vertex fromVertex = vertices.get(fromVertexName);
		
		//search
		ArrayList<Edge> edgeList = fromVertex.getEdges();
		if(edgeList.size() < index)
			return new StrV("");
		else
			return new StrV(edgeList.get(index-1).getID());
	}
	Set<String> getVertices(){
		return vertices.keySet();
	}
	void show(){
		graphlib.display();
	}
}

class Vertex{
	private ArrayList<Edge> edges = new ArrayList<>();
	private Value label;
	private String id;
	
	Vertex(String id){
		this.id = id;
	}
	String getID(){
		return id;
	}
	void setLabel(Value label){
		this.label = label;
	}
	Value getLabel(){
		return label;
	}
	void addEdge(Edge edge){
		edges.add(edge);
	}
	void removeEdge(Edge edge){
		edges.remove(edge);
	}
	void removeAllEdge(){
		edges.clear();
	}
	ArrayList<Edge> getEdges(){
		return edges;
	}
}

class Edge{
	private Vertex fromVertex;
	private Vertex toVertex;
	private String id;
	private Value label;	
	
	Edge(String id, Vertex from, Vertex to){
		this.id = id;
		this.fromVertex = from;
		this.toVertex = to;
	}
	Edge(String id, Vertex from, Vertex to, Value label){
		this(id, from, to);
		this.label = label;
	}
	void setLabel(Value label){
		this.label = label;
	}
	Value getLabel(){
		return label;
	}
	String getID(){
		return id;
	}
	Vertex getFromVertex(){
		return fromVertex;
	}
	Vertex getToVertex(){
		return toVertex;
	}
}
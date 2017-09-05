package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Graph {
	
	private static HashMap<String, Graph> graph_map = new HashMap<>();
	private static int key = 1 ;
	
	private HashSet<Vertex> vertex_set = new HashSet<Vertex>();
	private HashSet<Edge> edge_set = new HashSet<Edge>();
	
	public static Value Graph(ArrayList<Value> args) {
		
		if (args.size() == 1) {
			
			Graph g = new Graph();
			
			Vertex v = new Vertex(args.get(0));
			g.vertex_set.add(v);
			
			graph_map.put("Graph" + key, g);


		} else if (args.size() > 1) {
			
			Graph g = new Graph();
			
			for ( int i = 0; i < args.size(); i++ ) {
				
				Vertex v = new Vertex(args.get(i));
				g.vertex_set.add(v);
				
			}
			
			Object[] edges = g.vertex_set.toArray();
			
			for ( int i = 0; i < args.size() - 1; i++ ) {
				Edge e = new Edge((Vertex)edges[i], (Vertex)edges[i+1]);
				g.edge_set.add(e);
				
			}
			
			graph_map.put("Graph" + key, g);
			
			
		} else {
			
			throw new InterpretException("Graph : Unexpected # of args : " + args.size());
			
		}
		
		return new StrV("Graph" + key++);
		
	}
	
	public static Value Union(ArrayList<Value> args) {
		
		Graph g0;
		Graph g1;
		Graph new_graph;
		
		String str_arg0;
		String str_arg1;
		
		if (args.size() == 2) {
			
			if (args.get(0) instanceof StrV) {

				str_arg0 = ((StrV) args.get(0)).getValue();
				g0 = graph_map.get(str_arg0);

			} else {

				throw new InterpretException("Union : Unexpected 1st argument");

			}
			
			if (args.get(1) instanceof StrV) {

				str_arg1 = ((StrV) args.get(1)).getValue();
				g1 = graph_map.get(str_arg1);

			} else {

				throw new InterpretException("Union : Unexpected 2nd argument");

			}
			
		} else
			
			throw new InterpretException("Union : Unexpected # of args : " + args.size());
		
		new_graph = new Graph();
		
		new_graph.vertex_set.addAll(g0.vertex_set);
		new_graph.vertex_set.addAll(g1.vertex_set);
		
		new_graph.edge_set.addAll(g0.edge_set);
		new_graph.edge_set.addAll(g1.edge_set);
		
		graph_map.put("Graph" + key, new_graph);
		
		return new StrV("Graph" + key++);
	}
	
	public static Value printVertex(ArrayList<Value> args) {
		
		String str;
		
		str = ((StrV)args.get(0)).getValue();
		Graph g = graph_map.get(str);

		Object[] vertex = g.vertex_set.toArray();
		
		for( int i = 0; i < vertex.length; i++ ) {
			System.out.println(vertex[i]);
		}
		
		return new StrV();
	}
	
	public static Value Neighbors(ArrayList<Value> args) {
		
		return null;
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}

}

class Vertex {
	
	Value v;
	
	Vertex (Value v) {
		this.v = v;
	}
	
	@Override
	public boolean equals (Object arg) {
		
		if(arg instanceof Vertex)
			return this.v.equals(((Vertex)arg).v);
		else
			return false;
		
	}
	
	@Override
	public String toString() {
		return this.v.toString();
	}
	
}

class Edge {
	
	Vertex departure;
	Vertex destination;
	
	Edge (Vertex departure, Vertex destination) {
		this.departure = departure;
		this.destination = destination;
	}
	
}
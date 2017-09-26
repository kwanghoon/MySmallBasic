package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Graph {
	
	private static HashMap<String, Graph> graph_map = new HashMap<>();
	private static int key = 1 ;
	
	private Set<Vertex> vertex_set = new LinkedHashSet<Vertex>();
	private Set<Edge> edge_set = new LinkedHashSet<Edge>();
	
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
	
	// list = Graph.Neighbors(g, 4)
	public static Value Neighbors(ArrayList<Value> args) {
		
		Graph graph;
		
		String str_arg;
		Value v_arg;
		
		if (args.size() == 2) {
			
			if (args.get(0) instanceof StrV) {
				
				str_arg = ((StrV) args.get(0)).getValue();
				graph = graph_map.get(str_arg);

			} else {
			
				throw new InterpretException("Neighbors : Unexpected 1st argument");
				
			}

			if (args.get(1) instanceof Value) {

				v_arg = args.get(1);
				
			} else {

				throw new InterpretException("Neighbors : Unexpected 2nd argument");

			}
			
		} else
			
			throw new InterpretException("Neighbors : Unexpected # of args: " + args.size());
		
		ArrayList<Value> neighbor_list = new ArrayList<Value>();
		
		for (Edge e : graph.edge_set) {
			if ( e.departure.getValue().equals(v_arg) ) {
				neighbor_list.add(e.destination.getValue());
			}
		}
		
		StrV str_key = (StrV)List.List(neighbor_list);
		
		return str_key;
	}
	
	public static Value Print(ArrayList<Value> args) {
		
		String str_arg = ((StrV) args.get(0)).getValue();
		Graph graph = graph_map.get(str_arg);
		
		for (Vertex v : graph.vertex_set) {
			
			System.out.print( v + " : ");
			
			for(Edge e : graph.edge_set) {

				if ( e.departure.getValue().equals(v.getValue()) ) {
					System.out.print(e.destination + "  ");
				}
				
			}
			
			System.out.println();
		}
		
		return null;
	}
	
	public static void notifyFieldAssign(String fieldName) {

	}

	public static void notifyFieldRead(String fieldName) {

	}
	
	// 테스트용 메소드
	public static Value printVertex(ArrayList<Value> args) {
		
		String str;
		
		str = ((StrV)args.get(0)).getValue();
		Graph g = graph_map.get(str);

		Object[] vertex = g.vertex_set.toArray();
		
		for( int i = 0; i < vertex.length; i++ ) {
			System.out.println(vertex[i]) ;
		}
		
		return new StrV();
	}

}

class Vertex {
	
	private Value v;
	
	Vertex (Value v) {
		this.v = v;
	}
	
	public Value getValue() {
		return v;
	}
	
	@Override
	public boolean equals (Object arg) {
		if(arg instanceof Vertex)
			return v.equals(((Vertex)arg).v);
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return (v.toString()).hashCode();
	}
	
	@Override
	public String toString() {
		return v.toString();
	}
	
}

class Edge {
	
	Vertex departure;
	Vertex destination;
	
	Edge (Vertex departure, Vertex destination) {
		this.departure = departure;
		this.destination = destination;
	}
	
	@Override
	public boolean equals (Object arg) {
		if(arg instanceof Edge) {
			boolean comp1 = departure.equals(((Edge)arg).departure);
			boolean comp2 = destination.equals(((Edge)arg).destination);
			return comp1 && comp2;
		}
		else
			return false;
	}
	
	@Override
	public int hashCode() {
		return 31*(departure.hashCode()+destination.hashCode());
	}
	
	@Override
	public String toString() {
		return departure.toString() + "->" + destination.toString();
	}
	
}
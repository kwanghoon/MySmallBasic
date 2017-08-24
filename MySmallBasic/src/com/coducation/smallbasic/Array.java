package com.coducation.smallbasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Array extends Expr
{
	public Array(String variable, ArrayList<Expr> list)
	{
		super();
		var = variable;
		index_list = list;
	
	} // Builder
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public int getDim() {
		return index_list.size();
	}
	public Expr getIndex(int i) {
		return index_list.get(i);
	}
	public ArrayList<Expr> indices() {
		return index_list;
	}
	private String var;
	private ArrayList<Expr> index_list;
}

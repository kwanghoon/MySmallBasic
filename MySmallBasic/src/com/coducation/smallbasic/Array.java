package com.coducation.smallbasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Array extends CondExpr
{
	public Array(String variable, ArrayList<Expr> list)
	{
		super();
		var = variable;
		index_list = list;
	
	} // Builder
	
	private String var;
	private ArrayList<Expr> index_list;
}

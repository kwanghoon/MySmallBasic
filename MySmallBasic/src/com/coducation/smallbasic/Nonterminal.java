package com.coducation.smallbasic;

public class Nonterminal<T> extends Stkelem
{
	public Nonterminal(T tree)
	{
		this.tree = tree;
	}
	
	public T getTree()
	{
		return tree;
	}
	
	private T tree;
}

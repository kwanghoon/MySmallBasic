package com.coducation.smallbasic.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class ArrayStringConversionTest {

	@Test
	public void test1() {
		ArrayV arr = ArrayV.from("0=123;1=456;2=abc;");
		Value v0 = arr.get("0");
		assert(v0 instanceof StrV && "123".equals(((StrV)v0).getValue()));
		Value v1 = arr.get("1");
		assert(v1 instanceof StrV && "456".equals(((StrV)v1).getValue()));
		Value v2 = arr.get("2");
		assert(v2 instanceof StrV && "abc".equals(((StrV)v2).getValue()));
	}
	
	@Test
	public void test2() {
		Value v;
		ArrayV arr = ArrayV.from("0=\\;;1=\\\\;2=\\=;3=\\;ab\\;c\\;def,ghi.jkl;4=\\\\ab\\\\c\\\\;5=\\=ab\\=c\\=;");
		v = arr.get("0");
		assert(v instanceof StrV && ";".equals(((StrV)v).getValue()));
		v = arr.get("1");
		assert(v instanceof StrV && "\\".equals(((StrV)v).getValue()));
		v = arr.get("2");
		assert(v instanceof StrV && "=".equals(((StrV)v).getValue()));
		v = arr.get("3");
		assert(v instanceof StrV && ";ab;c;def,ghi.jkl".equals(((StrV)v).getValue()));
		v = arr.get("4");
		assert(v instanceof StrV && "\\ab\\c\\".equals(((StrV)v).getValue()));
		v = arr.get("5");
		assert(v instanceof StrV && "=ab=c=".equals(((StrV)v).getValue()));		
	}
	
	@Test
	public void test3() {
		ArrayV arr = ArrayV.from("0=0\\=123\\;1\\=456\\;2\\=abc\\;;1=0\\=789\\;1\\=12\\;2\\=345\\;;");
		Value v;
		
		ArrayV arr0 = (ArrayV)arr.get("0");
		
		v = arr0.get("0");
		assert(v instanceof StrV && "123".equals(((StrV)v).getValue()));
		
		v = arr0.get("1");
		assert(v instanceof StrV && "456".equals(((StrV)v).getValue()));
		
		v = arr0.get("2");
		assert(v instanceof StrV && "abc".equals(((StrV)v).getValue()));
		
		ArrayV arr1 = (ArrayV)arr.get("1");
		
		v = arr1.get("0");
		assert(v instanceof StrV && "789".equals(((StrV)v).getValue()));
		
		v = arr1.get("1");
		assert(v instanceof StrV && "12".equals(((StrV)v).getValue()));
		
		v = arr1.get("2");
		assert(v instanceof StrV && "345".equals(((StrV)v).getValue()));
	}

}

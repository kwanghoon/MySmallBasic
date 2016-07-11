package com.coducation.smallbasic;

public enum Token
{
	// (), {}
	OPEN_PARA,
	CLOSE_PARA,
	OPEN_BRACE,
	CLOSE_BRACE,
	
	// . , :
	DOT, // Object.Propertyname of Object.MethodName
	COMMA, // Mathod1(Param1, Param2, ... , Paramn)
	COLON,// Label
	
	// ´ÜÇ× -
	// °ö¼À, ³ª´°¼À
	// µ¡¼À »¬¼À	
	PLUS, // O + O
	MINUS, // O - O
	MULTIPLY, // O * O
	DIVIDE, // O / O
	UNARY_MINUS, // -O
	
	// ³í¸®¿¬»êÀÚ > < >= <= = <>
	LESS, // O < O
	LESS_THAN, // O <= O
	GREATER, // O > O
	GREATER_THAN, // O >= O
	EQUAL, // O = O
	NOT_EQUAL, // O <> O
	
	// ÇÒ´ç¿¬»êÀÚ =
	ASSIGN // Var or Property = expr
}

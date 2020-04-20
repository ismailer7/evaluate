package com.code.evaluate;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

public class EvaluateExpressionTest {

	@Test
	public void evalTest() {
		assertEquals("2", EvaluateExpression.eval("2+(3*4)-12"));
		assertEquals("18", EvaluateExpression.eval("2+(3*4)+(4*1)"));
		assertEquals("3", EvaluateExpression.eval("(((((1+2)))))"));
		assertEquals("18", EvaluateExpression.eval("((1+2)+(4*3)+(5-2))"));
		assertEquals("12", EvaluateExpression.eval("((1+2)+(4*3)+(2-5))"));
		assertEquals("-1", EvaluateExpression.eval("((-5*2)+(4*3)+(2-5))"));
		assertEquals("8", EvaluateExpression.eval("3+(5+(4*3))-12"));
	}

	@Test
	public void calcTest() {
		assertEquals("2", EvaluateExpression.calc("2+3*4-12"));
		assertEquals("-22", EvaluateExpression.calc("2-3*4-12"));
		assertEquals("3", EvaluateExpression.calc("-1*2+5"));
		assertEquals("13", EvaluateExpression.calc("5*2+3/1"));
		assertEquals("17", EvaluateExpression.calc("5*5-2*4"));
		assertEquals("42", EvaluateExpression.calc("6*6+6"));
		assertEquals("-2", EvaluateExpression.calc("-2+3*4-12"));
	}

	@Test
	public void multiplyTest() {
		assertEquals("50", EvaluateExpression.multiply("5*10"));
		assertEquals("24", EvaluateExpression.multiply("1*2*3*4"));
		assertEquals("50", EvaluateExpression.multiply("-5*-10"));
		assertEquals("50", EvaluateExpression.multiply("5*10"));
		assertEquals("-50", EvaluateExpression.multiply("-5*-10*-1"));
	}

	@Test
	public void divisionTest() {
		assertEquals("2", EvaluateExpression.division("10/5"));
		assertEquals("1", EvaluateExpression.division("10/10/1"));
	}

	@Test
	public void additionTest() {
		assertEquals("18", EvaluateExpression.addition("5+10+2+1"));
		assertEquals("-8", EvaluateExpression.addition("-10+2"));
	}

	@Test
	public void subTest() {
		assertEquals("0", EvaluateExpression.sub("1-1"));
		assertEquals("0", EvaluateExpression.sub("10-2-2-2-2-2"));
		assertEquals("-8", EvaluateExpression.sub("-5-2-1"));
	}

	@Test
	public void adaptTest() {
		assertEquals("1-2", EvaluateExpression.adapt("1-+2"));
		assertEquals("1+2", EvaluateExpression.adapt("1--2"));
	}

	@Test
	public void exprGatherTest() {
		Stack<String> stack = new Stack<String>();
		stack.push("2");
		stack.push("+");
		stack.push("4");
		stack.push("+");
		stack.push("10");
		// expected expression is 2+4+10
		assertEquals("2+4+10", EvaluateExpression.exprGather(stack));
	}
}

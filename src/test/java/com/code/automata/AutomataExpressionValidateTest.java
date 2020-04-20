package com.code.automata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AutomataExpressionValidateTest {

	@Test
	public void validateExpressionTest() {
		assertTrue(AutomataExpressionValidate.validateExpression("((-5*2)+(4*3)+(2-5))"));
		assertFalse(AutomataExpressionValidate.validateExpression("3 +((((5+ 2))"));
		assertTrue(AutomataExpressionValidate.validateExpression("3+ ((5 +2))"));
		assertTrue(AutomataExpressionValidate.validateExpression("3+((5+2)+(1*4))"));
		assertFalse(AutomataExpressionValidate.validateExpression("3+((((5+2))"));
		assertTrue(AutomataExpressionValidate.validateExpression("3+(5+(12))-12"));
		assertTrue(AutomataExpressionValidate.validateExpression("1+2*5-4+88/2"));
		assertFalse(AutomataExpressionValidate.validateExpression("3+"));
		assertFalse(AutomataExpressionValidate.validateExpression("+88"));
		assertFalse(AutomataExpressionValidate.validateExpression("5/0"));
	}

	@Test
	public void extractParenthesesTest() {
		assertEquals("((()", AutomataExpressionValidate.extractParentheses("((()"));
		assertEquals("((())))))", AutomataExpressionValidate.extractParentheses("((())))))"));
		assertEquals("((()()", AutomataExpressionValidate.extractParentheses("(((2+4)+()"));
	}

	@Test
	public void isValideParenthesesTest() {
		assertFalse(AutomataExpressionValidate.isValideParentheses(")()()()()()"));
		assertTrue(AutomataExpressionValidate.isValideParentheses("(((())))"));
		assertFalse(AutomataExpressionValidate.isValideParentheses("(((()))))"));
		assertFalse(AutomataExpressionValidate.isValideParentheses("(((()))))("));
	}

	@Test
	public void isDigitTest() {
		// non-sign digit: for my case i treat every single character
		// so I'm not concerned about digit more the one character this will be handled
		// in the automata
		assertTrue(AutomataExpressionValidate.isDigit("5"));
		assertTrue(AutomataExpressionValidate.isDigit("0"));
		assertTrue(AutomataExpressionValidate.isDigit("1"));
		assertTrue(AutomataExpressionValidate.isDigit("2"));
	}

	@Test
	public void cleanTest() {
		assertEquals("3+((((5+2))", AutomataExpressionValidate.clean("3 +((((5+ 2))"));
		assertEquals("3+((((5+2))", AutomataExpressionValidate.clean("3 +( ( ((5+   2)   )"));
		assertEquals("3+((((5+2))", AutomataExpressionValidate.clean("3 +( ( ((5 + 2) )"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void zeroDivisionValidationTest() {
		AutomataExpressionValidate.zeroDivisionValidation("1+5/0");
	}
}

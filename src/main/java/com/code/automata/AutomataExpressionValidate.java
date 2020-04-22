package com.code.automata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutomataExpressionValidate {

	static List<String> states = Arrays.asList("q0", "q1", "q2", "q3", "q4");
	static Map<String, Integer> transition = new HashMap<String, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("+", 0);
			put("-", 1);
			put("*", 2);
			put("/", 3);
			put(")", 4);
			put("(", 5);
			put("digit", 6);
		}
	};
	static List<Integer> finiteStates = Arrays.asList(1, 3); // expected finite state in case we have valid expression is q1 or q3
	
	static String[][] automata = { { "", "", "", "", "", "q2", "q1" }, { "q2", "q2", "q2", "q2", "q3", "", "q1" },
			{ "q1", "q1", "q1", "q1", "", "q2", "q1" }, { "q0", "q0", "q0", "q0", "q3", "", "" } };

	/**
	 * TODO  - add the Error tracking to inform user/ help him recognize where the expression goes wrong
	 */
	
	public static boolean validateExpression(String expression) {
		// 1- check parentheses validation
		// -> extract the parentheses
		String parentheses = extractParentheses(expression);
		// -> validate the parentheses order
		if (!isValideParentheses(parentheses)) {
			return false;
		}
		try {
			zeroDivisionValidation(expression);
		} catch (Exception e) {
			return false;
		}
		// 2- validate the expression upon the automata
		// -> remove the spaces between each character
		expression = clean(expression);
		// example -> "5+(1+2) - 2" -> "5+(1+2)-2" then validate it.
		int currentState = 0;
		int init = 0;
		while (init < expression.length()) {
			String currentTransition = expression.charAt(init) + "";
			// check if the transition is a number
			String outputState = null;
			outputState = isDigit(currentTransition) ? automata[currentState][transition.get("digit")]
					: automata[currentState][transition.get(currentTransition)];
			if (outputState.isEmpty()) {
				return false;
			}
			currentState = states.indexOf(outputState);
			init++;
		}
		if (finiteStates.contains(currentState)) {
			return true;
		}
		return false;
	}

	public static boolean isValideParentheses(String parentheses) {
		try {
			if (parentheses.startsWith(")")) {
				throw new ExpressionException(String.format("Invalid Expression \"%s\" -> start with an enclosing parenthese", parentheses));
			}
			Stack<Character> myStack = new Stack<Character>();
			int init = 0;
			int errorTrack = 0;
			while (init < parentheses.length()) {
				if (myStack.isEmpty()) {
					myStack.push(parentheses.charAt(init));
					errorTrack ++;
				} else {
					// get the element at the top
					char top = myStack.peek();
					if (top == '(' && parentheses.charAt(init) == ')') {
						myStack.pop();
						errorTrack --;
					} else {
						myStack.push(parentheses.charAt(init));
						errorTrack ++;
					}
				}
				init++;
			}
			if(!myStack.isEmpty()) throw new ExpressionException(String.format("Invalid Expression \"%s\" -> Parentheses combination no closing parenthese for \"%s\" at position [%d] ",parentheses, parentheses.charAt(errorTrack), errorTrack));
		} catch(RuntimeException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public static String extractParentheses(String expression) {
		Pattern pattern = Pattern.compile("(\\)|\\()");
		Matcher matcher = pattern.matcher(expression);
		StringBuilder sb = new StringBuilder("");
		while (matcher.find()) {
			sb.append(matcher.group());
		}
		return sb.toString();
	}

	public static boolean isDigit(String number) {
		Pattern pattern = Pattern.compile("\\d");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}

	public static String clean(String expression) {
		return expression.replaceAll("\\s", "");
	}

	public static void zeroDivisionValidation(String expression) {
		if (expression.contains("/0")) {
			throw new IllegalArgumentException("Argument 'divisor' is 0");
		}
	}

}

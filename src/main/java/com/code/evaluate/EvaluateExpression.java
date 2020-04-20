package com.code.evaluate;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.code.automata.AutomataExpressionValidate;

public class EvaluateExpression {

	public static String eval(String expression) {

		Stack<String> stack = new Stack<String>();
		// 1- check if the expression is valid
		StringBuilder sb = new StringBuilder("");
		if (AutomataExpressionValidate.validateExpression(expression)) {
			int init = 0;
			while (init < expression.length()) {

				if (expression.charAt(init) == ')') {
					sb.setLength(0); // clear the string
					// sb.append(")");
					Stack<String> reverseStack = new Stack<String>();
					while (!stack.peek().equals("(")) {
//						sb.append(stack.pop());
						reverseStack.push(stack.pop());
					}
					// sb.append(stack.pop());
					stack.pop();
					// sb = sb.reverse();
					String result = calc(exprGatherReverse(reverseStack));
					stack.push(result);
				} else if ((expression.charAt(init) + "").matches("\\d")) {
					int at = init;
					StringBuilder number = new StringBuilder("");
					while (at < expression.length() && (expression.charAt(at) + "").matches("\\d")) {
						number.append(expression.charAt(at) + "");
						at++;
					}
					if (number.length() > 1) {
						stack.push(number.toString());
						init = at - 1;
					} else {
						stack.push(number.toString());
					}
				} else {
					stack.push(expression.charAt(init) + "");
				}
				init++;
			}
		}
		// get the left expression from the stack
		return calc(exprGather(stack));
	}

	public static String exprGather(Stack<String> s) {
		String[] exprList = new String[s.size()];
		int size = s.size();
		for (int i = size - 1; i >= 0; i--) {
			exprList[i] = s.pop();
		}
		return String.join("", exprList);
	}

	public static String exprGatherReverse(Stack<String> s) {
		String[] exprList = new String[s.size()];
		int size = s.size();
		for (int i = 0; i < size; i++) {
			exprList[i] = s.pop();
		}
		return String.join("", exprList);
	}

	public static String calc(String expr) {
		// respect the order
		expr = adapt(expr);
		expr = multiply(expr);
		expr = division(expr);
		expr = addition(expr);
		expr = sub(expr);

		return expr;
	}

	public static String adapt(String expr) {
		// +- or -+ -> -
		// -- -> +
		return expr.replaceAll("\\+\\-", "-").replaceAll("\\-\\+", "-").replaceAll("\\-\\-", "+");
	}

	public static String multiply(String expr) {
		Pattern pattern = null;
		Matcher matcher = null;
		// priorities : * -> / -> + -> -
		pattern = Pattern.compile("(?<left>-?\\d+)\\*(?<right>-?\\d+)");
		matcher = pattern.matcher(expr);

		if (matcher.find()) {
			int left = Integer.valueOf(matcher.group("left"));
			int right = Integer.valueOf(matcher.group("right"));
			int result = left * right;
			String outcome = matcher.replaceFirst(String.valueOf(result));
			return multiply(outcome);
		} else {
			return expr;
		}
	}

	public static String division(String expr) {
		Pattern pattern = null;
		Matcher matcher = null;
		// priorities : * -> / -> + -> -
		pattern = Pattern.compile("(?<left>-?\\d+)\\/(?<right>-?\\d+)");
		matcher = pattern.matcher(expr);

		if (matcher.find()) {
			int left = Integer.valueOf(matcher.group("left"));
			int right = Integer.valueOf(matcher.group("right"));
			int result = left / right;
			String outcome = matcher.replaceFirst(String.valueOf(result));
			return division(outcome);
		} else {
			return expr;
		}
	}

	public static String addition(String expr) {
		Pattern pattern = null;
		Matcher matcher = null;
		// priorities : * -> / -> + -> -
		pattern = Pattern.compile("(?<left>-?\\d+)\\+(?<right>\\d+)");
		matcher = pattern.matcher(expr);

		if (matcher.find()) {
			int left = Integer.valueOf(matcher.group("left"));
			int right = Integer.valueOf(matcher.group("right"));
			int result = left + right;
			String outcome = matcher.replaceFirst(String.valueOf(result));
			return addition(outcome);
		} else {
			return expr;
		}
	}

	public static String sub(String expr) {
		Pattern pattern = null;
		Matcher matcher = null;
		// priorities : * -> / -> + -> -
		pattern = Pattern.compile("(?<left>-?\\d+)\\-(?<right>\\d+)");
		matcher = pattern.matcher(expr);

		if (matcher.find()) {
			int left = Integer.valueOf(matcher.group("left"));
			int right = Integer.valueOf(matcher.group("right"));
			int result = left - right;
			String outcome = matcher.replaceFirst(String.valueOf(result));
			return sub(outcome);
		} else {
			return expr;
		}
	}

}

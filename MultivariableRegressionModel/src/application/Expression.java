package application;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/*
 * THIS CLASS WAS ORGINALLY CREATED FOR THE PURPOSES OF AN ACADEMIC ASSIGNMENT
 *  USING VARIABLES BUT THIS IS THE MODIFIED VERSION TO BE SUITABLE FOR OUR 
 *  PURPOSES. SORRY FOR THE LACK OF ANNOTATIONS. THIS IS NOT MEANT TO BE A 
 *  MAIN FEAUTURE REGARDLESS. THANK YOU!
 */

class GenNode<Item> {
	Item item;
	GenNode<Item> next;
}

class Stack<Item> implements Iterable<Item> {
	private GenNode<Item> first; // top of stack
	private int n; // size of the stack

	public Stack() {
		first = null;
		n = 0;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return n;
	}

	public void push(Item item) {
		GenNode<Item> oldfirst = first;
		first = new GenNode<Item>();
		first.item = item;
		first.next = oldfirst;
		n++;
	}

	public Item pop() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		Item item = first.item;
		first = first.next;
		n--;
		return item;
	}

	public Item peek() {
		if (isEmpty())
			throw new NoSuchElementException("Stack underflow");
		return first.item;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item item : this) {
			s.append(item);
			s.append(' ');
		}
		return s.toString();
	}

	@Override
	public Iterator<Item> iterator() {
		return null;
	}

}

public class Expression {
	public static String delims = " \t*+-/()[]";

	public static float evaluate(String expr) {
		// printVarsNArrays(vars, arrays);
		Stack<String> operations = new Stack<>();
		Stack<Float> numbers = new Stack<>();
		StringTokenizer splitTokens = new StringTokenizer(expr, delims, true);

		while (splitTokens.hasMoreTokens()) {
			String curr = splitTokens.nextToken();
			if (curr.equals("\t") || curr.equals(" ")) {
			} else if (isNumber(curr)) {
				numbers.push(Float.parseFloat(curr));
				continue;
			} else if (curr.equals("(")) {
				operations.push("(");
			} else if (curr.equals(")")) {
				while (operations.peek() != "(") {
					String sign = operations.pop();
					float temp1 = numbers.pop();
					float temp2 = numbers.pop();
					numbers.push(calculations(temp1, temp2, sign));
				}
				operations.pop();
			} else if (isOperator(curr)) {
				while (operations.isEmpty() != true && Operations(curr, operations.peek())) {
					String sign = operations.pop();
					float temp1 = numbers.pop();
					float temp2 = numbers.pop();
					numbers.push(calculations(temp1, temp2, sign));

				}
				operations.push(curr);
			}
		}
		while (operations.isEmpty() != true) {
			float popFirst = numbers.pop();
			float popSecond = numbers.pop();
			String sign = operations.pop();
			numbers.push(calculations(popFirst, popSecond, sign));
		}
		return numbers.pop();
	}

	private static boolean isNumber(String test) {
		for (int i = 0; i < test.length(); i++) {
			if (!(test.charAt(i) >= '0' && test.charAt(i) <= '9') && !(test.charAt(i) == '.')) {
				return false;
			}
		}
		return true;
	}

	private static boolean Operations(String curr, String curr1) {
		if (curr.equals("/") || (curr.equals("*")) && (curr1.equals("+") || curr1.equals("-"))) {
			return false;
		} else if (curr1.equals("[")) {
			return false;
		} else if (curr1.equals("(")) {
			return false;
		}
		return true;
	}

	private static boolean isOperator(String test) {
		if (test.equals("+") || test.equals("*") || test.equals("/") || test.equals("-")) {
			return true;
		}
		return false;
	}

	private static float calculations(float num1, float num2, String test) {
		if (test.equals("*")) {
			return num1 * num2;
		} else if (test.equals("/")) {
			return num2 / num1;
		} else if (test.equals("-")) {
			return num2 - num1;
		} else {
			return num1 + num2;
		}
	}
	
}

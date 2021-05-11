package calculator;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class VariableManager {
	private final Map<String, BigInteger> variables = new HashMap<>();
	private static final ReentrantLock lock = new ReentrantLock();
	
	private static VariableManager instance;
	
	private VariableManager () { 
    } 
	
    public static VariableManager getInstance() {
        if (instance == null) {
        	lock.lock();
        	// Double check that another thread did not create an instance during the time it took to lock this thread.
        	if (instance == null) {
        		instance = new VariableManager();
            }
        	lock.unlock();
        }
        return instance;
    }
	
	public void addVariable(String input) {
		// As splitting at the =, need to remove whitespace
		String whitespaceRegex = "(\\s+)";
		input = input.replaceAll(whitespaceRegex, "");
		// Split string into before/after the = sign
		String[] parts = input.split("=");
		String variableName = parts[0];
		BigInteger value = getVariableOrValue(parts[1]);
		String charactersRegex = "[a-zA-Z]+";
		if (!variableName.matches(charactersRegex)) {
			System.out.println("Invalid identifier");
			return;
		}
		if (value == null || parts.length > 2) {
			System.out.println("Invalid assignment");
			return;
		}
		variables.put(variableName, value); // either converts to int, or retrieves variable value
		System.out.println("Variable '" + variableName + "' with value '" + value + "' stored in memory.");
	}
	
	public BigInteger getVariableOrValue(String str) {
		if (variables.containsKey(str)) {
			return new BigInteger(variables.get(str).toString());
		}
		if (StringValidator.isNumber(str)) {
			return new BigInteger(str);
		}
		return null; // If the string is not a variable or a valid number
	}
	
    public String[] convertVariablesToValues(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            if (variables.containsKey(parts[i])) {
                // Convert variables to their numerical values
                if (this.getVariableOrValue(parts[i]) != null) {
                    parts[i] = this.getVariableOrValue(parts[i]).toString();
                }
            }
        }
        return parts;
    }
	
}

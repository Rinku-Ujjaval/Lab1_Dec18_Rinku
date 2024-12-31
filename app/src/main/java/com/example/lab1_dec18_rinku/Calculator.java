package com.example.lab1_dec18_rinku;
import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private List<String> inputs;
    private boolean isAdvancedMode = false;
    private List<String> historyList;   // Stores calculation history
    public Calculator() {
        inputs = new ArrayList<>();
        historyList = new ArrayList<>();
        isAdvancedMode = false; // Default to standard mode
    }
    public void push(String value) {
        inputs.add(value);
    }

    public int calculate() {
        if (inputs.isEmpty()) return 0;

        int result = Integer.parseInt(inputs.get(0));

        for (int i = 1; i < inputs.size(); i += 2) {
            String operator = inputs.get(i);
            int operand = Integer.parseInt(inputs.get(i + 1));

            switch (operator) {
                case "+":
                    result += operand;
                    break;
                case "-":
                    result -= operand;
                    break;
                case "*":
                    result *= operand;
                    break;
                case "/":
                    if (operand != 0) {
                        result /= operand;
                    } else {
                        throw new ArithmeticException("Division by zero");
                    }
                    break;
            }
        }

        if (isAdvancedMode) {
            historyList.add(String.join(" ", inputs) + " = " + result);
        }

        inputs.clear(); // Clear the list after calculation
        inputs.add(String.valueOf(result)); // Store result for further calculations
        return result;
    }
    // Retrieve the calculation history
    public String getHistory() {
        return String.join("\n", historyList);
    }

    // Clear history
    public void clearHistory() {
        historyList.clear();
    }


    public void toggleMode() {
        isAdvancedMode = !isAdvancedMode;
    }

    public boolean isAdvancedMode() {
        return isAdvancedMode;
    }

}

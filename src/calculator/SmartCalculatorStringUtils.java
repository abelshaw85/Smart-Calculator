package calculator;

public class SmartCalculatorStringUtils {
    
    // Add spacing around =, (, and ), then remove excess whitespace
    public static String[] expressionToArray(String line) {
        StringBuilder formattedLine = new StringBuilder();
        // Characters that will have white space added around where the format is without it e.g. 10+5
        String charactersToPad = "=()*/";
        // + and - can be a continuous string e.g. +++ or ---
        String charactersToBlockPad = "+-";
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (charactersToPad.indexOf(c) >= 0) {
                formattedLine.append(" ");
                formattedLine.append(c);
                formattedLine.append(" ");
            } else if (charactersToBlockPad.indexOf(c) >= 0) {
                formattedLine.append(" ");
                formattedLine.append(c);
                while (line.charAt(i + 1) == c) {
                    formattedLine.append(c);
                    i++;
                }
                formattedLine.append(" ");
            } else {
                formattedLine.append(line.charAt(i));
            }
        }
        // Remove excess whitespace
        String whitespaceRegex = "(\\s+)";
        line = formattedLine.toString().replaceAll(whitespaceRegex, " ");
        line = line.trim();

        return line.split(" ");
    }
}

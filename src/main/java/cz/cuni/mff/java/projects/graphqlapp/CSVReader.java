package cz.cuni.mff.java.projects.graphqlapp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private final List<List<String>> lines = new ArrayList<>();
    private final InputStreamReader inputReader;
    private boolean lineEnded = false;
    private boolean streamEnded = false;

    public CSVReader(InputStreamReader inReader) throws IOException {
        inputReader = inReader;
        while(!streamEnded) {
            lines.add(getNextLine());
        }

    }

    private List<String> getNextLine() throws IOException {
        lineEnded = false;
        ArrayList<String> nextLine = new ArrayList<>();
        String nextField = getNextField();
        while (nextField != null) {
            nextLine.add(nextField.trim());
            nextField = getNextField();
        }
        return nextLine;
    }

    private String getNextField() throws IOException {
        if (lineEnded) {
            return null;
        }

        StringBuilder nextField = new StringBuilder();
        int next = inputReader.read();
        boolean inQuotes = false;
        while (next != '\n' && next != -1) {
            switch (next) {
                case ',' -> {
                    if(!inQuotes) {
                        return nextField.toString();
                    }
                    nextField.append(',');
                }
                case '"' -> {
                    if(!inQuotes && nextField.length() != 0) {
                        throw new IllegalArgumentException("Incorrect CSV file format!");
                    } else if (!inQuotes) {
                        inQuotes = true;
                    } else {
                        next = inputReader.read();
                        if(next != ',') {
                            throw new IllegalArgumentException("Incorrect CSV file format!");
                        }
                        return nextField.toString();
                    }
                }
                default -> nextField.append((char) next);
            }
            next = inputReader.read();
        }
        lineEnded = true;
        if (next == -1) {
            streamEnded = true;
        }
        return nextField.toString();
    }

    public List<List<String>> getLines() {
        return lines;
    }
}

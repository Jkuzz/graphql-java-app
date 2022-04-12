package cz.cuni.mff.java.projects.graphqlapp.csv;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class CSVReader {
    private final List<List<String>> lines = new ArrayList<>();
    private final Reader inputReader;
    private boolean lineEnded = false;
    private boolean streamEnded = false;

    public List<String> getHeader() {
        assert lines.size() > 0;
        return lines.get(0);
    }

    public CSVReader(Reader inReader) throws IOException {
        inputReader = inReader;
        while(!streamEnded) {
            List<String> nextLine = getNextLine();
            if (nextLine != null && nextLine.size() != 0) {
                lines.add(nextLine);
            }
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
        if(lines.size() > 0 && nextLine.size() < lines.get(0).size()) {
            return null;  // Do not add lines that have fewer fields than the header
        }
        return nextLine;
    }

    private @Nullable String getNextField() throws IOException {
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
                        if(next == ',') {
                            return nextField.toString();
                        } else if(next == '\n' || next == '\r') {
                            lineEnded = true;
                            return nextField.toString();
                        } else if(next == -1) {
                            lineEnded = streamEnded = true;
                            return nextField.toString();
                        }
                        nextField.append('"');
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
        return lines.subList(1, lines.size());
    }

    public List<Map<String, String>> getLinesAsMaps() {
        List<Map<String, String>> lineMaps = new ArrayList<>();
        List<String> header = getHeader();

        for(List<String> line: getLines()) {
            Map<String, String> outMap = new TreeMap<>();
            for(int i=0; i<line.size(); i+=1) {
                outMap.put(header.get(i), line.get(i));
            }
            if(outMap.size() < 10) {
                System.out.println(outMap);
            }
            lineMaps.add(outMap);
        }
        return lineMaps;
    }

    public List<String> getLine(int index) {
        if (index > lines.size()) {
            return null;
        }
        return lines.get(index);
    }
}

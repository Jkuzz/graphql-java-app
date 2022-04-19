package cz.cuni.mff.java.projects.graphqlapp.csv;

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

    /**
     * Reads the input reader and parses legal lines in the csv file into list of Strings
     * @param inReader Reader containing the csv input
     * @throws IOException reader IO fails
     */
    public CSVReader(Reader inReader) throws IOException {
        inputReader = inReader;
        while(!streamEnded) {
            List<String> nextLine = getNextLine();
            if (nextLine != null && nextLine.size() != 0) {
                lines.add(nextLine);
            }
        }
    }

    /**
     * Reads csv fields and if line length matches header length, returns the line
     * @return next valid csv line as list
     * @throws IOException reader IO fails
     */
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

    /**
     * Parses next csv field from input reader. Respects double-quoted fields.
     * @return next field
     * @throws IOException reader IO fails
     * @throws IllegalArgumentException csv file has incorrect format
     */
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

    /**
     * Returns parsed csv lines without header.
     * @return List of all lines
     */
    public List<List<String>> getLines() {
        return lines.subList(1, lines.size());
    }

    /**
     * Converts parsed csv file into a list of maps, one map for each csv line
     * maps use csv header as keys and the column contents as values
     * @return List of lines converted to maps
     */
    public List<Map<String, String>> getLinesAsMaps() {
        List<Map<String, String>> lineMaps = new ArrayList<>();
        List<String> header = getHeader();

        for(List<String> line: getLines()) {
            Map<String, String> outMap = new TreeMap<>();
            for(int i=0; i<line.size(); i+=1) {
                outMap.put(header.get(i), line.get(i));
            }
            lineMaps.add(outMap);
        }
        return lineMaps;
    }

    /**
     * Return parsed line at selected index. For header, use getHeader instead!
     * @param index line no. to get
     * @return The selected line
     */
    public List<String> getLine(int index) {
        if (index + 1 > lines.size()) {
            return null;
        }
        return lines.get(index + 1);
    }
}

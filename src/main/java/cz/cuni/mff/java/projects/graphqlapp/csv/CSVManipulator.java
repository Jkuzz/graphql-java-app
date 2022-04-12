package cz.cuni.mff.java.projects.graphqlapp.csv;

import java.util.ArrayList;
import java.util.List;

public class CSVManipulator {
    public static List<String> getColumn(String colName, List<String> header, List<List<String>> lines) {
        int colIndex = header.indexOf(colName);
        if(colIndex == -1) {
            return null;
        }
        List<String> column = new ArrayList<>();
        for(List<String> line: lines) {
            column.add(line.get(colIndex));
        }
        return column;
    }
}

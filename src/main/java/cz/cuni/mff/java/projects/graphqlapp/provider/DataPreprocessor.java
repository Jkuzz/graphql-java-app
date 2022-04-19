package cz.cuni.mff.java.projects.graphqlapp.provider;

import graphql.com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataPreprocessor {
    /**
     * Transforms input CSV lines maps according to the provided fields transformation map
     * @param originalCSVMap CSV lines as maps column_name:field
     * @param fieldsDict map of new_col_name:old_col_name
     * @return original line transformed according to fieldsDict
     */
    public static List<Map<String, String>> PrepareData(List<Map<String, String>> originalCSVMap, Map<String, String> fieldsDict) {
        List<Map<String, String>> outMaps = new ArrayList<>();

        for(Map<String, String> line: originalCSVMap) {
            Map<String, String> newMap = new HashMap<>();

            for(Map.Entry<String, String> fieldToAdd: fieldsDict.entrySet()) {
                newMap.put(fieldToAdd.getKey(), line.get(fieldToAdd.getValue()));
            }
            outMaps.add(newMap);
        }
        return outMaps;
    }

    public static Map<String, String> PrepareBinding(List<Map<String, String>> bindingMap) {
        Map<String, String> outMap = new HashMap<>();
        for(Map<String, String> bind: bindingMap) {
            outMap.put(bind.get("CHODNOTA2"), bind.get("CHODNOTA1"));
        }
        return outMap;
    }
}

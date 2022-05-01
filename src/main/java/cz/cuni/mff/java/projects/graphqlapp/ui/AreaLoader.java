package cz.cuni.mff.java.projects.graphqlapp.ui;

import graphql.ExecutionResult;
import graphql.GraphQL;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public record AreaLoader(GraphQL graphQL) {

    /**
     * Endpoint for loading area lists. For selected area type, fetches
     * list of available entries from the database.
     *
     * @param type which type of area to query
     * @return AreaListItems available in database
     */
    public ArrayList<AreaListItem> loadArea(AreaType type) {
        ArrayList<AreaListItem> loadedList = new ArrayList<>();
        String queryType;
        switch (type) {
            case KRAJE -> queryType = "kraje";
            case OKRESY -> queryType = "okresy";
            case OBCE -> queryType = "obce";
            default -> queryType = "";
        }

        ExecutionResult result = graphQL.execute("{" + queryType + "{name id}}");
        LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> kraje = result.getData();
        for (Map<String, String> kraj : kraje.get(queryType)) {
            loadedList.add(new AreaListItem(kraj.get("name"), kraj.get("id"), type));
        }
        return loadedList;
    }
}

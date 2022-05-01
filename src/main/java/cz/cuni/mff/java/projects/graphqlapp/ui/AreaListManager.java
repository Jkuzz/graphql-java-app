package cz.cuni.mff.java.projects.graphqlapp.ui;

import graphql.GraphQL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Simulates ArrayList adding and removing functionality, but manages one arraylist for each AreaType.
 * To use the underlying types, use `getListByType()`
 */
public class AreaListManager {
    private final Map<AreaType, ArrayList<AreaListItem>> areaLists = new HashMap<>();

    /**
     * Uses AreaLoader to load known areas items from database and places them in map storage.
     * These will be used in AreaPanel when switching between area types.
     * Allows for easy adding and removing of items from the store.
     * @param graphQL database instance
     */
    public AreaListManager(GraphQL graphQL) {
        AreaLoader areaLoader = new AreaLoader(graphQL);
        for(AreaType type: AreaType.values()) {
            areaLists.put(type, areaLoader.loadArea(type));
        }
    }

    /**
     * Get list of AreaListItems according to their AreaType
     * @param type which codebook type to get
     * @return list of items in storage
     */
    public ArrayList<AreaListItem> getListByType(AreaType type) {
        return areaLists.get(type);
    }

    /**
     * Add AreaListItem to the managed storage
     * @param areaItem to add
     */
    public void add(AreaListItem areaItem) {
        areaLists.get(areaItem.areaType()).add(areaItem);
    }

    /**
     * Remove AreaListItem from the managed storage
     * @param areaItem to remove
     */
    public void remove(AreaListItem areaItem) {
        areaLists.get(areaItem.areaType()).remove(areaItem);
    }
}

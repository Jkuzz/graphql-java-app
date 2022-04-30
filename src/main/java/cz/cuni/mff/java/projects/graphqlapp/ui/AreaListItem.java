package cz.cuni.mff.java.projects.graphqlapp.ui;

/**
 * Stores data about area to allow it to be queried for in database
 * as well as application lists.
 *
 * Use this in JList to allow user to select areas.
 * Use id to query database
 */
public record AreaListItem(String name, String id, AreaType areaType) {
    @Override
    public String toString() {
        return name();
    }
}

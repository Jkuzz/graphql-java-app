package cz.cuni.mff.java.projects.graphqlapp.ui;

public record AreaListItem(String name, String id) {
    @Override
    public String toString() {
        return name();
    }
}

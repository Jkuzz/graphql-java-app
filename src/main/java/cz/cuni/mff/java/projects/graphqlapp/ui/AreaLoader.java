package cz.cuni.mff.java.projects.graphqlapp.ui;

import java.util.ArrayList;

public class AreaLoader {

    /**
     * Endpoint for loading area lists. For selected area type, fetches
     * list of available entries from the database.
     * @param type which type of area to query
     * @return AreaListItems available in database
     */
    public ArrayList<AreaListItem> loadArea(AreaType type) {
        ArrayList<AreaListItem> loadedList = new ArrayList<>();
        switch (type) {
            case KRAJE -> loadKraje(loadedList);
            case OKRESY -> loadOkresy(loadedList);
            case OBCE -> loadObce(loadedList);
        }
        return loadedList;
    }

    private void loadKraje(ArrayList<AreaListItem> loadedList) {
        loadedList.clear();
        for (int i=0; i<=15; i+=1) {
            loadedList.add(new AreaListItem("Kraj " + i, ""+i, AreaType.KRAJE));
        }
    }

    private void loadOkresy(ArrayList<AreaListItem> loadedList) {
        loadedList.clear();
        for (int i=0; i<=50; i+=1) {
            loadedList.add(new AreaListItem("Okres " + i, ""+i, AreaType.OKRESY));
        }
    }

    private void loadObce(ArrayList<AreaListItem> loadedList) {
        loadedList.clear();
        for (int i=0; i<=200; i+=1) {
            loadedList.add(new AreaListItem("Obec " + i, ""+i, AreaType.OBCE));
        }
    }
}

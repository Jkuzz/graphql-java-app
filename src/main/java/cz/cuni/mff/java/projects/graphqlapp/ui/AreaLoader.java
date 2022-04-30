package cz.cuni.mff.java.projects.graphqlapp.ui;

import java.util.ArrayList;

public class AreaLoader {
    public enum AreaType {
        KRAJE, OKRESY, OBCE
    }

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
            loadedList.add(new AreaListItem("Kraj " + i, ""+i));
        }
    }

    private void loadOkresy(ArrayList<AreaListItem> loadedList) {
        loadedList.clear();
        for (int i=0; i<=50; i+=1) {
            loadedList.add(new AreaListItem("Okres " + i, ""+i));
        }
    }

    private void loadObce(ArrayList<AreaListItem> loadedList) {
        loadedList.clear();
        for (int i=0; i<=200; i+=1) {
            loadedList.add(new AreaListItem("Obec " + i, ""+i));
        }
    }
}

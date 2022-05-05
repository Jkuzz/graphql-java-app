package cz.cuni.mff.java.projects.graphqlapp.provider;

import graphql.schema.DataFetcher;

import java.util.List;
import java.util.Map;

/**
 * Provides DataFetchers used to create RuntimeWiring. DataFetchers fetch from the DataStore instance.
 */
public class GraphQLDataFetchers {

    private final GraphQLDataStore dataStore = new GraphQLDataStore();

    /**
     * Gets all kraje from the DataStore.
     * @return All kraje.
     */
    public DataFetcher<List<Map<String, String>>> getKrajeDataFetcher() {
        return dataFetchingEnvironment -> dataStore.getKraje();
    }

    /**
     * Gets all okresy from the DataStore.
     * @return All okresy.
     */
    public DataFetcher<List<Map<String, String>>> getOkresyDataFetcher() {
        return dataFetchingEnvironment -> dataStore.getOkresy();
    }

    /**
     * Gets all obce from the DataStore.
     * @return All obce.
     */
    public DataFetcher<List<Map<String, String>>> getObceDataFetcher() {
        return dataFetchingEnvironment -> dataStore.getObce();
    }

    /**
     * DataFetcher for Obec or Okres to get its Kraj by krajId
     * @return the parent kraj map
     */
    public DataFetcher<Map<String, String>> getKrajDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> kraj = dataFetchingEnvironment.getSource();
            String krajId = kraj.get("krajId");
            return dataStore.getById(krajId, dataStore.getKraje());
        };
    }

    /**
     * DataFetcher for Obec to get its Okres by okresIdId
     * @return the parent okres map
     */
    public DataFetcher<Map<String, String>> getOkresDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> okres = dataFetchingEnvironment.getSource();
            String okresId = okres.get("okresId");
            return dataStore.getById(okresId, dataStore.getOkresy());
        };
    }

    /**
     * Fetches DataStore for area map with the given ID.
     * @param listToGet Type of area to fetch from
     * @return DataFetcher
     */
    public DataFetcher<Map<String, String>> getByIdDataFetcher(String listToGet) {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            switch (listToGet) {
                case "kraj" -> { return dataStore.getById(id, dataStore.getKraje()); }
                case "okres" -> { return dataStore.getById(id, dataStore.getOkresy()); }
                case "obec" -> { return dataStore.getById(id, dataStore.getObce()); }
            }
            return null;
        };
    }

    /**
     * Fetches demographic data for given codebook by id from the DataStore.
     * @param codebook which codebook the demographic data are from
     * @return List of available demographics maps
     */
    public DataFetcher<List<Map<String, String>>> getDemsDataFetcher(String codebook) {
        return dataFetchingEnvironment -> {
            Map<String,String> source = dataFetchingEnvironment.getSource();
            String sourceId = source.get("id");
            return dataStore.getDemographics(codebook, sourceId);
        };
    }

}
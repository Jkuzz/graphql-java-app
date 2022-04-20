package cz.cuni.mff.java.projects.graphqlapp.provider;

import cz.cuni.mff.java.projects.graphqlapp.Main;
import cz.cuni.mff.java.projects.graphqlapp.csv.CSVReader;
import graphql.com.google.common.collect.ImmutableMap;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class GraphQLDataStore {
    public List<Map<String, String>> getKraje() {
        return kraje;
    }

    public List<Map<String, String>> getOkresy() {
        return okresy;
    }

    public List<Map<String, String>> getObce() {
        return obce;
    }

    private final List<Map<String, String>> obce;
    private final List<Map<String, String>> okresy;
    private final List<Map<String, String>> kraje;

    public GraphQLDataStore() {
        kraje = renameCSV("CIS0100_CS.csv", ImmutableMap.of(
                "id", "CHODNOTA",
                "name", "TEXT",
                "NUTS", "CZNUTS"
        ));
        okresy = renameCSV("CIS0101_CS.csv", ImmutableMap.of(
                "id", "CHODNOTA",
                "name", "TEXT",
                "NUTS", "CZNUTS",
                "KOD_RUIAN", "KOD_RUIAN"
        ));
        obce = renameCSV("CIS0043_CS.csv", ImmutableMap.of(
                "id", "CHODNOTA",
                "name", "TEXT"
        ));
        createBinding("VAZ0100_0101_CS.csv", "krajId", okresy);
        createBinding("VAZ0100_0043_CS.csv", "krajId", obce);
        createBinding("VAZ0101_0043_CS.csv", "okresId", obce);
        createDemographics(kraje, okresy, obce);
    }

    private List<Map<String, String>> renameCSV(String resourceName, Map<String, String> fieldsDict) {
        CSVReader csv;
        try {
            String resource = Main.class.getClassLoader().getResource(resourceName).getPath();
            csv = new CSVReader(new FileReader(resource, Charset.forName("Cp1250")));
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return DataPreprocessor.PrepareData(csv.getLinesAsMaps(), fieldsDict);
    }

    private void createBinding(String resourceName, String newFieldName, List<Map<String, String>> lineMaps) {
        Map<String, String> binding = DataPreprocessor.PrepareBinding(
                Objects.requireNonNull(renameCSV(resourceName, ImmutableMap.of(
                                "CHODNOTA1", "CHODNOTA1",
                                "CHODNOTA2", "CHODNOTA2"
                )))
        );

        for(Map<String, String> map: lineMaps) {
            map.put(newFieldName, binding.get(map.get("id")));
        }
    }

    public Map<String, String> getById(String id, List<Map<String, String>> targetData) {
        return targetData
                .stream()
                .filter(author -> author.get("id").equals(id))
                .findFirst()
                .orElse(null);
    }

    private void createDemographics(List<Map<String, String>> kraje,
                                    List<Map<String, String>> okresy,
                                    List<Map<String, String>> obce) {
        List<File> demFiles = getDemographicResources();
        for(File res: demFiles) {
            System.out.println(res);
        }
        //        List<Map<String, String>> demLines = renameCSV()
    }

    /**
     * Searches the resources' directory for files matching the demographic file naming convention
     * @return List of available demographic files
     */
    private List<File> getDemographicResources() {
        File resourcesPath = getResourcesPath();
        List<File> demFiles = new ArrayList<>();
        try(Stream<Path> resources = Files.walk(resourcesPath.toPath())) {
            demFiles = resources.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().startsWith("130141-"))
                    .filter(p -> p.getFileName().toString().endsWith(".csv"))
                    .map(path -> new File(path.toString()))
                    .toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return demFiles;
    }

    /**
     * Finds the resources' folder of the Main class. Uses CIS0043_CS.csv which must be present
     * @return path to resources folder
     */
    private File getResourcesPath() {
        URL resource = Main.class.getClassLoader().getResource("CIS0043_CS.csv");
        assert resource != null;
        return new File(new File(resource.getFile()).getParent());
    }

}

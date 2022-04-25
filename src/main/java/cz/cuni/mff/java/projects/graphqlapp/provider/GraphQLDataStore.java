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

    /**
     * Reads csv file from resources folder and keeps and renames the csv columns according to fieldsDict
     * Returns a list of maps, each csv line returning one map with keys according to the ones provided
     * in fieldsDict and values according to original csv column
     * @param resourceName name of the csv file in the resources folder
     * @param fieldsDict map of original column name: new map key name
     * @return list of: map for each csv line
     */
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
                .filter(area -> area.get("id").equals(id))
                .findFirst()
                .orElse(null);
    }

    private void createDemographics(List<Map<String, String>> kraje,
                                    List<Map<String, String>> okresy,
                                    List<Map<String, String>> obce) {
        List<File> demFiles = getDemographicResources();
        Map<String, Map<String, String>> demographics = new HashMap<>();

        for(File res: demFiles) {
            System.out.println(res);
            List<Map<String, String>> demLines = renameCSV(res.getName(), ImmutableMap.of(
                    "value", "hodnota",
                    "type", "vuk",
                    "codebook", "vuzemi_cis",
                    "cb_code", "vuzemi_kod",
                    "year", "rok"
            ));
            for(Map<String, String> demLine: demLines) {
                safeInsertDemLine(demographics, demLine);
            }
            for(Map.Entry<String, Map<String, String>> demLine: demographics.entrySet()) {
                System.out.println(demLine.getValue());
            }
        }
    }

    private void safeInsertDemLine(Map<String, Map<String, String>> demographics, Map<String, String> line) {
        String demId = line.get("codebook") + "-" + line.get("cb_code");
        demographics.computeIfAbsent(demId, k -> new HashMap<>(
                Map.of("year", line.get("year"))
        ));  // If demId map is not yet created, create it
        demographics.get(demId).put(getDemType(line.get("type")), line.get("value"));
    }

    private String getDemType(String vuk) {
        switch (vuk) {
            case "DEM0008" -> { return "deaths"; }
            case "DEM0009" -> { return "immigrations"; }
            case "DEM0010" -> { return "emigrations"; }
            case "DEM0011" -> { return "natGrowth"; }
            case "DEM0012" -> { return "totalGrowth"; }
            case "DEM0007" -> { return "births"; }
            case "DEM0001" -> { return "migSaldo"; }
            case "DEM0004" -> { return "popMean"; }
            default -> { return vuk; }
        }
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
     * @return resources folder
     */
    private File getResourcesPath() {
        URL resource = Main.class.getClassLoader().getResource("CIS0043_CS.csv");
        assert resource != null;
        return new File(new File(resource.getFile()).getParent());
    }

}

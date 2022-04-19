package cz.cuni.mff.java.projects.graphqlapp.provider;

import cz.cuni.mff.java.projects.graphqlapp.Main;
import cz.cuni.mff.java.projects.graphqlapp.csv.CSVReader;
import graphql.com.google.common.collect.ImmutableMap;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GraphQLDataStore {

    public List<Map<String, String>> getBooks() {
        return books;
    }

    public List<Map<String, String>> getAuthors() {
        return authors;
    }

    private final List<Map<String, String>> books = Arrays.asList(
            ImmutableMap.of("id", "book-1",
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-1"),
            ImmutableMap.of("id", "book-2",
                    "name", "Moby Dick",
                    "pageCount", "635",
                    "authorId", "author-2"),
            ImmutableMap.of("id", "book-3",
                    "name", "Interview with the vampire",
                    "pageCount", "371",
                    "authorId", "author-3")
    );

    private final List<Map<String, String>> authors = Arrays.asList(
            ImmutableMap.of("id", "author-1",
                    "firstName", "Joanne",
                    "lastName", "Rowling"),
            ImmutableMap.of("id", "author-2",
                    "firstName", "Herman",
                    "lastName", "Melville"),
            ImmutableMap.of("id", "author-3",
                    "firstName", "Anne",
                    "lastName", "Rice")
    );

    public List<Map<String, String>> getKraje() {
        return kraje;
    }

    //    private List<Map<String, String>> obce;
    //    private List<Map<String, String>> okresy;
    private List<Map<String, String>> kraje;

    public GraphQLDataStore() {
        CSVReader csv;
        try {
            String resourceName = Main.class.getClassLoader().getResource("CIS0100_CS.csv").getPath();
            csv = new CSVReader(new FileReader(resourceName, Charset.forName("Cp1250")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        kraje = DataPreprocessor.PrepareData(csv.getLinesAsMaps(), ImmutableMap.of(
                "id", "CHODNOTA",
                "name", "TEXT",
                "NUTS", "CZNUTS"
        ));
    }




}

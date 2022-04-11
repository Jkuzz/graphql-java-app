package cz.cuni.mff.java.projects.graphqlapp;

import graphql.com.google.common.collect.ImmutableMap;

import java.io.FileReader;
import java.io.IOException;
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

    private List<Map<String, String>> characters;

    public GraphQLDataStore() {
        CSVReader csv;
        try {
            String resourceName = Main.class.getClassLoader().getResource("year.csv").getPath();
            csv = new CSVReader(new FileReader(resourceName));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        characters = csv.getLinesAsMaps();

    }

    public List<Map<String, String>> getCharacters() {
        return characters;
    }
}

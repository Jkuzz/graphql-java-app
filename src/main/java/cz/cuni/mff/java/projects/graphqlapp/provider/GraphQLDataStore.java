package cz.cuni.mff.java.projects.graphqlapp.provider;

import cz.cuni.mff.java.projects.graphqlapp.Main;
import cz.cuni.mff.java.projects.graphqlapp.csv.CSVReader;
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

    /**
     * Search for a character by name. Handles unnamed spouses by creating dummy maps.
     * @param name character name to be searched for
     * @return Map of character
     */
    public Map<String, String> searchByName(String name) {
        System.out.println("Searching for: \"" + name + "\"");
        if(name.equals("Unnamed wife") || name.equals("Unnamed husband")) {
            return getDummySpouseMap(name.equals("Unnamed husband"), name);
        }
        return getCharacters()
            .stream()
            .filter(character -> character.get("name").equals(name))
            .findFirst()
            .orElse(null);
    }

    /**
     * Creates a dummy map for an unknown spouse of a known character.
     * Returned map in format identical to that of other characters.
     * @param isMale true if spouse is husband
     * @param spouseName name of the character for which the spouse is being generated
     * @return spouse map
     */
    private Map<String, String> getDummySpouseMap(boolean isMale, String spouseName) {
        //id,birth,death,gender,hair,height,name,race,realm,spouse
        return Map.of(
            "id", "-1",
            "birth", "Unknown",
            "death", "Unknown",
            "gender", isMale ? "Male" : "Female",
            "hair", "Unknown",
            "height", "Unknown",
            "name", "Unknown",
            "race", "Unknown",
            "realm", "Unknown",
            "Spouse", spouseName
        );
    }
}

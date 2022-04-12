package cz.cuni.mff.java.projects.graphqlapp.provider;

import graphql.schema.DataFetcher;

import java.util.List;
import java.util.Map;

public class GraphQLDataFetchers {

    private final GraphQLDataStore dataStore = new GraphQLDataStore();

    public DataFetcher<Map<String, String>> getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return dataStore.getBooks()
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<List<Map<String, String>>> getBooksByAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            String authorID = dataFetchingEnvironment.getArgument("authorId");
            return dataStore.getBooks()
                    .stream()
                    .filter(book -> book.get("authorId").equals(authorID))
                    .toList();
        };
    }

    public DataFetcher<List<Map<String, String>>> getBooksDataFetcher() {
        return dataFetchingEnvironment -> dataStore.getBooks();
    }

    public DataFetcher<List<Map<String, String>>> getCharactersDataFetcher() {
        return dataFetchingEnvironment -> dataStore.getCharacters();
    }

    public DataFetcher<Map<String, String>> getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            return dataStore.getAuthors()
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<Map<String, String>> getCharacterByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            return dataStore.getCharacters().get(Integer.parseInt(id));
        };
    }

    public DataFetcher<Map<String, String>> getCharacterSpouseDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> character = dataFetchingEnvironment.getSource();
            String name = character.get("spouse");
            return dataStore.searchByName(name);
        };
    }

    public DataFetcher<Map<String, String>> getCharacterByNameDataFetcher() {
        return dataFetchingEnvironment -> {
            String name = dataFetchingEnvironment.getArgument("name");
            return dataStore.searchByName(name);
        };
    }
}
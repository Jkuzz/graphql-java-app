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

    public DataFetcher<List<Map<String, String>>> getKrajeDataFetcher() {
        return dataFetchingEnvironment -> dataStore.getKraje();
    }

    public DataFetcher<List<Map<String, String>>> getOkresyDataFetcher() {
        return dataFetchingEnvironment -> dataStore.getOkresy();
    }

    public DataFetcher<List<Map<String, String>>> getObceDataFetcher() {
        return dataFetchingEnvironment -> dataStore.getObce();
    }

    public DataFetcher<Map<String, String>> getKrajDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> kraj = dataFetchingEnvironment.getSource();
            String krajId = kraj.get("krajId");
            return dataStore.getById(krajId, dataStore.getKraje());
        };
    }

    public DataFetcher<Map<String, String>> getOkresDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> okres = dataFetchingEnvironment.getSource();
            String okresId = okres.get("okresId");
            return dataStore.getById(okresId, dataStore.getOkresy());
        };
    }

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


}
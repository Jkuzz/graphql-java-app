package cz.cuni.mff.java.projects.graphqlapp;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;

import java.io.*;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class GraphQLProvider {
    private final GraphQL graphQL;
    private final GraphQLDataFetchers graphQLDataFetchers;

    public GraphQL getGraphQL() {
        return graphQL;
    }

    public GraphQLProvider() {
        this.graphQLDataFetchers = new GraphQLDataFetchers();
        GraphQLSchema schema = buildSchema(getResourceAsString("schema.graphqls"));
        this.graphQL = GraphQL.newGraphQL(schema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("booksByAuthor", graphQLDataFetchers.getBooksByAuthorDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("books", graphQLDataFetchers.getBooksDataFetcher()))
                .type(newTypeWiring("Book")
                        .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .build();
    }

    /**
     * Reads file included in resources folder.
     * @param fileName name of file
     * @return String content of the file, empty if file not found
     */
    private String getResourceAsString(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while(true) {
            try {
                int next = inputStream.read();
                if(next == -1)
                    return sb.toString();
                sb.append((char)next);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

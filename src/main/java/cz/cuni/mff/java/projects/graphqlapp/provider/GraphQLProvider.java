package cz.cuni.mff.java.projects.graphqlapp.provider;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class GraphQLProvider {
    private final GraphQL graphQL;
    private final GraphQLDataFetchers graphQLDataFetchers;

    /**
     * This provides the GraphQL object to be queried
     */
    public GraphQL getGraphQL() {
        return graphQL;
    }

    public GraphQLProvider() {
        this.graphQLDataFetchers = new GraphQLDataFetchers();
        GraphQLSchema schema = buildSchema(ResourceGetter.getResourceAsString("schema.graphqls"));
        this.graphQL = GraphQL.newGraphQL(schema).build();
    }

    /**
     * Builds the schema using GraphQL-java from the raw schema file contents and the runtime wiring
     * @param sdl The parsed GraphQL schema file
     * @return The build schema
     */
    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }


    /**
     * Builds the runtime wiring that is used in the schema generator to create an ExecutableSchema.
     * This links individual queries with their data fetchers, which are provided by graphQLDataFetchers
     * @return The runtime wiring
     */
    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("booksByAuthor", graphQLDataFetchers.getBooksByAuthorDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("books", graphQLDataFetchers.getBooksDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("kraje", graphQLDataFetchers.getKrajeDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("okresy", graphQLDataFetchers.getOkresyDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("obce", graphQLDataFetchers.getObceDataFetcher()))
                .type(newTypeWiring("Book")
                        .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .type(newTypeWiring("Okres")
                        .dataFetcher("kraj", graphQLDataFetchers.getOkresKrajDataFetcher()))
                .build();
    }
}

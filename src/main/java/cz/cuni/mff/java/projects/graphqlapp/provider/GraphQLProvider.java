package cz.cuni.mff.java.projects.graphqlapp.provider;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

public class GraphQLProvider {
    private final GraphQL graphQL;
    private final GraphQLDataFetchers graphQLDataFetchers;

    public GraphQL getGraphQL() {
        return graphQL;
    }

    public GraphQLProvider() {
        this.graphQLDataFetchers = new GraphQLDataFetchers();
        GraphQLSchema schema = buildSchema(ResourceGetter.getResourceAsString("schema.graphqls"));
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
                .type(newTypeWiring("Query")
                        .dataFetcher("characterById", graphQLDataFetchers.getCharacterByIdDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("characterByName", graphQLDataFetchers.getCharacterByNameDataFetcher()))
                .type(newTypeWiring("Book")
                        .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .type(newTypeWiring("Query")
                        .dataFetcher("characters", graphQLDataFetchers.getCharactersDataFetcher()))
                .type(newTypeWiring("Character")
                        .dataFetcher("spouse", graphQLDataFetchers.getCharacterSpouseDataFetcher()))
                .build();
    }
}

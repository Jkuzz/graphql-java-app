package cz.cuni.mff.java.projects.graphqlapp;

import graphql.ExecutionResult;
import graphql.GraphQL;

public class Main {

    public static void main(String[] args) {
        GraphQLProvider provider = new GraphQLProvider();
        GraphQL graphQL = provider.getGraphQL();
        String query = """
                {
                bookById(id: "book-2") {
                name
                pageCount
                }
                }""";
        ExecutionResult result = graphQL.execute(query);
        System.out.println(result.getData().toString());
    }
}

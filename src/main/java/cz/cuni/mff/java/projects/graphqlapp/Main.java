package cz.cuni.mff.java.projects.graphqlapp;

import cz.cuni.mff.java.projects.graphqlapp.provider.GraphQLProvider;
import graphql.ExecutionResult;
import graphql.GraphQL;

public class Main {

    public static void main(String[] args) {
        GraphQL graphQL = new GraphQLProvider().getGraphQL();
        String query = """
                {
                krajById(id: 3018) {
                    name
                    demographics {
                        year
                        migSaldo
                    }
                }
                }""";
        ExecutionResult result = graphQL.execute(query);
        if (!result.getErrors().isEmpty()) {
            System.out.println("Error occurred during query.");
            System.out.println(result.getErrors());
        }
        if (result.getData() != null) {
            System.out.println(result.getData().toString());
        }

    }
}

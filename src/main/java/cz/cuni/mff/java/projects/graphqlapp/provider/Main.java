package cz.cuni.mff.java.projects.graphqlapp.provider;

import graphql.ExecutionResult;
import graphql.GraphQL;

/**
 * Mostly for development purposes.
 */
public class Main {

    /**
     * Executes the query and prints result.
     * @param args unused
     */
    public static void main(String[] args) {
        GraphQL graphQL = new GraphQLProvider().getGraphQL();
        System.out.println(graphQL.getGraphQLSchema().getType("Kraj"));
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

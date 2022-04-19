package cz.cuni.mff.java.projects.graphqlapp.cmd;

import cz.cuni.mff.java.projects.graphqlapp.provider.GraphQLProvider;
import graphql.ExecutionResult;
import graphql.GraphQL;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        GraphQL graphQL = new GraphQLProvider().getGraphQL();
        String query = "";
        if(args.length < 1) {
            System.out.println("Enter your query:");
            query = getUserQuery();
        } else {
            try {
                query = Files.readString(Path.of(args[0]));
            } catch (IOException e) {
                System.out.println("Provided file name could not be found.");
                e.printStackTrace();
                System.exit(2);
            }
        }
        ExecutionResult result = graphQL.execute(query);
        if (!result.getErrors().isEmpty()) {
            System.out.println("Error occurred during query.");
            System.out.println(result.getErrors());
        }
        if (result.getData() != null) {
            System.out.println(result.getData().toString());
        } else {
            System.out.println("No data match the query");
        }
    }

    /**
     * Reads user input from stdin and returns it as string
     * @return user query string
     */
    private static String getUserQuery() {
        Scanner input = new Scanner(System.in);
        StringBuilder userQuery = new StringBuilder();
        while(input.hasNext()) {
            userQuery.append(input.nextLine());
        }
        System.out.println(userQuery);
        return userQuery.toString();
    }
}

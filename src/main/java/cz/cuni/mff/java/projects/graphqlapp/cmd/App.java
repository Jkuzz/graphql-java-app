package cz.cuni.mff.java.projects.graphqlapp.cmd;

import cz.cuni.mff.java.projects.graphqlapp.provider.GraphQLProvider;
import graphql.ExecutionResult;
import graphql.GraphQL;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
                FileReader fileReader = new FileReader(args[0]);
                query = fileReader.toString();
            } catch (FileNotFoundException e) {
                System.out.println("Provided file name could not be found.");
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
        }
    }

    private static String getUserQuery() {
        Scanner input = new Scanner(System.in);
        String line = input.nextLine();
        System.out.println(line);
        return line;
    }
}

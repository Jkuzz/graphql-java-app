package cz.cuni.mff.java.projects.graphqlapp.cmd;

import cz.cuni.mff.java.projects.graphqlapp.provider.GraphQLProvider;
import graphql.ExecutionResult;
import graphql.GraphQL;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        GraphQL graphQL = new GraphQLProvider().getGraphQL();
        for(int i = 0;; i += 1) {
            String query = "";
            if(args.length <= i) {
                // System.out.println("Enter your query:");
                query = getPipeQuery();
                if(query.equalsIgnoreCase("exit") || query.equalsIgnoreCase("quit")) {
                    break;
                }
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
        return userQuery.toString().trim();
    }

    private static String getPipeQuery() {
        try {
            // Connect to the named pipe
            Scanner pipe = new Scanner(new File("./query-pipe"));
            StringBuilder userQuery = new StringBuilder();
            while(pipe.hasNext()) {
                userQuery.append(pipe.next()).append(' ');
            }
            pipe.close();
            return userQuery.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}

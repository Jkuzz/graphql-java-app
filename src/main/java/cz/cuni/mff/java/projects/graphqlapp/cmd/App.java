package cz.cuni.mff.java.projects.graphqlapp.cmd;
import cz.cuni.mff.java.projects.graphqlapp.provider.GraphQLProvider;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        GraphQL graphQL = new GraphQLProvider().getGraphQL();
        CommandLine cmd = parseOptions(args);
        boolean continueExec = cmd.hasOption("c");

        if(cmd.hasOption("f")) {
            for(String fileName: cmd.getOptionValues("f")) {
                String query = "";
                try {
                    query = Files.readString(Path.of(fileName));
                } catch (IOException e) {
                    System.out.println("Provided file name could not be found.");
                    e.printStackTrace();
                    System.exit(2);
                }
                System.out.println(executeQuery(graphQL, query));
            }
            // If files queries were executed and no pipe and no continuous querying, exit
            if(!cmd.hasOption("p") && !continueExec) {
                System.exit(0);
            }
        }

        do {  // Happens once if continuous exec is not enabled, else until terminated by user
            Scanner queryScanner = getQueryScanner(cmd);
            if(queryScanner == null) {  // Exception when reading pipe file
                System.exit(1);
            }
            String query = getUserQuery(queryScanner);
            if(query.length() == 0 || query.equalsIgnoreCase("exit") || query.equalsIgnoreCase("quit")) {
                break;
            }
            System.out.println(executeQuery(graphQL, query));
        } while (continueExec);
    }


    /**
     * Uses program options to determine which scanner to use for query reading
     * @param cmd parsed options
     * @return Scanner for reading queries
     */
    private static Scanner getQueryScanner(CommandLine cmd) {
        if(cmd.hasOption("p")) {
            Scanner outScanner;
            try {
                outScanner = new Scanner(new File(cmd.getOptionValue("p")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Could not open pipe file");
                return null;
            }
            return outScanner;
        } else {
            return new Scanner(System.console().reader());
        }
    }


    /**
     * Execute query on graphQL provider
     * @param graphQL provider to query
     * @param query to execute
     * @return response as string or error message
     */
    private static String executeQuery(GraphQL graphQL, String query) {
        ExecutionResult result = graphQL.execute(query);
        if (!result.getErrors().isEmpty()) {
            System.out.println(result.getErrors());
            return("Error occurred during query.");
        }
        if (result.getData() != null) {
            return(result.getData().toString());
        } else {
            return("No data match the query");
        }
    }


    /**
     * Uses Apache Commons CLI to parse options ang arguments from command line
     * @param args program arguments
     * @return parsed options
     */
    private static CommandLine parseOptions(String[] args) {
        Options options = new Options();

        Option pipe = new Option("p", "pipe input", true,
                "input FIFO pipe path (see man mkfifo)"
        );
        pipe.setRequired(false);
        options.addOption(pipe);

        Option file = new Option("f", "file input", true,
                "input file path"
        );
        file.setRequired(false);
        options.addOption(file);

        Option stdin = new Option("i", "std input", false,
                "input queries using std input"
        );
        stdin.setRequired(false);
        options.addOption(stdin);

        Option continuous = new Option("c", "continuous query input", false,
                "don't terminate after input query(ies) are executed and wait for more"
        );
        continuous.setRequired(false);
        options.addOption(continuous);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }
        return null;
    }

    /**
     * Reads user input from stdin and returns it as string
     * @return user query string
     */
    private static String getUserQuery(Scanner input) {
        StringBuilder userQuery = new StringBuilder();
        while(input.hasNext()) {
            userQuery.append(input.next().trim()).append(' ');
        }
        input.close();
        return userQuery.toString().trim();
    }
}

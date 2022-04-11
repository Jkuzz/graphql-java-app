package cz.cuni.mff.java.projects.graphqlapp;

import graphql.ExecutionResult;
import graphql.GraphQL;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        GraphQL graphQL = new GraphQLProvider().getGraphQL();
//        String query = """
//                {
//                bookOne: bookById(id: "book-1") {
//                    name
//                    pageCount
//                    author {
//                        id
//                        firstName
//                        lastName
//                    }
//                }
//                }""";
//        query = """
//                {
//                booksByAuthor(authorId: "author-1") {
//                    name
//                    pageCount
//                    author {
//                        firstName
//                        lastName
//                    }
//                }
//                }""";
//        ExecutionResult result = graphQL.execute(query);
//        if (!result.getErrors().isEmpty()) {
//            System.out.println("Error occurred during query.");
//            System.out.println(result.getErrors());
//        }
//        if (result.getData() != null) {
//            System.out.println(result.getData().toString());
//        }
        try {
            String resourceName = Main.class.getClassLoader().getResource("year.csv").getPath();
            System.out.println(resourceName);
            CSVReader reader = new CSVReader(new FileReader(resourceName));
            System.out.println(reader.getHeader());
            System.out.println(reader.getLine(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

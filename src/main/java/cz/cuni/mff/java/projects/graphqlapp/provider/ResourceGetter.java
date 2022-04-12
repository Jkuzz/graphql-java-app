package cz.cuni.mff.java.projects.graphqlapp.provider;

import java.io.IOException;
import java.io.InputStream;

public class ResourceGetter {
    /**
     * Reads file included in resources folder.
     * @param fileName name of file
     * @return String content of the file, empty if file not found
     */
    public static String getResourceAsString(String fileName) {
        ClassLoader classLoader = ResourceGetter.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while(true) {
            try {
                int next = inputStream.read();
                if(next == -1)
                    return sb.toString();
                sb.append((char)next);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
